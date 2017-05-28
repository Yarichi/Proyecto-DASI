package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.PropuestaAgente;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.ConocimientoInformes;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.FormatoInforme;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InfoEquipoMoic;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformePiedra;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformeRio;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos.ConstruirPuente;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos.PicarPiedra;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class EnviaOrdenPicarPiedra extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		PicarPiedra objetivoPicarPiedra = (PicarPiedra) params[0];
		InfoEquipoMoic infoEquipo = (InfoEquipoMoic) params[1];
		ConocimientoInformes cono = (ConocimientoInformes) params[2];
		String idAgente = objetivoPicarPiedra.getIdAgenteDescubridor();
		
		
		if(infoEquipo.getTeamMemberIDs().contains(idAgente)){
			InformePiedra inf = objetivoPicarPiedra.getInforme();
			FormatoInforme form = new FormatoInforme(inf, cono.getFormato(inf), idAgente);
			this.getComunicator().enviarInfoAotroAgente(form, idAgente);
		}
		
		objetivoPicarPiedra.setSolved();
		this.itfProcObjetivos.actualizarHecho(objetivoPicarPiedra);
		
		
	}

}
