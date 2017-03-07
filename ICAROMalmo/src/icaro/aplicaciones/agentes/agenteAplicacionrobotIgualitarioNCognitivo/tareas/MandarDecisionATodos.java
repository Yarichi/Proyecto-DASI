/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.DecisionAgente;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.informacion.InfoParaDecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 *
 * @author Francisco J Garijo
 */
public class MandarDecisionATodos extends TareaSincrona {
       
	@Override
	public void ejecutar(Object... params) {
            try {
  //               trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
                 Objetivo objetivoEjecutantedeTarea = (Objetivo)params[0];
                 InfoParaDecidirQuienVa  infoDecision = (InfoParaDecidirQuienVa)params[1];
                 String msgDecision = (String) params[2];
                 String identObjetoDecision = (String) params[3];
                 DecisionAgente decision = new DecisionAgente (this.identAgente,msgDecision);
                             decision.setidentObjectRefDecision(identObjetoDecision); // En este caso el identificador se refiere a la victima
                             decision.setJustificacion(infoDecision.getMi_eval());
                 trazas.aceptaNuevaTrazaEjecReglas(this.identAgente, "Enviamos la decision " + decision); 
                 trazas.aceptaNuevaTraza(new InfoTraza("OrdenAsignacion",
                                                     "El robot " + this.identAgente + " se hace cargo de la victima " + identObjetoDecision,
                                                     InfoTraza.NivelTraza.debug));
                 this.getComunicator().informaraGrupoAgentes(decision, infoDecision.getAgentesEquipo());
                 infoDecision.setMiDecisionParaAsumirElObjetivoEnviadaAtodos(Boolean.TRUE);
                // se prodria quitar
                this.getEnvioHechos().actualizarHechoWithoutFireRules(infoDecision);
                this.generarInformeOK(identTarea, objetivoEjecutantedeTarea, this.identAgente, "DecisionDeIrEnviadaAtodos");
                           //     trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Numero de agentes de los que espero respuesta:" + agentesEquipo.size(), InfoTraza.NivelTraza.info));
                           //            tiempoSinRecibirRespuesta.start();

		    } catch (Exception e) {
			     e.printStackTrace();
            }
    }
	
}