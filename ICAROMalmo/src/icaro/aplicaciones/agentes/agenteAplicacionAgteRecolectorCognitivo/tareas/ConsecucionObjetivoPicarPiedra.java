package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.PropuestaAgente;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformePiedra;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformeRio;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos.ConstruirPuente;
import icaro.aplicaciones.recursos.recursoMalmo.ItfUsoRecursoMalmo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class ConsecucionObjetivoPicarPiedra extends TareaSincrona{
	ItfUsoRecursoMalmo itfMalmo;
	@Override
	public void ejecutar(Object... params) {
		// TODO Auto-generated method stub
		PropuestaAgente propuesta = (PropuestaAgente) params[0];
		try {
			itfMalmo = (ItfUsoRecursoMalmo) this.repoInterfaces.obtenerInterfazGestion(VocabularioRosace.IdentRecursoMalmo);
			new Thread(){
				public void run(){
					try {
						//itfMalmo.trataMensajeInforme(propuesta.getJustificacion());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.itfProcObjetivos.eliminarHecho(propuesta);
		
	}

}
