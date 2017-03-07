/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.infraestructura.entidadesBasicas.procesadorCognitivo;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ItfProcesadorObjetivos;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.gestorTareas.GestorTareas;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.motorDeReglas.ItfConfigMotorDeReglas;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.motorDeReglas.ItfMotorDeReglas;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FGarijo
 */

public abstract class TareaSincrona {

    protected ItfProcesadorObjetivos itfProcObjetivos;
    protected AgenteCognitivo agente;
    protected String  identTarea;
    protected String  identAgente;
    protected Object[] params;
    protected boolean terminada;
    protected ItfUsoRecursoTrazas trazas;
    protected ItfUsoRepositorioInterfaces repoInterfaces;
    protected ItfUsoConfiguracion itfConfig;
    protected GestorTareas gestorTareas;
    protected ComunicacionAgentes comunicator;
		
    public TareaSincrona(){
		
                this.trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
                this.repoInterfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
	}
    
    public TareaSincrona(ItfProcesadorObjetivos envioHechos, AgenteCognitivo agente) {
    
    	this.itfProcObjetivos = envioHechos;
    	this.agente = agente;
        this.trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        this.identAgente = agente.getIdentAgente();
        this.repoInterfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
    }

    public abstract void ejecutar(Object... params);
    
    public void generarInformeConCausaTerminacion (String idTarea,Objetivo contxtGoal,String idAgenteOrdenante,Object contenido, CausaTerminacionTarea causaTerminacion ){

        String identGoal = null;
        if (contxtGoal!= null) identGoal = contxtGoal.getgoalId();
        InformeDeTarea resultadoTarea = new InformeDeTarea (idTarea,identGoal,idAgenteOrdenante,contenido, causaTerminacion );
        itfProcObjetivos.insertarHecho(resultadoTarea);
    //    envioHechos.insertarHecho(contenido);
    }
    
    public void generarInforme (InformeDeTarea informe){
        itfProcObjetivos.insertarHecho(informe);
 //       envioHechos.insertarHecho(informe.getContenidoInforme());
    }

    public void generarInformeOK (String idTarea,Objetivo contxtGoal,String idAgenteOrdenante, Object contenido){
        String goalId = null ;
        if (contxtGoal!=null){
            goalId= contxtGoal.getgoalId();
        }
        InformeDeTarea resultadoTarea = new InformeDeTarea (idTarea,goalId,idAgenteOrdenante, contenido );
        itfProcObjetivos.insertarHecho(resultadoTarea);
    //  envioHechos.insertarHecho(contenido);
    }
    
    public void generarInformeTemporizado (long milis,String idTarea,Objetivo contxtGoal,String idAgenteOrdenante, Object contenido){
        String goalId = null ;
        if (contxtGoal!=null){
            goalId= contxtGoal.getgoalId();
        }
        if(contenido==null)contenido = NombresPredefinidos.PREFIJO_MSG_TIMEOUT;
        InformeDeTarea informeTarea = new InformeDeTarea (idTarea,goalId,idAgenteOrdenante, contenido );
        Temporizador informeTemporizado = new Temporizador ( milis, itfProcObjetivos,informeTarea );
        informeTemporizado.start();
    }
    public void generarInformeTemporizadoFromConfigProperty (String identproperty,Objetivo contxtGoal,String idAgenteOrdenante, Object contenido){
        try {
            // el nombre de la propiedad es el nombre de la tarea y debe estar definido en la configuracion. El valor debe ser un entero
           //  identproperty = NombresPredefinidos.PREFIJO_PROPIEDAD_TAREA_TIMEOUT+identproperty;
            int valorTimeout = this.getItfUsoConfiguracion().getValorNumericoPropiedadGlobal(NombresPredefinidos.PREFIJO_PROPIEDAD_TAREA_TIMEOUT+identproperty);
              if (valorTimeout <= 0){
                  trazas.trazar(idAgenteOrdenante, "Se ejecuta la tarea " + this.getIdentTarea()+
                                         " No se puede obtener el nombre de la propiedad en la configuracion. Se intenta la propiedad por defecto"
                         + " Verifique el nombre de la propiedad en la descripcion de la organizacion :  "+ NombresPredefinidos.PREFIJO_PROPIEDAD_TAREA_TIMEOUT+identproperty, NivelTraza.error);
                  valorTimeout = this.getItfUsoConfiguracion().getValorNumericoPropiedadGlobal(NombresPredefinidos.PROPERTY_TIME_TIMEOUT_POR_DEFECTO);
                if (valorTimeout <= 0) {
                    trazas.trazar(idAgenteOrdenante, "Se ejecuta la tarea " + this.getIdentTarea()+
                                         " No se puede obtener el nombre de la propiedad en la configuracion.El valor de la propiedad por defecto NO esta definido"
                         + " Defina el nombre de la propiedad :"+ NombresPredefinidos.PROPERTY_TIME_TIMEOUT_POR_DEFECTO +
                            "en la descripcion de la organizacion :  ", NivelTraza.error);
                }
              }else
                 if(contenido==null)contenido = NombresPredefinidos.PREFIJO_MSG_TIMEOUT;
                   this.generarInformeTemporizado(valorTimeout, identproperty, contxtGoal, idAgenteOrdenante, contenido);      
                   trazas.trazar(idAgenteOrdenante, "Se ejecuta la tarea " + this.getIdentTarea()+
                    "propiedad en la configuracion: " + identproperty + " Se activa un informe temporizado de :  "+ valorTimeout + "  y contenido : " + contenido, NivelTraza.debug);                    
              
        } catch (ExcepcionEnComponente ex) {
            trazas.trazar(idAgenteOrdenante, "Se ejecuta la tarea " + this.getIdentTarea()+
                                         " No se puede obtener el nombre de la propiedad en la configuracion."
                         + " Verifique el nombre de la propiedad en la descripcion de la organizacion :  "+ identproperty, NivelTraza.error);
            Logger.getLogger(TareaSincrona.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(TareaSincrona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    public ItfProcesadorObjetivos getEnvioHechos() {
        return itfProcObjetivos;
    }
    public ItfUsoConfiguracion getItfUsoConfiguracion() {
         if (itfConfig == null ){
            try {
                itfConfig = (ItfUsoConfiguracion)repoInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + NombresPredefinidos.CONFIGURACION);
            } catch (Exception ex) {
                Logger.getLogger(TareaSincrona.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
        return itfConfig;
    }

    public void setEnvioHechos(ItfProcesadorObjetivos envioHechos) {
        this.itfProcObjetivos = envioHechos;
    }
     public void setTrazas(ItfUsoRecursoTrazas trazasItf) {
        this.trazas = trazasItf;
    }
    public ItfConfigMotorDeReglas getItfConfigMotorDeReglas() {
        return itfProcObjetivos.getItfConfigMotorDeReglas();
    }
    public ItfMotorDeReglas getItfMotorDeReglas() {
        return itfProcObjetivos.getItfMotorDeReglas();
    }
    public AgenteCognitivo getAgente() {
        return agente;
    }

    public void setAgente(AgenteCognitivo agente) {
        this.agente = agente;
    }
    
    public void setIdentAgente(String agentId) {
        this.identAgente = agentId;
    }
    
    public String getIdentAgente(){
       return identAgente ;
    }
    
    public void setIdentTarea(String idTarea){
        this.identTarea = idTarea;
    }
    
    public String getIdentTarea(){
       return identTarea ;
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
      
}