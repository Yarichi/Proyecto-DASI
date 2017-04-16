package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion;

public class Agente {
	private String idAgente;
	private int x, y, z;

	public Agente(String idAgente, int x, int y, int z){
		this.idAgente = idAgente;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public String getId(){
		return this.idAgente;
	}
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
	
	public String toString(){
		return "Agente -> X: "+this.x+ " Y: "+this.y+ " Z: "+this.z + '\n';
	}
	
}
