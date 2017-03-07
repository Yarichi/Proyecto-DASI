/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.imp;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ItfControlAgteReactivo;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.FactoriaControlAgteReactivo;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfConsumidorPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ProcesadorInfoReactivoAbstracto;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.ExcepcionNoSePudoCrearAutomataEFE;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.imp.EjecutorDeAccionesImp;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.imp.AutomataEFEImp;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.AgenteReactivoAbstracto;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp.ConfiguracionTrazas;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfConsumidorPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;


import org.apache.log4j.Logger;
/**
 *
 * @author Francisco J Garijo
 */


public class FactoriaControlAgteReactivoImp2 extends FactoriaControlAgteReactivo{
private static final long serialVersionUID = 1L;
    /**
	 * Control del agente
	 * @uml.property  name="control"
	 * @uml.associationEnd
	 */
    protected ProcesadorInfoReactivoAbstracto procesadorEventos;
    /**
	 * @uml.property  name="itfGesControl"
	 * @uml.associationEnd
	 */
    protected InterfazGestion itfGesControl; //Control
    /**
	 * Percepcion del agente
	 * @uml.property  name="itfConsumidorPercepcion"
	 * @uml.associationEnd
	 */
    protected ItfConsumidorPercepcion itfConsumidorPercepcion;
    /**
	 * @uml.property  name="itfProductorPercepcion"
	 * @uml.associationEnd
	 */
    protected ItfProductorPercepcion itfProductorPercepcion;
    /**
	 * Nombre del agente a efectos de traza
	 * @uml.property  name="nombre"
	 */
    protected String nombreAgente;
    protected AgenteReactivoAbstracto agente;
    /**
	 * Estado del agente reactivo
	 * @uml.property  name="estado"
	 */
    protected int estado = InterfazGestion.ESTADO_OTRO;
    /**
	 * Acciones semnticas del agente reactivo
	 * @uml.property  name="accionesSemanticas"
	 * @uml.associationEnd
	 */
    protected EjecutorDeAccionesImp accionesSemanticas;

    protected AutomataEFEImp automataControl=null;
    /**
	 * @uml.property  name="dEBUG"
	 */
    private boolean DEBUG = false;
    /**
	 * Conocimiento del agente reactivo
	 * @uml.property  name="itfUsoGestorAReportar"
	 * @uml.associationEnd
	 */
    protected ItfControlAgteReactivo itfGestionControlAgteCreado;
    /**
	 * @uml.property  name="logger"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
    /**
	 * @uml.property  name="trazas"
	 * @uml.associationEnd
	 */
    protected ItfUsoRecursoTrazas trazas;

//    private ItfProductorPercepcion percepcionProductor;

    @Override
    public ProcesadorInfoReactivoImp crearControlAgteReactivo(AccionesSemanticasAgenteReactivo accionesSemanticasEspecificas, String nombreFicheroTablaEstados, AgenteReactivoAbstracto agente)throws ExcepcionEnComponente {

        new ConfiguracionTrazas(logger);

        trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;

         accionesSemanticasEspecificas.setLogger(logger);
       
        // crea las Acciones semnticas referidas al contenedor de acciones

        // crea el automata de control
   //     AutomataEFEAbstracto ac = null;

            try {
                nombreAgente = agente.getIdentAgente();
                this.accionesSemanticas = new EjecutorDeAccionesImp(accionesSemanticasEspecificas);
                automataControl = new AutomataEFEImp(nombreFicheroTablaEstados, accionesSemanticas, AutomataEFEImp.NIVEL_TRAZA_DESACTIVADO,nombreAgente );
                return new ProcesadorInfoReactivoImp(automataControl, accionesSemanticasEspecificas,  agente);
                }
                catch (Exception ExcepcionNoSePudoCrearAutomataEFE)
                {
                logger.error("Error al crear el automata del agente " + nombreAgente + " utilizando el fichero " + nombreFicheroTablaEstados);
                trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
                        "Error al crear el automata del agente " + nombreAgente + " utilizando el fichero " + nombreFicheroTablaEstados,
                        InfoTraza.NivelTraza.error));
                throw new ExcepcionEnComponente ("AutomataEFEImp", "no se pudo crear el Automata EFE","Automta EFE","automataControl = new AutomataEFEImp(" );
                }
        // Crea el procesador de eventos
     //    this.procesadorEventos = new ProcesadorEventosImp(percConsumidor, automataControl,percProductor, nombreDelAgente);

           // return  itfGestionControlAgteCreado = (ItfGestionControlAgteReactivo) new ProcesadorEventosImp(percConsumidor,automataControl,percProductor, nombreDelAgente);


    //elijo la implementacin adecuada (aunque podra haber ms)
    }
    @Override
    public ProcesadorEventosImp crearControlAgteReactivo(AccionesSemanticasAgenteReactivo accionesSemanticasEspecificas, String nombreFicheroTablaEstados, String nombreDelAgente,ItfConsumidorPercepcion percConsumidor,
			ItfProductorPercepcion percProductor)throws ExcepcionEnComponente {
        return null;
    }

}
