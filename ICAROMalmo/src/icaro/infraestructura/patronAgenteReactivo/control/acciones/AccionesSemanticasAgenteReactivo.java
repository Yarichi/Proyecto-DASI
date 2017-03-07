package icaro.infraestructura.patronAgenteReactivo.control.acciones;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoInternoAgteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.ItfUsoAutomataEFE;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ItfControlAgteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.AgenteReactivoAbstracto;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfGestionAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.util.Hashtable;
import java.util.logging.Level;

import org.apache.log4j.Logger;


	
/**
 * Clase base de la que deben heredar todas las acciones semnticas de los agentes reactivos.
 *
 * @author F Garijo
 * Esta clase depende del Gestor de Acciones del Agte reactivo que es quien crea las A semanticas especificas
 * Necesita informacion contextual que debe definirse en la inicializacion. La informacion qe necesite la pide a su gestor
 * que a su vez la pedira a los elementos internos de los que dependa
 * @version 4.0
 */

public abstract class AccionesSemanticasAgenteReactivo {

  /**
 * Itf de uso del agente al que pertenecen estas acciones para permitir la realimentacin de inputs
 * @uml.property  name="itfUsoAgente"
 * @uml.associationEnd  
 */
//  public ItfUsoAgenteReactivo itfUsoPropiadeEsteAgente;
  /**
 * @uml.property  name="itfUsoGestorAReportar"
 * @uml.associationEnd  
 */
public ItfUsoAgenteReactivo itfUsoGestorAReportar;
protected  ItfControlAgteReactivo itfControlAgente ;
protected ItfGestionAgenteReactivo itfGestionAgte;

  /**
 * @uml.property  name="nombreAgente"
 */
protected String nombreAgente;

protected  AgenteReactivoAbstracto ctrlGlobalAgenteReactivo ;


  /**
 * @param nombreAgente
 * @uml.property  name="nombreAgente"
 */

/**
 * Pizarra para almacenar parametros enviados por la clase que implementa el interfaz de uso del agente reactivo
 * @uml.property  name="tablaParametros"
 * @uml.associationEnd  multiplicity="(0 -1)" ordering="true" elementType="java.lang.Object" qualifier="nombre:java.lang.String java.lang.Object"
 */
//  protected Hashtable<String,Object> tablaParametros;
  
  /**
 * @uml.property  name="logger"
 * @uml.associationEnd  
 */
protected Logger logger;
  /**
 * @uml.property  name="trazas"
 * @uml.associationEnd  multiplicity="(1 1)"
 */
public ItfUsoRecursoTrazas trazas ;
  
  /**
 * Interfaz de uso del Repositorio de Interfaces
 * @uml.property  name="itfUsoRepositorio"
 * @uml.associationEnd  multiplicity="(1 1)"
 */
  public ItfUsoRepositorioInterfaces itfUsoRepositorio;
  private String identEstaClase = this.getClass().getSimpleName();
  private boolean recursoTrazasDefinido = false;
  private String mensajeParaDesarrollador;
  private ItfUsoAgenteReactivo itfGestorAreportar;

protected ComunicacionAgentes comunicator;
protected ItfProductorPercepcion itfEnvioEventosInternos;
//protected ItfProductorPercepcion itfUsoPropiadeEsteAgente;

  /**
   * Es necesario un constructor sin parmetros
   */
  public AccionesSemanticasAgenteReactivo() {
	 
//    this.itfUsoRepositorio = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
//    this.trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
//    this.logger =  Logger.getLogger(this.getClass().getCanonicalName());
  }
  public void inicializarAcciones(String identAgte, ItfControlAgteReactivo itfControl,ItfProductorPercepcion itfProductPercept){
      
    this.logger =  Logger.getLogger(this.getClass().getCanonicalName());
    if(NombresPredefinidos.RECURSO_TRAZAS_OBJ!= null){
        this.trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        recursoTrazasDefinido = true;
    }else this.infoParaDesarrollador("El recuros de trazas no ha sido creado Ver mensajes del Logger");
    if(NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ!= null){
        this.itfUsoRepositorio = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
    }else this.infoParaDesarrollador("El repositorio de interfaces  no ha sido creado Ver mensajes del Logger");
    if(identAgte!= null){
        this.nombreAgente=identAgte;
    }else this.infoParaDesarrollador("El nombre del agente no puede ser null ");
   if(itfControl!= null){
        this.itfControlAgente=itfControl;
    }else this.infoParaDesarrollador("La interfaz de Control del agente es  null y debe ser definida");
   if(itfProductPercept!= null){
        this.itfEnvioEventosInternos=itfProductPercept;
    }else this.infoParaDesarrollador("La interfaz de la percepcion que implementa el  Control del agente es  null y debe ser definida");
   this.getComunicator();
  }
          
public void infoParaDesarrollador ( String mensaje){
    if (recursoTrazasDefinido) this.trazas.trazar(identEstaClase, mensaje, InfoTraza.NivelTraza.error);
    logger.error(mensaje);
}

public void setNombreAgente(String nombreAgente) {
	this.nombreAgente = nombreAgente;
}

