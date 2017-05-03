package icaro.infraestructura.patronAgenteCognitivo.percepcion.imp;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoSimple;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimpleConInfoTemporal;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.percepcion.PercepcionAgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ItfProcesadorObjetivos;

import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

/**
 * Implementation for Cognitive Agent Perception 
 */
public class PercepcionAgenteCognitivoImp extends PercepcionAgenteCognitivo {

	private static final int CAPACIDAD_BUZON_PORDEFECTO = 15;
	private ItfProcesadorItems procesador;
//      private ProcesadorItems procesador ;
	private LinkedBlockingQueue<Object> buzon;
	private EnvioItemsThread envioItems;
	
	private AgenteCognitivo agente;
        private ExecutorService executorService1;
        private Future ejecucionHebra;
	private Logger log = Logger.getLogger(PercepcionAgenteCognitivoImp.class);
	private ItfUsoRecursoTrazas trazas=NombresPredefinidos.RECURSO_TRAZAS_OBJ ;

	public PercepcionAgenteCognitivoImp() {
	// Se crea un objeto vacio. Es necesario definir los parametros para que funcione correctamente	 
                buzon = null;
                this.agente = null;
                this.procesador = null;
//		this.envioItems = new EnvioItemsThread();
	}
	
    public PercepcionAgenteCognitivoImp(AgenteCognitivo agente) {
		buzon = new LinkedBlockingQueue<Object>(CAPACIDAD_BUZON_PORDEFECTO);
		this.agente = agente;
		this.procesador = new ProcesadorItems(agente);
		this.envioItems = new EnvioItemsThread();
		 
	}
        
    public PercepcionAgenteCognitivoImp(int CapacidadBuzon, ProcesadorItems prItems,AgenteCognitivo agente) {
		buzon = new LinkedBlockingQueue<Object>(CapacidadBuzon);
		this.agente = agente;
		this.procesador = prItems;
		this.envioItems = new EnvioItemsThread();
	}
    
	public PercepcionAgenteCognitivoImp(ProcesadorItems procesador) {
		this.procesador = procesador;
		buzon = new LinkedBlockingQueue<Object>(CAPACIDAD_BUZON_PORDEFECTO);
	}
	
    @Override
    public void SetParametrosPercepcionAgenteCognitivoImp(LinkedBlockingQueue<Object> colaEvtosyMsgs, ProcesadorItems prItems,AgenteCognitivo agente) {
		this.buzon=colaEvtosyMsgs;
		this.agente = agente;
		this.procesador = prItems;
		this.envioItems = new EnvioItemsThread();
//                this.envioItems.setName(agente.getIdentAgente()+"envioItemsThread");
	}
    
    public void SetProcesadorEvidencias(ItfProcesadorObjetivos itfProcesEvidencias) {
		if (itfProcesEvidencias !=null){
				 procesador.SetProcesadorEvidencias(itfProcesEvidencias);
					}
                    else {
                    log.debug("Interfaz del Procesador de Objetivos  == NULL!!!!!");
                    trazas.aceptaNuevaTraza(new InfoTraza(this.agente.getIdentAgente(),"Interfaz del Procesador de Objetivos  == NULL!!!!!",InfoTraza.NivelTraza.debug));
                    }
    }

    @Override
	public synchronized void aceptaEvento(EventoSimple evento) {
		trazas.aceptaNuevaTraza(new InfoTraza(this.agente.getIdentAgente(),"Percepcion: Ha llegado un nuevo evento desde "+ evento.getOrigen(),InfoTraza.NivelTraza.debug));
		trazas.aceptaNuevaTrazaEventoRecibido(this.agente.getIdentAgente(), evento);
        buzon.offer(evento);
	}
    
    @Override
    public synchronized void aceptaEvento(EventoRecAgte evento)throws RemoteException {		
        trazas.aceptaNuevaTraza(new InfoTraza(this.agente.getIdentAgente(),"Percepcion: Ha llegado un nuevo evento desde "+ evento.getOrigen(),InfoTraza.NivelTraza.debug));
        trazas.aceptaNuevaTrazaEventoRecibido(this.agente.getIdentAgente(), evento);
		buzon.offer(evento);
	}
	
    @Override
	public synchronized void aceptaMensaje(MensajeSimple mensaje) {
		   //trazas.aceptaNuevaTraza(new InfoTraza(this.agente.getIdentAgente(),"Percepcion: Ha llegado un nuevo mensaje desde "+mensaje.getEmisor(),InfoTraza.NivelTraza.debug));
           //                trazas.aceptaNuevaTrazaMuestraMensaje(new InfoTraza(this.agente.getIdentAgente()," Emisor: "+mensaje.getEmisor()+". Tipo Contenido: "+ mensaje.getContenido().getClass().getSimpleName()
           //                        +". Contenido: "+mensaje.getContenido() ,InfoTraza.NivelTraza.debug));
            if ( mensaje instanceof MensajeSimpleConInfoTemporal){
                MensajeSimpleConInfoTemporal mensajeTemporizado = (MensajeSimpleConInfoTemporal)mensaje;
                mensajeTemporizado.putMomentoRecepcionMsg(System.currentTimeMillis());
                trazas.aceptaNuevaTrazaMensajeRecibido(mensajeTemporizado);
        }
            //trazas.aceptaNuevaTrazaMensajeRecibido(mensaje);
		buzon.offer(mensaje);
	}
	
	private class EnvioItemsThread implements Runnable{

		private static final long TIEMPO_ESPERA = 10;		
		private boolean termina;

		public EnvioItemsThread() {
//			this.setDaemon(true);
			termina = false;
		}
		
                @Override
		public void run() {
			while (!termina) {
				Object item = null;
				try {
					log.debug("Recogiendo item desde el buzon de items de la percepcion...");
					item = buzon.take();
					if (item != null) {
						boolean seguirEnviando = procesador.procesarItem(item);
						if (!seguirEnviando)
							Thread.sleep(TIEMPO_ESPERA);
					}
					else 
						log.debug("Item == NULL!!!!!");
				} catch (InterruptedException e) {
					log.debug("Interrumpida la espera de nuevo item en el buzon de items");
				}
			}
			log.debug("Terminando EnvioItems");
		}

		
		public void termina() {
			this.termina = true;
//			envioItems.interrupt();
		}		
	}

	
        @Override
	public void termina() {
		this.envioItems.termina();
		this.buzon.clear();
		this.procesador.termina();
                ejecucionHebra.cancel(true);   
	}

        @Override
	public void arranca() {
//		this.envioItems.start();
            ExecutorService executor = Executors.newSingleThreadExecutor();
        ejecucionHebra=executor.submit(envioItems);
		this.procesador.arranca();
	}
	
	
	//JM: Nuevo metodo para tener acceso al procesador de items de la percepcion 
	public ItfProcesadorItems getItfProcesadorItems(){
		return this.procesador;
	}
	
	
}
