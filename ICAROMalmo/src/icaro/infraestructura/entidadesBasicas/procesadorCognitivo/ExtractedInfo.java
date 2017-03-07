package icaro.infraestructura.entidadesBasicas.procesadorCognitivo;

import java.util.ArrayList;

public class ExtractedInfo {
	
    // El contenido puede ser un objeto o  una colección de entidades que se obtiene a partir de mensajes o de eventos
    // Se puede crear la evidencia a partir de una collección de objetos o con una colección vacía a la que
    //  se van añadiendo elementos
	private Object origen;      //JM: Identificador de la entidad que creo (y lo envio) el mensaje/evento
    private Object creador;     //JM: Identificdor  del agente que recibe el mensaje/evento (es decir el destinatario del mensaje/evento)
	private Object contenido;   //JM: Contenido del mensaje/evento que fue creado en el origen
    private Boolean esElContenidoUnaColeccion = false;
   
	
	public ExtractedInfo(){
        contenido = null;
        origen = null;
        creador = null;
    }
	
	public ExtractedInfo(Object origen, Object creador, Object contenidoInicial) {
		this.origen = origen;
        this.creador = creador;
        this.contenido = contenidoInicial;
                     //		this.contenido.add(contenidoInicial) ;
	}
	
	public Object getOrigen() {
		return origen;
	}
	
	public void setOrigen(Object origen) {
		this.origen = origen;
	}
	
    public Object getCreador() {
		return creador;
	}
    
	public void setCreador(Object creador) {
		this.creador = creador;
	}
	
	public Object getContenido() {
		return this.contenido;
	}
    
	public Boolean isContentACollection() {

                return esElContenidoUnaColeccion;
	}
	
	public void setContenido(Object contenido) {
		this.contenido = contenido;
	}
    
	public void setContentCollection(ArrayList contenido) {
		this.contenido = contenido;
        esElContenidoUnaColeccion = true;
	}
    
	public void addElementToContent(Object elemento) {
        if (esElContenidoUnaColeccion)
             this.contenido = ((ArrayList)this.contenido).add(elemento);
        else 
        	 this.contenido = elemento ;
	}
	
    public void removeElementFromContenido(Object elemento) {
	    if (esElContenidoUnaColeccion)
             this.contenido = ((ArrayList)this.contenido).remove(elemento);
        else 
        	 this.contenido = null ;	        
	}
    
    /**
     *  JM: Cadena de texto para la depuración
     */
        @Override
    public String toString() {
    	return "ExtractedInfo:"+"clase -> "+" origen->" + this.getOrigen() + " ; creador->" + this.getCreador() + " ; contenido->"+contenido.getClass().getSimpleName() + this.contenido; 
    }
    
   
}
