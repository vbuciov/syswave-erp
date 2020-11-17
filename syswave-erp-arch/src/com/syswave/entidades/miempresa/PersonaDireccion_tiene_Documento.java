package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByIdDocumentoPersona;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaDireccion_tiene_Documento extends CompositeKeyByIdDocumentoPersona
{
    protected int rol;
    
    protected ForeignKey navigation;
    
        //---------------------------------------------------------------------
    public PersonaDireccion_tiene_Documento()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public PersonaDireccion_tiene_Documento(PersonaDireccion_tiene_Documento that)
    {
        super();
        createAtributes();
        assign(that);
    }

    //---------------------------------------------------------------------
    private void createAtributes()
    {
        navigation = new ForeignKey();
    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        rol = EMPTY_INT;
    }

    //---------------------------------------------------------------------
    private void assign(PersonaDireccion_tiene_Documento that)
    {
        super.assign(that);
        rol = that.rol;
        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    @Override
    public void setIdDocumento(int value)
    {
        super.setIdDocumento(value);
        navigation.releaseDocumentos();
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdDocumento()
    {
        return null != navigation.getFk_persona_direccion_documento_id()
                ? navigation.getFk_persona_direccion_documento_id().getId()
                : super.getIdDocumento();
    }
    
        //---------------------------------------------------------------------
    @Override
    public int getIdDocumento_Viejo()
    {
        return null != navigation.getFk_persona_direccion_documento_id()
                ? navigation.getFk_persona_direccion_documento_id().getId_Viejo()
                : super.getIdDocumento_Viejo();
    }

    //---------------------------------------------------------------------
    @Override
    public void setIdPersona(int value)
    {
        super.setIdPersona(value);
        navigation.releasePersonaDirecciones();
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdPersona()
    {
        return null != navigation.getFk_documento_persona_direccion_id()
                ? navigation.getFk_documento_persona_direccion_id().getIdPersona()
                : super.getIdPersona();
    }
    
        //---------------------------------------------------------------------
    @Override
    public int getIdPersona_Viejo()
    {
        return null != navigation.getFk_documento_persona_direccion_id()
                ? navigation.getFk_documento_persona_direccion_id().getIdPersona_Viejo()
                : super.getIdPersona_Viejo();
    }

    //---------------------------------------------------------------------
    @Override
    public void setConsecutivo(int value)
    {
        super.setConsecutivo(value);
        navigation.releasePersonaDirecciones();
    }

    //---------------------------------------------------------------------
    @Override
    public int getConsecutivo()
    {
        return null != navigation.getFk_documento_persona_direccion_id()
                ? navigation.getFk_documento_persona_direccion_id().getConsecutivo()
                : super.getConsecutivo();
    }
    
        //---------------------------------------------------------------------
    @Override
    public int getConsecutivo_Viejo()
    {
        return null != navigation.getFk_documento_persona_direccion_id()
                ? navigation.getFk_documento_persona_direccion_id().getConsecutivo_Viejo()
                : super.getConsecutivo_Viejo();
    }

    //---------------------------------------------------------------------
    public void setRol(int value)
    {
        rol = value;
    }

    //---------------------------------------------------------------------
    public int getEsRol()
    {
        return rol;
    }

    //---------------------------------------------------------------------
    public Documento getHasOneDocumento()
    {
        return null != navigation.getFk_persona_direccion_documento_id()
                ? navigation.getFk_persona_direccion_documento_id()
                : navigation.joinDocumentos(super.getIdDocumento());
    }

    //---------------------------------------------------------------------
    public void setHasOneDocumento(Documento value)
    {
        navigation.setFk_persona_direccion_documento_id(value);
    }

    //---------------------------------------------------------------------
    public PersonaDireccion getHasOnePersonaDireccion()
    {
        return null != navigation.getFk_documento_persona_direccion_id()
                ? navigation.getFk_documento_persona_direccion_id()
                : navigation.joinPersonaDirecciones(super.getConsecutivo(),
                                                    super.getIdPersona());
    }

    //---------------------------------------------------------------------
    public void setHasOnePersonaDireccion(PersonaDireccion value)
    {
        navigation.setFk_documento_persona_direccion_id(value);
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releaseDocumentos();
        navigation.releasePersonaDirecciones();
    }

    //---------------------------------------------------------------------
    public void copy(PersonaDireccion_tiene_Documento that)
    {
        assign(that);
    }

    //---------------------------------------------------------------------
    @Override
    public void acceptChanges()
    {
        super.acceptChanges();
        navigation.acceptChanges();
    }

    //---------------------------------------------------------------------
    protected class ForeignKey
    {
        
        protected Documento fk_persona_direccion_documento_id;
        protected PersonaDireccion fk_documento_persona_direccion_id;

        //---------------------------------------------------------------------
        public Documento getFk_persona_direccion_documento_id()
        {
            return fk_persona_direccion_documento_id;
        }

        //---------------------------------------------------------------------
        public void setFk_persona_direccion_documento_id(Documento value)
        {
            this.fk_persona_direccion_documento_id = value;
            this.fk_persona_direccion_documento_id.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public PersonaDireccion getFk_documento_persona_direccion_id()
        {
            return fk_documento_persona_direccion_id;
        }

        //---------------------------------------------------------------------
        public void setFk_documento_persona_direccion_id(PersonaDireccion value)
        {
            this.fk_documento_persona_direccion_id = value;
            this.fk_documento_persona_direccion_id.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Documento joinDocumentos(int id_documento)
        {
            fk_persona_direccion_documento_id = new Documento();
            fk_persona_direccion_documento_id.setId(id_documento);
            return fk_persona_direccion_documento_id;
        }

        //---------------------------------------------------------------------
        public void releaseDocumentos()
        {
            fk_persona_direccion_documento_id = null;
        }

        //---------------------------------------------------------------------
        public PersonaDireccion joinPersonaDirecciones(int consecutivo, int id_persona)
        {
            fk_documento_persona_direccion_id = new PersonaDireccion();
            fk_documento_persona_direccion_id.setConsecutivo(consecutivo);
            fk_documento_persona_direccion_id.setIdPersona(id_persona);
            return fk_documento_persona_direccion_id;
        }

        //---------------------------------------------------------------------
        public void releasePersonaDirecciones()
        {
            fk_documento_persona_direccion_id = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_persona_direccion_documento_id && fk_persona_direccion_documento_id.isDependentOn())
                fk_persona_direccion_documento_id.acceptChanges();
            
            if (null != fk_documento_persona_direccion_id && fk_documento_persona_direccion_id.isDependentOn())
                fk_documento_persona_direccion_id.acceptChanges();
        }
    }
}
