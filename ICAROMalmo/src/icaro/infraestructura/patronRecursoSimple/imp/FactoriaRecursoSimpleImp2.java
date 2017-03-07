/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.patronRecursoSimple.imp;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFsinAcciones.ItfUsoAutomataEFsinAcciones;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFsinAcciones.imp.ClaseGeneradoraAutomataEFsinAcciones;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescRecursoAplicacion;
import icaro.infraestructura.patronRecursoSimple.FactoriaRecursoSimple;
import icaro.infraestructura.patronRecursoSimple.ItfGestionRecursoSimple;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
import java.lang.reflect.InvocationTargetException;
import org.apache.log4j.Logger;

public class FactoriaRecursoSimpleImp2 extends FactoriaRecursoSimple {

//	private static final String PAQUETE_RECURSOS_APLICACION = "icaro.aplicaciones.recursos.";
        String msgError;
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
        private NombresPredefinidos.TipoEntidad tipoEntidad = NombresPredefinidos.TipoEntidad.Recurso ;


	@Override
	public void crearRecursoSimple(DescInstanciaRecursoAplicacion recurso) {
		String idRecurso = recurso.getId();
		try {
			// obtengo la clase generadora del recurso
		    trazas.aceptaNuevaTraza(new InfoTraza(idRecurso,tipoEntidad,
					"Factoria de recurso simple: Creando el recurso "
					+ idRecurso,
					InfoTraza.NivelTraza.debug));
			
			 ImplRecursoSimple objRecurso = obtenerInstClaseGeneradora( recurso);

                         if (objRecurso != null) {
                            itfAutomata = (ItfUsoAutomataEFsinAcciones) ClaseGeneradoraAutomataEFsinAcciones.instance(NombresPredefinidos.FICHERO_AUTOMATA_CICLO_VIDA_COMPONENTE);
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
                    } else {
                             trazas.aceptaNuevaTraza(new InfoTraza(idRecurso,
                    idRecurso + ":No se puede crear el recurso. La clase generadora no esta bien definida ",
                    NivelTraza.error));
                    System.err.println(" No se puede crear  el recurso. La clase generadora no esta bien definida.");
                    }

		} catch (Exception e) {
			trazas.aceptaNuevaTraza(new InfoTraza(idRecurso,
					"Factoria de recurso simple: Error al crear el recurso "+ idRecurso+e,
					InfoTraza.NivelTraza.error));
			logger.error("Factoria de recurso simple: Error al crear el recurso "+ recurso,e);
		}
	}

    private ImplRecursoSimple obtenerInstClaseGeneradora(DescInstanciaRecursoAplicacion instRecurso) {

		DescRecursoAplicacion descComportamiento = instRecurso.getDescRecurso();

 //       TipoAgente tipo = comportamiento.getTipo();
//		if (comportamiento.getTipo().equals(TipoAgente.REACTIVO)) {
//			DescComportamientoAgenteReactivo comportamientoReactivo = (DescComportamientoAgenteReactivo) comportamiento;
			String rutaClase = descComportamiento.getLocalizacionClaseGeneradora();
//		} else
//            logger.debug(nombreInstanciaAgente + ":La descripcion del comportamiento no es la de un agente reactivo");

		String idRecurso = instRecurso.getId();

		try {	
                    Integer posicion = rutaClase.indexOf(".class");
                    if ( posicion < 0) posicion = rutaClase.indexOf(".java");
                    if ( posicion >= 0) rutaClase = rutaClase.substring(0,posicion);
                    Class claseGenradoraRecurso = Class.forName(rutaClase);
                    ImplRecursoSimple objRecurso = (ImplRecursoSimple) claseGenradoraRecurso.getConstructor(String.class).newInstance(idRecurso);	
			return objRecurso;
		} catch (InstantiationException ex) {
                    msgError = "La clase: " + rutaClase
					  + " que debe implementar la generacion del recurso, no puede instanciarse. \n" ;
                    trazas.aceptaNuevaTraza(new InfoTraza(idRecurso,msgError,InfoTraza.NivelTraza.error));
                    System.err.println(msgError);
		} catch (IllegalAccessException ex) {
                    msgError = "La clase: " + rutaClase
					  + " que debe implementar la generacion del recurso, no tiene un constructor sin parmetros. \n" ;
                    trazas.aceptaNuevaTraza(new InfoTraza(idRecurso,msgError,InfoTraza.NivelTraza.error));
                    System.err.println(msgError);
			
		} catch (ClassNotFoundException ex) {
                    msgError = "La clase: " + rutaClase
					  + " que debe implementar la generacion del recurso, no se encuentra. \n" ;
                    trazas.aceptaNuevaTraza(new InfoTraza(idRecurso,msgError,InfoTraza.NivelTraza.error));
                    System.err.println(msgError);
        } catch (InvocationTargetException x) {
                    Throwable cause = x.getCause();
              //      err.format("invocation of %s failed: %s%n", mname, cause.getMessage());
                   msgError = "La invocation de la clase " + rutaClase + " que debe implementar la generacion del recurso "
                           + " produce  un error \n Cuya causa es : " + cause.getMessage() +"\n";
                   trazas.aceptaNuevaTraza(new InfoTraza(idRecurso,msgError,InfoTraza.NivelTraza.error));
                   System.err.println(msgError);
		} catch (Exception e) {
                    msgError = "Factoria de recurso simple: Error al crear el recurso "+ idRecurso+e + "\n" ;
                    trazas.aceptaNuevaTraza(new InfoTraza(idRecurso,msgError,InfoTraza.NivelTraza.error));
                    System.err.println(msgError);
		}
		// si falla algo, devuelvo un null
		return null;
	}
  private String normalizarRuta(String ruta){
	/*Esta funcin cambia la primera letra del nombre y la pone en minsculas*/
		String primero = ruta.substring(0,1).toLowerCase(); //obtengo el primer carcter en minsculas
		String rutaNormalizada = primero + ruta.substring(1, ruta.length());

		return rutaNormalizada;
	}

}

