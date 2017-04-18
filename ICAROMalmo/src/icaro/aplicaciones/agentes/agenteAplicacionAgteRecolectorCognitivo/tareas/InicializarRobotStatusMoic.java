package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Agente;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.RobotStatusMoic;
import icaro.aplicaciones.recursos.recursoMalmo.ItfUsoRecursoMalmo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class InicializarRobotStatusMoic extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		// TODO Auto-generated method stub
		RobotStatusMoic robotStatus = new RobotStatusMoic();
		robotStatus.setIdAgente(this.identAgente);
		if(this.identAgente.contains("1"))
			robotStatus.setIdRobotRol(VocabularioRosace.IdentRolAgtesRecolectorCoord);
		else{
			robotStatus.setIdRobotRol(VocabularioRosace.IdentRolAgtesRecolectores);
		}
		ItfUsoRecursoMalmo itfMalmo;
		Agente ag = null;
		try {
			itfMalmo = (ItfUsoRecursoMalmo) this.repoInterfaces.obtenerInterfazUso(VocabularioRosace.IdentRecursoMalmo);
			ag = itfMalmo.getInformacionAgente(this.identAgente);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		robotStatus.setPosicionAgente(ag.getCoordinate());
		
		
		this.itfProcObjetivos.insertarHecho(robotStatus);
		
		
	}

}
