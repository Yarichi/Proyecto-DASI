/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.Rosace.informacion;


/**
 *
 * @author FGarijo
 */
public class InfoEstadoAgente {
    private String identEstado;
    private String identAgte;
    private String refIteracion;
    public InfoEstadoAgente (String agentId,String estadoId,String iteracionRef ){
        identAgte=agentId;
        identEstado=estadoId;
        refIteracion=iteracionRef;
}
    public void setidentEstado ( String estadoId){
        identEstado=estadoId;
    }
    public String getidentEstado ( ){
        return identEstado;
    }
    public String getidentAgte(){
         return identAgte ;
    }
    public void setidentAgte(String agentId){
        identAgte =agentId;
    }
   
    public String getrefIteracion(){
         return refIteracion ;
    }
    public void setrefIteracion(String iterId){
        refIteracion =iterId;
    }
}
    
