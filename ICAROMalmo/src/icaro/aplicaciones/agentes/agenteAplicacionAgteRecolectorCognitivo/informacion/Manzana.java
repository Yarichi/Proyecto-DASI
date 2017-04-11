package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion;

public class Manzana {
	private int x, y, z;
	private boolean recolectado;
	
	public Manzana(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.recolectado = false;
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
	public boolean isRecolectado() {
		return recolectado;
	}
	public void setRecolectado(boolean recolectado) {
		this.recolectado = recolectado;
	}
	
	public String toString(){
		return "Manzana -> X: "+this.x+ " Y: "+this.y+ " Z: "+this.z + '\n';
	}

	
}
