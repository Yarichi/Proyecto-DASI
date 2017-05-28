package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion;

public class FormatoInforme {
	private InformeAbs inf;
	private String formato;
	private String agentReceptor;
	
	
	public FormatoInforme(InformeAbs inf, String formato, String agentReceptor) {
		super();
		this.inf = inf;
		this.formato = formato;
		this.agentReceptor = agentReceptor;
	}
	
	public InformeAbs getInf() {
		return inf;
	}
	public void setInf(InformeAbs inf) {
		this.inf = inf;
	}
	public String getFormato() {
		return formato;
	}
	public void setFormato(String formato) {
		this.formato = formato;
	}
	public String getAgentReceptor() {
		return agentReceptor;
	}
	public void setAgentReceptor(String agentReceptor) {
		this.agentReceptor = agentReceptor;
	}
}
