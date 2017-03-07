/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.entidadesBasicas.procesadorCognitivo;

/**
 * Esta clase crea objetos que contienen el mensaje de los eventos simples
 * @author Francisco J Garijo
 */
public class EventMessageAtr {

private Object eventMessagecontent;

     public EventMessageAtr(Object contenido) {
        // Crea cola circular
        this.eventMessagecontent = contenido ;

		}
     public void setContent(Object contenido) {

        this.eventMessagecontent = contenido ;

        }
}
