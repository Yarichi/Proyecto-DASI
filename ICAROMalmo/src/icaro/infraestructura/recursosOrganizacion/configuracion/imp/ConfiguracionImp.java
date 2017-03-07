package icaro.infraestructura.recursosOrganizacion.configuracion.imp;

import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.ConstructorDescOrganizacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.SchemaValidator;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.ComprobadorDescripciones;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.*;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescComportamientoAgente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescOrganizacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescRecursoAplicacion;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;


/**
 * @author Damiano Spina
 * @version 1.0
 * @created 19-feb-2008 13:20:43
 */
public class ConfiguracionImp extends ClaseGeneradoraConfiguracion {

	private transient DescOrganizacion descOrganizacion;
	
	private File schema;
	private File xmlDescripcion;

//	private static final String DESCRIPCION_SCHEMA = "./schemas/DescripcionOrganizacionSchema.xsd";
//	private static final String DESCRIPCION_XML_POR_DEFECTO = "descripcionAcceso.xml";
//	private static final String RUTA_DESCRIPCIONES = "./config/icaro/aplicaciones/descripcionOrganizaciones/";
//	private static final String PAQUETE_JAXB = "icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb";
//  private String descXML = RUTA_DESCRIPCIONES+DESCRIPCION_XML_POR_DEFECTO;
//  private String descXML = NombresPredefinidos.DESCRIPCION_XML_POR_DEFECTO+".xml";
    private String identFicheroDescripcion =NombresPredefinidos.DESCRIPCION_XML_POR_DEFECTO;
    private String identGestorNodo = null;
    private String descXML = NombresPredefinidos.RUTA_DESCRIPCIONES+identFicheroDescripcion+".xml";
    private transient Logger logger = Logger
			.getLogger(this.getClass().getCanonicalName());
	public ItfUsoRecursoTrazas trazas; 
	private transient ConstructorDescOrganizacion constructorDescInstancias;
        private  HashMap<String,Object> tablaDescripcionComponentes;
	private Properties properties;
        private String  valorPanelTrazas, msgUsuario;
        private String  esteNodo ;

	public ConfiguracionImp(String descAlternativa) throws ExcepcionEnComponente, RemoteException {
		super(NombresPredefinidos.CONFIGURACION);
		descXML = NombresPredefinidos.RUTA_DESCRIPCIONES+descAlternativa+".xml";
                identFicheroDescripcion = descAlternativa;
                trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
		creacionConfiguracion();
	}

	public ConfiguracionImp() throws ExcepcionEnComponente, RemoteException {
		super(NombresPredefinidos.CONFIGURACION);
//		creacionConfiguracion();
	}

