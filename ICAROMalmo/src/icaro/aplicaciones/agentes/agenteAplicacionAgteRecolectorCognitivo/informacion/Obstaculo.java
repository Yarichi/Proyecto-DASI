package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion;

public class Obstaculo {
	private int x, y, z;

	public Obstaculo(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
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
		return "Obstaculo -> X: "+this.x+ " Y: "+this.y+ " Z: "+this.z + '\n';
	}
}
