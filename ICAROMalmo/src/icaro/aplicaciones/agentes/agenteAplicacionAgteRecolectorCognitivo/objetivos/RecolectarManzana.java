package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos;

import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Manzana;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;


/**
 * EN PROGRESO
 * @author Sergio & Luis
 *
 */
public class RecolectarManzana extends Objetivo{
	private Manzana manzana;
	private String id;
	private String agentId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public RecolectarManzana(Manzana m){
		this.manzana = m;
		this.id = m.getId();
	}
	
	public void setManzana(Manzana m){this.manzana = m;}
	public Manzana getManzana(){return this.manzana;}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	

}
