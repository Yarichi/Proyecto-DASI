package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion;

import icaro.aplicaciones.Rosace.informacion.Coordinate;

public class InformePiedra extends InformeAbs{
	private Coordinate coordenadaPiedra;
	private Coordinate coordenadaManzana;
	private int orientacionAgente;
	
	
	
	public InformePiedra(String idAgenteInvolucrado, Coordinate coordenadaManzana, Coordinate coordenadaAgente,
			int orientacionAgente) {
		super(idAgenteInvolucrado);
		this.coordenadaPiedra = coordenadaAgente;
		this.coordenadaManzana = coordenadaManzana;
		this.orientacionAgente = orientacionAgente;
	}
	
	
	
	public InformePiedra() {
		super();
	}



	public Coordinate getCoordenadaObs() {
		return coordenadaPiedra;
	}
	public void setCoordenadaO(Coordinate coordenadaPiedra) {
		this.coordenadaPiedra = coordenadaPiedra;
	}
	public int getOrientacionAgente() {
		return orientacionAgente;
	}
	public void setOrientacionAgente(int orientacionAgente) {
		this.orientacionAgente = orientacionAgente;
	}
	public Coordinate getCoordenadaObj() {
		return this.coordenadaManzana;
	}
}
