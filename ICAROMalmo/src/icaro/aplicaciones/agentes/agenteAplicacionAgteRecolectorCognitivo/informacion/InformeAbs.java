package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion;

import icaro.aplicaciones.Rosace.informacion.Coordinate;

public abstract class  InformeAbs {
	private String idAgenteInvolucrado;
	private Coordinate coordenadaAgente;
	
	
	
	public InformeAbs(String idAgenteInvolucrado) {
		this.idAgenteInvolucrado = idAgenteInvolucrado;
	}
	
	
	
	public InformeAbs() {
	}



	public String getIdAgenteInvolucrado() {
		return idAgenteInvolucrado;
	}
	public void setIdAgenteInvolucrado(String idAgenteInvolucrado) {
		this.idAgenteInvolucrado = idAgenteInvolucrado;
	}
	
	public abstract Coordinate getCoordenadaObs();
	public abstract Coordinate getCoordenadaObj();
	public abstract int getOrientacionAgente();

}