  /**
   * Fija el interfaz de uso del agente para que pueda realimentarse la tabla con inputs
   * procidos por las acciones
   * @param itfUsoA
   */
//  public void setItfUsoAgenteReactivo(ItfUsoAgenteReactivo itfUsoA)
//  {
//    this.itfUsoPropiadeEsteAgente = itfUsoA;
//  }
  public ItfGestionAgenteReactivo getIftGestionAgente(){
  if(itfGestionAgte!= null){
        return this.itfGestionAgte;
    }else {
      this.infoParaDesarrollador("Se obtiene la interfaz de  Gestion del agente ");
      if(this.itfUsoRepositorio != null)
          try {
          itfGestionAgte =  (ItfGestionAgenteReactivo) itfUsoRepositorio.obtenerInterfazGestion(nombreAgente);
      } catch (Exception ex) {
//          java.util.logging.Logger.getLogger(AccionesSemanticasAgenteReactivo.class.getName()).log(Level.SEVERE, null, ex);
          this.infoParaDesarrollador("Se ha producido un error al obtener la interfaz de gestion del agente " + ex );
           return null;
      }
      return itfGestionAgte;
  }
  }
  public void  setCtrlGlobalAgenteReactivo (AgenteReactivoAbstracto agteReactivo){
      this.ctrlGlobalAgenteReactivo = agteReactivo;
//      this.itfUsoPropiadeEsteAgente = (ItfUsoAgenteReactivo) agteReactivo;
  }
  
  /**
 * @param itfUsoA
 * @uml.property  name="itfUsoGestorAReportar"
 */
public void setItfUsoGestorAReportar(ItfUsoAgenteReactivo itfUsoA)
  {
    this.itfUsoGestorAReportar = itfUsoA;
  }
public ItfUsoAgenteReactivo getItfUsoGestorAReportar() {
    if (itfGestorAreportar !=null)return itfGestorAreportar;
        this.itfUsoGestorAReportar = itfControlAgente.getGestorAReportar();
    if (this.itfUsoGestorAReportar ==null )infoParaDesarrollador("El gestor a reportar es null. Es necesario definirlo");
    return itfGestorAreportar;
  }
  
//  public void setItfUsoRepositorioInterfaces(ItfUsoRepositorioInterfaces itfUsoR)
//  {
//    this.itfUsoRepositorio = itfControlAgente.getGestoraReportar;
//    if ()
//  }
  
