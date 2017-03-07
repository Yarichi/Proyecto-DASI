/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.tareas;
import icaro.aplicaciones.Rosace.informacion.AceptacionPropuesta;
import icaro.aplicaciones.Rosace.informacion.PropuestaAgente;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.informacion.InfoParaDecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 *
 * @author Francisco J Garijo
 */
public class ProcesarConfirmacionPropuestaParaIr extends TareaSincrona{

// private ItfUsoRecursoTrazas trazas;

  @Override
  public void ejecutar(Object... params) {
		try {

                      //         InterfazUsoAgente    itfUsoPropiadeEsteAgente = (InterfazUsoAgente) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz(nombreAgenteEmisor);
                      //           tiempoSinRecibirRespuesta = new TimeOutRespuestas(5000,itfUsoPropiadeEsteAgente, notifTimeoutRespuesta);
                      // para generar el informe con referencia al objetivo en que se ejecuta la tarea, se le pasa el ident del objetivo en el primer parametro
             Objetivo objetivoEjecutantedeTarea = (Objetivo)params[0];
                      //  String identTareaLong = getClass().getName();
             String identTarea = this.getIdentTarea();
             String nombreAgenteEmisor = this.getAgente().getIdentAgente();
             InfoParaDecidirQuienVa infoDecisionAgente = (InfoParaDecidirQuienVa) params[1];
                      //  PropuestaAgente respuestaRecibida = (PropuestaAgente) params[2];
             String nombreAgenteEmisorRespuesta = (String) params[2];
             AceptacionPropuesta confirmacionRecibida = (AceptacionPropuesta) params[3];
             trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se Ejecuta la Tarea :"+ identTarea , InfoTraza.NivelTraza.info));
             PropuestaAgente propuestaConfirmada = confirmacionRecibida.getpropuestaAceptada();
             String msgPropuestaOriginal = propuestaConfirmada.getMensajePropuesta();
                      //             if ((msgPropuestaOriginal.equals(VocabularioRosace.MsgPropuesta_Para_Q_vayaYo) )||(msgPropuestaOriginal.equals( VocabularioRosace.MsgAceptacionPropuesta) )){

             if ((msgPropuestaOriginal != VocabularioRosace.MsgPropuesta_Decision_Ir )&
            	 (msgPropuestaOriginal != VocabularioRosace.MsgPropuesta_Oferta_Para_Ir )){
                      this.generarInformeConCausaTerminacion(identTarea, objetivoEjecutantedeTarea, nombreAgenteEmisor, 
                    		                                 "LaPropuestaConfirmadaNoEsValida", CausaTerminacionTarea.ERROR);
                      trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "La propuesta Confirmada No es valida :" + 
                    		                                propuestaConfirmada  , InfoTraza.NivelTraza.error));
             } else {
            	      //JM: El contenido de la confirmacion de propuestaaceptada era YoVoy o SoyElMejorSituadoParaRealizarElObjetivo
            	      //la siguiente llamada actualiza la variable tengoAcuerdoDeTodos de InfoParaDecidirQuienVa
            	      //se volvera a meter al motor
                      infoDecisionAgente.addConfirmacionAcuerdoParaIr(nombreAgenteEmisorRespuesta,
                    		                                          confirmacionRecibida.getmsgAceptacionPropuesta());

                      if (infoDecisionAgente.getTengoAcuerdoDeTodos()){
                           if (infoDecisionAgente.gettengoLaMejorEval()){
                                this.generarInformeOK(identTarea, objetivoEjecutantedeTarea, nombreAgenteEmisor, 
                                		              "TengoAcuerdoDeTodosParaIrYo");
                                infoDecisionAgente.setTengoAcuerdoDeTodos(Boolean.TRUE);
                           } else {
                                this.generarInformeOK(identTarea, objetivoEjecutantedeTarea, nombreAgenteEmisor, 
                                		              "NoSoyElMejor Pero TengoAcuerdoDeTodosParaIrYo");
                           }
                      } else {
                           // Todavia no tengo todas las respuestas
                           if (infoDecisionAgente.tengoTodasLasRespuestasEsperadas()){
                                this.generarInformeOK(identTarea, objetivoEjecutantedeTarea, nombreAgenteEmisor, 
                                		              "HayOtroAgenteQueQuiereIr");
                           } else { // Me faltan respuestas pa
                                this.generarInformeOK(identTarea, objetivoEjecutantedeTarea, nombreAgenteEmisor, 
                                		              VocabularioRosace.ResEjTaskDebenLlegarMasConfirmacionesParaIrYo);
                           }
                      }
             }             
                      //           this.getEnvioHechos().insertarHecho(infoDecisionAgente);
             this.getEnvioHechos().actualizarHechoWithoutFireRules(infoDecisionAgente);
             //en la regla tambien se hace un retract
             this.getEnvioHechos().eliminarHecho(confirmacionRecibida);
                          
             trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "El numero de respuestas confirmadas para irYo es  :"+ infoDecisionAgente.numeroRespuestasConfirmacionParaIrYo  , InfoTraza.NivelTraza.info));

        } catch (Exception e) {
			e.printStackTrace();
        }
  }

}