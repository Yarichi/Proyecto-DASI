package icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.imp;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Creencia;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Evidencia;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.ExtractedInfo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ItfProcesadorObjetivos;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ProcesadorObjetivos;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.motorDeReglas.ItfMotorDeReglas;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.gestorTareas.ItfGestorTareas;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.motorDeReglas.ItfConfigMotorDeReglas;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.apache.log4j.Logger;
import org.drools.runtime.StatefulKnowledgeSession;

/**
 * Implementacion del procesdor de objetivos
 * @author F Garijo
 *
 */
public class ProcesadorObjetivosImp extends ProcesadorObjetivos {

	private ItfMotorDeReglas ruleEngine;
	private ItfGestorTareas taskManager;
        private String idAgente;
	private Logger log = Logger.getLogger(ProcesadorObjetivosImp.class);
	
    private ArrayList<Creencia> creencias;
    private ArrayList<Object> extractecItems;
    
    
    public ProcesadorObjetivosImp(ItfMotorDeReglas ruleEngine,ItfGestorTareas taskManager) {
	   this.ruleEngine = ruleEngine;
           this.taskManager = taskManager;
	}
    
    public ProcesadorObjetivosImp() {
	
	}

    
    public void  SetItfMotorDeReglas (ItfMotorDeReglas itfMotor){
           this.ruleEngine = itfMotor;
    }

    
    public ItfMotorDeReglas GetItfMotorDeReglas (){
           return this.ruleEngine;
    }
    
    
    public void  SetItfGestorTareas (ItfGestorTareas gestorTareas){
           this.taskManager = gestorTareas;
    }

    
    @Override
    public ItfGestorTareas GetItfGestorTareas (){
           return this.taskManager ;
    }
	
    public boolean procesarCreencia(Creencia cre) {
		
	       if ((cre != null)&(cre.getContenido()!=null)) {
               ruleEngine.assertFact(cre);
               return true;
           }else {
               // Se intenta introducir en el motor una creencia vacio o de contenido vacio      
               return false;
           }
	}
       
    public boolean procesarEvidencia(Evidencia evidence) {
		   Creencia believe;
           // verificamos la cardinalidad del contenido de la evidencia. Si es mayor que uno se trata de una
           // colección de elementos que transformaremos en creencias
           // Obtenemos las creecias a partir de las evidencias
           // Analizamos el contenido de la evidencia
           // Metemos en el motor el contenido de la evidencia / creencia
		   
           if ( (evidence.getContent().size()== 1)){
                believe = asimilarEvidencia(evidence);

                if (believe != null) {
			        ruleEngine.assertFact(believe);
                    ruleEngine.assertFact(believe.getContenido());
			        return true;
                }
                return true;
           }
           else {
                creencias = asimilarEvidencias (evidence);

                //    ArrayList aux =  evidence.getContent();
                //    Object objetoActual;
                Iterator it = creencias.iterator();
                while(it.hasNext()){
                    believe = (Creencia) it.next();
                    if (believe !=null) {
                        ruleEngine.assertFact(believe);
                        ruleEngine.assertFact(believe.getContenido());
                    }
                    //        for (int i = 0; i< aux.size();i++){
                    //            objetoActual = aux.get(i);
                    //        }

                    //        believe = asimilarEvidencia(ev);
		        }
	            return true;
           }
    }

    @Override
    public boolean procesarExtractedInfo(ExtractedInfo extrInfo) {
           // verificamos la cardinalidad del contenido de la informacion extraida. Si es mayor que uno se trata de una
           // colección de elementos que seran enviados al motor
           // Metemos en el motor el contenido de extrInfo
           if (extrInfo == null) return false;
           
           if ( !(extrInfo.isContentACollection())){    
                Object msgContent = extrInfo.getContenido();			
                ruleEngine.assertFact(msgContent);
                               //                        ruleEngine.assertFact(extrInfo);
                               //			return true;
           }
           else {
                ArrayList aux = (ArrayList) extrInfo.getContenido();
                ExtractedInfo extrInfoEnArray;
                Iterator it = aux.iterator();
                while(it.hasNext()){
                    extrInfoEnArray = (ExtractedInfo) it.next();
                    if (extrInfoEnArray !=null) {
                        ruleEngine.assertFact(extrInfoEnArray);
                        ruleEngine.assertFact(extrInfoEnArray.getContenido());
                    }
		        }
                
           }
     // Opcional se puede anadir al motor el InfoExtracted o solo su contenido. Esto se podria configurar      
    //       ruleEngine.assertFact(extrInfo);
           return true;
    }
   
