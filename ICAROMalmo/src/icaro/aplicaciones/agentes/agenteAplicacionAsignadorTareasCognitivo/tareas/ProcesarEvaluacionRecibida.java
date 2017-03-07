/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.tareas;
import icaro.aplicaciones.Rosace.informacion.EvaluacionAgente;
import icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.informacion.InfoParaDecidirAQuienAsignarObjetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 *
 * @author Francisco J Garijo
 */
public class ProcesarEvaluacionRecibida extends TareaSincrona{
        private String nombreAgenteEmisor;
        private ItfUsoRecursoTrazas trazas;
        private String identDeEstaTarea, IdentAgenteEmpatado ;
        private String mensajePropuesta ;
        
  @Override
  public void ejecutar(Object... params) {
		try {

                     //         InterfazUsoAgente    itfUsoPropiadeEsteAgente = (InterfazUsoAgente) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz(nombreAgenteEmisor);
                     //           tiempoSinRecibirRespuesta = new TimeOutRespuestas(5000,itfUsoPropiadeEsteAgente, notifTimeoutRespuesta);
                     // para generar el informe con referencia al objetivo en que se ejecuta la tarea, se le pasa el ident del objetivo en el primer parametro
             trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
             Objetivo objetivoEjecutantedeTarea = (Objetivo)params[0];
             String nombreAgenteEmisor = this.identAgente;
             InfoParaDecidirAQuienAsignarObjetivo infoDecisionAgente = (InfoParaDecidirAQuienAsignarObjetivo) params[1];
             EvaluacionAgente evaluacionRecibida = (EvaluacionAgente) params[2];

             identDeEstaTarea = this.getIdentTarea();
             trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se Ejecuta la Tarea :"+ identDeEstaTarea , InfoTraza.NivelTraza.info));
             trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se Procesa la Evaluacion  enviada por el agente :"+ evaluacionRecibida.identAgente +" Cuyo Valor es:"+evaluacionRecibida.valorEvaluacion  , InfoTraza.NivelTraza.info));
             //Cuanto tengo todas las evaluaciones el metodo addNuevaEvaluacion actualiza las variables noSoyElMejor, hayEmpates, 
             //tengoLaMejorEvaluacion de InfoParaDecidirQuienVa  
              infoDecisionAgente.addNuevaEvaluacion(evaluacionRecibida);
               this.getEnvioHechos().eliminarHechoWithoutFireRules(evaluacionRecibida);
              if (infoDecisionAgente.hanLlegadoTodasLasEvaluaciones) this.getEnvioHechos().actualizarHecho(infoDecisionAgente);
              else this.getEnvioHechos().actualizarHechoWithoutFireRules(infoDecisionAgente);
             // lo metemos directamente al motor el infoDecisionAgente  
        //      this.getEnvioHechos().actualizarHechoWithoutFireRules(infoDecisionAgente);
        //      this.getEnvioHechos().eliminarHecho(evaluacionRecibida);
             
//}             

        } catch (Exception e) {
			   e.printStackTrace();
        }
}


}
