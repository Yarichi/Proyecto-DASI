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
public class InfoParaDecidirQuienVa implements Serializable{

      private ArrayList confirmacionesAgentes,evaluacionesRecibidas;//resto de agentes que forman mi equipo
      private String  identEquipo = null;
      private String  nombreAgente;
//      private InfoEquipo miEquipo ;
      private ArrayList<String>  agentesEquipo,agentesEmpatados,respuestasAgentes;
      private  int mi_eval_nueva,respuestasEsperadas,confirmacionesEsperadas ;
      private int numeroEvaluacionesRecibidas, valorMejorEvalucionRecibida,indiceAgenteConMejorEvaluacion;
      public int numeroRespuestasConfirmacionParaIrYo = 0 ;
      public int mi_eval = 0;
      public int respuestasRecibidas = 0;
      public int propuestasDesempateRecibidas = 0;
      public int propuestasDesempateEsperadas = 0;
      public boolean tengoLaMejorEvaluacion = false ;
      public boolean hanLlegadoTodasLasEvaluaciones = false;
      public boolean hayEmpates = false;
      public boolean noSoyElMejor = true ; // Not (hayEmpates) or Not ( tengoLaMejorEvaluacion) 
      public boolean objetivoAsumidoPorOtroAgte = false;
      public boolean tengoAcuerdoDeTodos = false;
      public boolean tengoMiEvaluacion = false;
      public boolean miEvaluacionEnviadaAtodos = false;
      public boolean miPropuestaParaAsumirElObjetivoEnviadaAtodos = false;
      public boolean miDecisionParaAsumirElObjetivoEnviadaAtodos = false;
      public boolean miPropuestaDeDesempateEnviadaAtodos = false;
      public boolean heInformadoAlmejorParaQueAsumaElObjetivo = false;
      public String idElementoDecision = null;

      public  InfoParaDecidirQuienVa(String identAgente){
        nombreAgente = identAgente;
        inicializarInfo();
      }
     public InfoParaDecidirQuienVa(String identAgente,ArrayList<String> agtsEquipoIds){
            setAgentesEquipo(agtsEquipoIds);
            nombreAgente = identAgente;
            inicializarInfo();
     }
     private void inicializarInfo(){
            respuestasAgentes = new ArrayList();
            confirmacionesAgentes = new ArrayList();
            agentesEmpatados = new ArrayList();
            evaluacionesRecibidas = new ArrayList();
            numeroEvaluacionesRecibidas = 0;
            valorMejorEvalucionRecibida = 0;
            indiceAgenteConMejorEvaluacion = 0;
            respuestasRecibidas = 0;
            numeroRespuestasConfirmacionParaIrYo = 0;
            propuestasDesempateEsperadas = 0;   
         // Inicializamos para cada agente las respuestas, empates, confirmaciones
            String aux;
            if ( agentesEquipo!= null){
                for (String agentesEquipo1 : agentesEquipo) {
                    respuestasAgentes.add("");
                    confirmacionesAgentes.add("");
                    evaluacionesRecibidas.add(0);
                }
                  confirmacionesEsperadas = agentesEquipo.size();
            }
     }
     
     public synchronized void inicializarInfoParaDecidir(String idInfoDecision){
      // Inicializamos para cada agente las respuestas, empates, confirmaciones
         if(agentesEquipo!= null){
         for (int i = 0; i < agentesEquipo.size(); i++) {
             respuestasAgentes.add("");
             confirmacionesAgentes.add("");
             evaluacionesRecibidas.add(0);
         }
         }
         idElementoDecision = idInfoDecision;
         numeroEvaluacionesRecibidas = 0;
         valorMejorEvalucionRecibida = 0;
         indiceAgenteConMejorEvaluacion = 0;
         numeroRespuestasConfirmacionParaIrYo = 0;
         tengoLaMejorEvaluacion = false;
         hayEmpates = false;
         hanLlegadoTodasLasEvaluaciones = false;
         objetivoAsumidoPorOtroAgte = false;
         tengoAcuerdoDeTodos = false;
         tengoMiEvaluacion = false;
         miEvaluacionEnviadaAtodos = false;
         miPropuestaParaAsumirElObjetivoEnviadaAtodos = false;
         heInformadoAlmejorParaQueAsumaElObjetivo = false;
     }
        
     public synchronized int numRespuestasRecibidas(){
         int respRecibidas = 0;
         for(int i = 0; i< respuestasAgentes.size(); i++){
             if(((String)respuestasAgentes.get(i)) != ""){
                 respRecibidas++;
             }
         }
         return respRecibidas;
     }

