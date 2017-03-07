/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.gestorAcciones.imp;

import icaro.infraestructura.patronAgenteReactivo.control.GestorAccionesAgteReactivoImp;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.acciones.Accion;
import icaro.infraestructura.entidadesBasicas.acciones.AccionProxy;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionAsincrona;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionSincrona;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.gestorAcciones.ItfGestorAcciones;
import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.ItfUsoAutomataEFE;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.ExcepcionEjecucionAcciones;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author FGarijo
 */
public class GestorAccionesGenImp implements ItfGestorAcciones{
//    private AgenteCognitivo agente;
    private String identPropietario;
//    private ItfUsoAutomataEFE envioInputs;
    private ItfUsoRecursoTrazas trazas;
    private String identAccion;
//    private ComunicacionAgentes comunicator;
    private AccionAsincrona accionAsinc ;
    private String accionAsincSimpleName;
    private AccionSincrona accionSinc ;
    private String accionSincSimpleName;
//    private AccionesSemanticasAgenteReactivo accionesSemAgteReactivo ;
    private String accionesSemAgteReactivoSimpleName;
    private Map<String, Object> accionesCreadas;
    private Logger log = Logger.getLogger(GestorAccionesAgteReactivoImp.class);
    
//    public GestorAccionesImp(AgenteCognitivo agente,ItfProcesadorObjetivos envioHechos){
    public void GestorAccionesImp(String propietarioId){
        this.identPropietario = propietarioId;
//        this.envioInputs = itfautomata;
        accionAsincSimpleName = AccionAsincrona.class.getSimpleName();
        accionSincSimpleName = AccionSincrona.class.getSimpleName();
        accionesSemAgteReactivoSimpleName = AccionesSemanticasAgenteReactivo.class.getSimpleName();
        this.trazas= NombresPredefinidos.RECURSO_TRAZAS_OBJ;
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
        trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;		
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
//    public AccionesSemanticasAgenteReactivo getInstanceASagteReactivo(Class accionClass){
//        
//        if (accionesSemAgteReactivo ==null)
////        String identAccion = accionClass.getSimpleName();
////        if (accionesCreadas.containsKey(identAccion)) return (AccionesSemanticasAgenteReactivo) accionesCreadas.get(identAccion);
////        AccionesSemanticasAgenteReactivo accion; 
//        try {
//            accionesSemAgteReactivo = (AccionesSemanticasAgenteReactivo)accionClass.newInstance();
//            accionesSemAgteReactivo.itfAutomataControl = this.envioInputs;
//            accionesCreadas.put(identAccion, accionesSemAgteReactivo);
//            accionesSemAgteReactivo.trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;	
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(GestorAccionesAgteReactivoImp.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(GestorAccionesAgteReactivoImp.class.getName()).log(Level.SEVERE, null, ex);
//        }
////        accion.setEnvioHechos(envioHechos);
////        accion.setAgente(agente);
////        accion.setIdentAgente (agente.getIdentAgente());
////        accion.setComunicator(comunicator);
////        String identAccionLong = accion.getClass().getName();
////        identAccion = identAccionLong.substring(identAccionLong.lastIndexOf(".")+1);
////        String identAccion = accion.getClass().getSimpleName();
//        
////        accion.setIdentAccion(identAccion);
////        log.debug("Accion creada:"+clase.getName());
//        
//        return accionesSemAgteReactivo;
//    }
            
//    public void inicializarAccion(AccionSincrona accion) throws Exception {
//    //    AccionSincrona accion = (AccionSincrona)clase.newInstance(); 
//        accion.setItfAutomata(envioInputs);
////        accion.setAgente(agente);
////        accion.setIdentAgente (agente.getIdentAgente());
//        accion.setComunicator(comunicator);
//        accion.setIdentAccion(accion.getClass().getSimpleName());
// //       log.debug("Accion creada:"+clase.getName());
// //       accion.trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;	
// //       return accion;
//    }
//    
//    @Override
    public void ejecutar(Object... paramsEjecucion) throws Exception {
        
        if(paramsEjecucion==null){
              this.trazas.trazar ( this.getClass().getSimpleName(), "Error en  la ejecucion de la accion: " +
                    " No se han especificado parametros de ejecucion", InfoTraza.NivelTraza.error );
        }
        Class claseAccionEjecutar = (Class)paramsEjecucion[0];
       // Extraccion de parametros y verificacion de la clase
        if ( claseAccionEjecutar == null){
            this.trazas.trazar ( this.getClass().getSimpleName(), "Error en  la ejecucion de la accion: " +
                    " No se ha especificado la clase de las acciones a ejecutar", InfoTraza.NivelTraza.error );
        }
        String superclase = claseAccionEjecutar.getSuperclass().getSimpleName();
        int numparam = paramsEjecucion.length -1;
   
        for (int i = 0; i<numparam; i++){
           paramsEjecucion[i]= paramsEjecucion[i+1];
        }
            paramsEjecucion[numparam]=null;
        if (superclase.equals(accionSincSimpleName)) {
            accionSinc = crearAccionSincrona(claseAccionEjecutar);
            accionSinc.ejecutar(paramsEjecucion);
        }
        else if (superclase.equals(accionAsincSimpleName)){
             accionAsinc = (AccionAsincrona) claseAccionEjecutar.newInstance();
//             inicializarAccion(accionAsinc);
             accionAsinc.setParams(paramsEjecucion);
             accionAsinc.comenzar();
        }
        else {
            this.trazas.trazar ( this.getClass().getSimpleName(), "Error en  la ejecucion de la accion: "+ claseAccionEjecutar.getSimpleName() +
                    " debe extender a AccionSincrona o a AccionAsincrona ", InfoTraza.NivelTraza.error );
        }
        }
    @Override
    public void ejecutarAccion(Class claseAccionEjecutar, Object... paramsEjecucion) throws Exception {
//        Class claseAccionEjecutar = (Class)paramsEjecucion[0];
       // Extraccion de parametros y verificacion de la clase
         String identAccion = claseAccionEjecutar.getSimpleName();
        Object accionCreada = accionesCreadas.get(identAccion);
        String superclase = claseAccionEjecutar.getSuperclass().getSimpleName();
//        int numparam = paramsEjecucion.length ;
//        if (superclase.equals(accionesSemAgteReactivoSimpleName) ) {
//           accionesSemAgteReactivo = getInstanceASagteReactivo(claseAccionEjecutar);
//        }
        if (superclase.equals(accionSincSimpleName) ) {
//            accionSinc = (AccionAsincrona)accionesCreadas.get(identAccion);
            if(accionCreada!=null)accionSinc = (AccionAsincrona)accionCreada;
            else accionSinc = crearAccionSincrona(claseAccionEjecutar);
            accionSinc.ejecutar(paramsEjecucion);
        }
        else if (superclase.equals(accionAsincSimpleName)){
             accionAsinc = (AccionAsincrona) claseAccionEjecutar.newInstance();
//             inicializarAccion(accionAsinc);
             accionAsinc.setParams(paramsEjecucion);
             accionAsinc.comenzar();
        }
        else {
            this.trazas.trazar ( this.getClass().getSimpleName(), "Error en  la ejecucion de la accion: "+ claseAccionEjecutar.getSimpleName() +
                    " debe extender a AccionSincrona o a AccionAsincrona ", InfoTraza.NivelTraza.error );
        }
    }   
    
    @Override
    public synchronized void ejecutarMetodo(Class claseAccionEjecutar,String identMetodo, Object[] paramsEjecucion) throws Exception {
//        protected synchronized void ejecutarAccionBloqueante(String nombre, Object[] parametros) throws ExcepcionEjecucionAcciones {
	String superclase = claseAccionEjecutar.getSuperclass().getSimpleName();
//        String accionId = accionesSemAgteReactivo.getClass().getName();
//        if (superclase.equals(accionesSemAgteReactivoSimpleName) ){
//            if(accionesSemAgteReactivo==null) accionesSemAgteReactivo = getInstanceASagteReactivo(claseAccionEjecutar);
//            
//        }// habria que lanzar una excepcion pq debe ser instancia 
		Class params[] = {};
		Object paramsObj[] = {};
		
		if (paramsEjecucion == null ||(paramsEjecucion.length == 1 && paramsEjecucion[0]==null) ){
                    params = new Class[0];
                    paramsObj = new Object[0];
                }
                else {
			params = new Class[paramsEjecucion.length];
			paramsObj = new Object[paramsEjecucion.length];
			for (int i=0; (i<paramsEjecucion.length ); i++) {
                                params[i] = paramsEjecucion[i].getClass();
				paramsObj[i] = paramsEjecucion[i];
                        }
//		}
//		else {
//			params = new Class[0];
//			paramsObj = new Object[0];
		}

		try	{
//                    Class thisClass = accionesSemanticas.getClass();
//			Object iClass = accionesSemanticas;
//			Class thisClass = this.crearAccionSincrona(accion).getClass();
//			Object accionesSemanticas = this.crearAccionSincrona(accion);
			Method thisMethod = claseAccionEjecutar.getMethod(identMetodo, params);
			thisMethod.invoke(claseAccionEjecutar, paramsObj);
		}
		catch (IllegalAccessException iae) {
			System.out.println("ERROR en los privilegios de acceso (no es publico) para en metodo: " + identMetodo + " de la clase: " +claseAccionEjecutar.getClass().getName() );
			iae.printStackTrace();
            throw new ExcepcionEjecucionAcciones( "AccionesSemanticasImp", "error al ejecutar un metodo"+ identMetodo + " de la clase: " + claseAccionEjecutar.getClass().getName(),
                                                          "se ha producido un IlegalAccessIAE");
		}
		catch (NoSuchMethodError nsme) {
			System.out.println("ERROR (NO EXISTE) al invocar el metodo: " + identMetodo + " en la clase: " + claseAccionEjecutar.getClass().getName());
			nsme.printStackTrace();
            throw new ExcepcionEjecucionAcciones( "AccionesSemanticasImp", "error al ejecutar un metodo"+ identMetodo + " de la clase: " + claseAccionEjecutar.getClass().getName(),
                                                          "El metodo a invocar no existe. Se ha producido una excepcion NoSuchMethodError");
		}
		catch (NoSuchMethodException nsmee) {
			System.out.println("ERROR (NO EXISTE) al invocar el metodo: " + identMetodo + " en la clase: " + claseAccionEjecutar.getClass().getName());
			nsmee.printStackTrace();
			System.out.println("Invocando metodo con parametros de sus superclases correspondientes");
			throw new ExcepcionEjecucionAcciones( "AccionesSemanticasImp", "error al ejecutar un metodo"+ identMetodo + " de la clase: " + claseAccionEjecutar.getClass().getName(),
                                                          "El metodo a invocar no existe. Se ha producido una excepcion NoSuchMethodError");
            //ejecutarAccionBloqueantePolimorfica(nombre, parametros, 1);
		}
		catch (InvocationTargetException ite) {
			System.out.println("ERROR en la ejecucion del metodo: " + identMetodo + " en la clase: " + claseAccionEjecutar.getClass().getName());
			ite.printStackTrace();
			System.out.println("Excepcion producida en el metodo: ");
			ite.getTargetException().printStackTrace();
		throw new ExcepcionEjecucionAcciones( "AccionesSemanticasImp", "error al ejecutar un metodo"+ identMetodo + " de la clase: " + claseAccionEjecutar.getClass().getName(),
                                                          "El metodo a invocar no existe. Se ha producido una excepcion InvocationTargetException");
        }
	}
    @Override
         public synchronized void ejecutarMetodoThread(Class claseAccionEjecutar,String identMetodo, Object... paramsEjecucion) throws Exception { 
             throw new ExcepcionEjecucionAcciones( "GestorAccionesImp", "error al ejecutar un metodo"+ identMetodo + " de la clase: " + claseAccionEjecutar.getClass().getName(),
                                                          "El metodo no esta iplementado. Se ha producido una excepcion InvocationTargetException"); 
         }  
 }
