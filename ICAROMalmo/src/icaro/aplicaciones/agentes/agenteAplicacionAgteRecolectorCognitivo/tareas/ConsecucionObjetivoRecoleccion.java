package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.informacion.PropuestaAgente;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Manzana;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos.RecolectarManzana;
import icaro.aplicaciones.recursos.recursoMalmo.ItfUsoRecursoMalmo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class ConsecucionObjetivoRecoleccion extends TareaSincrona{
	ItfUsoRecursoMalmo itfMalmo;
	PropuestaAgente propuesta;
	@Override
	public void ejecutar(Object... params) {
		// TODO Auto-generated method stub
		 propuesta = (PropuestaAgente) params[0];
		 RecolectarManzana obj = (RecolectarManzana) params[1];
		try {
			itfMalmo = (ItfUsoRecursoMalmo) this.repoInterfaces.obtenerInterfazGestion(VocabularioRosace.IdentRecursoMalmo);
			Coordinate coord = ((Manzana)propuesta.getJustificacion()).getCoordinate();
			System.out.println("La manzana esta en: " + coord.getX()+" " +coord.getY() + " " + coord.getZ());
			itfMalmo.moverAgente(identAgente,(Manzana) propuesta.getJustificacion());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(obj == null){
			RecolectarManzana obj2 = new RecolectarManzana((Manzana)propuesta.getJustificacion());
			obj2.setSolving();
			this.itfProcObjetivos.insertarHechoWithoutFireRules(obj2);
		}
		else{
			obj.setSolving();
			this.itfProcObjetivos.actualizarHechoWithoutFireRules(obj);
		}
		this.itfProcObjetivos.eliminarHecho(propuesta);
		
	}

}
