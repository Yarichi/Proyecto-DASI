/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.imp;

import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.imp.AutomataEFEImp;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ProcesadorInfoReactivoAbstracto;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.AgenteReactivoAbstracto;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfConsumidorPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 *
 * @author Francisco J Garijo
 */
public class ProcesadorInfoReactivoImp extends ProcesadorInfoReactivoAbstracto implements Serializable{

	/**
	 * @uml.property  name="dEBUG"
	 */
	private boolean DEBUG = false;
	/**
	 * autmata que describe el control
	 * @uml.property  name="automataControl"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private AutomataEFEImp automataControl;

//    private AccionesSemanticasAgenteReactivo accionesSemanticasAgenteCreado;
	/**
	 * estado interno del componente control
	 * @uml.property  name="estado"
	 */
//	private int estado = InterfazGestion.ESTADO_CREADO;


	/**
	 * Nombre del componente a efectos de traza
	 * @uml.property  name="nombre"
	 */
//	private String nombre;
//        private  AgenteReactivoAbstracto agente;
	/**
	 * @uml.property  name="percepcionConsumidor"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
//	private ItfConsumidorPercepcion percepcionConsumidor;
	/**
	 * @uml.property  name="percepcionProductor"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
//	private ItfProductorPercepcion percepcionProductor;


	/**
	 *  Constructor
	 *
	 *@param  automata          Autmata con los estados que rigen el control
	 *@param  percConsumidor    Interfaz de consumo de la percepcin
	 *@param  percProductor     Interfaz de produccin de la percepcin
	 *@param  nombreDelControl  Nombre que tomar en componente control
	 */
	public ProcesadorInfoReactivoImp( AutomataEFEImp automata,AccionesSemanticasAgenteReactivo accionesSemanticasEspecificas,
			 AgenteReactivoAbstracto implItfsagente) throws RemoteException
	{
		super("Agente reactivo "+implItfsagente.getIdentAgente());
                automataControl = automata;
                accionesSemanticasAgenteCreado = accionesSemanticasEspecificas;
             //   percepcionConsumidor = percConsumidor;
		//percepcionProductor = percProductor;
		agente = implItfsagente;
                this.arranca();
	}


	/**
	 *  Inicia los recursos internos
	 */
//	public void arranca()
//	{
//		if (DEBUG)
//			System.out.println(nombre + ": arranca()");
//		estado = InterfazGestion.ESTADO_ARRANCANDO;
//		
//	}
	/*
	public void arrancaConEvento()
	{
		if (DEBUG)
			System.out.println(nombre + ": arranca()");
		estado = InterfazGestion.ESTADO_ARRANCANDO;
		try
		{
			this.start();
			//start llama a run()
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void arrancaConInput(String nombreInput)
	{
		if (DEBUG)
			System.out.println(nombre + ": arranca()");
		estado = InterfazGestion.ESTADO_ARRANCANDO;
		try
		{
			this.start();
			//start llama a run()
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

*/
	/**
	 *  Descripcin del mtodo
	 */
//	public void continua()
//	{
//		throw new java.lang.UnsupportedOperationException("El metodo continua() an no est implementado.");
//	}



	/**
	 *  Consulta el estado interno del control
	 *
	 *@return    estado del control
	 */
//	public int obtenerEstado()
//	{
//		if (DEBUG)
//			System.out.println(nombre + ": obtenerEstado()");
//		return estado;
//	}


	/**
	 *  Description of the Method
	 */
//	public void para()
//	{
//		throw new java.lang.UnsupportedOperationException("El metodo para() an no est implementado.");
//	}


	/**
	 *  Mira si el buzon tiene algun evento, lo trata y vuelve a dormir
	 */


	/**
	 *  Elimina los recursos internos usados por el control
	 */
//	public synchronized void termina()
//	{
//		if (DEBUG)
//		{
//			System.out.println(nombre + ":terminando ...");
//		}
//		estado = InterfazGestion.ESTADO_TERMINANDO;
//		// vamos a terminar usando la percepcin para salir de los posibles consume()
//	//	percepcionProductor.produceParaConsumirInmediatamente(new ItemControl(ItemControl.OPERACION_TERMINAR));
//                automataControl.transita("terminar");
//	}


