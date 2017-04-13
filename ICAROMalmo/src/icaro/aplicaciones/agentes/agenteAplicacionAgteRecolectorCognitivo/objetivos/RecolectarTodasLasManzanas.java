package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos;

import java.util.ArrayList;

import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Manzana;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

public class RecolectarTodasLasManzanas extends Objetivo{
	private ArrayList<Manzana> manzanas;
	private ArrayList<String> manzanas_tratadas;
	
	//Revisar si hace falta un clone
	public RecolectarTodasLasManzanas(ArrayList<Manzana> m){this.manzanas = m;this.manzanas_tratadas = new ArrayList<String>();}

	public ArrayList<Manzana> getManzanas() {return manzanas;}
	public void setManzanas(ArrayList<Manzana> manzanas) {this.manzanas = manzanas;}
	
	public void setRecolectada(String id){
		this.manzanas_tratadas.add(id);
		if(this.manzanas_tratadas.size() == this.manzanas.size()) this.setSolved();
	}
	
	public boolean isRecolectada(String id){return this.manzanas_tratadas.contains(id);}
	
	public Manzana getNextManzana(){
		int x = this.manzanas_tratadas.size();
		Manzana manzana = this.manzanas.get(x); 
		this.manzanas_tratadas.add(manzana.getId());
		return manzana;
	}
	
}
