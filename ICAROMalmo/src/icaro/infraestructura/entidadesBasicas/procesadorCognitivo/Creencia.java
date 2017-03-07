package icaro.infraestructura.entidadesBasicas.procesadorCognitivo;

/**
 * Represents a believe of the cognitive agent mental state
 * @author Carlos Celorrio
 *
 */
public class Creencia {
	
	private Object contenido;
	private Object emisor;
	private Object receptor;
	public Creencia(){
        }
        public Creencia(Object emisor, Object contenido,Object receptor ) {
		this.emisor = emisor;
                this.receptor = receptor;
                this.contenido = contenido;
	}
	public Object getEmisor() {
		return emisor;
	}
	public void setEmisor(Object emisor) {
		this.emisor = emisor;
	}
	public Object getReceptor() {
		return receptor;
	}
	public void setReceptor(Object receptor) {
		this.receptor = receptor;
	}
	public Object getContenido() {
		return contenido;
	}
	public void setContenido(Object content) {
		this.contenido = content;
	}
	public String toString(){
            return "Emisor: "+emisor+ " Receptor: "+receptor+ " Contenido:"+contenido;
        }
}