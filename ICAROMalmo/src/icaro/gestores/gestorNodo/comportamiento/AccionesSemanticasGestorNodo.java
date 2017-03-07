/*
 * F garijo
 */
package icaro.gestores.gestorNodo.comportamiento;


//import icaro.infraestructura.corba.ORBDaemonExec;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.AdaptadorRegRMI;
import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgenteAplicacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaGestor;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.TipoAgente;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.FactoriaAgenteCognitivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.FactoriaAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfGestionAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp.HebraMonitorizacion;
import icaro.infraestructura.patronRecursoSimple.FactoriaRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
import java.io.Serializable;
import java.net.InetAddress;
import java.rmi.Remote;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que contiene las acciones necesarias para el gestor de Nodo
 * 
 
 * @created Enero 2011
 */
public class AccionesSemanticasGestorNodo extends
		AccionesSemanticasAgenteReactivo implements Serializable{
	// Tiempo que fijaremos para las monitorizaciones ciclicas
	/**
	 * @uml.property  name="tiempoParaNuevaMonitorizacion"
	 */
	protected long tiempoParaNuevaMonitorizacion;

	/**
	 * Hebra para que inyecte eventos de monitorizaci�n cada cierto tiempo
	 * @uml.property  name="hebra"
	 * @uml.associationEnd  
	 */
    private HebraMonitorizacion hebra;
    private transient InterfazGestion ItfGestionRecTrazas ;
    private transient ItfUsoConfiguracion config = null;
    private ItfUsoAgenteReactivo itfUsoPropiadeEsteAgente;
    private int puertoRMI;
//    private ComunicacionAgentes infoComunicacionAgtes;
    private String irEstadoListo = "listo";
    private String terminar = "termina";
    private String esteNodo;
	public AccionesSemanticasGestorNodo() {
		try {
			
//            itfUsoRepositorio = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
          
//            ItfGestionRecTrazas = (InterfazGestion) this.itfUsoRepositorio.obtenerInterfaz(
//                        NombresPredefinidos.ITF_GESTION + NombresPredefinidos.RECURSO_TRAZAS);
            trazas.setIdentAgenteAReportar(nombreAgente);
            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
                        "Construyendo agente reactivo " + nombreAgente + ".",
                        InfoTraza.NivelTraza.debug));
            config = (ItfUsoConfiguracion) this.itfUsoRepositorio.obtenerInterfaz(
							NombresPredefinidos.ITF_USO
									+ NombresPredefinidos.CONFIGURACION);
             itfUsoPropiadeEsteAgente=(ItfUsoAgenteReactivo)itfUsoRepositorio.obtenerInterfazUso(NombresPredefinidos.ITF_USO
                                                                         +nombreAgente);
		} catch (Exception ex) {
			 logger.fatal("GestorNodo: Fue imposible obtener el repositorio de interfaces.");
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Fue imposible obtener el repositorio de interfaces.",
					InfoTraza.NivelTraza.error));
			ex.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Establece la configuracion para el gestor de Nodo
	 */
     public void comenzar () {
        try { /* Saco mi configuracion */
//           // obtener la interfaz del RegistroRMI
        } catch (Exception ex) {
            Logger.getLogger(AccionesSemanticasGestorNodo.class.getName()).log(Level.SEVERE, null, ex);
//            trazarError ("No puedo comenzar, termino");
//            aceptarInput (terminar);
        }
        /* Arranco RMI */
       AdaptadorRegRMI.setPuertoRMI(puertoRMI);
//        boolean listo = ControlRMI.startRMI();
     if ( ! AdaptadorRegRMI.addElement2LocalRegRMI("GestorNodo", itfUsoPropiadeEsteAgente)){
//        trazarDebug ("Estado RMI en puerto " + puertoRMI + " ... " + listo);
        trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Arranco el RMI en el puerto:" +puertoRMI ,
					InfoTraza.NivelTraza.debug));
//        boolean exportado = ControlRMI.export("GestorNodo", itfUsoPropiadeEsteAgente);
        trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Se añade la interfaz del gestor de nodo :" +nombreAgente + " al RMI registry",
					InfoTraza.NivelTraza.debug));
         } else {
           trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"No he podido añadir mi interfaz al registo RMI" +puertoRMI ,
					InfoTraza.NivelTraza.error));
         }

