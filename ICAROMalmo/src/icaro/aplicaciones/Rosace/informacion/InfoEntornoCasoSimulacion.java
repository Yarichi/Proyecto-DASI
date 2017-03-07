/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.Rosace.informacion;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author FGarijo
 */
public class InfoEntornoCasoSimulacion {

   private long tiempoInicioSimulacion = 0;      //Variable para obtener el tiempo al iniciar la simulacion
    private int nroVictimasenEntorno = 0;            //Numero de victimas actuales que se han introducido en el entorno    
 //   private int nrovictimastotalasignadas = 0;          //Numero de victimas asignadas a robots
 //   private int numeroVictimasDiferentesSimulacion; //Numero de victimas diferentes que van a intervenir en el proceso de simulacion
    private int numeroRobotsSimulacion = 0;         //Numero de robots diferentes que van a intervenir en el proceso de simulacion	
    private int intervaloSecuencia;                 //Intervalo uniforme de envio de la secuencia de victimas
    private String equipoId;                       // victima a la que se refiere el contexto 
    
    public InfoEntornoCasoSimulacion(String idEquipo) {
        equipoId = idEquipo;
    }

    public InfoEntornoCasoSimulacion(String idEquipo, int numRobotsSimulacion, int numVictimasEntorno, int intervSecuencia) {
        equipoId = idEquipo;
        nroVictimasenEntorno = numVictimasEntorno;
        numeroRobotsSimulacion = numRobotsSimulacion;
        intervaloSecuencia = intervSecuencia;
    }
    @XmlElement (name = "equipoId")
    public String getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(String victima) {
        this.equipoId = victima;
    }
    @XmlElement (name = "nroVictimasenEntorno")
    public int getNrovictimasenentorno() {
        return nroVictimasenEntorno;
    }   
    public void setNrovictimasenentorno(int nrovictimasenentorno) {
        this.nroVictimasenEntorno = nrovictimasenentorno;
    } 
    @XmlElement (name = "numeroRobotsSimulacion")
    public int getnumeroRobotsSimulacion() {
        return numeroRobotsSimulacion;
    }   
    public void setnumeroRobotsSimulacion(int numRobotsSimulacion) {
        this.numeroRobotsSimulacion = numRobotsSimulacion;
    } 
    @XmlElement (name = "intervaloSecuencia")
    public int getintervSecuencia() {
        return intervaloSecuencia;
    }   
    public void setintervaloSecuencia(int intervSec) {
        this.intervaloSecuencia = intervSec;
    } 
     @XmlElement (name = "tiempoInicioSimulacion")
    public long getTiempoInicioSimulacion() {
        return tiempoInicioSimulacion;
    }   
    public void setTiempoInicioSimulacion(long tiempoInicio) {
        this.tiempoInicioSimulacion = tiempoInicio;
    } 
}
