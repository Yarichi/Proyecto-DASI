/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.Rosace.tareasComunes;
import icaro.aplicaciones.Rosace.informacion.InfoAgteRescateVictima;
import icaro.aplicaciones.Rosace.informacion.RobotStatus1;
import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.informacion.VictimsToRescue;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.InfoCompMovimiento;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.imp.MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.ItfUsoMovimientoCtrl;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Informe;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

/**
 *
 * @author Francisco J Garijo
 */
public class ProcesarInformeLlegadaDestino extends TareaSincrona{
 private int velocidadCruceroPordefecto = 1;// metros por segundo 
 private int tiempoMedioRescate = 15; // minutos
 private int gastoEnergíaMinuto = 5; //unidades de energia
 private int costeEstimadoRescate = 0;
 
  private ItfUsoMovimientoCtrl itfcompMov;
  private Victim victimaRescatar;
  @Override
  public void ejecutar(Object... params) {
		try {
//Suponemos que cuando llega al destino se salva la victima
 // habría que actualizar las victimas, los objetivos, el estado del movimiento  y cambiar el foco                   
             MisObjetivos misObjs = (MisObjetivos)params[0];
             VictimsToRescue victims = (VictimsToRescue) params[1];
             Focus focoActual = (Focus) params[2];
             RobotStatus1 estatusRobot = (RobotStatus1) params[3];
             InfoCompMovimiento infoCompMov = (InfoCompMovimiento) params[4];
             Informe informeRecibido = (Informe) params[5];
//             trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente, "Se Ejecuta la Tarea :"+ this.identTarea , InfoTraza.NivelTraza.info));
             trazas.aceptaNuevaTrazaEjecReglas(this.identAgente, "Se Procesa el informe   recibido por el agente :"+ informeRecibido.referenciaContexto +" Cuyo contenido:"+informeRecibido.contenidoInforme + "\n");
            // se actualiza el coste de la  vicitima salvada
             itfcompMov = (ItfUsoMovimientoCtrl)infoCompMov.getitfAccesoComponente();
             String victimaRescatadaId = informeRecibido.getReferenciaContexto();
             victims.addEstimatedCostVictim2Rescue(victimaRescatadaId, 0);
             this.getEnvioHechos().eliminarHecho(informeRecibido);
             this.informarControladorRescateVictima(victimaRescatadaId); // informamos al agente controlador
             // Se actualizan los objetivos, se da por conseguido el objetivo salvar a la victima
             // se supone que este objetivo era el mas prioritario, si no lo era hay un problema
              Objetivo objetivoConseguido = misObjs.getobjetivoMasPrioritario();
              Thread accesoCompMovimiento = new Thread(){
				public void run(){
//                                    itfcompMov.initContadorGastoEnergia();
					itfcompMov.moverAdestino(victimaRescatar.getName(), victimaRescatar.getCoordinateVictim(), velocidadCruceroPordefecto);
				}
			};
              if (victimaRescatadaId.equals(objetivoConseguido.getobjectReferenceId())){
                  objetivoConseguido.setSolved();
                  objetivoConseguido.setPriority(-1);
                  misObjs.cambiarPrioridad(objetivoConseguido);
                  // Se actualiza el componente movimiento
             // Verificamos el foco si tiene un objetivo solved cambiamos el foco al objetivo actual
             // si tiene uno solving lo dejamos
                  Objetivo nuevoObjetivo = misObjs.getobjetivoMasPrioritario();
                  if( nuevoObjetivo.getState()!= Objetivo.SOLVED){
             // tiene  objetivos pendientes , se da la orden de que vaya a salvar a la victima
                      Victim victimaRescatada = victims.getVictimToRescue(victimaRescatadaId);
                      String idVictimaRescatar=nuevoObjetivo.getobjectReferenceId();
                      victimaRescatar = victims.getVictimToRescue(idVictimaRescatar);
                     estatusRobot.setidentDestino(idVictimaRescatar);
//                     itfcompMov.moverAdestino(nuevoObjetivo.getobjectReferenceId(), victimaRescatada.getCoordinateVictim(), velocidadCruceroPordefecto);
                     nuevoObjetivo.setSolving();
                     this.getEnvioHechos().actualizarHechoWithoutFireRules(nuevoObjetivo);
                     
                     accesoCompMovimiento.start();
                  }
                  String estadoComponente=EstadoMovimientoRobot.RobotEnMovimiento.name();
                  estatusRobot.setestadoMovimiento(estadoComponente);
//                  if(focoActual.getFoco().getState()==Objetivo.SOLVED) focoActual.setFoco(objetivoConseguido);
                  focoActual.setFoco(nuevoObjetivo);
//                   focoActual.refocusUltimoObjetivoSolving();
              // Se envian los cambios al motor   
                  
                  this.getEnvioHechos().actualizarHechoWithoutFireRules(victims);
                  this.getEnvioHechos().actualizarHechoWithoutFireRules(misObjs);
                  this.getEnvioHechos().actualizarHechoWithoutFireRules(estatusRobot);
                  this.getEnvioHechos().actualizarHechoWithoutFireRules(objetivoConseguido);
                  this.getEnvioHechos().actualizarHecho(focoActual);
                  trazas.aceptaNuevaTrazaEjecReglas(this.identAgente, "Se Ejecuta la Tarea :"+ this.identTarea +
                           "El identificador de la victima  :"+ victimaRescatadaId + " y el del ultimo objetivo : "+objetivoConseguido.getobjectReferenceId()+" coinciden " +
                           "EstadoComponente : "+estadoComponente+ "Objetivo mas prioritario en curso "+misObjs.getobjetivoMasPrioritario().toString()+ "\n"+ "  El foco esta en el ojetivo :  "+focoActual + "\n");
              }else
                   trazas.aceptaNuevaTrazaEjecReglas(this.identAgente, "Se Ejecuta la Tarea :"+ this.identTarea +
                           "El identificador de la victima  :"+ victimaRescatadaId + " y el del ultimo objetivo : "+objetivoConseguido.getobjectReferenceId()+" NO coinciden " + "\n");
        } catch (Exception e) {
			   e.printStackTrace();
        }
                
}
private void informarControladorRescateVictima(String idVictimaAsignada){
    //ACTUALIZAR ESTADISTICAS
            //Inicializar y recuperar la referencia al recurso de estadisticas 
    // Supongo como prueba que la evaluación es una constante, pero deberia obtenerse del RobotStatus
            long tiempoActual = System.currentTimeMillis();
             int gastoEnergia = itfcompMov.getContadorGastoEnergia(); // En terminos de energia consumida  para el rescate
//            int gastoEnergia = 3000;
//            String refVictima = objetivoAsignado.getobjectReferenceId();
            //      	 itfUsoRecursoEstadistica.escribeEstadisticaFicheroTextoPlanoTRealAsignacionVictimasRobots(tiempoActual, refVictima, nombreAgenteEmisor, coste);
            ////////////////////////////////////////////////////////
            //ENVIAR INFORMACION AL AGENTE CONTROLADOR DEL SIMULADOR           
//            Object[] valoresParametrosAccion = new Object[4];
//            valoresParametrosAccion[0] = tiempoActual;
//            valoresParametrosAccion[1] = refVictima;
//            valoresParametrosAccion[2] = nombreAgenteEmisor;
//            valoresParametrosAccion[3] = miEvaluacion;
            InfoAgteRescateVictima infoVictimaRescatada = new InfoAgteRescateVictima (this.identAgente,idVictimaAsignada,tiempoActual,gastoEnergia);
            InfoContEvtMsgAgteReactivo msg = new InfoContEvtMsgAgteReactivo("victimaRescatada",infoVictimaRescatada);
            this.getComunicator().enviarInfoAotroAgente(msg, VocabularioRosace.IdentAgteControladorSimulador);
}

}
