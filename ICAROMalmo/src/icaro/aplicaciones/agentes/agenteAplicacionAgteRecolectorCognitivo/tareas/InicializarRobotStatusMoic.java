package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Agente;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.RobotStatusMoic;
import icaro.aplicaciones.recursos.recursoMalmo.ItfUsoRecursoMalmo;
import icaro.aplicaciones.recursos.recursoMalmo.imp.PythonOrderDispatcher;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class InicializarRobotStatusMoic extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		// TODO Auto-generated method stub
		RobotStatusMoic robotStatus = (RobotStatusMoic) params[0];
		//robotStatus.setIdRobot(this.identAgente);
		/*if(this.identAgente.contains("1"))
			robotStatus.setIdRobotRol(VocabularioRosace.IdentRolAgtesRecolectorCoord);
		else{
			robotStatus.setIdRobotRol(VocabularioRosace.IdentRolAgtesRecolectores);
		}*/
		ItfUsoRecursoMalmo itfMalmo;
		try {
			itfMalmo = (ItfUsoRecursoMalmo) this.repoInterfaces.obtenerInterfaz(VocabularioRosace.IdentRecursoMalmo);
			itfMalmo.getInformacionAgente(this.identAgente);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(!PythonOrderDispatcher.agentes.containsKey(this.identAgente.toLowerCase())){
			
		}
		robotStatus.setPosicionAgente(PythonOrderDispatcher.agentes.get(this.identAgente.toLowerCase()));
		robotStatus.setInicializado(true);
		this.itfProcObjetivos.actualizarHecho(robotStatus);
		
		
	}

}