     public synchronized int numEvaluacionesRecibidas(){
         int evalRecibidas = 0;
         for(int i = 0; i< respuestasAgentes.size(); i++){
             if(((Integer)evaluacionesRecibidas.get(i)) > 0){
                 evalRecibidas++;
             }
         }
         return evalRecibidas;
     }

     
     public synchronized int getnumeroEvaluacionesRecibidas(){
         int evalRecibidas = 0;
         for(int i = 0; i< respuestasAgentes.size(); i++){
             if(((Integer)evaluacionesRecibidas.get(i)) > 0){
                 evalRecibidas++;
             }
         }
         return evalRecibidas;
     }
    public synchronized ArrayList getAgentesEquipo(){
          return agentesEquipo;
     }
     public synchronized void setAgentesEquipo(ArrayList agtesEquipoIds){
         agentesEquipo=agtesEquipoIds;
     }
     public  String getIdenEquipo(){
         return identEquipo;
     }
     public  void setIdenEquipo(String quipoId){
          identEquipo=quipoId;
     } 
     
     public synchronized void setMi_eval(Integer valor){
         mi_eval = valor;
     }

     public synchronized Integer getMi_eval(){
         return mi_eval ;
     }

     public synchronized String getIdentAgente(){
         return nombreAgente ;
     }
 
     public synchronized boolean gettengoLaMejorEval(){
         return tengoLaMejorEvaluacion;
     }
     public synchronized void settengoLaMejorEval(){
          tengoLaMejorEvaluacion=true;
     }
     
     public synchronized boolean tengoTodasLasEvaluaciones(){					   
         return hanLlegadoTodasLasEvaluaciones;      
     }

     //El que tiene mejor evaluacion nueva es el que menor Id tiene
     public synchronized boolean tengoLaMejorEvalNueva(ArrayList respuestas){
         boolean soyElMejor = true;
         for(int i = 0; i< respuestas.size();i++){
             Integer auxiliar = (Integer)respuestas.get(i);
             if(auxiliar >0){
                 if(mi_eval_nueva>auxiliar ){
                      soyElMejor = false;
                 }
             }
         }
         return soyElMejor;
     }
     
     
     //devuelve el agente mejor dentro de mi equipo
     public synchronized String dameIdentMejor(){
         if(agentesEquipo !=null){
         String mejorAgente = (String)agentesEquipo.get(0);
         int mejor_eval = (Integer)evaluacionesRecibidas.get(0);
         int evaluacion_local;
         //empezamos en el uno porque lo hemos inicializado en el cero
         for(int i = 1; i< evaluacionesRecibidas.size();i++){
             evaluacion_local = (Integer)evaluacionesRecibidas.get(i);
             if(mejor_eval<evaluacion_local){
                 mejorAgente = (String)agentesEquipo.get(i);
                 mejor_eval = evaluacion_local;
             }
         }
         return mejorAgente;
         }else return null;
     }
     
     public synchronized String dameEmpatados(){
         
         if(agentesEquipo !=null){
         String mejorAgente = (String)agentesEquipo.get(0);
            			//         int mejor_eval = (Integer)evaluacionesRecibidas.get(0);
         int evaluacion_local;
         //empezamos en el uno porque lo hemos inicializado en el cero
         for(int i = 0; i< evaluacionesRecibidas.size();i++){
             evaluacion_local = (Integer)evaluacionesRecibidas.get(i);
             if(mi_eval == (Integer)evaluacionesRecibidas.get(i)){
                 agentesEmpatados.add((String)agentesEquipo.get(i));
             }
         }
         return mejorAgente;
         }else return null;
     }

