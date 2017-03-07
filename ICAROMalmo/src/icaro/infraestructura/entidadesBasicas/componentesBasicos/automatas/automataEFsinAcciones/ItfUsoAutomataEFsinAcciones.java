package icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFsinAcciones;
/**
 * Interfaz de uso de un autmata de ciclo de vida de un Recurso
 *@author     lvaro Rodrguez
 *@created    1 de Febrero de 2007
 */
public interface ItfUsoAutomataEFsinAcciones {
	/**
	 *  Dice si el automata se encuentra en un estado final o no
	 *
	 *@return    est en estado final o no
	 */
	public boolean esEstadoFinal();


	/**
	 *  Admite un input y lo procesa segul ta tabla de estados, ejecutando la
	 *  transicin correspondiente
	 *
	 *@param  input  Input a procesar
	 */
	public void transita(String input);

	/**
	 * 	Dice si el recurso est en estado activo, es decir, que puede
	 *  ejecutar mtodos
	 * 
	 * @return est en estado activo o no
	 */
	public boolean estadoActivo();
	
	/**
	 *  Imprime la tabla de estados y el estado actual del autmata
	 *
	 *@return    Cadena con la informacin
	 */
	public String toString();

    public String estadoActual();


	/**
	 *  Devuelve el autmata a su estado inicial
	 */
	public void volverAEstadoInicial();
}
