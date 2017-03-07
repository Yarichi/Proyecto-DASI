package icaro.aplicaciones.Rosace.utils;

import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

public class AccesoPropiedadesGlobalesRosace {


	//getValorNumericoPropiedadGlobal Se utiliza para obtener el valor numerico de una propiedad global cuya cadena es un numero
	//VocabularioRosace.NombreDeLaPropiedadGlobal es el parametro de este metodo
	//Por ejemplo se pasaria VocabularioRosace.intervaloMilisegundosEnvioCCMensajes para obtener el numero que indica a que ritmo se envia una secuencia
    public static int getValorNumericoPropiedadGlobal(String nombrePropiedad){
		int valorPropiedad;
		String strvalorPropiedad = "";
		
		ClaseGeneradoraRepositorioInterfaces itfUsoRepositorioInterfaces;
		ItfUsoConfiguracion itfconfig;
		
    	try{    	
    		itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
    		itfconfig = (ItfUsoConfiguracion)itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.NOMBRE_ITF_USO_CONFIGURACION);
    		strvalorPropiedad = itfconfig.getValorPropiedadGlobal(nombrePropiedad); 
    	}
    	catch (Exception e){
    		e.printStackTrace();
    	}
			
    	//convertir a int la cadena leida de la propiedad global 
    	String strAUX = strvalorPropiedad.replaceAll(" ", "");
    	valorPropiedad = Integer.parseInt(strAUX);
    	
		return valorPropiedad;								    	
    }
	

	//getValorCadenaPropiedadGlobal Se utiliza para obtener el valor de la cadena de una propiedad
	//VocabularioRosace.NombreDeLaPropiedadGlobal es el parametro de este metodo
	//Por ejemplo se pasaria VocabularioRosace.identificadorEquipo para obtener el identificador de equipo indicado en la propiedad global
    public static String getValorCadenaPropiedadGlobal(String nombrePropiedad){
		
		String valorPropiedad="";
		
		ClaseGeneradoraRepositorioInterfaces itfUsoRepositorioInterfaces;
		ItfUsoConfiguracion itfconfig;
		
    	try{    	
    		itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
    		itfconfig = (ItfUsoConfiguracion)itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.NOMBRE_ITF_USO_CONFIGURACION);
    		valorPropiedad = itfconfig.getValorPropiedadGlobal(nombrePropiedad); 
    	}
    	catch (Exception e){
    		e.printStackTrace();
    	}
			
    	String strAUX = valorPropiedad.replaceAll(" ", "");  //elimina los espacios en blanco que podria haber en los numeros    	
    	valorPropiedad = strAUX;
    	    	
		return valorPropiedad;				
	}
       

    public static String getIdentificadorEquipo(){
		
		String identificadorEquipo="";
		
		ClaseGeneradoraRepositorioInterfaces itfUsoRepositorioInterfaces;
		ItfUsoConfiguracion itfconfig;
		
    	try{    	
    		itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
    		itfconfig = (ItfUsoConfiguracion)itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.NOMBRE_ITF_USO_CONFIGURACION);
    		identificadorEquipo = itfconfig.getValorPropiedadGlobal(VocabularioRosace.identificadorEquipo); 
    	}
    	catch (Exception e){
    		e.printStackTrace();
    	}
			
    	String strAUX = identificadorEquipo.replaceAll(" ", "");  //elimina los espacios en blanco que podria haber en los numeros    	
    	identificadorEquipo = strAUX;
    	    	
		return identificadorEquipo;				
	}

	
	
