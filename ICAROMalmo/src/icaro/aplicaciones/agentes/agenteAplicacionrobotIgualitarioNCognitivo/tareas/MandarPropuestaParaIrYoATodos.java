/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.tareas;
import icaro.aplicaciones.Rosace.informacion.PropuestaAgente;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.informacion.InfoParaDecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.*;

import java.util.ArrayList;

/**
 *
 * @author Francisco J Garijo
 */
//public class MandarPropuestaParaIrYoATodos  extends TareaComunicacion {
public class MandarPropuestaParaIrYoATodos  extends TareaSincrona {
	/**  */
        private InterfazUsoAgente agenteReceptor;
        private ArrayList agentesEquipo, respuestasAgentes,confirmacionesAgentes,nuevasEvaluacionesAgentes,empates;//resto de agentes que forman mi equipo
        
        private String nombreAgenteEmisor;
 //       private ItfUsoRecursoTrazas trazas;
        private InfoParaDecidirQuienVa infoDecision;
        private String identDeEstaTarea ;

        // private TimeOutRespuestas tiempoSinRecibirRespuesta;  //no usado

	@Override
	public void ejecutar(Object... params) {
		
 //             trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
              Objetivo objetivoEjecutantedeTarea = (Objetivo)params[0];
              infoDecision = (InfoParaDecidirQuienVa)params[1];

              nombreAgenteEmisor = this.getAgente().getIdentAgente();            
              identDeEstaTarea = this.getIdentTarea();

           try {
                  //      PropuestaAgente miPropuesta = new PropuestaAgente (nombreAgenteEmisor,"SoyElMejorSituadoParaRealizarElObjetivo", infoDecision.getValorMiEvaluacion());
                 PropuestaAgente miPropuesta = new PropuestaAgente (nombreAgenteEmisor);
                 miPropuesta.setMensajePropuesta(VocabularioRosace.MsgPropuesta_Oferta_Para_Ir);
                 miPropuesta.setJustificacion(infoDecision.getMi_eval());
                 miPropuesta.setIdentObjectRefPropuesta(infoDecision.getidElementoDecision());
                 if (!infoDecision.miPropuestaParaAsumirElObjetivoEnviadaAtodos){ // si ya lo he enviado no hago nada asi evito problemas de invocacion en  la regla
                 trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se Ejecuta la Tarea :"+ identDeEstaTarea , InfoTraza.NivelTraza.debug));
                 agentesEquipo = infoDecision.getAgentesEquipo();
                 trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Enviamos la propuesta: "+ VocabularioRosace.MsgPropuesta_Oferta_Para_Ir , InfoTraza.NivelTraza.debug));
                          //            ComunicacionAgentes comunicacion = new ComunicacionAgentes(nombreAgenteEmisor);
                          //            comunicacion.informaraGrupoAgentes(miPropuesta, agentesEquipo);
                 this.getComunicator().informaraGrupoAgentes(miPropuesta, agentesEquipo);
                 
                 

                 
//                 this.generarInformeTemporizado(configConstantesSimulacion.TimeTimeoutRecibirRespPropuestasIrYo,
//                		    VocabularioRosace.IdentTareaTimeOutRecibirConfirmacionesRealizacionObjetivo1,     objetivoEjecutantedeTarea, 
//                		    nombreAgenteEmisor,infoDecision.getidElementoDecision()   );
                           
                          //           this.getGestorTareas().crearTarea(TimeOutRespuestas2.class).ejecutar(10000,"ExpiroElTimeoutRespuestaPropuestasParaIrYo");
                 infoDecision.setRespuestasEsperadas(infoDecision.getAgentesEquipo().size()-1);
                 infoDecision.setMiPropuestaParaAsumirElObjetivoEnviadaAtodos(Boolean.TRUE);
             //    this.getEnvioHechos().actualizarHechoWithoutFireRules(infoDecision);
                 this.getEnvioHechos().actualizarHecho(infoDecision);
            //   this.generarInformeOK(identDeEstaTarea, objetivoEjecutantedeTarea, nombreAgenteEmisor, VocabularioRosace.ResEjTaskMiPropuestaParaIrYoEnviadaAlEquipo);
                 trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Numero de agentes de los que espero respuesta:" + agentesEquipo.size(), InfoTraza.NivelTraza.info));
                 this.generarInformeTemporizadoFromConfigProperty(VocabularioRosace.IdentTareaTimeOutRecibirConfirmacionesRealizacionObjetivo1,  objetivoEjecutantedeTarea, 
                      nombreAgenteEmisor,  infoDecision.getidElementoDecision());
                 
                 }        //            tiempoSinRecibirRespuesta.start();		
           }catch(Exception e) {
			    e.printStackTrace();
           }
	}

 }
