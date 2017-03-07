package icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.imp;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
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
 * Implementation for Cognitive Control
 * @author F Garijo
 *
 */
public class ProcesadorObjetivosImp1 extends ProcesadorObjetivos {
    private ItfMotorDeReglas ruleEngine;
    private ItfGestorTareas taskManager;
    private Logger log = Logger.getLogger(ProcesadorObjetivosImp1.class);	
    private ArrayList<Creencia> creencias;
    private ArrayList<Object> extractecItems;
    private String idAgente;
    public ProcesadorObjetivosImp1(ItfMotorDeReglas ruleEngine,ItfGestorTareas taskManager) {
	   this.ruleEngine = ruleEngine;
           this.taskManager = taskManager;
	}
    
    public ProcesadorObjetivosImp1() {
	
	}
 
    public void  SetItfMotorDeReglas (ItfMotorDeReglas itfMotor){
           this.ruleEngine = itfMotor;
    }
  
    public ItfMotorDeReglas GetItfMotorDeReglas (){
           return this.ruleEngine;
    }
    public void  SetAgentId (String identAgente){
           this.idAgente = identAgente;
    }  
    @Override
    public String   getAgentId (){
          return this.idAgente ;
    }
    public void  SetItfGestorTareas (ItfGestorTareas gestorTareas){
           this.taskManager = gestorTareas;
    }
   
    @Override
    public ItfGestorTareas GetItfGestorTareas (){
           return this.taskManager ;
    }
	
    @Override
    public boolean procesarExtractedInfo(ExtractedInfo extrInfo) {
           // verificamos la cardinalidad del contenido de la informacion extraida. Si es mayor que uno se trata de una
           // colección de elementos que seran enviados al motor
           // Metemos en el motor el contenido de extrInfo
           if (extrInfo == null) return false;
           
           if ( !(extrInfo.isContentACollection())){    
//                Object msgContent = extrInfo.getContenido();			
                ruleEngine.assertFact(extrInfo.getContenido());
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
    public synchronized void actualizarHecho(Object fact) {
           ruleEngine.updateFact(fact);
    }
          
	//The three next methods do not call to fireAllRules inside its code.   
    @Override
    public synchronized void insertarHechoWithoutFireRules(Object fact) {
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
    @Override
    public ItfMotorDeReglas getItfMotorDeReglas (){
    return ruleEngine;
}
    @Override
    public boolean cambiarComportamiento(String identFicheroReglasComportamiento){
       InputStream reglas = this.getClass().getResourceAsStream(identFicheroReglasComportamiento);
       if ( ruleEngine.crearKbSesionConNuevasReglas(reglas, identFicheroReglasComportamiento)){
           //inicializamos la Memoria de trabajo con variables globales
           inicializaVariablesGlobales();
           return true;
       }else return false;
    }
    @Override
    public Collection <Object> copiarObjetosDeMiMemoria(){
      return ruleEngine.getStatefulKnowledgeSession().getObjects();
      
    }
    @Override
    public void insertarObjetosEnMiMemoria(Collection <Object> objetosAinsertar){
      
	Iterator <Object>  it = objetosAinsertar.iterator();
        
	    while( it.hasNext() ) {
                this.insertarHechoWithoutFireRules(it.next());
	    }
    }
    
  public void inicializaVariablesGlobales(){
       ruleEngine.addGlobalVariable(NombresPredefinidos.TASK_MANAGER_GLOBAL, taskManager);
       ruleEngine.addGlobalVariable(NombresPredefinidos.ITFUSO_RECURSOTRAZAS_GLOBAL, NombresPredefinidos.RECURSO_TRAZAS_OBJ);
       ruleEngine.addGlobalVariable(NombresPredefinidos.AGENT_ID_GLOBAL, idAgente);
   }

    @Override
    public void reinicializarComportamiento() {  
       ruleEngine.reinicializarSesion();
    }
    
}
