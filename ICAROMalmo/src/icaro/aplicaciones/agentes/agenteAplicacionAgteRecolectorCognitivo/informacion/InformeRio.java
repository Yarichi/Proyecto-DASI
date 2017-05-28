package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion;

import icaro.aplicaciones.Rosace.informacion.Coordinate;

public class InformeRio extends InformeAbs {
	private Coordinate coordenadaManzana;
	private Coordinate coordenadaAgente;
	private int orientacionAgente;
	
	
	
	public InformeRio(String idAgenteInvolucrado, Coordinate coordenadaManzana, Coordinate coordenadaAgente,
			int orientacionAgente) {
		super(idAgenteInvolucrado);
		this.coordenadaManzana = coordenadaManzana;
		this.orientacionAgente = orientacionAgente;
		this.coordenadaAgente = coordenadaAgente;
	}
	
	
	
	public InformeRio() {
		super();
	}



	public Coordinate getCoordenadaObj() {
		return coordenadaManzana;
	}
	public void setCoordenadaObj(Coordinate coordenadaManzana) {
		this.coordenadaManzana = coordenadaManzana;
	}
	
	public Coordinate getCoordenadaObs() {
		return coordenadaAgente;
	}
	public void setCoordenadaObs(Coordinate coordenada) {
		this.coordenadaAgente = coordenada;
	}
	
	public int getOrientacionAgente() {
		return orientacionAgente;
	}
	public void setOrientacionAgente(int orientacionAgente) {
		this.orientacionAgente = orientacionAgente;
	}
}
