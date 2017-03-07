package icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE;

import icaro.infraestructura.patronAgenteReactivo.control.acciones.ExcepcionEjecucionAcciones;



/**
 *  
 *
 *@author     
 *@created    3 de Diciembre de 2007
 */
// public abstract class EjecutroDeAccionesAbstracto extends java.lang.Thread implements ItfAccionesSemanticas {
public abstract class EjecutorDeAccionesAbstracto implements Runnable {
	public EjecutorDeAccionesAbstracto(String string) {
		
	}

	public EjecutorDeAccionesAbstracto() {}
        public abstract void ejecutarAccion(String accion, Object[] parametros, boolean modoBloqueante) throws ExcepcionEjecucionAcciones ;
        ;
	
}
