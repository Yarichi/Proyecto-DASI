/*
 *  Copyright 2001 Telefnica I+D. All rights reserved
 */
package icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces;




/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public interface ItfConsumidorPercepcion {
	/**
	 *  Consume un objeto, pero no se bloquea ms de un tiempo especificado por
	 *  parmetro en caso de consumir ese tiempo se produce una excepcin
	 *
	 *@param  tiempoEnMilisegundos               Tiempo que se esperar como mximo
	 *@return                                    Objeto que se ha consumido
	 *@exception  ExcepcionSuperadoTiempoLimite  No se consiguio consumir en el tiempo especificado
	 *@todo                                      producen
	 */
	public Object consumeConTimeout(int tiempoEnMilisegundos)
		throws ExcepcionSuperadoTiempoLimite;


	/**
	 *  Consume un objeto, se queda esperando hasta que haya un objeto que consumir
	 *
	 *@return    Objeto que se ha consumido
	 */
//	public Object consumeBloqueante();
}
