package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.PropuestaAgente;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformeRio;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos.ConstruirPuente;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class CrearObjetivoConstruirPuente extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		// TODO Auto-generated method stub
		InformeRio informe = (InformeRio) params[0];
		ConstruirPuente objetivo = new ConstruirPuente(informe.getIdAgenteInvolucrado(),informe);
		objetivo.setPending();
		this.itfProcObjetivos.eliminarHechoWithoutFireRules(informe);
		this.itfProcObjetivos.insertarHecho(objetivo);
	}

}
