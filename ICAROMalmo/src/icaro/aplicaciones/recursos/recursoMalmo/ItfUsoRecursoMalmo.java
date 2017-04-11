package icaro.aplicaciones.recursos.recursoMalmo;

import java.rmi.RemoteException;
import java.util.ArrayList;

import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Agente;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Manzana;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Obstaculo;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoRecursoMalmo extends ItfUsoRecursoSimple{
	public abstract ArrayList<Obstaculo> getObstaculos() throws Exception;
	public abstract ArrayList<Agente> getAgentes() throws Exception;
	public abstract Agente getInformacionAgente(String idAgente) throws Exception;
	public abstract ArrayList<Manzana> getInformacionManzanas() throws Exception;
	
}
