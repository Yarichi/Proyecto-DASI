package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.PropuestaAgente;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InfoEquipoMoic;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformeRio;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class EnviaOrdenConstruirPuente extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		InformeRio informeRio = (InformeRio) params[0];
		InfoEquipoMoic infoEquipo = (InfoEquipoMoic) params[1];
		
		String idAgente = informeRio.getIdAgenteInvolucrado();
		if(infoEquipo.getTeamMemberIDs().contains(idAgente)){
			PropuestaAgente miPropuesta = new PropuestaAgente (this.identAgente);
			miPropuesta.setMensajePropuesta(VocabularioRosace.MsgConstruirPuente);
			miPropuesta.setIdentObjectRefPropuesta(idAgente);
			miPropuesta.setJustificacion(informeRio);
			this.getComunicator().enviarInfoAotroAgente(miPropuesta, idAgente);
		}
	}

}