  public synchronized void procesarValorPropuestaDesempate(PropuestaAgente propuesta) { 
    
     if (hayEmpates){ // si no hay empates no se hace nada
        String identAgteProponente =  propuesta.getIdentAgente();
        String msgPropuesta = propuesta.getMensajePropuesta();
        propuestasDesempateRecibidas ++;
        if(msgPropuesta.equals(VocabularioRosace.MsgPropuesta_Oferta_Para_Ir)){ // otro agente se propone para realizar el objetivo
                tengoLaMejorEvaluacion = false;
                hayEmpates = false;
                noSoyElMejor=true; 
        }else if (msgPropuesta.equals(VocabularioRosace.MsgPropuesta_Para_Q_vayaYo)){// otro agente dice que me haga cargo yo
                //    agentesEmpatados.remove(identAgteProponente);
                    this.procesarPropuestaRecibida(propuesta);
                    if (this.propuestasDesempateRecibidas == this.propuestasDesempateEsperadas){
                        hayEmpates = false;
                        noSoyElMejor=false;
                        tengoLaMejorEvaluacion = true;
                        }
                } else{ // se recibe una propuesta con un valor 
                Integer valorPropuesta = (Integer) propuesta.getJustificacion();
            
                if (mi_eval < valorPropuesta){
                  noSoyElMejor=true; 
                  hayEmpates = false; // dejo de estar empatado porque hay otro con mejor evaluacion
                  tengoLaMejorEvaluacion = false;
                   }
                else if (mi_eval > valorPropuesta){ // quito al agente de la lista de empatados
              //      agentesEmpatados.remove(identAgteProponente); anado la evaluacion a las evaluaciones recibidas
               //     EvaluacionAgente evalDelaPropuesta = new EvaluacionAgente(identAgteProponente,valorPropuesta);
                    Integer indiceAgente = agentesEquipo.indexOf (identAgteProponente );
                    evaluacionesRecibidas.set(indiceAgente, valorPropuesta);
                    if (this.propuestasDesempateRecibidas == this.propuestasDesempateEsperadas){
                        hayEmpates = false; // dejo de estar empatado porque tengo la mejor evaluacion
                        tengoLaMejorEvaluacion = true;
                    }
                }
         }
     }
  }    
     public synchronized void procesarPropuestaRecibida(PropuestaAgente propuesta) {
         String respuesta = propuesta.getMensajePropuesta();
         String idAgenteEmisorRespuesta = propuesta.getIdentAgente();
         Integer indiceAgenteEmisorRespuesta = agentesEquipo.indexOf(idAgenteEmisorRespuesta);
         if ( (String) respuestasAgentes.get(indiceAgenteEmisorRespuesta)== "" ){
              respuestasRecibidas ++;
              respuestasAgentes.set(indiceAgenteEmisorRespuesta, respuesta);//guardamos la respuesta
              if ((respuesta == "CreoQueDebesIrTu")& (tengoLaMejorEvaluacion)){
                 numeroRespuestasConfirmacionParaIrYo ++;
                 this.addConfirmacionAcuerdoParaIr(idAgenteEmisorRespuesta, respuesta);
                 if (numeroRespuestasConfirmacionParaIrYo == respuestasEsperadas){
                     tengoAcuerdoDeTodos = true;
                 }
             }
         }
     }
     
     public synchronized void addConfirmacionAcuerdoParaIr(String idAgenteEmisorRespuesta, String respuesta ) {
	if (agentesEquipo !=null){				  
          Integer indiceAgenteEmisorRespuesta = agentesEquipo.indexOf(idAgenteEmisorRespuesta);
          if(indiceAgenteEmisorRespuesta>=0)
          if ( ((String) confirmacionesAgentes.get(indiceAgenteEmisorRespuesta)).equals("" )) {
                numeroRespuestasConfirmacionParaIrYo ++;
                confirmacionesAgentes.set(indiceAgenteEmisorRespuesta, respuesta);//guardamos la respuesta
                 if (numeroRespuestasConfirmacionParaIrYo == confirmacionesEsperadas)
                    tengoAcuerdoDeTodos = true;
          }  
        }
      }

      public synchronized void addNuevaEvaluacion(EvaluacionAgente evaluacion) {
          if(agentesEquipo!=null){
          Integer eval = evaluacion.getEvaluacion();
          Integer indiceAgente = agentesEquipo.indexOf ( evaluacion.getIdentAgente());
          // Si el agente no pertenece al equipo ignoro la evalucion que puede ser la mia
          if (indiceAgente != -1 ) {
              if (eval > valorMejorEvalucionRecibida   ){
                    // Si la evaluacion recibida es mayor que la mejor evaluacion actualizo el valor de la mejor evaluacion
                    valorMejorEvalucionRecibida = eval;
                    indiceAgenteConMejorEvaluacion = indiceAgente;
              }

              if ((Integer)evaluacionesRecibidas.get(indiceAgente)== 0 ){
                    // Si recibimos otra evaluacion del mismo agente no incrementamos     
                    numeroEvaluacionesRecibidas ++;
              }
                
              evaluacionesRecibidas.set(indiceAgente, eval);//guardamos la evaluacion recibida

              if (numeroEvaluacionesRecibidas == agentesEquipo.size()){
                    hanLlegadoTodasLasEvaluaciones = true;
                    // Caluculo si tengo la mejor evaluacio o si hay empate con otros
                    if (mi_eval < valorMejorEvalucionRecibida ){
                        noSoyElMejor=true; 
                        hayEmpates = false;
                        tengoLaMejorEvaluacion = false;
                    }else
                        if (mi_eval == valorMejorEvalucionRecibida ){
                             tengoLaMejorEvaluacion = false;
                             hayEmpates = true;
                             noSoyElMejor=false;
                        }else {// mi evaluacion es mayor
                             tengoLaMejorEvaluacion = true;
                             hayEmpates = false;
                             noSoyElMejor=false;
                        }
              }
                
          }
          }
          // Si la evaluacion es -1 es decir esta fuera de rango  ignoro  la evaluacion
          // el motor la elimina
    }
    
