/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.entidadesBasicas.comunicacion;

/**
 *
 * @author Francisco J Garijo
 */
import icaro.infraestructura.entidadesBasicas.*;
import java.io.Serializable;
import java.util.ArrayList;
public class MensajeSimple implements Serializable {


	private static final long serialVersionUID = 1624889937599726865L;

	private Object contenido;
	private Object emisor;
	private Object receptor;
        private boolean iscontentColection= false;
        private ArrayList contenidoColeccion= null;

    public MensajeSimple() {

    }

	public MensajeSimple(Object contenido, Object emisor, Object receptor) {
            this.contenido = contenido;
		this.emisor=emisor;
		this.receptor = receptor;
	}
        public MensajeSimple(ArrayList contenido, Object emisor, Object receptor) {
            this.contenidoColeccion = contenido;
		this.emisor=emisor;
		this.receptor = receptor;
                iscontentColection = true;
	}

	public Object getEmisor() {
		return emisor;
	}

        
	public void setEmisor(Object emisor) {
	//	if(emisor instanceof AgentID)
	//		this.emisor = (AgentID) emisor;
	//	else if(emisor!=null)
	//		this.emisor = new AgentID(emisor.toString(),null );
            this.emisor = emisor;
	}

        public void addObjectalContenido(Object elemento){
            if (!iscontentColection){
                if (contenidoColeccion == null)contenidoColeccion = new ArrayList();
                contenidoColeccion.add(elemento);
            }
        }

	public Object getReceptor () {
		return receptor;
	}

	public void setReceptor(Object receptor) {
	//	if(receptor instanceof AgentID)
	//		this.receptor = (AgentID) receptor;
	//	else if(receptor!=null)
		//	this.receptor = new AgentID(receptor.toString(),null );
            this.receptor = receptor;
	}

	public Object getContenido() {
            if(iscontentColection) return contenidoColeccion;
            else return contenido;
	}

	public void setContenido(Object contenido) {
		this.contenido = contenido;
	}
        public boolean isContenidoColection(){
            return iscontentColection;
        }

    public String toString(){
    	return "Emisor: "+emisor+", Receptor: "+receptor;
    }

    public ArrayList getColeccionContenido() {
        if(iscontentColection) return contenidoColeccion;
        else return null;
    }
    public void setColeccionContenido(ArrayList colContenido) {
        this.contenidoColeccion = colContenido;
        iscontentColection=true;
        this.contenido=colContenido;
    }

}