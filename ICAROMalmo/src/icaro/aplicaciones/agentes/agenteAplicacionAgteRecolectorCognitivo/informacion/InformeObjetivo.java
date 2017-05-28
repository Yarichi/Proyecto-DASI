package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion;

import icaro.aplicaciones.Rosace.informacion.Coordinate;

public class InformeObjetivo {
	private String idAgenteInvolucrado;
	
	
	
	public InformeObjetivo(String idAgenteInvolucrado) {
		this.idAgenteInvolucrado = idAgenteInvolucrado;
	}
	
	
	
	public String getIdAgenteInvolucrado() {
		return idAgenteInvolucrado;
	}
	public void setIdAgenteInvolucrado(String idAgenteInvolucrado) {
		this.idAgenteInvolucrado = idAgenteInvolucrado;
	}
}
