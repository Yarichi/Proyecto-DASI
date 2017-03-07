/*
 *  Copyright 2001 Telef�nica I+D. All rights reserved
 */
package icaro.gestores.gestorOrganizacion.comportamiento;


//import icaro.infraestructura.corba.ORBDaemonExec;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaGestor;
import icaro.infraestructura.entidadesBasicas.factorias.FactoriaComponenteIcaro;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.FactoriaAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfGestionAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp.HebraMonitorizacion;
import icaro.infraestructura.patronRecursoSimple.ItfGestionRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Clase que contiene las acciones necesarias para el gestor de la organizaci�n
 * 
 
 * @created 3 de Diciembre de 2007
 */
public class AccionesSemanticasGestorOrganizacion extends AccionesSemanticasAgenteReactivo {
	// Tiempo que fijaremos para las monitorizaciones ciclicas
	/**
	 * @uml.property  name="tiempoParaNuevaMonitorizacion"
	 */
	protected long tiempoParaNuevaMonitorizacion;

	/**
	 * Hebra para que inyecte eventos de monitorizacion cada cierto tiempo
	 * @uml.property  name="hebra"
	 * @uml.associationEnd  
	 */
	private HebraMonitorizacion hebra;
        private InterfazGestion itfGestionRecTrazas ;
        private ItfUsoConfiguracion config ;
        private ItfUsoAgenteReactivo itfUsoPropiadeEsteAgente;
//        private ItfUsoRecursoTrazas ItfUsoRecTrazas;
         

	public AccionesSemanticasGestorOrganizacion() {
		 super();
//            try {
//		
//             itfGestionRecTrazas = (InterfazGestion) this.itfUsoRepositorio.obtenerInterfaz(
//                        NombresPredefinidos.ITF_GESTION + NombresPredefinidos.RECURSO_TRAZAS);
//            
//		} catch (Exception ex) {
//			 logger.fatal("No sepuede obtener la interfaz de gestion del recurso de trazas.");
//			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
//					"No sepuede obtener la interfaz de gestion del recurso de trazas.",
//					InfoTraza.NivelTraza.error));
//			ex.printStackTrace();
////			System.exit(1);
//		}
                
//                 trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
//                        "Construyendo agente reactivo " + nombreAgente + ".",
//                        InfoTraza.NivelTraza.debug));
	}

	/**
	 * Establece la configuracion para el gestor de Organizacion
	 */
	public void configurarGestor() {
		try {
			/*
			 * En esta accion semantica se configura todo aquello que sea
			 * necesario a partir del archivo xml
			 */
                    
                    trazas.setIdentAgenteAReportar(nombreAgente);
                    trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Configuracion del agente : " + nombreAgente + ".",
                        InfoTraza.NivelTraza.debug));
                    config = (ItfUsoConfiguracion) this.itfUsoRepositorio.obtenerInterfaz(
							NombresPredefinidos.ITF_USO+ NombresPredefinidos.CONFIGURACION);
                    tiempoParaNuevaMonitorizacion = Integer.parseInt(config.getValorPropiedadGlobal(NombresPredefinidos.INTERVALO_MONITORIZACION_ATR_PROPERTY));
                    itfUsoPropiadeEsteAgente=(ItfUsoAgenteReactivo)itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.ITF_USO
                                                                         +nombreAgente);
                     itfGestionRecTrazas = (InterfazGestion) this.itfUsoRepositorio.obtenerInterfaz(
                        NombresPredefinidos.ITF_GESTION + NombresPredefinidos.RECURSO_TRAZAS);
                        this.informaraMiAutomata("gestor_configurado");					
		} catch (Exception e) {
			e.printStackTrace();
			 logger.error("GestorRecursos: Hubo problemas al configurar el gestor de Organizacion.");
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Hubo problemas al configurar el gestor de Organizacion.",
					InfoTraza.NivelTraza.error));
		}

	}

	/**
	 * Crea el gestor de agentes y el gestor de recursos
	 */
	public void crearGestores() {
		try {
			// creo los gestores
//			logger.debug("GestorOrganizacion: Creando gestor de agentes...");
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Creando gestor de agentes ...",
					InfoTraza.NivelTraza.debug));
			
			// Gestor de Agentes: local o remoto?
			DescInstanciaGestor descGestor = config.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
			String esteNodo = descGestor.getNodo().getNombreUso();

			DescInstanciaGestor gestorRecursos = config.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_RECURSOS);
			String nodoDestino = gestorRecursos.getNodo().getNombreUso();
			int intentos = 0;
			boolean ok = false;		
			if (nodoDestino.equals(esteNodo)) {
//				FactoriaAgenteReactivo.instancia().crearAgenteReactivo(gestorAgentes);
                                FactoriaComponenteIcaro.instanceAgteReactInpObj().crearAgenteReactivo(gestorRecursos);
			}
                        
                        
