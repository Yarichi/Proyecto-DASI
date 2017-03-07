
/*
 * Creado 3 de mayo de 2010, 11:56
 * Modificado 5 de noviembre de 2011
 *
 * @author Francisco J Garijo & JM Gascuegna
 */
 
package icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.motorDeReglas.imp;
//import icaro.aplicaciones.recursos.recursoMorse.imp.configDebugging;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.motorDeReglas.ItfConfigMotorDeReglas;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.motorDeReglas.ItfMotorDeReglas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.compiler.DroolsParserException;
import org.drools.compiler.PackageBuilder;
import org.drools.event.rule.*;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.FactHandle;
import org.drools.runtime.rule.WorkingMemoryEntryPoint;

/**
 * Drools implementation of the Rule Engine
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 * @author Carlos Celorrio
 * @author JM Gascuegna
 */
public class MotorDeReglasDroolsImp5 implements ItfMotorDeReglas,ItfConfigMotorDeReglas {

    private AgenteCognitivo agent;               //inicializada en el constructor
    private StatefulKnowledgeSession kSesion = null;   //inicializada en el metodo compileRules
    private WorkingMemoryEntryPoint entrypoint ; //inicializada en el metodo compileRules  
    private ItfUsoRecursoTrazas trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
    private ArrayList <String> ficheroReglasCompilados = new ArrayList();
    private ArrayList<KnowledgeBuilder>  KbuildersObtenidos=new ArrayList();
    private KnowledgeBuilder kbuilder;
    private FactoriaMotorDeReglasDroolsImp1 miFactoria ;
    private boolean depuracionActivationRulesDebugging = false;
    private boolean depuracionHechosInsertados  = false;
    private boolean depuracionHechosModificados  = false;
    private boolean depuracionWorkingMemoryDebugging = false;
    private boolean factHandlesMonitoringINSERT_DEBUGGING = false;
    private boolean factHandlesMonitoringRETRACT_DEBUGGING = false;
    private boolean factHandlesMonitoring_beforeActivationFired_DEBUGGING = false;
    private boolean factHandlesMonitoring_DEBUGGING = false;  //ANTES ESTABA A true      
    private boolean factHandlesMonitoringUPDATE_DEBUGGING = false;
    private boolean factHandlesMonitoring_afterActivationFired_DEBUGGING = false;
    private boolean terminarSesion=false;
	//variables para la depuracion
//	private int index; 
//	private String number; //numero de agente. Ejemplo IAMasterCognitivo1 --> number es 1
//	private ItfUsoRecursoDepuracionCognitivo itfUsoRecursoDepuracionCognitivo;
        private String agentId;
	
