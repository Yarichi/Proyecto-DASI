package icaro.infraestructura.patronRecursoSimple.imp;

import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFsinAcciones.ItfUsoAutomataEFsinAcciones;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp.ConfiguracionTrazas;
import icaro.infraestructura.patronRecursoSimple.ItfGestionRecursoSimple;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.Logger;



/**
 * 
 *@author     
 *@created    30 de noviembre de 2007
 */

public class ImplRecursoSimple extends UnicastRemoteObject
		implements ItfUsoRecursoSimple, ItfGestionRecursoSimple,Serializable {

	private static final long serialVersionUID = 1L;
//	public final static String ficheroAutomataCicloVida = "/icaro/infraestructura/patronRecursoSimple/TablaEstadosCicloVidaRecursos.xml";
	
	/**
	 * @uml.property  name="itfUsoRepositorioInterfaces"
	 * @uml.associationEnd  
	 */
	protected icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces itfUsoRepositorioInterfaces;
	//protected int estado;
	/**
	 * @uml.property  name="estadoAutomata"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	protected ItfUsoAutomataEFsinAcciones itfAutomata;
	/**
	 * @uml.property  name="logger"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	protected transient Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
	
	/**
	 * @uml.property  name="id"
	 */
	protected String id;
        public String identAgenteAReportar;
	public ItfUsoRecursoTrazas trazas;
        public ItfUsoRepositorioInterfaces repoIntfaces;//ClaseGeneradoraRepositorioInterfaces.instance();

	/**
	 * @return
	 * @uml.property  name="id"
	 */
	public String getId() {
		return id;
	}


	/**
	 * @param id
	 * @uml.property  name="id"
	 */
	public void setId(String id) {
		this.id = id;
	}


	
	public ImplRecursoSimple(String idRecurso) throws RemoteException  {
//		this.estadoAutomata = new AutomataCicloVidaRecurso(fichero_automata_ciclo_vida, 2);

 //       ItfUsoAutomataCicloVidaRecurso AutomataItf = (ItfUsoAutomataCicloVidaRecurso) ClaseGeneradoraAutomataEFsinAcciones.instance(NombresPredefinidos.FICHERO_AUTOMATA_CICLO_VIDA_COMPONENTE);
 //       this.itfAutomata=AutomataItf;
        this.id = idRecurso;
        this.repoIntfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
        this.itfUsoRepositorioInterfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
        this.trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
     
	}

    @Override
	public void arranca() {
		//this.estado = InterfazGestion.ESTADO_ACTIVO;
		this.itfAutomata.transita("arrancar");
		this.itfAutomata.transita("ok");
	}
/*	
	public void arrancaConEvento() {
		//this.estado = InterfazGestion.ESTADO_ACTIVO;
		this.estadoAutomata.transita("arrancar");
		this.estadoAutomata.transita("ok");
	}
	
	public void arrancaConInput(String nombreInput) {
		//this.estado = InterfazGestion.ESTADO_ACTIVO;
		this.estadoAutomata.transita(nombreInput);
		this.estadoAutomata.transita("ok");
	}
*/
    @Override
	public void para() {
		throw new UnsupportedOperationException();
	}

    @Override
	public void termina()  {
		//this.estado = InterfazGestion.ESTADO_TERMINADO;
            if(itfAutomata!=null){
		this.itfAutomata.transita("terminar");
		this.itfAutomata.transita("ok");
            }
	}

    @Override
	public void continua() {
		throw new UnsupportedOperationException();
	}

	/*public int monitorizacion() throws RemoteException {
		return this.estado;
	}*/
	//Este mtodo no se ejecuta, se mantiene por el patrn anterior
    @Override
	public int obtenerEstado() {
		String estadoAutomata = this.itfAutomata.estadoActual();
		if (estadoAutomata.equals(NombresPredefinidos.ESTADO_ACTIVO)
				|| estadoAutomata.equals(NombresPredefinidos.ESTADO_ARRANCADO))
			return InterfazGestion.ESTADO_ACTIVO;
		if (estadoAutomata.equals(NombresPredefinidos.ESTADO_TERMINADO))
			return InterfazGestion.ESTADO_TERMINADO;
		if (estadoAutomata.equals(NombresPredefinidos.ESTADO_TERMINANDO))
			return InterfazGestion.ESTADO_TERMINANDO;
	
		if (estadoAutomata.equals(NombresPredefinidos.ESTADO_CREADO))
			return InterfazGestion.ESTADO_CREADO;
		if (estadoAutomata.equals(NombresPredefinidos.ESTADO_ERROR))
			return InterfazGestion.ESTADO_ERRONEO_IRRECUPERABLE;
		if (estadoAutomata.equals(NombresPredefinidos.ESTADO_FALLO_TEMPORAL))
			return InterfazGestion.ESTADO_ERRONEO_RECUPERABLE;
		if (estadoAutomata.equals(NombresPredefinidos.ESTADO_PARADO))
			return InterfazGestion.ESTADO_PARADO;
		return InterfazGestion.ESTADO_OTRO;
	}
	
	/*public String obtenerEstado() {
		return this.estadoAutomata.monitorizar();
	}*/
	
	/**
	 * @param itfUsoRepositorioInterfaces
	 * @uml.property  name="itfUsoRepositorioInterfaces"
	 */
	public void setItfUsoRepositorioInterfaces(ItfUsoRepositorioInterfaces itfUsoRepositorioInterfaces) {
		this.itfUsoRepositorioInterfaces = itfUsoRepositorioInterfaces;
	}
    public void setItfUsoRecursoTrazas(ItfUsoRecursoTrazas itfUsoRecursoTrazas) {
		this.trazas = itfUsoRecursoTrazas;
	}
     public void setItfAutomataCicloDeVida(ItfUsoAutomataEFsinAcciones AutomataItf) {
		this.itfAutomata=AutomataItf;
	}
    @Override
     public void setIdentAgenteAReportar(String identAgenteAReportar) {
		this.identAgenteAReportar =identAgenteAReportar;
	}
    public void setParametrosLogger(String archivoLog, String nivelLog){
        ConfiguracionTrazas configuracionTrazas = new ConfiguracionTrazas(logger, archivoLog, nivelLog);
	}

}