//                        else {
//				while (!ok) {
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
//						trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
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
//			}

			logger.debug("GestorOrganizacion: Gestor de recursos creado.");
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Gestor de recursos creado.",
					InfoTraza.NivelTraza.debug));

//			Set<Object> conjuntoEventos = new HashSet<Object>();
//			conjuntoEventos.add(EventoRecAgte.class);

			// indico a quien debe reportar

		
			((ItfGestionAgenteReactivo) itfUsoRepositorio
					.obtenerInterfaz(
							NombresPredefinidos.ITF_GESTION
									+ NombresPredefinidos.NOMBRE_GESTOR_RECURSOS))
					.setGestorAReportar(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);

			logger.debug("GestorOrganizacion: Creando gestor de agentes ...");
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Creando gestor de agentes ...",
					InfoTraza.NivelTraza.debug));
			
			
			
			// Gestor de recursos: local o remoto?
			
			DescInstanciaAgente gestorAgentes = config.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_AGENTES); 
			 nodoDestino = gestorAgentes.getNodo().getNombreUso();
			if (nodoDestino.equals(esteNodo)) {
//				FactoriaAgenteReactivo.instancia().crearAgenteReactivo(gestorRecursos);
                                FactoriaComponenteIcaro.instanceAgteReactInpObj().crearAgenteReactivo(gestorAgentes);
			}
