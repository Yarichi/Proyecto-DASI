package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.tareas;
import icaro.aplicaciones.Rosace.informacion.PropuestaAgente;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.informacion.InfoParaDecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.util.ArrayList;

/**
 *
 * @author Francisco J Garijo
 */
public class ProcesarPropuestaDesempate  extends TareaSincrona {

	/**  */
    private InterfazUsoAgente agenteReceptor;
    private ArrayList<String> identsAgentesEmpatados;


    private String nombreAgenteEmisor;
    private InfoParaDecidirQuienVa infoDecision;
    private String identDeEstaTarea, IdentAgenteEmpatado ;
    private PropuestaAgente miPropuesta;
    private String mensajePropuesta ;   //se utiliza para en el generarInformeOK y para mostrar información en las trazas

	@Override
	public void ejecutar(Object... params) {
         try {
               Objetivo objetivoEjecutantedeTarea = (Objetivo)params[0];
               infoDecision = (InfoParaDecidirQuienVa)params[1];
               PropuestaAgente propuestaRecibida =  (PropuestaAgente)params[2];     
               nombreAgenteEmisor = this.getAgente().getIdentAgente();
               identDeEstaTarea = this.getIdentTarea();
               trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se Ejecuta la Tarea :"+ identDeEstaTarea , InfoTraza.NivelTraza.info));
  //             identsAgentesEmpatados = infoDecision.getAgentesEmpatados();           
               infoDecision.procesarValorPropuestaDesempate(propuestaRecibida);        
               if  (infoDecision.hayEmpates && (infoDecision.propuestasDesempateRecibidas ==infoDecision.propuestasDesempateEsperadas  )){
                // Se ha termiando la primera ronda de desempates con empates
                // Se intenta desempatar con los empatados
                    identsAgentesEmpatados = infoDecision.getAgentesEmpatados();
                    infoDecision.propuestasDesempateRecibidas = 0;
                    infoDecision.propuestasDesempateEsperadas = identsAgentesEmpatados.size();
                    Integer nuevaEvalucion = infoDecision.calcularEvalucionParaDesempate();   
                    mensajePropuesta = VocabularioRosace.MsgPropuesta_Para_Desempatar;
                    miPropuesta = new PropuestaAgente (nombreAgenteEmisor);
                    miPropuesta.setMensajePropuesta(VocabularioRosace.MsgPropuesta_Para_Desempatar );
                    miPropuesta.setJustificacion(nuevaEvalucion);
                    miPropuesta.setIdentObjectRefPropuesta(infoDecision.getidElementoDecision());
                 //   ComunicacionAgentes comunicacion = new ComunicacionAgentes(nombreAgenteEmisor);
                    this.getComunicator().informaraGrupoAgentes(miPropuesta, identsAgentesEmpatados);
                    trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Enviamos la propuesta: " + mensajePropuesta , InfoTraza.NivelTraza.info)); 
               }else if (!infoDecision.hayEmpates && infoDecision.tengoLaMejorEvaluacion) {
                       identsAgentesEmpatados = infoDecision.getAgentesEmpatados();
                   if(!identsAgentesEmpatados.isEmpty()){ // la propuesta hace que se desempate
                   // Se manda una propuesta a los empatados para que vayan ellos
                    miPropuesta = new PropuestaAgente (nombreAgenteEmisor);
                    miPropuesta.setMensajePropuesta(VocabularioRosace.MsgPropuesta_Para_Q_vayaOtro );
                    miPropuesta.setJustificacion(infoDecision.getMi_eval());
                    miPropuesta.setIdentObjectRefPropuesta(infoDecision.getidElementoDecision());
                    this.getComunicator().informaraGrupoAgentes(miPropuesta, identsAgentesEmpatados);
                    }
                        
               }      this.getEnvioHechos().eliminarHechoWithoutFireRules(propuestaRecibida);
                     this.getEnvioHechos().actualizarHecho(infoDecision);
            //         this.generarInformeOK(identDeEstaTarea, objetivoEjecutantedeTarea, nombreAgenteEmisor, mensajePropuesta);
            //         trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Numero de agentes con los que estoy empatado:" + identsAgentesEmpatados.size(), InfoTraza.NivelTraza.info));
                                      //            tiempoSinRecibirRespuesta.start(); 
               
   /*            
               IdentsAgentesEmpatados = infoDecision.getAgentesEmpatados();
               Integer valorEvaluacionRecibida = (Integer) propuestaRecibida.getJustificacion();
               Integer miEvaluacion =infoDecision.getMi_eval();
               
              //la evaluación recibida es menor  que la mia y no tengo todas las respuestas de los empatados NO espero las restantes
              
               if ((valorEvaluacionRecibida > miEvaluacion) || 
                                    (propuestaRecibida.getMensajePropuesta().equals(VocabularioRosace.MsgPropuesta_Oferta_Para_Ir) )){
                                //          mensajePropuesta = "CreoQueDebesIrTu";
                                //             miPropuesta = new PropuestaAgente (nombreAgenteEmisor,"CreoQueDebesIrTu", miEvaluacion);
                        miPropuesta = new PropuestaAgente (nombreAgenteEmisor);
                        mensajePropuesta = VocabularioRosace.MsgPropuesta_Para_Q_vayaOtro ;
                        miPropuesta.setMensajePropuesta(mensajePropuesta);
                        miPropuesta.setJustificacion(miEvaluacion);
                        miPropuesta.setIdentObjectRefPropuesta(infoDecision.getidElementoDecision());
                        infoDecision.noSoyElMejor = true;
                        infoDecision.tengoLaMejorEvaluacion = false;
                        infoDecision.hayEmpates = false;
                           
               } else
            	        //la evaluacion recibida es igual que la mia
                        if (valorEvaluacionRecibida == miEvaluacion){
                        	
                              Integer nuevaEvalucion = infoDecision.calcularEvalucionParaDesempate();
                              
                              mensajePropuesta = VocabularioRosace.MsgPropuesta_Para_Desempatar;
                                            //          miPropuesta = new PropuestaAgente (nombreAgenteEmisor,"MiEvaluacionParaDesempatar", nuevaEvalucion);
                              miPropuesta = new PropuestaAgente (nombreAgenteEmisor);
                              miPropuesta.setMensajePropuesta(VocabularioRosace.MsgPropuesta_Para_Desempatar );
                              miPropuesta.setJustificacion(nuevaEvalucion);
                              miPropuesta.setIdentObjectRefPropuesta(infoDecision.getidElementoDecision());
                             
                        } else  // la evaluacion recibida es menor que la mia

                                if (infoDecision.tengoTodasLasRespuestasEsperadas() ){
                                                     // genero una propuesta para ir yo
                                                    
                                        mensajePropuesta = VocabularioRosace.MsgPropuesta_Oferta_Para_Ir;
                                                     //         miPropuesta = new PropuestaAgente (nombreAgenteEmisor,"SoyElMejorSituadoParaRealizarElObjetivo", miEvaluacion);
                                        miPropuesta = new PropuestaAgente (nombreAgenteEmisor);
                                        miPropuesta.setMensajePropuesta(VocabularioRosace.MsgPropuesta_Oferta_Para_Ir );
                                        miPropuesta.setJustificacion(miEvaluacion);
                                        miPropuesta.setIdentObjectRefPropuesta(infoDecision.getidElementoDecision());
                                        infoDecision.noSoyElMejor = false;
                                        infoDecision.tengoLaMejorEvaluacion = true;
                                        infoDecision.hayEmpates = false;
                                }else
                                     infoDecision.respuestasRecibidas ++; // espero el resto de respuestas
               
               //fin del if primero  
               if (valorEvaluacionRecibida < miEvaluacion)
                       if (!infoDecision.tengoTodasLasRespuestasEsperadas())
                                            infoDecision.respuestasRecibidas ++; // espero el resto de respuestas
                       else{ // mi evaluacion es la mejor. Les mando mi propuesta para hacerme cargo del objetivo a todos los empatados
                                mensajePropuesta = VocabularioRosace.MsgPropuesta_Oferta_Para_Ir;
                                miPropuesta = new PropuestaAgente (nombreAgenteEmisor);
                                miPropuesta.setMensajePropuesta(VocabularioRosace.MsgPropuesta_Oferta_Para_Ir );
                                miPropuesta.setJustificacion(miEvaluacion);
                                miPropuesta.setIdentObjectRefPropuesta(infoDecision.getidElementoDecision());
                                infoDecision.noSoyElMejor = false;
                                infoDecision.tengoLaMejorEvaluacion = true;
                                infoDecision.hayEmpates = false; 
                       }//la evaluación recibida es mayor que la mia o alguien  propone hacerse cargo del objetivo    
               else 
               
                     trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Enviamos la propuesta: " + mensajePropuesta , InfoTraza.NivelTraza.info));           
                                      //            this.mandaMensajeAEmpatados(miPropuesta);
                     
                     ComunicacionAgentes comunicacion = new ComunicacionAgentes(nombreAgenteEmisor);
                     //¿PORQUE SE INFORMA A TODO EL GRUPO DE EMPATADOS CUANDO LA EVALUACION RECIBIDA ES MAYOR QUE LA MIA?
                     comunicacion.informaraGrupoAgentes(miPropuesta, IdentsAgentesEmpatados);
                     
                                      //      this.getEnvioHechos().assertFact(new EvaluacionAgente(nombreAgenteEmisor, nuevaEvalucion));
                                      //     this.generarTimeoutRespuestas(15000, "ExpiroElTimeoutPropuestaDesempate");
                                      //     infoDecision.setRespuestasEsperadas(IdentsAgentesEmpatados.size());
                     this.getEnvioHechos().eliminarHechoWithoutFireRules(propuestaRecibida);
                     this.getEnvioHechos().actualizarHecho(infoDecision);
            //         this.generarInformeOK(identDeEstaTarea, objetivoEjecutantedeTarea, nombreAgenteEmisor, mensajePropuesta);
                     trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Numero de agentes con los que estoy empatado:" + IdentsAgentesEmpatados.size(), InfoTraza.NivelTraza.info));
                                      //            tiempoSinRecibirRespuesta.start();  
                                      
      */
	 } catch (Exception e) {
            e.printStackTrace();
               }
              
   }


}
