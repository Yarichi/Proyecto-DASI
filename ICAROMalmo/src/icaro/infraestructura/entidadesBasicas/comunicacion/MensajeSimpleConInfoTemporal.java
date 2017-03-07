/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.infraestructura.entidadesBasicas.comunicacion;
import java.io.Serializable;
/**
 *
 * @author FGarijo
 */
public class MensajeSimpleConInfoTemporal extends MensajeSimple implements Serializable{
    private long momentoCreacionMsg;
    private long momentoRecepcionMsg = 0;

    MensajeSimpleConInfoTemporal(Object contenido, Object emisor, Object receptor) {
        super.setContenido(contenido) ;
        super.setEmisor(emisor);
        super.setReceptor(receptor);
        momentoCreacionMsg = System.currentTimeMillis();
    }
    
    public void MensajeSimpleConInfoTemporal(){
        momentoCreacionMsg = System.currentTimeMillis();
    }
    public long getMomentoCreacionMsg (){
        return momentoCreacionMsg;
    }
    public void putMomentoCreacionMsg (long momentRecepcion){
        momentoRecepcionMsg = momentRecepcion;
    }
     public void putMomentoRecepcionMsg (long momentRecepcion){
        momentoRecepcionMsg = momentRecepcion;
    }
    public long getMomentoRecepcionMsg (){
        return momentoCreacionMsg;
    }
    
}
