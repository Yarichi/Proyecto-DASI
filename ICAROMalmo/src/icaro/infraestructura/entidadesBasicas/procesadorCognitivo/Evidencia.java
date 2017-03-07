package icaro.infraestructura.entidadesBasicas.procesadorCognitivo;

import java.util.ArrayList;

public class Evidencia {
// El contenido es una colección de entidades que se obtiene a partir de mensajes o de eventos
// Se puede crear la evidencia a partir de una collección de objetos o con una colección vacía a la que
//  se van añadiendo elementos
	private Object origen;
        private Object creador;
	private ArrayList contenido;
	
	public Evidencia(){
        contenido = new ArrayList();
        origen = null;
        creador = null;
    }
	public Evidencia(Object origen, Object creador, Object contenidoInicial) {
		this.origen = origen;
                this.creador = creador;
                this.contenido = new ArrayList();
		this.contenido.add(contenidoInicial) ;
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
	public ArrayList getContent() {
		return contenido;
	}
	public void setContent(ArrayList contenido) {
		this.contenido = contenido;
	}
    public void addElementToContent(Object contenido) {
		this.contenido.add(contenido);
	}
    public void removeElementToContenido(Object objetoAQuitar) {
		this.contenido.remove(contenido);
	}
}
