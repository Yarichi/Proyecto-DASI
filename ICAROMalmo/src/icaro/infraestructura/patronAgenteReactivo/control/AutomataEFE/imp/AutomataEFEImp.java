/*
    
 */
package icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.imp;

import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.*;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.Operacion;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.TablaEstadosControl;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.XMLParserTablaEstados;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.EjecutorDeAccionesAbstracto;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.ItfUsoAutomataEFE;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.ExcepcionEjecucionAcciones;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

import java.util.Set;

import java.util.logging.Level;
import org.apache.log4j.Logger;


/**
 * Clase que define autmatas de estados finitos sin transiciones vacas
 * escritos en XML
 * 
 * @author Jorge Gonzlez
 * @created 5 de septiembre de 2001
 * @modified 26 de Junio de 2006
 */

public class AutomataEFEImp implements ItfUsoAutomataEFE {

	/**
	 * Indica si se deben mostrar mensajes de depuracin o no
	 * @uml.property  name="dEBUG"
	 */
	public boolean DEBUG = false;

	/**
	 * Controla la profundidad de las trazas
	 * @uml.property  name="traza"
	 */
	protected int traza = 0;

	/**
	 * @uml.property  name="acciones"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private EjecutorDeAccionesAbstracto acciones;

	/**
	 * @uml.property  name="estadoActual"
	 */
	protected  String estadoActual;

	/**
	 * Tabla que representa los estados del autmata
	 * @uml.property  name="theTablaEstadosControl"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private TablaEstadosControl theTablaEstadosControl;
	
	/**
	 * Nombre del agente al que pertenece el control
	 * @uml.property  name="nombreAgente"
	 */
	protected String nombreAgente;

	/**
	 * No se muestra traza
	 */
	public final static int NIVEL_TRAZA_DESACTIVADO = 0;
	/**
	 * Slo se muestra una indicacin cuando existe una transicin de estados
	 */
	public final static int NIVEL_TRAZA_SOLO_TRANSICIONES = 1;
	/**
	 * Se muestra todo lo posible en la traza
	 */
	public final static int NIVEL_TRAZA_TODO = 2;

	/**
	 * @uml.property  name="logger"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private Logger logger = Logger.getRootLogger();
	
	/**
	 * @uml.property  name="trazas"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	protected ItfUsoRecursoTrazas trazas;
	
	/**
	 * @uml.property  name="conjuntoInputs"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
	private Set<String> conjuntoInputs;
	
	/**
	 * @uml.property  name="descripcionAutomata"
	 */
	private String descripcionAutomata;

