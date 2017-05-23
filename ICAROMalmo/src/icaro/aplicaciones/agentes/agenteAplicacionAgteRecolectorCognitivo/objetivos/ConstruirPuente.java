package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos;

import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformeRio;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

public class ConstruirPuente extends Objetivo{
	private String idAgenteDescubridor;//Id del agente que descubrio el rio. sera el encargado de construir el puente.
	private InformeRio informe;
	
	
	
	public ConstruirPuente( String idAgente, InformeRio informe) {
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
	public InformeRio getInforme() {
		return informe;
	}
	public void setInforme(InformeRio informe) {
		this.informe = informe;
	}
	
}