//        trazarDebug ("Gestor exportado?... " + exportado);
//        aceptarInput (irEstadoListo);
    }
	public void configurarGestor() {
		try {
			/*
			 * En esta accion semantica se configura todo aquello que sea
			 * necesario a partir del archivo xml
			 */
			tiempoParaNuevaMonitorizacion = Integer.parseInt(config.getValorPropiedadGlobal(NombresPredefinidos.INTERVALO_MONITORIZACION_ATR_PROPERTY));
                        nombreAgente = this.getNombreAgente();
                        DescInstanciaGestor descGestor = config.getDescInstanciaGestor(nombreAgente);
                        esteNodo = InetAddress.getLocalHost().getHostName();
                        this.comunicator=this.getComunicator();

        /* Arranco RMI */
        AdaptadorRegRMI.inicializar();
        if ( AdaptadorRegRMI.addElement2LocalRegRMI(nombreAgente, itfUsoPropiadeEsteAgente)){
//        trazarDebug ("Estado RMI en puerto " + puertoRMI + " ... " + listo);

//        boolean exportado = ControlRMI.export("GestorNodo", itfUsoPropiadeEsteAgente);
            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Se añade la interfaz del gestor de nodo :" +nombreAgente + " al RMI registry",
					InfoTraza.NivelTraza.debug));
            this.informaraMiAutomata("gestor_configurado");
//            this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
//					"gestor_configurado",
//					nombreAgente,
//					nombreAgente));
            }else
                {
                    trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"No he podido añadir mi interfaz al registo RMI" +puertoRMI ,
					InfoTraza.NivelTraza.error));
                }
                  
		} catch (Exception e) {
			e.printStackTrace();
			 logger.error("GestorNodo: Hubo problemas al configurar el gestor de Nodo.");
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Hubo problemas al configurar el gestor de Nodo.",
					InfoTraza.NivelTraza.error));
		}

	}

	/**
	 * Crea el gestor de agentes y el gestor de recursos
	 */

         public void crearAgenteAplicacion(String identAgente) {

              try {
                DescInstanciaAgenteAplicacion   descAgente = config.getDescInstanciaAgenteAplicacion(identAgente);
                String tipoAgente = descAgente.getDescComportamiento().getTipo().value();
                    if (tipoAgente.equals(NombresPredefinidos.TIPO_REACTIVO))
                                         FactoriaAgenteReactivo.instancia().crearAgenteReactivo(descAgente);
                    else
                        if (tipoAgente.equals(NombresPredefinidos.TIPO_COGNITIVO))
                                        FactoriaAgenteCognitivo.instance().crearAgenteCognitivo(descAgente);
                        else {

                        }
                } catch (Exception e) {
			logger.error("GestorNodo : Fue imposible crear el agente : " +identAgente ,e);
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Fue imposible crear el agente : "+identAgente ,
					InfoTraza.NivelTraza.error));
                        trazas.trazar(nombreAgente, "Fue imposible crear el agente : "+identAgente , NivelTraza.error);
			e.printStackTrace();
                    }
                         try {
                             this.informaraMiAutomata("error_en_creacion_gestores");
//				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
//						"error_en_creacion_gestores",
//						NombresPredefinidos.NOMBRE_GESTOR_NODO,
//						NombresPredefinidos.NOMBRE_GESTOR_NODO));
			} catch (Exception e1) {
				e1.printStackTrace();
            }
      }

     public void crearGestor(String identGestor) {

     DescInstanciaGestor descGestor;
        try {
            logger.debug("GestorNodo: Creando el gestor ..." +identGestor);
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,"Creando gestor ..."+identGestor,
					InfoTraza.NivelTraza.debug));
            descGestor = config.getDescInstanciaGestor(identGestor);
            String nodoDestino = descGestor.getNodo().getNombreUso();
			int intentos = 0;
			boolean ok = false;

                  if (nodoDestino.equals(esteNodo)) {
				FactoriaAgenteReactivo.instancia().crearAgenteReactivo(
						descGestor);
			} else {
				while (!ok) {
					++intentos;
					try {
						((FactoriaAgenteReactivo) ClaseGeneradoraRepositorioInterfaces
								.instance()
								.obtenerInterfaz(
										NombresPredefinidos.FACTORIA_AGENTE_REACTIVO
												+ nodoDestino))
								.crearAgenteReactivo(descGestor);
						ok = true;
					} catch (Exception e) {
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
								"Error al crear el agente "
										+ NombresPredefinidos.NOMBRE_GESTOR_AGENTES
										+ " en un nodo remoto. Se volver� a intentar en "
										+ intentos
										+ " segundos...\n nodo origen: "
										+ esteNodo + "\t nodo destino: "
										+ nodoDestino,
								InfoTraza.NivelTraza.error));
						logger
								.error("Error al crear el agente "
										+ NombresPredefinidos.NOMBRE_GESTOR_AGENTES
										+ " en un nodo remoto. Se volvera a intentar en "
										+ intentos
										+ " segundos...\n nodo origen: "
										+ esteNodo + "\t nodo destino: "
										+ nodoDestino);

						Thread.sleep(1000 * intentos);

						ok = false;
					}
				}
			}

			logger.debug("GestorNodo: Gestor: + de agentes creado.");
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					identGestor+" Gestor  creado.",
					InfoTraza.NivelTraza.debug));

			Set<Object> conjuntoEventos = new HashSet<Object>();
			conjuntoEventos.add(EventoRecAgte.class);

			// indico a quien debe reportar. Por defecto se pone el gestor de nodo
                        // todos los gestores creados reportan a el


			((ItfGestionAgenteReactivo) itfUsoRepositorio
					.obtenerInterfaz(
							NombresPredefinidos.ITF_GESTION
									+ identGestor))
					.setGestorAReportar(
							nombreAgente);

			logger.debug("GestorNodo: Creando gestor de recursos ...");
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Creando gestor de  ..." + identGestor,
					InfoTraza.NivelTraza.debug));

                } catch (Exception e) {
			logger.error("GestorOrganizaci�n: Fue imposible crear los gestores de agentes y recursos en el gestor de la organizaci�n",e);
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"Fue imposible crear los gestores de agentes y recursos en el gestor de la organizaci�n",
					InfoTraza.NivelTraza.error));
			e.printStackTrace();
			try {
                            this.informaraMiAutomata("error_en_creacion_gestores");
//				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
//						"error_en_creacion_gestores",
//						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
//						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
			} catch (Exception e1) {
				e1.printStackTrace();
            }
        }
    }
	public void crearGestores() {
		try {
//			 config = (ItfUsoConfiguracion) this.itfUsoRepositorio.obtenerInterfaz(
//							NombresPredefinidos.ITF_USO
//									+ NombresPredefinidos.CONFIGURACION);

			// creo los gestores
			logger.debug("GestorNodo: Creando gestor de agentes...");
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Creando gestor de agentes ...",
					InfoTraza.NivelTraza.debug));
			
			// Gestor de Agentes: local o remoto?
			DescInstanciaGestor descGestor = config.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
			String esteNodo = descGestor.getNodo().getNombreUso();

			DescInstanciaGestor gestorAgentes = config.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_AGENTES);
			String nodoDestino = gestorAgentes.getNodo().getNombreUso();
			int intentos = 0;
			boolean ok = false;
			if (nodoDestino.equals(esteNodo)) {
				FactoriaAgenteReactivo.instancia().crearAgenteReactivo(
						gestorAgentes);
			} else {
				while (!ok) {
					++intentos;
					try {
						((FactoriaAgenteReactivo) ClaseGeneradoraRepositorioInterfaces
								.instance()
								.obtenerInterfaz(
										NombresPredefinidos.FACTORIA_AGENTE_REACTIVO
												+ nodoDestino))
								.crearAgenteReactivo(gestorAgentes);
						ok = true;
					} catch (Exception e) {
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
								"Error al crear el agente "
										+ NombresPredefinidos.NOMBRE_GESTOR_AGENTES
										+ " en un nodo remoto. Se volver� a intentar en "
										+ intentos
										+ " segundos...\n nodo origen: "
										+ esteNodo + "\t nodo destino: "
										+ nodoDestino,
								InfoTraza.NivelTraza.error));
						logger
								.error("Error al crear el agente "
										+ NombresPredefinidos.NOMBRE_GESTOR_AGENTES
										+ " en un nodo remoto. Se volver� a intentar en "
										+ intentos
										+ " segundos...\n nodo origen: "
										+ esteNodo + "\t nodo destino: "
										+ nodoDestino);

						Thread.sleep(1000 * intentos);

						ok = false;
					}
				}
			}

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

			logger.debug("GestorOrganizaci�n: Creando gestor de recursos ...");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"Creando gestor de recursos ...",
					InfoTraza.NivelTraza.debug));
			
			
			
			// Gestor de recursos: local o remoto?
			
			DescInstanciaAgente gestorRecursos = config.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_RECURSOS); 
			 nodoDestino = gestorRecursos.getNodo().getNombreUso();
			if (nodoDestino.equals(esteNodo)) {
				FactoriaAgenteReactivo.instancia().crearAgenteReactivo(
						gestorRecursos);
			} else {
				intentos = 0;
				ok = false;
				while (!ok) {
					++intentos;
					try {
						((FactoriaAgenteReactivo) ClaseGeneradoraRepositorioInterfaces
								.instance()
								.obtenerInterfaz(
										NombresPredefinidos.FACTORIA_AGENTE_REACTIVO
												+ nodoDestino))
								.crearAgenteReactivo(gestorRecursos);
						ok = true;
					} catch (Exception e) {
						trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
								"Error al crear agente "
										+ NombresPredefinidos.NOMBRE_GESTOR_RECURSOS
										+ " en un nodo remoto. Se volver� a intentar en "
										+ intentos
										+ " segundos...\n nodo origen: "
										+ esteNodo + "\t nodo destino: "
										+ nodoDestino,
								InfoTraza.NivelTraza.error));
						logger
								.error("Error al crear agente "
										+ NombresPredefinidos.NOMBRE_GESTOR_RECURSOS
										+ " en un nodo remoto. Se volver� a intentar en "
										+ intentos
										+ " segundos...\n nodo origen: "
										+ esteNodo + "\t nodo destino: "
										+ nodoDestino);

						Thread.sleep(1000 * intentos);
						ok = false;
					}
				}
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

			logger.debug("GestorOrganizaci�n: Gestores registrados correctamente.");
			
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"Gestores registrados correctamente.",
					InfoTraza.NivelTraza.debug));
                        this.informaraMiAutomata("gestores_creados");
