/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.infraestructura.patronAgenteReactivo.control.acciones;
import icaro.infraestructura.entidadesBasicas.informes.InformeTemporizacion;
import icaro.infraestructura.entidadesBasicas.informes.InformeError;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.*;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.accionesAutomataEF.GeneracionInputTimeout;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.gestorAcciones.ItfGestorAcciones;
import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoInternoAgteReactivo;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.ItfUsoAutomataEFE;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FGarijo
 */

public abstract class AccionSincrona {

    protected ItfProductorPercepcion itfEnvioEventosInternos;
    protected ItfUsoAgenteReactivo itfUsoAgente;
//    protected AgenteCognitivo agente;
    protected String  identAccion;
//    protected String  identAgente;
    protected Object[] params;
    protected boolean terminada;
    protected ItfUsoRecursoTrazas trazas;
    protected ItfUsoRepositorioInterfaces repoInterfaces;
    protected ItfUsoConfiguracion itfConfig;
    protected ItfGestorAcciones gestorAcciones;
    protected ComunicacionAgentes comunicator;
    protected String identAgte;
		
    public AccionSincrona(){
		
                this.trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
                this.repoInterfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
	}
    
//    public AccionSincrona(ItfAutomataEFconGestAcciones envioInputs, AgenteCognitivo agente) {
//    public AccionSincrona() {
////    	this.itfProcObjetivos = envioHechos;
////    	this.agente = agente;
//        this.trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
////        this.identAgente = agente.getIdentAgente();
//        this.repoInterfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
//    }
    public void inicializar (String identAgte, ItfProductorPercepcion iftPercepAgte){
        this.identAgte = identAgte;
        this.itfEnvioEventosInternos = iftPercepAgte;
    }

    public abstract void ejecutar(Object... params);
    
//    public void generarInformeConCausaTerminacion (String idAccion,Objetivo contxtGoal,String idAgenteOrdenante,Object contenido, CausaTerminacionAccion causaTerminacion ){
//
//        String identGoal = null;
//        if (contxtGoal!= null) identGoal = contxtGoal.getgoalId();
//        InformeDeAccion resultadoAccion = new InformeDeAccion (idAccion,identGoal,idAgenteOrdenante,contenido, causaTerminacion );
//        itfProcObjetivos.insertarHecho(resultadoAccion);
//    //    envioHechos.insertarHecho(contenido);
//    }
//    
    public void generarInformeError (String idAccion,InformeError informe){
    // definir un Informe de Error
    }
    
    public void generarInputAutomata (Object input){
        if(itfEnvioEventosInternos !=null)
        itfEnvioEventosInternos.produceParaConsumirInmediatamente(new EventoInternoAgteReactivo(identAccion, input, null));
 //       envioHechos.insertarHecho(informe.getContenidoInforme());
    }

//    public void generarInformeOK (String idTarea,Objetivo contxtGoal,String idAgenteOrdenante, Object contenido){
//        String goalId = null ;
//        if (contxtGoal!=null){
//            goalId= contxtGoal.getgoalId();
//        }
//        InformeDeTarea resultadoTarea = new InformeDeTarea (idTarea,goalId,idAgenteOrdenante, contenido );
//        itfProcObjetivos.insertarHecho(resultadoTarea);
//    //  envioHechos.insertarHecho(contenido);
//    }
    
