/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.tareas;
import icaro.aplicaciones.Rosace.informacion.EvaluacionAgente;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.informacion.InfoParaDecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 *
 * @author Francisco J Garijo
 */
public class ProcesarEvaluacionRecibida extends TareaSincrona{
        
  @Override
  public void ejecutar(Object... params) {
		try {

                     //         InterfazUsoAgente    itfUsoPropiadeEsteAgente = (InterfazUsoAgente) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz(nombreAgenteEmisor);
                     //           tiempoSinRecibirRespuesta = new TimeOutRespuestas(5000,itfUsoPropiadeEsteAgente, notifTimeoutRespuesta);
                     // para generar el informe con referencia al objetivo en que se ejecuta la tarea, se le pasa el ident del objetivo en el primer parametro
             Objetivo objetivoEjecutantedeTarea = (Objetivo)params[0];
             InfoParaDecidirQuienVa infoDecisionAgente = (InfoParaDecidirQuienVa) params[1];
             EvaluacionAgente evaluacionRecibida = (EvaluacionAgente) params[2];
             trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente, "Se Ejecuta la Tarea :"+ this.identTarea , InfoTraza.NivelTraza.info));
             trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente, "Se Procesa la Evaluacion  enviada por el agente :"+ evaluacionRecibida.identAgente +" Cuyo Valor es:"+evaluacionRecibida.valorEvaluacion  , InfoTraza.NivelTraza.debug));
             //Cuanto tengo todas las evaluaciones el metodo addNuevaEvaluacion actualiza las variables noSoyElMejor, hayEmpates, 
             //tengoLaMejorEvaluacion de InfoParaDecidirQuienVa  
              this.getEnvioHechos().eliminarHechoWithoutFireRules(evaluacionRecibida);
             infoDecisionAgente.addNuevaEvaluacion(evaluacionRecibida);
             
             if (infoDecisionAgente.tengoTodasLasEvaluaciones()){
                 if ( infoDecisionAgente.tengoLaMejorEvaluacion){
                       // this.generarInformeOK(identDeEstaTarea, objetivoEjecutantedeTarea, nombreAgenteEmisor, VocabularioRosace.ResEjTaskProcesadoEvaluaciones_SoyElMejor);
                      this.getEnvioHechos().actualizarHecho(infoDecisionAgente);
                 } else {
                          if (infoDecisionAgente.gethayEmpates()){
                           //  this.generarInformeOK(identDeEstaTarea, objetivoEjecutantedeTarea, nombreAgenteEmisor, VocabularioRosace.ResEjTaskProcesadoEvaluaciones_HayEmpates);
                          this.getEnvioHechos().actualizarHecho(infoDecisionAgente);
                          } else {
                              //   this.generarInformeOK(identDeEstaTarea, objetivoEjecutantedeTarea, nombreAgenteEmisor, VocabularioRosace.ResEjTaskProcesadoEvaluaciones_NoSoyElMejor);
                              this.getEnvioHechos().actualizarHechoWithoutFireRules(infoDecisionAgente);
                          }
                   }
             } else {
           // Todavia no tengo todas las respuestas
//                 this.generarInformeOK(identDeEstaTarea, objetivoEjecutantedeTarea, nombreAgenteEmisor, VocabularioRosace.ResEjTaskProcesadoEvaluaciones_MefaltanEvaluaciones);
             }
             
            
             // lo metemos directamente al motor el infoDecisionAgente  
        //      this.getEnvioHechos().actualizarHechoWithoutFireRules(infoDecisionAgente);
        //      this.getEnvioHechos().eliminarHecho(evaluacionRecibida);
             
//}             
             
        } catch (Exception e) {
			   e.printStackTrace();
        }
}


}