//			this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte("gestores_creados",
//					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
//					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));

		} catch (Exception e) {
			logger.error("GestorOrganizacion: Fue imposible crear los gestores de agentes y recursos en el gestor de la organizaci�n",e);
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"Fue imposible crear los gestores de agentes y recursos en el gestor de la organizaci�n",
					InfoTraza.NivelTraza.error));
			e.printStackTrace();
			try {
                             this.informaraMiAutomata("error_en_creacion_gestores");
//				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
//						"error_en_creacion_gestores",
//						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
//						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public void arrancarGestorAgentes() {

		logger.debug("GestorOrganizaci�n: Arrancando Gestor de Agentes.");
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
			logger.error("GestorOrganizaci�n: Fue imposible arrancar el Gestor de Agentes.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"Fue imposible arrancar el Gestor de Agentes.",
					InfoTraza.NivelTraza.error));
			e.printStackTrace();
			try {
                             this.informaraMiAutomata("error_en_arranque_gestor_agentes");
//				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
//						"error_en_arranque_gestor_agentes",
//						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
//						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
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
			logger.error("GestorOrganizaci�n: Fue imposible arrancar el Gestor de Recursos.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"Fue imposible arrancar el Gestor de Recursos.",
					InfoTraza.NivelTraza.error));
			e.printStackTrace();
			try {
                             this.informaraMiAutomata("error_en_arranque_gestor_recursos");
//				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
//						"error_en_arranque_gestor_recursos",
//						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
//						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
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
		logger.debug("GestorOrganizaci�n: Se decide cerrar el sistema ante un error cr�tico irrecuperable.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Se decide cerrar el sistema ante un error cr�tico irrecuperable.",
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
                     this.informaraMiAutomata("imposible_recuperar_arranque");
//			this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
//					"imposible_recuperar_arranque",
//					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
//					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
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
                    this.informaraMiAutomata("informe_generado");
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

				logger.debug("GestorOrganizaci�n: Error al monitorizar gestores");
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
					logger.error("GestorOrganizaci�n: El GESTOR DE RECURSOS ha fallado. Su estado es "+ monitRecursos);
					trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
						"El GESTOR DE RECURSOS ha fallado. Su estado es "+ monitRecursos,
						InfoTraza.NivelTraza.error));
                                        this.informaraMiAutomata("error_al_monitorizar");
