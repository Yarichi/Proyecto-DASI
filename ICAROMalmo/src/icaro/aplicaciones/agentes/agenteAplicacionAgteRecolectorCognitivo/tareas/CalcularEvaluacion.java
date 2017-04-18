package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InfoEquipoMoic;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.PeticionRecoleccion;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.RespuestaRecoleccion;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos.RecolectarManzana;
import icaro.aplicaciones.recursos.recursoMalmo.ItfUsoRecursoMalmo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class CalcularEvaluacion extends TareaSincrona {

	@Override
	public void ejecutar(Object... params) {
		//Comunicacion con python para realizar el algoritmo de rutas
		PeticionRecoleccion peticion = (PeticionRecoleccion) params[0];
		RecolectarManzana recolectarManzana = (RecolectarManzana) params[1];
		InfoEquipoMoic infoEquipo = (InfoEquipoMoic) params[2];
		
		ItfUsoRecursoMalmo itfMalmo;
		Integer coste = -1;
		try {
			itfMalmo = (ItfUsoRecursoMalmo) this.repoInterfaces.obtenerInterfazGestion(VocabularioRosace.IdentRecursoMalmo);
			coste = itfMalmo.calculaCoste(this.identAgente,peticion.getManzana().getCoordinate());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RespuestaRecoleccion resp = new RespuestaRecoleccion(this.identAgente, VocabularioRosace.RespuestaCosteRecoleccion, recolectarManzana.getId() , coste);
		this.comunicator.enviarInfoAotroAgente(resp, infoEquipo.getidentAgenteJefeEquipo());
	
	}
	

}
