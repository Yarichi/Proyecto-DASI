/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.imp;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoInternoAgteReactivo;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoSimple;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ItfControlAgteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.AgenteReactivoAbstracto;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.rmi.RemoteException;
import java.util.logging.Level;



import org.apache.log4j.Logger;


public class ProcesadorItemsPercepReactivo {

//	private static final int CAPACIDAD_BUZON_EVIDENCIAS = 15;
	
	private ItfControlAgteReactivo itfControlReactivo;
	private Object item;
	private AgenteReactivoAbstracto agente;
   //   private InfoControlAgteReactivo infoControlExtraida = null;
        private String nombreAgente = null;
	private Logger log = Logger.getLogger(ProcesadorItemsPercepReactivo.class);
	private ItfUsoRecursoTrazas trazas= NombresPredefinidos.RECURSO_TRAZAS_OBJ;
	public ProcesadorItemsPercepReactivo(AgenteReactivoAbstracto agente, ItfControlAgteReactivo itfControl) {
		this.agente = agente;

//		this.evidencias = new LinkedBlockingQueue<Evidencia>(
//				CAPACIDAD_BUZON_EVIDENCIAS);
		this.itfControlReactivo = itfControl;
                try {
                    nombreAgente = agente.getIdentAgente();
                    } catch (RemoteException ex) {
                    java.util.logging.Logger.getLogger(ProcesadorItemsPercepReactivo.class.getName()).log(Level.SEVERE, null, ex);
                 }
	}
        public ProcesadorItemsPercepReactivo() {
		this.agente = null;
		this.itfControlReactivo = null;
	}
	// De momento filtra los items que no tengan como destinatario este agente.
	private boolean filtrarItem() {
		
		if (item instanceof EventoSimple) {
			EventoSimple evento = (EventoSimple) item;
//			log.warn("El evento" + evento.toString()
//					+ " ha sido filtrado por el agente " + nombreAgente );
//			trazas.aceptaNuevaTraza(new InfoTraza (this.agente.getLocalName(),"Percepcin: El evento" + evento.toString()
//					+ " ha sido filtrado por el agente " + nombreAgente ,InfoTraza.NivelTraza.debug ));
			return true;
		} else if (item instanceof MensajeSimple) {
			MensajeSimple mensaje = (MensajeSimple) item;
			if (!mensaje.getReceptor().equals(nombreAgente)) {
				log.warn("El mensaje" + mensaje.toString()
						+ " no tiene como receptor el agente " + nombreAgente+"\n Destinatario del mensaje: "+mensaje.getReceptor());
				trazas.aceptaNuevaTraza(new InfoTraza (nombreAgente,"Percepcion: El mensaje " + mensaje.toString()
						+ " no tiene como receptor el agente " + nombreAgente+"\n Destinatario del mensaje: "+mensaje.getReceptor(),InfoTraza.NivelTraza.debug ));
				return false;
			} else
				return true;
		} else {
			log.error("Item " + item + " no reconocido");
			trazas.aceptaNuevaTraza(new InfoTraza (  nombreAgente ,"Percepcin: Item "+ item + " no reconocido",InfoTraza.NivelTraza.debug));
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
//Extrae del evento o del mensaje la información que necesita el control para transitar
	private  InfoContEvtMsgAgteReactivo extraerInfoControl() {
		 		
             InfoContEvtMsgAgteReactivo   infoControlExtraida = new InfoContEvtMsgAgteReactivo();
                if (item instanceof EventoSimple) {

		EventoSimple evento = (EventoSimple) item;
                  infoControlExtraida = (InfoContEvtMsgAgteReactivo) evento.getContenido();
//                   infoControlExtraida.setInput(evento.getMsg());
//                   infoControlExtraida.setvaloresParametrosAccion(evento.getMsgElements());

		} else if (item instanceof MensajeSimple) {
 // En el contenido del mensaje se debe poner un objeto del tipo InfoControlAgteReactivo
                    MensajeSimple mensaje = (MensajeSimple) item;
                    infoControlExtraida = (InfoContEvtMsgAgteReactivo) mensaje.getContenido();
		
		}

                else {
			log.error("Item " + item + " no reconocido");
			trazas.aceptaNuevaTraza(new InfoTraza (  nombreAgente ,"Percepcion: Item "+ item + " no reconocido",InfoTraza.NivelTraza.debug));
		}
		return infoControlExtraida;
	}


	public synchronized void procesarItem(Object item) throws RemoteException {
            this.item = item;
//            InfoContEvtMsgAgteReactivo infoExtraida = extraerInfoControl();
            Object infoExtraida = null;
            if (item instanceof EventoInternoAgteReactivo)
                itfControlReactivo.procesarInput(((EventoInternoAgteReactivo)item).input, ((EventoInternoAgteReactivo)item).valoresParametrosAccion);
            else {
                if (item instanceof EventoSimple)
                 infoExtraida = ((EventoSimple)item).getContenido();		
                else if (item instanceof MensajeSimple)
                 infoExtraida = ((MensajeSimple)item).getContenido();
             if (infoExtraida != null)
			itfControlReactivo.procesarInfoControlAgteReactivo(infoExtraida);           
		else{
			log.error("No se ha podido extraer informacion valida del  " + item + " no reconocido");
			trazas.aceptaNuevaTraza(new InfoTraza (this.agente.getIdentAgente(),"Percepcion: Item "+ item + " no se ha podido extraer informacion valida",InfoTraza.NivelTraza.debug));
                    }
                }

	}

	
}
