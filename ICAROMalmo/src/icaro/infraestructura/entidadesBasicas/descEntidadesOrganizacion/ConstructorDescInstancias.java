package icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.recursosOrganizacion.configuracion.imp.*;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.ConstructorProperties;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstancia;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgenteAplicacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaGestor;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.ComponenteGestionado;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.Instancia;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.InstanciaGestor;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.Nodo;
import icaro.infraestructura.entidadesBasicas.excepciones.UsoRecursoException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class ConstructorDescInstancias {
	ConfiguracionImp config;

	public ConstructorDescInstancias(ConfiguracionImp configuracionImp) {
		this.config = configuracionImp;
	}

	public DescInstanciaAgenteAplicacion construirDescInstanciaAgenteAplicacion(
			String id) throws UsoRecursoException {
		try {
			DescInstanciaAgenteAplicacion descAgente = new DescInstanciaAgenteAplicacion();
			descAgente.setId(id);
                        descAgente.setCategoriaComponente(NombresPredefinidos.NOMBRE_ENTIDAD_AGENTE);
			// Obtener la instancia
			List<Instancia> instanciasAgentes = config.getDescOrganizacion()
					.getDescInstancias().getAgentesAplicacion().getInstancia();
			Iterator<Instancia> iter = instanciasAgentes.iterator();
			Instancia instancia = null;
			boolean encontrado = false;
			while (iter.hasNext() && !encontrado) {
				instancia = iter.next();
				if (instancia.getId().equals(id)) {
					encontrado = true;
				}
			}
			if (encontrado) {
				// Obtener el comportamiento
				descAgente.setDescComportamiento(config
						.getDescComportamientoAgente(instancia
								.getRefDescripcion()));
				descAgente.setTipoComponente(config
						.getDescComportamientoAgente(instancia.getRefDescripcion()).getTipo().value());
                                descAgente.setRolComponente(config
						.getDescComportamientoAgente(instancia.getRefDescripcion()).getRol().value());
                                // Obtener lista de propiedades
				descAgente.setPropiedades(ConstructorProperties
						.obtenerProperties(instancia.getListaPropiedades()));
				// obtener el nodo especifico
				Nodo nodo = instancia.getNodoEspecifico();
				if (nodo == null) {
					// obtener el nodo comun de las instancias de agentes
					nodo = config.getDescOrganizacion().getDescInstancias()
							.getAgentesAplicacion().getNodoComun();
					if (nodo == null) {
						// obtener el nodo comun de todas las instancias
						nodo = config.getDescOrganizacion().getDescInstancias()
								.getNodoComun();
					}
				}
				descAgente.setNodo(nodo);
				if (nodo == null) {
					throw new UsoRecursoException(
							"Error al leer el nodo de la instancia de agente con id "
									+ id);
				}
			}
			return descAgente;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsoRecursoException(
					"Error al interpretar la descripcin de la instancia de agente con id "
							+ id);
		}

	}

	public DescInstanciaGestor construirDescInstanciaGestor(String id)
			throws UsoRecursoException {
		try {
			DescInstanciaGestor descGestor = new DescInstanciaGestor();
			descGestor.setId(id);
                        descGestor.setCategoriaComponente(NombresPredefinidos.NOMBRE_ENTIDAD_AGENTE);
                        
			// Obtener la instancia
			List<InstanciaGestor> instanciasGestores = config
					.getDescOrganizacion().getDescInstancias().getGestores()
					.getInstanciaGestor();
			Iterator<InstanciaGestor> iter = instanciasGestores.iterator();
			InstanciaGestor instancia = null;
			boolean encontrado = false;
			while (iter.hasNext() && !encontrado) {
				instancia = iter.next();
				if (instancia.getId().equals(id)) {
					encontrado = true;
				}
			}
			if (encontrado) {
				// Obtener el comportamiento
				descGestor.setDescComportamiento(config
						.getDescComportamientoAgente(instancia
								.getRefDescripcion()));
				descGestor.setTipoComponente(config
						.getDescComportamientoAgente(instancia.getRefDescripcion()).getTipo().value());
                                descGestor.setRolComponente(config
						.getDescComportamientoAgente(instancia.getRefDescripcion()).getRol().value());
                                // Obtener lista de propiedades
				descGestor.setPropiedades(ConstructorProperties
						.obtenerProperties(instancia.getListaPropiedades()));
				// obtener el nodo especfico
				Nodo nodo = instancia.getNodoEspecifico();
				if (nodo == null) {
					// obtener el nodo comn de las instancias de gestores
					nodo = config.getDescOrganizacion().getDescInstancias()
							.getGestores().getNodoComun();
					if (nodo == null) {
						// obtener el nodo comn de todas las instancias
						nodo = config.getDescOrganizacion().getDescInstancias()
								.getNodoComun();
					}
				}
				descGestor.setNodo(nodo);
				if (nodo == null) {
					throw new UsoRecursoException(
							"Error al leer el nodo de la instancia de agente con id "
									+ id);
				}
				// obtener lista de componentes gestionados
				List<ComponenteGestionado> componentes = instancia
						.getComponentesGestionados().getComponenteGestionado();
				Iterator<ComponenteGestionado> iterator = componentes
						.iterator();
				ComponenteGestionado componente = null;
				List<DescInstancia> componentesGestionados = new ArrayList<DescInstancia>();
				while (iterator.hasNext()) {
					componente = iterator.next();
					DescInstancia inst = null;
					switch (componente.getTipoComponente()) {
					case GESTOR:
						inst = this.construirDescInstanciaGestor(componente
								.getRefId());
						break;
					case AGENTE_APLICACION:
						inst = this
								.construirDescInstanciaAgenteAplicacion(componente
										.getRefId());
						break;
					case RECURSO_APLICACION:
						inst = this
								.construirDescInstanciaRecursoAplicacion(componente
										.getRefId());

					}
					componentesGestionados.add(inst);
				}
				descGestor.setComponentesGestionados(componentesGestionados);

			} else
				throw new UsoRecursoException(
						"Error al interpretar la descripcin de la instancia de gestor con id "
								+ id);
			return descGestor;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsoRecursoException(
					"Error al interpretar la descripcin de la instancia de gestor con id "
							+ id);
		}
	}

	public DescInstanciaRecursoAplicacion construirDescInstanciaRecursoAplicacion(
			String id) throws UsoRecursoException {
		try {
			DescInstanciaRecursoAplicacion descInstanciaRecursoAplicacion = new DescInstanciaRecursoAplicacion();
			descInstanciaRecursoAplicacion.setId(id);
                        descInstanciaRecursoAplicacion.setCategoriaComponente(NombresPredefinidos.NOMBRE_ENTIDAD_RECURSO);
			// Obtener la instancia
			List<Instancia> instanciasRecursos = config.getDescOrganizacion()
					.getDescInstancias().getRecursosAplicacion().getInstancia();
			Iterator<Instancia> iter = instanciasRecursos.iterator();
			Instancia instancia = null;
			boolean encontrado = false;
			while (iter.hasNext() && !encontrado) {
				instancia = iter.next();
				if (instancia.getId().equals(id)) {
					encontrado = true;
				}
			}
			if (encontrado) {
				// Obtener descripcion
				descInstanciaRecursoAplicacion.setDescRecurso(config
						.getDescRecursoAplicacion(instancia
								.getRefDescripcion()));
				// Obtener lista de propiedades
				descInstanciaRecursoAplicacion.setPropiedades(ConstructorProperties
						.obtenerProperties(instancia.getListaPropiedades()));
				// obtener el nodo especfico
				Nodo nodo = instancia.getNodoEspecifico();
				if (nodo == null) {
					// obtener el nodo comun de las instancias de recursos de aplicacin
					nodo = config.getDescOrganizacion().getDescInstancias()
							.getRecursosAplicacion().getNodoComun();
					if (nodo == null) {
						// obtener el nodo comun de todas las instancias
						nodo = config.getDescOrganizacion().getDescInstancias()
								.getNodoComun();
					}
				}
				descInstanciaRecursoAplicacion.setNodo(nodo);
				if (nodo == null) {
					throw new UsoRecursoException(
							"Error al leer el nodo de la instancia de agente con id "
									+ id);
				}
			}
			return descInstanciaRecursoAplicacion;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsoRecursoException(
					"Error al interpretar la descripcin de la instancia de agente con id "
							+ id);
		}
	}

}
