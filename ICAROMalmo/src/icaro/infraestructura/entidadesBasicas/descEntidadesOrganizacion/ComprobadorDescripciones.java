package icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion;

import com.thoughtworks.xstream.io.path.Path;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.*;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp.FactoriaAgenteReactivoImp;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;


public class ComprobadorDescripciones {



    private static ComprobadorDescripciones instance;

	//private ItfUsoRecursoTrazas trazas;

	private Logger logger = Logger
			.getLogger(this.getClass().getCanonicalName());

	private List<InfoTraza> errores;
        private ItfUsoRecursoTrazas trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        public DescComportamientoAgente descComptmtoVerificado;
	public ComprobadorDescripciones() {
		errores = new LinkedList<InfoTraza>();
		/*
		try {
			

			trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance()
					.obtenerInterfaz(
							NombresPredefinidos.ITF_USO
									+ NombresPredefinidos.RECURSO_TRAZAS);
			InfoTraza traza = new InfoTraza(NombresPredefinidos.CONFIGURACION,
					"Comprobando descripciones de los comportamientos...",
					NivelTraza.info);
			trazas.aceptaNuevaTraza(traza);



		} catch (Exception e) {
			logger.error("Error al acceder al repositorio de interfaces", e);
		}
*/
	}

	public static ComprobadorDescripciones instance() {
		if (instance == null)
			instance = new ComprobadorDescripciones();
		return instance;
	}

	public boolean comprobar(DescOrganizacion descOrganizacion) throws Exception {
		DescripcionComponentes componentes = descOrganizacion
				.getDescripcionComponentes();
		// comprobar existencia de gestor de organizacion o de gestor de nodo
              // Verificamos que existe un gestor del NODO que debe ser un gestor de  organización o un Gestor de Nodo
//					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION
                  String  identGestorNodo = descOrganizacion.getDescInstancias().getGestores().getInstanciaGestor().get(0).getId();
                  comprobarExistenciaGestorNodo(identGestorNodo);
//                  if (!(identGestorNodo.startsWith(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION))||!(identGestorNodo.startsWith(NombresPredefinidos.NOMBRE_GESTOR_NODO)) ){
//                                 logger.fatal("Error al verificar los nombres predefinidos del GO y del GN. Compruebe que exista,un GO o un GN  en la descripcion de la organizacion,"
//                                              + " y que los nombres asignados comiencen por GestroOrganizacion o GestorNodo");
//                                 errores.add(new InfoTraza(NombresPredefinidos.CONFIGURACION,
//                                            "Error al verificar los nombres predefinidos del GO y del GN."
//                                                  + " Compruebe que exista,un GO o un GN  en la descripcion de la organizacion",
//                                                  NivelTraza.fatal));
//                                        }
  
                // comprobar comportamiento de los gestores
		List<DescComportamientoAgente> gestores = componentes
				.getDescComportamientoAgentes().getDescComportamientoGestores()
				.getDescComportamientoAgente();
		for (DescComportamientoAgente gestor : gestores)
			gestor = comprobarGestor(gestor);

		// comprobar agentes de aplicacion
		List<DescComportamientoAgente> agentesAplicacion = componentes
				.getDescComportamientoAgentes()
				.getDescComportamientoAgentesAplicacion()
				.getDescComportamientoAgente();
		for (DescComportamientoAgente agenteAplicacion : agentesAplicacion)
			agenteAplicacion = comprobarAgenteAplicacion(agenteAplicacion);
		// comprobar recursos de aplicacin
		List<DescRecursoAplicacion> recursosAplicacion = componentes
				.getDescRecursosAplicacion().getDescRecursoAplicacion();
		for (DescRecursoAplicacion recursoAplicacion : recursosAplicacion)
			recursoAplicacion = comprobarRecursoAplicacion(recursoAplicacion);

		if (errores.size() > 0) {
			
			for (InfoTraza traza : errores) {
				trazas.aceptaNuevaTraza(traza);
				logger.error(traza);
			}
//			throw new Exception();
                        return false;
		}else return true;
                
	}

