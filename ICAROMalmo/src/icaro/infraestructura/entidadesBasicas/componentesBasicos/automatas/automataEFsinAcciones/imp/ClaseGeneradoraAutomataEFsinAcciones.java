/*
    
  */
package icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFsinAcciones.imp;

import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFsinAcciones.*;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFsinAcciones.ItfUsoAutomataEFsinAcciones;
import org.apache.log4j.Logger;


/**
 *  Clase que define automatas de estados finitos, sin acciones semanticas a ejecutar en las transiciones,
 *  escritos en XML
 *
 *@author     Alvaro Rodrguez
 *@created    5 de septiembre de 2001
 *@modified	  02 de Marzo de 2007
 */

public abstract class ClaseGeneradoraAutomataEFsinAcciones implements ItfUsoAutomataEFsinAcciones {
	
	
	/**
	 * Indica si se deben mostrar mensajes de depuracin o no
	 * @uml.property  name="dEBUG"
	 */
	public boolean DEBUG = false;

	/**
	 * Controla la profundidad de las trazas
	 * @uml.property  name="traza"
	 */
	protected static int traza = 0;

	/**
	 * @uml.property  name="estadoActual"
	 */
	private String estadoActual;

	/**
	 * Tabla que representa los estados del autmata
	 * @uml.property  name="theTablaEstadosControl"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private static TablaEstadosAutomataEF tablaEstadosAutomata;
    private static ClaseGeneradoraAutomataEFsinAcciones instance;
	
	private Logger logger = Logger.getRootLogger();
	
	/**
	 * @uml.property  name="trazas"
	 * @uml.associationEnd  readOnly="true"
	 */
	protected ItfUsoRecursoTrazas trazas;
	
//private static InterpreteTablaEstadosAutomataSinAcciones interpreteTablaEstados;
    private static XMLParserTablaEstadosAutomataEF interpreteTablaEstados;
	/**
	 *  Crea un autmata XML
	 *
	 *@param  NombreFicheroDescriptor  Nombre del fichero que contiene el autmata
	 *@param  nivelTraza               Profundidad de las trazas (usar constantes
	 *      definidas estticas en esta clase)
	 */
   
	public static ClaseGeneradoraAutomataEFsinAcciones instance(String rutaFicheroTablaEstados){
		if (instance == null){
       // Creo el interprete de la tabla y luego el automata
//            interpreteTablaEstados = new InterpreteTablaEstadosAutomataSinAcciones();
            interpreteTablaEstados = new XMLParserTablaEstadosAutomataEF();
            tablaEstadosAutomata = interpreteTablaEstados.extraeTablaEstadosDesdeFicheroXML(rutaFicheroTablaEstados);
//			instance = new AutomataSinAccionesControlImp( tablaEstadosAutomata,traza);
		
                        }
// Hacemos una copia de la Tabla de estados
            {
            tablaEstadosAutomata = (TablaEstadosAutomataEF)tablaEstadosAutomata.clone();
            instance = new AutomataSinAccionesControlImp( tablaEstadosAutomata,traza);
            }
              return instance;
    }
    }
