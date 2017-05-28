package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.tareas;

import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformeObjetivo;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos.RecolectarManzana;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos.RecolectarTodasLasManzanas;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class FinalizacionObjetivoRecolectarManzanaCoord extends TareaSincrona {

	@Override
	public void ejecutar(Object... params) {
		RecolectarManzana obj = (RecolectarManzana) params[0];
		RecolectarTodasLasManzanas obj1 = (RecolectarTodasLasManzanas) params[1];
		InformeObjetivo inf = (InformeObjetivo) params[2];
		
		System.out.println("Manzana recogida por " + inf.getIdAgenteInvolucrado());
		obj.setSolved();
		obj1.setRecolectada(obj.getManzana().getId());
		this.itfProcObjetivos.eliminarHechoWithoutFireRules(inf);
		this.itfProcObjetivos.actualizarHechoWithoutFireRules(obj1);
		this.itfProcObjetivos.actualizarHecho(obj);
		
	
	}
	

}
