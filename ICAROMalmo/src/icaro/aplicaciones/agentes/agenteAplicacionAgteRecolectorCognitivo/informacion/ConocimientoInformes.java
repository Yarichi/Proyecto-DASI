package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion;

import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.imp.AgenteCognitivotImp2;

import java.util.HashMap;
import java.util.Iterator;

public class ConocimientoInformes {
	private HashMap<Class, String> formatoInformes;
	
	public ConocimientoInformes(){
		this.formatoInformes = new HashMap<Class, String>();
	}
	
	public void initConocimiento(InformeAbs[] args){
		for(InformeAbs inf : args){
			this.formatoInformes.put(inf.getClass(), null);
		}
	}
	
	public void addFormato(InformeAbs inf, String formato){
		this.formatoInformes.put(inf.getClass(), formato);
	}
	
	public boolean existFormato(InformeAbs inf){
		return this.formatoInformes.get(inf.getClass()) != null;
	}
	public String getFormato(InformeAbs inf){
		return this.formatoInformes.get(inf.getClass());
	}
	
	public String getMensaje(InformeAbs inf){
		return this.buildMensaje(inf);
	}
	
	private String buildMensaje(InformeAbs inf){
		String formato = this.formatoInformes.get(inf.getClass());
		String[] formatoSplit = formato.split(" ");
		String mensaje = formatoSplit[0];
		for(int i = 1; i < formatoSplit.length; i++){
			mensaje +=  (" " + this.parsePart(inf, formatoSplit[i]));
		}
		return mensaje;
	}
	
	private String parsePart(InformeAbs inf, String part){
		if(part.equals(VocabularioRosace.parte_informe_orientacion)){
			return Integer.toString(inf.getOrientacionAgente());
		}
		else if(part.equals(VocabularioRosace.parte_informe_coordObjX)){
			return Double.toString(inf.getCoordenadaObj().getX());
		}
		else if(part.equals(VocabularioRosace.parte_informe_coordObjZ)){
			return Double.toString(inf.getCoordenadaObj().getZ());
		}
		else if(part.equals(VocabularioRosace.parte_informe_coordObsX)){
			return Double.toString(inf.getCoordenadaObs().getX());
		}
		else if(part.equals(VocabularioRosace.parte_informe_coordObsZ)){
			return Double.toString(inf.getCoordenadaObs().getZ());
		}
		else if(part.equals(VocabularioRosace.parte_informe_idAgente)){
			return inf.getIdAgenteInvolucrado();
		}
		else return null;
	}
}
