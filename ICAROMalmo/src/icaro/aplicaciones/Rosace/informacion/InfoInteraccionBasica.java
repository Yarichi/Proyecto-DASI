/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.Rosace.informacion;

/**
 *
 * @author FGarijo
 * Esta clase debe servir para envio de items de informacion - representados mediante identificadores - de un tema
 * o asunto en que un agente quiera informar a otro agente.
 * Tambien puede utilizarse  como  base para crear otros items mas complicados como propuestas o peticiones, mediante herencia
 */
public class InfoInteraccionBasica {
    private String agteIniciadorId ;
    private String refIteracion ;
    private String tema ;
    public InfoInteraccionBasica ( ){       
    }
    public InfoInteraccionBasica (String agentId, String temaId, String iteracionId ){
        agteIniciadorId =agentId;
        refIteracion = iteracionId;
        tema =temaId;
    }
     public String getAgteIniciadorId(){
         return agteIniciadorId ;
    }
    public void setAgteIniciadorId(String agentId){
        agteIniciadorId =agentId;
    }
    public String getTema(){
         return tema ;
    }
    public void setTema(String identTema){
        tema =identTema;
    }
    public String getrefIteracion(){
         return refIteracion ;
    }
    public void setrefIteracion(String iterId){
        refIteracion =iterId;
    }
}
