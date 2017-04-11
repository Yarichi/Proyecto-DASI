package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.tareas;

import java.util.ArrayList;

import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Manzana;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos.RecolectarTodasLasManzanas;
import icaro.aplicaciones.recursos.recursoMalmo.ItfUsoRecursoMalmo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class ConseguirInformacionDelEntorno extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		try {
			ItfUsoRecursoMalmo malmo = (ItfUsoRecursoMalmo) repoInterfaces.obtenerInterfazGestion(VocabularioRosace.IdentRecursoMalmo);
			ArrayList<Manzana> manzanas = malmo.getInformacionManzanas();
			RecolectarTodasLasManzanas obj = new RecolectarTodasLasManzanas();
			
			//ArrayList<Obstaculo> obstaculos = malmo.getObstaculos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
