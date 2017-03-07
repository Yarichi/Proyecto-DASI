/*
 *  Copyright 2001 Telef�nica I+D. All rights reserved
 */
package icaro.gestores.gestorOrganizacion.comportamientoDist;


//import icaro.infraestructura.corba.ORBDaemonExec;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaGestor;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.FactoriaAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfGestionAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp.HebraMonitorizacion;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que contiene las acciones necesarias para el gestor de la organizaci�n
 * 
 
 * @created 3 de Diciembre de 2007
 */
public class AccionesSemanticasGestorOrganizacion extends
		AccionesSemanticasAgenteReactivo {
	// Tiempo que fijaremos para las monitorizaciones c�clicas
	/**
	 * @uml.property  name="tiempoParaNuevaMonitorizacion"
	 */
	protected long tiempoParaNuevaMonitorizacion;
        protected int maxNumIntentosCreacionCompGestionados;

	/**
	 * Hebra para que inyecte eventos de monitorizaci�n cada cierto tiempo
	 * @uml.property  name="hebra"
	 * @uml.associationEnd  
	 */
	private HebraMonitorizacion hebra;
        private InterfazGestion ItfGestionRecTrazas ;
        private ItfUsoRecursoTrazas ItfUsoRecTrazas;
        private ItfUsoConfiguracion config;
        private String identEsteNodo ;
        private ItfUsoAgenteReactivo itfUsoPropiadeEsteAgente;
	public AccionesSemanticasGestorOrganizacion() {
		try {
			// creo el repositorio de interfaces
			itfUsoRepositorio = ClaseGeneradoraRepositorioInterfaces.instance();
          
            ItfGestionRecTrazas = (InterfazGestion) this.itfUsoRepositorio.obtenerInterfaz(
                        NombresPredefinidos.ITF_GESTION + NombresPredefinidos.RECURSO_TRAZAS);
            trazas.setIdentAgenteAReportar(nombreAgente);
            trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
                        "Construyendo agente reactivo " + nombreAgente + ".",
                        InfoTraza.NivelTraza.debug));
		} catch (Exception ex) {
			 logger.fatal("GestorOrganizacion: Fue imposible crear el repositorio de interfaces.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"Fue imposible crear el repositorio de interfaces.",
					InfoTraza.NivelTraza.error));
			ex.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Establece la configuracion para el gestor de Organizaci�n
	 */
	public void configurarGestor() {
		try {
			/*
			 * En esta acci�n sem�ntica se configura todo aquello que sea
			 * necesario a partir del archivo xml
			 */

			 config = (ItfUsoConfiguracion) ClaseGeneradoraRepositorioInterfaces
					.instance().obtenerInterfaz(
							NombresPredefinidos.ITF_USO
									+ NombresPredefinidos.CONFIGURACION);
//                           String itMonit = config.getValorPropiedadGlobal("intervaloMonitorizacion");
                         itfUsoPropiadeEsteAgente=(ItfUsoAgenteReactivo)itfUsoRepositorio.obtenerInterfazUso(NombresPredefinidos.ITF_USO
                                                                         +nombreAgente);
			tiempoParaNuevaMonitorizacion = Integer.parseInt(config.getValorPropiedadGlobal("intervaloMonitorizacion"));
//			tiempoParaNuevaMonitorizacion = Integer.parseInt(config.getValorPropiedadGlobal("gestores.comun.intervaloMonitorizacion"));
                        maxNumIntentosCreacionCompGestionados = Integer.parseInt(config.getValorPropiedadGlobal("maxIntentosCompGestionados"));
                        DescInstanciaGestor descGestor = config.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
			identEsteNodo = descGestor.getNodo().getNombreUso();

                        // Si la organizacion tiene varios nodos de despliegue debo crear el Registro RMI




                        this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
					"gestor_configurado",
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
		} catch (Exception e) {
			e.printStackTrace();
			 logger.error("GestorRecursos: Hubo problemas al configurar el gestor de Organizacion.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"Hubo problemas al configurar el gestor de Organizacion.",
					InfoTraza.NivelTraza.error));
		}

	}

	/**
	 * Crea el gestor de agentes y el gestor de recursos
	 */
        public void crearRecursoRMI (){

        }

	public void crearGestores() {
		try {
//			ItfUsoConfiguracion config = (ItfUsoConfiguracion) ClaseGeneradoraRepositorioInterfaces
//					.instance().obtenerInterfaz(
//							NombresPredefinidos.ITF_USO
//									+ NombresPredefinidos.CONFIGURACION);

			// creo los gestores
			logger.debug("GestorOrganizacion: Creando gestor de agentes...");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"Creando gestor de agentes ...",
					InfoTraza.NivelTraza.debug));
			
			// Gestor de Agentes: local o remoto?
			DescInstanciaGestor descGestor = config.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
