package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.tareas;

import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.ConocimientoInformes;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformeAbs;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformePiedra;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformeRio;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class GenerarConocimientoInformes extends TareaSincrona{


	
	InformeAbs[] inf = {new InformeRio(), new InformePiedra()};
	public void ejecutar(Object... params) {
		ConocimientoInformes cono = new ConocimientoInformes();
		cono.initConocimiento(inf);
		this.itfProcObjetivos.insertarHecho(cono);
	}

}