//				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
//						"error_al_monitorizar",
//						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
//						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
				}
			} 
			else {
                            this.informaraMiAutomata("gestores_ok");
//				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte("gestores_ok",
//						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
//						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
				// if(DEBUG) System.out.println("GestorOrganizaci�n:
				// Monitorizaci�n de los gestores ok");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			try {
                            this.informaraMiAutomata("error_al_monitorizar");
//				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
//						"error_al_monitorizar",
//						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
//						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Da orden de terminaci�n al gestor de agentes si est� activos.
	 */
	public void terminarGestorAgentes() {
		// mandamos la orden de terminar a los gestores
		logger.debug("GestorOrganizaci�n: Terminando gestor de agentes");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Terminando gestor de agentes",
				InfoTraza.NivelTraza.debug));
		InterfazGestion gestorAgentes;
		try {
			gestorAgentes = (ItfGestionAgenteReactivo) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
							+ NombresPredefinidos.NOMBRE_GESTOR_AGENTES);
			if (gestorAgentes != null){
                                gestorAgentes.termina();

			// timeout de 5 segundos
                            this.generarTimeOut(2000, "timeout_gestor_agentes",
					nombreAgente,
					nombreAgente);
                            }else
                            this.informaraMiAutomata("continuaTerminacion");
//                                this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
//					"continuaTerminacion",
//					nombreAgente,
//					nombreAgente));

		} catch (Exception ex) {
			logger.debug("GestorOrganizacion: No se pudo acceder al gestor de agentes.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"No se pudo acceder al gestor de agentes.",
					InfoTraza.NivelTraza.debug));
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
		InterfazGestion gestorRecursos;
		try {
			gestorRecursos = (ItfGestionAgenteReactivo) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
							+ NombresPredefinidos.NOMBRE_GESTOR_RECURSOS);
			if (gestorRecursos != null){
                        gestorRecursos.termina();

			// timeout de 5 segundos
			this.generarTimeOut(2000, "timeout_gestor_recursos",
					nombreAgente,
					nombreAgente);
                    }else
                            this.informaraMiAutomata("continuaTerminacion");    
//                            this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
//					"continuaTerminacion",
//					nombreAgente,
//					nombreAgente));
		} catch (Exception ex) {
			logger.debug("GestorOrganizacion: No se pudo acceder al gestor de recursos.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"No se pudo acceder al gestor de recursos.",
					InfoTraza.NivelTraza.debug));
			ex.printStackTrace();
		}

	}
  private void crearUnAgenteReactivo(String identAgenteAcrear) throws Exception {
        try {

            if (config == null) config = (ItfUsoConfiguracion) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.CONFIGURACION);
            logger.debug("GestorNodo: Construyendo agente reactivo " + nombreAgente + ".");

                trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
                        "Construyendo agente reactivo " + nombreAgente + ".",
                        InfoTraza.NivelTraza.debug));


            // creamos el reactivo y lo registramos en el repositorio

            // Agentes de aplicacion: local o remoto?
//			DescInstanciaGestor descGestorAgentes = config.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_AGENTES);
//			String esteNodo = descGestorAgentes.getNodo().getNombreUso();

			DescInstanciaAgenteAplicacion instancia = config.getDescInstanciaAgenteAplicacion(identAgenteAcrear);
			String nodoDestino = instancia.getNodo().getNombreUso();

            boolean ok = false;
            int intentos = 0;
            if (nodoDestino.equalsIgnoreCase(esteNodo)) {
                FactoriaAgenteReactivo.instancia().crearAgenteReactivo(instancia);
            } else {
//                while (!ok) {
//                    ++intentos;
//                    try {
//                        ((FactoriaAgenteReactivo) this.itfUsoRepositorio.obtenerInterfaz(
//                                NombresPredefinidos.FACTORIA_AGENTE_REACTIVO + nodoDestino)).crearAgenteReactivo(instancia);
//                        ok = true;
//                    } catch (Exception e) {

                            trazas.aceptaNuevaTraza(new InfoTraza(
                                    "GestorNodo",
                                    "El nodo especificado:" + nodoDestino + " para el agente " + identAgenteAcrear + " no se corresponde con este nodo  " + esteNodo ,
                                    InfoTraza.NivelTraza.error));


                        logger.error("El nodo especificado:" + nodoDestino + " para el agente " + identAgenteAcrear + " no se corresponde con este nodo  " + esteNodo );

//                        Thread.sleep(1000 * intentos);
//                        ok = false;
                    }
//                }
//            }

            /*
             * logger.debug("GestorAgentes: Agente reactivo " + agente + "
             * creado.");
             */


//                trazas.aceptaNuevaTraza(new InfoTraza("GestorNodo",
//                        "Agente reactivo " + identAgenteAcrear + " creado.",
//                        InfoTraza.NivelTraza.debug));
//
//
//            Set<Object> conjuntoEventos = new HashSet<Object>();
//            conjuntoEventos.add(EventoRecAgte.class);
//
//            // indico a quien debe reportar
//            ((ItfGestionAgenteReactivo) this.itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.ITF_GESTION + nombreAgente)).setGestorAReportar(
//                    NombresPredefinidos.NOMBRE_GESTOR_AGENTES);

        } catch (Exception ex) {

            logger.error("GestorNodo: Error al crear el agente reactivo " +
                    identAgenteAcrear + ".", ex);



                trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
                        "Error al crear el agente reactivo " + identAgenteAcrear + ".",
                        InfoTraza.NivelTraza.error));

            throw ex;
        }




    }
