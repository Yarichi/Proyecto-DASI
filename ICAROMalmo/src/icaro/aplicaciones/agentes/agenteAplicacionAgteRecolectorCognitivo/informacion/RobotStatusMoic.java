package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion;

import icaro.aplicaciones.Rosace.informacion.Coordinate;

public class RobotStatusMoic implements Cloneable{
	private String idAgente;
	private String idRolAgente;
	private Coordinate posicionAgente;
	private boolean bloqueado;
	
	public String getIdAgente() {
		return idAgente;
	}
	public void setIdAgente(String idAgente) {
		this.idAgente = idAgente;
	}
	public Coordinate getPosicionAgente() {
		return posicionAgente;
	}
	public void setPosicionAgente(Coordinate posicionAgente) {
		this.posicionAgente = posicionAgente;
	}
	public void setIdRobotRol(String rolEnEquipoId) {
		this.idRolAgente = rolEnEquipoId;
		
	}
	public String getIdRobotRol() {
		return this.idRolAgente;
	}
	public boolean getBloqueado() {
		return this.bloqueado;
	}
	
}
