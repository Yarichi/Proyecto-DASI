package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion;

import icaro.aplicaciones.Rosace.informacion.Coordinate;

public class InformeObjetivo {
	private String idAgenteInvolucrado;
	private String idManzana;
	
	
	public InformeObjetivo(String idAgenteInvolucrado,String idManzana) {
		this.idAgenteInvolucrado = idAgenteInvolucrado;
		this.idManzana = idManzana;
	}
	
	public String getIdManzana() {
		return idManzana;
	}
	public void setIdManzana(String idManzana) {
		this.idManzana = idManzana;
	}
	public String getIdAgenteInvolucrado() {
		return idAgenteInvolucrado;
	}
	public void setIdAgenteInvolucrado(String idAgenteInvolucrado) {
		this.idAgenteInvolucrado = idAgenteInvolucrado;
	}
}
