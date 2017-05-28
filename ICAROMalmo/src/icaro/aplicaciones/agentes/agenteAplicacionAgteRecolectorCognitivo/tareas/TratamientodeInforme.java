package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.ConocimientoInformes;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformeAbs;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformePiedra;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformeRio;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos.ConstruirPuente;
import icaro.aplicaciones.recursos.recursoMalmo.ItfUsoRecursoMalmo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class TratamientodeInforme extends TareaSincrona{
	ItfUsoRecursoMalmo itfMalmo;
	@Override
	public void ejecutar(Object... params) {
		// TODO Auto-generated method stub
		InformeAbs inf = (InformeAbs) params[0];
		ConocimientoInformes cono = (ConocimientoInformes) params[1];
		String msg = cono.getMensaje(inf);
		try {
			itfMalmo = (ItfUsoRecursoMalmo) this.repoInterfaces.obtenerInterfazGestion(VocabularioRosace.IdentRecursoMalmo);
			new Thread(){
				public void run(){
					try {
						itfMalmo.trataMensajeInforme(msg);
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
		this.itfProcObjetivos.eliminarHecho(inf);
		
	}

}
