/*
 *  Copyright 2001 Telefnica I+D. All rights reserved
 */
package icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE;

/**
 *  Ttulo: Contenedor de acciones y estados siguientes Descripcion:
 *
 *@author     Jorge Gonzlez
 *@created    24 de septiembre de 2001
 *@version    1.0
 */

public class Operacion {

	/**
	 * Nombre de la accin a ejecutar
	 * @uml.property  name="accionSemantica"
	 */
	public String accionSemantica;
	/**
	 * Estado que se alcanza tras la ejecucin de la accin
	 * @uml.property  name="estadoSiguiente"
	 */
	public String estadoSiguiente;
	/**
	 * determina si la accin se ejecutar de forma bloqueante y se esperar su resultado antes de cambiar de estado o no
	 * @uml.property  name="modoTransicionBloqueante"
	 */
	public boolean modoTransicionBloqueante;


	/**
	 *  Constructor
	 *
	 *@param  accion     mtodo a ejecutar
	 *@param  estadoSig  estado siguiente del autmata
	 *@param  modo       modo de ejecucin (true=bloqueante, false=nobloqueante)
	 */
	public Operacion(String accion, String estadoSig, boolean modo)
	{
		accionSemantica = accion;
		estadoSiguiente = estadoSig;
		modoTransicionBloqueante = modo;
	}
}
