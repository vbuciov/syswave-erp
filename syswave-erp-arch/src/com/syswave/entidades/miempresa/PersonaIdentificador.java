package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByClavePersona;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class PersonaIdentificador extends CompositeKeyByClavePersona
{

    private String nota;
    private Integer id_tipo_identificador;

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    public PersonaIdentificador()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public PersonaIdentificador(PersonaIdentificador that)
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
    private void assign(PersonaIdentificador that)
    {
        super.assign(that);
        nota = that.nota;
        id_tipo_identificador = that.id_tipo_identificador;
        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    private void initAtributes()
    {
        nota = EMPTY_STRING;
        id_tipo_identificador = EMPTY_INT;
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdPersona()
    {
        return null != navigation.getFk_persona_identificador()
                ? navigation.getFk_persona_identificador().getId()
                : super.getIdPersona();
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdPersona_Viejo()
    {
        return null != navigation.getFk_persona_identificador()
                ? navigation.getFk_persona_identificador().getId_Viejo()
                : super.getIdPersona_Viejo();
    }

    //---------------------------------------------------------------------
    @Override
    public void setIdPersona(int value)
    {
        super.setIdPersona(value);
        navigation.releasePersona();
    }

    public String getNota()
    {
        return nota;
    }

    public void setNota(String nota)
    {
        this.nota = nota;
    }

    //---------------------------------------------------------------------
    public Persona getHasOnePersona()
    {
        return null != navigation.getFk_persona_identificador()
                ? navigation.getFk_persona_identificador()
                : navigation.joinPersonas(super.getIdPersona());
    }

    //---------------------------------------------------------------------
    public void setHasOnePersona(Persona value)
    {
        navigation.setFk_persona_identificador(value);
    }

    //---------------------------------------------------------------------
    public int getIdTipoIdentificador()
    {
        return null != navigation.getFk_identificador_tipo()
                ? navigation.getFk_identificador_tipo().getId()
                : id_tipo_identificador;
    }

    //---------------------------------------------------------------------
    public void setIdTipoIdentificador(int value)
    {
        id_tipo_identificador = value;
        navigation.releaseTipoIdentificador();
    }

    //---------------------------------------------------------------------
    public Valor getHasOneTipoIdentificador()
    {
        return null != navigation.getFk_identificador_tipo()
                ? navigation.getFk_identificador_tipo()
                : navigation.joinIdentificadorTipo(id_tipo_identificador);
    }

    //---------------------------------------------------------------------
    public void setHasOneTipoIdentificador(Valor value)
    {
        navigation.setFk_identificador_tipo(value);
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releaseTipoIdentificador();
        navigation.releasePersona();
    }

    //---------------------------------------------------------------------
    public void copy(PersonaIdentificador that)
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

        private Persona fk_persona_identificador;
        private Valor fk_identificador_tipo;

        //---------------------------------------------------------------------
        public Persona getFk_persona_identificador()
        {
            return fk_persona_identificador;
        }

        //---------------------------------------------------------------------
        public void setFk_persona_identificador(Persona value)
        {
            this.fk_persona_identificador = value;
            this.fk_persona_identificador.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Persona joinPersonas(int id_persona)
        {
            fk_persona_identificador = new Persona();
            fk_persona_identificador.setId(id_persona);
            return fk_persona_identificador;
        }

        //---------------------------------------------------------------------
        public void releasePersona()
        {
            fk_persona_identificador = null;
        }

        //---------------------------------------------------------------------
        public Valor getFk_identificador_tipo()
        {
            return fk_identificador_tipo;
        }

        //---------------------------------------------------------------------
        public void setFk_identificador_tipo(Valor value)
        {
            this.fk_identificador_tipo = value;
            this.fk_identificador_tipo.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Valor joinIdentificadorTipo(int id_identificador_tipo)
        {
            fk_identificador_tipo = new Valor();
            fk_identificador_tipo.setId(id_identificador_tipo);
            return fk_identificador_tipo;
        }

        //---------------------------------------------------------------------
        public void releaseTipoIdentificador()
        {
            fk_identificador_tipo = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_persona_identificador && fk_persona_identificador.isDependentOn())
                fk_persona_identificador.acceptChanges();

            if (null != fk_identificador_tipo && fk_identificador_tipo.isDependentOn())
                fk_identificador_tipo.acceptChanges();
        }
    }
}
