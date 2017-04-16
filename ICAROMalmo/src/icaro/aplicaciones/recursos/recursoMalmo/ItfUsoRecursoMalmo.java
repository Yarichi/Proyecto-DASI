package icaro.aplicaciones.recursos.recursoMalmo;

import java.util.ArrayList;

import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Agente;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Manzana;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Obstaculo;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoRecursoMalmo extends ItfUsoRecursoSimple{
	public abstract ArrayList<Obstaculo> getInformacionObstaculos() throws Exception;
	public abstract ArrayList<Agente> getInformacionAgentes() throws Exception;
	public abstract Agente getInformacionAgente(String idAgente) throws Exception;
	public abstract ArrayList<Manzana> getInformacionManzanas() throws Exception;
	
}
