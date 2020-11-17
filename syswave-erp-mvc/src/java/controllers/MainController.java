package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author victor
 */
@Controller
@RequestMapping(value = "/main")
public class MainController //extends AbstractController
{
    
    /*public MainController()
    {
    }
    
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }*/
    
    @RequestMapping(value = "/index", method=RequestMethod.GET)
    public String Index(Model response)
    {
        response.addAttribute("response", "This my response");
        return "main/index";
    }

    
}
