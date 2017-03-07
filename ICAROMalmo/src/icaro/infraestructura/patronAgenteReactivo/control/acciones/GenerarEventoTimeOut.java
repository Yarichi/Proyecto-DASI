package icaro.infraestructura.patronAgenteReactivo.control.acciones;

import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;

public class GenerarEventoTimeOut extends Thread {
	
	/**
	 * @uml.property  name="milis"
	 */
	private long milis;
	/**
	 * @uml.property  name="nombre"
	 */
	private String nombre;
	/**
	 * @uml.property  name="origen"
	 */
	private String origen;
	/**
	 * @uml.property  name="destino"
	 */
	private String destino;
	
	/**
	 * @uml.property  name="repositorio"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private ItfUsoRepositorioInterfaces repositorio;
        ItfUsoAgenteReactivo itfUsoAgenteDestinatario = null;
	
	public GenerarEventoTimeOut(long milis,String nombre,String origen,String destino, ItfUsoRepositorioInterfaces repositorio) {
		super();
		this.milis = milis;
		this.nombre = nombre;
		this.origen = origen;
		this.destino = destino;
		this.repositorio = repositorio;
		this.setDaemon(true);
	}
        public GenerarEventoTimeOut(long milis,String nombre,String origen, ItfUsoAgenteReactivo itfUsoAgente) {
                super();
		this.milis = milis;
		this.nombre = nombre;
		this.origen = origen;
                this.setDaemon(true);
                itfUsoAgenteDestinatario = itfUsoAgente;
        }
	@Override
	public void run(){
		try {
			sleep(milis);
                        ItfUsoAgenteReactivo destinatario;
                        if (itfUsoAgenteDestinatario == null){
			   itfUsoAgenteDestinatario = (ItfUsoAgenteReactivo) repositorio.obtenerInterfaz(NombresPredefinidos.ITF_USO+destino);
                        }
                        itfUsoAgenteDestinatario.aceptaEvento(new EventoRecAgte(nombre,origen,destino));
		} catch (Exception e) {
			System.err.println("Error al enviar evento de timeout");
			e.printStackTrace();
		}
		
	}


	
}