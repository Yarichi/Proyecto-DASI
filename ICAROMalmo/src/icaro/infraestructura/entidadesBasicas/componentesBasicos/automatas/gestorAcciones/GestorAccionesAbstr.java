/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.gestorAcciones;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.acciones.Accion;
import icaro.infraestructura.entidadesBasicas.acciones.AccionProxy;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionAsincrona;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionSincrona;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.ExcepcionEjecucionAcciones;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author FGarijo
 */
public abstract class GestorAccionesAbstr implements ItfGestorAcciones{
//    private AgenteCognitivo agente;
    private String identPropietario;
//    private ItfUsoAutomataEFE envioInputs;
//    private ItfUsoRecursoTrazas trazas;
    private String identAccion;
//    private ComunicacionAgentes comunicator;
    private AccionAsincrona accionAsinc ;
    private String accionAsincSimpleName;
    private AccionSincrona accionSinc ;
    private String accionSincSimpleName;
//    private AccionesSemanticasAgenteReactivo accionesSemAgteReactivo ;
//    private String accionesSemAgteReactivoSimpleName;
    private Map<String, Object> accionesCreadas;
    private Logger log = Logger.getLogger(icaro.infraestructura.patronAgenteReactivo.control.GestorAccionesAgteReactivoImp.class);
    
//    public GestorAccionesImp(AgenteCognitivo agente,ItfProcesadorObjetivos envioHechos){
    public void GestorAcciones(String propietarioId){
        this.identPropietario = propietarioId;
//        this.envioInputs = itfautomata;
        accionAsincSimpleName = AccionAsincrona.class.getSimpleName();
        accionSincSimpleName = AccionSincrona.class.getSimpleName();
//        accionesSemAgteReactivoSimpleName = AccionesSemanticasAgenteReactivo.class.getSimpleName();
//        this.trazas= NombresPredefinidos.RECURSO_TRAZAS_OBJ;
//        comunicator = new ComunicacionAgentes (propietarioId);
        accionesCreadas = new HashMap <String, Object>();
    }
//    public void setItfAutomataEFconGestAcciones(ItfUsoAutomataEFE itfautomata){
//        envioInputs = itfautomata;
//    }

    @Override
    public Accion crearAccion(Class clase) throws Exception {
        Accion accion = (Accion)clase.newInstance(); 
//        accion.setEnvioHechos(envioHechos);
//        accion.setAgente(agente);
//        accion.setIdentAgente (agente.getIdentAgente());
//        accion.setComunicator(comunicator);
//        String identAccionLong = accion.getClass().getName();
//        identAccion = identAccionLong.substring(identAccionLong.lastIndexOf(".")+1);
        String identAccion = accion.getClass().getSimpleName();
        accion.setIdentAccion(identAccion);
        log.debug("Accion creada:"+clase.getName());
//        trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;		
        return new AccionProxy(accion);
    }
    @Override
    public AccionSincrona crearAccionSincrona(Class clase) throws Exception {
        String identAccion = clase.getSimpleName();
        if (accionesCreadas.containsKey(identAccion)) return (AccionSincrona) accionesCreadas.get(identAccion);
        AccionSincrona accion = (AccionSincrona)clase.newInstance(); 
//        accion.setItfAutomata(envioInputs);
//        accion.setAgente(agente);
//        accion.setIdentAgente (agente.getIdentAgente());
//        accion.setComunicator(comunicator);
//        String identAccionLong = accion.getClass().getName();
//        identAccion = identAccionLong.substring(identAccionLong.lastIndexOf(".")+1);
//        String identAccion = accion.getClass().getSimpleName();
        accionesCreadas.put(identAccion, accion);
        accion.setIdentAccion(identAccion);
        log.debug("Accion creada:"+clase.getName());
//        accion.setTrazas(NombresPredefinidos.RECURSO_TRAZAS_OBJ);	
        return accion;
    }
    @Override
    public AccionSincrona crearAccionAsincrona(Class clase) throws Exception {
        String identAccion = clase.getSimpleName();
        if (accionesCreadas.containsKey(identAccion)) return (AccionSincrona) accionesCreadas.get(identAccion);
        AccionSincrona accion = (AccionAsincrona)clase.newInstance(); 
//        accion.setEnvioHechos(envioHechos);
//        accion.setAgente(agente);
//        accion.setIdentAgente (agente.getIdentAgente());
//        accion.setComunicator(comunicator);
//        String identAccionLong = accion.getClass().getName();
//        identAccion = identAccionLong.substring(identAccionLong.lastIndexOf(".")+1);
//        String identAccion = accion.getClass().getSimpleName();
        accionesCreadas.put(identAccion, accion);
        accion.setIdentAccion(identAccion);
        log.debug("Accion creada:"+clase.getName());
//        accion.setTrazas(NombresPredefinidos.RECURSO_TRAZAS_OBJ);	
        return accion;
    }
    public void setPropietario (String identAgte){
      identPropietario = identAgte;
  }
    public String  getPropietario (){
      return identPropietario ;
  }
    public abstract void inicializarInfoAcciones(Class claseAccionAinicializar, Object... paramsInicializacion) throws Exception; 
    
    @Override
    public abstract void ejecutar(Object... paramsEjecucion) throws Exception; 
    @Override
    public abstract void ejecutarAccion(Class claseAccionEjecutar, Object... paramsEjecucion) throws Exception;   
    
    @Override
    public abstract void ejecutarMetodo(Class claseAccionEjecutar,String identMetodo, Object[] paramsEjecucion) throws Exception ; 
    @Override
    public synchronized void ejecutarMetodoThread(Class claseAccionEjecutar,String identMetodo, Object... paramsEjecucion) throws Exception { 
             throw new ExcepcionEjecucionAcciones( "GestorAccionesImp", "error al ejecutar un metodo"+ identMetodo + " de la clase: " + claseAccionEjecutar.getName(),
                                                          "El metodo no esta iplementado. Se ha producido una excepcion InvocationTargetException"); 
         } 
}
