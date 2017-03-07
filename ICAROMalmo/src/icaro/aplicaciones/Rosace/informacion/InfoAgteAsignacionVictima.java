/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.Rosace.informacion;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author FGarijo
 */
public class InfoAgteAsignacionVictima implements Serializable{

    private String victimId;
    private int evaluacion;
    private long tiempoAsignacion;
    private long tiempoPeticion;
    private int nrovictimastotalasignadas;
    private int nrovictimasenentorno;
//    private InfoContextoAsignacionVictima infoCtxtAsingacionVictima;
    private String idAgteInformante;

    public InfoAgteAsignacionVictima() {
    }
    public InfoAgteAsignacionVictima(String identAgteInformante, String idVictima,long tmpoAsignacion, int costeEstimado) {
        idAgteInformante = identAgteInformante;
        victimId=idVictima;
        tiempoAsignacion = tmpoAsignacion;
        evaluacion = costeEstimado;
    }
    public String getVictimId() {
        return victimId;
    }

    public void setVictimId(String vict) {
        this.victimId = vict;
    }

    public String getidAgteInformante() {
        return idAgteInformante;
    }

    public void setidAgteInformante(String robot) {
        this.idAgteInformante = robot;
    }

    public int getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(int evaluacion) {
        this.evaluacion = evaluacion;
    }

    public long getTiempoAsignacion() {
        return tiempoAsignacion;
    }

    public void setTiempoAsignacion(long tiempoAsignacion) {
        this.tiempoAsignacion = tiempoAsignacion;
    }
    public long getTiempoPeticion() {
        return tiempoPeticion;
    }

    public void setTiempoPeticion(long tiempoAsignacion) {
        this.tiempoPeticion = tiempoAsignacion;
    }

//    public int getNrovictimastotalasignadas() {
//        return nrovictimastotalasignadas;
//    }
//
//    public void setNrovictimastotalasignadas(int nrovictimastotalasignadas) {
//        this.nrovictimastotalasignadas = nrovictimastotalasignadas;
//    }
//
//    public int getNrovictimasenentorno() {
//        return nrovictimasenentorno;
//    }
//
//    public void setNrovictimasenentorno(int nrovictimasenentorno) {
//        this.nrovictimasenentorno = nrovictimasenentorno;
//    }

    
/*
    public void setInfoCtxAsignacion(InfoContextoAsignacionVictima infoContxVict) {
        infoCtxtAsingacionVictima = infoContxVict;
    }
    public InfoContextoAsignacionVictima getInfoCtxAsignacion() {
       return infoCtxtAsingacionVictima ;
    }
    * 
    */
}
