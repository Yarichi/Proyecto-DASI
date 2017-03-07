/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.PeticionAgente;
import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.informacion.InfoParaDecidirAQuienAsignarObjetivo;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
/**
 *
 * @author Francisco J Garijo
 */
public class PedirEvaluacionesQueFaltan extends TareaSincrona{

	private InterfazUsoAgente agenteReceptor;
                  //        private ArrayList agentesEquipo, respuestasAgentes,confirmacionesAgentes,nuevasEvaluacionesAgentes,empates;//resto de agentes que forman mi equipo
        private int mi_eval, mi_eval_nueva;
        private String nombreAgenteEmisor;
        private ItfUsoRecursoTrazas trazas;
        private InfoParaDecidirAQuienAsignarObjetivo infoDecisionAgente;
        //private TimeOutRespuestas tiempoSinRecibirRespuesta;  //no usado      
  @Override
  public void ejecutar(Object... params) {
	try {
              trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
              Objetivo objetivoEjecutantedeTarea = (Objetivo)params[0];
              String identDeEstaTarea = this.getIdentTarea();
              nombreAgenteEmisor = this.getAgente().getIdentAgente();
              infoDecisionAgente = (InfoParaDecidirAQuienAsignarObjetivo) params[1];
              Victim victima = (Victim) params[2];
              trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se Ejecuta la Tarea :"+ identDeEstaTarea , InfoTraza.NivelTraza.debug));
                  if (!infoDecisionAgente.hanLlegadoTodasLasEvaluaciones){ // si no han llegado todas las evaluaciones  
                 //  ComunicacionAgentes comunicacion = new ComunicacionAgentes(nombreAgenteEmisor );            
                     for(int i = 0; i< infoDecisionAgente.getAgentesEquipo().size(); i++){
                       Integer evaluacionAgente = (Integer)infoDecisionAgente.getEvaluacionesRecibidas().get(i);
                       if(evaluacionAgente == 0){//si aun no tenemos la evaluacion , queremos que nos la vuelva a mandar
                          String agenteReceptor = (String)infoDecisionAgente.getAgentesEquipo().get(i);      
                          trazas.aceptaNuevaTraza(
                        		  new InfoTraza(nombreAgenteEmisor, "!!!!!!!!Pidiendo evaluacion al agente: "+ agenteReceptor,
                                                        InfoTraza.NivelTraza.debug));
                                  PeticionAgente peticionEval = new PeticionAgente(this.getIdentAgente());
                                  peticionEval.setidentObjectRefPeticion(objetivoEjecutantedeTarea.getobjectReferenceId());
                                  peticionEval.setMensajePeticion(VocabularioRosace.MsgPeticionEnvioEvaluaciones);
                                  peticionEval.setJustificacion(victima); // para que se sepa qué evaluacion le pedimos
                                  this.getComunicator().enviarInfoAotroAgente(peticionEval, agenteReceptor);       
                       }
                     }
                   }
                       //            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Pedimos que nos reenvien las evaluaciones al agente: " +agenteReceptor, InfoTraza.NivelTraza.debug));

           //    long time = (long)AccesoPropiedadesGlobalesRosace.getTimeTimeoutMilisegundosRecibirEvaluaciones();

               this.generarInformeTemporizadoFromConfigProperty(VocabularioRosace.IdentTareaTimeOutRecibirEvaluaciones2,objetivoEjecutantedeTarea, 
                       nombreAgenteEmisor,infoDecisionAgente.getidElementoDecision());
               
//               this.generarInformeTemporizado(configConstantesSimulacion.TimeTimeoutRecibirEvaluaciones ,            		  
//                     VocabularioRosace.IdentTareaTimeOutRecibirEvaluaciones2,objetivoEjecutantedeTarea, 
//                     nombreAgenteEmisor,infoDecisionAgente.getidElementoDecision());
              this.generarInformeOK(identDeEstaTarea, objetivoEjecutantedeTarea, nombreAgenteEmisor, "PeticionDeEvaluacionesQueFaltanRealizada");
                    
            //  this.getEnvioHechos().insertarHecho(infoDecisionAgente);
        }
        catch (Exception e) {
			e.printStackTrace();
                         trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Error al enviar peticionRecibirRespuestasRestantes "+ e, InfoTraza.NivelTraza.error));
        }
  }
}
