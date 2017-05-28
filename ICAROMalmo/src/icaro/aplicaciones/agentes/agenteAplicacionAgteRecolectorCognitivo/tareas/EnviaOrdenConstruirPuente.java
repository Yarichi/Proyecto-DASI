package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.PropuestaAgente;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.ConocimientoInformes;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.FormatoInforme;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InfoEquipoMoic;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformeRio;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos.ConstruirPuente;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class EnviaOrdenConstruirPuente extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		ConstruirPuente objetivoConstruirPuente = (ConstruirPuente) params[0];
		InfoEquipoMoic infoEquipo = (InfoEquipoMoic) params[1];
		ConocimientoInformes cono = (ConocimientoInformes) params[2];
		String idAgente = objetivoConstruirPuente.getIdAgenteDescubridor();
		
		
		if(infoEquipo.getTeamMemberIDs().contains(idAgente)){
			InformeRio inf = objetivoConstruirPuente.getInforme();
			FormatoInforme form = new FormatoInforme(inf, cono.getFormato(inf), idAgente);
			this.getComunicator().enviarInfoAotroAgente(form, idAgente);
		}
		
		objetivoConstruirPuente.setSolved();
		this.itfProcObjetivos.actualizarHecho(objetivoConstruirPuente);
		
		
	}

}
