/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.informacion;
import icaro.aplicaciones.Rosace.informacion.AceptacionPropuesta;
import icaro.aplicaciones.Rosace.informacion.EvaluacionAgente;
import icaro.aplicaciones.Rosace.informacion.InfoEquipo;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
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
public class InfoParaDecidirAQuienAsignarObjetivo implements Serializable{

      private ArrayList confirmacionesAgentes,evaluacionesRecibidas;//resto de agentes que forman mi equipo
      private String  identEquipo = null;
      private String  nombreAgente;
 //     private ItfUsoConfiguracion itfConfig  ;
      private ArrayList<String> agentesAplicacionDefinidos, agentesEquipo,agentesEmpatados,respuestasAgentes;
      private  int respuestasEsperadas,confirmacionesEsperadas ;
      public int numeroEvaluacionesRecibidas, valorMejorEvalucionRecibida,indiceAgenteConMejorEvaluacion;
      public int numeroRespuestasConfirmacionParaIrYo = 0 ;
      public int mi_eval = 0;
      public int respuestasRecibidas = 0;
      public int propuestasDesempateRecibidas = 0;
      public int propuestasDesempateEsperadas = 0;
      public boolean tengoLaMejorEvaluacion = false ;
      public boolean hanLlegadoTodasLasEvaluaciones = false;
      public boolean hayEmpates = false;
 //     public boolean noSoyElMejor = true ; // Not (hayEmpates) or Not ( tengoLaMejorEvaluacion) 
      public boolean hayOtrosQueQuierenIr = false;
      public boolean tengoAcuerdoDeTodos = false;
//      public boolean tengoMiEvaluacion = false;
      public boolean peticionEvaluacionEnviadaAtodos = false;
      public boolean miPropuestaParaAsumirElObjetivoEnviadaAtodos = false;
      public boolean miDecisionParaAsumirElObjetivoEnviadaAtodos = false;
      public boolean miPropuestaDeDesempateEnviadaAtodos = false;
      public boolean heInformadoAlmejorParaQueAsumaElObjetivo = false;
      public String idElementoDecision = null;
//      private ItfUsoRecursoTrazas trazas;

