package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion;

import icaro.aplicaciones.Rosace.informacion.Coordinate;

public class Manzana {
	private Coordinate coordinate;
	private String Id;
	private boolean recolectado;
	
	public Manzana(String id, int x, int y, int z) {
		this.coordinate = new Coordinate(x,y,z);
		this.Id = id;
		this.recolectado = false;
	}
	public boolean isRecolectado() {
		return recolectado;
	}
	public void setRecolectado(boolean recolectado) {
		this.recolectado = recolectado;
	}
	
	public String toString(){
		return "Manzana -> X: "+this.coordinate.getX() + " Y: "+ this.coordinate.getY() + " Z: "+ this.coordinate.getZ() + '\n';
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