	/**
	 *  Mtodo auxiliar para tratar eventos de control
	 *
	 *@param  ec  Evanto de control a tratar
	 */


	/**
	 *  Mtodo auxiliar que trata los eventos de input
	 *
	 *@param  ei  Evento de input a tratar


	private void tratarEventoInput(EventoSimple ei)
	{
    // Se extrae del evento el mensaje (msg) y los objetos enviados como parametros (msgElements)
    // Se envian al automata para que transite y ejecute las acciones correspondientes
    // El control vuelve si la ejecucion de las acciones termina. Si la accion es no bloqueante siempre vuelve

                String inputExtraido = ei.getMsg().trim();
                automataControl.procesaInput(inputExtraido, ei.getMsgElements());

		if (automataControl.esEstadoFinal())
		{
			this.termina();
		}
            }

        private void tratarMensajeSimple(MensajeSimple msg)
	{
    // Se extrae del evento el mensaje (msg) y los objetos enviados como parametros (msgElements)
    // Se envian al automata para que transite y ejecute las acciones correspondientes
    // El control vuelve si la ejecucion de las acciones termina. Si la accion es no bloqueante siempre vuelve
            ArrayList contenidoMsg = (ArrayList) msg.getContenido();
            String inputExtraido = contenidoMsg.get(0).toString().trim();
    // Los parametros deben ir en el resto de los elementos del array
            if  (contenidoMsg.size() > 1) {
                contenidoMsg.remove(0);
            }
            automataControl.procesaInput(inputExtraido,contenidoMsg.toArray());

		if (automataControl.esEstadoFinal())
		{
			this.termina();
		}
            }
    */
        @Override
        public synchronized void procesarInfoControlAgteReactivo (Object infoParaProcesar  ) {
      if(this.estado == InterfazGestion.ESTADO_ACTIVO)
          if( infoParaProcesar instanceof InfoContEvtMsgAgteReactivo){
             InfoContEvtMsgAgteReactivo infoParaAutomata = (InfoContEvtMsgAgteReactivo) infoParaProcesar;
            automataControl.procesaInput(infoParaAutomata.getInput(),infoParaAutomata.getvaloresParametrosAccion());
          }else{
              System.out.println(identAgte + ": El input debe ser de  clase InfoContEvtMsgAgteReactivo  y el objeto es clase" + infoParaProcesar.getClass()
                      + " Cambiar el contenido del evento");
//              automataControl.procesaInput(infoParaProcesar);
          }
  }
        @Override
        public synchronized String getEstadoControlAgenteReactivo ( ){

        return automataControl.getEstadoAutomata();
}
    public void setDebug(boolean d) {
        this.DEBUG = d;
    }

    public boolean getDebug() {
        return this.DEBUG;
    }

@Override
    public void procesarInput (Object input, Object ...paramsAccion  ){
    if (paramsAccion == null)automataControl.procesaInput(input);
        automataControl.procesaInput(input, paramsAccion);
}

    public synchronized int getEstado() {
        return estado;
    }

    /**
	 * @param e
	 * @uml.property  name="estado"
	 */
    public synchronized void setEstado(int e) {
        this.estado = e;
    }

    /**
	 * @return
	 * @uml.property  name="control"
	 */
        @Override
        public synchronized  void setGestorAReportar(ItfUsoAgenteReactivo itfUsoGestorAReportar) {

       accionesSemanticasAgenteCreado.setItfUsoGestorAReportar(itfUsoGestorAReportar);

        }

 public   void inicializarInfoGestorAcciones(String identAgte,ItfProductorPercepcion itfEvtosInternos ){
     if(accionesSemanticasAgenteCreado !=null){
         accionesSemanticasAgenteCreado.inicializarAcciones(identAgte, this,itfEvtosInternos);
     }
 }
 
  
        @Override
    public String toString() {
        return identAgte;
    }

    //@Override
    /**
     *  Establece el gestor a reportar
     *  @param nombreGestor nombre del gestor a reportar
     *  @param listaEventos lista de posibles eventos que le puede enviar.
     *
     *  El gestionador obtendr las interfaces del gestor a partir del repositorio de interfaces y podr validar la informacin.
     *
     */
}
