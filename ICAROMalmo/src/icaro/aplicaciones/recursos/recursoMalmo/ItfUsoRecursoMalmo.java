package icaro.aplicaciones.recursos.recursoMalmo;

import java.util.ArrayList;

import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Agente;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Manzana;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Obstaculo;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoRecursoMalmo extends ItfUsoRecursoSimple{
	public abstract ArrayList<Obstaculo> getObstaculos();
	public abstract ArrayList<Agente> getAgentes();
	public abstract Agente getInformacionAgente(String idAgente);
	public abstract ArrayList<Manzana> getInformacionManzanas();
	
}
