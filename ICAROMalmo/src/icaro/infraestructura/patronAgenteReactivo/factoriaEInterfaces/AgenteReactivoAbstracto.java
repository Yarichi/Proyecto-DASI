package icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces;


import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFsinAcciones.ItfUsoAutomataEFsinAcciones;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestionPercepcion;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ItfControlAgteReactivo;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfConsumidorPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;
import java.rmi.server.UnicastRemoteObject;
/**
 * @author      Felipe Polo
 * @created     30 de noviembre de 2007
 */

public abstract class AgenteReactivoAbstracto extends UnicastRemoteObject implements ItfGestionAgenteReactivo,ItfUsoAgenteReactivo {

	public AgenteReactivoAbstracto () throws java.rmi.RemoteException { }
	public abstract void setParametrosLoggerAgReactivo(String archivoLog, String nivelLog);
	
	public abstract void setDebug (boolean d);
	public abstract boolean getDebug();
	/**
	 * @uml.property  name="patron"
	 * @uml.associationEnd  readOnly="true"
	 */
	public abstract AgenteReactivoAbstracto getAgente();
	public abstract int getEstado();
        public abstract ItfControlAgteReactivo getItfControl();
	public abstract void setEstado(int e);
        public abstract void setComponentesInternos(String nombreAgente, ItfUsoAutomataEFsinAcciones itfAutomata,ItfControlAgteReactivo itfControlAgte, ItfProductorPercepcion itfProdPercepcion,InterfazGestionPercepcion itfgestionPercepcion);
        @Override
        public abstract  String getIdentAgente()throws java.rmi.RemoteException;
        @Override
        public abstract void aceptaMensaje(MensajeSimple mensaje);
        public abstract ItfProductorPercepcion getItfProductorPercepcion();
	/**
	 * @uml.property  name="control"
	 * @uml.associationEnd  readOnly="true"
	 */
//	public abstract ControlReactivoImp getControl();
	public abstract AgenteReactivoAbstracto AgenteReactivoImplementacion(ItfControlAgteReactivo itfGestionControlAgteReactivo, ItfProductorPercepcion itfProductorPercepcion,ItfConsumidorPercepcion itfConsumidorPercepcion) ;
}