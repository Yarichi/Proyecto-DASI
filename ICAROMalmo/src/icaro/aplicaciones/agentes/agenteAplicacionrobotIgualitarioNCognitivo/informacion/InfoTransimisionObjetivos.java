/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.informacion;
import icaro.aplicaciones.Rosace.informacion.*;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Francisco J Garijo
 */
public class InfoTransimisionObjetivos implements Serializable{

      private String  identEquipo = null;
      private String  nombreAgente;
      private InfoEquipo miEquipo ;
      private ArrayList<String>  agentesEquipo,respuestasAgentes, propuestasEnviadas,propuestasAceptadas ;
      private  int respuestasEsperadas;
      private int numPropuestasAceptadas;
      public int respuestasRecibidas = 0;
      public boolean hanLlegadoTodasLasAceptaciones = false;
      public String idClaseObjetivos = null;
      public String refJustificacion = null;
//      private ItfUsoRecursoTrazas trazas;

     public InfoTransimisionObjetivos(String identAgente,InfoEquipo equipo,String refJustif){
            miEquipo = equipo;
            agentesEquipo = miEquipo.getTeamMemberIDs();
            identEquipo =miEquipo.getTeamId();
            nombreAgente = identAgente;
//            respuestasAgentes = new ArrayList();
            propuestasEnviadas= new ArrayList();
            propuestasAceptadas= new ArrayList();
            respuestasRecibidas = 0;
            refJustificacion = refJustif;
         // Inicializamos para cada agente las respuestas, empates, confirmaciones
//            String aux;
//            for (int i = 0; i < agentesEquipo.size(); i++) {
//                respuestasAgentes.add("");
//                }
         
     }
     
     public  ArrayList getIdentsAgentesEquipo(){
         if (identEquipo != null) return agentesEquipo;
         else return null;
     }
     public  String getIdenEquipo(){
         return identEquipo;
     }
      public void procesarAceptacionPropuesta( AceptacionPropuesta ceptProp){
          
      }
      public void addInfoPropuestaEnviada(String refPropuesta){
         
          if(!propuestasEnviadas.contains(refPropuesta)){
               int ultimoIndice = propuestasEnviadas.size();
               propuestasEnviadas.add(ultimoIndice,refPropuesta);
               propuestasAceptadas.add(ultimoIndice,"");
          }
             
      }
     
//     public synchronized int numRespuestasRecibidas(){
//         int respRecibidas = 0;
//         for(int i = 0; i< respuestasAgentes.size(); i++){
//             if(((String)respuestasAgentes.get(i)) != ""){
//                 respRecibidas++;
//             }
//         }
//         return respRecibidas;
//     }

     public synchronized String getIdentAgente(){
         return nombreAgente ;
     }
 
//     public synchronized ArrayList getRespuestas(){
//            //mandar un mensaje a los agentes que no nos han enviado la respuesta aun
//            //enviamos el mensaje y la info adicional, que es de donde viene
//         return respuestasAgentes;
//     }
    
//     public synchronized void setRespuestasEsperadas(int numRespuestas){
//            //mandar un mensaje a los agentes que no nos han enviado la respuesta aun
//            //enviamos el mensaje y la info adicional, que es de donde viene
//         respuestasEsperadas =numRespuestas ;
//     }
//    
//     public synchronized Boolean tengoTodasLasRespuestasEsperadas(){
//         return (respuestasRecibidas == respuestasAgentes.size()) ;
//     }
      public synchronized Boolean tengoTodasLasPropuestasAceptadas(){
          
         return (numPropuestasAceptadas == propuestasAceptadas.size()) ;
     }

//     public synchronized void initRespuestasRecibidas(){
//            //mandar un mensaje a los agentes que no nos han enviado la respuesta aun
//            //enviamos el mensaje y la info adicional, que es de donde viene
//         respuestasEsperadas =0 ;
//     }
    
//     public synchronized Integer getRespuestasRecibidas(){
//            //mandar un mensaje a los agentes que no nos han enviado la respuesta aun
//            //enviamos el mensaje y la info adicional, que es de donde viene
//         return respuestasRecibidas ;
//     }
// 
//     public synchronized ArrayList<String> getAgentesEquipo(){      
//						
//         return agentesEquipo;
//     }
 public synchronized void anotarAceptacionPropuesta(AceptacionPropuesta acptProp){
     String idAgteEmisor= acptProp.getIdentAgente();
     String refPropuesta= acptProp.getidentObjectRefAcetPropuesta();
//     this.addRespuestaAgente(idAgteEmisor,acptProp.getidentObjectRefAcetPropuesta());
      int indiceRefPropuesta = propuestasEnviadas.indexOf(refPropuesta);
       if ( indiceRefPropuesta >=0 ){
              propuestasAceptadas.add(indiceRefPropuesta, idAgteEmisor);
              numPropuestasAceptadas ++;
              hanLlegadoTodasLasAceptaciones = (numPropuestasAceptadas==propuestasEnviadas.size());
              
       }
 }
          
      public synchronized void setidClaseObjetivos(String claseObjId){
         idClaseObjetivos = claseObjId;
     }
      public synchronized String getidClaseObjetivos(){
         return idClaseObjetivos ;
     }
     public String toString(){
    	 return " ref Justificacion->" + refJustificacion +
                "; InfoTransmisionObjetivos: " + "Agente->" + nombreAgente + 
    	        " ; equipo->" + agentesEquipo +
                " ; propuestas Enviadas ->" + propuestasEnviadas +
                 " ; propuestas Aceptadas ->" + propuestasAceptadas +
    	        " ; hanLlegadoTodasLasAceptaciones->" + hanLlegadoTodasLasAceptaciones + 
    	        " ; numeroAcepacionesRecibidas->" + numPropuestasAceptadas
    	        ; 
     }
 
}