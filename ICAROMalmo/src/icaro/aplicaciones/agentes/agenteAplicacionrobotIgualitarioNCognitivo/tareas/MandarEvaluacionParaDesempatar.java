/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.tareas;
import icaro.aplicaciones.Rosace.informacion.PropuestaAgente;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.Rosace.utils.AccesoPropiedadesGlobalesRosace;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.informacion.InfoParaDecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco J Garijo
 */
public class MandarEvaluacionParaDesempatar  extends TareaSincrona {
//public class MandarEvaluacionParaDesempatar  extends Tarea {
	/**  */
    private InterfazUsoAgente agenteReceptor;
    private ArrayList<String> identsAgentesEmpatados;//resto de agentes que forman mi equipo

    private String nombreAgenteEmisor;
//    private ItfUsoRecursoTrazas trazas;
    private InfoParaDecidirQuienVa infoDecision;
    private String identDeEstaTarea ;
    private Objetivo objetivoEjecutantedeTarea ;

    //private TimeOutRespuestas tiempoSinRecibirRespuesta;   //no usado
    
	@Override
	public void ejecutar(Object... params) {
        try {
  //           trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
             objetivoEjecutantedeTarea = (Objetivo)params[0];
             infoDecision = (InfoParaDecidirQuienVa)params[1];     
             nombreAgenteEmisor = this.getAgente().getIdentAgente();
             identDeEstaTarea = this.getIdentTarea();
             trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se Ejecuta la Tarea :"+ identDeEstaTarea , InfoTraza.NivelTraza.info));            
             identsAgentesEmpatados = infoDecision.getAgentesEmpatados();
             infoDecision.propuestasDesempateRecibidas = 0;
             infoDecision.propuestasDesempateEsperadas = identsAgentesEmpatados.size();
             Integer nuevaEvalucion = infoDecision.calcularEvalucionParaDesempate();	   
                     //       PropuestaAgente miPropuesta = new PropuestaAgente (nombreAgenteEmisor,"MiEvaluacionParaDesempatar", nuevaEvalucion);
                  PropuestaAgente miPropuesta = new PropuestaAgente (nombreAgenteEmisor);
                  miPropuesta.setMensajePropuesta(VocabularioRosace.MsgPropuesta_Para_Desempatar );
                  miPropuesta.setJustificacion(nuevaEvalucion);
                  miPropuesta.setIdentObjectRefPropuesta(infoDecision.getidElementoDecision());

                  trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Enviamos la propuesta: MiEvaluacionParaDesempatar " + nuevaEvalucion , InfoTraza.NivelTraza.debug));

                  this.mandaMensajeAEmpatados(miPropuesta);
                  infoDecision.setMiPropuestaDeDesempateEnviadaAtodos(Boolean.TRUE);
                  this.getEnvioHechos().actualizarHecho(infoDecision);
            
                     //            this.generarTimeoutRespuestas(5000, "ExpiroElTimeoutPropuestaDesempate");
                  Random r = new Random();                  


//                  long time = (long)AccesoPropiedadesGlobalesRosace.getTimeTimeoutMilisegundosRecibirPropuestaDesempate();
                  long time = (long)Integer.parseInt(this.itfConfig.getValorPropiedadGlobal(VocabularioRosace.timeTimeoutMilisegundosRecibirPropuestaDesempate));
                  long timeoutRespuestas = time + r.nextInt(1000);   //OBSERVO QUE timeoutRespuestas no se usa
                  this.generarInformeTemporizado(time, 
                		                         VocabularioRosace.IdentTareaTimeOutRecibirPropuestasDesempate, objetivoEjecutantedeTarea, 
                		                         nombreAgenteEmisor, infoDecision.getidElementoDecision());
                                                      
//                  long timeoutRespuestas = configConstantesSimulacion.TimeTimeoutRecibirPropuestaDesempate + r.nextInt(1000);
//                  this.generarInformeTemporizado(configConstantesSimulacion.TimeTimeoutRecibirPropuestaDesempate, 
//                		                         VocabularioRosace.IdentTareaTimeOutRecibirPropuestasDesempate, objetivoEjecutantedeTarea, 
//                		                         nombreAgenteEmisor, infoDecision.getidElementoDecision());
                  
                     //     infoDecision.setRespuestasEsperadas(IdentsAgentesEmpatados.size());
            //      this.generarInformeOK(identDeEstaTarea, objetivoEjecutantedeTarea, nombreAgenteEmisor, VocabularioRosace.ResEjTaskMiPropuestaParaDesempatarEnviada);
                  trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Numero de agentes con los que estoy empatado:" + identsAgentesEmpatados.size(), InfoTraza.NivelTraza.info));
                     //            tiempoSinRecibirRespuesta.start
		     
            }
              catch(Exception e) {
			  e.printStackTrace();
            }
    }

	
    private void mandaMensajeAEmpatados( Object contenidoMsg){
        try {
                       //mandamos el mensaje a todos los agentes del sistema, menos a los gestores
                       //          String aux;
                       //          ArrayList paquete = new ArrayList();
              trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
                
              Integer numAgentesEquipo = identsAgentesEmpatados.size();
                       //          String aux =""+ evaluacion;
                       //          paquete.add(aux);
                       //          paquete.add(nombreAgenteEmisor);
                       //          EvaluacionAgente evalAgente = new EvaluacionAgente(nombreAgenteEmisor,evaluacion);
              ComunicacionAgentes comunicacion = new ComunicacionAgentes(nombreAgenteEmisor);
            
              for(int i = 0; i< identsAgentesEmpatados.size(); i++){
                    String agenteReceptor = (String)identsAgentesEmpatados.get(i);
                    comunicacion.enviarInfoAotroAgente(contenidoMsg, agenteReceptor);
                                 //                    this.mandaMensajeAAgenteId(contenidoMsg, agenteReceptor );
              }
            } catch (Exception ex) {
                Logger.getLogger(MandarEvalATodos.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    
    //EL SIGUIENTE METODO NO SE UTILIZA
    public void mandaMensajeAAgenteId(Object contenido,String identAgenteReceptor){

            // Este método crea un evento con la información de entrada y se le envía al agente REACTIVO que se indique por medio de
            // la  interfaz de uso
            MensajeSimple mensajeaEnviar = null;
         InterfazUsoAgente itfUsoAgenteReceptor = null;

//           Se verifica que la interfaz del aegente no es vacia

        try {


                itfUsoAgenteReceptor = (InterfazUsoAgente) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz
                    (NombresPredefinidos.ITF_USO+identAgenteReceptor);
               }
               catch (Exception e) {
                   Logger.getLogger(MandarEvalATodos.class.getName()).log(Level.SEVERE, null, e);
//                   logger.error("Ha habido un problema enviar un  evento al agente "+IdentAgenteReceptor);
    			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor,
				"Ha habido un problema al enviar el mensaje con informacion "+ contenido.toString()+"al   agente "+identAgenteReceptor,
					  InfoTraza.NivelTraza.error));
               }


         //   En primer lugar se crea el mensaje con  la informacion de entrada
            if (mensajeaEnviar == null){
                mensajeaEnviar = new MensajeSimple(contenido, nombreAgenteEmisor, identAgenteReceptor);
             }
        //    else{eventoaEnviar = new EventoRecAgte(input,infoComplementaria, nombreAgenteEmisor, IdentAgenteReceptor);}
             // Obtener la interfaz de uso del agente reactivo con el que se quiere comunicar
             try {
			itfUsoAgenteReceptor.aceptaMensaje(mensajeaEnviar);
                        trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se manda 1 mensaje de PropuestaParaIrYo " + contenido.toString() + " al agente  "+ identAgenteReceptor, InfoTraza.NivelTraza.debug));
            }catch (Exception e) {
	//	logger.error("Ha habido un problema enviar un  evento al agente "+IdentAgenteReceptor, e);
		trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor,
			  "Ha habido un problema al enviar el mensaje con informacion "+ contenido.toString()+"al   agente "+identAgenteReceptor,
                        	  InfoTraza.NivelTraza.error));
            }
   }

 
}