	public void comprobarExistenciaGestorNodo(String identGestorNodo) {
  
            if (!(identGestorNodo.startsWith(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION))&&(!(identGestorNodo.startsWith(NombresPredefinidos.NOMBRE_GESTOR_NODO))) ){
                                 logger.fatal("Error al verificar los nombres predefinidos del GO y del GN. Compruebe que exista,un GO o un GN  en la descripcion de la organizacion,"
                                              + " y que los nombres asignados comiencen por GestroOrganizacion o GestorNodo");
                                 errores.add(new InfoTraza(NombresPredefinidos.CONFIGURACION,
                                            "Error al verificar los nombres predefinidos del GO y del GN."
                                                  + " Compruebe que exista,un GO o un GN  en la descripcion de la organizacion",
                                                  NivelTraza.error));

                                        }
        }
        public DescComportamientoAgente comprobarGestor(DescComportamientoAgente agente) {
		String nombreAgente = agente.getNombreComportamiento();
		TipoAgente tipo = agente.getTipo();
		// paquete de comportamiento por defecto:
		RolAgente rol = agente.getRol();
		String comportamientoPorDefecto = null;
		if (rol == RolAgente.GESTOR)
			comportamientoPorDefecto = NombresPredefinidos.PAQUETE_GESTORES
					+ "." + primeraMinuscula(nombreAgente)+".comportamiento";
		String comportamientoEspecificado = agente.getLocalizacionComportamiento();
                if (comportamientoEspecificado == null)agente.setLocalizacionComportamiento(comportamientoPorDefecto);
			if (tipo == TipoAgente.REACTIVO)
			agente=	comprobarAgenteReactivo( agente);
			else 
			if (tipo == TipoAgente.COGNITIVO ||tipo==TipoAgente.ADO ||tipo==TipoAgente.DIRIGIDO_POR_OBJETIVOS  )
			agente=	comprobarAgenteADO(agente);
		return agente;
	}

