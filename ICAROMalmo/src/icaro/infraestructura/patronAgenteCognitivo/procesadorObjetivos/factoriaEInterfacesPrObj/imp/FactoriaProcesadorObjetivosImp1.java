package icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.imp;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.FactoriaProcesadorObjetivos;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ItfProcesadorObjetivos;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ProcesadorObjetivos;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.gestorTareas.ItfGestorTareas;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.gestorTareas.TaskManagerFactory;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.motorDeReglas.FactoriaMotorDeReglas;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.motorDeReglas.ItfMotorDeReglas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import java.io.InputStream;
import org.apache.log4j.Logger;

/**
 * Implementation for Cognitive Agent Control Factory
 * @author Carlos Celorrio
 * Modificacion F Garijo  28/08/2012
 *
 */
public class FactoriaProcesadorObjetivosImp1 extends FactoriaProcesadorObjetivos {
	private Logger log = Logger.getLogger(FactoriaProcesadorObjetivosImp1.class);
	@Override
	public ProcesadorObjetivos crearProcesadorObjetivos( AgenteCognitivo agente,String ficheroResolucionObjetivos ) throws ExcepcionEnComponente {			
		String identAgte = agente.getIdentAgente();
            	InputStream reglas ;
		reglas = this.getClass().getResourceAsStream(ficheroResolucionObjetivos);
                ItfUsoRecursoTrazas trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
                     try {
	   	      if (reglas!=null) {  
		//	ItfMotorDeReglas motorDeReglas = FactoriaMotorDeReglas.instance().crearMotorDeReglas(agente,reglas);
                        ItfMotorDeReglas motorDeReglas = FactoriaMotorDeReglas.instance().crearMotorDeReglas(agente,reglas,ficheroResolucionObjetivos);
                        ProcesadorObjetivosImp1  procObj = new ProcesadorObjetivosImp1();
                        procObj.SetAgentId(identAgte);
                        procObj.SetItfMotorDeReglas(motorDeReglas);             
                        ItfProcesadorObjetivos itfProcObj = procObj;
			ItfGestorTareas taskManager = TaskManagerFactory.instance().createTaskManager(agente, itfProcObj);		
                //        ItfUsoRepositorioInterfaces repoIntfs = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
                        procObj.SetItfGestorTareas(taskManager);
                        procObj.inicializaVariablesGlobales();
			return procObj;
//                        log.info("Compiling rules...");
                    //    System.out.println("Compiling rules...");
                   //     motorDeReglas.compileRules2(reglasF);
                   //     motorDeReglas.compileRules(reglas); 
                        
                    //	motorDeReglas.addGlobalVariable(NombresPredefinidos.TASK_MANAGER_GLOBAL, taskManager);
                    //    motorDeReglas.addGlobalVariable(NombresPredefinidos.ITFUSO_RECURSOTRAZAS_GLOBAL, trazas);
                    //    motorDeReglas.addGlobalVariable(NombresPredefinidos.AGENT_ID_GLOBAL, agentName);
                        
                    //    int index = getNumberStartIndex(agentName);
                    //    String number = getNumber(agentName,index);
                    //	log.info("Rules Compiled Successfully.");
                        
		      } else {
			    String msg = "Rules not found for "+identAgte +"\n Check the "+ ficheroResolucionObjetivos+" is in classpath";
			    trazas.trazar(identAgte, msg, InfoTraza.NivelTraza.error) ;     
                                    log.error(msg);
                                        //                    ExcepcionEnComponente(String identComponente,String causa, String identParteAfectada,String contextoExcepcion )
			            throw new ExcepcionEnComponente("FactoriaMotorDeReglas",msg, "MotorDeReglas", "Creacion del Motor de Reglas");
		}
		} catch (Exception e) {
			log.error("Errors compiling rules for agent "+identAgte, e);
			throw new ExcepcionEnComponente("FactoriaMotorDeReglas","Errors compiling rules for agent "+identAgte, "MotorDeReglas", "Creacion del Motor de Reglas");
		}
	}
       
 //El string finaliza en un número.
    //Este método devuelve la posición en el que empieza el numero.
    private int getNumberStartIndex(String s){
    	
    	int index=0;
    	
    	for (int x=s.length()-1;x>=0;x--){
    		char ch = s.charAt(x);
    		String sch = "" + ch;
    		int chint = (int)ch;    		
    		int numberchint = chint - 48; //48 es el valor ascii del 0

    		if ((numberchint<0) || (numberchint >= 10)) //no es un numero
    		   {   
    			  return x+1;
    		}                   		
    	}
    	return index;
    }
    
    //El string finaliza en un número.
    //Este método devuelve el substring que contiene el numero.    
    private String getNumber(String s, int index){
    	String stringNumber;
    	stringNumber = s.substring(index);    	    	
    	return stringNumber;
    }

}
