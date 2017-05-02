package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.tareas;

import java.util.ArrayList;

import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Agente;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Manzana;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Obstaculo;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos.RecolectarTodasLasManzanas;
import icaro.aplicaciones.recursos.recursoMalmo.ItfUsoRecursoMalmo;
import icaro.gestores.informacionComun.VocabularioGestores;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class ConseguirInformacionDelEntorno extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		try {
			ItfUsoRecursoMalmo itfMalmo = (ItfUsoRecursoMalmo)this.repoInterfaces.obtenerInterfaz(VocabularioRosace.IdentRecursoMalmo);
			ArrayList<Manzana> manzanas = itfMalmo.getInformacionManzanas();
			Objetivo o = new RecolectarTodasLasManzanas(manzanas);
			o.setPending();
			this.itfProcObjetivos.insertarHecho(o);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
