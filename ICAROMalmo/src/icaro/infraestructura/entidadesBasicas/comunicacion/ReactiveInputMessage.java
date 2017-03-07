package icaro.infraestructura.entidadesBasicas.comunicacion;

import icaro.infraestructura.entidadesBasicas.comunicacion.SimpleMessage;


/**
 *  
 *
 *@author	Carlos Celorrio
 *@created    11 de septiembre de 2001
 *@modified	  6 de enero de 2009
 *@version    2.0
 */

public class ReactiveInputMessage extends SimpleMessage {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8126226537539821846L;
	
	private Object[] params;

	public ReactiveInputMessage(String input, Object origen, Object destino) {
		super( input,  origen, destino);		
        
	}
	
	public ReactiveInputMessage(String input, Object msgParametro, Object origen, Object destino) {
		super( input, origen, destino );
		
		this.params = new Object[1];
		this.params[0] = msgParametro;
	}
	
	public ReactiveInputMessage(String input, Object[] msgParametros, Object origen, Object destino) {
		super( input, origen, destino );
		
		this.params = msgParametros;
	}

	public String getInput() {
		return this.getContenido().toString();
	}
	
	/**
	 * 
	 * @return
	 */
	public Object[] getMsgParams() {
		return params;
	}
}