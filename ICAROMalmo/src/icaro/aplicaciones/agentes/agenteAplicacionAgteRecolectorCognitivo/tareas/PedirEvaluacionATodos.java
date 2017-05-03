package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.tareas;

import java.util.ArrayList;

import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InfoDecidirRecolector;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InfoEquipoMoic;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.PeticionRecoleccion;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos.RecolectarManzana;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class PedirEvaluacionATodos extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		RecolectarManzana obj1 = (RecolectarManzana) params[0];
		InfoDecidirRecolector infoDec = (InfoDecidirRecolector) params[1];
		InfoEquipoMoic info = (InfoEquipoMoic) params[2];
		ArrayList<String> members = info.getTeamMemberIDs();
		members.add(this.identAgente);
		String id = this.agente.getIdentAgente();
		infoDec.setReceptores(members);
		infoDec.setTodasLasPeticiones(true);
		PeticionRecoleccion pet = new PeticionRecoleccion(id, VocabularioRosace.MsgPeticionEnvioEvaluaciones, obj1.getManzana());
		pet.setEmisor(this.identAgente);
		for(int i = 0;i<members.size();i++){
			this.comunicator.enviarInfoAotroAgente(pet, members.get(i));
		}
		this.itfProcObjetivos.actualizarHecho(infoDec);
		
		this.generarInformeTemporizadoFromConfigProperty(VocabularioRosace.IdentTareaTimeOutRecibirEvaluaciones1,obj1, 
				id,  infoDec.getObjetivoID());
	}

}
