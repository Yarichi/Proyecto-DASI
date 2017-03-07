package icaro.infraestructura.entidadesBasicas.procesadorCognitivo;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ItfProcesadorObjetivos;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.gestorTareas.GestorTareas;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.motorDeReglas.ItfConfigMotorDeReglas;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.motorDeReglas.ItfMotorDeReglas;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class Tarea extends Thread {

    private ItfProcesadorObjetivos itfProcObjetivos;
    private AgenteCognitivo agente;
    private String  identTarea;
    private String  identAgente;
    private Object[] params;
    private boolean terminada;
    public ItfUsoRecursoTrazas trazas;
    private ItfUsoRepositorioInterfaces repoInterfaces;
    private ItfUsoConfiguracion itfConfig;
    private GestorTareas gestorTareas;
    private ComunicacionAgentes comunicator;
		
	public Tarea(){
		super("Tarea");
		this.setDaemon(true);
                this.trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
                this.repoInterfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
	}
    
    public Tarea(ItfProcesadorObjetivos envioHechos, AgenteCognitivo agente, GestorTareas gestorTareas) {
    	super(agente.getIdentAgente()+"Tarea");
    	this.itfProcObjetivos = envioHechos;
    	this.agente = agente;
    	this.gestorTareas = gestorTareas;
        this.trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        this.identAgente = agente.getIdentAgente();
    	this.setDaemon(true);
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
        InformeDeTarea informeTarea = new InformeDeTarea (idTarea,goalId,idAgenteOrdenante, contenido );
        Temporizador informeTemporizado = new Temporizador ( milis, itfProcObjetivos,informeTarea );
        informeTemporizado.start();
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
    public ItfProcesadorObjetivos getEnvioHechos() {
        return itfProcObjetivos;
    }
    public ItfConfigMotorDeReglas getItfConfigMotorDeReglas() {
        return itfProcObjetivos.getItfConfigMotorDeReglas();
    }
    public ItfMotorDeReglas getItfMotorDeReglas() {
        return itfProcObjetivos.getItfMotorDeReglas();
    }
    public void setEnvioHechos(ItfProcesadorObjetivos envioHechos) {
        this.itfProcObjetivos = envioHechos;
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
	
    public boolean terminada() {
    	return terminada;
    }
    
    @Override
    public void run() {
        terminada = false;
		ejecutar(params);
    }

/*
	public void terminar(CausaTerminacionTarea causa) {
		switch (causa) {
		case EXITO:
			break;
		case ERROR:
			break;
		case TIMEOUT:
			break;
		case EXPECTATIVA:
			break;
		case TERMINACION_AGENTE:
			break;
		case OTRO:
			break;
		}
		gestorTareas.eliminarTareaActiva(this);
		terminada = true;
	}
*/
    
	 public void setGestorTareas(GestorTareas gestorTareas) {
		 this.gestorTareas = gestorTareas;
	 }

 
	 public GestorTareas getGestorTareas() {
		 return gestorTareas;
	 }

    public void setComunicator(ComunicacionAgentes comunicator) {
        this.comunicator = comunicator;
    }
    public ComunicacionAgentes getComunicator() {
        return this.comunicator ;
    }

}