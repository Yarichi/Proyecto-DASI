package icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn;

import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFsinAcciones.ItfUsoAutomataEFsinAcciones;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteCognitivo.percepcion.PercepcionAgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ProcesadorObjetivos;
import java.rmi.server.UnicastRemoteObject;

/**
 * Cognitive Agent abstract class that provides use and management interfaces
 * @author carf
 * @author Carlos Celorrio
 */
public abstract class AgenteCognitivo extends UnicastRemoteObject implements ItfUsoAgenteCognitivo, InterfazGestion {
	
    public AgenteCognitivo () throws java.rmi.RemoteException { }
    public abstract void setComponentesInternos(ItfUsoAutomataEFsinAcciones itfAutomataCiclVidaAgente,PercepcionAgenteCognitivo percepcion,ProcesadorObjetivos procObjetivos);
    public abstract void setEstado(String estado);
    @Override
    public abstract String getIdentAgente();
    public abstract ProcesadorObjetivos getControl();
    public abstract PercepcionAgenteCognitivo getPercepcion();
    @Override
    public abstract void aceptaMensaje(MensajeSimple mensaje);
    @Override
    public abstract void aceptaEvento(EventoRecAgte evento);
}
