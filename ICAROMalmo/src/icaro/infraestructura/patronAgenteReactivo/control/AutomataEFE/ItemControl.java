/*
 *  Copyright 2001 Telefnica I+D
 *
 *
 *  All rights reserved
 */
package icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE;


/**
 * Clase de los eventos de control *
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public class ItemControl {
	/**
	 * Tipo de evento de control
	 * @uml.property  name="operacion"
	 */
	public int operacion;
	/**
	 *  Ha ocurrido un evento de timeout de alguna clase
	 */
	public final static int OPERACION_TIMEOUT = 0;
	/**
	 *  Se ha ordenado terminar
	 */
	public final static int OPERACION_TERMINAR = 1;


	/**
	 *  Constructor
	 *
	 *@param  op  Operacin que simboliza este evento
	 */
	public ItemControl(int op)
	{
		operacion = op;
	}
}
