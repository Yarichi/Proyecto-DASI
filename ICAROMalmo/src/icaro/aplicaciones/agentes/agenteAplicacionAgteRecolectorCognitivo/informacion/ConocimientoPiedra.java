package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion;

import icaro.aplicaciones.Rosace.informacion.Coordinate;

public class ConocimientoPiedra {
	private String idAgente;
	private Coordinate piedra;
	private Coordinate objetivo;
	private int yaw;
	
	
	public ConocimientoPiedra(String idAgente, Coordinate piedra,
			Coordinate objetivo, int yaw) {
		super();
		this.idAgente = idAgente;
		this.piedra = piedra;
		this.objetivo = objetivo;
		this.yaw = yaw;
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
	public Coordinate getPiedra() {
		return piedra;
	}
	public void setPiedra(Coordinate piedra) {
		this.piedra = piedra;
	}
	public Coordinate getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(Coordinate objetivo) {
		this.objetivo = objetivo;
	}
	
	public String toString(){
		return String.format("pickstone %s %f %f %d %f %f", this.idAgente, this.piedra.getX(), 
				this.piedra.getY(), this.yaw, this.objetivo.getX(), this.objetivo.getY());
	}
	

}
