/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.Rosace.tareasComunes;
import icaro.aplicaciones.Rosace.informacion.InfoEquipo;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.Rosace.informacion.InfoRolAgente;
import icaro.aplicaciones.Rosace.informacion.RobotStatus1;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.util.ArrayList;

/**
 *
 * @author Francisco J Garijo
 */
   public class ContactarMiembrosEquipo  extends TareaSincrona {
   private ArrayList <String> agentesEquipo;//resto de agentes que forman mi equipo                                
   private String nombreAgenteEmisor;
 
   

   // private TimeOutRespuestas tiempoSinRecibirRespuesta;  //no usado

	@Override
	public void ejecutar(Object... params) {
		try {     
              RobotStatus1 miStatus = (RobotStatus1)params[0];    
             InfoEquipo equipoInfo = (InfoEquipo)params[1];  
              nombreAgenteEmisor = this.identAgente;
              agentesEquipo = equipoInfo.getTeamMemberIDs();
              trazas.aceptaNuevaTrazaEjecReglas(identAgente, "Se Ejecuta la Tarea : "+ identTarea + " Los agtes de mi equipo son : " + agentesEquipo );
                        //            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Enviamos la evaluacion " + miEvaluacion, InfoTraza.NivelTraza.masterIA));          
              InfoRolAgente mirol = new InfoRolAgente(identAgente,equipoInfo.getTeamId(),miStatus.getIdRobotRol(),VocabularioRosace.IdentMisionEquipo);
              trazas.aceptaNuevaTrazaEjecReglas(identAgente, " Enviamos informacion de Rol " + mirol.toString());  
              this.getComunicator().informaraGrupoAgentes(mirol, agentesEquipo);
              equipoInfo.setinicioContactoConEquipo();
              equipoInfo.setidentMiRolEnEsteEquipo(miStatus.getIdRobotRol());
              this.getEnvioHechos().actualizarHechoWithoutFireRules(equipoInfo);
              trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Numero de agentes de los que espero respuesta:" + agentesEquipo.size(), InfoTraza.NivelTraza.info));     
              this.generarInformeTemporizadoFromConfigProperty(VocabularioRosace.IdentTareaTimeOutContactarMiembrosEquipo,null,nombreAgenteEmisor, null);
              // en le caso de que ya la haya enviado la evaluacion no hago nada
		} catch (Exception e) {
			e.printStackTrace();
        }
    }
   
}