    public DescComportamientoAgente comprobarAgenteAplicacion(DescComportamientoAgente agente) {
		String nombreAgente = agente.getNombreComportamiento();
		TipoAgente tipo = agente.getTipo();
		// paquete de comportamiento por defecto:      
		RolAgente rol = agente.getRol();
			if (tipo == TipoAgente.REACTIVO)
			 return	 comprobarAgenteReactivo(agente);
                                        
			else if (tipo == TipoAgente.COGNITIVO ||tipo==TipoAgente.ADO ||tipo==TipoAgente.DIRIGIDO_POR_OBJETIVOS )
				return  comprobarAgenteADO(agente);
		return agente;
		// no se especifica localizacion del comportamiento o el
		// especificado no se encuentra. Se usa el
		// comportamiento por defecto:
				
	}
//	public boolean comprobarAgenteReactivo(String rutaComportamiento,String nombreAgente, String rolAgente) {
        public DescComportamientoAgente comprobarAgenteReactivo(DescComportamientoAgente comptAgente) {
		boolean validacionAcciones = false, validacionAutomata = false ;
                String rutaDirectorioCompt = comptAgente.getLocalizacionComportamiento();
                String rutaClaseAcciones= comptAgente.getLocalizacionClaseAcciones();
                String rutaFicheroAutomata = comptAgente.getLocalizacionFicheroAutomata();
                String identComportmto = comptAgente.getNombreComportamiento();
		// acciones semanticas:
                // Si no se ha especificado directorio del comportamiento se pone el directorio por defecto              
                if (rutaClaseAcciones ==null){
                    if (rutaDirectorioCompt == null)//se valida  la ruta y luego que exista la clase
                        rutaDirectorioCompt = NombresPredefinidos.RUTA_AGENTES_APLICACION+"."+identComportmto+"."+NombresPredefinidos.CARPETA_COMPORTAMIENTO;
                    // rutaficheroAutomata definido en la descripcion de la organizacion
                        rutaClaseAcciones= rutaDirectorioCompt + "." +NombresPredefinidos.PREFIJO_CLASE_ACCIONES_SEMANTICAS+identComportmto+".class";
                        rutaFicheroAutomata= rutaDirectorioCompt + "." +NombresPredefinidos.FICHERO_AUTOMATA; 
                    }
                else if (rutaFicheroAutomata ==null){// se toma  la ruta de la clase acciones
                        rutaDirectorioCompt =rutaClaseAcciones.substring(0, rutaClaseAcciones.indexOf("." +NombresPredefinidos.PREFIJO_CLASE_ACCIONES_SEMANTICAS));
                        rutaFicheroAutomata= rutaDirectorioCompt + "." +NombresPredefinidos.FICHERO_AUTOMATA;
                }          
                rutaClaseAcciones= validarRutaClaseEntidad(rutaClaseAcciones,rutaDirectorioCompt, identComportmto );
                rutaFicheroAutomata = validarRutaEntidadComportamiento(rutaFicheroAutomata, rutaDirectorioCompt, identComportmto );
                comptAgente.setLocalizacionClaseAcciones(rutaClaseAcciones);
                comptAgente.setLocalizacionFicheroAutomata(rutaFicheroAutomata);
                return comptAgente;
        //        if (validacionAcciones&&validacionAutomata)return true;
         //       else return false;                
}
  public DescComportamientoAgente comprobarAgenteADO(DescComportamientoAgente comptAgente) {
    //	boolean validacionAcciones = false, validacionAutomata = false ;
        String rutaDirectorioCompt = comptAgente.getLocalizacionComportamiento();
        String rutaFicheroReglas = comptAgente.getLocalizacionFicheroReglas();
        String identComportmto = comptAgente.getNombreComportamiento();
                // Si no se ha especificado directorio del comportamiento se pone el directorio por defecto              
        if (rutaFicheroReglas == null){//se valida  la ruta y luego que exista la entidad
              if (rutaDirectorioCompt == null)     
                 rutaDirectorioCompt = NombresPredefinidos.RUTA_AGENTES_APLICACION+"."+identComportmto+"."+NombresPredefinidos.CARPETA_REGLAS;
                 rutaFicheroReglas= rutaDirectorioCompt +"."+NombresPredefinidos.CARPETA_REGLAS+ "." +NombresPredefinidos.FICHERO_REGLAS; 
        } // si la ruta del fichero de reglas esta definida se valida
    //    if (validarRutaEntidadComportamiento(rutaFicheroReglas,rutaDirectorioCompt,identComportmto)) return true;
    //    else return false; 
        comptAgente.setLocalizacionFicheroReglas(validarRutaEntidadComportamiento(rutaFicheroReglas,rutaDirectorioCompt,identComportmto));
       return comptAgente;
}
  public boolean validarRutaReglasAgteADO (String rutaEntidad, String identDescComptoEntidad){
     boolean especCorrecta=true, ficheroValido=false;
     String msgInfoUsuario = null;
     String  rutaDirectorioCompt = NombresPredefinidos.RUTA_AGENTES_APLICACION+"."+identDescComptoEntidad+"."+NombresPredefinidos.CARPETA_REGLAS;
     String  rutaFicheroReglasPorDfto= rutaDirectorioCompt +"."+NombresPredefinidos.CARPETA_REGLAS+ "." +NombresPredefinidos.FICHERO_REGLAS;
    
     if (!rutaEntidad.startsWith(rutaDirectorioCompt)){
              msgInfoUsuario = "Error en la ruta del comportamiento "
							+ identDescComptoEntidad
							+ "\n  La ruta especificada es :  "
							+ rutaEntidad
                                                        + " \n La ruta debe comenzar por " + rutaDirectorioCompt 
							+ ". Compruebe la ruta definida  en la descripcion de la organizacion.";
              InfoTraza traza = new InfoTraza(NombresPredefinidos.CONFIGURACION,msgInfoUsuario, NivelTraza.error);
   //           ItfUsoRecTrazas.aceptaNuevaTraza(traza);
              errores.add(traza);
              logger.fatal(msgInfoUsuario);
          } else especCorrecta = true;
              // Se valida el tipo de fichero y el nombre asociado 
           // if (! rutaEntidad.endsWith(".drl"))rutaEntidad = rutaEntidad+".drl";
            if (! rutaEntidad.endsWith(".drl"))rutaEntidad = rutaEntidad+".drl";
                  if ( ! verificarNombradoEntidad(rutaEntidad,NombresPredefinidos.NOMBRE_FICHERO_PDFTO_REGLAS)){
                       msgInfoUsuario = "Error en el nombre del fichero del Reglas. \n"+
                            "El nombre del comportamiento es : " + identDescComptoEntidad + ": " +
                            "En la ruta: " + rutaEntidad + "\n" +
                            "El nombre del fichero debe tener el formato : reglas<nombreAgente>.drl \n";
                  }
                else ficheroValido = true;
              if ( !ficheroValido){
                InfoTraza traza = new InfoTraza(NombresPredefinidos.CONFIGURACION,msgInfoUsuario, NivelTraza.error);
             //   ItfUsoRecTrazas.aceptaNuevaTraza(traza);
                errores.add(traza);
                logger.fatal(msgInfoUsuario);
                return false;
              }
      return false;
  
  }             
  public String validarRutaClaseEntidad (String rutaEntidadEspecificada, String rutaEntidadPorDfto, String identDescComptoEntidad){
      boolean especCorrecta=false;
      String msgInfoUsuario = null;
   //   String rutaValidada = rutaEntidadEspecificada;
      // se construye  la ruta por defecto y se valida que la ruta especificada cumple con el patron de ruta   
       // se valida que cumple el patron de ruta 
       // si termina en .class o .java se elimina
      Integer posicion = rutaEntidadEspecificada.indexOf(".class");
      if ( posicion < 0) posicion = rutaEntidadEspecificada.indexOf(".java");
      if ( posicion >= 0) rutaEntidadEspecificada = rutaEntidadEspecificada.substring(0,posicion);
      if (!rutaEntidadEspecificada.startsWith(rutaEntidadPorDfto)){
              msgInfoUsuario = "Error en la especificacion de la clase de la entidad :  "
							+ identDescComptoEntidad
							+ "\n  La ruta especificada es :  "
							+ rutaEntidadEspecificada
                                                        + " \n La ruta debe comenzar por " + rutaEntidadPorDfto 
							+ ". Compruebe la ruta definida  en la descripcion de la organizacion.";
              InfoTraza traza = new InfoTraza(NombresPredefinidos.CONFIGURACION,msgInfoUsuario, NivelTraza.error);
        //      ItfUsoRecTrazas.aceptaNuevaTraza(traza);
              errores.add(traza);
              logger.fatal(msgInfoUsuario);              
          }else especCorrecta= true;
      if (!existeClase(rutaEntidadEspecificada)){
           msgInfoUsuario = "Error no se encuentra la  clase especificada \n"+
                            "Para la  entidad:" + identDescComptoEntidad + 
                            "En la ruta: " + rutaEntidadEspecificada + "\n" +
                            "Verifique la existencia del fichero en el directorio src \n";
                    InfoTraza traza = new InfoTraza(NombresPredefinidos.CONFIGURACION,msgInfoUsuario, NivelTraza.error);
            //        ItfUsoRecTrazas.aceptaNuevaTraza(traza);
                    errores.add(traza);
                    logger.fatal(msgInfoUsuario);
      }//else if (especCorrecta) return true;
      return rutaEntidadEspecificada;
  } 
  /*
  public boolean validarRutaClaseGeneradoraRecursos (String rutaEntidad, String identDescComptoEntidad){
      boolean especCorrecta=true;
      String msgInfoUsuario = null;
      // se construye  la ruta por defecto y se valida que la ruta especificada cumple con el patron de ruta
      String rutaclaseGeneradoraPorDefecto = NombresPredefinidos.RUTA_RECURSOS_APLICACION
				+ "." + primeraMinuscula(identDescComptoEntidad) + ".imp."
				+ NombresPredefinidos.PREFIJO_CLASE_GENERADORA_RECURSO
				+ identDescComptoEntidad;
      if (rutaEntidad == null)
             rutaEntidad = rutaclaseGeneradoraPorDefecto;
      else // se valida que cumple el patron de ruta 
          if (!rutaEntidad.startsWith(rutaclaseGeneradoraPorDefecto)){
              msgInfoUsuario = "Error en la especificacion de la clase generadora del recurso :  "
							+ identDescComptoEntidad
							+ "\n  La ruta especificada es :  "
							+ rutaEntidad
                                                        + " \n La ruta debe comenzar por " + rutaclaseGeneradoraPorDefecto 
							+ ". Compruebe la ruta definida  en la descripcion de la organizacion.";
              InfoTraza traza = new InfoTraza(NombresPredefinidos.CONFIGURACION,msgInfoUsuario, NivelTraza.error);
              ItfUsoRecTrazas.aceptaNuevaTraza(traza);
              logger.fatal(msgInfoUsuario);
              especCorrecta=false;
          }
      if (!validarRutaClaseEntidad(rutaEntidad, rutaEntidad, identDescComptoEntidad)){
           msgInfoUsuario = "Error no se encuentra la  clase especificada \n"+
                            "Para el comportamiento:" + identDescComptoEntidad + 
                            "En la ruta: " + rutaEntidad + "\n" +
                            "Verifique la existencia del fichero en el directorio src \n";
                    InfoTraza traza = new InfoTraza(NombresPredefinidos.CONFIGURACION,msgInfoUsuario, NivelTraza.error);
                    ItfUsoRecTrazas.aceptaNuevaTraza(traza);
                    logger.fatal(msgInfoUsuario);
      }else if (especCorrecta) return true;
      return false;
  }
 */       
  public String validarRutaEntidadComportamiento (String rutaEntidad, String rutaPdftoDirEntidad ,String identDescComptoEntidad ){
          // se valida la ruta del directorio
         Boolean rutaValida=false, ficheroValido = false, encontrarFicheroEspedificado = false;
         Boolean esCompAgteReactivo = false, esCompAgteDirigidoPorObjetivos = false;
         String msgInfoUsuario= null, extensionFicheroRuta= null ;
          if (!rutaEntidad.startsWith(rutaPdftoDirEntidad)){
              msgInfoUsuario = "Error en la ruta del comportamiento "
							+ identDescComptoEntidad
							+ "\n  La ruta especificada es :  "
							+ rutaEntidad
                                                        + " \n La ruta debe comenzar por " + rutaPdftoDirEntidad 
							+ ". Compruebe la ruta definida  en la descripcion de la organizacion.";
              InfoTraza traza = new InfoTraza(NombresPredefinidos.CONFIGURACION,msgInfoUsuario, NivelTraza.error);
      //        ItfUsoRecTrazas.aceptaNuevaTraza(traza);
              errores.add(traza);
              logger.fatal(msgInfoUsuario);
          } else rutaValida = true;
              // Se valida el tipo de fichero y el nombre asociado 
              if(rutaEntidad.contains("automata")){
                   esCompAgteReactivo= true;
                   extensionFicheroRuta= ".xml";
               }else if (rutaEntidad.contains("reglas")){
                  esCompAgteDirigidoPorObjetivos= true;
                  extensionFicheroRuta = ".drl";
               }else { // el fichero no existe o no verifica las reglas de nombrado
                   msgInfoUsuario = "Error en el nombre del fichero que debe definir el comportamiento del agente.\n "+
                            "El nombre del comportamiento es : " + identDescComptoEntidad + ": " +
                            "En la ruta: " + rutaEntidad + "\n" +
                            "El nombre del fichero debe tener el formato : automata<nombreAgente>.java o reglas<nombreAgente>.drl\n";
                InfoTraza traza = new InfoTraza(NombresPredefinidos.CONFIGURACION,msgInfoUsuario, NivelTraza.error);
          //      ItfUsoRecTrazas.aceptaNuevaTraza(traza);
                 errores.add(traza);
                logger.fatal(msgInfoUsuario);
                return rutaEntidad;
               }
          if ( rutaEntidad.lastIndexOf(extensionFicheroRuta)<0)rutaEntidad = rutaEntidad+extensionFicheroRuta;
                        
            if (esCompAgteReactivo)
                  if ( ! verificarNombradoEntidad(rutaEntidad,NombresPredefinidos.NOMBRE_FICHERO_PDFTO_AUTOMATA)){
                      msgInfoUsuario = "Error en el nombre del fichero del Automata.\n "+
                            "El nombre del comportamiento es : " + identDescComptoEntidad + ": " +
                            "En la ruta: " + rutaEntidad + "\n" +
                            "El nombre del fichero debe tener el formato : automata<nombreAgente>.java \n";
                  }
                  else ficheroValido = true;
              else if (esCompAgteDirigidoPorObjetivos)
                  if ( ! verificarNombradoEntidad(rutaEntidad,NombresPredefinidos.NOMBRE_FICHERO_PDFTO_REGLAS)){
                       msgInfoUsuario = "Error en el nombre del fichero del Reglas. \n"+
                            "El nombre del comportamiento es : " + identDescComptoEntidad + ": " +
                            " En la ruta: " + rutaEntidad + "\n" +
                            "El nombre del fichero debe tener el formato : reglas<nombreAgente>.drl \n";
                  }
                else ficheroValido = true;
              if ( !ficheroValido){
                InfoTraza traza = new InfoTraza(NombresPredefinidos.CONFIGURACION,msgInfoUsuario, NivelTraza.error);
            //    ItfUsoRecTrazas.aceptaNuevaTraza(traza);
                errores.add(traza);
                logger.fatal(msgInfoUsuario);
              }
                if (rutaValida&&ficheroValido){ // se valida la existencia del recurso
                rutaEntidad = "/" + rutaEntidad.substring(0,rutaEntidad.lastIndexOf(".")).replace(".", "/")+extensionFicheroRuta;	
		InputStream input = this.getClass().getResourceAsStream(rutaEntidad);
		logger.debug(rutaEntidad+"?"+ ((input != null) ? "  OK" : "  null"));
		if  (input != null) return rutaEntidad;
                else {
                    // la entidad no se encuentra o no esta definida 
                    msgInfoUsuario = "Error no se encuentra el fichero especificado \n"+
                            "Para el comportamiento:" + identDescComptoEntidad + 
                            "\n En la ruta: " + rutaEntidad + "\n" +
                            " Verifique la existencia del fichero en el directorio src \n";
                    InfoTraza traza = new InfoTraza(NombresPredefinidos.CONFIGURACION,msgInfoUsuario, NivelTraza.error);
     //               ItfUsoRecTrazas.aceptaNuevaTraza(traza);
                    errores.add(traza);
                    logger.fatal(msgInfoUsuario); 
                    }
                }
                return rutaEntidad;
  }
  public boolean verificarNombradoEntidad(String rutaEntidad, String nombreFicheroPorDefecto){
      String msgInfoUsuario ;
      boolean extensionCorrecta = false;
      Integer posicionPunto = nombreFicheroPorDefecto.indexOf(".");
      String extensionFicheroPD = nombreFicheroPorDefecto.substring(posicionPunto);
      String nombreFicheroPD = nombreFicheroPorDefecto.substring(0,posicionPunto);
      // se obtiene el nombre del fichero en la ruta
      posicionPunto = rutaEntidad.lastIndexOf(".");
      String extensionFicheroRuta = rutaEntidad.substring(posicionPunto);
      String nombreficheroEnlaRuta = rutaEntidad.substring(0, posicionPunto);
              nombreficheroEnlaRuta = nombreficheroEnlaRuta.substring(nombreficheroEnlaRuta.lastIndexOf(".")+1);
      if (! extensionFicheroPD.equals(extensionFicheroRuta)){
           msgInfoUsuario = "Error extension del fichero incorrecta \n "
                            + " La extension del  fichero especificado  debe ser :   " + extensionFicheroPD 
                            +"  \n Fichero especificado :" + nombreficheroEnlaRuta + ": " +
                            "En la ruta:" + rutaEntidad + "\n" + 
                            "Verifique la existencia del fichero en el directorio src \n";
                    InfoTraza traza = new InfoTraza(NombresPredefinidos.CONFIGURACION,msgInfoUsuario, NivelTraza.error);
                    trazas.aceptaNuevaTraza(traza);
                    logger.fatal(msgInfoUsuario); 
      } else  extensionCorrecta = true;
       if (!nombreficheroEnlaRuta.startsWith(nombreFicheroPD)){
             msgInfoUsuario = "Error nombre de fichero incorrecto /n "
                            + " El  fichero especificado  debe comenzar por :   " + nombreFicheroPD 
                            +"fichero especificado :" + nombreficheroEnlaRuta + ": " +
                            "En la ruta:" + rutaEntidad + "/n" + 
                            "Verifique la existencia del fichero en el directorio src /n";
                    InfoTraza traza = new InfoTraza(NombresPredefinidos.CONFIGURACION,msgInfoUsuario, NivelTraza.error);
                    trazas.aceptaNuevaTraza(traza);
                    logger.fatal(msgInfoUsuario); 
          return false;
      } 
          else if (extensionCorrecta)return true;
                  else return false;
      
  }
        public DescRecursoAplicacion comprobarRecursoAplicacion(DescRecursoAplicacion recursoAplicacion) {
            boolean encontrada = false;
            String rutaclaseGeneradoraPorDefecto;
		String nombreRecurso = recursoAplicacion.getNombre();		
		String rutaClaseGeneradoraEspecificada = recursoAplicacion.getLocalizacionClaseGeneradora();
		if (rutaClaseGeneradoraEspecificada == null) {
                     rutaclaseGeneradoraPorDefecto = NombresPredefinidos.RUTA_RECURSOS_APLICACION
				+ "." + primeraMinuscula(nombreRecurso) + ".imp."
				+ NombresPredefinidos.PREFIJO_CLASE_GENERADORA_RECURSO
				+ nombreRecurso;
               //     recursoAplicacion.setLocalizacionClaseGeneradora(rutaclaseGeneradoraPorDefecto);
                    rutaClaseGeneradoraEspecificada = rutaclaseGeneradoraPorDefecto;
                }else rutaclaseGeneradoraPorDefecto = rutaClaseGeneradoraEspecificada;
                recursoAplicacion.setLocalizacionClaseGeneradora(
                        validarRutaClaseEntidad(rutaClaseGeneradoraEspecificada,rutaclaseGeneradoraPorDefecto, nombreRecurso));
               return recursoAplicacion;
                   
                        
        }
        public boolean existeClase(String rutaClase) {		
		Class clase;
                // Si termina en class o .java se elimina
                Integer posicion = rutaClase.indexOf(".class");
                if ( posicion < 0) posicion = rutaClase.indexOf(".java");
                if ( posicion >= 0) rutaClase = rutaClase.substring(0,posicion);
		try {
			clase = Class.forName(rutaClase);
			logger.debug(rutaClase+"?  OK");
			return (clase != null);
		} catch (ClassNotFoundException e) {
			logger.debug(rutaClase+"?  null");
			return false;
		}
	}
        private String primeraMinuscula(String nombre) {
		String firstChar = nombre.substring(0, 1);
		return nombre.replaceFirst(firstChar, firstChar.toLowerCase());
	}
/*        
	public boolean comprobarRecursoAplicacion1(
			DescRecursoAplicacion recursoAplicacion) {
		boolean encontrada = false;
		String nombreRecurso = recursoAplicacion.getNombre();
		String claseGeneradoraPorDefecto = NombresPredefinidos.RUTA_RECURSOS_APLICACION
				+ "." + primeraMinuscula(nombreRecurso) + ".imp."
				+ NombresPredefinidos.PREFIJO_CLASE_GENERADORA_RECURSO
				+ nombreRecurso;
		String claseGeneradoraEspecificada = recursoAplicacion.getLocalizacionClaseGeneradora();
		if (claseGeneradoraEspecificada != null) {
			if (existeClase(claseGeneradoraEspecificada)) {
				encontrada = true;
				
				logger.debug(NombresPredefinidos.CONFIGURACION
						+ ": Clase generadora leida para el recurso "
						+ nombreRecurso + ": "
						+ claseGeneradoraEspecificada);
			} else {
				encontrada = false;
				InfoTraza traza = new InfoTraza(
						NombresPredefinidos.CONFIGURACION,
						"Error al leer la clase generadora "
								+ claseGeneradoraEspecificada
								+ " del recurso"
								+ nombreRecurso
								+ ". Compruebe que la clase existe o especifique otra localizacion del comportamiento del recurso" +nombreRecurso
								+ " en la descripcion de la organizacion.",
						NivelTraza.fatal);
				logger
						.fatal(NombresPredefinidos.CONFIGURACION
								+ ": Error al leer la clase generadora "
								+ claseGeneradoraEspecificada
								+ " del recurso"
								+ nombreRecurso
								+ ". Compruebe que la clase existe o especifique otra localizacion del comportamiento del recurso" +nombreRecurso
								+ " en la descripcion de la organizacion.");
                ItfUsoRecTrazas.aceptaNuevaTraza(traza);
				errores.add(traza);
			}
		}
		if (claseGeneradoraEspecificada == null || !encontrada) {
			if (existeClase(claseGeneradoraPorDefecto)) {
				encontrada = true;
                recursoAplicacion.setLocalizacionClaseGeneradora(claseGeneradoraPorDefecto);
				
				logger.debug(NombresPredefinidos.CONFIGURACION
						+ ": Clase generadora leida para el recurso "
						+ nombreRecurso + ": "
						+ claseGeneradoraPorDefecto);
			} else {
				encontrada = false;
				InfoTraza traza = new InfoTraza(
						NombresPredefinidos.CONFIGURACION,
						"Error al leer la clase generadora "
								+ claseGeneradoraPorDefecto
								+ "del recurso"
								+ nombreRecurso
								+ ". Compruebe que la clase existe o especifique la localizacion del comportamiento del recurso" +nombreRecurso
								+ "en la descripcion de la organizacion.",
						NivelTraza.fatal);
				logger
						.fatal(NombresPredefinidos.CONFIGURACION
								+ ": Error al leer la clase generadora "
								+ claseGeneradoraPorDefecto
								+ "del recurso"
								+ nombreRecurso
								+ ". Compruebe que la clase existe o especifique la localizacion del comportamiento del recurso" +nombreRecurso
								+ "en la descripcion de la organizacion.");
				ItfUsoRecTrazas.aceptaNuevaTraza(traza);
                errores.add(traza);
			}			
		}
		
		return encontrada;
	}

	

	public boolean existeRecursoClasspath(String recursoClassPath) {
		
		InputStream input = this.getClass().getResourceAsStream(
				recursoClassPath);
		logger.debug(recursoClassPath+"?"+ ((input != null) ? "  OK" : "  null"));
		return (input != null);
	}

	
        
        public Collection<File> lookupClasspathFiles(String name) {
        Enumeration<URL> resources;
        List<File> files = new ArrayList<File>();
        
        try {
            resources = ComprobadorDescripciones.class.getClassLoader().getResources(name);
            
            while (resources.hasMoreElements()) {
                URI uri = resources.nextElement().toURI();
                File file = new File(uri);
                files.add(file);
            }
            
        } catch (Exception e) {
            throw new RuntimeException("could not lookup files with name: "+name, e);
        }
        
        return files;
    }
        private Class obtenerClaseAccionesSemanticas(String rutaComportamiento)throws ExcepcionEnComponente {
    // La ruta del comportamiento no incluye la clase
    // Obtenemos la clase de AS en la ruta
    String rutaClases = rutaComportamiento;
            rutaComportamiento = NombresPredefinidos.RUTA_SRC+File.separator+rutaComportamiento.replace(".", File.separator);
 //           rutaComportamiento = utils.constantes.rutassrc +File.separator+rutaComportamiento.replace(".", File.separator);
            Collection<File> ficheros = lookupClasspathFiles(rutaComportamiento);
            String[] ficherosRuta = (new File (rutaComportamiento)).list() ;
   // Buscamos la clase de acciones semanticas en el array con los ficheros
          
        Boolean ClaseASEncontrada=false;
        String nombreClase= "";
        for (int i=0; (i<ficherosRuta.length)&!ClaseASEncontrada; i++){
             nombreClase = ficherosRuta[ i ];
         if (nombreClase.startsWith(NombresPredefinidos.PREFIJO_CLASE_ACCIONES_SEMANTICAS )&
            (nombreClase.lastIndexOf(".java")!= 0)  ){
                        ClaseASEncontrada = true;
                            }
            }
        if (ClaseASEncontrada = true) {
        try {

            rutaClases = rutaClases+"."+nombreClase;
            rutaClases =rutaClases.replace(".java", "");
            int i=1;
            Class claseAcciones = Class.forName(rutaClases);
            return claseAcciones;
            }
        catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FactoriaAgenteReactivoImp.class.getName()).log(Level.SEVERE, null, ex);
        throw new ExcepcionEnComponente ( "PatronAgenteReactivo", "No se encuentra la clase de acciones semanticas en la ruta :"+rutaComportamiento,"Factoria del Agente Reactivo","Class obtenerClaseAccionesSemanticas(DescInstanciaAgente instAgente)"  );
                                         }
            }
        else
            throw new ExcepcionEnComponente ( "PatronAgenteReactivo", "No se encuentra la clase de acciones semanticas en la ruta :"+rutaComportamiento,"Factoria del Agente Reactivo","Class obtenerClaseAccionesSemanticas(DescInstanciaAgente instAgente)"  );
   }
        private boolean validarAutomatayAccionesSemanticas(String rutaComportamiento)throws ExcepcionEnComponente {
    // La ruta del comportamiento no incluye la clase
    // Obtenemos la clase de AS en la ruta
    String rutaClases = rutaComportamiento;
            rutaComportamiento = NombresPredefinidos.RUTA_SRC+File.separator+rutaComportamiento.replace(".", File.separator);
 //           rutaComportamiento = utils.constantes.rutassrc +File.separator+rutaComportamiento.replace(".", File.separator);
            String[] ficherosRuta = (new File (rutaComportamiento)).list() ;
   // Buscamos la clase de acciones semanticas en el array con los ficheros
          
        Boolean claseASEncontrada=false;
        Boolean ficheroAutomataEncontrado=false;
        String nombreFichero= "";
        for (int i=0; (i<ficherosRuta.length)&(!claseASEncontrada|!ficheroAutomataEncontrado); i++){
             nombreFichero = ficherosRuta[ i ];
            if (nombreFichero.startsWith(NombresPredefinidos.PREFIJO_CLASE_ACCIONES_SEMANTICAS )&
                (nombreFichero.lastIndexOf(".java")!= 0)  ){
                        claseASEncontrada = true;  
            }else
                if (nombreFichero.startsWith(NombresPredefinidos.PREFIJO_AUTOMATA )&
                    (nombreFichero.lastIndexOf(".xml")!= 0)  ){
                        ficheroAutomataEncontrado = true; 
                }
            }
        if (claseASEncontrada && ficheroAutomataEncontrado)return true;
        else {
             return false;
       //     throw new ExcepcionEnComponente ( "PatronAgenteReactivo", "No se encuentra la clase de acciones semanticas en la ruta :"+rutaComportamiento,"Factoria del Agente Reactivo","Class obtenerClaseAccionesSemanticas(DescInstanciaAgente instAgente)"  );
        //        return false;
        }
}
*/
}   

