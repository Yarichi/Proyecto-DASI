/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.Rosace.tareasComunes;

import icaro.aplicaciones.Rosace.informacion.*;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 *
 * @author Francisco J Garijo
 */
public class ProcesarPropuestaParaAsumirObjetivo extends TareaSincrona {

    private RobotStatus1 miStatus;

//    private String mensajePropuesta ;   ////????????????????????esto no se inicializa?????????????

	@Override
	public void ejecutar(Object... params) {
            try {
                  Objetivo objetivoEjecutantedeTarea = (Objetivo)params[0];
                  miStatus = (RobotStatus1)params[1];
                  MisObjetivos misObjtvs = (MisObjetivos) params[2];
                  VictimsToRescue victimasRecibidas = (VictimsToRescue) params[3];
                  PropuestaAgente propuestaRecibida =  (PropuestaAgente)params[4];
                  Victim victima= (Victim)propuestaRecibida.getJustificacion();
          //        trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se Ejecuta la Tarea :"+ identDeEstaTarea , InfoTraza.NivelTraza.info));
                  
                  if (!(propuestaRecibida.getMensajePropuesta().equals(VocabularioRosace.MsgPropuesta_Para_Aceptar_Objetivo)) ){
                        this.generarInformeConCausaTerminacion(this.identTarea, objetivoEjecutantedeTarea,this.identAgente, VocabularioRosace.MsgContenidoPropuestaNoValida, CausaTerminacionTarea.ERROR);
                        trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente, "El mensaje de la propuesta Recibida No es valido :  "+ propuestaRecibida  , InfoTraza.NivelTraza.error));
                  }
                  else { //De acuerdo a la regla citada al principio, la ejecucion entrara por aqui
                      // evaluo de nuevo el coste de hacerme cargo del objetivo y si puedo hacerlo contesto que acepto
                       Coste coste = new Coste();
 //                      int costeAyudaVictima = coste.calculoCosteAyudarVictimaConRLocation(nombreAgenteEmisor, miStatus, victima,  victimasRecibidas,  misObjtvs, "FuncionEvaluacion3");
                       int costeAyudaVictima = coste.CalculoCosteAyudarVictima(this.identAgente,miStatus.getRobotCoordinate(), miStatus, victima,  victimasRecibidas,  misObjtvs, "FuncionEvaluacion3");
                   if ( costeAyudaVictima> -1){
                       AceptacionPropuesta aceptacionAsignacion = new AceptacionPropuesta ( this.identAgente,VocabularioRosace.MsgAceptacionPropuesta, propuestaRecibida );
                       // Actualizo la tabla de costa estimado de victimas
                       aceptacionAsignacion.setidentObjectRefAcetPropuesta(propuestaRecibida.getIdentObjectRefPropuesta());
                       victima.setEstimatedCost(costeAyudaVictima);
                       victimasRecibidas.addVictimToRescue(victima);
                       this.getComunicator().enviarInfoAotroAgente(aceptacionAsignacion, propuestaRecibida.getIdentAgente());
                       this.getEnvioHechos().eliminarHechoWithoutFireRules(propuestaRecibida);
                       this.getEnvioHechos().actualizarHechoWithoutFireRules(victima);
                       this.getEnvioHechos().actualizarHechoWithoutFireRules(victimasRecibidas);
                       this.getEnvioHechos().insertarHecho(aceptacionAsignacion);
                       trazas.aceptaNuevaTrazaEjecReglas(this.identAgente, "Se acepta la propuesta:  "+ propuestaRecibida );
                   }else { // no se acepta la propuesta por no disponer de recursos - como lo indica la evaluacion
                            RechazarPropuesta rechazoAsignacion = new RechazarPropuesta (this.identAgente,VocabularioRosace.MsgAceptacionPropuesta, propuestaRecibida);
                            rechazoAsignacion.setJustificacion(costeAyudaVictima);
                            this.getEnvioHechos().insertarHecho(rechazoAsignacion);
                             trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente, "Se Rechaza la propuesta:  "+ propuestaRecibida + "Justificacion mi evaluacion : " +costeAyudaVictima , InfoTraza.NivelTraza.debug));
                            }
                  //      this.getEnvioHechos().eliminarHecho(propuestaRecibida);
                       
                         }
                  }
            catch(Exception e) {
                  e.printStackTrace();
            }
    }
	
}
