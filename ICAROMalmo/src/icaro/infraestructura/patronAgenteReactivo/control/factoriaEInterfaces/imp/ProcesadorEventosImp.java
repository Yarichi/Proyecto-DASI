/*
 *  Copyright 2001 Telefnica I+D. All rights reserved
 */
package icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.imp;

import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.ItemControl;
//import icaro.infraestructura.entidadesBasicas.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoSimple;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.imp.AutomataEFEImp;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.ConversionDeEventosyMsgsSimplesEnInputs;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ProcesadorInfoReactivoAbstracto;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfConsumidorPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;
import java.util.ArrayList;



/**
 *  Clase que implementa un control mediante el uso del componente percepcin y
 *  el componente automata. En concreto el control trata eventos que llegan a
 *  travs de la percepcin y los procesa siguiendo un autmata descrito en XML
 *
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public class ProcesadorEventosImp extends ProcesadorInfoReactivoAbstracto{

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
        
    private AccionesSemanticasAgenteReactivo accionesSemanticasAgenteCreado;
	/**
	 * estado interno del componente control
	 * @uml.property  name="estado"
	 */
	private int estado = InterfazGestion.ESTADO_CREADO;


	/**
	 * Nombre del componente a efectos de traza
	 * @uml.property  name="nombre"
	 */
	private String nombre;

	/**
	 * @uml.property  name="percepcionConsumidor"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private ItfConsumidorPercepcion percepcionConsumidor;
	/**
	 * @uml.property  name="percepcionProductor"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private ItfProductorPercepcion percepcionProductor;


	/**
	 *  Constructor
	 *
	 *@param  automata          Autmata con los estados que rigen el control
	 *@param  percConsumidor    Interfaz de consumo de la percepcin
	 *@param  percProductor     Interfaz de produccin de la percepcin
	 *@param  nombreDelControl  Nombre que tomar en componente control
	 */
	public ProcesadorEventosImp( AutomataEFEImp automata,AccionesSemanticasAgenteReactivo accionesSemanticasEspecificas,
			ItfProductorPercepcion percProductor, ItfConsumidorPercepcion percConsumidor, String nombreDelControl)
	{
		super("Agente reactivo "+nombreDelControl);
                automataControl = automata;
                accionesSemanticasAgenteCreado = accionesSemanticasEspecificas;	
                percepcionConsumidor = percConsumidor;
		percepcionProductor = percProductor;
		nombre = nombreDelControl;
	}


	/**
	 *  Inicia los recursos internos
	 */
	public void arranca()
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
	 *  Descripcion del metodo
	 */
	public void continua()
	{
		throw new java.lang.UnsupportedOperationException("El metodo continua() an no est implementado.");
	}



	/**
	 *  Consulta el estado interno del control
	 *
	 *@return    estado del control
	 */
	public int obtenerEstado()
	{
		if (DEBUG)
			System.out.println(nombre + ": obtenerEstado()");
		return estado;
	}


	/**
	 *  Description of the Method
	 */
	public void para()
	{
		throw new java.lang.UnsupportedOperationException("El mtodo para() an no est implementado.");
	}


	/**
	 *  Mira si el buzon tiene algun evento, lo trata y vuelve a dormir
	 */
	public void run()
	{
		estado = InterfazGestion.ESTADO_ACTIVO;
		while (!(estado == InterfazGestion.ESTADO_TERMINANDO))
		{
			// habra que procurar no consumir recursos de la mquina cuando se est parado
//			if (!(estado == this.ESTADO_PARADO))
//			{
			int milis = 3600000;
			// 1 hora
			Object obj = null;
			try
			{
				// sacar el evento
				// controlamos tiempo para informar de inanicin del gestor
				obj = percepcionConsumidor.consumeConTimeout(milis);
			}
			catch (Exception e)
			{
				if (DEBUG)
					System.out.println(nombre + ": No ha llegado evento al control del Gestor en " + milis + " milisegundos");
			}

			if (obj != null)
			{
				// identificar el evento
				if (obj instanceof ItemControl)
				{
					// ejecutamos y cambiamos al siguiente estado
					if (DEBUG)
						System.out.println(nombre + ":Percibido Evento de control");
					tratarEventoControl((ItemControl) obj);
				}
				else if (obj instanceof EventoSimple)
				{
					if (DEBUG)
						System.out.println(nombre + ":Percibido Evento de input");
//					tratarEventoInput((EventoRecAgte) obj);
                                    tratarEventoInput((EventoSimple) obj);
				}
				else if (obj instanceof MensajeSimple)
				{
					if (DEBUG)
						System.out.println(nombre + ":Percibido Evento de input");
//					tratarEventoInput((EventoRecAgte) obj);
                                    tratarMensajeSimple((MensajeSimple) obj);
				}


                                {
					if (DEBUG)
						System.out.println("ERROR: " + nombre + ": Ha llegado al 'Control' del 'Gestor' un 'Evento' no reconocido");
				}
			}
			// if esEstadoFinal {itfGestion.termina();}
			yield();
		}
//	yield();
    }

