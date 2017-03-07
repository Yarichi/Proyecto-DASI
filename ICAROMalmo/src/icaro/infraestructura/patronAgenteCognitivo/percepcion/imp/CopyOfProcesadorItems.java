package icaro.infraestructura.patronAgenteCognitivo.percepcion.imp;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoSimple;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.AgenteCognitivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Evidencia;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.InterpreteEventosSimples;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.InterpreteMensajesSimples;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ProcesadorObjetivos;//import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeACLSimple;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.ExtractedInfo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ItfProcesadorObjetivos;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;

import org.apache.log4j.Logger;


public class CopyOfProcesadorItems implements ItfProcesadorItems {
//    public class ProcesadorItems {

	private static final int CAPACIDAD_BUZON_CONT_MESSG = 15;
	private LinkedBlockingQueue<ExtractedInfo> infoExtractedQ;
	
	private EnvioInfoExtractedThread envioEvidencias;

	private ItfProcesadorObjetivos itfProcesadorInfoExtracted;
	private Object item;
	private AgenteCognitivo agente;

	private Logger log = Logger.getLogger(CopyOfProcesadorItems.class);
	private ItfUsoRecursoTrazas trazas= NombresPredefinidos.RECURSO_TRAZAS_OBJ;
    private InterpreteEventosSimples interpreteEvS = null;
    private InterpreteMensajesSimples interpreteMsgS = null;

	public CopyOfProcesadorItems(AgenteCognitivo agente) {
		this.agente = agente;

		this.infoExtractedQ = new LinkedBlockingQueue<ExtractedInfo>(
				CAPACIDAD_BUZON_CONT_MESSG);
		this.itfProcesadorInfoExtracted = agente.getControl();
		this.envioEvidencias = new EnvioInfoExtractedThread();		
	}
	
	
    public CopyOfProcesadorItems() {
		this.agente = null;

		this.infoExtractedQ = null;
		this.itfProcesadorInfoExtracted = null;
		this.envioEvidencias = null;
	}

    
    public void SetParametrosProcesadorItems(AgenteCognitivo agente,LinkedBlockingQueue<ExtractedInfo> infoExtractedQ,ItfProcesadorObjetivos procesadorEvidencias  ) {
		this.agente = agente;
		
		this.infoExtractedQ = infoExtractedQ;
		this.itfProcesadorInfoExtracted = procesadorEvidencias;
				//		this.envioEvidencias = new EnvioEvidenciaThread();
	}
    
    
    public void SetProcesadorEvidencias(ItfProcesadorObjetivos procesadorEvidencias  ) {

		this.itfProcesadorInfoExtracted = procesadorEvidencias;
				//		this.envioEvidencias = new EnvioEvidenciaThread();
	}

    
	// De momento filtra los items que no tengan como destinatario este agente.
	private boolean filtrarItem() {
		String nombreAgente = agente.getIdentAgente();
		if (item instanceof EventoSimple) {
			EventoSimple evento = (EventoSimple) item;
					//			log.warn("El evento" + evento.toString()
					//					+ " ha sido filtrado por el agente " + nombreAgente );
					//			trazas.aceptaNuevaTraza(new InfoTraza (this.agente.getLocalName(),"Percepcion: El evento" + evento.toString()
					//					+ " ha sido filtrado por el agente " + nombreAgente ,InfoTraza.NivelTraza.debug ));
			return true;
		} else if (item instanceof MensajeSimple) {
			       MensajeSimple mensaje = (MensajeSimple) item;
			       if (!mensaje.getReceptor().equals(nombreAgente)) {
				        log.warn("El mensaje" + mensaje.toString()
						         + " no tiene como receptor el agente " + nombreAgente+"\n Destinatario del mensaje: "+mensaje.getReceptor());
				        trazas.aceptaNuevaTraza(new InfoTraza (this.agente.getIdentAgente(),"Percepcion: El mensaje " + mensaje.toString()
						         + " no tiene como receptor el agente " + nombreAgente+"\n Destinatario del mensaje: "+mensaje.getReceptor(),InfoTraza.NivelTraza.debug ));
				        return false;
			       } 
			       else
				        return true;
		} else {
			  log.error("Item " + item + " no reconocido");
			  trazas.aceptaNuevaTraza(new InfoTraza (this.agente.getIdentAgente(),"Percepci�n: Item "+ item + " no reconocido",InfoTraza.NivelTraza.debug));
			  return false;
		}		
	}

	
	//De momento no hace nada
	private void decodificarItem() {
		/*
		if (item instanceof EventoInput) {
			EventoInput evento = (EventoInput) item;
		} else if (item instanceof MensajeAgente) {
			MensajeAgente mensaje = (MensajeAgente) item;
		} else
			log.error("Item " + item + " no reconocido");
			*/
	}
	
	
    //Genera una evidencia a partir de un evento o un mensaje
	private ExtractedInfo extractInfo(Object item) {
		ExtractedInfo infoExtracted = null;
		if (item instanceof EventoSimple) {
			EventoSimple evento = (EventoSimple) item;
					//			evidencia = new Evidence();
					//			log.debug("Evento null?: "+ evento==null);
					//			evidencia.setOrigen(evento.getOrigen());
					//TODO Cambiar
					//			evidencia.setContent(evento);
					//         evidencia.setContent(evento.getMsgElements()[0]);
					//             evidencia.setContent(evento.getMsgElements());
            if (interpreteEvS == null)
                    interpreteEvS =  new InterpreteEventosSimples(agente.getIdentAgente());
            infoExtracted =  interpreteEvS.generarExtractedInfo(evento);
		} else if (item instanceof MensajeACLSimple) {
			       MensajeACLSimple mensaje = (MensajeACLSimple) item;
                   if (interpreteMsgS == null)
                       interpreteMsgS = new InterpreteMensajesSimples(agente.getIdentAgente());
                   
		}else if (item instanceof MensajeSimple) {
			       MensajeSimple mensaje = (MensajeSimple) item;
                   if (interpreteMsgS == null )
                       interpreteMsgS = new InterpreteMensajesSimples(agente.getIdentAgente());
                   infoExtracted =  interpreteMsgS.extractInfo(mensaje);
		}
        else {
		    	log.error("Item " + item + " no reconocido");
			    trazas.aceptaNuevaTraza(new InfoTraza (this.agente.getIdentAgente(),"Percepcion: Item "+ item + " no reconocido",InfoTraza.NivelTraza.debug));
		}
		return infoExtracted;
	}

	
	public boolean procesarItem(Object item) {
        this.item = item;
        if (filtrarItem()) {
			  decodificarItem(); //actualmente no hace nada
		      ExtractedInfo infoExtracted = extractInfo(item);
            
		      if (!encolarInfoExtracted(infoExtracted))
			      return false;
        }
		return true;            
	}

	
//	private ExtractedInfo extractInfo(Object item) {
//		this.item = item;
//		if (filtrarItem()) {
//			decodificarItem();
//			return generarEvidencia();
//		} else
//			return null;
//	}

