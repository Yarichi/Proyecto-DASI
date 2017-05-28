package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.ConocimientoInformes;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformeAbs;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformePiedra;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformeRio;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class GenerarConocimientoInformesCoord extends TareaSincrona{


	
	InformeAbs[] inf = {new InformeRio(), new InformePiedra()};
	
	String[] formatos = {(String.format("buildriver %s %s %s %s %s %s",
						VocabularioRosace.parte_informe_idAgente,
						VocabularioRosace.parte_informe_coordObsX,
						VocabularioRosace.parte_informe_coordObsZ,
						VocabularioRosace.parte_informe_coordObjX,
						VocabularioRosace.parte_informe_coordObjZ,
						VocabularioRosace.parte_informe_orientacion)),
						(String.format("pickstone %s %s %s %s %s %s",
								VocabularioRosace.parte_informe_idAgente,
								VocabularioRosace.parte_informe_coordObsX,
								VocabularioRosace.parte_informe_coordObsZ,
								VocabularioRosace.parte_informe_coordObjX,
								VocabularioRosace.parte_informe_coordObjZ,
								VocabularioRosace.parte_informe_orientacion))};
	public void ejecutar(Object... params) {
		ConocimientoInformes cono = new ConocimientoInformes();
		for (int i = 0; i < inf.length; i++)
			cono.addFormato(inf[i], formatos[i]);
		this.itfProcObjetivos.insertarHecho(cono);
	}

}