	/**
	 * Constructor for the drools implementation
	 * @param agent Cognitive Agent
	 */
    public MotorDeReglasDroolsImp5(AgenteCognitivo agent) {
		
	  	this.agent = agent; //referencia al agente
                this.agentId= agent.getIdentAgente();
	
    }
    public MotorDeReglasDroolsImp5(AgenteCognitivo agent, FactoriaMotorDeReglasDroolsImp1 factoriaMtReglas) {
		
	  	this.agent = agent; //referencia al agente
                this.agentId= agent.getIdentAgente();
                this.miFactoria = factoriaMtReglas;
	
    }
    @Override
    public void fireRules() {
        kSesion.fireAllRules();
        if(terminarSesion)kSesion.dispose();
                                    
	}
    public boolean compilarReglas(InputStream fichero, String identFicheroReglas) {
        
        // verifico que no estan ya compiladas sin errores
     //  String ficheroReglas = fichero.toString() ;
       int indiceFicheroEnArray = ficheroReglasCompilados.indexOf(identFicheroReglas);
 //       if (!ficheroReglasCompilados.isEmpty()){        
           if (indiceFicheroEnArray>=0){
               kbuilder = KbuildersObtenidos.get(indiceFicheroEnArray);
               return true;
           }
        else{ // se debe compilar
   //     PackageBuilder builder = new PackageBuilder();
        try {
   //            kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
              Resource rsc = ResourceFactory.newInputStreamResource( fichero );
              kbuilder.add( rsc,ResourceType.DRL );
              if ( kbuilder.hasErrors() ) {
                  System.out.println("Problemas con el fichero : "+ identFicheroReglas);
                   System.err.println( kbuilder.getErrors().toString() );
                   trazas.aceptaNuevaTraza(new InfoTraza(agentId,"Motor Drools : ERROR al compilar las regls. " + identFicheroReglas,InfoTraza.NivelTraza.error));
                   throw new RuntimeException( "Unable to compile");
                   
              }else{
                  int ultimoIndiceInsercion = ficheroReglasCompilados.size();
                  ficheroReglasCompilados.add(ultimoIndiceInsercion, identFicheroReglas);
                  KbuildersObtenidos.add(ultimoIndiceInsercion,kbuilder);               
                  return true;
              }
              } catch (Exception e) {
             trazas.aceptaNuevaTraza(new InfoTraza(agentId,"RuleEngine: ERROR compiling the rules. " + e,InfoTraza.NivelTraza.error));
             e.printStackTrace(); 
        }
          return false;
           }
    }
    public void crearSesionConConfiguracionStandard(KnowledgeBuilder kbuilder){
         KnowledgeBaseConfiguration kbaseconfiguration = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
    //          kbaseconfiguration.setProperty(AssertBehaviorOption.PROPERTY_NAME, "equality");
              
              System.out.println("\n\n\n\n");
              System.out.println("\nLISTADO DE LAS PROPIEDADES UTILIZADAS PARA LA CONFIGURACION DEL MOTOR DEL AGENTE " + this.agent.getIdentAgente() + "......\n");
              System.out.println("----------------------------------------------------------------");
              
              System.out.println("AssertBehaviorOption->" + kbaseconfiguration.getProperty(org.drools.conf.AssertBehaviorOption.PROPERTY_NAME));              
              System.out.println("EventProcessingOption->" + kbaseconfiguration.getProperty(org.drools.conf.EventProcessingOption.PROPERTY_NAME));              
              System.out.println("IndexLeftBetaMemoryOption->" + kbaseconfiguration.getProperty(org.drools.conf.IndexLeftBetaMemoryOption.PROPERTY_NAME));
              System.out.println("IndexRightBetaMemoryOption->" + kbaseconfiguration.getProperty(org.drools.conf.IndexRightBetaMemoryOption.PROPERTY_NAME));
              System.out.println("LogicalOverrideOption->" + kbaseconfiguration.getProperty(org.drools.conf.LogicalOverrideOption.PROPERTY_NAME));              
              System.out.println("MaintainTMSOption->" + kbaseconfiguration.getProperty(org.drools.conf.MaintainTMSOption.PROPERTY_NAME));              
              System.out.println("MBeansOption->" + kbaseconfiguration.getProperty(org.drools.conf.MBeansOption.PROPERTY_NAME));              
              System.out.println("MultithreadEvaluationOption->" + kbaseconfiguration.getProperty(org.drools.conf.MultithreadEvaluationOption.PROPERTY_NAME));              
              System.out.println("RemoveIdentitiesOption->" + kbaseconfiguration.getProperty(org.drools.conf.RemoveIdentitiesOption.PROPERTY_NAME));
              System.out.println("SequentialAgendaOption->" + kbaseconfiguration.getProperty(org.drools.conf.SequentialAgendaOption.PROPERTY_NAME));
              System.out.println("SequentialOption->" + kbaseconfiguration.getProperty(org.drools.conf.SequentialOption.PROPERTY_NAME));
              System.out.println("ShareAlphaNodesOption->" + kbaseconfiguration.getProperty(org.drools.conf.ShareAlphaNodesOption.PROPERTY_NAME));
              System.out.println("ShareBetaNodesOption->" + kbaseconfiguration.getProperty(org.drools.conf.ShareBetaNodesOption.PROPERTY_NAME));
              System.out.println("----------------------------------------------------------------\n\n\n\n");                                                            
              KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase(kbaseconfiguration);
              kbase.addKnowledgePackages( kbuilder.getKnowledgePackages() );
              if(kSesion != null)kSesion.dispose();
              kSesion = kbase.newStatefulKnowledgeSession();
              entrypoint = kSesion.getWorkingMemoryEntryPoint("DEFAULT");
    }
    @Override
     public boolean crearKbSesionConNuevasReglas(InputStream fichero,String identFicheroReglas){
       // Suponemos que ya hay un sesion creada y ahora se pide una sesion nueva con otras reglas
         
         KnowledgeBuilder Kbuild = miFactoria.compilarReglas(agentId,fichero, identFicheroReglas);
         if ( Kbuild != null){
             kbuilder = Kbuild;
             crearSesionConConfiguracionStandard(kbuilder);
             return true;    
         }else return false;
     }
     public boolean actualizarKbSesionConNuevasReglas(InputStream fichero,String identFicheroReglas){
       // se crea una nueva sesion con la nuevas reglas y se incorporan los objetos de la memoria de la sesion anterior 
         
         KnowledgeBuilder Kbuild = miFactoria.compilarReglas(agentId,fichero, identFicheroReglas);
         if ( Kbuild != null){
             kbuilder = Kbuild;
             crearSesionConConfiguracionStandard(kbuilder);
             return true;    
         }else return false;
     }
   public void inicializaVariablesGlobales(){
 //      this.addGlobalVariable(NombresPredefinidos.TASK_MANAGER_GLOBAL, this.);
       this.addGlobalVariable(NombresPredefinidos.ITFUSO_RECURSOTRAZAS_GLOBAL, trazas);
       this.addGlobalVariable(NombresPredefinidos.AGENT_ID_GLOBAL, agentId);
   }
    
