package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.tareas;

import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InfoDecidirRecolector;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.RespuestaRecoleccion;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class ProcesarRespuestaRecoleccion extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		InfoDecidirRecolector info = (InfoDecidirRecolector) params[0];
		RespuestaRecoleccion resp = (RespuestaRecoleccion) params[1];
		
		info.addResponse(resp.getEmisor(), resp.getCoste());
		this.itfProcObjetivos.eliminarHechoWithoutFireRules(resp);
		this.itfProcObjetivos.actualizarHecho(info);

		
	}

}