    @Override
    public void arranca() {
	       ruleEngine.fireRules();
	}
  
	/**
	 * Decide whether to assimilate evidence or not
	 * @param ev The evidence
	 * @return A believe yield from the evidence
	 */
	public Creencia asimilarEvidencia(Evidencia ev) {
	    // A partir de las evidencias se generan  creencias. En este caso se trata de una sola evidencia a partir de la cual generamos una creencia
           // Se pueden utilizar varios modelos e incluso delegar la creación a una clase exterior 
           // dependiente del dominio. Aqui nos limitamos a creernos  la evidencia recibida. Tranformamos la evidencia en creencia
           Creencia believe = new Creencia();
		   believe.setContenido(ev.getContent().get(0));
		   believe.setEmisor(ev.getOrigen());
           believe.setReceptor(ev.getCreador());
		   return  believe;
	}
	
	
    public Creencia nuevaCreencia(Object emisor,Object creador, Object contenido) {
	       // A partir de las evidencias se crean creencias
           // Se pueden utilizar varios modelos e incluso delegar la creación a una clase exterior
           // dependiente del dominio. Aqui nos limitamos a creernos todas las evidencias recibidas
           Creencia believe = new Creencia();
		   believe.setEmisor(emisor);
           believe.setReceptor(creador);
           believe.setContenido(contenido);
		   return believe;
	}
    
    
    public ArrayList<Creencia> asimilarEvidencias(Evidencia evidence) {
	       // A partir de las evidencias se crean creencias
           // Se pueden utilizar varios modelos e incluso delegar la creación a una clase exterior
           // dependiente del dominio. Aqui nos limitamos a creernos todas las evidencias recibidas
           ArrayList contenidoEvidencia =  evidence.getContent();
           Object origen = evidence.getOrigen();
           Object creador = evidence.getCreador();
           creencias = new ArrayList ();
           Object objetoActual;
           Iterator it = contenidoEvidencia.iterator();
           while(it.hasNext()){
               objetoActual = it.next();
               if ( objetoActual != null ) {
                    Creencia nuevaCrencia = nuevaCreencia(origen,creador,objetoActual );
                    creencias.add(nuevaCrencia);
               }
           }    
	       return creencias;
	}
   
    //The three next methods call to fireAllRules inside its code.  
    @Override
    public  void insertarHecho(Object fact) {
           ruleEngine.assertFact(fact);
    }
  
    @Override
    public  void eliminarHecho(Object fact) {
           ruleEngine.retracttFact(fact);
    }
  
    @Override
    public  void actualizarHecho(Object fact) {
           ruleEngine.updateFact(fact);
    }
          
	//The three next methods do not call to fireAllRules inside its code.   
    @Override
    public  void insertarHechoWithoutFireRules(Object fact) {
     	   ruleEngine.assertFactWithoutFireRules(fact);       
    }

    @Override
    public  void eliminarHechoWithoutFireRules(Object fact) {
    	   ruleEngine.retractFactWithoutFireRules(fact);
    }

    @Override
    public  void actualizarHechoWithoutFireRules(Object fact) {
    	   ruleEngine.updateFactWithoutFireRules(fact);
    }
        
    public  StatefulKnowledgeSession getStatefulKnowledgeSession(){
           return ruleEngine.getStatefulKnowledgeSession();
    }
    @Override
    public ItfConfigMotorDeReglas getItfConfigMotorDeReglas (){
    return ruleEngine.getItfConfigMotorDeReglas();
}
//    @Override
//    public ItfMotorDeReglas getItfMotorDeReglas (){
//    return ruleEngine.getItfMotorDeReglas();
//}
     @Override
    public String   getAgentId (){
          return this.idAgente ;
    }
      public void   setAgentId (String identAgente){
         idAgente = identAgente ;
    }
      @Override
    public boolean cambiarComportamiento(String identFicheroReglasComportamiento){
        return false;
    }
      @Override
    public Collection <Object> copiarObjetosDeMiMemoria(){
      return ruleEngine.getStatefulKnowledgeSession().getObjects();
      
    }
    @Override
    public void insertarObjetosEnMiMemoria(Collection <Object> objetosAinsertar){
      
	Iterator <Object>  it = objetosAinsertar.iterator();
        
	    while( it.hasNext() ) {
                this.insertarHecho(it.next());
	    }
    }

    @Override
    public ItfMotorDeReglas getItfMotorDeReglas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reinicializarComportamiento() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