    @Override
    public void compileRules(InputStream fichero) throws Exception {
        PackageBuilder builder = new PackageBuilder();
        try {
              KnowledgeBuilder Kbuild = KnowledgeBuilderFactory.newKnowledgeBuilder();
              Resource rsc = ResourceFactory.newInputStreamResource( fichero );
              Kbuild.add( rsc,ResourceType.DRL );
              if ( Kbuild.hasErrors() ) {
                  System.out.println("Problemas con el fichero : "+ fichero.toString());
                   System.err.println( Kbuild.getErrors().toString() );
                   throw new RuntimeException( "Unable to compile");
              }

              //FRAGMENTO NUEVO
              KnowledgeBaseConfiguration kbaseconfiguration = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
    //          kbaseconfiguration.setProperty(AssertBehaviorOption.PROPERTY_NAME, "equality");
              
              System.out.println("\n\n\n\n");
              System.out.println("\nLISTADO DE LAS PROPIEDADES UTILIZADAS PARA LA CONFIGURACION DEL MOTOR DEL AGENTE " + this.agent.getIdentAgente() + "......\n");
              System.out.println("----------------------------------------------------------------");
              
              System.out.println("AssertBehaviorOption->" + kbaseconfiguration.getProperty(org.drools.conf.AssertBehaviorOption.PROPERTY_NAME));              
              System.out.println("EventProcessingOption->" + kbaseconfiguration.getProperty(org.drools.conf.EventProcessingOption.PROPERTY_NAME));              
              System.out.println("IndexLeftBetaMemoryOption->" + kbaseconfiguration.getProperty(org.drools.conf.IndexLeftBetaMemoryOption.PROPERTY_NAME));
              System.out.println("IndexRightBetaMemoryOption->" + kbaseconfiguration.getProperty(org.drools.conf.IndexRightBetaMemoryOption.PROPERTY_NAME));
              System.out.println("LogicalOverrideOption->" + kbaseconfiguration.getProperty(org.drools.conf.LogicalOverrideOption.PROPERTY_NAME));              
              System.out.println("MaintainTMSOption->" + kbaseconfiguration.getProperty(org.drools.conf.MaintainTMSOption.PROPERTY_NAME));              
              System.out.println("MBeansOption->" + kbaseconfiguration.getProperty(org.drools.conf.MBeansOption.PROPERTY_NAME));              
              System.out.println("MultithreadEvaluationOption->" + kbaseconfiguration.getProperty(org.drools.conf.MultithreadEvaluationOption.PROPERTY_NAME));              
              System.out.println("RemoveIdentitiesOption->" + kbaseconfiguration.getProperty(org.drools.conf.RemoveIdentitiesOption.PROPERTY_NAME));
              System.out.println("SequentialAgendaOption->" + kbaseconfiguration.getProperty(org.drools.conf.SequentialAgendaOption.PROPERTY_NAME));
              System.out.println("SequentialOption->" + kbaseconfiguration.getProperty(org.drools.conf.SequentialOption.PROPERTY_NAME));
              System.out.println("ShareAlphaNodesOption->" + kbaseconfiguration.getProperty(org.drools.conf.ShareAlphaNodesOption.PROPERTY_NAME));
              System.out.println("ShareBetaNodesOption->" + kbaseconfiguration.getProperty(org.drools.conf.ShareBetaNodesOption.PROPERTY_NAME));
              System.out.println("----------------------------------------------------------------\n\n\n\n");
                                                         
              
              KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase(kbaseconfiguration);            	                
              //FIN FRAGMENTO NUEVO        
//              KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
              kbase.addKnowledgePackages( Kbuild.getKnowledgePackages() );
              kSesion = kbase.newStatefulKnowledgeSession();
              entrypoint = kSesion.getWorkingMemoryEntryPoint("DEFAULT");
              if (depuracionActivationRulesDebugging)trazarRuleActivation();
              

//////////////////////////////////////////////////              if (ConfigDebugging.WORKINGMEMORY_DEBUGGING==1){
              if (depuracionWorkingMemoryDebugging)trazarWorkingMemory ();//fin del if depuracion working memory
                trazas.aceptaNuevaTraza(new InfoTraza(agentId,"RuleEngine: Rules compiled successfully. ",
                                                    InfoTraza.NivelTraza.debug));
        } catch (Exception e) {
             trazas.aceptaNuevaTraza(new InfoTraza(agentId,"RuleEngine: ERROR compiling the rules. " + e,
                                                   InfoTraza.NivelTraza.error));
             e.printStackTrace();
        }
    }
    //fin metodo compileRules
public void compileRules2(URL fichero)  {
        PackageBuilder builder = new PackageBuilder();
        try {
              KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
           
              Resource rsc = ResourceFactory.newUrlResource(fichero);
              kbuilder.add( rsc,ResourceType.DRL );
              if ( kbuilder.hasErrors() ) {
                  System.out.println("Problemas con el fichero : "+ fichero.toString());
                  File f = new File (fichero.toString());
                    System.out.println("PATH ABSOLUTO: "+f.getAbsolutePath());
                   System.err.println( kbuilder.getErrors().toString() );
                   throw new RuntimeException( "Unable to compile");
              }
              //FRAGMENTO NUEVO
              KnowledgeBaseConfiguration kbaseconfiguration = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
    //          kbaseconfiguration.setProperty(AssertBehaviorOption.PROPERTY_NAME, "equality");
              
              System.out.println("\n\n\n\n");
              System.out.println("\nLISTADO DE LAS PROPIEDADES UTILIZADAS PARA LA CONFIGURACION DEL MOTOR DEL AGENTE " + this.agent.getIdentAgente() + "......\n");
              System.out.println("----------------------------------------------------------------");
              
              System.out.println("AssertBehaviorOption->" + kbaseconfiguration.getProperty(org.drools.conf.AssertBehaviorOption.PROPERTY_NAME));              
              System.out.println("EventProcessingOption->" + kbaseconfiguration.getProperty(org.drools.conf.EventProcessingOption.PROPERTY_NAME));              
              System.out.println("IndexLeftBetaMemoryOption->" + kbaseconfiguration.getProperty(org.drools.conf.IndexLeftBetaMemoryOption.PROPERTY_NAME));
              System.out.println("IndexRightBetaMemoryOption->" + kbaseconfiguration.getProperty(org.drools.conf.IndexRightBetaMemoryOption.PROPERTY_NAME));
              System.out.println("LogicalOverrideOption->" + kbaseconfiguration.getProperty(org.drools.conf.LogicalOverrideOption.PROPERTY_NAME));              
              System.out.println("MaintainTMSOption->" + kbaseconfiguration.getProperty(org.drools.conf.MaintainTMSOption.PROPERTY_NAME));              
              System.out.println("MBeansOption->" + kbaseconfiguration.getProperty(org.drools.conf.MBeansOption.PROPERTY_NAME));              
              System.out.println("MultithreadEvaluationOption->" + kbaseconfiguration.getProperty(org.drools.conf.MultithreadEvaluationOption.PROPERTY_NAME));              
              System.out.println("RemoveIdentitiesOption->" + kbaseconfiguration.getProperty(org.drools.conf.RemoveIdentitiesOption.PROPERTY_NAME));
              System.out.println("SequentialAgendaOption->" + kbaseconfiguration.getProperty(org.drools.conf.SequentialAgendaOption.PROPERTY_NAME));
              System.out.println("SequentialOption->" + kbaseconfiguration.getProperty(org.drools.conf.SequentialOption.PROPERTY_NAME));
              System.out.println("ShareAlphaNodesOption->" + kbaseconfiguration.getProperty(org.drools.conf.ShareAlphaNodesOption.PROPERTY_NAME));
              System.out.println("ShareBetaNodesOption->" + kbaseconfiguration.getProperty(org.drools.conf.ShareBetaNodesOption.PROPERTY_NAME));
              System.out.println("----------------------------------------------------------------\n\n\n\n");
                                                         
              
              KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase(kbaseconfiguration);            	                
              //FIN FRAGMENTO NUEVO        
//              KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
              kbase.addKnowledgePackages( kbuilder.getKnowledgePackages() );
              kSesion = kbase.newStatefulKnowledgeSession();
              entrypoint = kSesion.getWorkingMemoryEntryPoint("DEFAULT");
           if (depuracionActivationRulesDebugging)trazarRuleActivation();
             

//////////////////////////////////////////////////              if (ConfigDebugging.WORKINGMEMORY_DEBUGGING==1){
              if (depuracionWorkingMemoryDebugging)trazarWorkingMemory ();//fin del if depuracion working memory
                
              trazas.aceptaNuevaTraza(new InfoTraza(agentId,"RuleEngine: Rules compiled successfully. ",
                                                    InfoTraza.NivelTraza.debug));
        } catch (Exception e) {
            trazas.trazar(agentId, "RuleEngine: ERROR compiling the rules. " +fichero + " ---"+"\n" + e, NivelTraza.error);
             File f = new File (fichero.toString());
                    System.out.println("PATH ABSOLUTO: "+f.getAbsolutePath());                   
             e.printStackTrace();
        }
    }
    
