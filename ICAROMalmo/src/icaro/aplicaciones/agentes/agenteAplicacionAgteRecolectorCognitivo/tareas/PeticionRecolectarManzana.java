package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.tareas;

import javax.swing.JOptionPane;

import icaro.aplicaciones.Rosace.informacion.PropuestaAgente;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InfoDecidirRecolector;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos.RecolectarManzana;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos.RecolectarTodasLasManzanas;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.objetivos.DecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class PeticionRecolectarManzana extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		// TODO Auto-generated method stub
		RecolectarManzana recolectarManzana = (RecolectarManzana) params[0];
		DecidirQuienVa decidirQuienVa = (DecidirQuienVa) params[1];
		InfoDecidirRecolector infoDecidirRecolector = (InfoDecidirRecolector) params[2];
		Focus foco = (Focus) params[3];
		RecolectarTodasLasManzanas obj2 = (RecolectarTodasLasManzanas) params[4];
		System.out.println("Voy a seleccionar el mejor agente");
		String agenteDestino =  infoDecidirRecolector.selectBestAgent();
		System.out.println("El agente: " + agenteDestino); 
		Thread t = new Thread(){
			public void run(){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				PropuestaAgente miPropuesta = new PropuestaAgente (identAgente);
				miPropuesta.setMensajePropuesta(VocabularioRosace.MsgPropuesta_Para_Aceptar_Objetivo);
				miPropuesta.setIdentObjectRefPropuesta(recolectarManzana.getManzana().getId());
				miPropuesta.setJustificacion(recolectarManzana.getManzana());
				getComunicator().enviarInfoAotroAgente(miPropuesta, agenteDestino);
				
				
			}
		};
		if(agenteDestino != null){
			this.trazas.aceptaNuevaTrazaEjecReglas(this.identAgente, "             Agente seleccionado: " + agenteDestino + ". Id manzana: "+ recolectarManzana.getManzana().getId());
			recolectarManzana.setAgentID(agenteDestino);
			if(!this.identAgente.equalsIgnoreCase(agenteDestino))
				recolectarManzana.setSolving();
			decidirQuienVa.setSolved();
			foco.setFoco(obj2);
			itfProcObjetivos.actualizarHechoWithoutFireRules(decidirQuienVa);
			itfProcObjetivos.eliminarHechoWithoutFireRules(infoDecidirRecolector);
			itfProcObjetivos.actualizarHechoWithoutFireRules(recolectarManzana);
			itfProcObjetivos.actualizarHecho(foco);
			t.start();
		}
		else{
			JOptionPane.showMessageDialog(null, "Agente null en la seleccion");
		}
	}

}