  /**
 * @param logger
 * @uml.property  name="logger"
 */
public void setLogger(Logger logger){
	  this.logger = logger;
  }
// public void informaraMiAutomata(String input, Object[] infoComplementaria){
//  // Este método crea un evento con la información de entrada y se le envía a si mismo por medio de
//  // la  interfaz de uso
//        EventoRecAgte eventoaEnviar;
//      // En primer lugar se crea el evento con  la informacion de entrada
//       if (infoComplementaria == null) {
//             eventoaEnviar = new EventoRecAgte(input, nombreAgente, nombreAgente);
//                            }
//       else {
//               eventoaEnviar = new EventoRecAgte(input,infoComplementaria, nombreAgente, nombreAgente);
//                   }
//        try {
////            if (itfUsoPropiadeEsteAgente==null){
//                itfAutomataEFE.procesaInput(input,infoComplementaria);
////            }else  itfUsoPropiadeEsteAgente.aceptaEvento(eventoaEnviar);	
//		}
//		catch (Exception ex) {
//			logger.error("Ha habido un problema al enviar un  input a este agente " +nombreAgente);
//			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
//                    "Ha habido un problema al procesar el  "+ input + "por el automata que implementa el control", InfoTraza.NivelTraza.error));
//			}
//
//   }

//public void informaraMiAutomata(String input, Object[] infoComplementaria){
//    
//    if ( itfEnvioEventosInternos !=null) 
//    itfEnvioEventosInternos.produceParaConsumirInmediatamente(new EventoInternoAgteReactivo(nombreAgente, input, infoComplementaria));
//    else  trazas.trazar(nombreAgente, " El interfaz para el envio de eventos internos no esta definida "
//                 + " El input:  " + input + " No sera procesado ", InfoTraza.NivelTraza.error);
//}
public void informaraMiAutomata(String input, Object...infoComplementaria){
    
  Object paramsAccion[] = {};
		
		if (infoComplementaria == null ||(infoComplementaria.length == 1 && infoComplementaria[0]==null) )
                    paramsAccion = new Object[0];
                else {
                    paramsAccion = new Object[infoComplementaria.length];
			for (int i=0; (i<infoComplementaria.length ); i++) {
				paramsAccion[i] = infoComplementaria[i];
                        }
                }
    if ( itfEnvioEventosInternos !=null) 
    itfEnvioEventosInternos.produceParaConsumirInmediatamente(new EventoInternoAgteReactivo(nombreAgente, input, paramsAccion));
    else  trazas.trazar(nombreAgente, " El interfaz para el envio de eventos internos no esta definida "
                 + " El input:  " + input + " No sera procesado ", InfoTraza.NivelTraza.error);
}
public void informaraMiAutomata(String input){
    
  Object paramsAccion[] = new Object[0];
    if ( itfEnvioEventosInternos !=null) 
    itfEnvioEventosInternos.produceParaConsumirInmediatamente(new EventoInternoAgteReactivo(nombreAgente, input, paramsAccion));
    else  trazas.trazar(nombreAgente, " El interfaz para el envio de eventos internos no esta definida "
                 + " El input:  " + input + " No sera procesado ", InfoTraza.NivelTraza.error);
}

//
// public void setItfAutomata (ItfUsoAutomataEFE automataItf){
//     itfAutomataEFE = automataItf;
//
// }
 public void procesarInput (Object input,Object[] infoComplementaria ){
     if ( itfEnvioEventosInternos == null){
         trazas.trazar(nombreAgente, " El interfaz para el envio de eventos internos no esta definida "
                 + " El input:  " + input + " No sera procesado ", InfoTraza.NivelTraza.error);
     }else 
     {
          itfEnvioEventosInternos.produceParaConsumirInmediatamente(new EventoInternoAgteReactivo(nombreAgente, input, infoComplementaria));
         
     }
 }

  /**
   * Fija un parmetro para que sea recogido por alguna accin semntica
   * @param nombre Nombre del parmetro
   * @param valor Valor del parmetro
   */
//  protected synchronized void fijarParametro(String nombre, Object valor)
//  {
//    if (!"".equals(nombre))
//      this.tablaParametros.put(nombre,valor);
//  }

  /**
   * Obtiene el parmetro
   * @param nombre Nombre del parmetro
   * @return Object Valor del parmetro o null si el parmetro no existe en la tabla.
   */
//  protected Object obtenerParametro(String nombre)
//  {
//    if (!"".equals(nombre) && this.tablaParametros.containsKey(nombre))
//      return this.tablaParametros.get(nombre);
//    //else
//    System.err.println("Se intenta acceder al parametro: "+nombre+", pero no estaba definido en la tabla de parametros");
//    return null;
//  }
 
  public abstract void clasificaError();
  /**
 * Este mtodo debe ser implementado por todos los agentes para  realizar el necesario tratamiento de los errores.
 * @uml.property  name="nombreAgente"
 */

public String getNombreAgente() {
	return nombreAgente;
}

 public void generarTimeOut(long milis, String identInput, String origen, String destino) {
		GenerarEventoTimeOut timeout = new GenerarEventoTimeOut(milis, identInput, origen,destino, this.itfUsoRepositorio);
		logger.debug("Generando evento de timeout de "+ milis + " milisegundos");
		timeout.start();
 }
 public ComunicacionAgentes getComunicator (){
     if (comunicator==null) comunicator = new ComunicacionAgentes(nombreAgente);
     return comunicator;
 }
 
 public  void ejecutar(Object... params){
     // no implementada
 }
}