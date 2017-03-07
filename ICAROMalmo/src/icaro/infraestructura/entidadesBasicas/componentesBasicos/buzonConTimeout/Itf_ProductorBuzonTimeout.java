/*
 *  Copyright 2001 Telefnica I+D. All rights reserved
 */
package icaro.infraestructura.entidadesBasicas.componentesBasicos.buzonConTimeout;

/**
 *  Mtodos que necesita un productor de un buzn
 *
 *@author     Jorge Gonzlez
 *@created    2 de octubre de 2001
 */
public interface Itf_ProductorBuzonTimeout {

	/**
	 *  Aade un objeto al buzn, el objeto se encola al final de los ya existentes
	 *
	 *@param  evento  Objeto que aadimos
	 */
	public void produce(Object evento);


	/**
	 *  Aade un objeto al buzn, el objeto ser consumido inmediatamente, se coloca como el primero a consumir
	 *
	 *@param  evento  Objeto que aadimos
	 */
	public void produceParaConsumirInmediatamente(Object evento);
}
