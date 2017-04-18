package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.PropuestaAgente;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InfoDecidirRecolector;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos.RecolectarManzana;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.objetivos.DecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class PeticionRecolectarManzana extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		// TODO Auto-generated method stub
		RecolectarManzana recolectarManzana = (RecolectarManzana) params[0];
		DecidirQuienVa decidirQuienVa = (DecidirQuienVa) params[1];
		InfoDecidirRecolector infoDecidirRecolector = (InfoDecidirRecolector) params[2];
		String agenteDestino =  infoDecidirRecolector.selectBestAgent();
		if(agenteDestino != null){
			PropuestaAgente miPropuesta = new PropuestaAgente (this.identAgente);
			miPropuesta.setMensajePropuesta(VocabularioRosace.MsgPropuesta_Para_Aceptar_Objetivo);
			miPropuesta.setIdentObjectRefPropuesta(agenteDestino);
			miPropuesta.setJustificacion(recolectarManzana.getManzana());
			
			decidirQuienVa.setSolving();
			this.getComunicator().enviarInfoAotroAgente(miPropuesta, agenteDestino);
			
			
			this.itfProcObjetivos.actualizarHechoWithoutFireRules(decidirQuienVa);
			this.itfProcObjetivos.eliminarHechoWithoutFireRules(infoDecidirRecolector);
			this.itfProcObjetivos.actualizarHecho(recolectarManzana);
			
		}
		//trazas.aceptaNuevaTrazaEjecReglas(identAgente, "");
		
	}

}