	public void creacionConfiguracion() throws ExcepcionEnComponente {
		// Lectura de los ficheros (esquema y xml)

       
        ComprobadorRutasEntidades comprobadorEntidades = new ComprobadorRutasEntidades();
        if (!comprobadorEntidades.existeSchemaDescOrganizacion()){
			logger
					.fatal("Error. No se ha encontrado el esquema del fichero de descripcion:"
							+ "\n\t\t\t"
							+ schema.getAbsolutePath()
							+ ".\n Compruebe la ruta y el nombre del fichero.");
			throw new RuntimeException();
		}

        if (!comprobadorEntidades.existeDescOrganizacion(identFicheroDescripcion)){
                        msgUsuario = "No se ha encontrado el fichero de descripcion:"
					+ "\n\t\t\t" + xmlDescripcion.getAbsolutePath()
					+ ".\n Compruebe la ruta y el nombre del fichero.";
                        trazas.trazar("configuracion",msgUsuario, NivelTraza.error);
			logger.fatal(msgUsuario);
			throw new RuntimeException();
		}
		// Validacion del fichero xml:
		schema = new File(NombresPredefinidos.DESCRIPCION_SCHEMA);
        xmlDescripcion = new File(descXML);
        SchemaValidator validator = new SchemaValidator(schema);
		try {
			validator.validate(xmlDescripcion);

				// Interpretacion del xml con JAXB
				try {
					JAXBContext jc = JAXBContext.newInstance(NombresPredefinidos.PAQUETE_JAXB);
					Unmarshaller unmarshaller;
					unmarshaller = jc.createUnmarshaller();
					descOrganizacion = (DescOrganizacion) ((JAXBElement) unmarshaller
						.unmarshal(new FileInputStream(xmlDescripcion))).getValue();
				} catch (Exception e) {
					logger.fatal("Error al interpretar la descripcion de la organizacion que se encuentra en:" + xmlDescripcion.getAbsolutePath(), e);
					throw new RuntimeException();
				}
				try {
					// Verificamos que existe un gestor del NODO que debe ser un gestor de  organización o un Gestor de Nodo
//					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION
//                                        identGestorNodo = this.descOrganizacion.getDescInstancias().getGestores().getInstanciaGestor().get(0).getId();
//					if (!(identGestorNodo.startsWith(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION))||!(identGestorNodo.startsWith(NombresPredefinidos.NOMBRE_GESTOR_NODO)) ){
//                                          logger.fatal("Error al verificar los nombres predefinidos del GO y del GN. Compruebe que exista,un GO o un GN  en la descripcion de la organizacion,"
//                                                  + " y que los nombres asignados comiencen por GestroOrganizacion o GestorNodo");
//                                            }

//                                      NombresPredefinidos.NOMBRE_GESTOR_AGENTES = this.descOrganizacion.getDescInstancias().getGestores().getInstanciaGestor().get(1).getId();
//					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS = this.descOrganizacion.getDescInstancias().getGestores().getInstanciaGestor().get(2).getId();
					// Actualizo la configuracion de trazas
					NombresPredefinidos.ACTIVACION_PANEL_TRAZAS_DEBUG = Boolean.toString(this.descOrganizacion.getPropiedadesGlobales().isActivarPanelTrazasDebug());
					valorPanelTrazas = NombresPredefinidos.ACTIVACION_PANEL_TRAZAS_DEBUG;
				} catch (Exception e) {
					logger.fatal("Error al inicializar el panel de trazas según   la descripcion de la organizacion. Compruebe  la descripcion de la organizacion, ",
							e);
				}
				try {
					ComprobadorDescripciones comprobador = ComprobadorDescripciones.instance();
                                    if ( comprobador.comprobar(descOrganizacion)){
                                        try {
                                            esteNodo = InetAddress.getLocalHost().getHostName();
                                          } catch (UnknownHostException ex) {
                                            java.util.logging.Logger.getLogger(ConfiguracionImp.class.getName()).log(Level.SEVERE, " --Error al obtener el identificador del Host  -- ", ex);
                                          }
				constructorDescInstancias = new ConstructorDescOrganizacion(descOrganizacion);
      //                          tablaDescripcionComponentes = new HashMap<String, Object>();

				properties = ConstructorProperties.obtenerProperties(descOrganizacion.getPropiedadesGlobales().getListaPropiedades());
				properties.setProperty(NombresPredefinidos.INTERVALO_MONITORIZACION_ATR_PROPERTY,
						Integer.toString(descOrganizacion.getPropiedadesGlobales().getIntervaloMonitorizacionGestores()));
				properties.setProperty(NombresPredefinidos.ACTIVACION_PANEL_TRAZAS_DEBUG,
						Boolean.toString(descOrganizacion.getPropiedadesGlobales().isActivarPanelTrazasDebug()));
                                    }
                                    else {
                                         trazas.trazar(this.id, "\n\nError al comprobar los comportamientos de los gestores, agentes y recursos descritos en el fichero de descripcion del XML " + xmlDescripcion.getAbsolutePath(), InfoTraza.NivelTraza.error);
                                    };
				} catch (Exception e) {
					logger.fatal("\n\nError al comprobar los comportamientos de los gestores, agentes y recursos descritos en el fichero de descripcion del XML " + xmlDescripcion.getAbsolutePath() + ".",
							e);
                                        trazas.trazar(this.id, "\n\nError al comprobar los comportamientos de los gestores, agentes y recursos descritos en el fichero de descripcion del XML " + xmlDescripcion.getAbsolutePath(), InfoTraza.NivelTraza.error);
					throw new ExcepcionEnComponente("\n\nError al comprobar los comportamientos de los gestores, agentes y recursos descritos en el fichero de descripcion del XML " + xmlDescripcion.getAbsolutePath());
				}
                                     
		} catch (SAXException ex) {
			logger.fatal("\n\nEl fichero de descripcion del XML " + xmlDescripcion.getAbsolutePath() + "  no es valido. Compruebe la sintaxis de la descripcion y vuelva a intentarlo");
	//			throw new RuntimeException();
                        throw new ExcepcionEnComponente("Componente:configuracion","causa:Erores en el fichero de descripcion de la organizacion ", "Recurso de configuracion", "interpretacion del fichero de descripcion" );
		}

	}
    @Override
public void interpretarDescripOrganizacion(String rutaDescrOrganizacion)throws ExcepcionEnComponente,RemoteException{
    // La existencia de la descripcion en la ruta ya ha sido validada
        schema = new File(NombresPredefinidos.DESCRIPCION_SCHEMA);
        xmlDescripcion = new File(rutaDescrOrganizacion);
        SchemaValidator validator = new SchemaValidator(schema);
		try {
			validator.validate(xmlDescripcion);

				// Interpretacion del xml con JAXB
				try {
					JAXBContext jc = JAXBContext.newInstance(NombresPredefinidos.PAQUETE_JAXB);
					Unmarshaller unmarshaller;
					unmarshaller = jc.createUnmarshaller();
					descOrganizacion = (DescOrganizacion) ((JAXBElement) unmarshaller
						.unmarshal(new FileInputStream(xmlDescripcion))).getValue();
				} catch (Exception e) {
                                        msgUsuario ="Error al interpretar la descripcion de la organizacion que se encuentra en:" + xmlDescripcion.getAbsolutePath();
                                        trazas.trazar("configuracion",msgUsuario, NivelTraza.error);
					logger.fatal(msgUsuario, e);
					//throw new RuntimeException();
                                        throw new ExcepcionEnComponente("Componente:configuracion","causa:Erores en el fichero de descripcion de la organizacion ", "Recurso de configuracion", "interpretacion del fichero de descripcion" );
				}
                }catch (SAXException ex) {
                        msgUsuario ="\n\nEl fichero de descripcion del XML " + xmlDescripcion.getAbsolutePath() + "  no es valido. Compruebe la sintaxis de la descripcion y vuelva a intentarlo";
                        trazas.trazar("configuracion",msgUsuario, NivelTraza.error);
			logger.fatal(msgUsuario);
	//			throw new RuntimeException();
                        throw new ExcepcionEnComponente("Componente:configuracion","causa:Erores en el fichero de descripcion de la organizacion ", "Recurso de configuracion", "interpretacion del fichero de descripcion" );
		}        
}
    @Override
   public boolean validarDescripOrganizacion()throws ExcepcionEnComponente,RemoteException{ 
       
       try {
            ComprobadorDescripciones comprobador = ComprobadorDescripciones.instance();
           if ( comprobador.comprobar(descOrganizacion)){
              try {
                  esteNodo = InetAddress.getLocalHost().getHostName();
                  } catch (UnknownHostException ex) {
                     java.util.logging.Logger.getLogger(ConfiguracionImp.class.getName()).log(Level.SEVERE, " --Error al obtener el identificador del Host  -- ", ex);
                    }
            constructorDescInstancias = new ConstructorDescOrganizacion(descOrganizacion);
      //                          tablaDescripcionComponentes = new HashMap<String, Object>();

            properties = ConstructorProperties.obtenerProperties(descOrganizacion.getPropiedadesGlobales().getListaPropiedades());
            properties.setProperty(NombresPredefinidos.INTERVALO_MONITORIZACION_ATR_PROPERTY,
			Integer.toString(descOrganizacion.getPropiedadesGlobales().getIntervaloMonitorizacionGestores()));
            properties.setProperty(NombresPredefinidos.ACTIVACION_PANEL_TRAZAS_DEBUG,
				Boolean.toString(descOrganizacion.getPropiedadesGlobales().isActivarPanelTrazasDebug()));
           return true;
           }
             else {
                  trazas.trazar(this.id, "\n\nError al comprobar los comportamientos de los gestores, agentes y recursos descritos en \n fichero de descripcion de la organizacion " + xmlDescripcion.getAbsolutePath(), InfoTraza.NivelTraza.error);
                  return false;
                }
	} catch (Exception e) {
		logger.fatal("\n\nError al comprobar los comportamientos de los gestores, agentes y recursos descritos en \n fichero de descripcion de la organizacion  " + xmlDescripcion.getAbsolutePath() + ".",
                            e);
                trazas.trazar(this.id, "\n\nError al comprobar los comportamientos de los gestores, agentes y recursos descritos en \n fichero de descripcion de la organizacion  " + xmlDescripcion.getAbsolutePath(), InfoTraza.NivelTraza.error);
                throw new ExcepcionEnComponente("\n\nError al comprobar los comportamientos de los gestores, agentes y recursos descritos en \n fichero de descripcion de la organizacion  " + xmlDescripcion.getAbsolutePath());
		}
   }
    
