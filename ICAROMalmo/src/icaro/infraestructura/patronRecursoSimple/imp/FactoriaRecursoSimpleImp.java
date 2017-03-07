package icaro.infraestructura.patronRecursoSimple.imp;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFsinAcciones.ItfUsoAutomataEFsinAcciones;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFsinAcciones.imp.ClaseGeneradoraAutomataEFsinAcciones;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.patronRecursoSimple.FactoriaRecursoSimple;
import icaro.infraestructura.patronRecursoSimple.ItfGestionRecursoSimple;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

import org.apache.log4j.Logger;



public class FactoriaRecursoSimpleImp extends FactoriaRecursoSimple {
	
	private static final String PAQUETE_RECURSOS_APLICACION = "icaro.aplicaciones.recursos.";
	/**
	 * @uml.property  name="logger"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private Logger logger = Logger
			.getLogger(this.getClass().getCanonicalName());
	/**
	 * @uml.property  name="trazas"
	 * @uml.associationEnd  readOnly="true"
	 */
	private ItfUsoRecursoTrazas trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
    private ItfUsoRepositorioInterfaces repoIntfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
    private ItfUsoAutomataEFsinAcciones itfAutomata;

	@Override
	public void crearRecursoSimple(DescInstanciaRecursoAplicacion recurso) {
		String idRecurso = recurso.getId();
		try {
/*
			try{
		    	trazas = (ItfUsoRecursoTrazas)repoIntfaces.obtenerInterfaz(
		    			NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
		    }catch(Exception e){
		    	System.out.println("No se pudo usar el recurso de trazas");
		    }
 */
			// obtengo la clase generadora del recurso
		    trazas.aceptaNuevaTraza(new InfoTraza(idRecurso,
					"Factoria de recurso simple: Creando el recurso "
					+ idRecurso,
					InfoTraza.NivelTraza.debug));
			//logger.debug("Factoria de recurso simple: Creando el recurso "+ recurso);
			ItfUsoConfiguracion config = (ItfUsoConfiguracion) repoIntfaces.obtenerInterfaz(
							NombresPredefinidos.ITF_USO
									+ NombresPredefinidos.CONFIGURACION);
			String nombreRecurso = recurso.getDescRecurso().getNombre();
			String primero = nombreRecurso.substring(0,1).toLowerCase(); //obtengo el primer carcter en minsculas
			String nombrePaqueteRecurso = nombreRecurso.replaceFirst(nombreRecurso.substring(0,1),primero); 
		
			String rutaClaseGeneradora = PAQUETE_RECURSOS_APLICACION+nombrePaqueteRecurso+".imp.ClaseGeneradora"+nombreRecurso;

			// normalizo la cadena
			rutaClaseGeneradora = rutaClaseGeneradora.replace(" ", "");
			rutaClaseGeneradora = rutaClaseGeneradora.replace("\n", "");
			rutaClaseGeneradora = rutaClaseGeneradora.replace("\t", "");

			Class claseGen = null;
			try {
				claseGen = Class.forName(rutaClaseGeneradora);
			} catch (Exception ex) {
				trazas.aceptaNuevaTraza(new InfoTraza(idRecurso,
						"Error al leer la clase generadora del recurso "+nombreRecurso+". Compruebe que existe la clase " + rutaClaseGeneradora,
						InfoTraza.NivelTraza.error));
				//logger.error("Error al leer la clase generadora "+ rutaClaseGeneradora, ex);
			}

			// consigo el recurso
			ItfUsoAutomataEFsinAcciones itfAutomata = (ItfUsoAutomataEFsinAcciones) ClaseGeneradoraAutomataEFsinAcciones.instance(NombresPredefinidos.FICHERO_AUTOMATA_CICLO_VIDA_COMPONENTE);
                        ImplRecursoSimple objRecurso = (ImplRecursoSimple) claseGen.getConstructor(String.class).newInstance(idRecurso);
				objRecurso.setItfAutomataCicloDeVida(itfAutomata);
			
			// Guardamos el id de la instancia
		//	objRecurso.setId(idRecurso);
			
			// End Logging
			//logger.debug("Factoria de recurso simple: recurso " + recurso+ " creado.");
			trazas.aceptaNuevaTraza(new InfoTraza(idRecurso,
					"Factoria de recurso simple: recurso " + idRecurso+ " creado.",
					InfoTraza.NivelTraza.debug));
			objRecurso.setItfUsoRepositorioInterfaces(ClaseGeneradoraRepositorioInterfaces
					.instance());

			// registramos ambos objetos en el repositorio
			/*logger.debug("Factoria de recurso simple: Registrando el recurso "
					+ idRecurso + " en el repositorio de interfaces.");*/
			trazas.aceptaNuevaTraza(new InfoTraza(idRecurso,
					"Factoria de recurso simple: Registrando el recurso "
					+ idRecurso + " en el repositorio de interfaces.",
					InfoTraza.NivelTraza.debug));
			repoIntfaces.registrarInterfaz(
					NombresPredefinidos.ITF_GESTION + idRecurso, (ItfGestionRecursoSimple)objRecurso);
			repoIntfaces.registrarInterfaz(
					NombresPredefinidos.ITF_USO + idRecurso, (ItfUsoRecursoSimple)objRecurso);
                        objRecurso.setItfUsoRepositorioInterfaces(repoIntfaces);
                        objRecurso.setItfUsoRecursoTrazas(trazas);
		} catch (Exception e) {
			trazas.aceptaNuevaTraza(new InfoTraza(idRecurso,
					"Factoria de recurso simple: Error al crear el recurso "+ idRecurso+e,
					InfoTraza.NivelTraza.error));
			logger.error("Factoria de recurso simple: Error al crear el recurso "+ recurso,e);
		}
	}
}
