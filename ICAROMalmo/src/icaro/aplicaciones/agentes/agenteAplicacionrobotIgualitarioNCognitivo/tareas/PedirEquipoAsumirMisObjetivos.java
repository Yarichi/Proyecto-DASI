/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.PeticionAgente;
import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.informacion.VictimsToRescue;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.informacion.InfoParaDecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Francisco J Garijo
 */
public class PedirEquipoAsumirMisObjetivos extends TareaSincrona{

    private InterfazUsoAgente agenteReceptor;
                 //        private ArrayList agentesEquipo, respuestasAgentes,confirmacionesAgentes,nuevasEvaluacionesAgentes,empates;//resto de agentes que forman mi equipo
    private int mi_eval, mi_eval_nueva;
    private String nombreAgenteEmisor;
    private InfoParaDecidirQuienVa infoDecision;
        
  @Override
  public void ejecutar(Object... params) {
              MisObjetivos misObjs = (MisObjetivos) params[0];
              VictimsToRescue victimas = (VictimsToRescue) params[1];
              Focus foco = (Focus)params[2];
              String identDeEstaTarea = this.getIdentTarea();
//              try {
                   trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se Ejecuta la Tarea :"+ identDeEstaTarea , InfoTraza.NivelTraza.debug));          
                   
////                   for(int i = 0; i< infoDecisionAgente.getAgentesEquipo().size(); i++){
////                       Integer evaluacionAgente = (Integer)infoDecisionAgente.getEvaluacionesRecibidas().get(i);
////                       if(evaluacionAgente == 0){si aun no tenemos la evaluacion , queremos que nos la vuelva a mandar
////                          String agenteReceptor = (String)infoDecisionAgente.getAgentesEquipo().get(i);
////                          
////                          trazas.aceptaNuevaTrazaEjecReglas(nombreAgenteEmisor, "!!!!!!!!Pidiendo evaluacion al agente "+ agenteReceptor);
////                          
////                                                          mandaMensajeAAgenteId("PeticionReenvioEvaluaciones",agenteReceptor );
////                                  PeticionAgente peticionEval = new PeticionAgente(this.getIdentAgente());
////                                  peticionEval.setidentObjectRefPeticion(objetivoEjecutantedeTarea.getobjectReferenceId());
////                                  peticionEval.setMensajePeticion(VocabularioRosace.MsgPeticionEnvioEvaluaciones);
////                                  peticionEval.setJustificacion(victima);  para que se sepa qué evaluacion le pedimos
////                                  comunicacion.enviarInfoAotroAgente(peticionEval, agenteReceptor);
////                                  comunicacion.enviarInfoConMomentoCreacionAotroAgente(peticionEval, agenteReceptor);
////                       }
////                   }
////                   }
////                                   trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Pedimos que nos reenvien las evaluaciones al agente: " +agenteReceptor, InfoTraza.NivelTraza.debug));
////              } catch (Exception ex) {
////                 trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Error al enviar peticionRecibirRespuestasRestantes "+ ex, InfoTraza.NivelTraza.error));
////              }
////        
//              NO SE LA NECESIDAD DE LAS DOS SENTENCIAS SIGUIENTES (el informe no se usa y el infoDecisionAgente solo se ha consultado y no se ha modificado)
//              this.generarInformeOK(identDeEstaTarea, objetivoEjecutantedeTarea, nombreAgenteEmisor, "PeticionDeEvaluacionesQueFaltanRealizada");
//                    
            
        }

    }

