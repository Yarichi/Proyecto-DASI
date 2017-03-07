/*
 *  Copyright 2001 Telefnica I+D. All rights reserved
 */
package icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFsinAcciones.imp;

import java.io.Serializable;
import java.util.Hashtable;



/**
 *  Description of the Class
 *
 *@author     lvaro Rodrguez
 *@created    6 de septiembre de 2001
 *@modified   02 de Marzo de 2007
 */
public class TablaEstadosAutomataEF implements Cloneable, Serializable{

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

	/**
	 * Tabla de tablas que almacena las tablas input/estado siguiente y las indexa por estado
	 * @uml.property  name="inputsDeEstados"
	 * @uml.associationEnd  qualifier="input:java.lang.String java.lang.String"
	 */
	private Hashtable<String,Hashtable<String,String>> inputsDeEstados = new Hashtable<String,Hashtable<String,String>>();
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
	public TablaEstadosAutomataEF() { }


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
	 *  Devuelve el estado siguiente al estado Qi con el input i PRE: El
	 *  estado actual no es final. El input pertenece a los aceptados por ese
	 *  estado
	 *
	 *@param  estadoActual  Estado en el que se est
	 *@param  input         Input que se ha recibido
	 *@return               estado siguiente
	 */
	public String dameEstadoSiguiente(String estadoActual, String input)
	{
		Hashtable transiciones = (Hashtable) inputsDeEstados.get(estadoActual);
		return ((String)transiciones.get(input));
	}


	/**
	 *  Comprueba si el estado dado es final
	 *
	 *@param  estado  Estado a evaluar
	 *@return         Devuelve si cuando el estado es final, no e.o.c.
	 */
	public boolean esEstadoFinal(String estado)
	{
		return (((Integer) clasificacionEstados.get(estado)).intValue() == TablaEstadosAutomataEF.TIPO_DE_ESTADO_FINAL);
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
		return (inp.containsKey(input));
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
		inputsDeEstados.put(identificador, new Hashtable<String,String>());

		// aceleramos la recuperacin del estado inicial
		if (tipo == TIPO_DE_ESTADO_INICIAL)
		{
			identificadorEstadoInicial = identificador;
		}
	}


	/**
	 *  Aade una nueva transicin de estados a la tabla
	 *
	 *@param  estado           Estado desde el que parte la transicin
	 *@param  input            Input que activa la transicin
	 *@param  estadoSiguiente  Estado al que se pasa tras ejecutar la transicin
	 */
	public void putTransicion(String estado, String input, String estadoSiguiente)
	{

		// obtenemos la tabla de inputs y operaciones
		Hashtable<String,String> inputsDeUnEstado = (Hashtable<String,String>)inputsDeEstados.get(estado);

		// aadimos el nuevo input
		inputsDeUnEstado.put(input, estadoSiguiente);
	}


	/**
	 *  Aade la transicin indicada como parmetro a todos los estados del
	 *  autmata PRE: El autmata debe estar completamente creado ( todos los
	 *  estados aadidos ) NOTA: En caso de existir inputs repetidos, se dejar
	 *  intacto el input ya existente
	 *
	 *@param  input      input de la transicin universal
	 *@param  estadoSig  estado al que llegamos tras la transicin universal
	 */
	public void putTransicionUniversal(String input, String estadoSig)
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
				putTransicion(estadoPivote, input, estadoSig);
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
		String dev = "LEYENDA:   Estado: input -> estado siguiente";
		dev += "\n------------------------------------------------------";
		java.util.Enumeration nombres = clasificacionEstados.keys();

		String input = "";
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
					estsig = (String) inp.get(input);
					dev += "\n" + id + ": " + input + " -> " + estsig;
				}

			}
			else
			{
				dev += "\n" + id + " <- ES UN ESTADO FINAL";
			}

		}
		return dev += "\n------------------------------------------------------";
	}
    public Object clone(){
        Object obj=null;
        try{
            obj=super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println(" no se puede duplicar");
        }
        return obj;
    }

}