    @Override
	public DescComportamientoAgente getDescComportamientoAgente(String nombre)
			throws ExcepcionEnComponente {

            return constructorDescInstancias.getDescComportamientoAgente(nombre);

        }

    @Override
	public DescRecursoAplicacion getDescRecursoAplicacion(String nombre)
			throws ExcepcionEnComponente {
                         return constructorDescInstancias.getDescRecursoAplicacion(nombre);
//		Iterator<DescRecursoAplicacion> iter = this.descOrganizacion.getDescripcionComponentes().getDescRecursosAplicacion().getDescRecursoAplicacion().iterator();
//		DescRecursoAplicacion desc;
//		while (iter.hasNext()) {
//			desc = iter.next();
//			if (desc.getNombre().equals(nombre))
//				return desc;
//		}
//		throw new UsoRecursoException("La descripcion de recurso de aplicacion de nombre "+ nombre
//						+ "no se encuentra en la descripcion de comportamiento de recursos de aplicaci�n");
	}

    @Override
        public Boolean existeDescInstancia(String id)  {

           return constructorDescInstancias.existeDescInstancia(id);
	}
        
    @Override
        public DescInstancia getDescInstancia(String id)  {

           return constructorDescInstancias.getDescInstancia(id);
	}
    @Override
	public DescInstanciaAgenteAplicacion getDescInstanciaAgenteAplicacion(String id) throws ExcepcionEnComponente {

           return  constructorDescInstancias.getDescInstanciaAgenteAplicacion(id);
                
	}

