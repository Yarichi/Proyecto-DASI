package icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces;

import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.imp.AutomataEFEImp;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.AgenteReactivoAbstracto;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfConsumidorPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;

/**
 * 
 *@author    F Garijo
 */

 public abstract class ProcesadorInfoReactivoAbstracto extends Thread implements ItfControlAgteReactivo {
// public abstract class ProcesadorEventosAbstracto extends java.lang.Thread {
     public boolean DEBUG = false;
	/**
	 * autmata que describe el control
	 * @uml.property  name="automataControl"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
//	public AutomataEFEImp automataControl;

    public AccionesSemanticasAgenteReactivo accionesSemanticasAgenteCreado;
	/**
	 * estado interno del componente control
	 * @uml.property  name="estado"
	 */
	public int estado = InterfazGestion.ESTADO_CREADO;

	/**
	 * Nombre del componente a efectos de traza
	 * @uml.property  name="nombre"
	 */
	public String identAgte;
        public  AgenteReactivoAbstracto agente;
        protected ItfUsoAgenteReactivo itfUsoGestorAreportar;
	/**
	 * @uml.property  name="percepcionConsumidor"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	public ItfConsumidorPercepcion percepcionConsumidor;
	/**
	 * @uml.property  name="percepcionProductor"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	public ItfProductorPercepcion percepcionProductor;


	/**
	 *  Constructor
	 *
	 *@param  automata          Autmata con los estados que rigen el control
	 *@param  percConsumidor    Interfaz de consumo de la percepcin
	 *@param  percProductor     Interfaz de produccin de la percepcin
	 *@param  nombreDelControl  Nombre que tomar en componente control
	 */

	public ProcesadorInfoReactivoAbstracto(String string) {
		super(string);
	}
     @Override
        public void arranca()
	{
		if (DEBUG)
			System.out.println(identAgte + ": arranca()");
		if(estado ==InterfazGestion.ESTADO_CREADO ){
                    
                    estado = InterfazGestion.ESTADO_ACTIVO;
                }
		
	}
     /**
	 *  Descripcin del mtodo
	 */
     @Override
	public void continua()
	{
//		throw new java.lang.UnsupportedOperationException("El metodo continua() an no est implementado.");
                estado = InterfazGestion.ESTADO_ACTIVO;
	}



	/**
	 *  Consulta el estado interno del control
	 *
	 *@return    estado del control
	 */
     @Override
	public synchronized int obtenerEstado()
	{
		if (DEBUG)
			System.out.println(identAgte + ": obtenerEstado()");
		return estado;
	}


	/**
	 *  Description of the Method
	 */
     @Override
	public synchronized void para()
	{
//		throw new java.lang.UnsupportedOperationException("El metodo para() an no est implementado.");
                estado = InterfazGestion.ESTADO_PARADO;
	}


	/**
	 *  Mira si el buzon tiene algun evento, lo trata y vuelve a dormir
	 */


	/**
	 *  Elimina los recursos internos usados por el control
	 */
     @Override
	public synchronized void termina()
	{
		if (DEBUG)
		{
			System.out.println(identAgte + ":terminando ...");
		}
		estado = InterfazGestion.ESTADO_TERMINADO;
		// vamos a terminar usando la percepcin para salir de los posibles consume()
	//	percepcionProductor.produceParaConsumirInmediatamente(new ItemControl(ItemControl.OPERACION_TERMINAR));
//                automataControl.transita("terminar");
	}
     @Override
     public void setGestorAReportar(ItfUsoAgenteReactivo itfUsoGestor){
         itfUsoGestorAreportar = itfUsoGestor;
     }
     
     @Override
     public ItfUsoAgenteReactivo getGestorAReportar(){
         return itfUsoGestorAreportar;
     }
     public  abstract void inicializarInfoGestorAcciones(String identAgte,ItfProductorPercepcion itfEvtosInternos );
//     public synchronized void procesarInfoControlAgteReactivo (Object infoParaProcesar  ) {
//      if(this.estado == InterfazGestion.ESTADO_ACTIVO)
//          if( infoParaProcesar instanceof InfoContEvtMsgAgteReactivo){
//             InfoContEvtMsgAgteReactivo infoParaAutomata = (InfoContEvtMsgAgteReactivo) infoParaProcesar;
//            automataControl.procesaInput(infoParaAutomata.getInput(),infoParaAutomata.getvaloresParametrosAccion());
//          }else automataControl.procesaInput(infoParaProcesar);
//  }
}