private void crearUnAgenteCognitivo(String nombreAgente) throws Exception {
        try {

            if (config == null) config = (ItfUsoConfiguracion) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.CONFIGURACION);
            logger.debug("GestorAgentes: Construyendo agente Cognitivo " + nombreAgente + ".");

                trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
                        "Construyendo agente Cognitivo " + nombreAgente + ".",
                        InfoTraza.NivelTraza.debug));


            // creamos el agente y lo registramos en el repositorio

            // Agentes de aplicacion: local o remoto?
			DescInstanciaGestor descGestorAgentes = config.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_AGENTES);
			String esteNodo = descGestorAgentes.getNodo().getNombreUso();

			DescInstanciaAgenteAplicacion instancia = config.getDescInstanciaAgenteAplicacion(nombreAgente);
			String nodoDestino = instancia.getNodo().getNombreUso();

            boolean ok = false;
            int intentos = 0;
            if (nodoDestino.equals(esteNodo)) {
//                FactoriaAgenteCognitivo.instance().createCognitiveAgent(instancia);
                FactoriaAgenteCognitivo.instance().crearAgenteCognitivo(instancia);
            } else {
                while (!ok) {
                    ++intentos;
                    try {
                        ((FactoriaAgenteCognitivo) this.itfUsoRepositorio.obtenerInterfaz(
                                NombresPredefinidos.FACTORIA_AGENTE_COGNITIVO + nodoDestino)).crearAgenteCognitivo(instancia);
                        ok = true;
                    } catch (Exception e) {

                            trazas.aceptaNuevaTraza(new InfoTraza(
                                    "GestorAgentes",
                                    "Error al crear el agente " + nombreAgente + " en un nodo remoto. Se volver� a intentar en " + intentos + " segundos...\n nodo origen: " + esteNodo + "\t nodo destino: " + nodoDestino,
                                    InfoTraza.NivelTraza.error));


                        logger.error("Error al crear el agente " + nombreAgente + " en un nodo remoto. Se volver� a intentar en " +
                                intentos + " segundos...\n nodo origen: " + esteNodo +
                                "\t nodo destino: " + nodoDestino);

                        Thread.sleep(1000 * intentos);
                        ok = false;
                    }
                }
            }

            /*
             * logger.debug("GestorAgentes: Agente reactivo " + agente + "
             * creado.");
             */


                trazas.aceptaNuevaTraza(new InfoTraza("GestorAgentes",
                        "Agente Cognitivo " + nombreAgente + " creado.",
                        InfoTraza.NivelTraza.debug));


            Set<Object> conjuntoEventos = new HashSet<Object>();
            conjuntoEventos.add(EventoRecAgte.class);

            // indico a qui�n debe reportar
//            ((ItfGestionAgenteReactivo) this.itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.ITF_GESTION + nombreAgente)).setGestorAReportar(
//                    NombresPredefinidos.NOMBRE_GESTOR_AGENTES, conjuntoEventos);

        } catch (Exception ex) {

            logger.error("GestorAgentes: Error al crear el agente Cognitivo " +
                    nombreAgente + ".", ex);



                trazas.aceptaNuevaTraza(new InfoTraza("GestorAgentes",
                        "Error al crear el agente Cognitivo " + nombreAgente + ".",
                        InfoTraza.NivelTraza.error));

            throw ex;
        }




    }
public void crearUnRecursoEnNodoLocal(String  identInstRecurso) throws Exception {
		try {

//			ItfUsoConfiguracion config = (ItfUsoConfiguracion) ClaseGeneradoraRepositorioInterfaces
//					.instance().obtenerInterfaz(
//							NombresPredefinidos.ITF_USO
//									+ NombresPredefinidos.CONFIGURACION);
                       if (config == null) config = (ItfUsoConfiguracion) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
                             NombresPredefinidos.ITF_USO + NombresPredefinidos.CONFIGURACION);

			logger.debug("GestorNodo: Construyendo recurso " + identInstRecurso+ ".");
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Construyendo recurso " + identInstRecurso+ ".",
					InfoTraza.NivelTraza.debug));
			// Recurso de aplicacion: local o remoto?


			DescInstanciaRecursoAplicacion descRecurso = config.getDescInstanciaRecursoAplicacion(identInstRecurso);
//			String esteNodo = descGestorRecursos.getNodo().getNombreUso();
//
//			String nodoDestino = recurso.getNodo().getNombreUso();
			int intentos = 0;
			boolean ok = false;
             // Se crea el recurso en el mismo nodo
			FactoriaRecursoSimple.instance().crearRecursoSimple(descRecurso);
                        this.informaraMiAutomata("recurso_creado");
			logger.debug("GestorRecursos: Recurso " + descRecurso.getId() + " creado.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Recurso " + descRecurso.getId() + " creado.",
					InfoTraza.NivelTraza.debug));

		} catch (Exception ex) {
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Error al crear el recurso " + identInstRecurso+ ".",
					InfoTraza.NivelTraza.error));
			logger.error("GestorRecursos: Error al crear el recurso " + identInstRecurso+ ".", ex);
                        this.informaraMiAutomata("error_en_creacion_recurso");  
//                        this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
//						"error_en_creacion_recurso",
//						nombreAgente,
//						nombreAgente));
			throw ex;
		}
	}
public void crearUnRecursoEnNodoLocalyPublicIntfEnRMIO(String  identInstRecurso) throws Exception {

                // se crea un recurso local y se publican sus interfaces de uso y de gestión en el rep RMI
                try {

//			ItfUsoConfiguracion config = (ItfUsoConfiguracion) ClaseGeneradoraRepositorioInterfaces
//					.instance().obtenerInterfaz(
//							NombresPredefinidos.ITF_USO
//									+ NombresPredefinidos.CONFIGURACION);
                       if (config == null) config = (ItfUsoConfiguracion) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
                             NombresPredefinidos.ITF_USO + NombresPredefinidos.CONFIGURACION);

			logger.debug("GestorNodo: Construyendo recurso " + identInstRecurso+ ".");
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Construyendo recurso " + identInstRecurso+ ".",
					InfoTraza.NivelTraza.debug));
			// Recurso de aplicacion: local o remoto?


			DescInstanciaRecursoAplicacion descRecurso = config.getDescInstanciaRecursoAplicacion(identInstRecurso);
