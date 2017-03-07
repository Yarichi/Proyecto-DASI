package icaro.infraestructura.entidadesBasicas.interfaces;

import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import java.rmi.Remote;
import java.rmi.RemoteException;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoSimple;

public interface ItfEventoSimpe extends Remote {
	
	/**
	 *  Inserta un nuevo evento en la percepcin del agente
	 *
	 *@param  ev  Evento que se inserta en el agente
	 */
	public void aceptaEvento(EventoRecAgte ev) throws  RemoteException;

	public void aceptaEvento(EventoSimple ev) throws  RemoteException;

	;


}
