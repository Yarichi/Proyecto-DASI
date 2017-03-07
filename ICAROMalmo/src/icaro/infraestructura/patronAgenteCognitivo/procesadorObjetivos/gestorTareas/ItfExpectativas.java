package icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.gestorTareas;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Evidencia;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Expectativa;


public interface ItfExpectativas {

	
	public void comprobarEvidencia(Evidencia ev) throws Exception;
	
	public boolean asociarExpectativa(Expectativa exp) throws Exception;
}