    @Override
	public DescInstanciaGestor getDescInstanciaGestor(String id) throws ExcepcionEnComponente {
            return  constructorDescInstancias.getDescInstanciaGestor(id);
	}

    @Override
	public DescInstanciaRecursoAplicacion getDescInstanciaRecursoAplicacion(String id) throws ExcepcionEnComponente {
		return  constructorDescInstancias.getDescInstanciaRecursoAplicacion(id);
                    
	}

    @Override
        public String getHostAgente(String idAgente) throws ExcepcionEnComponente,RemoteException {

           return constructorDescInstancias.getNodoInstanciaComponente(idAgente);

        }
    @Override
        public String getHostComponente(String idComponente) throws ExcepcionEnComponente,RemoteException {

           return constructorDescInstancias.getNodoInstanciaComponente(idComponente);

        }
//         DescComportamientoAgente descCompAgte =     constructorDescInstancias.getDescComportamientoAgente(idAgente);
//         if (descCompAgte == null){
//             logger.fatal("\n\nError El comportamiento del agente  " + idAgente + "  . Es null . El agente no ha sido definido con ese identificador");
//              return null;
//         }
//         if (descCompAgte.getRol().value().equalsIgnoreCase("Gestor")){
//             return constructorDescInstancias.getDescInstanciaGestor(idAgente).getNodo().getNombreUso();
//         }else
//             // suponemos que es un agente de aplicacion
//             return constructorDescInstancias.getDescInstanciaAgenteAplicacion(idAgente).getNodo().getNombreUso();
//	}