    public synchronized void addConfirmacionRealizacionObjetivo(AceptacionPropuesta confObjetivo) {
         if(agentesEquipo!=null){
        String identObj = confObjetivo.getmsgAceptacionPropuesta();
        String idAgenteEmisorEvaluacion = confObjetivo.getIdentAgente();
        confirmacionesAgentes.set(agentesEquipo.indexOf(idAgenteEmisorEvaluacion), identObj);//guardamos la respuesta
         }
     }

     public synchronized Integer calcularEvalucionParaDesempate (){
    	 // Incremento mi funcion de evaluacion con un numero aleatorio menor     que 2500
    	 mi_eval = Math.abs(mi_eval+(int) ((Math.random()*10500+1)));
    	 tengoLaMejorEvaluacion = true;
    	 return mi_eval;
     }
     //funcion que sirve para ver con cuantos empata
         
     public synchronized ArrayList getRespuestas(){
            //mandar un mensaje a los agentes que no nos han enviado la respuesta aun
            //enviamos el mensaje y la info adicional, que es de donde viene
         return respuestasAgentes;
     }
    
     public synchronized void setRespuestasEsperadas(Integer numRespuestas){
            //mandar un mensaje a los agentes que no nos han enviado la respuesta aun
            //enviamos el mensaje y la info adicional, que es de donde viene
         respuestasEsperadas =numRespuestas ;
     }
    public synchronized Boolean gethanLlegadoTodasLasEvaluaciones() {
        return hanLlegadoTodasLasEvaluaciones;
    
    }
    
     public synchronized boolean getTengoAcuerdoDeTodos(){
					    
         return tengoAcuerdoDeTodos;
     }
    public synchronized void setTengoAcuerdoDeTodos(Boolean bvalue){
					    
          tengoAcuerdoDeTodos = bvalue;
     }
     public synchronized void sethanLlegadoTodasLasEvaluaciones(Boolean bvalue) {
         hanLlegadoTodasLasEvaluaciones = bvalue;
    
    }
     public synchronized void setobjetivoAsumidoPorOtroAgte(Boolean bvalue){
					    
          objetivoAsumidoPorOtroAgte = bvalue;
     }
     public synchronized Boolean getobjetivoAsumidoPorOtroAgte() {
         return objetivoAsumidoPorOtroAgte ;
    
    }
   
     public synchronized Integer getRespuestasEsperadas(){
            //mandar un mensaje a los agentes que no nos han enviado la respuesta aun
            //enviamos el mensaje y la info adicional, que es de donde viene
         return respuestasEsperadas ;
     }
    
     public synchronized Boolean tengoTodasLasRespuestasEsperadas(){
         return (respuestasRecibidas == respuestasAgentes.size()) ;
     }

     public synchronized void initRespuestasRecibidas(){
            //mandar un mensaje a los agentes que no nos han enviado la respuesta aun
            //enviamos el mensaje y la info adicional, que es de donde viene
         respuestasEsperadas =0 ;
     }
    
     public synchronized Integer getRespuestasRecibidas(){
            //mandar un mensaje a los agentes que no nos han enviado la respuesta aun
            //enviamos el mensaje y la info adicional, que es de donde viene
         return respuestasRecibidas ;
     }
     
     public synchronized ArrayList getConfirmacionesRecibidas(){
         return confirmacionesAgentes;
     }
    public synchronized ArrayList getEvaluacionesRecibidas(){
         return evaluacionesRecibidas;
     }
     public synchronized ArrayList<String> getAgentesEmpatados(){
         //empezamos en el uno porque lo hemos inicializado en el cero
         // si esta vacio lo calculamos pero si ya hay elementos se devuelve  como esta. Esto se debe a que a terminado una rodo de desempate con empates
         if (agentesEmpatados.isEmpty()){
            for(int i = 0; i< evaluacionesRecibidas.size();i++){
              if(mi_eval == (Integer)evaluacionesRecibidas.get(i)){
                  agentesEmpatados.add((String)agentesEquipo.get(i));
              }      
            }
         }
         propuestasDesempateEsperadas =agentesEmpatados.size(); 
         return agentesEmpatados;
     }
     
