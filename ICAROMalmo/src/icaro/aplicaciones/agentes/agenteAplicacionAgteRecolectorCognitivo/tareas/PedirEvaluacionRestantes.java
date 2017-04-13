package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.tareas;

import java.util.ArrayList;

import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InfoDecidirRecolector;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InfoEquipoMoic;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.PeticionRecoleccion;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos.RecolectarManzana;
import icaro.infraestructura.entidadesBasicas.informes.InformeDeTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class PedirEvaluacionRestantes extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		RecolectarManzana obj1 = (RecolectarManzana) params[0];
		InfoDecidirRecolector infoDec = (InfoDecidirRecolector) params[1];
		InfoEquipoMoic info = (InfoEquipoMoic) params[2];
		InformeDeTarea tarea = (InformeDeTarea) params[3];
		
		String id = this.agente.getIdentAgente();
		PeticionRecoleccion pet = new PeticionRecoleccion(id, VocabularioRosace.MsgPeticionEnvioEvaluaciones, obj1.getManzana());
		this.comunicator.enviarMsgaGrupoAgentes(pet, infoDec.getReceptores());
		this.itfProcObjetivos.eliminarHecho(tarea);
		this.generarInformeTemporizadoFromConfigProperty(VocabularioRosace.IdentTareaTimeOutRecibirEvaluaciones2,obj1, 
				id,  infoDec.getObjetivoID());
		this.generarInformeOK(this.getIdentTarea(), obj1, id, "PeticionDeEvaluacionesQueFaltanRealizada");
		
	}

}