//                        else {
//				intentos = 0;
//				ok = false;
//				while (!ok) {
//					++intentos;
//					try {
//						((FactoriaAgenteReactivo) ClaseGeneradoraRepositorioInterfaces
//								.instance()
//								.obtenerInterfaz(
//										NombresPredefinidos.FACTORIA_AGENTE_REACTIVO
//												+ nodoDestino))
//								.crearAgenteReactivo(gestorRecursos);
//						ok = true;
//					} catch (Exception e) {
//						trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
//								"Error al crear agente "
//										+ NombresPredefinidos.NOMBRE_GESTOR_RECURSOS
//										+ " en un nodo remoto. Se volver� a intentar en "
//										+ intentos
//										+ " segundos...\n nodo origen: "
//										+ esteNodo + "\t nodo destino: "
//										+ nodoDestino,
//								InfoTraza.NivelTraza.error));
//						logger
//								.error("Error al crear agente "
//										+ NombresPredefinidos.NOMBRE_GESTOR_RECURSOS
//										+ " en un nodo remoto. Se volver� a intentar en "
//										+ intentos
//										+ " segundos...\n nodo origen: "
//										+ esteNodo + "\t nodo destino: "
//										+ nodoDestino);
//
//						Thread.sleep(1000 * intentos);
//						ok = false;
//					}
//				}
//			}
			logger.debug("GestorOrganizacion: Gestor de agentes creado.");
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Gestor de agentes creado.",
					InfoTraza.NivelTraza.debug));

			// indico a quien debe reportar
			((ItfGestionAgenteReactivo) this.itfUsoRepositorio.obtenerInterfaz(
							NombresPredefinidos.ITF_GESTION+ NombresPredefinidos.NOMBRE_GESTOR_AGENTES))
					.setGestorAReportar(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);

			logger.debug("GestorOrganizacion: Gestores registrados correctamente.");	
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Gestores registrados correctamente.",
					InfoTraza.NivelTraza.debug));
			this.informaraMiAutomata("gestores_creados");
   
                        
		} catch (Exception e) {
			logger.error("GestorOrganizacion: Fue imposible crear los gestores de agentes y recursos en el gestor de la organizacion",e);
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Fue imposible crear los gestores de agentes y recursos en el gestor de la organizacion",
					InfoTraza.NivelTraza.error));
			e.printStackTrace();
			try {
				this.informaraMiAutomata ("error_en_creacion_gestores");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public void arrancarGestorAgentes() {

		logger.debug("GestorOrganizacion: Arrancando Gestor de Agentes.");
		trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
				"Arrancando Gestor de Agentes.",
				InfoTraza.NivelTraza.debug));
		try {
			((ItfGestionAgenteReactivo) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
							+ NombresPredefinidos.NOMBRE_GESTOR_AGENTES))
					.arranca();
                        this.informaraMiAutomata("gestor_agentes_arrancado_ok");
			// this.itfUsoAgente.aceptaEvento(new
			// EventoRecAgte("gestor_agentes_arrancado_ok"));

		} catch (Exception e) {
			logger.error("GestorOrganizacion: Fue imposible arrancar el Gestor de Agentes.");
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Fue imposible arrancar el Gestor de Agentes.",
					InfoTraza.NivelTraza.error));
			e.printStackTrace();
			try {
//				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
//						"error_en_arranque_gestor_agentes",
//						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
//						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
                            this.informaraMiAutomata("error_en_arranque_gestor_agentes");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public void arrancarGestorRecursos() {

		logger.debug("GestorOrganizacion: Arrancando Gestor de Recursos.");
		trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
				"Arrancando Gestor de Recursos.",
				InfoTraza.NivelTraza.debug));
		try {
			((ItfGestionAgenteReactivo) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
							+ NombresPredefinidos.NOMBRE_GESTOR_RECURSOS))
					.arranca();
                        this.informaraMiAutomata("gestor_recursos_arrancado_ok");
			// this.itfUsoAgente.aceptaEvento(new
			// EventoRecAgte("gestor_recursos_arrancado_ok"));
		} catch (Exception e) {
			logger.error("GestorOrganizacion: Fue imposible arrancar el Gestor de Recursos.");
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Fue imposible arrancar el Gestor de Recursos.",
					InfoTraza.NivelTraza.error));
			e.printStackTrace();
			try {
//				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
//						"error_en_arranque_gestor_recursos",
//						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
//						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
                             this.informaraMiAutomata("error_en_arranque_gestor_recursos");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public void gestoresArrancadosConExito() {
		// creo hebra de monitorizacion

		hebra = new HebraMonitorizacion(tiempoParaNuevaMonitorizacion,
				this.itfUsoPropiadeEsteAgente, "monitorizar");
		this.hebra.start();
                this.generarTimeOut(tiempoParaNuevaMonitorizacion, "monitorizar", nombreAgente, nombreAgente);
		logger.debug("GestorOrganizacion: Gestor de la organizacion esperando peticiones.");
		trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
				"Gestor de la organizacion esperando peticiones.",
				InfoTraza.NivelTraza.debug));
	}

	/**
	 * Decide que hacer en caso de fallos en el gestor de agentes y/o en el
	 * gestor de recursos
	 */
	public void decidirTratamientoErrorIrrecuperable() {
            
		// el tratamiento ser� siempre cerrar todo el chiringuito
		logger.debug("GestorOrganizacion: Se decide cerrar el sistema ante un error critico irrecuperable.");
		trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
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
		trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
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
		logger.debug("GestorOrganizaci�n: Finalizando gestor de la organizacion debido a un error irrecuperable.");
		trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
				"Finalizando gestor de la organizaci�n debido a un error irrecuperable.",
				InfoTraza.NivelTraza.debug));
		try {
//			this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte("informe_generado",
//					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
//					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
                        this.informaraMiAutomata("informe_generado");
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

			//  hay problemas con el gestor de agentes ?
			errorAlMonitorizar = ((monitAgentes == InterfazGestion.ESTADO_ERRONEO_IRRECUPERABLE)
					|| (monitAgentes == InterfazGestion.ESTADO_ERRONEO_RECUPERABLE)
					|| (monitAgentes == InterfazGestion.ESTADO_TERMINADO) || (monitAgentes == InterfazGestion.ESTADO_TERMINANDO));

			if (errorAlMonitorizar) {

				logger.debug("GestorOrganizacion: Error al monitorizar gestores");
				trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
						"Error al monitorizar gestores",
						InfoTraza.NivelTraza.debug));
				if ((monitAgentes == InterfazGestion.ESTADO_ERRONEO_IRRECUPERABLE)
						|| (monitAgentes == InterfazGestion.ESTADO_ERRONEO_RECUPERABLE)) {
					logger.error("GestorOrganizacion: El GESTOR DE AGENTES ha fallado. Su estado es "+ monitAgentes);
					trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
						"El GESTOR DE AGENTES ha fallado. Su estado es "+ monitAgentes,
						InfoTraza.NivelTraza.error));
				}
				else{
					logger.error("GestorOrganizacion: El GESTOR DE RECURSOS ha fallado. Su estado es "+ monitRecursos);
					trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
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
	 * Da orden de terminacion al gestor de agentes si est� activos.
	 */
	public void terminarGestorAgentes() {
		// mandamos la orden de terminar a los gestores
		logger.debug("GestorOrganizaci�n: Terminando gestor de agentes");
		trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
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
//                    itfGestorAgentes.aceptaEvento(new EventoRecAgte("ordenTerminacion", nombreAgente, itfGestorAgentes.getIdentAgente()));
			itfGestorAgentes.aceptaMensaje(new MensajeSimple (new InfoContEvtMsgAgteReactivo("ordenTerminacion"), this.nombreAgente,NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
                    // timeout de 5 segundosnew
			this.generarTimeOut(2000, "timeout_gestor_agentes",
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);

		} catch (Exception ex) {
			logger.debug("GestorOrganizacion: No se pudo acceder al gestor de agentes.");
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"No se pudo acceder al gestor de agentes.",
					InfoTraza.NivelTraza.debug));
			ex.printStackTrace();
		}
	}

	/**
	 * Da orden de terminacion al gestor de recursos si esta activo.
	 */
	public void terminarGestorRecursos() {
		// mandamos la orden de terminar a los gestores
		logger.debug("GestorOrganizacion: Terminando gestor de recursos");
		trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
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
//                        gestorRecursos.aceptaEvento(new EventoRecAgte("ordenTerminacion", nombreAgente, gestorRecursos.getIdentAgente()));
                        gestorRecursos.aceptaMensaje(new MensajeSimple (new InfoContEvtMsgAgteReactivo("ordenTerminacion"), this.nombreAgente,NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
                    // timeout de 5 segundosnew
			// timeout de 5 segundos
			this.generarTimeOut(2000, "timeout_gestor_recursos",
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
		} catch (Exception ex) {
			logger.debug("GestorOrganizacion: No se pudo acceder al gestor de recursos.");
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"No se pudo acceder al gestor de recursos.",
					InfoTraza.NivelTraza.debug));
			ex.printStackTrace();
		}

	}

	public void procesarPeticionTerminacion() {
		logger.debug("GestorOrganizacion: Procesando la peticion de terminacion");
		trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
				"Procesando la peticion de terminacion",
				InfoTraza.NivelTraza.debug));

		trazas.setIdentAgenteAReportar(nombreAgente);
                trazas.pedirConfirmacionTerminacionAlUsuario();
		
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
		trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
				"Comenzando la terminacion de la organizacion...",
				InfoTraza.NivelTraza.info));
		try {
//           String estadoInternoAgente = this.ctrlGlobalAgenteReactivo.getItfControl().getEstadoControlAgenteReactivo();
//           ItfGestionAgenteReactivo gestion = (ItfGestionAgenteReactivo) this.itfUsoRepositorio
//				.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
//						+ NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
		//	gestion.termina();
//            this.itfUsoPropiadeEsteAgente.aceptaEvento(new
//			 EventoRecAgte ("termina",this.nombreAgente,this.nombreAgente));
//                        this.ctrlGlobalAgenteReactivo.getItfControl().getEstadoControlAgenteReactivo();
                        this.informaraMiAutomata("termina");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}

	public void recuperarErrorAlMonitorizarGestores() {
		// por defecto no se implementan pol�ticas de recuperaci�n
		logger.debug("GestorOrganizaci�n: No se pudo recuperar el error de monitorizaci�n.");
		trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
				"No se pudo recuperar el error de monitorizacion.",
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
	 * gestor de la organizacion-
	 */
	public void terminarGestorOrganizacion() {
		// termina el gestor.
		// puede esperarse a que terminen los dos gestores para mayor seguridad
		logger.debug("GestorOrganizacion: Terminando gestor de la organizacion y los recursos de la infraestructura.");
		trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
				"Terminando gestor de la organizacion y los recursos de la infraestructura.",
				InfoTraza.NivelTraza.debug));
		try {
			// se acaba con los recursos de la organizacion que necesiten ser
			// terminados
			itfGestionRecTrazas.termina();
                       
			// y a continuacion se termina el gestor de organizacion
			((ItfGestionAgenteReactivo) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
							+ NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION))
					.termina();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.debug("GestorOrganizacion: Cerrando sistema.");
		trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
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

    @Override
	public void clasificaError() {
	}

	public void tratarTerminacionNoConfirmada() {
		logger.debug("Se ha recibido un evento de timeout debido a que un gestor no ha confirmado la terminacion. Se procede a continuar la terminaci�n del sistema");
		trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
				"Se ha recibido un evento de timeout debido a que un gestor no ha confirmado la terminacion. Se procede a continuar la terminaci�n del sistema",
				InfoTraza.NivelTraza.debug));
		try {
//			this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
//					"continuaTerminacion",
//					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
//					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
                        this.informaraMiAutomata("continuaTerminacion");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}