//			String esteNodo = descGestorRecursos.getNodo().getNombreUso();
//
//			String nodoDestino = recurso.getNodo().getNombreUso();
			int intentos = 0;
			boolean ok = false;
             // Se crea el recurso en el mismo nodo
			FactoriaRecursoSimple.instance().crearRecursoSimple(descRecurso);                
                        this.informaraMiAutomata("recursoCreadoyRegistrado");
			logger.debug("GestorRecursos: Recurso " + descRecurso.getId() + " creado.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Recurso " + descRecurso.getId() + " creado.",
					InfoTraza.NivelTraza.debug));

                     if(! addEntityIntfs2Local_RMIregistry(identInstRecurso) ){

                             trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Error al crear el recurso. No se puede añadir al RMI registry local " + identInstRecurso+ ".",
					InfoTraza.NivelTraza.error));
			logger.error("GestorNodo: Error al crear el recurso. No se puede añadir al RMI registry local  " + identInstRecurso+ ".");
                             }
                            else {
                           
            // Se informa al Gestor de Recursos de la organizacion de la creacion y registro del recurso
//                       config.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_RECURSOS).getNodo().getNombreUso();
                        ItfUsoAgenteReactivo itfUsoAgteReceptor = (ItfUsoAgenteReactivo) AdaptadorRegRMI.getItfAgenteRemoto(NombresPredefinidos.NOMBRE_GESTOR_RECURSOS, NombresPredefinidos.ITF_USO);
                        if (itfUsoAgteReceptor != null) {
                                comunicator.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo("RecursoRemotoCreadoyRegistrado") , NombresPredefinidos.NOMBRE_GESTOR_RECURSOS, itfUsoAgteReceptor);
                            } else {
                                         trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"No he podido obtener la interfaz   del agente: " +NombresPredefinidos.NOMBRE_GESTOR_RECURSOS ,
					InfoTraza.NivelTraza.error));
                             }
                        }
		} catch (Exception ex) {
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Error al crear el recurso " + identInstRecurso+ ".",
					InfoTraza.NivelTraza.error));
			logger.error("GestorNodo: Error al crear el recurso " + identInstRecurso+ ".", ex);
                        this.informaraMiAutomata("error_en_creacion_recurso");  
//                        this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
//						"error_en_creacion_recurso",
//						nombreAgente,
//						nombreAgente));
			throw ex;
		}
	}
public void crearUnAgenteEnNodoLocalyPublicIntfEnRMIO(String  identAgenteAcrear) throws Exception {

                // se crea un recurso local y se publican sus interfaces de uso y de gestión en el rep RMI
                try {

//			ItfUsoConfiguracion config = (ItfUsoConfiguracion) ClaseGeneradoraRepositorioInterfaces
//					.instance().obtenerInterfaz(
//							NombresPredefinidos.ITF_USO
//									+ NombresPredefinidos.CONFIGURACION);
                       if (config == null) config = (ItfUsoConfiguracion) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
                             NombresPredefinidos.ITF_USO + NombresPredefinidos.CONFIGURACION);

			logger.debug("GestorNodo: Construyendo agente " + identAgenteAcrear+ ".");
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Construyendo agente " + identAgenteAcrear+ ".",
					InfoTraza.NivelTraza.debug));
			// Recurso de aplicacion: local o remoto?


			DescInstanciaAgenteAplicacion instanciaActual = config.getDescInstanciaAgenteAplicacion(identAgenteAcrear);
//			String esteNodo = descGestorRecursos.getNodo().getNombreUso();
//			String nodoDestino = recurso.getNodo().getNombreUso();
                        String nodoDestino = instanciaActual.getNodo().getNombreUso();

                        TipoAgente tipoAgenteAcrear = instanciaActual.getDescComportamiento().getTipo();
                        if (!nodoDestino.equalsIgnoreCase(esteNodo)) {

                            trazas.aceptaNuevaTraza(new InfoTraza(
                                    "GestorNodo",
                                    "El nodo especificado:" + nodoDestino + " para el agente " + identAgenteAcrear + " no se corresponde con este nodo  " + esteNodo ,
                                    InfoTraza.NivelTraza.error));
                            logger.error("El nodo especificado:" + nodoDestino + " para el agente " + identAgenteAcrear + " no se corresponde con este nodo  " + esteNodo );

                                }
                        else {

                        if (tipoAgenteAcrear == TipoAgente.REACTIVO) {
                            FactoriaAgenteReactivo.instancia().crearAgenteReactivo(instanciaActual);
                            } else

                             if (tipoAgenteAcrear != TipoAgente.COGNITIVO) { // no es ni cognitivo ni reactivo
                                  trazas.aceptaNuevaTraza(new InfoTraza(    nombreAgente,
                                "El subtipo del agente " + identAgenteAcrear + " definido en la configuracion no es correcto.",
                                InfoTraza.NivelTraza.error));
                                logger.error("El subtipo del agente " + identAgenteAcrear + " definido en la configuracion no es correcto.");

                                throw new Exception("El subtipo del agente " + identAgenteAcrear + " definido en la configuracion no es correcto.");
                                }
                             else
                                 FactoriaAgenteCognitivo.instance().crearAgenteCognitivo(instanciaActual);
                   // Se genera informacion para que transite el automata y se registran las interfaces
                        

                    if(! addEntityIntfs2Local_RMIregistry(identAgenteAcrear) ){

                             trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Error al crear el agente remoto. No se puede añadir al RMI registry local " + identAgenteAcrear+ ".",
					InfoTraza.NivelTraza.error));
                            logger.error("GestorNodo: Error al crear el agente remoto. No se puede añadir al RMI registry local  " + identAgenteAcrear+ ".");
                             }
                            else {

            // Se informa al Gestor de Agentes de la organizacion de la creacion y registro del recurso
//                       config.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_RECURSOS).getNodo().getNombreUso();
                        ItfUsoAgenteReactivo itfUsoAgteReceptor = (ItfUsoAgenteReactivo) AdaptadorRegRMI.getItfAgenteRemoto(NombresPredefinidos.NOMBRE_GESTOR_AGENTES, NombresPredefinidos.ITF_USO);
                        if (itfUsoAgteReceptor != null) {
                            // Construimos los parametros que se envian con el input
                                Object [] param = {identAgenteAcrear};
                                comunicator.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo("agenteRemotoCreadoyRegistrado",param) , NombresPredefinidos.NOMBRE_GESTOR_AGENTES, itfUsoAgteReceptor);
                            } else {
                                         trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"No he podido obtener la interfaz   del agente: " +NombresPredefinidos.NOMBRE_GESTOR_AGENTES ,
					InfoTraza.NivelTraza.error));
                             }
                        this.informaraMiAutomata("agenteRemoto_creado");
			logger.debug("GestorNodo: Recurso " + identAgenteAcrear + " creado.");
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,"Agente " + identAgenteAcrear + " creado.",
					InfoTraza.NivelTraza.debug));
                    }
                  }
		} catch (Exception ex) {
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Error al crear el agente " + identAgenteAcrear+ ".",
					InfoTraza.NivelTraza.error));
			logger.error("GestorNodo: Error al crear el agente " + identAgenteAcrear+ ".", ex);
                        this.informaraMiAutomata("error_en_creacion_agente");  
