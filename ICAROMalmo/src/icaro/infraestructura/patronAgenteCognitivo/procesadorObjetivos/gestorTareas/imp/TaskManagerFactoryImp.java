/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.gestorTareas.imp;

import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ItfProcesadorObjetivos;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.gestorTareas.ItfGestorTareas;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.gestorTareas.TaskManagerFactory;



/**
 *
 * @author carf
 */
public class TaskManagerFactoryImp extends TaskManagerFactory {

    @Override
    public ItfGestorTareas createTaskManager(AgenteCognitivo agent, ItfProcesadorObjetivos envioHechos) {
        return new GestorTareasImp(agent,envioHechos);
    }

}