     public synchronized Boolean gethayEmpates(){
         return hayEmpates;
     }
  
     public synchronized Boolean gettengoLaMejorEvaluacion(){
         return tengoLaMejorEvaluacion;
     }
  
     public synchronized Boolean getnoSoyElMejor(){
         return noSoyElMejor;
     }
    
     public synchronized Boolean getMiEvaluacionEnviadaAtodos(){
         return miEvaluacionEnviadaAtodos;
     }
     
     public synchronized void setMiEvaluacionEnviadaAtodos(Boolean valor){
          miEvaluacionEnviadaAtodos = valor ;
     }
     public synchronized Boolean getMiPropuestaParaAsumirElObjetivoEnviadaAtodos(){
         return miPropuestaParaAsumirElObjetivoEnviadaAtodos;
     }
     
     public synchronized void setMiPropuestaParaAsumirElObjetivoEnviadaAtodos(Boolean valor){
          miPropuestaParaAsumirElObjetivoEnviadaAtodos = valor ;
     }
      public synchronized Boolean getMiDecisionParaAsumirElObjetivoEnviadaAtodos(){
         return miDecisionParaAsumirElObjetivoEnviadaAtodos;
     }
     
     public synchronized void setMiDecisionParaAsumirElObjetivoEnviadaAtodos(Boolean valor){
          miDecisionParaAsumirElObjetivoEnviadaAtodos = valor ;
     }
     public synchronized Boolean getTengoMiEvaluacion(){
         return tengoMiEvaluacion;
     }
     
     public synchronized void setTengoMiEvaluacion(Boolean valor){
          tengoMiEvaluacion = valor ;
     }
     
      public synchronized Boolean getheInformadoAlmejorParaQueAsumaElObjetivo(){
         return heInformadoAlmejorParaQueAsumaElObjetivo;
     }
     
     public synchronized void setheInformadoAlmejorParaQueAsumaElObjetivo(Boolean valor){
          heInformadoAlmejorParaQueAsumaElObjetivo = valor ;
     }
      public synchronized Boolean getMiPropuestaDeDesempateEnviadaAtodos(){
         return miPropuestaDeDesempateEnviadaAtodos;
     }
     
     public synchronized void setMiPropuestaDeDesempateEnviadaAtodos(Boolean valor){
          miPropuestaDeDesempateEnviadaAtodos = valor ;
     }   
     public synchronized Integer getnumeroRespuestasConfirmacionParaIrYo(){
         return numeroRespuestasConfirmacionParaIrYo;
     }
     
      public synchronized void setidElementoDecision(String elementDecisionId){
         idElementoDecision = elementDecisionId;
     }
      public synchronized String getidElementoDecision(){
         return idElementoDecision ;
     }
      public synchronized boolean recibidaEvaluacionAgente(int refAgente){
         return (Integer)evaluacionesRecibidas.get(refAgente)>0 ;
     }
      
     public String toString(){
    	 return " idElementoDecision->" + idElementoDecision +
                "; InfoParaDecidirQuienVa: " + "Agente->" + nombreAgente + 
    	        " ; equipo->" + agentesEquipo +
    	        " ; mi_eval->" + mi_eval + 
    	        " ; evaluacionesRecibidas->" + evaluacionesRecibidas +
    	        " ; tengoLaMejorEvaluacion->" + tengoLaMejorEvaluacion + 
                " ; tengoMiEvaluacion->" + tengoMiEvaluacion + 
    	        " ; hayEmpates->" + hayEmpates + 
    	        " ; noSoyElMejor->" + noSoyElMejor + 
    	        " ; tengoAcuerdoDeTodos->" + tengoAcuerdoDeTodos + 
    	        " ; hayOtrosQueQuierenIr->" + objetivoAsumidoPorOtroAgte + 
    	        " ; hanLlegadoTodasLasEvaluaciones->" + hanLlegadoTodasLasEvaluaciones + 
    	        " ; numeroEvaluacionesRecibidas->" + numeroEvaluacionesRecibidas
    	        
    	        ; 
     }
 
}