package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion;

import icaro.aplicaciones.Rosace.informacion.Coordinate;

public class InformeRio {
	private String idAgenteInvolucrado;
	private Coordinate coordenadaManzana;
	private Coordinate coordenadaAgente;
	private int orientacionAgente;
	
	
	
	public InformeRio(String idAgenteInvolucrado, Coordinate coordenadaManzana, Coordinate coordenadaAgente,
			int orientacionAgente) {
		this.idAgenteInvolucrado = idAgenteInvolucrado;
		this.coordenadaManzana = coordenadaManzana;
		this.coordenadaAgente = coordenadaAgente;
		this.orientacionAgente = orientacionAgente;
	}
	
	
	
	public String getIdAgenteInvolucrado() {
		return idAgenteInvolucrado;
	}
	public void setIdAgenteInvolucrado(String idAgenteInvolucrado) {
		this.idAgenteInvolucrado = idAgenteInvolucrado;
	}
	public Coordinate getCoordenadaManzana() {
		return coordenadaManzana;
	}
	public void setCoordenadaManzana(Coordinate coordenadaManzana) {
		this.coordenadaManzana = coordenadaManzana;
	}
	public Coordinate getCoordenadaAgente() {
		return coordenadaAgente;
	}
	public void setCoordenadaAgente(Coordinate coordenadaAgente) {
		this.coordenadaAgente = coordenadaAgente;
	}
	public int getOrientacionAgente() {
		return orientacionAgente;
	}
	public void setOrientacionAgente(int orientacionAgente) {
		this.orientacionAgente = orientacionAgente;
	}
}
