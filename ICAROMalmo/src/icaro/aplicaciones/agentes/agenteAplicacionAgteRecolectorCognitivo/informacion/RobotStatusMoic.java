package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion;

import icaro.aplicaciones.Rosace.informacion.Coordinate;

public class RobotStatusMoic implements Cloneable{
	private String idRobot;
	private String idRolAgente;
	private Coordinate posicionAgente;
	private boolean bloqueado;
	private boolean inicializado;
	
	public RobotStatusMoic(){
		this.inicializado = false;
	}
	
	public boolean isInicializado() {
		return inicializado;
	}

	public void setInicializado(boolean inicializado) {
		this.inicializado = inicializado;
	}

	public String getIdRobot() {
		return idRobot;
	}
	public void setIdRobot(String idAgente) {
		this.idRobot = idAgente;
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
