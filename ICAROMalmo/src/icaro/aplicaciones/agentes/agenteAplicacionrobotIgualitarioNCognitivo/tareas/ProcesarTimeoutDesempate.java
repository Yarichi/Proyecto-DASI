package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.tareas;
import icaro.aplicaciones.Rosace.informacion.DecisionAgente;
import icaro.aplicaciones.Rosace.informacion.PropuestaAgente;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.informacion.InfoParaDecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaComunicacion;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.util.ArrayList;

/**
 *
 * @author Francisco J Garijo
 */
public class ProcesarTimeoutDesempate  extends TareaSincrona {

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
               
               nombreAgenteEmisor = this.getAgente().getIdentAgente();
               String identTareaLong = getClass().getName();
               identDeEstaTarea = this.getIdentTarea();
               trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se Ejecuta la Tarea :"+ identDeEstaTarea , InfoTraza.NivelTraza.info));
  //             identsAgentesEmpatados = infoDecision.getAgentesEmpatados();           
               
               if  (infoDecision.hayEmpates ){
                // El agente se hace cargo del objetivo.               
                    DecisionAgente decision = new DecisionAgente (nombreAgenteEmisor,VocabularioRosace.MsgPropuesta_Decision_Ir);
                    decision.setidentObjectRefDecision(infoDecision.getidElementoDecision()); // En este caso el identificador se refiere a la victima
                    decision.setJustificacion(infoDecision.getMi_eval());
                    
                    this.getComunicator().informaraGrupoAgentes(decision, infoDecision.getAgentesEquipo());
                    this.generarInformeOK(identDeEstaTarea, objetivoEjecutantedeTarea, nombreAgenteEmisor, "DecisionDeIrEnviadaAtodos");
                  //  infoDecision.hayEmpates = false; infoDecision.tengoLaMejorEvaluacion = true;
                    infoDecision.setMiDecisionParaAsumirElObjetivoEnviadaAtodos(true);
                    trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Enviamos la decision: " + decision , InfoTraza.NivelTraza.debug)); 
                    this.getEnvioHechos().eliminarHechoWithoutFireRules(params[2]);
                    this.getEnvioHechos().actualizarHecho(infoDecision);
               }
            
	 } catch (Exception e) {
            e.printStackTrace();
               }
              
   }


}
