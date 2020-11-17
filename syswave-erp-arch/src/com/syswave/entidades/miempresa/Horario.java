package com.syswave.entidades.miempresa;

import com.syswave.entidades.keys.PrimaryKeyById;
import java.util.Date;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class Horario extends PrimaryKeyById
{

    private String nombre;
    private Date hora_inicio, hora_fin;
    private int idJornada;

    protected ForeignKey navigation;

    //---------------------------------------------------------------------
    public Horario()
    {
        super();
        createAtributes();
        initAtributes();
    }

    //---------------------------------------------------------------------
    public Horario(Horario that)
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
        nombre = EMPTY_STRING;
        hora_inicio = EMPTY_DATE;
        hora_fin = EMPTY_DATE;
        idJornada = EMPTY_INT;
    }

    //---------------------------------------------------------------------
    private void assign(Horario that)
    {
        super.assign(that);
        nombre = that.nombre;
        hora_inicio = that.hora_inicio;
        hora_fin = that.hora_fin;
        idJornada = that.idJornada;

        navigation = that.navigation;
    }

    //---------------------------------------------------------------------
    public void setIdJornada(int value)
    {
        idJornada = value;
        navigation.releaseJornada();
    }

    //---------------------------------------------------------------------
    public int getIdJornada()
    {
        return null != navigation.getFk_horario_id_jornada()
                ? navigation.getFk_horario_id_jornada().getId()
                : idJornada;
    }

    //---------------------------------------------------------------------
    public String getNombre()
    {
        return nombre;
    }

    //---------------------------------------------------------------------
    public void setNombre(String value)
    {
        this.nombre = value;
    }

    //---------------------------------------------------------------------
    public Date getHoraInicio()
    {
        return hora_inicio;
    }

    //---------------------------------------------------------------------
    public void setHoraInicio(Date value)
    {
        this.hora_inicio = value;
    }

    //---------------------------------------------------------------------
    public Date getHoraFin()
    {
        return hora_fin;
    }

    //---------------------------------------------------------------------
    public void setHoraFin(Date value)
    {
        this.hora_fin = value;
    }

    //---------------------------------------------------------------------
    @Override
    public void clear()
    {
        initAtributes();
        navigation.releaseJornada();
    }

    //---------------------------------------------------------------------
    public void copy(Horario that)
    {
        assign(that);
    }

    //---------------------------------------------------------------------
    public void setHasOneJornada(Jornada value)
    {
        navigation.setFk_horario_id_jornada(value);
    }

    public Jornada getHasOneJornada()
    {
        return null != navigation.getFk_horario_id_jornada()
                ? navigation.getFk_horario_id_jornada()
                : navigation.joinJornada(idJornada);
    }

    //---------------------------------------------------------------------
    protected class ForeignKey
    {

        private Jornada fk_horario_id_jornada;

        //---------------------------------------------------------------------
        public Jornada getFk_horario_id_jornada()
        {
            return fk_horario_id_jornada;
        }

        //---------------------------------------------------------------------
        public void setFk_horario_id_jornada(Jornada value)
        {
            this.fk_horario_id_jornada = value;
            this.fk_horario_id_jornada.setDependentOn(false);
        }

        //---------------------------------------------------------------------
        public Jornada joinJornada(int id_jornada)
        {
            fk_horario_id_jornada = new Jornada();
            fk_horario_id_jornada.setId(id_jornada);
            return fk_horario_id_jornada;
        }

        //---------------------------------------------------------------------
        public void releaseJornada()
        {
            fk_horario_id_jornada = null;
        }

        //---------------------------------------------------------------------   
        private void acceptChanges()
        {
            if (null != fk_horario_id_jornada && fk_horario_id_jornada.isDependentOn())
                fk_horario_id_jornada.acceptChanges();
        }
    }
}