//			String esteNodo = descGestor.getNodo().getNombreUso();

			DescInstanciaGestor gestorAgentes = config.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_AGENTES);
			String nodoDestino = gestorAgentes.getNodo().getNombreUso();
			int intentos = 0;
			boolean ok = false;
			

			if (nodoDestino.equals(identEsteNodo)) {
                            crearGestoreEnEsteNodo(NombresPredefinidos.NOMBRE_GESTOR_AGENTES);
//
//				FactoriaAgenteReactivo.instancia().crearAgenteReactivo(
//						gestorAgentes);
			} else {
                            crearGestorEnNodoRemoto(NombresPredefinidos.NOMBRE_GESTOR_AGENTES);
                            }
//				while (!ok & (intentos<=maxNumIntentosCreacionCompGestionados)) {
//					++intentos;
//					try {
//						((FactoriaAgenteReactivo) ClaseGeneradoraRepositorioInterfaces
//								.instance()
//								.obtenerInterfaz(
//										NombresPredefinidos.FACTORIA_AGENTE_REACTIVO
//												+ nodoDestino))
//								.crearAgenteReactivo(gestorAgentes);
//						ok = true;
//					} catch (Exception e) {
//						trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
//								"Error al crear el agente "
//										+ NombresPredefinidos.NOMBRE_GESTOR_AGENTES
//										+ " en un nodo remoto. Se volver� a intentar en "
//										+ intentos
//										+ " segundos...\n nodo origen: "
//										+ esteNodo + "\t nodo destino: "
//										+ nodoDestino,
//								InfoTraza.NivelTraza.error));
//						logger
//								.error("Error al crear el agente "
//										+ NombresPredefinidos.NOMBRE_GESTOR_AGENTES
//										+ " en un nodo remoto. Se volver� a intentar en "
//										+ intentos
//										+ " segundos...\n nodo origen: "
//										+ esteNodo + "\t nodo destino: "
//										+ nodoDestino);
//
//						Thread.sleep(1000 * intentos);
//
//						ok = false;
//					}
//				}
//                                if (!ok){
//
//                               trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
//								"No se pudo crear el agente "
//										+ NombresPredefinidos.NOMBRE_GESTOR_AGENTES
//										+ " en un nodo remoto. Se supero el numero de intentos definido "
//										+ intentos
//										+ esteNodo + "\t nodo destino: "
//										+ nodoDestino,
//								InfoTraza.NivelTraza.error));
//						logger
//								.error("Error al crear el agente "
//										+ NombresPredefinidos.NOMBRE_GESTOR_AGENTES
//										+ " en un nodo remoto. Se supero el numero de intentos definido "
//										+ intentos
//										+ esteNodo + "\t nodo destino: "
//										+ nodoDestino);
//			}

			logger.debug("GestorOrganizacion: Gestor de agentes creado.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"Gestor de agentes creado.",
					InfoTraza.NivelTraza.debug));

			Set<Object> conjuntoEventos = new HashSet<Object>();
			conjuntoEventos.add(EventoRecAgte.class);

			// indico a qui�n debe reportar

		
			((ItfGestionAgenteReactivo) itfUsoRepositorio
					.obtenerInterfaz(
							NombresPredefinidos.ITF_GESTION
									+ NombresPredefinidos.NOMBRE_GESTOR_AGENTES))
					.setGestorAReportar(
							NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);

			logger.debug("GestorOrganizacion: Creando gestor de recursos ...");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"Creando gestor de recursos ...",
					InfoTraza.NivelTraza.debug));
			
			
			
			// Gestor de recursos: local o remoto?
			
			DescInstanciaAgente gestorRecursos = config.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_RECURSOS); 
			 nodoDestino = gestorRecursos.getNodo().getNombreUso();
			if (nodoDestino.equals(identEsteNodo)) {
//				FactoriaAgenteReactivo.instancia().crearAgenteReactivo(
//						gestorRecursos);
                            crearGestoreEnEsteNodo(NombresPredefinidos.NOMBRE_GESTOR_RECURSOS);
			} else {
                             crearGestorEnNodoRemoto(NombresPredefinidos.NOMBRE_GESTOR_RECURSOS);
////				intentos = 0;
////				ok = false;
////				while (!ok) {
////					++intentos;
////					try {
////						((FactoriaAgenteReactivo) ClaseGeneradoraRepositorioInterfaces
////								.instance()
////								.obtenerInterfaz(
////										NombresPredefinidos.FACTORIA_AGENTE_REACTIVO
////												+ nodoDestino))
////								.crearAgenteReactivo(gestorRecursos);
////						ok = true;
////					} catch (Exception e) {
////						trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
////								"Error al crear agente "
////										+ NombresPredefinidos.NOMBRE_GESTOR_RECURSOS
////										+ " en un nodo remoto. Se volver� a intentar en "
////										+ intentos
////										+ " segundos...\n nodo origen: "
////										+ esteNodo + "\t nodo destino: "
////										+ nodoDestino,
////								InfoTraza.NivelTraza.error));
////						logger
////								.error("Error al crear agente "
////										+ NombresPredefinidos.NOMBRE_GESTOR_RECURSOS
////										+ " en un nodo remoto. Se volver� a intentar en "
////										+ intentos
////										+ " segundos...\n nodo origen: "
////										+ esteNodo + "\t nodo destino: "
////										+ nodoDestino);
////
////						Thread.sleep(1000 * intentos);
////						ok = false;
////					}
//				}
			}
			logger.debug("GestorOrganizacion: Gestor de recursos creado.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"Gestor de recursos creado.",
					InfoTraza.NivelTraza.debug));

			// indico a qui�n debe reportar
			((ItfGestionAgenteReactivo) ClaseGeneradoraRepositorioInterfaces
					.instance()
					.obtenerInterfaz(
							NombresPredefinidos.ITF_GESTION
									+ NombresPredefinidos.NOMBRE_GESTOR_RECURSOS))
					.setGestorAReportar(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION
									);

			logger.debug("GestorOrganizacion: Gestores registrados correctamente.");
			
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"Gestores registrados correctamente.",
					InfoTraza.NivelTraza.debug));

			this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte("gestores_creados",
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));

		} catch (Exception e) {
			logger.error("GestorOrganizacion: Fue imposible crear los gestores de agentes y recursos en el gestor de la organizaci�n",e);
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"Fue imposible crear los gestores de agentes y recursos en el gestor de la organizaci�n",
					InfoTraza.NivelTraza.error));
			e.printStackTrace();
			try {
				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
						"error_en_creacion_gestores",
						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

   public void crearGestoreEnEsteNodo(String identAgente) throws RemoteException {


       try {
           DescInstanciaGestor gestorAgentes = config.getDescInstanciaGestor(identAgente);
           String nodoDestino = gestorAgentes.getNodo().getNombreUso();
//            int intentos = 0;
                    if (nodoDestino.equals(identEsteNodo)) {
				FactoriaAgenteReactivo.instancia().crearAgenteReactivo(
						gestorAgentes);
//                    return true;
                }else
                    {
                        trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
								"Error al crear agente "
										+ NombresPredefinidos.NOMBRE_GESTOR_RECURSOS
										+ " en el nodo local "
										+ identEsteNodo + "\t El nodo del gestor a crear es diferente El Nodo definido es : "
										+ nodoDestino,
								InfoTraza.NivelTraza.error));
                        logger.error("Error al crear agente "
							+ NombresPredefinidos.NOMBRE_GESTOR_RECURSOS
							+ " en un nodo remoto. Se volver� a intentar en "
							+ " en el nodo local "
							+ identEsteNodo + "\t El nodo del gestor a crear es diferente El Nodo definido es : "
							+ nodoDestino);
//                return false;
                }
            } catch (ExcepcionEnComponente ex) {
                trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
                                                        "Error al crear agente "
							+ identAgente+ " en el nodo local "
							+ identEsteNodo + "\t  ",InfoTraza.NivelTraza.error));
                        logger.error("Error al crear agente "
							+ identAgente
							+ " en el nodo local "
							+ identEsteNodo + "\t ");
