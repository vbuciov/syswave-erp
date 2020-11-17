package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.CompositeKeyByIdCondicionPagoDocumento;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class Documento_tiene_Condicion extends CompositeKeyByIdCondicionPagoDocumento
{

    protected ForeignKey navigation;
    
        //---------------------------------------------------------------------
    public Documento_tiene_Condicion()
    {
        super();
        createAtributes();
    }

    //---------------------------------------------------------------------
    public Documento_tiene_Condicion(Documento_tiene_Condicion that)
    {
        super();
        createAtributes();
        asign(that);
    }

    //---------------------------------------------------------------------
    private void createAtributes()
    {
        navigation = new ForeignKey();
    }

    //---------------------------------------------------------------------
    private void asign(Documento_tiene_Condicion that)
    {
        super.assign(that);

        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    @Override
    public void setIdDocumento(int value)
    {
        super.setIdDocumento(value);
        navigation.releaseDocumento();
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdDocumento()
    {
        return null != navigation.getFk_condicion_documento_id()
                ? navigation.getFk_condicion_documento_id().getId()
                : super.getIdDocumento();
    }
    
    //---------------------------------------------------------------------
    @Override
    public int getIdDocumento_Viejo()
    {
        return null != navigation.getFk_condicion_documento_id()
                ? navigation.getFk_condicion_documento_id().getId_Viejo()
                : super.getIdDocumento_Viejo();
    }

    //---------------------------------------------------------------------
    @Override
    public void setIdCondicionPago(int value)
    {
        super.setIdCondicionPago(value);
        navigation.releaseCondicionPago();
    }

    //---------------------------------------------------------------------
    @Override
    public int getIdCondicionPago()
    {
        return null != navigation.getFk_documento_condicion_id()
                ? navigation.getFk_documento_condicion_id().getId()
                : super.getIdCondicionPago();
    }
    
    //---------------------------------------------------------------------
    @Override
    public int getIdCondicionPago_Viejo()
    {
        return null != navigation.getFk_documento_condicion_id()
                ? navigation.getFk_documento_condicion_id().getId_Viejo()
                : super.getIdCondicionPago_Viejo();
    }

    //----------------------------------------------------------------------
    public CondicionPago getHasOneCondicion()
    {
        return null != navigation.getFk_documento_condicion_id()
                ? navigation.getFk_documento_condicion_id()
                : navigation.joinCondicionPago(super.getIdCondicionPago());
    }

    //----------------------------------------------------------------------
    public void setHasOneCondicion(CondicionPago value)
    {
        navigation.setFk_documento_condicion_id(value);
    }

    //----------------------------------------------------------------------
    public Documento getHasOneDocumento()
    {
        return null != navigation.getFk_condicion_documento_id()
                ? navigation.getFk_condicion_documento_id()
                : navigation.joinDocumento(super.getIdDocumento());
    }

    //----------------------------------------------------------------------
    public void setHasOneDocumento(Documento value)
    {
        navigation.setFk_condicion_documento_id(value);
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        navigation.releaseCondicionPago();
        navigation.releaseDocumento();
    }

    //---------------------------------------------------------------------
    public void copy(Documento_tiene_Condicion that)
    {
        asign(that);
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

        private CondicionPago fk_documento_condicion_id;
        private Documento fk_condicion_documento_id;

        //---------------------------------------------------------------------
        public CondicionPago getFk_documento_condicion_id()
        {
            return fk_documento_condicion_id;
        }

        //---------------------------------------------------------------------
        public void setFk_documento_condicion_id(CondicionPago value)
        {
            this.fk_documento_condicion_id = value;
            this.fk_documento_condicion_id.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Documento getFk_condicion_documento_id()
        {
            return fk_condicion_documento_id;
        }

        //---------------------------------------------------------------------
        public void setFk_condicion_documento_id(Documento value)
        {
            this.fk_condicion_documento_id = value;
            this.fk_condicion_documento_id.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public CondicionPago joinCondicionPago(int id_condicion_pago)
        {
            fk_documento_condicion_id = new CondicionPago();
            fk_documento_condicion_id.setId(id_condicion_pago);
            return fk_documento_condicion_id;
        }

        //---------------------------------------------------------------------
        public void releaseCondicionPago()
        {
            fk_documento_condicion_id = null;
        }

        //---------------------------------------------------------------------
        public Documento joinDocumento(int id_documento)
        {
            fk_condicion_documento_id = new Documento();
            fk_condicion_documento_id.setId(id_documento);
            return fk_condicion_documento_id;
        }

        //---------------------------------------------------------------------
        public void releaseDocumento()
        {
            fk_condicion_documento_id = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_condicion_documento_id && fk_condicion_documento_id.isDependentOn())
                fk_condicion_documento_id.acceptChanges();

            if (null != fk_documento_condicion_id && fk_documento_condicion_id.isDependentOn())
                fk_documento_condicion_id.acceptChanges();
        }
    }
}
