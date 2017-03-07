/*
 *  Copyright 2001 Telefnica I+D. All rights reserved
 */
package icaro.infraestructura.entidadesBasicas.componentesBasicos.buzonConTimeout;



/**
 *  Operaciones que necesita el consumidor de este buzn
 *
 *@author     Jorge Gonzlez
 *@created    11 de septiembre de 2001
 */
public interface Itf_ConsumidorBuzonTimeout {

	/**
	 *  Consumir un objeto del buzn, mantenindose el consumidor bloqueado no ms
	 *  de 'tiempoEnMilisegundos' milisegundos
	 *
	 *@param  tiempoEnMilisegundos          Milis que se esperar como mximo
	 *@return                               El Objeto que se ha sacado
	 *@exception  ExcepcionTimeOutSuperado  Generada si pasa el tiempo especificado y no se ha extrado ningun objeto
	 */
	public Object consumeConTimeout(int tiempoEnMilisegundos)
		throws ExcepcionTimeOutSuperado;


	/**
	 *  Consume un objeto del buzn. Espera hasta que haya un objeto disponible.
	 *
	 *@return    Objeto que se ha extrado del buzn
	 */
	public Object consumeBloqueante();
}
