package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByValorVariante;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class VarianteIdentificador extends CompositeKeyByValorVariante
{

    private int idTipoIdentificador;

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    public VarianteIdentificador()
    {
        super();
        createAtributes();
        initAttributes();
    }

    //---------------------------------------------------------------------
    public VarianteIdentificador(VarianteIdentificador that)
    {
        super();
        createAtributes();
        assign(that);
    }

    //---------------------------------------------------------------------
    private void initAttributes()
    {
        idTipoIdentificador = EMPTY_INT;
    }

    //---------------------------------------------------------------------
    private void assign(VarianteIdentificador that)
    {
        super.assign(that);
        idTipoIdentificador = that.idTipoIdentificador;
        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    private void createAtributes()
    {
        navigation = new ForeignKey();
    }

    //---------------------------------------------------------------------
    @Override
    public String getValor()
    {
        return null != navigation.getFk_identificador_valor()
                ? navigation.getFk_identificador_valor().getValor()
                : super.getValor();
    }

    //---------------------------------------------------------------------
    @Override
    public void setValor(String value)
    {
        super.setValor(value);
        navigation.releaseIdentificadorValor();
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdVariante()
    {
        return null != navigation.getFk_identificador_variante()
                ? navigation.getFk_identificador_variante().getId()
                : super.getIdVariante();
    }
    
        //---------------------------------------------------------------------
    @Override
    public int getIdVariante_Viejo()
    {
        return null != navigation.getFk_identificador_variante()
                ? navigation.getFk_identificador_variante().getId_Viejo()
                : super.getIdVariante_Viejo();
    }

    //---------------------------------------------------------------------
    @Override
    public void setIdVariante(int value)
    {
        super.setIdVariante(value);
        navigation.releaseBienVariante();
    }

    //---------------------------------------------------------------------
    public BienVariante getHasOneVariante()
    {
        return null != navigation.getFk_identificador_variante()
                ? navigation.getFk_identificador_variante()
                : navigation.joinBienVariante(super.getIdVariante());
    }

    //---------------------------------------------------------------------
    public void setHasOneVariante(BienVariante value)
    {
        navigation.setFk_identificador_variante(value);
    }

    //---------------------------------------------------------------------
    public int getIdTipoIdentificador()
    {
        return null != navigation.getFk_identificador_valor()
                ? navigation.getFk_identificador_valor().getId()
                : idTipoIdentificador;
    }

    //---------------------------------------------------------------------
    public void setIdTipoIdentificador(int value)
    {
        idTipoIdentificador = value;
        navigation.releaseIdentificadorValor();
    }

    //---------------------------------------------------------------------
    public Valor getHasOneIdentificador_valor()
    {
        return null != navigation.getFk_identificador_valor()
                ? navigation.getFk_identificador_valor()
                : navigation.joinIdentificadorValor(idTipoIdentificador);
    }

    //---------------------------------------------------------------------
    public void setHasOneIdentificador_valor(Valor value)
    {
        navigation.setFk_identificador_valor(value);
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAttributes();
        navigation.releaseBienVariante();
        navigation.releaseIdentificadorValor();
    }

    //---------------------------------------------------------------------
    public void copy(VarianteIdentificador that)
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
    public class ForeignKey
    {

        private BienVariante fk_identificador_variante;
        private Valor fk_identificador_valor;

        //---------------------------------------------------------------------
        public BienVariante joinBienVariante(int id_variante)
        {
            fk_identificador_variante = new BienVariante();
            fk_identificador_variante.setId(id_variante);
            return fk_identificador_variante;
        }

        //---------------------------------------------------------------------
        public Valor joinIdentificadorValor(int idTipoIdentificador)
        {
            fk_identificador_valor = new Valor();
            fk_identificador_valor.setId(idTipoIdentificador);
            return fk_identificador_valor;
        }

        //---------------------------------------------------------------------
        public void releaseBienVariante()
        {
            fk_identificador_variante = null;
        }

        //---------------------------------------------------------------------
        public void releaseIdentificadorValor()
        {
            fk_identificador_valor = null;
        }

        //---------------------------------------------------------------------
        public BienVariante getFk_identificador_variante()
        {
            return fk_identificador_variante;
        }

        //---------------------------------------------------------------------
        public void setFk_identificador_variante(BienVariante value)
        {
            this.fk_identificador_variante = value;
            this.fk_identificador_variante.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Valor getFk_identificador_valor()
        {
            return fk_identificador_valor;
        }

        //---------------------------------------------------------------------
        public void setFk_identificador_valor(Valor value)
        {
            this.fk_identificador_valor = value;
            this.fk_identificador_valor.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        private void acceptChanges()
        {
            if (null != fk_identificador_variante && fk_identificador_variante.isDependentOn())
                fk_identificador_variante.acceptChanges();

            if (null != fk_identificador_valor && fk_identificador_valor.isDependentOn())
                fk_identificador_valor.acceptChanges();

        }
    }
}
