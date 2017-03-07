/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.tareas;
import icaro.aplicaciones.Rosace.informacion.PropuestaAgente;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.informacion.InfoParaDecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.InformeDeTarea;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaComunicacion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.util.ArrayList;

/**
 *
 * @author Francisco J Garijo
 */
public class MandarPropuestaParaIrYoALosqNoHanConfirmado  extends TareaComunicacion {

	/**  */
    private InterfazUsoAgente agenteReceptor;
    private ArrayList agentesEquipo, respuestasAgentes,confirmacionesAgentes,nuevasEvaluacionesAgentes,empates;//resto de agentes que forman mi equipo
        
    private String nombreAgenteEmisor;
//    private ItfUsoRecursoTrazas trazas;
    private InfoParaDecidirQuienVa infoDecision;
    private String identDeEstaTarea ;

    //private TimeOutRespuestas tiempoSinRecibirRespuesta;   //no usado

	@Override
	public void ejecutar(Object... params) {
		
 //          trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
           Objetivo objetivoEjecutantedeTarea = (Objetivo)params[0];
           infoDecision = (InfoParaDecidirQuienVa)params[1];
           nombreAgenteEmisor = this.getIdentAgente();     
           identDeEstaTarea = this.getIdentTarea();
           try {
                    //      PropuestaAgente miPropuesta = new PropuestaAgente (nombreAgenteEmisor,"SoyElMejorSituadoParaRealizarElObjetivo", infoDecision.getValorMiEvaluacion());
                 PropuestaAgente miPropuesta = new PropuestaAgente (nombreAgenteEmisor);
                 miPropuesta.setMensajePropuesta(VocabularioRosace.MsgPropuesta_Oferta_Para_Ir);
                 miPropuesta.setJustificacion(infoDecision.getMi_eval());
                 trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se Ejecuta la Tarea :"+ identDeEstaTarea , InfoTraza.NivelTraza.debug));
                 agentesEquipo = infoDecision.getConfirmacionesRecibidas();
                 for(int i = 0; i< infoDecision.getConfirmacionesRecibidas().size(); i++){  
                     String respuestaAgente = (String)infoDecision.getConfirmacionesRecibidas().get(i);
                     if(respuestaAgente == ""){//si aun no tenemos confirmacion, queremos que nos vuelva a mandar las cosas
                        String agenteReceptor = (String)infoDecision.getAgentesEquipo().get(i);
                        this.informaraOtroAgente(miPropuesta, agenteReceptor);
                     }
                 }
                 trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Enviamos la propuesta: "+ VocabularioRosace.MsgPropuesta_Oferta_Para_Ir , InfoTraza.NivelTraza.debug));           
                    //            this.mandaMensajeATodos(miPropuesta);
                 this.generarTimeoutRespuestas(5000, VocabularioRosace.MsgTimeoutRecibirRespPropuestasIrYo);
                    //           this.getGestorTareas().crearTarea(TimeOutRespuestas2.class).ejecutar(10000,"ExpiroElTimeoutRespuestaPropuestasParaIrYo");
                 infoDecision.setRespuestasEsperadas(infoDecision.getAgentesEquipo().size());
                 this.generarInformeOK(identDeEstaTarea, objetivoEjecutantedeTarea, nombreAgenteEmisor, VocabularioRosace.ResEjTaskMiPropuestaParaIrYoEnviadaAlEquipo);
                 trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Numero de agentes de los que espero respuesta:" + agentesEquipo.size(), InfoTraza.NivelTraza.info));
                    //            tiempoSinRecibirRespuesta.start();		
           } catch(Exception e) {
			    e.printStackTrace();
           }
    }

	
    public void generarTimeoutRespuestas(Integer milis, String textoMsgTimout)   {

         try {
        	    Thread.sleep(milis);
	     } catch (InterruptedException ex) {}

    	  // Genera un informe de Tarea
          //	  if (!this.finalizar)
		 try {
  			   this.generarInforme(new InformeDeTarea (identDeEstaTarea, VocabularioRosace.MsgTimeoutRecibirRespPropuestasIrYo, nombreAgenteEmisor, textoMsgTimout));
		 } catch (Exception e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		 }
    }
    
    
 }
