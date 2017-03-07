/*
 *  Copyright 2001 Telefnica I+D. All rights reserved
 */
package icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces;

import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import java.rmi.RemoteException;

import java.util.Set;


/**
 *  Interfaz de Gestin del agente reactivo
 *
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public interface ItfGestionAgenteReactivo extends InterfazGestion {

	
	/**
	 *  Establece el gestor a reportar
	 *  @param nombreGestor nombre del gestor a reportar
	 *  @param listaEventos lista de posibles eventos que le puede enviar.
	 *  
	 *  El gestionador obtendr las interfaces del gestor a partir del repositorio de interfaces y podr validar la informacin.
	 *
	 */
	public void setGestorAReportar(String nombreGestor)throws RemoteException;
}
