package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.tareas;

import java.io.IOException;

import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InfoEquipoMoic;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.PeticionRecoleccion;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.RespuestaRecoleccion;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.RobotStatusMoic;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos.RecolectarManzana;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos.RecolectarTodasLasManzanas;
import icaro.aplicaciones.recursos.recursoMalmo.ItfUsoRecursoMalmo;
import icaro.aplicaciones.recursos.recursoMalmo.imp.PythonOrderDispatcher;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class FinalizarSimulacionRecoleccion extends TareaSincrona {

	@Override
	public void ejecutar(Object... params) {
		//Comunicacion con python para realizar el algoritmo de rutas
		RecolectarTodasLasManzanas obj = (RecolectarTodasLasManzanas) params[0];
		try {
			Process p = Runtime.getRuntime().exec("Taskkill -IM python.exe -F");
			p.waitFor();
			
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		obj.setSolved();
		
		
		this.itfProcObjetivos.actualizarHecho(obj);
	
	}

	
}