//                        this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
//						"error_en_creacion_agente",
//						nombreAgente,
//						nombreAgente));
			throw ex;
		}
	}

public Boolean addEntityIntfs2RMI_ORGregistry (String identEntity){
    Boolean interfacesRegistradas = false;
    try {
    Remote itfEntity = (Remote)itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.ITF_USO+identEntity);
        if (itfEntity != null)
                        if (  AdaptadorRegRMI.addElementRegRMIOrg(NombresPredefinidos.ITF_USO+identEntity, itfEntity)){
                        trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Se añaden las Itfs de uso y de gestion de la entidad :" +identEntity + " -- al RMI registry de la organizacion",
					InfoTraza.NivelTraza.debug));
                            } else {
                                         trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"No he podido añadir la entidad :" +identEntity + " -- al RMI registry de la organizacion.",
					InfoTraza.NivelTraza.error));
//                                         informaraMiAutomata("error_en_registroRemoto_recurso");
                                         return interfacesRegistradas;
                                } else {
                                    trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"No he podido añadir la entidad :" +identEntity + " -- al RMI registry de la organizacion. La entidad no ha sido registrada en el registro local",
					InfoTraza.NivelTraza.error));
//                                        informaraMiAutomata("error_en_registroRemoto_recurso");
                                        return interfacesRegistradas;
                                }
         itfEntity = (Remote)itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.ITF_GESTION+identEntity);
             if (itfEntity != null)
                        if (  AdaptadorRegRMI.addElement2LocalRegRMI(NombresPredefinidos.ITF_GESTION+identEntity, itfEntity)){
                        trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Se añaden las Itfs de uso y de gestion de la entidad :" +identEntity + " -- al RMI registry de la organizacion",
					InfoTraza.NivelTraza.debug));
                                        interfacesRegistradas = true ;

                            } else {
                                         trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"No he podido añadir la entidad :" +identEntity + " -- al RMI registry de la organizacion .",
					InfoTraza.NivelTraza.error));
                                          return interfacesRegistradas;
//                                         informaraMiAutomata("error_en_registroRemoto_recurso");
                                } else {
                                    trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"No he podido añadir la entidad :" +identEntity + " -- al RMI registry de la organizacion. La entidad no ha sido registrada en el registro local",
					InfoTraza.NivelTraza.error));
                                     return interfacesRegistradas;
//                                        informaraMiAutomata("error_en_registroRemoto_recurso");
                                }

		} catch (Exception ex) {
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Error al registrar  la entidad " + identEntity+ ". en el registro RMI de la organizacion",
					InfoTraza.NivelTraza.error));
			logger.error("GestorRecursos: Error al crear el recurso " + identEntity+ ".en el registro RMI de la organizacion", ex);
                        informaraMiAutomata("error_en_registroRemoto_recurso");
//			throw ex;
		}
     return interfacesRegistradas;
}
public Boolean addEntityIntfs2Local_RMIregistry (String identEntity){
Boolean interfacesRegistradas = false;
    try {
    Remote itfEntity = (Remote)itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.ITF_USO+identEntity);
        if (itfEntity != null)
                        if (  AdaptadorRegRMI.addElement2LocalRegRMI(NombresPredefinidos.ITF_USO+identEntity, itfEntity)){
                        trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Se añaden las Itfs de uso y de gestion de la entidad :" +identEntity + " -- al RMI registry local",
					InfoTraza.NivelTraza.debug));
                            } else {
                                         trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"No he podido añadir la entidad :" +identEntity + " -- al RMI registry local.",
					InfoTraza.NivelTraza.error));
                                         informaraMiAutomata("error_en_registroRemoto_recurso");
                                         return interfacesRegistradas;
                                } else {
                                    trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"No he podido añadir la entidad :" +identEntity + " -- al RMI registry. La entidad no ha sido registrada en el registro local",
					InfoTraza.NivelTraza.error));
                                        informaraMiAutomata("error_en_registroRemoto_recurso");
                                        return interfacesRegistradas;
                                }
         itfEntity = (Remote)itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.ITF_GESTION+identEntity);
             if (itfEntity != null)
                        if (  AdaptadorRegRMI.addElement2LocalRegRMI(NombresPredefinidos.ITF_GESTION+identEntity, itfEntity)){
                        trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Se añaden las Itfs de uso y de gestion de la entidad :" +identEntity + " -- al RMI registry local",
					InfoTraza.NivelTraza.debug));
                                        interfacesRegistradas = true;
                            } else {
                                         trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"No he podido añadir la entidad :" +identEntity + " -- al RMI registry local.",
					InfoTraza.NivelTraza.error));
                                         informaraMiAutomata("error_en_registroRemoto_recurso");
                                         return false;
                                } else {
                                    trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"No he podido añadir la entidad :" +identEntity + " -- al RMI registry. La entidad no ha sido registrada en el registro local",
					InfoTraza.NivelTraza.error));
                                        informaraMiAutomata("error_en_registroRemoto_recurso");
                                        return false;
                                }

		} catch (Exception ex) {
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					"Error al registrar  la entidad " + identEntity+ ".",
					InfoTraza.NivelTraza.error));
			logger.error("GestorRecursos: Error al crear el recurso " + identEntity+ ". En el registro RMI local", ex);
                        informaraMiAutomata("error_en_registroRemoto_recurso");