      public  InfoParaDecidirAQuienAsignarObjetivo(String identAgente, InfoEquipo equipo){
        try {
     //      trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
         
    //        ItfUsoConfiguracion itfConfig = (ItfUsoConfiguracion) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz(NombresPredefinidos.ITF_USO+"Configuracion");
     //       agentesAplicacionDefinidos = itfConfig.getIdentificadoresInstanciasAgentesAplicacion();
     //       agentesAplicacionDefinidos = equipo.getIdentificadoresInstanciasAgentesAplicacion();
     //       identEquipo = itfConfig.getValorPropiedadGlobal(NombresPredefinidos.NOMBRE_PROPIEDAD_GLOBAL_EQUIPO_AGENTES);
            identEquipo = equipo.getTeamId();
//            agentesEquipo = getNombreAgentesEquipoDefinidos(identAgente, identEquipo);
            agentesEquipo =equipo.getTeamMemberIDs();
            nombreAgente = identAgente;
            respuestasAgentes = new ArrayList();
            confirmacionesAgentes = new ArrayList();
            agentesEmpatados = new ArrayList();
            evaluacionesRecibidas = new ArrayList();
            numeroEvaluacionesRecibidas = 0;
            valorMejorEvalucionRecibida = -1;
            indiceAgenteConMejorEvaluacion = 0;
            respuestasRecibidas = 0;
            numeroRespuestasConfirmacionParaIrYo = 0;
            propuestasDesempateEsperadas = 0;
           
         // Inicializamos para cada agente las respuestas, empates, confirmaciones
            String aux;
            for (int i = 0; i < agentesEquipo.size(); i++) {
                respuestasAgentes.add("");
                confirmacionesAgentes.add("");
                evaluacionesRecibidas.add(-5); 
                }
            confirmacionesEsperadas = agentesEquipo.size()-1;
        } catch (Exception ex) {
            Logger.getLogger(InfoParaDecidirAQuienAsignarObjetivo.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
     
     public synchronized ArrayList ObtenerNombreAgentesDelEquipoRegistrados(String identEquipo, String identAgente){
         try {
            ArrayList agentesRegistrados = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.nombresInterfacesRegistradas();
        
            agentesEquipo = new ArrayList();
            String aux;
               for (int i = 0; i < agentesRegistrados.size(); i++) {
                   aux = (String) agentesRegistrados.get(i);
                   //filtramos los Gestores. Mandamos solo a las itf de uso de los agentes, y excluimos el propio agente
                   if (aux.contains(identEquipo) && aux.contains("Itf_Uso") && !aux.contains(identAgente)) {
                       String identAgenteRegistrado = aux.replaceFirst("Itf_Uso_", "");
                       agentesEquipo.add(identAgenteRegistrado);
                   }
               }
         } catch (Exception ex) {
            Logger.getLogger(InfoParaDecidirAQuienAsignarObjetivo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return agentesEquipo;
     }
     
     private ArrayList getNombreAgentesEquipoDefinidos(String identAgente, String identEquipo){    
         try {     
             if (identEquipo != null){
             
            agentesEquipo = new ArrayList();
            String aux;
                for (int i = 0; i < agentesAplicacionDefinidos.size(); i++) {
                    aux = (String) agentesAplicacionDefinidos.get(i); 
                //Excluimos el propio agente
                    if (!aux.contains(identAgente))
                        // esto hay que quitarlo y nombrar los agentes como es debido  *********
                        if (identEquipo.equals( VocabularioRosace.IdentEquipoJerarquico)) agentesEquipo.add(aux);
                        else if (aux.contains(identEquipo) && !aux.contains(identAgente)) {             
                        agentesEquipo.add(aux);
                        }
                }
             }
            // se deberia poner un mensaje de error en las trazas, pero ya saldra mas adelante
         } catch (Exception ex) {
            Logger.getLogger(InfoParaDecidirAQuienAsignarObjetivo.class.getName()).log(Level.SEVERE, null, ex);
            return null;
         }
         return agentesEquipo; 
     }
     public  ArrayList getIdentsAgentesEquipo(){
         if (identEquipo != null) return agentesEquipo;
         else return null;
     }
     public  String getIdenEquipo(){
         return identEquipo;
     }
    
     public synchronized void inicializarInfoParaDecidir(String idInfoDecision){
      // Inicializamos para cada agente las respuestas, empates, confirmaciones
         for (int i = 0; i < agentesEquipo.size(); i++) {
             respuestasAgentes.add("");
             confirmacionesAgentes.add("");
             evaluacionesRecibidas.add(0);
         }
         idElementoDecision = idInfoDecision;
         numeroEvaluacionesRecibidas = 0;
         valorMejorEvalucionRecibida = 0;
         indiceAgenteConMejorEvaluacion = 0;
         hanLlegadoTodasLasEvaluaciones = false;
    //     hayOtrosQueQuierenIr = false;
 //        tengoAcuerdoDeTodos = false;
 //        tengoMiEvaluacion = false;
         peticionEvaluacionEnviadaAtodos = false;
 //        miPropuestaParaAsumirElObjetivoEnviadaAtodos = false;
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
         
         return numeroEvaluacionesRecibidas;
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
 
     public synchronized boolean tengoLaMejorEval(){
    /*
         boolean soyElMejor = true;
 //           mi_eval = miEvaluacion.getEvaluacion();
            for(int i = 0; i< evaluacionesRecibidas.size();i++){
                if(mi_eval<((Integer)evaluacionesRecibidas.get(i))){
                    soyElMejor = false;
                }
     *

             tengoLaMejorEvaluacion =  soyElMejor;
            }
*/
         return tengoLaMejorEvaluacion;
     }
     
     public synchronized boolean tengoTodasLasEvaluaciones(){
						   /*
						       boolean todasLasEvaluaciones = true;
						            int i = 0;
						            while (todasLasEvaluaciones & ( i< agentesEquipo.size())){
						                if((Integer)evaluacionesRecibidas.get(i)== 0){
						                    todasLasEvaluaciones = false;
						                } i++;
						            }
						    *
						    */
         return hanLlegadoTodasLasEvaluaciones;      
       						//    return (agentesEquipo.size() == numEvaluacionesRecibidas());
     }

     //El que tiene mejor evaluacion nueva es el que menor Id tiene
    
     
     //devuelve el agente mejor dentro de mi equipo
     public synchronized String dameIdentMejor(){
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
     }
     
     public synchronized String dameEmpatados(){
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
     }

    
     public synchronized void addConfirmacionAcuerdoParaIr(String idAgenteEmisorRespuesta, String respuesta ) {
					  //          ArrayList p = paquete;
					  //          String eval = (String)p.get(0);
					  //          String respuesta = propuesta.getMensajePropuesta();
					 //           String idAgenteEmisorRespuesta = propuesta.getIdentAgente();
					  //          trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Respuesta Recibida. Dice :" + eval.toString()+" Emisor: "+idAgenteEmisorEvaluacion, InfoTraza.NivelTraza.info));
          //actualizar las respuestas
          Integer indiceAgenteEmisorRespuesta = agentesEquipo.indexOf(idAgenteEmisorRespuesta);

          if ( (String) confirmacionesAgentes.get(indiceAgenteEmisorRespuesta)== "" ){
                numeroRespuestasConfirmacionParaIrYo ++;
                confirmacionesAgentes.set(indiceAgenteEmisorRespuesta, respuesta);//guardamos la respuesta
                 if (numeroRespuestasConfirmacionParaIrYo == confirmacionesEsperadas)
                    tengoAcuerdoDeTodos = true;
          }           
      }

      public synchronized void addNuevaEvaluacion(EvaluacionAgente evaluacion) {
							  //          ArrayList p = paquete;
							  //          String eval = (String)p.get(0);
          Integer eval = evaluacion.getEvaluacion();
          					  //         String idAgenteEmisorEvaluacion = evaluacion.getIdentAgente();
          Integer indiceAgente = agentesEquipo.indexOf ( evaluacion.getIdentAgente());
          // Si el agente no pertenece al equipo ignoro la evalucion que puede ser la mia
          if (indiceAgente != -1 ) {
        	  			//          trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Respuesta Recibida. Dice :" + eval.toString()+" Emisor: "+idAgenteEmisorEvaluacion, InfoTraza.NivelTraza.info));
              //actualizar el valor de la mejor evaluacion
              if (eval > valorMejorEvalucionRecibida   ){
                    // Si la evaluacion recibida es mayor que la mejor evaluacion actualizo el valor de la mejor evaluacion
                    valorMejorEvalucionRecibida = eval;
                    hayEmpates = false;
                    indiceAgenteConMejorEvaluacion = indiceAgente;
              }else if (eval == valorMejorEvalucionRecibida   ) hayEmpates = true;

              if ((Integer)evaluacionesRecibidas.get(indiceAgente)== -5 ){
                    // Si recibimos otra evaluacion del mismo agente no incrementamos     
                    numeroEvaluacionesRecibidas ++;
              }
                
              evaluacionesRecibidas.set(indiceAgente, eval);//guardamos la evaluacion recibida

              if (numeroEvaluacionesRecibidas == agentesEquipo.size()){
                    hanLlegadoTodasLasEvaluaciones = true;
                    
                        }
              }
                
      }
          
    
    public synchronized void addConfirmacionRealizacionObjetivo(AceptacionPropuesta confObjetivo) {
					  //          ArrayList p = paquete;
					  //          String eval = (String)p.get(0);
        String identObj = confObjetivo.getmsgAceptacionPropuesta();
        String idAgenteEmisorEvaluacion = confObjetivo.getIdentAgente();
					 //           trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Confirmacion Recibida. Dice Objetivo :" + identObj +" Emisor: "+idAgenteEmisorEvaluacion, InfoTraza.NivelTraza.info));
        //actualizar las respuestas
        confirmacionesAgentes.set(agentesEquipo.indexOf(idAgenteEmisorEvaluacion), identObj);//guardamos la respuesta
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
     public synchronized ArrayList<String> getAgentesEquipo(){      
						
         return agentesEquipo;
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
  
     public synchronized Boolean getPeticionEvaluacionEnviadaAtodos(){
         return peticionEvaluacionEnviadaAtodos;
     }
     
     public synchronized void setPeticionEvaluacionEnviadaAtodos(Boolean valor){
          peticionEvaluacionEnviadaAtodos = valor ;
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
       
      public synchronized Boolean getheInformadoAlmejorParaQueAsumaElObjetivo(){
         return heInformadoAlmejorParaQueAsumaElObjetivo;
     }
     
     public synchronized void setheInformadoAlmejorParaQueAsumaElObjetivo(Boolean valor){
          heInformadoAlmejorParaQueAsumaElObjetivo = valor ;
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
     public String toString(){
    	 return " idElementoDecision->" + idElementoDecision +
                "; InfoParaDecidirQuienVa: " + "Agente->" + nombreAgente + 
    	        " ; equipo->" + agentesEquipo +
    	        " ; mi_eval->" + mi_eval + 
    	        " ; evaluacionesRecibidas->" + evaluacionesRecibidas +
    	        " ; tengoLaMejorEvaluacion->" + tengoLaMejorEvaluacion + 
                " ; hePedidoEvaluacionAtodos->" + peticionEvaluacionEnviadaAtodos + 
    	        " ; hayEmpates->" + hayEmpates + 
 //   	        " ; noSoyElMejor->" + noSoyElMejor + 
    	        " ; tengoAcuerdoDeTodos->" + tengoAcuerdoDeTodos + 
 //   	        " ; hayOtrosQueQuierenIr->" + hayOtrosQueQuierenIr + 
    	        " ; hanLlegadoTodasLasEvaluaciones->" + hanLlegadoTodasLasEvaluaciones + 
    	        " ; numeroEvaluacionesRecibidas->" + numeroEvaluacionesRecibidas
    	        
    	        ; 
     }
     

     
}