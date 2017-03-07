/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.Rosace.informacion;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlElement;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author FGarijo
 */
@Root (strict=false)
public class InfoRescateVictima implements Serializable,Comparable<InfoRescateVictima>{

      //Numero de robots diferentes que van a intervenir en el proceso de simulacion	
     //Intervalo uniforme de envio de la secuencia de victimas
    @Element
    private String victimId;
    @Element
    private String robotRescatadorId;
    @Element
    private long tiempoAsignacion;
    @Element
    private long tiempoPeticion;
    @Element
    private long tiempoRescate;
    @Element
    private double costeEstimadoEnAsignacion;
    @Element
    private double costeRescate;
//    @Element
    private String notas=" ";
    
    public InfoRescateVictima() {
    }
    public InfoRescateVictima(String idVictim) {
        victimId=idVictim;
        robotRescatadorId="";
//        idEntornoCasoSimulacion=escenarioId;
    }

    public String getvictimaId() {
        return victimId;
    }

    public String getRobotRescatadorId() {
        return robotRescatadorId;
    }

    public void setRobotRescatadorId(String robot) {
        this.robotRescatadorId = robot;
    }

    public double getcosteEstimadoEnAsignacion() {
        return costeEstimadoEnAsignacion;
    }
    public void setcosteEstimadoEnAsignacion(double evaluacion) {
        this.costeEstimadoEnAsignacion = evaluacion;
    }

    public double getcosteRescate() {
        return costeRescate;
    }
    public void setcosteRescate(double costRescate) {
        this.costeRescate = costRescate;
    }

    public long getTiempoAsignacion() {
        return tiempoAsignacion;
    }
    public void setTiempoRescate(long tRescate) {
        this.tiempoRescate = tRescate;
    }

    public long getTiempoRescate() {
        return tiempoRescate;
    }

    public void setTiempoAsignacion(long tiempoAsignacion) {
        this.tiempoAsignacion = tiempoAsignacion;
    }

    public void getTiempoAsignacion(long tiempoAsignacion) {
        this.tiempoAsignacion = tiempoAsignacion;
    }
     public void setTiempoPeticion(long tiempoAsignacion) {
        this.tiempoPeticion = tiempoAsignacion;
    }

    public long getTiempoPeticion() {
        return tiempoPeticion;
    }
    public void copiarInfoAsignacion(InfoAgteAsignacionVictima asignInfo){
    this.costeEstimadoEnAsignacion=asignInfo.getEvaluacion();
    this.tiempoPeticion=asignInfo.getTiempoPeticion();
    this.tiempoAsignacion=asignInfo.getTiempoAsignacion();
}
    public void addNota(String nota){
        this.notas= notas+nota +";/";
    }
//@Element (name = "notas")
    public String  getNotas(){
        return notas;
    }

    @Override
    public int compareTo(InfoRescateVictima infoRescate) {
       return (int)(this.tiempoRescate - infoRescate.getTiempoRescate());
    }
    public static Comparator <InfoRescateVictima> comparadorTiempoRescate = new Comparator<InfoRescateVictima>() {
        @Override
        public int  compare (InfoRescateVictima infoResc1,InfoRescateVictima infoResc2){
            return (int)(infoResc1.getTiempoRescate() - infoResc2.getTiempoRescate());
        }
    };
}
