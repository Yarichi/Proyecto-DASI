/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp;

/**
 *
 * @author FGarijo
 */
public class MemComunControladores {
    private boolean intencionUsuarioCrearRobot;
    private boolean intencionUsuarioCrearVictima;
    private boolean entidadSeleccionadaParaMover;
    private boolean escenarioSimulacionAbierto = false ;
    private boolean escenarioMovimientoAbierto = false ;
    private boolean simulacionEnCurso = false ;
    private boolean visualizandoResultados = false ;
    EscenarioSimulacionRobtsVictms escenarioSimulacion, escenarioMovimiento,escenarioEdicion;
    private int numeroRobots, mumeroVictimas;
    private VisorMovimientoEscenario visorMovimiento;
    private boolean cambioEnEscenarioSimulacion;
    private int numRobotsdefsEnOrganizacion;
    private String modorganizDefinidoEnOrg;
    private boolean escenarioEdicionAbierto;
    public MemComunControladores(){
        
    }
    
    public synchronized void setescenarioSimulacionAbierto(boolean estado){
         escenarioSimulacionAbierto=estado ;
    }
    public synchronized boolean getescenarioSimulacionAbierto(){
        return escenarioSimulacionAbierto;
    }
     public synchronized void setsimulacionEnCurso(boolean estado){
         simulacionEnCurso=estado ;
    }
    public synchronized boolean getsimulacionEnCurso(){
        return simulacionEnCurso;
    }
    public synchronized void setescenarioMovimiento(EscenarioSimulacionRobtsVictms escenarioMov){
        escenarioMovimiento = escenarioMov;
    }
    public synchronized  EscenarioSimulacionRobtsVictms getescenarioMovimiento(){
        return escenarioMovimiento ;
    }
    public synchronized void setevisorMovimiento(VisorMovimientoEscenario visorMov){
        visorMovimiento = visorMov;
    }
    public synchronized  VisorMovimientoEscenario getvisorMovimiento(){
        return visorMovimiento ;
    }
    public synchronized void setescenarioSimulacion(EscenarioSimulacionRobtsVictms escenarioSim){
        escenarioSimulacion = escenarioSim;
    }
    public synchronized EscenarioSimulacionRobtsVictms getescenarioSimulacion(){
        return escenarioSimulacion ;
    }
    public synchronized void setescenarioEdicion(EscenarioSimulacionRobtsVictms escenarioEdi){
        escenarioEdicion = escenarioEdi;
    }
    public synchronized EscenarioSimulacionRobtsVictms getescenarioEdicion(){
        return escenarioEdicion ;
    }

    public synchronized boolean getcambioEnEscenarioSimulacion() {
        // se pone a true siempre que cambie el escenario de simulacion, bien porque se abra uno nuevo diferente al qeu habia
//        o porque se edite se guarde  y se vuelva a abrir
       return cambioEnEscenarioSimulacion;
    }
 public synchronized void setcambioEnEscenarioSimulacion(boolean estado){
         cambioEnEscenarioSimulacion=estado ;
    }
 public synchronized void setnumRobotsdefsEnOrganizacion(int numRobots){
         numRobotsdefsEnOrganizacion=numRobots ;
    }
 public synchronized int getnumRobotsdefsEnOrganizacion(){
         return numRobotsdefsEnOrganizacion;
    }
   public synchronized void setmodorganizDefinidoEnOrg(String modorg){
         modorganizDefinidoEnOrg=modorg ;
    }
 public synchronized String getmodorganizDefinidoEnOrg(){
         return modorganizDefinidoEnOrg;
    }

  public  void setescenarioEdicionAbierto(boolean b) {
       this.escenarioEdicionAbierto =b;
    }
  public  boolean getescenarioEdicionAbierto() {
       return escenarioEdicionAbierto;
    }
}
