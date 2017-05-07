package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion;

import icaro.aplicaciones.Rosace.informacion.Coordinate;

public class Enemigo {

	private Coordinate coordinate;
	private String Id;
	
	public Enemigo(String id, int x, int y, int z) {
		this.coordinate = new Coordinate(x,y,z);
		this.Id = id;
	}
	
	public String toString(){
		return "Enemigo -> X: "+this.coordinate.getX() + " Y: "+ this.coordinate.getY() + " Z: "+ this.coordinate.getZ() + '\n';
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public Coordinate getCoordinate() {
		return this.coordinate;
	}
	
}
