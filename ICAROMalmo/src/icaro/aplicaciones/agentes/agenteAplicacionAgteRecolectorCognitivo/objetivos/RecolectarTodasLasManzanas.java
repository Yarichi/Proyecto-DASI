package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos;

import java.util.ArrayList;

import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Manzana;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

public class RecolectarTodasLasManzanas extends Objetivo{
	private ArrayList<Manzana> manzanas;
	
	//Revisar si hace falta un clone
	public RecolectarTodasLasManzanas(ArrayList<Manzana> m){this.manzanas = m;}

	public ArrayList<Manzana> getManzanas() {
		return manzanas;
	}

	public void setManzanas(ArrayList<Manzana> manzanas) {
		this.manzanas = manzanas;
	}
	
}
