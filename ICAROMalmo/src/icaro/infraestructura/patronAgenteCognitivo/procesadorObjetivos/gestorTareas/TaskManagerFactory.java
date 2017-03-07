/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.gestorTareas;

import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ItfProcesadorObjetivos;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.gestorTareas.imp.TaskManagerFactoryImp;



/**
 * Factory for Task Manager of the Cognitive Agent
 * @author carf
 * @author Carlos Celorrio
 */
public abstract class TaskManagerFactory {
	
	private static final String FACTORY_IMP_PROPERTY = "icaro.infraestructura.PatronAgenteCognitivo.knowledgeProcessor.taskManager.factory.imp";
	
    private static TaskManagerFactory instance;
    
    /**
     * Gets the singleton instance of this factory.
     * @return A cognitive agent task manager factory
     */
    public static TaskManagerFactory instance(){
        if(instance==null){
            String c = System.getProperty(FACTORY_IMP_PROPERTY,
                    TaskManagerFactoryImp.class.getName());
            try{
                instance = (TaskManagerFactory) Class.forName(c).newInstance();
            }catch(Exception ex){
                throw new RuntimeException("Implementation not found for: " + c);
            }
        }
        return instance;

    }
    
    public abstract ItfGestorTareas createTaskManager(AgenteCognitivo agente,
            ItfProcesadorObjetivos envioHechos);
}
