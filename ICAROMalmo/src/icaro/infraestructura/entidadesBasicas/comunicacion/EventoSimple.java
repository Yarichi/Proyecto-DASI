/*
 * Copyright ISSIS 2009
 * 
 */

package icaro.infraestructura.entidadesBasicas.comunicacion;
import java.io.Serializable;

/**
 *
 * @author Francisco J Garijo
 */
public class EventoSimple implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * @uml.property  name="msg" El mensaje que debe interpretar el receptor del evento
     * En el caso de un agente reactivo este msg debe coincidir con el input del autómata
	 */
//	protected String msg;
	/**
	 * @uml.property  name="msgElements " Son los elementos de información que acompañan
     * al mensaje. Normalmente son objetos.
	 */
//	protected Object[] msgElements;
	/**
	 * @uml.property  name="justificacion" Es la justificación del emisor del evento
	 */
//	protected String justificacion;
	/**
	 * @uml.property  name="origen" Identificador del emisor del evento
	 */
	protected String origen;
        protected Object contenido;

	public EventoSimple( ) {
		this.contenido  = null;
		this.origen = null;
	}
//        public EventoSimple(String msg, String origen ) {
//
//		this.msg = msg;
//		this.msgElements = new Object[0];
//		this.justificacion = null;
//		this.origen = origen;
//	}
        public EventoSimple(String origen, Object content ) {
		this.contenido  = content;
		this.origen = origen;
	}

//	public EventoSimple(String msg, Object msgElements, String origen) {
//
//		this.msg = msg;
//		this.msgElements = new Object[]{msgElements};
//		this.justificacion = null;
//		this.origen = origen;
//
//	}

//	public EventoSimple(String msg, Object[] msgElements, String origen) {
//		this.msg = msg;
//		this.msgElements = msgElements;
//		this.justificacion = null;
//		this.origen = origen;
//	}

    public Object getContenido() {
		return contenido;
	}
     public void  setContenido(Object contenido ) {
		this. contenido =contenido ;
	}
    /**
	 * @return  Returns the msg.
	 * @uml.property  name="msg"
	 */
//	public String getMsg() {
//		return msg;
//	}


	/**
	 * @param msg  The msg to set.
	 * @uml.property  name="msg"
	 */
//	public void setMsg(String msg) {
//		this.msg = msg;
//	}

	/**
	 * @return  Returns the justificacion.
	 * @uml.property  name="justificacion"
//	 */
//	public String getJustificacion() {
//		return justificacion;
//	}

	/**
	 * @param justificacion  The justificacion to set.
	 * @uml.property  name="justificacion"
	 */
//	public void setJustificacion(String justificacion) {
//		this.justificacion = justificacion;
//	}

	/**
	 * @return  Returns the msgElements.
	 * @uml.property  name="msgElements"
	 */
//	public Object[] getMsgElements() {
//		return msgElements;
//	}

	/**
	 * @param msgElements  The msgElements to set.
	 * @uml.property  name="msgElements"
	 */
//	public void setMsgElements(Object[] msgElements) {
//		this.msgElements = msgElements;
//	}

	/**
	 * @return
	 * @uml.property  name="origen"
	 */
	public String getOrigen(){
		if (origen!=null)

			return origen;
		else
			return "no especificado";
	}

	/**
	 * @param origen
	 * @uml.property  name="origen"
	 */
	public void setOrigen(String origen){
		this.origen = origen;
	}


//
//
//	public String toString() {
//        return this.msg;
//    }



}
