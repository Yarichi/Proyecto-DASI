package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.tareas;

import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InfoDecidirRecolector;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InfoEquipoMoic;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Manzana;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos.RecolectarManzana;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.objetivos.RecolectarTodasLasManzanas;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.objetivos.DecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class GenerarObjetivoRecolectarManzana extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		RecolectarTodasLasManzanas obj = (RecolectarTodasLasManzanas) params[0];
		Focus foco = (Focus) params[1];
		Manzana m = obj.getNextManzana();
		RecolectarManzana obj1 = new RecolectarManzana(m);
		InfoDecidirRecolector info = new InfoDecidirRecolector(m.getId());
		DecidirQuienVa obj2 = new DecidirQuienVa(m.getId());
		foco.setFoco(obj2);
		obj1.setPending();
		obj2.setSolving();
		this.itfProcObjetivos.actualizarHechoWithoutFireRules(obj);
		this.itfProcObjetivos.actualizarHechoWithoutFireRules(foco);
		this.itfProcObjetivos.insertarHechoWithoutFireRules(obj2);
		this.itfProcObjetivos.insertarHechoWithoutFireRules(info);
		this.itfProcObjetivos.insertarHecho(obj1);
	}

}
