/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.PropuestaAgente;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.informacion.InfoParaDecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 *
 * @author Francisco J Garijo
 */


//NOTA JM: esta tarea se llama en la regla "Procesar propuestas de otro agente para ir yo" 

public class ProcesarPropuestaDeOtroAgenteParaQueVayaYo extends TareaSincrona {

    private InfoParaDecidirQuienVa infoDecision;
	@Override
	public void ejecutar(Object... params) {
            try {

                  Objetivo objetivoEjecutantedeTarea = (Objetivo)params[0];
                  infoDecision = (InfoParaDecidirQuienVa)params[1];
                  PropuestaAgente propuestaRecibida =  (PropuestaAgente)params[2];
                  trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente, "Se Ejecuta la Tarea :"+ this.identTarea , InfoTraza.NivelTraza.info));
                  
                  if (!(propuestaRecibida.getMensajePropuesta().equals(VocabularioRosace.MsgPropuesta_Para_Q_vayaYo)) ){
                        this.generarInformeConCausaTerminacion(this.identTarea, objetivoEjecutantedeTarea, identAgente, VocabularioRosace.MsgContenidoPropuestaNoValida, CausaTerminacionTarea.ERROR);
                        trazas.aceptaNuevaTraza(new InfoTraza(identAgente, "El mensaje de la propuesta Recibida No es valido :  "+ propuestaRecibida  , InfoTraza.NivelTraza.error));
                  }
                  else { //De acuerdo a la regla citada al principio, la ejecucion entraría por aqui
                         if (!infoDecision.hanLlegadoTodasLasEvaluaciones){// Si no tengo todas las evaluaciones es que hay otros que las han recibido y proponen que vaya yo
                                                             infoDecision.tengoLaMejorEvaluacion = true; // cro lo que me dicen 
                                                             infoDecision.hanLlegadoTodasLasEvaluaciones = true; // no espero mas evaluaciones
                         }
                         if (infoDecision.tengoLaMejorEvaluacion){
//                             infoDecision.addConfirmacionAcuerdoParaIr(propuestaRecibida.getIdentAgente(), mensajePropuesta);

                             infoDecision.addConfirmacionAcuerdoParaIr(propuestaRecibida.getIdentAgente(), propuestaRecibida.getMensajePropuesta());
                             this.getEnvioHechos().eliminarHechoWithoutFireRules(propuestaRecibida);
                             this.getEnvioHechos().actualizarHechoWithoutFireRules(infoDecision);
                            }
                         //no tengo la mejor evaluación
                         else{// Si no tengo todas las evaluaciones es que hay otros que las han recibido y proponen que vaya yo
                             
                             this.generarInformeConCausaTerminacion(identTarea, objetivoEjecutantedeTarea, identAgente, "LaPropuestaNoEsValidaNoTengoLaMejorEvaluacion", CausaTerminacionTarea.OTRO);
                             trazas.aceptaNuevaTraza(new InfoTraza(identAgente, "La propuesta Recibida No es valida porque no tengo la mejor Evaluacion:  "+ propuestaRecibida  , InfoTraza.NivelTraza.error));
                         }
                  }
            }
            catch(Exception e) {
                  e.printStackTrace();
            }
    }
	
}
