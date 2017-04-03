package icaro.aplicaciones.recursos.recursoMalmo.imp;


import java.rmi.RemoteException;
import java.util.ArrayList;

import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Agente;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Manzana;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Obstaculo;
import icaro.aplicaciones.recursos.recursoMalmo.ItfUsoRecursoMalmo;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;

public class ClaseGeneradoraRecursoMalmo extends ImplRecursoSimple implements ItfUsoRecursoMalmo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8053587275334286680L;
	
	private PythonOrderDispatcher socket;
	
	public ClaseGeneradoraRecursoMalmo(String idRecurso) throws RemoteException {
		super(idRecurso);
		// TODO Auto-generated constructor stub
		this.socket = new PythonOrderDispatcher(17999);		
		
		
		
	}

	@Override
	public ArrayList<Obstaculo> getObstaculos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Agente> getAgentes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Agente getInformacionAgente(String idAgente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Manzana> getInformacionManzanas() {
		// TODO Auto-generated method stub
		return null;
	}

}
