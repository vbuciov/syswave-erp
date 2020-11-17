package controllers;

import com.syswave.entidades.miempresa.Persona;
import com.syswave.logicas.miempresa.PersonasBusinessLogic;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 *
 * @author victor
 */
@Controller
@RequestMapping(value = "/personas")
public class PersonasController /*extends SimpleFormController implements Controller*/
{
    PersonasBusinessLogic service;
    
    public PersonasController()
    {
        //Initialize controller properties here or 
        //in the Web Application Context

        //setCommandClass(Persona.class);
        //setCommandName("Persona");
        //setSuccessView("PersonaView");
        //setFormView("formView");
    }
    
    @RequestMapping(value = {"/index", "/", ""}, method=RequestMethod.GET)
    public String Index(Model response)
    {
        List<Persona> result = service.obtenerLista();
        response.addAttribute("data", result);
        
        return "personas/index";
    }
    
    @RequestMapping(value = "/{id}", method=RequestMethod.GET)
    public String Show(@PathVariable(value="id")Integer id, Model response )
    {
        Persona item = service.obtenerPersonaPorId(id);
        response.addAttribute("item", item);
     
        return "personas/show";
    }
    
    @RequestMapping(value = "/new", method=RequestMethod.GET)
    public String New(Model response)
    {
        Persona item = new Persona();
        
        response.addAttribute("item", item);
        response.addAttribute("method", "post");
        response.addAttribute("action", "/personas/create");
        response.addAttribute("new_record", true);
        
        return "personas/form";
    }
    
    @RequestMapping(value = "/create", method=RequestMethod.POST)
    public ModelAndView Create(@ModelAttribute("item")Persona item )
    {
        ModelAndView response = new ModelAndView("personas/form");
        response.addObject("item", item);
     
        if (service.agregar(item))
            response.setViewName("personas/show");
        
        else
            response.addObject("error", service.getMensaje());
          
        return response;
    }
    
    @RequestMapping(value = {"/edit/{id}"}, method=RequestMethod.GET)
    public String Edit(@PathVariable(value="id")Integer id, Model response)
    {
        Persona item = service.obtenerPersonaPorId(id);
        
        response.addAttribute("item", item);
        response.addAttribute("method", "put");
        response.addAttribute("action", "/personas/update");
        response.addAttribute("new_record", false);
        
        return "personas/form";
    }
    
    @RequestMapping(value = "/update", method=RequestMethod.POST)
    public ModelAndView Update(@ModelAttribute("item")Persona item )
    {
        ModelAndView response = new ModelAndView("personas/form");
        response.addObject("item", item);
        Persona old_item = service.obtenerPersonaPorId(item.getId());
        
        if (old_item.getId() == item.getId())
        {
            item.setNacimiento(old_item.getNacimiento());
            if (service.actualizar(item))
                response.setViewName("personas/show");
            else
                response.addObject("error", service.getMensaje());
        }
        
        else
            response.addObject("error", "The specified element doesn't exist");
          
        return response;
    }
    
    public void setService(PersonasBusinessLogic value)
    {
        this.service = value;
    }
    
    /**
     * You must bind the Date when you submit a HTTP POST. 
     * Spring does not know that this is a Date, it sees it as a String.
     */
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    
    /*@Override
protected void initBinder(PortletRequest request, PortletRequestDataBinder binder) throws Exception {
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    df.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(df, true));

    super.initBinder(request,binder);
}*/
    
        /*@Override
    protected void doSubmitAction(Object command) throws Exception
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }*/

    //Use onSubmit instead of doSubmitAction 
    //when you need access to the Request, Response, or BindException objects
    /*
    @Override
    protected ModelAndView onSubmit(
            HttpServletRequest request, 
            HttpServletResponse response, 
            Object command, 
            BindException errors) throws Exception {
        ModelAndView mv = new ModelAndView(getSuccessView());
        //Do something...
        return mv;
    }
     */

    /*@Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        Map<String, Object> myModel = new HashMap<String, Object>();
        myModel.put("people",service.RetrievePersonas()); 
        return new ModelAndView("index", "model", myModel);
    }*/
}
