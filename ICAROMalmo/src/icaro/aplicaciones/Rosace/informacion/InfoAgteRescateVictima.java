/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.Rosace.informacion;

/**
 *
 * @author FGarijo
 */
public class InfoAgteRescateVictima {
    private  String idAgteInformante;
    private  String victimId;
    private  long tiempoRescate;
    private  int costeRescate; // en terminos de energia consumida
    private String notas;
    public InfoAgteRescateVictima(String identAgteInformante, String idVictima,long tmpoRescate, int energiaConsumida) {
        idAgteInformante = identAgteInformante;
        victimId=idVictima;
        tiempoRescate = tmpoRescate;
        costeRescate = energiaConsumida;
    }
    public String getVictimId() {
        return victimId;
    }

    public void setVictimId(String vict) {
        this.victimId = vict;
    }

    public String getRobotId() {
        return idAgteInformante;
    }

    public void setRobotId(String robot) {
        this.idAgteInformante = robot;
    }

    public int getCosteRescate() {
        return costeRescate;
    }

    public void setCosteRescate(int evaluacion) {
        this.costeRescate = evaluacion;
    }
    
    public long getTiempoRescate() {
        return tiempoRescate;
    }

    public void setTiempoRescate(long tiempRescate) {
        this.tiempoRescate = tiempRescate;
    }
    public void addNota(String nota){
        this.notas=this.notas+nota;
    }
    public String getNotas(){
        return notas;
    }
//    public long getTiempoPeticion() {
//        return tiempoPeticion;
//    }

 
}