//                return false;
        }
   }
    public void crearGestorEnNodoRemoto(String identAgente) {
        try {
            int intentos = 0;
            boolean ok = false;
            DescInstanciaGestor gestorAgentes = config.getDescInstanciaGestor(identAgente);
            String nodoDestino = gestorAgentes.getNodo().getNombreUso();
            while (!ok & (intentos <= maxNumIntentosCreacionCompGestionados)) {
                ++intentos;
                try {
                    // Se obtien la interfaz remota del gestor de Nodo
                    // Se ordena al Gestor de Nodo que cree al Gestor. Se debe esperar la confirmacion
                    ((FactoriaAgenteReactivo) ClaseGeneradoraRepositorioInterfaces
								.instance()
								.obtenerInterfaz(
										NombresPredefinidos.FACTORIA_AGENTE_REACTIVO
												+ nodoDestino)).crearAgenteReactivo(gestorAgentes);
                    ok = true;
                } catch (Exception e) {
                    trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion", "Error al crear el agente " + NombresPredefinidos.NOMBRE_GESTOR_AGENTES + " en un nodo remoto. Se volver� a intentar en " + intentos + " segundos...\n nodo origen: " + identEsteNodo + "\t nodo destino: " + nodoDestino, InfoTraza.NivelTraza.error));
                    logger.error("Error al crear el agente " + NombresPredefinidos.NOMBRE_GESTOR_AGENTES + " en un nodo remoto. Se volver� a intentar en " + intentos + " segundos...\n nodo origen: " + identEsteNodo + "\t nodo destino: " + nodoDestino);
                    Thread.sleep(1000 * intentos);
                    ok = false;
                }
            }
            if (!ok) {
                trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion", "No se pudo crear el agente " + NombresPredefinidos.NOMBRE_GESTOR_AGENTES + " en un nodo remoto. Se supero el numero de intentos definido " + intentos + identEsteNodo + "\t nodo destino: " + nodoDestino, InfoTraza.NivelTraza.error));
                logger.error("Error al crear el agente " + NombresPredefinidos.NOMBRE_GESTOR_AGENTES + " en un nodo remoto. Se supero el numero de intentos definido " + intentos + identEsteNodo + "\t nodo destino: " + nodoDestino);
            }
            logger.debug("GestorOrganizacion: Gestor de agentes creado.");
            trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion", "Gestor de agentes creado.", InfoTraza.NivelTraza.debug));
        } catch (Exception e) {
			logger.error("GestorOrganizacion: Fue imposible crear los gestores de agentes y recursos en el gestor de la organizaci�n",e);
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"Fue imposible crear los gestores de agentes y recursos en el gestor de la organizaci�n",
					InfoTraza.NivelTraza.error));
			e.printStackTrace();
			try {
				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
						"error_en_creacion_gestores",
						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
        }

   }

	public void arrancarGestorAgentes() {

		logger.debug("GestorOrganizacion: Arrancando Gestor de Agentes.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Arrancando Gestor de Agentes.",
				InfoTraza.NivelTraza.debug));
		try {
			((ItfGestionAgenteReactivo) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
							+ NombresPredefinidos.NOMBRE_GESTOR_AGENTES))
					.arranca();
			// this.itfUsoAgente.aceptaEvento(new
			// EventoRecAgte("gestor_agentes_arrancado_ok"));

		} catch (Exception e) {
			logger.error("GestorOrganizacion: Fue imposible arrancar el Gestor de Agentes.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"Fue imposible arrancar el Gestor de Agentes.",
					InfoTraza.NivelTraza.error));
			e.printStackTrace();
			try {
				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
						"error_en_arranque_gestor_agentes",
						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public void arrancarGestorRecursos() {

		logger.debug("GestorOrganizacion: Arrancando Gestor de Recursos.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Arrancando Gestor de Recursos.",
				InfoTraza.NivelTraza.debug));
		try {
			((ItfGestionAgenteReactivo) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
							+ NombresPredefinidos.NOMBRE_GESTOR_RECURSOS))
					.arranca();
			// this.itfUsoAgente.aceptaEvento(new
			// EventoRecAgte("gestor_recursos_arrancado_ok"));
		} catch (Exception e) {
			logger.error("GestorOrganizacion: Fue imposible arrancar el Gestor de Recursos.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"Fue imposible arrancar el Gestor de Recursos.",
					InfoTraza.NivelTraza.error));
			e.printStackTrace();
			try {
				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
						"error_en_arranque_gestor_recursos",
						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public void gestoresArrancadosConExito() {
		// creo hebra de monitorizaci�n
		hebra = new HebraMonitorizacion(tiempoParaNuevaMonitorizacion,
				this.itfUsoPropiadeEsteAgente, "monitorizar");
		this.hebra.start();
		logger.debug("GestorOrganizacion: Gestor de la organizacion esperando peticiones.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Gestor de la organizacion esperando peticiones.",
				InfoTraza.NivelTraza.debug));
	}

	/**
	 * Decide qu� hacer en caso de fallos en el gestor de agentes y/o en el
	 * gestor de recursos
	 */
	public void decidirTratamientoErrorIrrecuperable() {
            
		// el tratamiento ser� siempre cerrar todo el chiringuito
		logger.debug("GestorOrganizacion: Se decide cerrar el sistema ante un error cr�tico irrecuperable.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Se decide cerrar el sistema ante un error critico irrecuperable.",
				InfoTraza.NivelTraza.debug));
                trazas.mostrarMensajeError("Error irrecuperable. Esperando por su solicitud de terminación");
		/*
                try {
			this.itfUsoAgente.aceptaEvento(new EventoRecAgte(
					"tratamiento_terminar_gestores_y_gestor_organizacion",
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
		} catch (Exception e) {
			e.printStackTrace();
		}*/
                
	}

	/**
	 * intenta arrancar el gestor de agentes y/o el gestor de recursos si alguno
	 * ha dado problemas en el arranque.
	 */
	public void recuperarErrorArranqueGestores() {
		// por defecto no se implementan politicas de recuperacion
		logger.debug("GestorOrganizacion: Fue imposible recuperar el error en el arranque de los gestores.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Fue imposible recuperar el error en el arranque de los gestores.",
				InfoTraza.NivelTraza.debug));
		try {
			this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
					"imposible_recuperar_arranque",
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Elabora un informe del estado en el que se encuentran el gestor de
	 * agentes y el gestor de recursos y lo env�a al sistema de trazas.
	 */
	public void generarInformeErrorIrrecuperable() {
		// Producimos traza de un error
		logger.debug("GestorOrganizaci�n: Finalizando gestor de la organizaci�n debido a un error irrecuperable.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Finalizando gestor de la organizaci�n debido a un error irrecuperable.",
				InfoTraza.NivelTraza.debug));
		try {
			this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte("informe_generado",
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Monitoriza al gestor de recursos y al gestor de agentes.
	 */
	public void monitorizarGestores() {
		// monitorizamos los dos gestores en serie
		// if(DEBUG) System.out.println("GestorOrganizaci�n: Iniciando ciclo de
		// monitorizaci�n");
		boolean errorAlMonitorizar = false;
		int monitAgentes = 0;
		int monitRecursos = 0;
		try {
			monitAgentes = ((ItfGestionAgenteReactivo) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
							+ NombresPredefinidos.NOMBRE_GESTOR_AGENTES))
					.obtenerEstado();
			monitRecursos = ((ItfGestionAgenteReactivo) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
							+ NombresPredefinidos.NOMBRE_GESTOR_RECURSOS))
					.obtenerEstado();

			// � hay problemas con el gestor de agentes ?
			errorAlMonitorizar = ((monitAgentes == InterfazGestion.ESTADO_ERRONEO_IRRECUPERABLE)
					|| (monitAgentes == InterfazGestion.ESTADO_ERRONEO_RECUPERABLE)
					|| (monitAgentes == InterfazGestion.ESTADO_TERMINADO) || (monitAgentes == InterfazGestion.ESTADO_TERMINANDO));

			if (errorAlMonitorizar) {

				logger.debug("GestorOrganizacion: Error al monitorizar gestores");
				trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
						"Error al monitorizar gestores",
						InfoTraza.NivelTraza.debug));
				if ((monitAgentes == InterfazGestion.ESTADO_ERRONEO_IRRECUPERABLE)
						|| (monitAgentes == InterfazGestion.ESTADO_ERRONEO_RECUPERABLE)) {
					logger.error("GestorOrganizaci�n: El GESTOR DE AGENTES ha fallado. Su estado es "+ monitAgentes);
					trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
						"El GESTOR DE AGENTES ha fallado. Su estado es "+ monitAgentes,
						InfoTraza.NivelTraza.error));
				}
				else{
					logger.error("GestorOrganizacion: El GESTOR DE RECURSOS ha fallado. Su estado es "+ monitRecursos);
					trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
						"El GESTOR DE RECURSOS ha fallado. Su estado es "+ monitRecursos,
						InfoTraza.NivelTraza.error));
				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
						"error_al_monitorizar",
						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
				}
			} 
			else {
				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte("gestores_ok",
						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
				// if(DEBUG) System.out.println("GestorOrganizaci�n:
				// Monitorizaci�n de los gestores ok");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
						"error_al_monitorizar",
						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Da orden de terminacion al gestor de agentes si esta activos.
	 */
	public void terminarGestorAgentes() {
		// mandamos la orden de terminar a los gestores
		logger.debug("GestorOrganizacion: Terminando gestor de agentes");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Terminando gestor de agentes",
				InfoTraza.NivelTraza.debug));
//		InterfazGestion gestorAgentes;
                InterfazUsoAgente itfGestorAgentes;
		try {
//			gestorAgentes = (ItfGestionAgenteReactivo) this.itfUsoRepositorio
//					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
//							+ NombresPredefinidos.NOMBRE_GESTOR_AGENTES);
//			gestorAgentes.termina();
                    itfGestorAgentes = (InterfazUsoAgente) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_USO
							+ NombresPredefinidos.NOMBRE_GESTOR_AGENTES);
                    itfGestorAgentes.aceptaEvento(new EventoRecAgte("ordenTerminacion", nombreAgente, itfGestorAgentes.getIdentAgente()));
			// timeout de 5 segundos
			this.generarTimeOut(2000, "timeout_gestor_agentes",
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);

		} catch (Exception ex) {
			logger.debug("GestorOrganizacion: No se pudo acceder al gestor de agentes.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"No se pudo acceder al gestor de agentes.",
					InfoTraza.NivelTraza.debug));
                        informaraMiAutomata("gestor_agentes_terminado");
			ex.printStackTrace();
		}
	}

	/**
	 * Da orden de terminaci�n al gestor de recursos si est� activos.
	 */
	public void terminarGestorRecursos() {
		// mandamos la orden de terminar a los gestores
		logger.debug("GestorOrganizaci�n: Terminando gestor de recursos");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Terminando gestor de recursos",
				InfoTraza.NivelTraza.debug));
		InterfazUsoAgente gestorRecursos;
		try {
//			gestorRecursos = (ItfGestionAgenteReactivo) this.itfUsoRepositorio
//					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
//							+ NombresPredefinidos.NOMBRE_GESTOR_RECURSOS);
//			gestorRecursos.termina();

                        gestorRecursos = (InterfazUsoAgente) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_USO
							+ NombresPredefinidos.NOMBRE_GESTOR_RECURSOS);
                        gestorRecursos.aceptaEvento(new EventoRecAgte("ordenTerminacion", nombreAgente, gestorRecursos.getIdentAgente()));

			// timeout de 5 segundos
			this.generarTimeOut(2000, "timeout_gestor_recursos",
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
		} catch (Exception ex) {
			logger.debug("GestorOrganizacion: No se pudo acceder al gestor de recursos.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"No se pudo acceder al gestor de recursos.",
					InfoTraza.NivelTraza.debug));
                        informaraMiAutomata("gestor_agentes_terminado");
			ex.printStackTrace();
		}

	}

	public void procesarPeticionTerminacion() {
		logger.debug("GestorOrganizacion: Procesando la peticion de terminacion");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Procesando la peticion de terminacion",
				InfoTraza.NivelTraza.debug));

	String estadoInternoAgente =	this.ctrlGlobalAgenteReactivo.getItfControl().getEstadoControlAgenteReactivo();
		trazas.setIdentAgenteAReportar(nombreAgente);
                trazas.pedirConfirmacionTerminacionAlUsuario();
		  this.informaraMiAutomata("termina");
		/*try {
			// this.itfUsoAgente.aceptaEvento(new
			// EventoRecAgte("termina",null,null));
			
		
			ItfGestionAgenteReactivo gestion = (ItfGestionAgenteReactivo) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
							+ NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
			gestion.termina();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}
	
	public void comenzarTerminacionConfirmada() {
		logger.debug("GestorOrganizacion: Comenzando terminacion de la organizacion...");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Comenzando la terminacion de la organizacion...",
				InfoTraza.NivelTraza.info));
		try {
           String estadoInternoAgente = this.ctrlGlobalAgenteReactivo.getItfControl().getEstadoControlAgenteReactivo();
           ItfGestionAgenteReactivo gestion = (ItfGestionAgenteReactivo) this.itfUsoRepositorio
				.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
						+ NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
		//	gestion.termina();
//            this.itfUsoPropiadeEsteAgente.aceptaEvento(new
//			 EventoRecAgte ("termina",this.nombreAgente,this.nombreAgente));
//                        this.ctrlGlobalAgenteReactivo.getItfControl().getEstadoControlAgenteReactivo();
                        this.informaraMiAutomata("termina");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}

	/**
	 * Da orden de terminaci�n al gestor de agentes y/o al gestor de recursos si
	 * est�n activos.
	 */
	/*
	 * public void terminarGestoresActivos() { // mandamos la orden de terminar
	 * a los gestores logger.debug("GestorOrganizaci�n: Terminando los
	 * gestores"); try {
	 * ((InterfazGestion)this.itfUsoRepositorio.obtenerInterfaz(NombresInterfaces.ITF_GES_GESTOR_AGENTES)).termina(); }
	 * catch (Exception ex) { logger.debug("GestorOrganizaci�n: No se pudo
	 * acceder al gestor de agentes."); ex.printStackTrace(); } try {
	 * ((InterfazGestion)this.itfUsoRepositorio.obtenerInterfaz(NombresInterfaces.ITF_GES_GESTOR_RECURSOS)).termina(); }
	 * catch (Exception ex) { logger.debug("GestorOrganizaci�n: No se pudo
	 * acceder al gestor de recursos."); ex.printStackTrace(); } try {
	 * this.itfUsoAgente.aceptaEvento(new
	 * EventoRecAgte("gestores_terminados",null,null)); } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */
	/**
	 * Intenta recuperar los errores detectados en la monitorizaci�n siguiendo
	 * la pol�tica definida para cada gestor.
	 */
	public void recuperarErrorAlMonitorizarGestores() {
		// por defecto no se implementan pol�ticas de recuperaci�n
		logger.debug("GestorOrganizaci�n: No se pudo recuperar el error de monitorizaci�n.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"No se pudo recuperar el error de monitorizaci�n.",
				InfoTraza.NivelTraza.debug));
		try {
			this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
					"imposible_recuperar_error_monitorizacion",
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * destruye los recursos que se crearon a lo largo del ciclo de vida del
	 * gestor de la organizaci�n-
	 */
	public void terminarGestorOrganizacion() {
		// termina el gestor.
		// puede esperarse a que terminen los dos gestores para mayor seguridad
		logger.debug("GestorOrganizacion: Terminando gestor de la organizaci�n y los recursos de la infraestructura.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Terminando gestor de la organizacion y los recursos de la infraestructura.",
				InfoTraza.NivelTraza.debug));
		try {
			// se acaba con los recursos de la organizacion que necesiten ser
			// terminados
			ItfGestionRecTrazas.termina();
                       
			// y a continuacion se termina el gestor de organizacion
			((ItfGestionAgenteReactivo) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
							+ NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION))
					.termina();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.debug("GestorOrganizacion: Cerrando sistema.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Cerrando sistema.",
				InfoTraza.NivelTraza.debug));
        
		if (this.hebra != null)
			this.hebra.finalizar();
                 System.exit(0);
		/*
		if (ORBDaemonExec.finalInstance() != null) {
			ORBDaemonExec.finalInstance().finalize();
		}
		*/
	}

	public void clasificaError() {
	}

	public void tratarTerminacionNoConfirmada() {
		logger.debug("Se ha recibido un evento de timeout debido a que un gestor no ha confirmado la terminaci�n. Se procede a continuar la terminaci�n del sistema");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Se ha recibido un evento de timeout debido a que un gestor no ha confirmado la terminaci�n. Se procede a continuar la terminaci�n del sistema",
				InfoTraza.NivelTraza.debug));
		try {
			this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
					"continuaTerminacion",
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}