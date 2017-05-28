package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos;

import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformePiedra;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformeRio;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

public class PicarPiedra extends Objetivo{
	private String idAgenteDescubridor;//Id del agente que descubrio el rio. sera el encargado de construir el puente.
	private InformePiedra informe;
	
	
	
	public PicarPiedra( String idAgente, InformePiedra informe) {
		super();
		this.idAgenteDescubridor = idAgente;
		this.informe = informe;
	}
	public String getIdAgenteDescubridor() {
		return idAgenteDescubridor;
	}
	public void setIdAgenteDescubridor(String idAgenteDescubridor) {
		this.idAgenteDescubridor = idAgenteDescubridor;
	}
	public InformePiedra getInforme() {
		return informe;
	}
	public void setInforme(InformePiedra informe) {
		this.informe = informe;
	}
	
}
