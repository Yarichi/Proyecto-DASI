package icaro.aplicaciones.recursos.recursoMalmo;

import java.rmi.RemoteException;
import java.util.ArrayList;

import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Agente;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Manzana;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Obstaculo;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoRecursoMalmo extends ItfUsoRecursoSimple{
	public abstract ArrayList<String> getObstaculos() throws RemoteException;
	public abstract ArrayList<String> getAgentes() throws RemoteException;
	public abstract String getInformacionAgente(String idAgente) throws RemoteException;
	public abstract ArrayList<String> getInformacionManzanas() throws RemoteException;
	
}