//			throw ex;
		}
            return interfacesRegistradas;
}
public void arrancarRecurso(String identRecurso) {
		logger.debug("GestorRecursos: Arrancando recurso.");
		trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
				"Arrancando recurso :"+identRecurso,
				InfoTraza.NivelTraza.debug));
		boolean errorEnArranque = false;

		// seleccionamos el recurso que corresponde
//		String nombre = (String) this.nombresRecursosGestionados
//				.elementAt(indice.intValue());
		try {
			// recuperamos el interfaz de gestion del recurso
			logger.debug("GestorRecursos: Es necesario arrancar el recurso "+ identRecurso + ", recuperando interfaz de gesti�n.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Es necesario arrancar el recurso "+ identRecurso + ", recuperando interfaz de gesti�n.",
					InfoTraza.NivelTraza.debug));
			InterfazGestion itfGesAg = (InterfazGestion) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION + identRecurso);
			// arrancamos el recurso
			logger.debug("GestorRecursos: Arrancando el recurso " + identRecurso+ ".");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Arrancando el recurso " + identRecurso+ ".",
					InfoTraza.NivelTraza.debug));
			if (itfGesAg!= null){
                             itfGesAg.arranca();
                             logger.debug("GestorRecursos: Orden de arranque ha sido dada al recurso "+ identRecurso + ".");
                             trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Orden de arranque ha sido dada al recurso "+ identRecurso + ".",
					InfoTraza.NivelTraza.debug));
                        }else {
                            logger.debug("GestorRecursos: No se puede dar la orden de arranque al recurso "+ identRecurso + ". Porque su interfaz es null");
                             trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					" No se puede dar la orden de arranque al recurso "+ identRecurso + ". Porque su interfaz es null",
					InfoTraza.NivelTraza.debug));
                                        errorEnArranque = true;
                        }
		} catch (Exception ex) {
			logger.error("GestorRecursos: Hubo un problema al acceder al interfaz remoto mientras se arrancaba el recurso "	+ identRecurso + " en el gestor de recursos.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Hubo un problema al acceder al interfaz remoto mientras se arrancaba el recurso "	+ identRecurso + " en el gestor de recursos.",
					InfoTraza.NivelTraza.error));
			ex.printStackTrace();
			errorEnArranque = true;
		}
		if (errorEnArranque) { // ha ocurrido alg�n problema en el arranque del
								// recurso
			try {
                            this.informaraMiAutomata("error_en_arranque_recurso");  
//				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
//						"error_en_arranque_recurso",
//						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
//						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.error("GestorRecursos: Se produjo un error en el arranque del recurso "+ identRecurso + ".");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Se produjo un error en el arranque del recurso "+ identRecurso + ".",
					InfoTraza.NivelTraza.error));

		} else {// el recurso ha sido arrancado


					this.informaraMiAutomata("recurso_arrancado");
//                                        this.itfUsoGestorAReportar.aceptaEvento(new EventoRecAgte(
//							"gestor_recursos_arrancado_ok",
//							NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
//							NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));

				logger.debug("GestorRecursos: Gestor de recursos esperando peticiones.");
				trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
						"Gestor de recursos esperando peticiones.",
						InfoTraza.NivelTraza.debug));

				// creo hebra de monitorizaci�n
				hebra = new HebraMonitorizacion(tiempoParaNuevaMonitorizacion,
						this.itfUsoPropiadeEsteAgente, "monitorizar");
				this.hebra.start();
			}
        }

	public void procesarPeticionTerminacion() {
		logger.debug("GestorNodo: Procesando la peticion de terminacion");
		trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
				"Procesando la peticion de terminacion",
				InfoTraza.NivelTraza.debug));
	// Terminamos de forma brutal lo que haya en el nodo

                if (this.hebra != null)
			this.hebra.finalizar();
                 System.exit(0);
//		trazas.setIdentAgenteAReportar(nombreAgente);
//                trazas.pedirConfirmacionTerminacionAlUsuario();
		
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
		logger.debug("GestorNodo: Comenzando terminacion de la organizacion...");
		trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
				"Comenzando la terminacion de la organizacion...",
				InfoTraza.NivelTraza.info));
		try {
           String estadoInternoAgente = this.ctrlGlobalAgenteReactivo.getItfControl().getEstadoControlAgenteReactivo();
           ItfGestionAgenteReactivo gestion = (ItfGestionAgenteReactivo) this.itfUsoRepositorio
				.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
						+ this.nombreAgente);
		//	gestion.termina();
//            this.itfUsoPropiadeEsteAgente.aceptaEvento(new
//			 EventoRecAgte ("termina",this.nombreAgente,this.nombreAgente));
//                        this.ctrlGlobalAgenteReactivo.getItfControl().getEstadoControlAgenteReactivo();
                    this.ctrlGlobalAgenteReactivo.getItfControl().procesarInfoControlAgteReactivo(new InfoContEvtMsgAgteReactivo("termina") );
//                  this.informaraMiAutomata("termina");
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
		trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
				"Terminando gestor de la organizacion y los recursos de la infraestructura.",
				InfoTraza.NivelTraza.debug));
		try {
			// se acaba con los recursos de la organizaci�n que necesiten ser
			// terminados
			ItfGestionRecTrazas.termina();

			// y a continuaci�n se termina el gestor de organizaci�n
			((ItfGestionAgenteReactivo) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
							+ this.nombreAgente))
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

	public void clasificaError() {
	}

	public void tratarTerminacionNoConfirmada() {
		logger.debug("Se ha recibido un evento de timeout debido a que un gestor no ha confirmado la terminaci�n. Se procede a continuar la terminaci�n del sistema");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Se ha recibido un evento de timeout debido a que un gestor no ha confirmado la terminaci�n. Se procede a continuar la terminaci�n del sistema",
				InfoTraza.NivelTraza.debug));
		try {
                    this.informaraMiAutomata("continuaTerminacion"); 
//			this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
//					"continuaTerminacion",
//					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
//					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}