	private boolean encolarInfoExtracted(ExtractedInfo infoExtract) {
		if (infoExtract != null)
			if (!infoExtractedQ.offer(infoExtract))
				return false;
			else
				return true;
		else
			return true;
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////// CLASE EnvioInfoExtractedThread  ////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////
	
    //Envia evidencias por la interfaz del control.
	private class EnvioInfoExtractedThread extends Thread {
		private static final long TIEMPO_ESPERA = 10;
		private boolean termina;

		public EnvioInfoExtractedThread() {
			setDaemon(true);
			termina = false;
		}

		public void run() {
                    while (!termina) {
                        ExtractedInfo infoExtr = null;
				        try {
					         log.debug("Recogiendo item desde el buzon de items de la percepcion...");
					         infoExtr = infoExtractedQ.take();
					         if (infoExtr != null) {
						         boolean seguirEnviando = itfProcesadorInfoExtracted.procesarExtractedInfo(infoExtr);
                                 trazas.aceptaNuevaTraza(new InfoTraza (agente.getIdentAgente(),"Procesador de Items: El mensaje " + infoExtr.toString()
						                     + " se envia al motor " +agente.getIdentAgente() ,InfoTraza.NivelTraza.debug ));
						         if (!seguirEnviando)
							         this.sleep(TIEMPO_ESPERA);
					         }
					         else
						        log.debug("Item == NULL!!!!!");
				        } catch (InterruptedException e) {
					            log.debug("Interrumpida la espera de nuevo item en el buzon de items");
				        }
			        }
                            //			log.debug("Terminando EnvioItems");
		}//fin run

    }//fin clase EnvioInfoExtractedThread

	
	public void termina() {				
		this.infoExtractedQ.clear();
        envioEvidencias.interrupt();
        		//        try {
        		//            //           envioEvidencias.interrupt();
        		//            envioEvidencias.join();
        		//        } catch (InterruptedException ex) {
        		//            java.util.logging.Logger.getLogger(ProcesadorItems.class.getName()).log(Level.SEVERE, null, ex);
        		//        }
	}
	
	public void arranca() {
        this.envioEvidencias = new EnvioInfoExtractedThread();
        envioEvidencias.start();
	}
	
	
	//JM: Nuevos metodos para parar y volver a arrancar el hilo
	public void pararProcesoEnvioInfoExtracted(){        
		envioEvidencias.suspend();
	}
	
	
	public void continuarProcesoEnvioInfoExtracted(){
		envioEvidencias.resume();
        
	}
	

}
