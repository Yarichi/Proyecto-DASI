/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.tareas;
import icaro.aplicaciones.Rosace.informacion.DecisionAgente;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.informacion.InfoParaDecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 *
 * @author FGarijo
 */
public class AceptarDecisionDeOtroAgente extends TareaSincrona{
    @Override
   public void ejecutar(Object... params) {
	   try {
             Objetivo ayudarVictima = (Objetivo) params[0];
             Objetivo decidirQuienVa = (Objetivo)params[1];
             InfoParaDecidirQuienVa infoDecision = (InfoParaDecidirQuienVa)params[2];
             Focus focoActual = (Focus)params[3];
             DecisionAgente decisionOtroAgente= (DecisionAgente)params[4];
             String nombreAgenteEmisor = this.getIdentAgente();
             decidirQuienVa.setSolved();
             infoDecision.objetivoAsumidoPorOtroAgte= true;
                this.getEnvioHechos().actualizarHechoWithoutFireRules(infoDecision);
                this.getEnvioHechos().actualizarHechoWithoutFireRules(decidirQuienVa);
                this.getEnvioHechos().eliminarHecho(decisionOtroAgente);
                this.getEnvioHechos().actualizarHecho(focoActual);
            trazas.aceptaNuevaTrazaEjecReglas(identAgente, "Se ha ejecutado la tarea " + identTarea
                            + " Se ha tomado una decision para salvar a la victima :  " + decisionOtroAgente.getidentObjectRefDecision() + "\n"+
                            "Objetivo conseguido :  " + decidirQuienVa + "El foco esta en el objetivo  :  " + focoActual+ "\n");
                            
            System.out.println("\n"+nombreAgenteEmisor +"Se ejecuta la tarea " + this.getIdentTarea()+ " Se actualiza el  objetivo:  "+ ayudarVictima+"\n\n" );
                          
             
       } catch (Exception e) {
			 e.printStackTrace();
       }
   }

}