    @Override
        public String getHostRecurso(String idRecurso) throws ExcepcionEnComponente,RemoteException {
         DescInstanciaRecursoAplicacion descRecurso =     constructorDescInstancias.getDescInstanciaRecursoAplicacion(idRecurso);
         if (descRecurso != null){
             return descRecurso.getNodo().getNombreUso();
         }else
              logger.fatal("\n\nError La descripcion del  comportamiento del recurso  " + idRecurso + "  . Es null . El recurso no ha sido definido con ese identificador");
              return null;
//      Tambien se puede hacer con la instrucción de abajo
//              return constructorDescInstancias.getNodoInstanciaComponente(idRecurso);
	}
    @Override
	public String getValorPropiedadGlobal(String atributo)
			throws ExcepcionEnComponente {
		return properties.getProperty(atributo);
		
	}
    @Override
    public int getValorNumericoPropiedadGlobal(String atributo)throws ExcepcionEnComponente,RemoteException {
        String valorAtributo = properties.getProperty(atributo);
        if(valorAtributo==null){
            logger.fatal("\n\n No existe o no esta definido el  atributo  especificada  " + atributo + "  ");
              return -1;
        }else{
            valorAtributo = valorAtributo.replaceAll(" ", "");
            return Integer.parseInt(valorAtributo);
        }
    }
    @Override
	public Properties getPropiedadesGlobales () {
        return properties;
        }
    @Override
        public String getIdentGestorInicial () {
        // El gestor inicial puede ser un GO o un GN
            return descOrganizacion.getDescInstancias().getGestores().getInstanciaGestor().get(0).getId();
        }
	public DescOrganizacion getDescOrganizacion() {
		return descOrganizacion;
	}
         public ArrayList<String> getIdentificadoresGestoresDefinidos(){
            return constructorDescInstancias.getIdentificadoresGestoresDefinidos();

        }
    @Override
        public ArrayList<String> getIdentificadoresInstanciasAgentesAplicacion()throws ExcepcionEnComponente, RemoteException{
            return constructorDescInstancias.getIdentificadoresInstanciasAgentesAplicacion();
        }
        public ArrayList<String> getIdentificadoresInstanciasRecursoAplicacion(){
            return constructorDescInstancias.getIdentificadoresInstanciasRecursoAplicacion();
        }
    @Override
         public Boolean despliegueOrgEnUnSoloNodo ()throws ExcepcionEnComponente,RemoteException  {
            return  (constructorDescInstancias.despliegueOrgEnUnSoloNodo());
         }
    @Override
        public Boolean esAgenteRemoto (String idAgente)throws ExcepcionEnComponente,RemoteException {
            return (!esteNodo.equalsIgnoreCase(getHostAgente(idAgente)));
        }
    @Override
        public Boolean esRecursoRemoto (String idRecurso)throws ExcepcionEnComponente,RemoteException{
             return (!esteNodo.equalsIgnoreCase(getHostRecurso(idRecurso)));
        }
    @Override
        public Boolean esComponenteRemoto (String idRecurso)throws ExcepcionEnComponente,RemoteException{
             return (!esteNodo.equalsIgnoreCase(getHostComponente(idRecurso)));
        }

}