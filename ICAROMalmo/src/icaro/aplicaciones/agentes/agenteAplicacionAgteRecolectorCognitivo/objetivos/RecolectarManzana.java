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
	
	public RecolectarManzana(Manzana m, String id){
		this.manzana = m;
		this.id = id;
	}
	
	public void setManzana(Manzana m){this.manzana = m;}
	public Manzana getManzana(){return this.manzana;}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

}