    //El metodo assertFact se llama cuando desde una TAREA se hace una llamada del tipo this.getEnvioHechos().insertarHecho(...)
    @Override
    public synchronized void assertFact(Object objeto) {
		if (depuracionHechosInsertados)
		trazas.aceptaNuevaTraza(new InfoTraza(agentId, "Motor de Reglas : nuevo Hecho insertado : " + " clase : " + objeto.getClass().getSimpleName() + objeto,
                                                    InfoTraza.NivelTraza.debug));
                if (objeto==null){
                    trazas.aceptaNuevaTraza(new InfoTraza(agentId, "Motor de Reglas : Se intenta insertar en el motor un objeto null : ",
                                                    InfoTraza.NivelTraza.error));
                }else {
                kSesion.insert(objeto);
                kSesion.fireAllRules();
                 
                }
	}
	

    //El metodo retracttFact se llama cuando desde una TAREA se hace una llamada del tipo this.getEnvioHechos().eliminarHecho(...)	
    @Override
    public synchronized void retracttFact(Object objeto) {
        if (depuracionHechosModificados)
		trazas.aceptaNuevaTraza(new InfoTraza( agentId,"MRuleEngine: new fact retracted: "+ objeto,
							                    InfoTraza.NivelTraza.debug) );
                 if (objeto==null){
                    trazas.aceptaNuevaTraza(new InfoTraza(agentId, "Motor de Reglas : Se intenta insertar en el motor un objeto null : ",
                                                    InfoTraza.NivelTraza.error));
                }else {
                FactHandle fh2 = entrypoint.insert(objeto);    
                entrypoint.retract(fh2);       
                kSesion.fireAllRules();
                 }
        
	}
        
