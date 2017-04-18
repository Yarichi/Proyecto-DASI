package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion;

import icaro.aplicaciones.Rosace.informacion.Coordinate;

public class Agente {
	private String idAgente;
	private Coordinate coordinate;

	public Agente(String idAgente, int x, int y, int z){
		this.idAgente = idAgente;
		this.coordinate = new Coordinate(x, y, z);
	}
	
	public String getId(){
		return this.idAgente;
	}
	
	public String getIdAgente() {
		return idAgente;
	}

	public void setIdAgente(String idAgente) {
		this.idAgente = idAgente;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public String toString(){
		return "Agente -> X: "+this.coordinate.getX()+ " Y: "+this.coordinate.getY()+ " Z: "+this.coordinate.getZ() + '\n';
	}
	
}
