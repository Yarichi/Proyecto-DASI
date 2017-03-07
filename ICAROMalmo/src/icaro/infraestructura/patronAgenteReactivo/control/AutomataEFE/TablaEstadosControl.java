/*
 *  Copyright 2001 Telefnica I+D. All rights reserved
 */
package icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE;

import java.util.Hashtable;

/**
 *  Description of the Class
 *
 *@author     Jorge Gonzlez
 *@created    6 de septiembre de 2001
 */
public class TablaEstadosControl {

	/**
	 * Tabla que almacena si los estados son iniciales / finales / etc, indexa por estado
	 * @uml.property  name="clasificacionEstados"
	 * @uml.associationEnd  qualifier="identificador:java.lang.String java.lang.Integer"
	 */
	private Hashtable<String,Integer> clasificacionEstados = new Hashtable<String,Integer>();

	/**
	 * El estado inicial
	 * @uml.property  name="identificadorEstadoInicial"
	 */
	private String identificadorEstadoInicial = "";
 //   private String identificadorEstadoFinal = "EstadoFinal";
    private String identificadorEstadoErrorInterno = "EstadoErrorInterno";


	/**
	 * Tabla de tablas que almacena las tablas input/accion,estado siguiente y las indexa por estado
	 * @uml.property  name="inputsDeEstados"
	 * @uml.associationEnd  qualifier="input:java.lang.String icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.imp.Operacion"
	 */
	private Hashtable<String,Hashtable<String,Operacion>> inputsDeEstados = new Hashtable<String,Hashtable<String,Operacion>>();
	/**
	 *  Marca el tipo de estados finales
	 */
	public final static int TIPO_DE_ESTADO_FINAL = 3;

	/**
	 *  Marca el tipo del estado inicial
	 */
	public final static int TIPO_DE_ESTADO_INICIAL = 0;
	/**
	 *  Marca el tipo de estados intermedios
	 */
	public final static int TIPO_DE_ESTADO_INTERMEDIO = 2;


	/**
	 *  Constructor
	 */
	public TablaEstadosControl() { }


	/**
	 *  Devuelve el estado inicial del autmata
	 *
	 *@return    Devuelve el estado inicial del autmata
	 */
	public String dameEstadoInicial()
	{
		return identificadorEstadoInicial;
	}


	/**
	 *  Devuelve la operacion a realizar desde el estado Qi con el input i PRE: El
	 *  estado actual no es final. El input pertenece a los aceptados por ese
	 *  estado
	 *
	 *@param  estadoActual  Estado en el que se est
	 *@param  input         Input que se ha recibido
	 *@return               Operacin que hay que realizar( y estado siguiente )
	 */
	public Operacion dameOperacion(String estadoActual, String input)
	{
		Hashtable operaciones = (Hashtable) inputsDeEstados.get(estadoActual);
		return (Operacion) operaciones.get(input);
	}


	/**
	 *  Comprueba si el estado dado es final
	 *
	 *@param  estado  Estado a evaluar
	 *@return         Devuelve si cuando el estado es final, no e.o.c.
	 */
	public boolean esEstadoFinal(String estado)
	{
		return (((Integer) clasificacionEstados.get(estado)).intValue() == TablaEstadosControl.TIPO_DE_ESTADO_FINAL);
	}



	/**
	 *  Determina si el input dado es uno de los inputs que se esperan en el estado
	 *  dado
	 *
	 *@param  estado  Estado a evaluar
	 *@param  input   Input que se desea comprobar
	 *@return         Dice si es vlido o no
	 */
	public boolean esInputValidoDeEstado(String estado, String input)
	{
		Hashtable inp = (Hashtable) inputsDeEstados.get(estado);
		if ( inp != null )
                                    return (inp.containsKey(input));
                    else return false;
	}