    //El metodo updateFact se llama cuando desde una TAREA se hace una llamada del tipo this.getEnvioHechos().actualizarHecho(...)	    
    @Override
    public synchronized void updateFact(Object objeto) {
        if (depuracionHechosModificados)
	trazas.aceptaNuevaTraza(new InfoTraza(agentId,"MRuleEngine: new fact updated: "+ objeto,
                                               InfoTraza.NivelTraza.debug));
        
        if (objeto==null){
                    trazas.aceptaNuevaTraza(new InfoTraza(agentId, "Motor de Reglas : Se intenta insertar en el motor un objeto null : ",
                                                    InfoTraza.NivelTraza.error));
                }else {
                FactHandle fh2 = entrypoint.insert(objeto);    
                entrypoint.update(fh2,objeto);       
                kSesion.fireAllRules();
                 }
      
	}


    //El metodo assertFactWithoutFireRules se llama cuando desde una TAREA se hace una llamada del tipo 
    //this.getEnvioHechos().insertarHechoWithoutFireRules(...)	        
	//The next method does not call to fireAllRules inside its code.       
    @Override
    public synchronized void assertFactWithoutFireRules(Object objeto) {
    	if (depuracionHechosInsertados)
         trazas.aceptaNuevaTraza(new InfoTraza(agentId,"Motor de Reglas : assertFactWithoutFireRules-> nuevo Hecho insertado : " + objeto,
                                                        InfoTraza.NivelTraza.debug));
         if (objeto==null){
                    trazas.aceptaNuevaTraza(new InfoTraza(agentId, "Motor de Reglas : Se intenta insertar en el motor un objeto null : ",
                                                    InfoTraza.NivelTraza.error));
                }else {
                kSesion.insert(objeto);
//                 kSesion.fireAllRules();
                }
     
    }   
    //El metodo retractFactWithoutFireRules se llama cuando desde una TAREA se hace una llamada del tipo 
    //this.getEnvioHechos().eliminarHechoWithoutFireRules(...)	            
	//The next method does not call to fireAllRules inside its code.   
    @Override
    public synchronized void retractFactWithoutFireRules(Object objeto) {
        if (depuracionHechosModificados)
	trazas.aceptaNuevaTraza(new InfoTraza(agentId,"MRuleEngine: retractFactWithoutFireRules -> new fact retracted: "+ objeto,
                                                        InfoTraza.NivelTraza.debug));
        if (objeto==null){
                    trazas.aceptaNuevaTraza(new InfoTraza(agentId, "Motor de Reglas : Se intenta insertar en el motor un objeto null : ",
                                                    InfoTraza.NivelTraza.error));
                }else {
                FactHandle fh2 = entrypoint.insert(objeto);    
                entrypoint.retract(fh2);       
//                kSesion.fireAllRules();
                 }
        
	}
    //El metodo updateFactWithoutFireRules se llama cuando desde una TAREA se hace una llamada del tipo 
    //this.getEnvioHechos().actualizarHechoWithoutFireRules(...)	                
	//The next method does not call to fireAllRules inside its code.   
    @Override
    public synchronized void updateFactWithoutFireRules(Object objeto) {
    	        if (depuracionHechosModificados)  
		trazas.aceptaNuevaTraza(new InfoTraza( agentId,"MRuleEngine: updateFactWithoutFireRules-> new fact updated: "+ objeto,
                                                        InfoTraza.NivelTraza.debug));
    if (objeto==null){
                    trazas.aceptaNuevaTraza(new InfoTraza(agentId, "Motor de Reglas : Se intenta insertar en el motor un objeto null : ",
                                                    InfoTraza.NivelTraza.error));
                }else {
                FactHandle fh2 = entrypoint.insert(objeto);    
                entrypoint.update(fh2,objeto);       
//                kSesion.fireAllRules();
                 }
    }
       
