package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion;

import java.util.ArrayList;

public class InformacionMundo {
	private ArrayList<Manzana> manzanas;
	private ArrayList<Agente> agentes;
	private ArrayList<Obstaculo> obstaculos;
	public ArrayList<Manzana> getManzanas() {
		return manzanas;
	}
	public void setManzanas(ArrayList<Manzana> manzanas) {
		this.manzanas = manzanas;
	}
	public void addManzana(Manzana manzana){
		this.manzanas.add(manzana);
	}
	public ArrayList<Agente> getAgentes() {
		return agentes;
	}
	public void setAgentes(ArrayList<Agente> agentes) {
		this.agentes = agentes;
	}
	public void addAgente(Agente agente){
		this.agentes.add(agente);
	}
	public ArrayList<Obstaculo> getObstaculos() {
		return obstaculos;
	}
	public void setObstaculos(ArrayList<Obstaculo> obstaculos) {
		this.obstaculos = obstaculos;
	}
	public void addObstaculo(Obstaculo obs){
		this.obstaculos.add(obs);
	}
	
}
