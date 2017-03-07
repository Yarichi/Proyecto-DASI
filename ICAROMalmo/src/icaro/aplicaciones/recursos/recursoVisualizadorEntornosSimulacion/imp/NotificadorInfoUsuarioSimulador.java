/*
 * Se notifican los eventos al Agte Distribuidor de Tareas
 * and open the template in the editor.
 */
package icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp;

import icaro.aplicaciones.Rosace.informacion.*;
import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Informe;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author FGarijo
 */
public class NotificadorInfoUsuarioSimulador extends ComunicacionAgentes{
  
    protected ItfUsoRepositorioInterfaces itfUsoRepositorioInterfaces;
    protected ClaseGeneradoraRecursoVisualizadorEntornosSimulacion generadoraVisualizador;
  //  protected InterfazUsoAgente itfUsoAgenteaReportar;
    protected String identificadorAgenteaReportar;
    protected String identRecurso;
 //   protected ComunicacionAgentes comunicacion;
    protected String identificadorEquipo ;
    protected InfoEquipo equipo;
    protected ArrayList identsAgtesEquipo;
    protected Integer victimOrder = 0;
    protected boolean stop = false;
    private InfoContEvtMsgAgteReactivo peticionEnvioVictimaSimulada,peticionEnvioSecuenciaVictimas,
            peticionParar,peticionMostrarEscenario, peticionPararAgente ;
     private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass().getSimpleName());
   
    private InterfazUsoAgente itfUsoAgenteControlador;
        
