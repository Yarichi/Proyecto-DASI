package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InfoEquipoMoic;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformeObjetivo;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.PeticionRecoleccion;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.RespuestaRecoleccion;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.RobotStatusMoic;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos.RecolectarManzana;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos.RecolectarTodasLasManzanas;
import icaro.aplicaciones.recursos.recursoMalmo.ItfUsoRecursoMalmo;
import icaro.aplicaciones.recursos.recursoMalmo.imp.PythonOrderDispatcher;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class FinalizacionObjetivoRecolectarManzana extends TareaSincrona {

	@Override
	public void ejecutar(Object... params) {		
		RecolectarManzana obj = (RecolectarManzana) params[0];
		RecolectarTodasLasManzanas obj1 = (RecolectarTodasLasManzanas) params[1];
		InformeObjetivo inf = (InformeObjetivo) params[2];
		
		if(!this.identAgente.equalsIgnoreCase(inf.getIdAgenteInvolucrado()))
			this.comunicator.enviarInfoAotroAgente(new MensajeSimple(new InformeObjetivo(inf.getIdAgenteInvolucrado(),inf.getIdManzana()), this.identAgente, VocabularioRosace.IdentRolAgtesRecolectorCoord), VocabularioRosace.IdentRolAgtesRecolectorCoord);
		else{
			obj.setSolved();
			this.itfProcObjetivos.actualizarHechoWithoutFireRules(obj);
		}
		obj1.setRecolectada(obj.getManzana().getId());
		this.itfProcObjetivos.eliminarHechoWithoutFireRules(inf);
		this.itfProcObjetivos.actualizarHecho(obj1);
	}
	

}