@Override
    public void procesarInput (Object input, Object ...paramsAccion  ){
    if (paramsAccion == null)automataControl.procesaInput(input);
        automataControl.procesaInput(input, paramsAccion);
}
        @Override
        public   void inicializarInfoGestorAcciones(String identAgte,ItfProductorPercepcion itfEvtosInternos ){
     if(accionesSemanticasAgenteCreado !=null){
         accionesSemanticasAgenteCreado.inicializarAcciones(identAgte, this,itfEvtosInternos);
     }
 }
	/**
	 *  Elimina los recursos internos usados por el control
	 */
        @Override
	public void termina()
	{
		if (DEBUG)
		{
			System.out.println(nombre + ":terminando ...");
		}
		estado = InterfazGestion.ESTADO_TERMINADO;
		// vamos a terminar usando la percepcin para salir de los posibles consume()
//		percepcionProductor.produceParaConsumirInmediatamente(new ItemControl(ItemControl.OPERACION_TERMINAR));
//                automataControl.transita("termina");
                
	}


	/**
	 *  Mtodo auxiliar para tratar eventos de control
	 *
	 *@param  ec  Evanto de control a tratar
	 */
	private void tratarEventoControl(ItemControl ec)
	{
		switch (ec.operacion)
		{
						case ItemControl.OPERACION_TERMINAR:
							estado = InterfazGestion.ESTADO_TERMINANDO;
							// paranoia
							break;
						case ItemControl.OPERACION_TIMEOUT:
							if (DEBUG)
								System.out.println(nombre + "Alerta: Ha llegado un timeout de inanicion: ");
							break;
						default:
							if (DEBUG)
								System.out.println("ERROR: " + nombre + " :Evento de control desconocido");
							break;
		}
	}


	/**
	 *  Mtodo auxiliar que trata los eventos de input
	 *
	 *@param  ei  Evento de input a tratar
	 */
	private void tratarEventoInput(EventoSimple ei)
	{
    // Se extrae del evento el mensaje (msg) y los objetos enviados como parametros (msgElements)
    // Se envian al automata para que transite y ejecute las acciones correspondientes
    // El control vuelve si la ejecucion de las acciones termina. Si la accion es no bloqueante siempre vuelve
                InfoContEvtMsgAgteReactivo infoControl = (InfoContEvtMsgAgteReactivo)ei.getContenido();
                String inputExtraido = infoControl.getInput().trim();
              if ( estado == InterfazGestion.ESTADO_ACTIVO) 
                  automataControl.procesaInput(inputExtraido, infoControl.getvaloresParametrosAccion());

//		if (automataControl.estasEnEstadoFinal())
//		{
//			this.termina();
//		}
            } 

        private void tratarMensajeSimple(MensajeSimple msg)
	{
    // Se extrae del evento el mensaje (msg) y los objetos enviados como parametros (msgElements)
    // Se envian al automata para que transite y ejecute las acciones correspondientes
    // El control vuelve si la ejecucion de las acciones termina. Si la accion es no bloqueante siempre vuelve
//            ArrayList contenidoMsg = (ArrayList) msg.getContenido();
//            String inputExtraido = contenidoMsg.get(0).toString().trim();
            InfoContMsgAgteReactivo contMsg = (InfoContMsgAgteReactivo) msg.getContenido();
            String inputExtraido = contMsg.getInput();

    // Los parametros deben ir en el resto de los elementos del array
//            if  (contMsg.getvaloresParametrosAccion().length > 1) {
//                contenidoMsg.remove(0);
//            }
//            automataControl.procesaInput(inputExtraido,contenidoMsg.toArray());
             if ( estado == InterfazGestion.ESTADO_ACTIVO) 
                 automataControl.procesaInput(inputExtraido,contMsg.getvaloresParametrosAccion());
//		if (automataControl.estasEnEstadoFinal())
//		{
//			this.termina();
//		}
            }
  public void procesarInputyParams (InfoContEvtMsgAgteReactivo infoParaProcesar  ) {
// Ponemos la operacion para evitar errores pero no se usa con este procesador
  }
//  public void procesarInputyParams (Object input,Object[] params  ) {
//      if ( estado == InterfazGestion.ESTADO_ACTIVO)
//          automataControl.procesaInput (input,params);
//  }

    public void setDebug(boolean d) {
        this.DEBUG = d;
    }

    public boolean getDebug() {
        return this.DEBUG;
    }

    /**
	 * @return
	 * @uml.property  name="estado"
	 */
    public int getEstado() {
        return estado;
    }

    /**
	 * @param e
	 * @uml.property  name="estado"
	 */
    public void setEstado(int e) {
        this.estado = e;
    }

    /**
	 * @return
	 * @uml.property  name="control"
	 */
public  void setGestorAReportar(ItfUsoAgenteReactivo itfUsoGestorAReportar) {
   
       accionesSemanticasAgenteCreado.setItfUsoGestorAReportar(itfUsoGestorAReportar);
        
        }   

    @Override
    public String toString() {
        return nombre;
    }
    @Override
        public String getEstadoControlAgenteReactivo ( ){
    return "OP No implementada";
}
@Override
        public synchronized void procesarInfoControlAgteReactivo (Object infoParaProcesar  ) {
      if(this.estado == InterfazGestion.ESTADO_ACTIVO)
          if( infoParaProcesar instanceof InfoContEvtMsgAgteReactivo){
             InfoContEvtMsgAgteReactivo infoParaAutomata = (InfoContEvtMsgAgteReactivo) infoParaProcesar;
            automataControl.procesaInput(infoParaAutomata.getInput(),infoParaAutomata.getvaloresParametrosAccion());
          }else{
              System.out.println(nombre + ": El input debe ser de  clase InfoContEvtMsgAgteReactivo  y el objeto es clase" + infoParaProcesar.getClass()
                      + " Cambiar el contenido del evento");
//              automataControl.procesaInput(infoParaProcesar);
          }
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