//    public static String getRutaFicheroVictimasTest(){
//		
//		String rutaFicheroVictimasTest="";
//		
//		ClaseGeneradoraRepositorioInterfaces itfUsoRepositorioInterfaces;
//		ItfUsoConfiguracion itfconfig;
//		
//    	try{    	
//    		itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
//    		itfconfig = (ItfUsoConfiguracion)itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.NOMBRE_ITF_USO_CONFIGURACION);
//    		rutaFicheroVictimasTest = itfconfig.getValorPropiedadGlobal(VocabularioRosace.rutaFicheroVictimasTest); 
//    	}
//    	catch (Exception e){
//    		e.printStackTrace();
//    	}
//			
//    	String strAUX = rutaFicheroVictimasTest.replaceAll(" ", "");  //elimina los espacios en blanco que podria haber en los numeros    	
//    	rutaFicheroVictimasTest = strAUX;
//    	    	
//		return rutaFicheroVictimasTest;				
//	}
	
	
	
    public static String getRutaFicheroRobotsTest(){
		
		String rutaFicheroRobotsTest="";
		
		ClaseGeneradoraRepositorioInterfaces itfUsoRepositorioInterfaces;
		ItfUsoConfiguracion itfconfig;
		
    	try{    	
    		itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
    		itfconfig = (ItfUsoConfiguracion)itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.NOMBRE_ITF_USO_CONFIGURACION);
    		rutaFicheroRobotsTest = itfconfig.getValorPropiedadGlobal(VocabularioRosace.rutaFicheroRobotsTest); 
    	}
    	catch (Exception e){
    		e.printStackTrace();
    	}
			
    	String strAUX = rutaFicheroRobotsTest.replaceAll(" ", "");  //elimina los espacios en blanco que podria haber en los numeros    	
    	rutaFicheroRobotsTest = strAUX;
    	    	
		return rutaFicheroRobotsTest;				
	}

	
    public static int getIntervaloEnvioMensajesDesdeCC(){
		int intervaloEnvioMensajesDesdeCC;
		String strintervaloEnvioMensajesDesdeCC = "";
		
		ClaseGeneradoraRepositorioInterfaces itfUsoRepositorioInterfaces;
		ItfUsoConfiguracion itfconfig;
		
    	try{    	
    		itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
    		itfconfig = (ItfUsoConfiguracion)itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.NOMBRE_ITF_USO_CONFIGURACION);
    		strintervaloEnvioMensajesDesdeCC = itfconfig.getValorPropiedadGlobal(VocabularioRosace.intervaloMilisegundosEnvioCCMensajes); 
    	}
    	catch (Exception e){
    		e.printStackTrace();
    	}
			
    	//convertir a int la cadena leida de la propiedad global 
    	String strAUX = strintervaloEnvioMensajesDesdeCC.replaceAll(" ", "");  //elimina los espacios en blanco que podria haber en los numeros
        intervaloEnvioMensajesDesdeCC = Integer.parseInt(strAUX);
    	
		return intervaloEnvioMensajesDesdeCC;				
	}

    
	public static int getTimeTimeoutMilisegundosRecibirEvaluaciones(){		
		int timeTimeoutRecibirEvaluaciones;
		String strtimeTimeoutRecibirEvaluaciones = "";
		
		ClaseGeneradoraRepositorioInterfaces itfUsoRepositorioInterfaces;
		ItfUsoConfiguracion itfconfig;
		
    	try{    	
    		itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
    		itfconfig = (ItfUsoConfiguracion)itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.NOMBRE_ITF_USO_CONFIGURACION);
    		strtimeTimeoutRecibirEvaluaciones = itfconfig.getValorPropiedadGlobal(VocabularioRosace.timeTimeoutMilisegundosRecibirEvaluaciones); 
    	}
    	catch (Exception e){
    		e.printStackTrace();
    	}
			
    	//convertir a int la cadena leida de la propiedad global 
    	String strAUX = strtimeTimeoutRecibirEvaluaciones.replaceAll(" ", "");
        if (strAUX ==null) return 0;
    	timeTimeoutRecibirEvaluaciones = Integer.parseInt(strAUX);
    	
		return timeTimeoutRecibirEvaluaciones;								
	}

		
	public static int getTimeTimeoutMilisegundosRecibirPropuestaDesempate(){		
		int timeTimeoutRecibirPropuestaDesempate;
		String strtimeTimeoutRecibirPropuestaDesempate = "";
		
		ClaseGeneradoraRepositorioInterfaces itfUsoRepositorioInterfaces;
		ItfUsoConfiguracion itfconfig;
		
    	try{    	
    		itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
    		itfconfig = (ItfUsoConfiguracion)itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.NOMBRE_ITF_USO_CONFIGURACION);
    		strtimeTimeoutRecibirPropuestaDesempate = itfconfig.getValorPropiedadGlobal(VocabularioRosace.timeTimeoutMilisegundosRecibirPropuestaDesempate); 
    	}
    	catch (Exception e){
    		e.printStackTrace();
    	}
			
    	//convertir a int la cadena leida de la propiedad global 
    	String strAUX = strtimeTimeoutRecibirPropuestaDesempate.replaceAll(" ", "");
    	timeTimeoutRecibirPropuestaDesempate = Integer.parseInt(strAUX);
    	
		return timeTimeoutRecibirPropuestaDesempate;								
	}
		
	
	public static int getTimeTimeoutMilisegundosRecibirRespPropuestasIrYo(){		
		int timeTimeoutMilisegundosRecibirRespPropuestasIrYo;
		String strtimeTimeoutMilisegundosRecibirRespPropuestasIrYo = "";
		
		ClaseGeneradoraRepositorioInterfaces itfUsoRepositorioInterfaces;
		ItfUsoConfiguracion itfconfig;
		
    	try{    	
    		itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
    		itfconfig = (ItfUsoConfiguracion)itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.NOMBRE_ITF_USO_CONFIGURACION);
    		strtimeTimeoutMilisegundosRecibirRespPropuestasIrYo = itfconfig.getValorPropiedadGlobal(
    				                                                        VocabularioRosace.timeTimeoutMilisegundosRecibirRespPropuestasIrYo); 
    	}
    	catch (Exception e){
    		e.printStackTrace();
    	}
			
    	//convertir a int la cadena leida de la propiedad global 
    	String strAUX = strtimeTimeoutMilisegundosRecibirRespPropuestasIrYo.replaceAll(" ", "");
    	timeTimeoutMilisegundosRecibirRespPropuestasIrYo = Integer.parseInt(strAUX);
    	
		return timeTimeoutMilisegundosRecibirRespPropuestasIrYo;								
	}
	
    public static String getTipoAplicacion(){
		
		String valorTipoAplicacion = "";
		
		String identificadorEquipo = getValorCadenaPropiedadGlobal(VocabularioRosace.identificadorEquipo);
		
		if (identificadorEquipo.equals(VocabularioRosace.IdentEquipoIgualitario)){			
			valorTipoAplicacion = VocabularioRosace.tipoAplicacionIgualitario;
		}
		else if (identificadorEquipo.equals(VocabularioRosace.IdentEquipoJerarquico)) {
			valorTipoAplicacion = VocabularioRosace.tipoAplicacionJerarquico;
		}
		else {
			
		    valorTipoAplicacion = "INCORRECTO ..";
		}
		
		return valorTipoAplicacion;		
	}
	
	
}
