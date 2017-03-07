package icaro.infraestructura.patronRecursoSimple;

import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.patronRecursoSimple.imp.FactoriaRecursoSimpleImp2;

public abstract class FactoriaRecursoSimple {

	private static FactoriaRecursoSimple instance;

	public static FactoriaRecursoSimple instance() {
		if (instance == null)
			instance = new FactoriaRecursoSimpleImp2();
		return instance;
	}
	
	public abstract void crearRecursoSimple(DescInstanciaRecursoAplicacion recurso);
	
}
