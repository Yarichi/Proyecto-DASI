package icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE;

import org.apache.log4j.Logger;

/**
 * Interfaz de uso de un autmata
 * @author      F Garijo
 * @created     3 de Mayo de 2014
 */

public interface ItfUsoAutomataEFE {
	/**
	 *  Dice si el automata se encuentra en un estado final o no
	 *
	 *@return    est en estado final o no
	 */
	public boolean estasEnEstadoFinal();

        public String getEstadoAutomata();


	/**
	 *  Admite un input y lo procesa segul ta tabla de estados, ejecutando la
	 *  transicin correspondiente
	 *
	 *@param  input  Input a procesar
	 */
	public  boolean procesaInput(String input, Object[] parametros);
        public  boolean procesaInput(Object input);
//        public  boolean procesaInputObj(Object input, Object[] parametros);
//        public  boolean procesaInput(Object input, Object... parametros);

	/**
	 *  Imprime la tabla de estados y el estado actual del autmata
	 *
	 *@return    Cadena con la informacin
	 */
        @Override
	public String toString();


	/**
	 *  Devuelve el autmata a su estado inicial
	 */
	public void volverAEstadoInicial();
	
	public void cambiaEstado(String estado);

        public void transita(String input);
	// El automata transita de acuerdo con el estado actual y con el input enviado
        // No se ejecutan acciones aunque estuvieran definidas en el automata
	/**
	 * @param logger
	 * @uml.property  name="logger"
	 */
	public void setLogger(Logger logger);
		
	/**
	 * @uml.property  name="logger"
	 * @uml.associationEnd  
	 */
	public Logger getLogger();
	
}
