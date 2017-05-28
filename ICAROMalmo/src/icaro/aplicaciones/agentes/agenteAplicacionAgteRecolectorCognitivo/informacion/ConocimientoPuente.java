package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion;

import icaro.aplicaciones.Rosace.informacion.Coordinate;

public class ConocimientoPuente {
	private String idAgente;
	private Coordinate puente;
	private Coordinate objetivo;
	private int yaw;
	
	
	public ConocimientoPuente() {
	}
	
	public int getYaw() {
		return yaw;
	}

	public void setYaw(int yaw) {
		this.yaw = yaw;
	}

	public String getIdAgente() {
		return idAgente;
	}
	public void setIdAgente(String idAgente) {
		this.idAgente = idAgente;
	}
	public Coordinate getPuente() {
		return puente;
	}
	public void setPuente(Coordinate puente) {
		this.puente = puente;
	}
	public Coordinate getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(Coordinate objetivo) {
		this.objetivo = objetivo;
	}
	
	public String toString(){
		return String.format("buildriver %s %f %f %d %f %f", this.idAgente, this.puente.getX(), 
				this.puente.getY(), this.yaw, this.objetivo.getX(), this.objetivo.getY());
	}
	

}
