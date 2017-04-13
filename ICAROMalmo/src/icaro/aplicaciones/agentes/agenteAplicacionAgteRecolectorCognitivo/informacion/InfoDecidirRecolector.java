package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class InfoDecidirRecolector {
	public boolean todasLasRespuestas;
	public boolean todasLasPeticiones;
	private ArrayList<String> receptores;
	public String objetivoID;
	private HashMap<String, Integer> tablaCostes;
	
	public InfoDecidirRecolector(String id){
		this.objetivoID = id;
		this.todasLasPeticiones = false;
		this.todasLasRespuestas = false;
		this.receptores = new ArrayList<String>();
		this.tablaCostes = new HashMap<String, Integer>();
	}
	
	public String getObjetivoID() {
		return objetivoID;
	}

	public void setObjetivoID(String objetivoID) {
		this.objetivoID = objetivoID;
	}


	public void addResponse(String id, Integer coste){
		this.tablaCostes.put(id, coste);
		this.receptores.remove(id);
		this.todasLasRespuestas = this.receptores.isEmpty();
	}
	
	public boolean isTodasLasRespuestas() {
		return todasLasRespuestas;
	}

	public ArrayList<String> getReceptores() {
		return receptores;
	}

	public void setReceptores(ArrayList<String> receptores) {
		this.receptores = receptores;
	}

	public void setTodasLasRespuestas(boolean todasLasRespuestas) {
		this.todasLasRespuestas = todasLasRespuestas;
	}

	public boolean isTodasLasPeticiones() {
		return todasLasPeticiones;
	}

	public void setTodasLasPeticiones(boolean todasLasPeticiones) {
		this.todasLasPeticiones = todasLasPeticiones;
	}

	public String selectBestAgent(){
		Iterator<Entry<String, Integer>> it = this.tablaCostes.entrySet().iterator();
		int mejorCoste = -1;
		String agente = "";
		while(it.hasNext()){
			Entry<String, Integer> e = it.next();
			if(mejorCoste == -1 || e.getValue() < mejorCoste){
				mejorCoste = e.getValue();
				agente = e.getKey();
			}
		}
		return agente;
	}
}
