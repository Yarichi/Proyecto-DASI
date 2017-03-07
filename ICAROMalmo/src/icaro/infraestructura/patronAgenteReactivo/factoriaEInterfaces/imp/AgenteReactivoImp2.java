package icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFsinAcciones.ItfUsoAutomataEFsinAcciones;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestionPercepcion;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.imp.EjecutorDeAccionesImp;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ItfControlAgteReactivo;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.ItemControl;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.AgenteReactivoAbstracto;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfConsumidorPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
import java.io.Serializable;

import java.rmi.RemoteException;
import java.util.logging.Level;

import org.apache.log4j.Logger;

import icaro.infraestructura.entidadesBasicas.comunicacion.EventoSimple;;

/**
 *  Clase que implementa la base del componente agente reactivo
 *
 *@author     F Garijo
 *@created    mayo  2010
 */
public class AgenteReactivoImp2 extends AgenteReactivoAbstracto implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
	 * Control del agente
	 * @uml.property  name="control"
	 * @uml.associationEnd
	 */
    protected ItfControlAgteReactivo itfControlAgteReactivo;
    /**
	 * @uml.property  name="itfGesControl"
	 * @uml.associationEnd
	 */
    protected ItfUsoAutomataEFsinAcciones itfAutomataCicloVida;
    protected InterfazGestion itfGesControl; //Control
    /**
	 * Percepcion del agente
	 * @uml.property  name="itfConsumidorPercepcion"
	 * @uml.associationEnd
	 */
    protected InterfazGestionPercepcion itfGestionPercepcion;
    /**
	 * @uml.property  name="itfProductorPercepcion"
	 * @uml.associationEnd
	 */
    protected ItfProductorPercepcion itfProductorPercepcion;
    /**
	 * Nombre del agente a efectos de traza
	 * @uml.property  name="nombre"
	 */
    protected String nombre;
    protected String estadoAgente;
    /**
	 * Estado del agente reactivo
	 * @uml.property  name="estado"
	 */
    protected int estado = InterfazGestion.ESTADO_OTRO;
    /**
	 * Acciones semnticas del agente reactivo
	 * @uml.property  name="accionesSemanticas"
	 * @uml.associationEnd
	 */
    protected EjecutorDeAccionesImp accionesSemanticas;
    /**
	 * @uml.property  name="dEBUG"
	 */
    private boolean DEBUG = false;
    /**
	 * Conocimiento del agente reactivo
	 * @uml.property  name="itfUsoGestorAReportar"
	 * @uml.associationEnd
	 */
    protected ItfUsoAgenteReactivo itfUsoGestorAReportar;
    /**
	 * @uml.property  name="logger"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
    /**
	 * @uml.property  name="trazas"
	 * @uml.associationEnd
	 */
    protected ItfUsoRecursoTrazas trazas;

    /**
     *  Crea un instancia del agente reactivo
     *
     *@param  objetoContenedorAcciones   Objeto que contiene la implementacin de todos los mtodos referidos en la tabla
     *@param  nombreFicheroTablaEstados  Nombre Fichero que contene la tabla de estados XML
     *@param  nombreDelAgente            Nombre del agente
     *@todo propagar el nivel de traza hasta el constructor para poder ser modificado sin tocar el componente
     */
    public AgenteReactivoImp2(String nombreAgente, ItfControlAgteReactivo itfControlAgte, ItfProductorPercepcion itfProdPercepcion,InterfazGestionPercepcion itfConsumPercepcion) throws RemoteException {
        super();
        new ConfiguracionTrazas(logger);
        trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        nombre = nombreAgente;
        itfControlAgteReactivo = itfControlAgte;
        itfProductorPercepcion = itfProdPercepcion;
        itfGestionPercepcion = itfConsumPercepcion;

    }

     public AgenteReactivoImp2(String nombreAgente, ItfUsoAutomataEFsinAcciones itfAutomata,ItfControlAgteReactivo itfControlAgte, ItfProductorPercepcion itfProdPercepcion,InterfazGestionPercepcion itfConsumPercepcion) throws RemoteException {
        super();
        new ConfiguracionTrazas(logger);
        trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        nombre = nombreAgente;
        itfAutomataCicloVida = itfAutomata;
        itfControlAgteReactivo = itfControlAgte;

        itfProductorPercepcion = itfProdPercepcion;
        itfGestionPercepcion = itfConsumPercepcion;

    }

    /**
     * Constructor sin parmetros: HAY QUE UTILIZAR INMEDIATAMENTE DESPUS EL MTODO
     * setParametrosAgReactivo
     * @throws RemoteException
     */
    public AgenteReactivoImp2(String nombreAgente) throws RemoteException {
        super();
        new ConfiguracionTrazas(logger);
        trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        this.nombre = nombreAgente;
    }

    @Override
    public void setParametrosLoggerAgReactivo(String archivoLog, String nivelLog) {
        new ConfiguracionTrazas(logger, archivoLog, nivelLog);
    }

    @Override
    public void setComponentesInternos(String nombreAgente, ItfUsoAutomataEFsinAcciones itfAutomata,ItfControlAgteReactivo itfControlAgte, ItfProductorPercepcion itfProdPercepcion,InterfazGestionPercepcion itfgestionPercepcion) {
        nombre = nombreAgente;
        itfAutomataCicloVida = itfAutomata;
        itfControlAgteReactivo = itfControlAgte;
        itfProductorPercepcion = itfProdPercepcion;
        itfGestionPercepcion = itfgestionPercepcion;

    }
    /**
     *  Inicializa los recursos necesarios para la ejecucin del componente agente reactivo
     *
     */
    @Override
    public synchronized void arranca() {
        try {
            //arranca el control
            logger.debug(nombre + ": arranca()");
            trazas.aceptaNuevaTraza(new InfoTraza(  nombre, NombresPredefinidos.TipoEntidad.Reactivo, ": arranca()", InfoTraza.NivelTraza.debug));
            itfAutomataCicloVida.transita("arrancar");
            //     itfAutomataCicloVida.transita("ok");
            itfGestionPercepcion.arranca();
            int estadoControl = itfControlAgteReactivo.obtenerEstado();
            if (estadoControl != itfControlAgteReactivo.ESTADO_ERRONEO_IRRECUPERABLE) {
                itfAutomataCicloVida.transita(NombresPredefinidos.INPUT_OK);
                estadoAgente = itfAutomataCicloVida.estadoActual();
                itfControlAgteReactivo.arranca();
                itfControlAgteReactivo.procesarInput(NombresPredefinidos.INPUT_COMENZAR, (Object)null);
            } else {
                itfAutomataCicloVida.transita(NombresPredefinidos.INPUT_ERROR);
                estadoAgente = itfAutomataCicloVida.estadoActual();
            }
        } catch (RemoteException ex) {
            java.util.logging.Logger.getLogger(AgenteReactivoImp2.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

   
    @Override
    public synchronized void continua() {
    }

    /**
     * Devuelve el estado interno del agente
     *@return     Description of the Returned Value
     */
    @Override
    public int obtenerEstado() {

		estadoAgente = this.itfAutomataCicloVida.estadoActual();
                logger.debug(nombre + ": monitoriza()Mi estado es "+estadoAgente );
                trazas.aceptaNuevaTraza(new InfoTraza(nombre, ": monitoriza()monitoriza()Mi estado es "+estadoAgente,InfoTraza.NivelTraza.debug));

		if (estadoAgente.equals(NombresPredefinidos.ESTADO_ACTIVO)
				|| estadoAgente.equals(NombresPredefinidos.ESTADO_ARRANCADO))
			return InterfazGestion.ESTADO_ACTIVO;
		if (estadoAgente.equals(NombresPredefinidos.ESTADO_TERMINADO))
			return InterfazGestion.ESTADO_TERMINADO;
		if (estadoAgente.equals(NombresPredefinidos.ESTADO_TERMINANDO))
			return InterfazGestion.ESTADO_TERMINANDO;

		if (estadoAgente.equals(NombresPredefinidos.ESTADO_CREADO))
			return InterfazGestion.ESTADO_CREADO;
		if (estadoAgente.equals(NombresPredefinidos.ESTADO_ERROR))
			return InterfazGestion.ESTADO_ERRONEO_IRRECUPERABLE;
		if (estadoAgente.equals(NombresPredefinidos.ESTADO_FALLO_TEMPORAL))
			return InterfazGestion.ESTADO_ERRONEO_RECUPERABLE;
		if (estadoAgente.equals(NombresPredefinidos.ESTADO_PARADO))
			return InterfazGestion.ESTADO_PARADO;
		return InterfazGestion.ESTADO_OTRO;

    }



    /**
     *  Detiene la ejecucin actual, descarta nuevos eventos.
     *
     */
    @Override
    public synchronized void para() {
    }

    /**
     *  Pruebas en desarrollo
     */
    public void printOutThreadGroups() {
        //create a thread
        //Thread t = new Thread();
        //get the current thread
        Thread tt = Thread.currentThread();
        //get the thread group of current thread
        ThreadGroup tg = tt.getThreadGroup();
        ThreadGroup topMost = null;
        //find the topmost thread group
        while (tg != null) {
            topMost = tg;
            tg = tg.getParent();
        }
        //get an estimate of active thread groups under topMost
        int groupCount = topMost.activeGroupCount();
        //get an enumeration of thread groups under topMost
        ThreadGroup[] tgArray = new ThreadGroup[groupCount];
        topMost.enumerate(tgArray, true);
        //for every thread group under topMost, print out the active threads
        System.out.println("Top Most ThreadGroup: \"" + topMost.getName() +
                "\" has the following Thread Groups:");
        System.out.println("**********************");
        for (int i = 0; i < tgArray.length; i++) {
            ThreadGroup aThreadGroup = tgArray[i];
            int count = aThreadGroup.activeCount();
            //get an enumeration of threads in aThreadGroup
            Thread[] tArray = new Thread[count];
            aThreadGroup.enumerate(tArray, true);
            System.out.println("Thread Group: \"" + aThreadGroup.getName() +
                    "\" has the following threads: ");
            String indent = " ";
            for (int j = 0; j < tArray.length; j++) {
                if (tArray[j] != null) {
                    Thread aThread = tArray[j];
                    System.out.println(indent + " \"" + aThread.getName() + "\"");
                    indent += " ";
                }
            }
            System.out.println("**********************");
            tArray = null;
        }
        tgArray = null;
    //t = null;
    }

    /**
     *  Libera los recursos que se ocuparon en la creacin
     *
     */
    @Override
    public synchronized void termina() {
        try {
            logger.debug(nombre + ": termina()");
            trazas.aceptaNuevaTraza(new InfoTraza(nombre, ": termina()", InfoTraza.NivelTraza.debug));
            //		itfGestionControlAgteReactivo.termina();
            itfAutomataCicloVida.transita("terminar");
            itfAutomataCicloVida.transita("ok");
            itfControlAgteReactivo.termina();
            itfGestionPercepcion.termina();
            //        this.aceptaEvento(new EventoRecAgte("termina", nombre, nombre));
        } catch (RemoteException ex) {
            java.util.logging.Logger.getLogger(AgenteReactivoImp2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Despacha el evento para tratarlo de forma diferente segn su tipo
     *@param  ev  Description of Parameter
     */
    @Override
    public synchronized void aceptaEvento(EventoRecAgte evento) {
        logger.debug(nombre + ": Ha llegado un evento por el interfaz de Uso:" + evento.toString());
        trazas.aceptaNuevaTraza(new InfoTraza(nombre,
                ": Ha llegado un nuevo evento desde "+ evento.getOrigen(),
                InfoTraza.NivelTraza.debug));
        trazas.aceptaNuevaTrazaEventoRecibido(nombre, evento);
        if ((estadoAgente.equals(NombresPredefinidos.ESTADO_ACTIVO))){
                    itfProductorPercepcion.produce(evento);
                }else
                    {
                  trazas.aceptaNuevaTraza(new InfoTraza(nombre,
                ": El agente se encuentra en el estado: " + estadoAgente + " y no puede procesar el evento recibido",
                InfoTraza.NivelTraza.debug));
                }
       }
    //JM: El siguiente metodo lo he anadido para poder enviar eventos simples
    @Override
    public synchronized void aceptaEvento(EventoSimple evento) {
        logger.debug(nombre + ": Ha llegado un evento por el interfaz de Uso:" + evento.toString());
        trazas.aceptaNuevaTraza(new InfoTraza(nombre,
                ": Ha llegado un nuevo evento desde "+ evento.getOrigen(),
                InfoTraza.NivelTraza.debug));
        trazas.aceptaNuevaTrazaEventoRecibido(this.nombre, evento);
        if ((estadoAgente.equals(NombresPredefinidos.ESTADO_ACTIVO))){
                    itfProductorPercepcion.produce(evento);
                }else
                    {
                  trazas.aceptaNuevaTraza(new InfoTraza(nombre,
                ": El agente se encuentra en el estado: " + estadoAgente + " y no puede procesar el evento recibido",
                InfoTraza.NivelTraza.debug));
                }
       }

    @Override
    public void aceptaMensaje(MensajeSimple mensaje){
        logger.debug(nombre + ": Ha llegado un mensaje por el interfaz de Uso:" + mensaje.toString());
        trazas.aceptaNuevaTraza(new InfoTraza(nombre,
                ": Ha llegado un mensaje por el interfaz de Uso:" + mensaje.toString(),
                InfoTraza.NivelTraza.debug));
         trazas.aceptaNuevaTrazaMensajeRecibido( mensaje);
        if ((estadoAgente.equals(NombresPredefinidos.ESTADO_ACTIVO))){
                    itfProductorPercepcion.produce(mensaje);
                }else
                    {
                  trazas.aceptaNuevaTraza(new InfoTraza(nombre,
                ": El agente se encuentra en el estado: " + estadoAgente + " y no puede procesar el mensaje recibido",
                InfoTraza.NivelTraza.debug));
                }
   }
    public void procesaEventoInterno(EventoSimple evtoInterno){
        logger.debug(nombre + ": Se procesa un evento interno:" + evtoInterno.toString());
        trazas.aceptaNuevaTraza(new InfoTraza(nombre,
                ": Se procesa un evento interno:" + evtoInterno.toString(),InfoTraza.NivelTraza.debug));
         trazas.aceptaNuevaTrazaEventoRecibido(nombre, evtoInterno);
        if ((estadoAgente.equals(NombresPredefinidos.ESTADO_ACTIVO))){
                    itfProductorPercepcion.produceParaConsumirInmediatamente(evtoInterno);
                }else
                    {
                  trazas.aceptaNuevaTraza(new InfoTraza(nombre,
                ": El agente se encuentra en el estado: " + estadoAgente + " y no puede procesar el evento interno recibido",
                InfoTraza.NivelTraza.debug));
                }
   }
    protected void clasificarEvento(Object ev) {
        if (ev instanceof EventoRecAgte) {
            itfProductorPercepcion.produce(ev);
        } else if (ev instanceof ItemControl) {
            itfProductorPercepcion.produceParaConsumirInmediatamente(ev);
        } else {
            logger.debug("ERROR:" + nombre + ": Clasificar evento: el evento no es de una clase conocida por el Agente Reactivo");
            trazas.aceptaNuevaTraza(new InfoTraza(nombre,
                    "ERROR: Clasificar evento: el evento no es de una clase conocida por el Agente Reactivo",
                    InfoTraza.NivelTraza.error));
        }
    }

    @Override
    public void setDebug(boolean d) {
        this.DEBUG = d;
    }

    @Override
    public boolean getDebug() {
        return this.DEBUG;
    }

    /**
	 * @return
	 * @uml.property  name="estado"
	 */
    @Override
    public int getEstado() {
        return estado;
    }

    /**
	 * @param e
	 * @uml.property  name="estado"
	 */
    @Override
    public void setEstado(int e) {
        this.estado = e;
    }

    /**
	 * @return
	 * @uml.property  name="control"
	 */
   /* public ControlReactivoImp getControl() {
     return this.control;
    }*/

    @Override
    public AgenteReactivoAbstracto getAgente() {
        return this;
    }

    @Override
//    public String toString() {
//        return nombre;
//    }

    //@Override
    /**
     *  Establece el gestor a reportar
     *  @param nombreGestor nombre del gestor a reportar
     *  @param listaEventos lista de posibles eventos que le puede enviar.
     *
     *  El gestionador obtendr las interfaces del gestor a partir del repositorio de interfaces y podr validar la informacin.
     *
     */
    public void setGestorAReportar(String nombreGestor) {
        try {
            this.logger.info("Estableciendo gestor a reportar. Agente: " + this.nombre + ". Gestor: " + nombreGestor);
            trazas.aceptaNuevaTraza(new InfoTraza(nombre,
                    "Estableciendo gestor a reportar. Agente: " + this.nombre + ". Gestor: " + nombreGestor,
                    InfoTraza.NivelTraza.info));
            // listaEventos: an no se va a utilizar.
            itfUsoGestorAReportar = (ItfUsoAgenteReactivo) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + nombreGestor);
            itfControlAgteReactivo.setGestorAReportar(itfUsoGestorAReportar);
     //    this.control.getAccionesSemanticas(). ;
     //      accionesSemanticas.setItfUsoGestorAReportar(itfUsoGestorAReportar);
        } catch (Exception e) {
            logger.error("Error al asignar el gestor a reportar", e);
            trazas.aceptaNuevaTraza(new InfoTraza(nombre,
                    "Error al asignar el gestor a reportar",
                    InfoTraza.NivelTraza.error));
        }

    }
    @Override
    public String getIdentAgente()throws java.rmi.RemoteException {
		return this.nombre;
}
    @Override
public ItfControlAgteReactivo getItfControl(){
		return this.itfControlAgteReactivo;
}
    @Override
    public  ItfProductorPercepcion getItfProductorPercepcion(){
    return itfProductorPercepcion;
}

public AgenteReactivoAbstracto getPatron() {
		return this;
}
    @Override
    public AgenteReactivoAbstracto AgenteReactivoImplementacion(ItfControlAgteReactivo itfGestionControlAgteReactivo, ItfProductorPercepcion itfProductorPercepcion, ItfConsumidorPercepcion itfConsumidorPercepcion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