//    private ItfUsoRecursoEstadistica itfUsoRecursoEstadistica;    //Para recoger estadisticas del instante de envio de victimas desde el centro de control

    
    public NotificadorInfoUsuarioSimulador(String  idRecurso, String identAgteControlador) {
            // la clase generadora pasa la informacion sobre el agente controlador a quien se le pasan los eventos
            // Obtenemos las informaciones que necesitamos de la clase generadora del recurso
            super (idRecurso)  ;      
            identRecurso = idRecurso;
            identificadorAgenteaReportar = identAgteControlador;
            // inicializo tipos de peticiones que se pueden enviar al aagente controlador
//            peticionEnvioVictimaSimulada = new InfoContEvtMsgAgteReactivo (VocabularioRosace.peticionSimulacionVictima);
            peticionEnvioSecuenciaVictimas = new InfoContEvtMsgAgteReactivo (VocabularioRosace.peticionSimulacionSecuenciaVictimas);
//            peticionParar = new InfoContEvtMsgAgteReactivo (VocabularioRosace.peticionPararSimulacion);
            peticionMostrarEscenario= new InfoContEvtMsgAgteReactivo (VocabularioRosace.peticionMostrarEscenarioActualSimulado);
    }
    public void setIdentAgenteAReportar(String identAgenteAReportar) {
        identificadorAgenteaReportar =identAgenteAReportar ;
    }
    public void sendPeticionInicioSimulacion (EscenarioSimulacionRobtsVictms escenario, String modalidadSimulacion, Object infoComplementaria){
        
        // se manda la peticion al agente para que decida lo que hay que hacer
             Object[] infoDefinidaPorUsuario = new Object[]{escenario,modalidadSimulacion,infoComplementaria};
            this.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo (VocabularioRosace.peticionInicioSimulacion,infoDefinidaPorUsuario), identificadorAgenteaReportar);
      //      comunicacion.enviarInfoAotroAgente(initialOrder, VocabularioRosace.IdentAgteDistribuidorTareas);
    }
    public void sendPeticionSimulacionVictimToRobotTeam (EscenarioSimulacionRobtsVictms escenarioActual,String idVictim){
        
        // se manda la peticion al agente para que decida lo que hay que hacer
//        Object[] parametrosPeticion = new Object[]{escenarioActual,VocabularioRosace.modoSimulacionPeticionesUsuario,idVictim};
            this.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo (
                    VocabularioRosace.peticionSimulacionVictima,new InfoPeticionSimulacion(escenarioActual,VocabularioRosace.modoSimulacionPeticionesUsuario,idVictim)),
                    identificadorAgenteaReportar);
      //      comunicacion.enviarInfoAotroAgente(initialOrder, VocabularioRosace.IdentAgteDistribuidorTareas);
    }
    public void sendPeticionSimulacionSecuenciaVictimasToRobotTeam (EscenarioSimulacionRobtsVictms escenarioActual, int intervSecuencia){
        
        // se manda la peticion al agente para que decida lo que hay que hacer
//            Object[] parametrosPeticion = new Object[]{escenarioActual,VocabularioRosace.modoSimulacionSecuencia,intervSecuencia};
//            peticionEnvioSecuenciaVictimas.setvaloresParametrosAccion(infoDefinidaPorUsuario);
            this.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo (
                    VocabularioRosace.peticionSimulacionSecuenciaVictimas,new InfoPeticionSimulacion(escenarioActual,VocabularioRosace.modoSimulacionSecuencia,intervSecuencia)),
                    identificadorAgenteaReportar);
    }
     public void sendPeticionPararAgente (String idAgente){
        
        // se manda la peticion al agente para que decida lo que hay que hacer
//            Object[] infoDefinidaPorUsuario = new Object[]{idAgente};
//            peticionPararAgente = new InfoContEvtMsgAgteReactivo (VocabularioRosace.PeticionAgteControlSimul.pararRobot.name());
//            peticionPararAgente.setvaloresParametrosAccion(infoDefinidaPorUsuario);
            this.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo (
                    VocabularioRosace.peticionPararRobot,idAgente),identificadorAgenteaReportar);
      //      comunicacion.enviarInfoAotroAgente(initialOrder, VocabularioRosace.IdentAgteDistribuidorTareas);
    }
    public void sendPeticionPararSimulacion (){
        
        // se manda la peticion al agente para que decida lo que hay que hacer
     
            this.informaraOtroAgenteReactivo(peticionParar, identificadorAgenteaReportar);
      //      comunicacion.enviarInfoAotroAgente(initialOrder, VocabularioRosace.IdentAgteDistribuidorTareas);
    }
    public void sendPeticionMostrarEscenarioSimulacion() {
        try {
            this.informaraOtroAgenteReactivo(peticionMostrarEscenario, identificadorAgenteaReportar);
        } catch (Exception ex) {
            Logger.getLogger(NotificadorInfoUsuarioSimulador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void sendPeticionAgteControlador(VocabularioRosace.PeticionAgteControlSimul identPeticion) {
        try {  
            this.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo (identPeticion.name()) , identificadorAgenteaReportar);
        } catch (Exception ex) {
            Logger.getLogger(NotificadorInfoUsuarioSimulador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendNotificacionLlegadaDestino (String robotId,String identDestino){
        try {
//            Informe informeLlegada = new Informe (robotId,identDestino, VocabularioRosace.MsgeLlegadaDestino);
             Informe informeLlegada = new Informe (robotId,identDestino, VocabularioRosace.MsgeLlegadaDestino);
            this.enviarInfoAotroAgente(informeLlegada, robotId);
             log.debug("Se envia la notificacion llegada a destino " + " Robot :  "+robotId + "  identDestino :  " +identDestino  ); 
        } catch (Exception ex) {
            Logger.getLogger(NotificadorInfoUsuarioSimulador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public void sendInfoEscenarioSeleccionado (EscenarioSimulacionRobtsVictms escenario){
         try {  
            this.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo ("escenarioSeleccionadoUsuario",escenario) , identificadorAgenteaReportar);
        } catch (Exception ex) {
            Logger.getLogger(NotificadorInfoUsuarioSimulador.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void sendInfoEscenarioSeleccionadoValido (EscenarioSimulacionRobtsVictms escenario){
         try {  
            this.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo ("escenarioDefinidoValido",escenario) , identificadorAgenteaReportar);
        } catch (Exception ex) {
            Logger.getLogger(NotificadorInfoUsuarioSimulador.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void sendInfoIdentEscenarioSeleccionado (String escenarioId){
         try {  
            this.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo ("escenarioSeleccionadoUsuario",escenarioId) , identificadorAgenteaReportar);
        } catch (Exception ex) {
            Logger.getLogger(NotificadorInfoUsuarioSimulador.class.getName()).log(Level.SEVERE, null, ex);
        }
     }

    void sendNotifAgteControlador(String notifString) {
//        notif String debe estar en le vocabulario Rosace
    try {  
            this.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo (notifString) , identificadorAgenteaReportar);
        } catch (Exception ex) {
            Logger.getLogger(NotificadorInfoUsuarioSimulador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    void sendPeticionTerminacionSimulacionEnCurso() {
//        try {  
//            this.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo (notifString) , identificadorAgenteaReportar);
//        } catch (Exception ex) {
//            Logger.getLogger(NotificadorInfoUsuarioSimulador.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    }