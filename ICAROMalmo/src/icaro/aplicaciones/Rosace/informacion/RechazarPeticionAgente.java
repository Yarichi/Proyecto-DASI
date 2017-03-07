/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.Rosace.informacion;

import java.io.Serializable;

/**
 *
 * @author Francisco J Garijo
 */
public class RechazarPeticionAgente  implements Serializable {
    // Mensajes validos en las propuestas : Ver vocabulario de la aplicacion
    public String identAgente;
    public PeticionAgente peticionRechazada;
    public Object justificacion;
    public String identObjectRefPeticion;
    
    public RechazarPeticionAgente(String identAgenteEmisor, PeticionAgente petcnRechazada,Object justificn) {
        identAgente= identAgenteEmisor;
        peticionRechazada =petcnRechazada;
        justificacion = justificn;
    }
    public PeticionAgente   getpeticionRechazada(){
        return peticionRechazada;
    }
    public void   setpeticionRechazada(PeticionAgente petcnRechazada){
         peticionRechazada = petcnRechazada;
    }
    
    public String   getIdentAgente(){
        return identAgente;
    }
    
    public void   setJustificacion(Object contJustificacion){
        justificacion =contJustificacion;
    }

    public Object   getJustificacion(){
        return justificacion;
    }
    
    @Override
    public String toString(){
        if ( justificacion == null )
            return "Agente Emisor :"+identAgente+ " PeticionRechazada :+" + peticionRechazada+ "  Justificacion: null "+"\n ";
        else 
            return "Agente Emisor :"+identAgente+ "PeticionRechazada :+" + peticionRechazada+ "  Justificacion: "+justificacion.toString() +"\n ";
    }
    
}