	/**
	 *  Aade un nuevo estado a la tabla
	 *
	 *@param  identificador  Nombre del estado
	 *@param  tipo           Tipo del estado (ver enumerados de esta clase)
	 */
	public void putEstado(String identificador, int tipo)
	{
		// clasificar el estado
		clasificacionEstados.put(identificador, new Integer(tipo));
		inputsDeEstados.put(identificador, new Hashtable<String,Operacion>());

		// aceleramos la recuperacin del estado inicial
		if (tipo == TIPO_DE_ESTADO_INICIAL)
		{
			identificadorEstadoInicial = identificador;
		}
	}

public String  crearEstadoErrorInterno(){
    putEstado( identificadorEstadoErrorInterno,TIPO_DE_ESTADO_FINAL );
    return identificadorEstadoErrorInterno;
}
	/**
	 *  Aade una nueva transicin de estados a la tabla
	 *
	 *@param  estado           Estado desde el que parte la transicin
	 *@param  accion           Accin que se ejecuta
	 *@param  estadoSiguiente  Estado al que se pasa tras ejecutar la transicin
	 *@param  input            Input que activa la transicin
	 *@param  modo             Description of Parameter
	 */
	public void putTransicion(String estado, String input, String accion, String estadoSiguiente, String modo)
	{
		boolean modoBloqueante = modo.equalsIgnoreCase("bloqueante");
		Operacion operacion = new Operacion(accion, estadoSiguiente, modoBloqueante);

		// obtenemos la tabla de inputs y operaciones
		Hashtable<String,Operacion> inputsDeUnEstado = (Hashtable<String,Operacion>)inputsDeEstados.get(estado);

		// aadimos el nuevo input
		inputsDeUnEstado.put(input, operacion);
	}


	/**
	 *  Aade la transicin indicada como parmetro a todos los estados del
	 *  autmata PRE: El autmata debe estar completamente creado ( todos los
	 *  estados aadidos ) NOTA: En caso de existir inputs repetidos, se dejar
	 *  intacto el input ya existente
	 *
	 *@param  input      input de la transicin universal
	 *@param  accion     accin a ejecutar en la transicin universal
	 *@param  estadoSig  estado al que llegamos tras la transicin universal
	 *@param  modo       modo de ejecucin de la accin
	 */
	public void putTransicionUniversal(String input, String accion, String estadoSig, String modo)
	{
		// esta operacin tenemos que aadrsela a todos los estados de la tabla
		// hay que tener cuidado de que no se repitan los inputs

		//recorremos todos los estados
		java.util.Enumeration iteradorClaves = clasificacionEstados.keys();
		while (iteradorClaves.hasMoreElements())
		{
			// obtenemos el siguiente estado
			String estadoPivote = (String) iteradorClaves.nextElement();

			// aadimos la transicin si no es estado final y el input no estaba en ese estado
			if ((!esEstadoFinal(estadoPivote)) && (!esInputValidoDeEstado(estadoPivote, input)))
			{
				putTransicion(estadoPivote, input, accion, estadoSig, modo);
			}
		}

	}


	/**
	 *  Expresa la tabla en texto
	 *
	 *@return    Texto con la tabla de estados
	 */
	public String toString()
	{
		String dev = "LEYENDA:   Estado: input/accion -> estado siguiente";
		dev += "\n------------------------------------------------------";
		java.util.Enumeration nombres = clasificacionEstados.keys();

		String input = "";
		String accion = "";
		String estsig = "";
		String id = "";
		while (nombres.hasMoreElements())
		{
			id = (String) nombres.nextElement();

			Hashtable inp = (Hashtable) inputsDeEstados.get(id);
			if (!inp.isEmpty())
			{
				java.util.Enumeration inps = inp.keys();
				while (inps.hasMoreElements())
				{
					input = (String) inps.nextElement();
					Operacion op = (Operacion) inp.get(input);
					accion = op.accionSemantica;
					estsig = op.estadoSiguiente;
					dev += "\n" + id + ": " + input + " / " + accion + " -> " + estsig;
				}

			}
			else
			{
				dev += "\n" + id + " <- ES UN ESTADO FINAL";
			}

		}
		return dev += "\n------------------------------------------------------";
	}
}
