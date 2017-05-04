package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.PropuestaAgente;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Manzana;
import icaro.aplicaciones.recursos.recursoMalmo.ItfUsoRecursoMalmo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class ConsecucionObjetivoRecoleccion extends TareaSincrona{
	ItfUsoRecursoMalmo itfMalmo;
	PropuestaAgente propuesta;
	@Override
	public void ejecutar(Object... params) {
		// TODO Auto-generated method stub
		 propuesta = (PropuestaAgente) params[0];
		try {
			itfMalmo = (ItfUsoRecursoMalmo) this.repoInterfaces.obtenerInterfazGestion(VocabularioRosace.IdentRecursoMalmo);
			new Thread(){
				public void run(){
					try {
						itfMalmo.moverAgente(identAgente,((Manzana) propuesta.getJustificacion()).getCoordinate());
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
