
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.AceptacionPropuesta;
import icaro.aplicaciones.Rosace.informacion.PropuestaAgente;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.util.ArrayList;

/**
 *
 * @author Francisco J Garijo
 */
public class MandarConfirmacionPropuesta  extends TareaSincrona {

	/**  */
    private InterfazUsoAgente agenteReceptor;
    private ArrayList agentesEquipo, respuestasAgentes,confirmacionesAgentes,nuevasEvaluacionesAgentes,empates;//resto de agentes que forman mi equipo

                  //private TimeOutRespuestas tiempoSinRecibirRespuesta;  //no usado

    private String nombreAgenteEmisor;
//    private ItfUsoRecursoTrazas trazas;
    private String identDeEstaTarea ;
    private String nombreAgenteReceptor ;
        
	@Override
	public void ejecutar(Object... params) {
		
//           trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
           Objetivo objetivoEjecutantedeTarea = (Objetivo)params[0];
           PropuestaAgente propuestaRecibida = (PropuestaAgente)params[1];
           nombreAgenteEmisor = this.getAgente().getIdentAgente();
           identDeEstaTarea = this.getIdentTarea();

           try {
                 trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se Ejecuta la Tarea :"+ identDeEstaTarea , InfoTraza.NivelTraza.debug));

                 nombreAgenteReceptor = propuestaRecibida.getIdentAgente();
                 
                 AceptacionPropuesta propuestaAceptada = new AceptacionPropuesta (nombreAgenteEmisor,VocabularioRosace.MsgAceptacionPropuesta,propuestaRecibida);
                 propuestaAceptada.setidentObjectRefAcetPropuesta(propuestaRecibida.getIdentObjectRefPropuesta());
                 trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Enviamos la confirmacion de la Propuesta: "+ propuestaRecibida + " Al agente " +nombreAgenteReceptor  , InfoTraza.NivelTraza.debug));
                 this.getComunicator().enviarInfoAotroAgente(propuestaAceptada, nombreAgenteReceptor);
       //          this.getComunicator().enviarInfoConMomentoCreacionAotroAgente(propuestaAceptada, nombreAgenteReceptor);
                 // Meto  la propuesta aceptada en el motor
                 this.getEnvioHechos().insertarHechoWithoutFireRules(propuestaAceptada);
         //      this.getEnvioHechos().eliminarHechoWithoutFireRules(propuestaRecibida); // No hace falta porque al meterla en la confirmacion es como si se eliminara
                 this.generarInformeOK(identDeEstaTarea, objetivoEjecutantedeTarea, nombreAgenteEmisor,"propuesta_Aceptada" );
                         //         trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Enviada Propuesta al agente " + agentesEquipo.size(), InfoTraza.NivelTraza.info));
                         //            tiempoSinRecibirRespuesta.start();

		   } catch (Exception e) {
			     e.printStackTrace();
           }            
    }

}