	/**
	 * Crea un autmata XML
	 * 
	 * @param NombreFicheroDescriptor
	 *            Nombre del fichero que contiene el autmata
	 * @param accionesSem
	 *            Objeto que contiene la implementacin de los mtodos referidos
	 *            en el autmata
	 * @param nivelTraza
	 *            Profundidad de las trazas (usar constantes definidas estticas
	 *            en esta clase)
	 */
	public AutomataEFEImp(String NombreFicheroDescriptor,
			EjecutorDeAccionesAbstracto accionesSem, int nivelTraza, String nombreAgente) throws ExcepcionNoSePudoCrearAutomataEFE {
		
        try{
	    	trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
	    			NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
	    }catch(Exception e){
	    	System.out.println("No se pudo usar el recurso de trazas");
	    }
        
        try {
        XMLParserTablaEstados parser = new XMLParserTablaEstados(NombreFicheroDescriptor);
		// String ruta =
		// tid.tecHabla.agentes.componentes.infraestructura.configuracion.Configuracion.obtenerParametro("RUTA_FICHEROS_DEFINICION_AUTOMATAS");

		theTablaEstadosControl = parser.extraeTablaEstados();
		conjuntoInputs = parser.extraeConjuntoInputs();
		descripcionAutomata = parser.extraerDescripcionTablaEstados();
		acciones = accionesSem;
		this.nombreAgente = nombreAgente;

		
		// colocamos el automata en el estado inicial
		cambiaEstado(theTablaEstadosControl.dameEstadoInicial());

		// actualizamos el DEBUG para las trazas
		if (nivelTraza == 2)
			DEBUG = true;
		else
			DEBUG = false;

		traza = nivelTraza;
		logger.debug("Usando el automata del fichero: "	+ NombreFicheroDescriptor);
		trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
				"Usando el autmata del fichero: "	+ NombreFicheroDescriptor,
				InfoTraza.NivelTraza.debug));
		logger.debug(this.toString());
	}
 catch (ExcepcionNoSePudoCrearAutomataEFE e){
     e.putParteAfectada("XMLParser Tabla de Estados");
     if (trazas != null) {
     trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
				"no se pudo crear el automata EFE porque se produjo un error en "+ e.getParteAfectada()+
                "debido a:" + e.getCausa()+" en el contexto " + e.getContextoExcepcion(),
				InfoTraza.NivelTraza.error));
         }
     throw e;
    }
  }

	/**
	 * Dice si el automata se encuentra en un estado final o no
	 * 
	 * @return est en estado final o no
	 */
        @Override
	public boolean estasEnEstadoFinal() {
		return (theTablaEstadosControl.esEstadoFinal(estadoActual));
	}

        @Override
        public String getEstadoAutomata() {
		return estadoActual;
	}
	/**
	 * Admite un input y lo procesa segul ta tabla de estados, ejecutando la
	 * transicin correspondiente
	 * 
	 * @param input
	 *            Input a procesar
	 */
        @Override
	public synchronized boolean procesaInput(String input, Object[] parametros) {
		// String accion;
		// String siguiente;
		Operacion op;
		// comprobar que es un input reconocido por el estado actual

		if (conjuntoInputs.contains(input)) {
			if (theTablaEstadosControl.esInputValidoDeEstado(estadoActual,
					input)) {
				op = theTablaEstadosControl.dameOperacion(estadoActual, input);
				// ejecutar la accion semantica (posible TO)
				 try {
                logger.debug("Ejecutando accion: " + op.accionSemantica);
				trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
						"Ejecutando accion: " + op.accionSemantica,
						InfoTraza.NivelTraza.debug));
                          String estadoAntesdeTransitar   =   estadoActual ;
                             cambiaEstado(op.estadoSiguiente);
                    acciones.ejecutarAccion(op.accionSemantica, parametros, op.modoTransicionBloqueante);
                             
				// cambiar al siguiente estado
				logger.info("Transicion usando input:" + input + "  :"+ estadoActual + " -> " + op.estadoSiguiente);
				trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
						"Transicion usando input '" + input + "'. ESTADO ACTUAL: "
						+ estadoAntesdeTransitar + " -> " + "ESTADO SIGUIENTE: "+op.estadoSiguiente,
						InfoTraza.NivelTraza.info));
			return true;	
                }
                catch (ExcepcionEjecucionAcciones ex) {
                    java.util.logging.Logger.getLogger(AutomataEFEImp.class.getName()).log(Level.SEVERE, null, ex);
//                   this.estadoActual = this.theTablaEstadosControl.crearEstadoErrorInterno();
                    this.estadoActual = "errorInternoIrrecuperable";

                logger.info("Error al procesar el " + input +  "Ejecutando accion: " + op.accionSemantica + "se transita al estado" + estadoActual);
				trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
						"Error al procesar el " + input +  "Ejecutando accion: " + op.accionSemantica+ "se transita al estado" + estadoActual,
						InfoTraza.NivelTraza.error));
                 
                }

			} else {
				trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
						"AVISO: Input ignorado.El input: "
								+ input
								+ " no pertenece a los inputs validos para el estado actual: "
								+ estadoActual,
						InfoTraza.NivelTraza.info));
				logger
						.info("AVISO: Input ignorado.El input: "
								+ input
								+ " no pertenece a los inputs validos para el estado actual: "
								+ estadoActual);

			}
		} else {
			logger.error("ERROR: Input " + input + " no valido para " + descripcionAutomata );
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"ERROR: Input " + input + " no valido para " + descripcionAutomata,
					InfoTraza.NivelTraza.error));
			logger.error(conjuntoInputs);
		}
             return false;
	}
     
        public boolean procesaInput(Object input, Object ... parametros) {
    
            Object[] valoresParametrosAccion = {};
             int i=0;
            valoresParametrosAccion= new Object[(parametros).length];
            for (Object param: parametros){           
                valoresParametrosAccion[i]=param;
                i++;
            }
          if ( this.procesaInput(input, valoresParametrosAccion))
              return true;
//            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
//					"ERROR: Operacion no soportada por este modelo de Automata- esta operacion  requiere otro tipo de automata de E.F. ",
//					InfoTraza.NivelTraza.error));
            return false;
        }
        @Override
        public  boolean procesaInput(Object input){
            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"ERROR: Operacion no soportada por este modelo de Automata- esta operacion  requiere otro tipo de automata de E.F. ",
					InfoTraza.NivelTraza.error));
    return false;
        }
        @Override
        public synchronized void transita(String input)
	{
		String siguiente;
		// comprobar que es un input reconocido por el estado actual
		if (theTablaEstadosControl.esInputValidoDeEstado(estadoActual, input))
		{
                 Operacion  op = theTablaEstadosControl.dameOperacion(estadoActual, input);
                logger.info("Transicion Directa input:" + input + "  :" + estadoActual + " -> " + op.estadoSiguiente);
                 cambiaEstado(op.estadoSiguiente);
			// cambiar al siguiente estado

		}
		else
		{
			logger.info("AVISO: Input de Transicion directa .El input: " + input + " no pertenece a los inputs validos para el estado actual: " + estadoActual);
			/*trazas.aceptaNuevaTraza(new InfoTraza("AutomataCicloVidaRecurso",
					"AVISO: Input de ciclo de vida ignorado.El input: " + input + " no pertenece a los inputs vlidos para el estado actual: " + estadoActual,
					InfoTraza.NivelTraza.info));*/
		}

	}



	/**
	 * Imprime la tabla de estados y el estado actual del autmata
	 * 
	 * @return Cadena con la informacin
	 */
	public String toString() {
		String dev = theTablaEstadosControl.toString();
		dev += "\nEstado actual= " + estadoActual;
		return dev;
	}

	/**
	 * Devuelve el autmata a su estado inicial
	 */
	public synchronized void volverAEstadoInicial() {
		this.cambiaEstado(this.theTablaEstadosControl.dameEstadoInicial());
	}

	/**
	 * Cambia el estado interno del autmata
	 * 
	 * @param estado
	 *            Estado del automata al que cambiamos
	 */
	public synchronized void cambiaEstado(String estado) {
		estadoActual = estado;
	}

	/**
	 * Programa de pruebas del componente
	 * 
	 * @param args
	 *            The command line arguments
	 */
	/*
	 * public static void main(String[] args) { // ejemplo de uso del automata
	 * de control
	 *  // 1. Creo las acciones semnticas
	 * tid.tecHabla.agentes.componentes.infraestructura.automata.AutomataControl.funciones
	 * fun = new
	 * tid.tecHabla.agentes.componentes.infraestructura.automata.AutomataControl.funciones(); //
	 * 1.5 Creo objeto de acciones semnticas AccionesSemanticas as = new
	 * AccionesSemanticas(fun);
	 *  // 2. Creo el autmata AutomataControl automata = new
	 * AutomataControl("TablaEstadosPruebaDeTablaEstados.xml", as,
	 * tid.tecHabla.agentes.componentes.infraestructura.automata.AutomataControl.NIVEL_TRAZA_TODO);
	 *  // 3. Tengo el autmata disponible para usar
	 * 
	 * automata.toString(); automata.procesaInput("inicia");
	 * automata.procesaInput("inputU");
	 * 
	 * automata.toString(); }
	 */

	/**
	 * Clase de pruebas para el componente, slo para pruebas de funcionamiento
	 * bsicas
	 * 
	 * @author Jorge Gonzlez
	 * @created 26 de septiembre de 2001
	 */
	public static class funciones {
		/**
		 */
		public void accionU() {
			System.out.println(">> Comienzo a ejecutar metodo accion()");
		}

		/**
		 * Mtodo del usuario del componente
		 */
		public void inicial() {
			System.out.println(">> Comienzo a ejecutar metodo inicial()");
			boolean repite = true;
			while (repite) {
			}
		}

	}

	/**
	 * @param logger
	 * @uml.property  name="logger"
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	/**
	 * @return
	 * @uml.property  name="logger"
	 */
	public Logger getLogger() {
		return logger;
	}

}