    public void generarInputTemporizador (long milis,String idAccion, String msgTimeout){
//        String goalId = null ;
//        if (contxtGoal!=null){
//            goalId= contxtGoal.getgoalId();
//        }
        if(msgTimeout==null)msgTimeout = NombresPredefinidos.PREFIJO_MSG_TIMEOUT;
        InformeTemporizacion informeTemp = new InformeTemporizacion (idAccion, msgTimeout );
        GeneracionInputTimeout informeTemporizado = new GeneracionInputTimeout ( milis, itfUsoAgente,informeTemp );
        informeTemporizado.start();
    }
    public void generarInformeTemporizadoFromConfigProperty (String identproperty,Objetivo contxtGoal,String idAgenteOrdenante, String msgTimeout){
        try {
            // el nombre de la propiedad es el nombre de la tarea y debe estar definido en la configuracion. El valor debe ser un entero
           //  identproperty = NombresPredefinidos.PREFIJO_PROPIEDAD_TAREA_TIMEOUT+identproperty;
            int valorTimeout = this.getItfUsoConfiguracion().getValorNumericoPropiedadGlobal(NombresPredefinidos.PREFIJO_PROPIEDAD_TAREA_TIMEOUT+identproperty);
              if (valorTimeout <= 0){
                  trazas.trazar("Accion", "Se ejecuta la accion " + this.getIdentAccion()+
                                         " No se puede obtener el nombre de la propiedad en la configuracion. Se intenta la propiedad por defecto"
                         + " Verifique el nombre de la propiedad en la descripcion de la organizacion :  "+ NombresPredefinidos.PREFIJO_PROPIEDAD_TAREA_TIMEOUT+identproperty, NivelTraza.error);
                  valorTimeout = this.getItfUsoConfiguracion().getValorNumericoPropiedadGlobal(NombresPredefinidos.PROPERTY_TIME_TIMEOUT_POR_DEFECTO);
                if (valorTimeout <= 0) {
                    trazas.trazar("Accion", "Se ejecuta la tarea " + this.getIdentAccion()+
                                         " No se puede obtener el nombre de la propiedad en la configuracion.El valor de la propiedad por defecto NO esta definido"
                         + " Defina el nombre de la propiedad :"+ NombresPredefinidos.PROPERTY_TIME_TIMEOUT_POR_DEFECTO +
                            "en la descripcion de la organizacion :  ", NivelTraza.error);
                }
              }else
                 if(msgTimeout==null)msgTimeout = NombresPredefinidos.PREFIJO_MSG_TIMEOUT;
                   this.generarInputTemporizador(valorTimeout, identproperty, msgTimeout);      
                   trazas.trazar("Accion", "Se ejecuta la accion " + this.getIdentAccion()+
                                         " Se activa un informe temporizado :  "+ msgTimeout, NivelTraza.debug);                    
              
        } catch (ExcepcionEnComponente ex) {
            trazas.trazar("Accion", "Se ejecuta la accion " + this.getIdentAccion()+
                                         " No se puede obtener el nombre de la propiedad en la configuracion."
                         + " Verifique el nombre de la propiedad en la descripcion de la organizacion :  "+ identproperty, NivelTraza.error);
            Logger.getLogger(AccionSincrona.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(AccionSincrona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
//    public ItfProcesadorObjetivos getEnvioHechos() {
//        return itfProcObjetivos;
//    }
    public ItfUsoConfiguracion getItfUsoConfiguracion() {
         if (itfConfig == null ){
            try {
                itfConfig = (ItfUsoConfiguracion)repoInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + NombresPredefinidos.CONFIGURACION);
            } catch (Exception ex) {
                Logger.getLogger(AccionSincrona.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
        return itfConfig;
    }
public void procesarInput (Object input,Object[] infoComplementaria ){
     if ( itfEnvioEventosInternos == null){
         trazas.trazar(identAgte, " El interfaz para el envio de eventos internos no esta definida "
                 + " El input:  " + input + " No sera procesado ", InfoTraza.NivelTraza.error);
     }else 
     {
          itfEnvioEventosInternos.produceParaConsumirInmediatamente(new EventoInternoAgteReactivo(identAgte, input, infoComplementaria));        
     }
}
//    public void setEnvioHechos(ItfProcesadorObjetivos envioHechos) {
//        this.itfProcObjetivos = envioHechos;
//    }
//     public void setItfAutomata(ItfUsoAutomataEFE itfAutomata){
//         this.itfAutomata = itfAutomata;
//     }
//      public ItfUsoAutomataEFE getItfAutomata(){
//         return this.itfAutomata ;
//     }
//     public void setTrazas(ItfUsoRecursoTrazas trazasItf) {
//        this.trazas = trazasItf;
//    }
//    public ItfConfigMotorDeReglas getItfConfigMotorDeReglas() {
//        return itfProcObjetivos.getItfConfigMotorDeReglas();
//    }
//    public ItfMotorDeReglas getItfMotorDeReglas() {
//        return itfProcObjetivos.getItfMotorDeReglas();
//    }
//    public AgenteCognitivo getAgente() {
//        return agente;
//    }
//
//    public void setAgente(AgenteCognitivo agente) {
//        this.agente = agente;
//    }
//    
//    public void setIdentAgente(String agentId) {
//        this.identAgente = agentId;
//    }
//    
//    public String getIdentAgente(){
//       return identAgente ;
//    }
    
    public void setIdentAccion(String idAccion){
        this.identAccion = idAccion;
    }
    
    public String getIdentAccion(){
       return identAccion ;
    }
	
    public Object[] getParams() {
		return params;
	}

    public void setParams(Object... params) {
		this.params = params;
	}
	
     public void setComunicator(ComunicacionAgentes comunicator) {
        this.comunicator = comunicator;
    }
      public ComunicacionAgentes getComunicator() {
        return this.comunicator ;
    }
      public abstract void  getInfoObjectInput(Object obj); 
      
}