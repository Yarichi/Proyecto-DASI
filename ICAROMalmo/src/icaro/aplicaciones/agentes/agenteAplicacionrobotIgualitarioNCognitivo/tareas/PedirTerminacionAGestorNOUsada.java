package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Tarea;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.comunicacion.ReactiveInputMessage;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;




public class PedirTerminacionAGestorNOUsada extends Tarea {

	@Override
	public void ejecutar(Object... params) {
		try {
                String identDeEstaTarea = this.getIdentTarea();
                String nombreAgenteEmisor = this.getAgente().getIdentAgente();
		ComunicacionAgentes comunicacion = new ComunicacionAgentes(nombreAgenteEmisor );
                comunicacion.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo(VocabularioRosace.MsgPeticionTerminacionGestor), nombreAgenteEmisor);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



}
