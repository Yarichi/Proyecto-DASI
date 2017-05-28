package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.PropuestaAgente;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformePiedra;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformeRio;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos.ConstruirPuente;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos.PicarPiedra;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class CrearObjetivoPicarPiedra extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		// TODO Auto-generated method stub
		InformePiedra informe = (InformePiedra) params[0];
		PicarPiedra objetivo = new PicarPiedra(informe.getIdAgenteInvolucrado(),informe);
		objetivo.setPending();
		this.itfProcObjetivos.eliminarHechoWithoutFireRules(informe);
		this.itfProcObjetivos.insertarHecho(objetivo);
	}

}
