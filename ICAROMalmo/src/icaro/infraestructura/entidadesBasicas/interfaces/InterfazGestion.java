/*
 * 
 */
package icaro.infraestructura.entidadesBasicas.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 
 *@created    3 de diciembre de 2007
 */

public interface InterfazGestion extends Remote {
	/**
	 *  Se ha detectado un error irrecuperable en el recurso
	 */
	public final static int ESTADO_ERRONEO_IRRECUPERABLE = -1;
	/**
	 *  Se ha detectado un error que puede subsanarse en el recurso
	 */
	public final static int ESTADO_ERRONEO_RECUPERABLE = -2;
	/**
	 *  El recurso se ha instanciado, pero no se ha arrancado todava
	 */
	public final static int ESTADO_CREADO = 0;
	/**
	 *  El recurso est arrancando pero no ha terminado todava
	 */
	public final static int ESTADO_ARRANCANDO = 1;
	/**
	 *  El recurso ha arrancado correctamente y est en espera de peticiones
	 */
	public final static int ESTADO_ACTIVO = 2;
	/**
	 *  El recurso est detenido temporalmente
	 */
	public final static int ESTADO_PARADO = 3;
	/**
	 *  El recurso est terminando su estructura
	 */
	public final static int ESTADO_TERMINANDO = 4;
	/**
	 *  El recurso ha terminado completamente
	 */
	public final static int ESTADO_TERMINADO = 5;
	/**
	 *  El recurso est en un estado indeterminado o especial
	 */
	public final static int ESTADO_OTRO = 10;


	/**
	 *  Inicializa y prepara el elemento para recibir rdenes o informacin
	 *
	 *@exception  Exception  
	 */
	public void arranca()throws RemoteException;


	//public void arrancaConEvento();
	
	//public void arrancaConInput(String nombreInput);
	/**
	 *  Detiene el servicio del elemento momentneamente Esta operacion es OPCIONAL
	 *
	 *@exception  Exception  
	 */
	public void para()throws RemoteException;


	/**
	 *  Acaba con el uso del elemento y destruye los recursos que estuviesen
	 *  ligados a l
	 *
	 *@exception  Exception
	 */
	public void termina()throws RemoteException;


	/**
	 *  Reanuda la disponibilidad del elemento si estaba parado, si no lo estaba no
	 *  hace nada. Esta operacion es OPCIONAL
	 *
	 *@exception  Exception
	 */
	public void continua()throws RemoteException;


	/**
	 *  Comprueba el estado actual del elemento
	 *
	 *@return                      Estado en el que se encuentra el elemento
	 *@exception  Exception
	 */
	public int obtenerEstado()throws RemoteException;
	
	
	
}
