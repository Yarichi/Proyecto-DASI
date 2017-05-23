package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.PropuestaAgente;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InfoEquipoMoic;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos.ConstruirPuente;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class EnviaOrdenConstruirPuente extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		ConstruirPuente objetivoConstruirPuente = (ConstruirPuente) params[0];
		InfoEquipoMoic infoEquipo = (InfoEquipoMoic) params[1];
		String idAgente = objetivoConstruirPuente.getIdAgenteDescubridor();
		
		
		if(infoEquipo.getTeamMemberIDs().contains(idAgente)){
			PropuestaAgente miPropuesta = new PropuestaAgente (this.identAgente);
			miPropuesta.setMensajePropuesta(VocabularioRosace.MsgConstruirPuente);
			miPropuesta.setIdentObjectRefPropuesta(idAgente);
			miPropuesta.setJustificacion(objetivoConstruirPuente.getInforme());
			this.getComunicator().enviarInfoAotroAgente(miPropuesta, idAgente);
		}
		
		objetivoConstruirPuente.setSolved();
		this.itfProcObjetivos.actualizarHecho(objetivoConstruirPuente);
		
		
	}

}
