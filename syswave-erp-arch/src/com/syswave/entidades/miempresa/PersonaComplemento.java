package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.PrimaryKeyByIdPersona;

/**
 *
 * @author Gilberto Aaron Jimenez Montelongo
 */
public class PersonaComplemento extends PrimaryKeyByIdPersona
{

    private String religion;
    private boolean es_genero;
    private int es_estado_civil;
    private int es_tipo_sangre;
    private float altura;
    private float peso;

    protected ForeignKey navigation;

    //------------------------------------------------------------------------------    
    public PersonaComplemento()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //------------------------------------------------------------------------------    
    public PersonaComplemento(PersonaComplemento that)
    {
        super();
        createAtributes();
        assign(that);
    }

    //------------------------------------------------------------------------------    
    private void createAtributes()
    {
        navigation = new ForeignKey();
    }

    //------------------------------------------------------------------------------    
    private void assign(PersonaComplemento that)
    {
        super.assign(that);
        religion = that.religion;
        es_genero = that.es_genero;
        es_estado_civil = that.es_estado_civil;
        es_tipo_sangre = that.es_tipo_sangre;
        altura = that.altura;
        peso = that.peso;

        navigation = that.navigation;
    }

    //------------------------------------------------------------------------------    
    private void initAtributes()
    {
        religion = EMPTY_STRING;
        es_genero = false;
        es_estado_civil = EMPTY_INT;
        es_tipo_sangre = EMPTY_INT;
        altura = EMPTY_FLOAT;
        peso = EMPTY_FLOAT;
    }

    //------------------------------------------------------------------------------     
    @Override
    public int getIdPersona()
    {
        return null != navigation.getFk_persona_complemento()
                ? navigation.getFk_persona_complemento().getId()
                : super.getIdPersona();
    }

    //------------------------------------------------------------------------------     
    @Override
    public int getIdPersona_Viejo()
    {
        return null != navigation.getFk_persona_complemento()
                ? navigation.getFk_persona_complemento().getId_Viejo()
                : super.getIdPersona_Viejo();
    }

    //------------------------------------------------------------------------------
    @Override
    public void setIdPersona(int value)
    {
        super.setIdPersona(value);
        navigation.releasePersona();
    }

    //------------------------------------------------------------------------------
    public Persona getHasOnePersona()
    {
        return null != navigation.getFk_persona_complemento()
                ? navigation.getFk_persona_complemento()
                : navigation.joinPersona(super.getIdPersona());
    }

    //------------------------------------------------------------------------------
    public void setHasOnePersona(Persona value)
    {
        navigation.setFk_persona_complemento(value);
    }

    //------------------------------------------------------------------------------
    public String getReligion()
    {
        return religion;
    }

    //------------------------------------------------------------------------------
    public void setReligion(String value)
    {
        this.religion = value;
    }

    //------------------------------------------------------------------------------
    public boolean esGenero()
    {
        return es_genero;
    }

    //------------------------------------------------------------------------------
    public void setGenero(boolean value)
    {
        this.es_genero = value;
    }

    //------------------------------------------------------------------------------
    public void setGenero(int value)
    {
        if (value > 0)
            this.es_genero = true;
        else
            this.es_genero = false;
    }

    //------------------------------------------------------------------------------
    public int getGenero()
    {
        if (es_genero == true)
            return 1;

        return 0;
    }

    //------------------------------------------------------------------------------
    public int getEstadoCivil()
    {
        return es_estado_civil;
    }

    //------------------------------------------------------------------------------
    public void setEstadoCivil(int value)
    {
        this.es_estado_civil = value;
    }

    //------------------------------------------------------------------------------
    public int getEsTipoSangre()
    {
        return es_tipo_sangre;
    }

    //------------------------------------------------------------------------------
    public void setEsTipoSangre(int value)
    {
        this.es_tipo_sangre = value;
    }

    //------------------------------------------------------------------------------
    public float getAltura()
    {
        return altura;
    }

    //------------------------------------------------------------------------------
    public void setAltura(float altura)
    {
        this.altura = altura;
    }

    //------------------------------------------------------------------------------
    public float getPeso()
    {
        return peso;
    }

    //------------------------------------------------------------------------------
    public void setPeso(float peso)
    {
        this.peso = peso;
    }

    //------------------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releasePersona();
    }

    //------------------------------------------------------------------------------
    public void copy(PersonaComplemento that)
    {
        assign(that);
    }

    //---------------------------------------------------------------------
    /* ==================  PENDIENTE: ==========================
    
       Quedaron pendientes los campos de es_activo y relacion
        que se muestran en el procedimiento almacenado*/
    @Override
    public void acceptChanges()
    {
        super.acceptChanges();
        navigation.acceptChanges();
    }

    //---------------------------------------------------------------------
    protected class ForeignKey
    {

        private Persona fk_persona_complemento;

        //---------------------------------------------------------------------
        public Persona getFk_persona_complemento()
        {
            return fk_persona_complemento;
        }

        //---------------------------------------------------------------------
        public void setFk_persona_complemento(Persona value)
        {
            this.fk_persona_complemento = value;
            this.fk_persona_complemento.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Persona joinPersona(int id_persona)
        {
            fk_persona_complemento = new Persona();
            fk_persona_complemento.setId(id_persona);
            return fk_persona_complemento;
        }

        //---------------------------------------------------------------------
        public void releasePersona()
        {
            fk_persona_complemento = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_persona_complemento && fk_persona_complemento.isDependentOn())
                fk_persona_complemento.acceptChanges();
        }
    }
}