    @Override
    public synchronized void addGlobalVariable(String nombre, Object object) {
		try {
	     	   kSesion.setGlobal(nombre, object);
		} catch(NullPointerException ex) {
               // log.error("ERROR al definir la variable global: " +nombre + " al agente. Revisar los atributos y valores de los objetos definidos en las reglas " +agent .getIdentAgente(), ex);
			   trazas.aceptaNuevaTraza(new InfoTraza(agent.getIdentAgente(),
					            "ERROR al definir la variable global: " +nombre + " al agente . Revisar los atributos y valores de los objetos definidos en las reglas " ,
					            InfoTraza.NivelTraza.debug));
		}
	}
	
    @Override
    public StatefulKnowledgeSession getStatefulKnowledgeSession(){
    	return kSesion;   
    }

	
	//type --> INSERT, RETRACT o UPDATE;    
	//object--> el objeto insertado, borrado o actualizado
    private void FactHandlesMonitoring_DEBUGGING(String monitoringType, String wmObject){
        Collection<FactHandle> cFH;
	    String s;
	    Iterator it;
	    cFH = kSesion.getFactHandles();
	    s = "";
	    it = cFH.iterator();
	    while( it.hasNext() ) {
		     s = s + " \n " + it.next() ;
	    }
	    
	    if (monitoringType.equals("INSERT")){
           
	       if (factHandlesMonitoringINSERT_DEBUGGING){	
	  	      String info = "FactHandles WM _ despues de INSERT " + wmObject + 
				            "( current size="+ kSesion.getFactHandles().size() + "): " + s + "\n\n";
              try {
                 //  	  this.itfUsoRecursoDepuracionCognitivo.mostrarInfoWM(info);
                   trazas.aceptaNuevaTrazaActivReglas(agentId, info);
              } catch (Exception e) {
	            e.printStackTrace();
              }	    
	          
	       }	       
	    }

	    if (monitoringType.equals("RETRACT")){
		   if (factHandlesMonitoringRETRACT_DEBUGGING){		    	
		  	   String info = "FactHandles WM _ despues de RETRACT " + wmObject +
  		  	                 "( current size="+ kSesion.getFactHandles().size() + "): " + s + "\n\n";  		   
               try {
              	 //     this.itfUsoRecursoDepuracionCognitivo.mostrarInfoWM(info);
                    trazas.aceptaNuevaTrazaActivReglas(agentId, info);
               } catch (Exception e) {
                      e.printStackTrace();
               }		       
		   }
		}
	    
	    if (monitoringType.equals("UPDATE")){
		   if (factHandlesMonitoringUPDATE_DEBUGGING){			   
		  	   String info = "FactHandles WM _ despues de UPDATE " + wmObject + 
 				             "( current size="+ kSesion.getFactHandles().size() + "): " + s + "\n\n";		  	   
               try {
	           //       this.itfUsoRecursoDepuracionCognitivo.mostrarInfoWM(info);
                    trazas.aceptaNuevaTrazaActivReglas(agentId, info);
               } catch (Exception e) {
                      e.printStackTrace();
               }		       
		   }
		}	    	    
	}
  private void trazarRuleActivation ()  {                          
       kSesion.addEventListener(new DefaultAgendaEventListener() {
     
                	  //Creeo que este metodo nos permite conocer como quedan los hechos que provoron que se dispare la regla
                	  //Observo que no se mostraran aquellos para los que se hace un retract
//  
        @Override
        public void activationCreated(ActivationCreatedEvent activationCreatedEvent) {

            trazas.aceptaNuevaTrazaEjecReglas(agentId,
    			"\n regla activada - " + activationCreatedEvent.getActivation().getRule().getName()+
                         "\n objetos en la activacion : " + activationCreatedEvent.getActivation().getObjects() );
//        log.debug("Activacion creada: "+activationCreatedEvent.toString());
        }    
        @Override
         public void afterActivationFired(AfterActivationFiredEvent event) {
                super.afterActivationFired( event );
                String ruleName = event.getActivation().getRule().getName();
            //Mostrar todos los facthandles de la memoria de trabajo
                if (factHandlesMonitoring_afterActivationFired_DEBUGGING){    	        	 
                        String info1 = "\n Rule fired -> " + ruleName + "\n";                             	        	
                        String info2 = "Facts in Agenda After  Rule fired -> " + event.getActivation().getFactHandles().toString() + "\n";  	    	
                        try {
                          	        //           itfUsoRecursoDepuracionCognitivo.mostrarInfoAR(info1);
                            trazas.aceptaNuevaTrazaActivReglas(agentId, info1+info2);
//                            itfUsoRecursoDepuracionCognitivo.mostrarInfoAR(info2);
                            } catch (Exception e) {
                            e.printStackTrace();
                        }                 	        	 
                 trazarFactHandlesForRuleDebugging(ruleName,NombresPredefinidos.DROOLS_Debugging_AFTER_RuleFired);    	        	 
              }
           }
                	  
         //Creeo que este metodo nos permite conocer los hechos que provocan que se dispare la regla
            @Override
    public void beforeActivationFired(BeforeActivationFiredEvent event) {
             	super.beforeActivationFired( event );
                String ruleName = event.getActivation().getRule().getName();
             	         //Mostrar todos los facthandles de la memoria de trabajo por la traza AR_Agente
             	if (factHandlesMonitoring_beforeActivationFired_DEBUGGING){                	        
                    String info1 = "\nActivate Rule -> " + ruleName + "\n";
                    String info2 = "Facts Activating Rule -> " + event.getActivation().getFactHandles().toString() + "\n";
                    trazas.aceptaNuevaTrazaActivReglas(agentId, info1+info2);
                 	    //     printFactHandlesMonitoring(NombresPredefinidos.DROOLS_Debugging_BEFORE_RuleFired,event.getActivation().getRule().getName()); 
                                trazarFactHandlesForRuleDebugging(ruleName,NombresPredefinidos.DROOLS_Debugging_BEFORE_RuleFired);
             	         }
                	  }
                	  
                  });  //fin del addEventListener          	              	  
              }
    private void trazarFactHandlesForRuleDebugging(String ruleId,String beforOrAfterRuleActivation ){       
	String infoAmostrar= "" ;
        Boolean obtenerFactHandles = false;
	
            if (factHandlesMonitoring_beforeActivationFired_DEBUGGING &&
                beforOrAfterRuleActivation.equals(NombresPredefinidos.DROOLS_Debugging_BEFORE_RuleFired)){
                obtenerFactHandles= true;
	    	infoAmostrar = "FactHandles WM _ ANTES de  dispararse la regla " + ruleId + 
	                  "( current size="+ kSesion.getFactHandles().size() + "): " +"\n";
	    }else if (factHandlesMonitoring_afterActivationFired_DEBUGGING &&
                beforOrAfterRuleActivation.equals(NombresPredefinidos.DROOLS_Debugging_AFTER_RuleFired)){
                obtenerFactHandles= true;
                infoAmostrar = "FactHandles WM _ DESPUES de  dispararse la regla " + ruleId + 
                        "( current size="+ kSesion.getFactHandles().size() + "): " +"\n";
                }       
            if (obtenerFactHandles){
                Collection<FactHandle> cFH;
                Iterator it;
                cFH = kSesion.getFactHandles();
                it = cFH.iterator();
                while( it.hasNext() ) {
		     infoAmostrar = infoAmostrar + it.next()+ " \n "  ;
                }
                 try {
	//		this.itfUsoRecursoDepuracionCognitivo.mostrarInfoAR(infoAmostrar);
                        trazas.aceptaNuevaTrazaActivReglas(agentId, infoAmostrar);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
            }
    }
	// str -> beforeActivationFired o afterActivationFired;   rule es el nombre de la regla
    private void printFactHandlesMonitoring(String str, String rule){
        Collection<FactHandle> cFH;
	    String s;
	    Iterator it;
	    cFH = kSesion.getFactHandles();
	    s = "";
	    it = cFH.iterator();
	    while( it.hasNext() ) {
		     s = s + " \n " + it.next() ;
	    }
	    
	    if (str.equals("afterActivationFired")){
	    	s = s + " \n ";
	    }
	    
	    String info = "FactHandles WM _ " + str + " dispararse la regla " + rule + 
	                  "( current size="+ kSesion.getFactHandles().size() + "): " + s + "\n";	    
	    try {
//			this.itfUsoRecursoDepuracionCognitivo.mostrarInfoAR(info);
                        trazas.aceptaNuevaTrazaActivReglas(agentId, info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    
	}
    private void  trazarWorkingMemory (){
       //MOSTRAR POR LA VENTANA DE TRAZAS LA EVOLUCION DE LA MEMORIA DE TRABAJO DEL AGENTE
       //HAY UNA PARA CADA AGENTE. EL NOMBRE DE LA TRAZA ES WM_NOMBREDELAINSTANCIADEAGENTE
      kSesion.addEventListener( new DefaultWorkingMemoryEventListener(){
             //Se reconoce tanto cuando (1) se hace un insert en una regla, como 
            //(2) se hace en una tarea un this.getEnvioHechos().insertarHecho(...)
    @Override
    public void objectInserted(ObjectInsertedEvent event){              	         

                String info = "INSERT->valor getObject: " + event.getObject().toString() + " , nroFactHandles -> " + 
          		  	                   kSesion.getFactHandles().size() + "\n";          		  	    	 
                try {
                  //  itfUsoRecursoDepuracionCognitivo.mostrarInfoWM(info); 
                      trazas.aceptaNuevaTrazaActivReglas(agentId, info);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }		       
                if (factHandlesMonitoring_DEBUGGING){
                    FactHandlesMonitoring_DEBUGGING("INSERT", event.getObject().toString());              	        	
                }
            }

            @Override
   public void objectRetracted(ObjectRetractedEvent event) { 
              	         //lo mas legible para depurar, vemos el hecho eliminado directamente              	         
                    String info = "RETRACT->valor getOldObject: " + event.getOldObject() + " , nroFactHandles -> " + 
          		  	                   kSesion.getFactHandles().size() + "\n";
                     try {
          	     //    itfUsoRecursoDepuracionCognitivo.mostrarInfoWM(info);  
                          trazas.aceptaNuevaTrazaActivReglas(agentId, info);
                         } catch (Exception e) {
                                 e.printStackTrace();
                         }              	                  	         
              	       if (factHandlesMonitoring_DEBUGGING){
              	        	 FactHandlesMonitoring_DEBUGGING("RETRACT", event.getOldObject().toString());
              	         }              	         
                 	  }
                	  
            @Override
   public void objectUpdated(ObjectUpdatedEvent event) {                	         
                    String info = "UPDATE->valor getOldObject: " + event.getOldObject()  + " , getObject->" + event.getObject() + 
          		  	                   " , nroFactHandles -> " + kSesion.getFactHandles().size() + "\n";
                    try {
                      //   itfUsoRecursoDepuracionCognitivo.mostrarInfoWM(info);  
                         trazas.aceptaNuevaTrazaActivReglas(agentId, info);
                    } catch (Exception e) {
                                e.printStackTrace();
                         }
                  	      
                    if (factHandlesMonitoring_DEBUGGING){
              	       FactHandlesMonitoring_DEBUGGING("UPDATE", event.getOldObject().toString());              	        	
              	    }
            }     	  
      });//fin del DefaultWorkingMemoryEventListener listener            	  
   }
    @Override
    public void reinicializarSesion() {
//        Iterator it;
	Collection<FactHandle> cFH = kSesion.getFactHandles();
	Iterator<FactHandle>    it = cFH.iterator();
	    while( it.hasNext() ) {
                kSesion.retract(it.next());
            }
    }
    
    @Override
  public ItfConfigMotorDeReglas getItfConfigMotorDeReglas (){
      return this;
  }
//    @Override
//    public ItfMotorDeReglas getItfMotorDeReglas (){
//      return this;
//  }
  @Override
  public void setDepuracionActivationRulesDebugging (boolean boolValor){
      depuracionActivationRulesDebugging = boolValor;
      
  }
  @Override
  public void setDepuracionHechosInsertados (boolean boolValor) {
      depuracionHechosInsertados = boolValor;

      
  }
  @Override
  public void setDepuracionHechosModificados (boolean boolValor) {
      depuracionHechosModificados = boolValor;
      
  }
    @Override
  public void setFactHandlesMonitoring_beforeActivationFired_DEBUGGING (boolean boolValor){
      factHandlesMonitoring_beforeActivationFired_DEBUGGING = boolValor;
      if (depuracionActivationRulesDebugging)trazarRuleActivation();
  }
    @Override
  public void setfactHandlesMonitoring_afterActivationFired_DEBUGGING (boolean boolValor){
      factHandlesMonitoring_afterActivationFired_DEBUGGING = boolValor;
      if (depuracionActivationRulesDebugging)trazarRuleActivation();
  }
    @Override
  public void setFactHandlesMonitoring_DEBUGGING (boolean boolValor){
      factHandlesMonitoring_DEBUGGING = boolValor;
  }
    @Override
  public void setFactHandlesMonitoringINSERT_DEBUGGING (boolean boolValor){
      factHandlesMonitoringINSERT_DEBUGGING = boolValor;
  }
    @Override
  public void setFactHandlesMonitoringRETRACT_DEBUGGING (boolean boolValor){
      factHandlesMonitoringRETRACT_DEBUGGING = boolValor;
  }
    @Override
  public void setFactHandlesMonitoringUPDATE_DEBUGGING (boolean boolValor){
      factHandlesMonitoringUPDATE_DEBUGGING = boolValor;
  }
  
//  
//  
//	public static void main(String[] args) throws DroolsParserException {
//		// Check compilation
//		try {
//			String rules = "/icaro/application/agent/deviceManagerCognitiveAgent/goalResolutionProcess/reglas.drl";
//
//			InputStream file = MotorDeReglasDroolsImp5.class.getResourceAsStream(rules);
//
//			PackageBuilder builder = new PackageBuilder();
//			try {
//				Reader source = new InputStreamReader(file);
//
//				builder.addPackageFromDrl(source);
//
//				// Check the builder for errors
//				if ( builder.hasErrors() ) {
//				    System.out.println( builder.getErrors().toString() );
//				    throw new RuntimeException( "Unable to compile");
//				}
//
//			} catch (Exception e) {
//
//				if ( builder.hasErrors() ) {
//				    System.out.println( builder.getErrors().toString() );
//				}
//				e.printStackTrace();
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

    

}
