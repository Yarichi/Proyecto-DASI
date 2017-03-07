package icaro.infraestructura.entidadesBasicas.comunicacion;


import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.Nodo;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.configuracion.imp.ClaseGeneradoraConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import org.apache.log4j.Logger;


/**
 *
 * @author Alejandro Fernandez
 * @author Andres Picazo
 * @author Arturo Mazon
 * @author F Garijo
 */
public class AdaptadorRegRMI {
    /* ---------------------------------------------------------------------- */
    public static Registry registroRMIOrganizacion,registroRMILocal  = null;
    public static int puertoRMI = 1099;
    public static String  hostRMI = null;
    public static String thisHost = null;
    public static ItfUsoConfiguracion configuracionOrganizacion = null;  ;
    public static ItfUsoRecursoTrazas trazas ;
    public static String hostRMIOrganizacionconfigurado;
    public static String puertoRMIOrganizacionconfigurado ;
    private static String infoParaTrazar;
    public static Boolean registroRMIOrganizacionCreado = false;
    public static Boolean registroRMILocalCreado= false;
    protected static transient Logger logger ;

    /* ---------------------------------------------------------------------- */
    public static int setPuertoRMI(int puerto) {
        puertoRMI = puerto;
        return puertoRMI;
    }
    /* ---------------------------------------------------------------------- */
    public static void inicializar() throws RemoteException, UnknownHostException{
        try {
 //           if ( configuracionOrganizacion == null)  configuracionOrganizacion = ClaseGeneradoraConfiguracion.instance();
            if ( configuracionOrganizacion == null){
              configuracionOrganizacion = (ItfUsoConfiguracion)NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz(NombresPredefinidos.NOMBRE_ITF_USO_CONFIGURACION);
            }
            trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ ;
            logger = Logger.getLogger(AdaptadorRegRMI.class);
            thisHost = InetAddress.getLocalHost().getHostName();
            hostRMIOrganizacionconfigurado = configuracionOrganizacion.getValorPropiedadGlobal("HostNodoRMI_Org");
            puertoRMIOrganizacionconfigurado =  configuracionOrganizacion.getValorPropiedadGlobal("PuertoRMI_org");
            if (puertoRMIOrganizacionconfigurado != null)puertoRMI = Integer.parseInt (puertoRMIOrganizacionconfigurado);
             registroRMILocal = LocateRegistry.createRegistry(puertoRMI);
             registroRMILocalCreado = true;
            if (thisHost.equalsIgnoreCase(hostRMIOrganizacionconfigurado)) {
                registroRMIOrganizacion = registroRMILocal;
//                hostRMI = thisHost;
                registroRMIOrganizacionCreado = true;
            }else{
                 registroRMIOrganizacion = LocateRegistry.getRegistry(hostRMIOrganizacionconfigurado);
//                 hostRMI = hostRMIOrganizacionconfigurado;
                 if (registroRMIOrganizacion == null){
                                infoParaTrazar = "No se ha podido obtener el registro de la organizacion en el nodo :  "+ hostRMIOrganizacionconfigurado + ": Revisar la descripcion de la organizacion" ;
                                trazarError(infoParaTrazar);
                }else
                     registroRMIOrganizacionCreado = true;
            }
//           if (System.getSecurityManager() == null) {
//             System.setSecurityManager(new SecurityManager().checkPermission());
//            }

        } catch (Exception ex) {
            logger.error("Error al localizar el registro local o el registro de la organizacion  en el nodo--: "+ hostRMIOrganizacionconfigurado + " Es posible que no se haya creado: Revisar la descripcion de la organizacion" );
            trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI",
						"Error al localizar el registro local o el registro de la organizacion  en el nodo  "+ hostRMIOrganizacionconfigurado + "-- Es posible que no se haya creado: Revisar la descripcion de la organizacion" ,
						InfoTraza.NivelTraza.error));
        }


    }


    public static Registry getRegistroRMIOrganizacion () { 
         try {
            if (registroRMIOrganizacion != null ) return registroRMIOrganizacion ;
             else
 //                if (configuracionOrganizacion == null)  inicializar();
                hostRMIOrganizacionconfigurado = configuracionOrganizacion.getPropiedadesGlobales().getProperty("HostNodoRMI_Org");
            if ((hostRMIOrganizacionconfigurado != null) & (!hostRMIOrganizacionconfigurado.isEmpty())){
                 thisHost = InetAddress.getLocalHost().getHostName();
                if (thisHost .equalsIgnoreCase(hostRMIOrganizacionconfigurado)){
                     registroRMIOrganizacion = getRegistroRMInodoLocal();
                     registroRMIOrganizacionCreado = true;
                }
                     else {
                         registroRMIOrganizacion = LocateRegistry.getRegistry(hostRMIOrganizacionconfigurado);
                         if (registroRMIOrganizacion == null){
                                infoParaTrazar = "No se ha podido obtener el registro de la organizacion en el nodo :  "+ hostRMIOrganizacionconfigurado + ": Revisar la descripcion de la organizacion" ;
                                trazarError(infoParaTrazar);
                         } else  registroRMIOrganizacionCreado = true;
                         return registroRMIOrganizacion;
                    }
             }else
                {
                infoParaTrazar = "El host RMI configurado o la direccion IP del registro RMI es NULA  "+ hostRMIOrganizacionconfigurado + ": Revisar la descripcion de la organizacion" ;
                trazarError(infoParaTrazar);
//            logger.error("La direccion IP del registro RMI es NULA  "+ hostRMIOrganizacionconfigurado + ": Revisar la descripcion de la organizacion" );
//            trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI",
//						"La direccion IP del registro RMI es NULA  "+ hostRMIOrganizacionconfigurado + "--: Revisar la descripcion de la organizacion" ,
//						InfoTraza.NivelTraza.error));
                }
        } catch (Exception e) {

            logger.error("Error al localizar el registro de la organizacion  en el nodo--: "+ hostRMIOrganizacionconfigurado + " Es posible que no se haya creado: Revisar la descripcion de la organizacion" );
            trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI",
						"La direccion IP del registro RMI es NULA  "+ hostRMIOrganizacionconfigurado + "-- Es posible que no se haya creado: Revisar la descripcion de la organizacion" ,
						InfoTraza.NivelTraza.error));
//

            }
            return null;

    }

     public static void  setRegistroRMIOrganizacion (Registry regOrg){
         registroRMIOrganizacion = regOrg ;
     }

     public static Registry getRegistroRMInodoLocal (){

   // Si no existe se crea el registro en el nodo local y en el puerto por defecto
       if (registroRMILocalCreado ) return registroRMILocal ;

         try {
 //          hostRMI =  getIPLocal();
             inicializar();
             hostRMI = InetAddress.getLocalHost().getHostName();
//             hostRMI = InetAddress.getByName("SERTORIUS").toString();
             puertoRMIOrganizacionconfigurado = configuracionOrganizacion.getPropiedadesGlobales().getProperty("PuertoRMI_Org");
             if (puertoRMIOrganizacionconfigurado != null)puertoRMI = Integer.parseInt (puertoRMIOrganizacionconfigurado);
             registroRMILocal = LocateRegistry.createRegistry(puertoRMI);
             registroRMILocalCreado = true;
            return registroRMILocal;

            } catch (Exception e) {

                logger.error("Error al localizar el registro RMI  en el nodo--: "+ hostRMI + " Es posible que no se haya creado: Revisar la descripcion de la organizacion" );
                trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI",
						"Error al localizar el registro RMI :  "+ hostRMI + "-- Es posible que no se haya creado: Revisar la descripcion de la organizacion" ,
						InfoTraza.NivelTraza.error));
//
                return null;
            }

    }
public static Registry getRegistroRMInodoRemoto (String identHost){

   // Si no existe se crea el registro en el nodo local y en el puerto por defecto
//       if (registroRMILocalCreado ) return registroRMILocal ;

         try {
//             hostRMI =  getIPLocal();
//             hostRMI = InetAddress.getLocalHost().getHostName();
////             hostRMI = InetAddress.getByName("SERTORIUS").toString();
//             puertoRMIOrganizacionconfigurado = configuracionOrganizacion.getPropiedadesGlobales().getProperty("PuertoRMI");
//             if (puertoRMIOrganizacionconfigurado != null)puertoRMI = Integer.parseInt (puertoRMIOrganizacionconfigurado);
//             registroRMILocal = LocateRegistry.createRegistry(puertoRMI);
//             registroRMILocalCreado = true;
            return LocateRegistry.getRegistry(identHost);

            } catch (Exception e) {

                logger.error("Error al localizar el registro de la organizacion  en el nodo--: "+ hostRMI + " Es posible que no se haya creado: Revisar la descripcion de la organizacion" );
                trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI",
						"La direccion IP del registro RMI es NULA  "+ hostRMI + "-- Es posible que no se haya creado: Revisar la descripcion de la organizacion" ,
						InfoTraza.NivelTraza.error));
//
                return null;
            }

    }
  public static boolean addElement2LocalRegRMI(String element, Remote obj) {
//      String registroAqSeAgnade = "registroRMIOrganizacion"  ;

     try {
            
             if (!registroRMILocalCreado){
                 inicializar();
                    }

                registroRMILocal.rebind(element, obj);
                trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI",
						"Se añade al registro de la organizacion  "+ thisHost + "-- el elemento  " + element ,
						InfoTraza.NivelTraza.debug));
            return true;
            }
            catch (Exception e) {
            logger.error("Error al añadir un elemento en el registro local --: "+ thisHost + " Es posible que no se haya creado: Revisar la descripcion de la organizacion" );
            trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI",
						"Error al añadir un elemento en el registro local-- "+ thisHost + "-- Es posible que no se haya creado: Revisar la descripcion de la organizacion" ,
						InfoTraza.NivelTraza.debug));
               return false;
            }
        }
  
public static boolean addElement2RegRMI(String identHost, String element, Remote obj) {
//      String registroAqSeAgnade = "registroRMIOrganizacion"  ;
      try {
            if (identHost !=null  ){
                     Registry identHostRegistry = LocateRegistry.getRegistry(identHost);
                     if (identHostRegistry != null){
                         identHostRegistry.rebind(element, obj);
                        logger.error("Se añade la interfaz : " +element +  " en el registro del  nodo --: "+identHost   );
                        trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI",
						"Se añade la interfaz : " +element +  " en el registro del  nodo --: "+identHost   ,
						InfoTraza.NivelTraza.debug));
                        return true;
                    }else{ // No se ha podido obtener un registro RMI para ese host
                            logger.error("Error al  obtener el RMI registro del host --: "+ identHost + " Es posible que no se haya creado: Revisar la descripcion de la organizacion" );
                            trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI",
						"Error al  obtener el RMI registro del host-- "+ identHost + "-- Es posible que no se haya creado: Revisar la descripcion de la organizacion" ,
						InfoTraza.NivelTraza.debug));

                    }
            }
            return false;
      } catch (Exception e) {
            logger.error("Error al añadir un elemento en el registro RMI del host --: "+ identHost + " Es posible que no se haya creado: Revisar la descripcion de la organizacion" );
            trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI",
						"Error al añadir un elemento en el registro RMI del host -- "+ identHost + "-- Es posible que no se haya creado: Revisar la descripcion de la organizacion" ,
						InfoTraza.NivelTraza.error));
               return false;
            }
        }
    public static boolean addElementRegRMIOrg(String element, Remote obj) {
        // Si el registro RMI esta en un nodo distinto del nodo local se deben tener permisos para hacer el rebind
        // si no se tienen dará una excepcion
        try {
            if (!registroRMIOrganizacionCreado){
                registroRMIOrganizacion = getRegistroRMIOrganizacion();
                if (registroRMIOrganizacion == null){
                    logger.error("No se ha podido obtener  el registro RMI de la organizacion --: "+ hostRMIOrganizacionconfigurado + " Es posible que no se haya creado: Revisar la descripcion de la organizacion" );
                    trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI",
						"No se ha podido obtener  el registro RMI de la organizacion  "+ hostRMIOrganizacionconfigurado + "-- No se puede registrar el elemento  " + element ,
						InfoTraza.NivelTraza.debug));
                     return false;
                    }
              
                }

            registroRMIOrganizacion.rebind(element, obj);
            trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI",
						"Se añade al registro de la organizacion  "+ hostRMIOrganizacionconfigurado + "-- el elemento  " + element ,
						InfoTraza.NivelTraza.debug));
//            registroRMI = LocateRegistry.getRegistry(puertoRMI);
//            registroRMI.rebind(element, obj);
//            System.err.println("Elemento exportado con exito: " + element);

            return true;
        } catch (Exception e) {
            logger.error("Error al añadir un elemento en el registro de la organizacion--: "+ hostRMIOrganizacionconfigurado + " Es posible que no se haya creado: Revisar la descripcion de la organizacion" );
            trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI",
						"Error al añadir un elemento en el registro de la organizacion-- "+ hostRMIOrganizacionconfigurado + "-- Es posible que no se haya creado: Revisar la descripcion de la organizacion" ,
						InfoTraza.NivelTraza.error));
            return false;
        }
    }
   public static boolean addElementRegRMIloc(String element, Remote obj) {
        try {
            registroRMILocal.rebind(element, obj);
            trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI",
						"Se añade al registro Local   "+ hostRMI + "-- el elemento  " + element ,
						InfoTraza.NivelTraza.debug));
//            registroRMI = LocateRegistry.getRegistry(puertoRMI);
//            registroRMI.rebind(element, obj);
//            System.err.println("Elemento exportado con exito: " + element);

            return true;
        } catch (Exception e) {
            logger.error("Error al añadir un elemento en el registro Local--: "+ hostRMI + " Es posible que no se haya creado: Revisar la descripcion de la organizacion" );
            trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI",
						"Error al añadir un elemento en el registro Local -- "+ hostRMI + "-- Es posible que no se haya creado: Revisar la descripcion de la organizacion" ,
						InfoTraza.NivelTraza.error));
            return false;
        }
    }
   
//    public static boolean addItfofElement2RegRMIloc(String element) {
//  // se busca  la interfaz del elemento en el repositoria de interfaces y se añade al registro RMI local      
//        
//    }
     public static boolean deleteElementRegRMIOrg(String element, Remote obj) {
        try {
            registroRMIOrganizacion.unbind(element);
            trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI",
						"Se eleimina del registro de la organizacion  "+ hostRMIOrganizacionconfigurado + "-- el elemento  " + element ,
						InfoTraza.NivelTraza.debug));
//            registroRMI = LocateRegistry.getRegistry(puertoRMI);
//            registroRMI.rebind(element, obj);
//            System.err.println("Elemento exportado con exito: " + element);

            return true;
        } catch (Exception e) {
            logger.error("Error al eliminar un elemento en el registro de la organizacion--: "+ hostRMIOrganizacionconfigurado + " Es posible que no se haya creado: Revisar la descripcion de la organizacion" );
            trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI",
						"Error al eliminar un elemento en el registro de la organizacion-- "+ hostRMIOrganizacionconfigurado + "-- Es posible que no se haya creado: Revisar la descripcion de la organizacion" ,
						InfoTraza.NivelTraza.error));
            return false;
        }
    }
      public static boolean deleteElementRegRMIlocal(String element, Remote obj) {
        try {
            registroRMILocal.unbind(element);
            trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI",
						"Se eleimina del registro Local  "+ hostRMI + "-- el elemento  " + element ,
						InfoTraza.NivelTraza.debug));
//            registroRMI = LocateRegistry.getRegistry(puertoRMI);
//            registroRMI.rebind(element, obj);
//            System.err.println("Elemento exportado con exito: " + element);

            return true;
        } catch (Exception e) {
            logger.error("Error al eliminar un elemento en el registro Local--: "+ hostRMI + " Es posible que no se haya creado: Revisar la descripcion de la organizacion" );
            trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI",
						"Error al eliminar un elemento en el registro Local-- "+ hostRMI + "-- Es posible que no se haya creado: Revisar la descripcion de la organizacion" ,
						InfoTraza.NivelTraza.error));
            return false;
        }
    }
    /* ---------------------------------------------------------------------- */
//    public static boolean export(String element, Remote obj) {
//        try {
//            registroRMI = LocateRegistry.getRegistry(puertoRMI);
//            registroRMI.rebind(element, obj);
//            System.err.println("Elemento exportado con exito: " + element);
////            trayIcon.getPopupMenu().add(new MenuItem(element));
////            trayIcon.getPopupMenu().add(new MenuItemIcaro(element));
////            updateMenu();
//            return true;
//        } catch (Exception e) {
//            System.err.println("No se ha podido exportar: " + element);
//            System.err.println(e.getMessage());
//            return false;
//        }
//    }
    /* ---------------------------------------------------------------------- */
//    public static boolean fire(String element){
//        try{
//            registroRMI.unbind(element);
//            return  true;
//        }catch(Exception e){
//            return false;
//        }
//    }
    /* ---------------------------------------------------------------------- */
    public static boolean vaciarRegistroRMI (Registry regtr) {
        if (regtr == null) return true;
//        ItfUsoRecursoTrazas trazas = Directorio.getRecursoTrazas();
             
//            NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ = repositorioInterfaces;
        try {
             trazas.aceptaNuevaTraza(new InfoTraza ("AdaptadorRegRMI", "Vaciando registro RMI", NivelTraza.info));
            String [] listaRemotos = regtr.list();
            for (String obj : listaRemotos) regtr.unbind(obj);
            return true;
        } catch (Exception e) {
             trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI", "Fallo al vaciar registro RMI", NivelTraza.error));
            return false;
        }
    }

    public static InterfazUsoAgente getItfAgente(String nombreAgente) throws java.rmi.RemoteException {

        // Se busca en el Registro local primero y si no esta se busca en el registro RMI de la organización
        String regbusqueda = null ;
//        ItfUsoAgenteReactivo agenteRemoto = null;
//        ItfUsoRecursoTrazas trazas = Directorio.getRecursoTrazas();
        try {
            if (registroRMILocal != null){
                    regbusqueda = "registroRMILocal";
               return (ItfUsoAgenteReactivo )  registroRMILocal.lookup(nombreAgente);
            }else
                if (registroRMIOrganizacion != null){
                     regbusqueda = "registroRMIOrganizacion";
                    return (ItfUsoAgenteReactivo )  registroRMIOrganizacion.lookup(nombreAgente);
                    }else{
                        // los dos registros son null No estan definidos
                    logger.error("Error Los registros local y remoto son null:  No se puede reallizar la busqueda : Revisar la descripcion de la organizacion" );
                    trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI",
						"Error Los registros local y remoto son null:  No se puede reallizar la busqueda : Revisar la descripcion de la organizacion" ,
						InfoTraza.NivelTraza.error));
                }
                } catch (Exception e) {
                    logger.error("Error al acceder al Registro --: "+ regbusqueda + " Es posible que exista : Revisar la descripcion de la organizacion" );
                     trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI",
						"Error al acceder al Registro ---- "+ regbusqueda + "-- Es posible que no se haya creado: Revisar la descripcion de la organizacion" ,
						InfoTraza.NivelTraza.error));
        }
        return null;
    }

    public static Remote getItfRecursoRemoto(String identRecurso, String tipoInterfaz) throws java.rmi.RemoteException {

        // Se busca en el Registro local primero y si no esta se busca en el registro del nodo del recurso
        String regbusqueda = null ;

        if (!(tipoInterfaz.equalsIgnoreCase(NombresPredefinidos.ITF_GESTION))&&!(tipoInterfaz.equalsIgnoreCase(NombresPredefinidos.ITF_USO))){
            logger.error("El tipo de interfaz no es correcto:  No se puede realizar la busqueda : Revisar el tipo de interfaz" );
                    trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI",
						"El tipo de interfaz no es correcto:  No se puede realizar la busqueda : Revisar el tipo de interfaz"  ,
						InfoTraza.NivelTraza.error));
                    return null;
        }
//        ItfUsoAgenteReactivo agenteRemoto = null;
//        ItfUsoRecursoTrazas trazas = Directorio.getRecursoTrazas();
        Remote resultadoBusqueda = null;
        String identObjetoBusqueda = tipoInterfaz+identRecurso;
        String registroRMI;

     try {
          if (!registroRMILocalCreado) inicializar ();


      // Se busca en el registro local
                resultadoBusqueda=getRemoteEntityFromLocalRegistryRMI(identObjetoBusqueda);
//      Si no lo encontramos se busca en el nodo de la entidad
        if (resultadoBusqueda == null){// buscamos en el nodo del agente
            String identHostAgente= configuracionOrganizacion.getHostRecurso(identRecurso);
            if (identHostAgente!= null)
                resultadoBusqueda= getRemoteEntityFromHost(identHostAgente, puertoRMI, identObjetoBusqueda);
            
            else // lo buscamos en el registro RMI de la organización
                if (!thisHost.equalsIgnoreCase(hostRMIOrganizacionconfigurado))
                                resultadoBusqueda = getRemoteEntityFromRegistryRMIOg(identObjetoBusqueda);
           }
        if ( resultadoBusqueda != null ){
                 addElement2LocalRegRMI(identObjetoBusqueda, resultadoBusqueda);

         } else
                   trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI", "No consigo encontrar la interfaz  del recurso remoto : "+
                        identRecurso , NivelTraza.error));


        }catch(Exception ex) {
            System.err.println("Fallo busqueda Recurso Remoto\n"+ ex.getMessage());

            trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI", "No consigo encontrar encontrar la interfaz  del recurso remoto :  "+
                        identRecurso , NivelTraza.error));
//            Logger.getLogger(ComunicacionAgentes.class.getName()).log(Level.SEVERE, null, ex);
            return null;
         }
       return resultadoBusqueda;
    }

    public static Remote getItfAgenteRemoto(String identAgente, String tipoInterfaz) throws java.rmi.RemoteException {

        // Se busca en el Registro local primero y si no esta se busca en el registro del nodo del recurso
        String regbusqueda = null ;

        if (!(tipoInterfaz.equalsIgnoreCase(NombresPredefinidos.ITF_GESTION))&&!(tipoInterfaz.equalsIgnoreCase(NombresPredefinidos.ITF_USO))){
            logger.error("El tipo de interfaz no es correcto:  No se puede realizar la busqueda : Revisar el tipo de interfaz" );
                    trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI",
						"El tipo de interfaz no es correcto:  No se puede realizar la busqueda : Revisar el tipo de interfaz"  ,
						InfoTraza.NivelTraza.error));
                    return null;
        }
//        ItfUsoAgenteReactivo agenteRemoto = null;
//        ItfUsoRecursoTrazas trazas = Directorio.getRecursoTrazas();
        Remote resultadoBusqueda = null;
        String identObjetoBusqueda = tipoInterfaz+identAgente;
        String registroRMI;
     try {
        if (!registroRMILocalCreado) inicializar ();    
      // Se busca en le registro local
          resultadoBusqueda=getRemoteEntityFromLocalRegistryRMI(identObjetoBusqueda);
//      Si no lo encontramos se busca en el nodo de la entidad
        if (resultadoBusqueda == null){// buscamos en el nodo del agente
            String identHostAgente= configuracionOrganizacion.getHostAgente(identAgente);
            if (identHostAgente!= null)
                resultadoBusqueda= getRemoteEntityFromHost(identHostAgente, puertoRMI, identObjetoBusqueda);
            
            else // lo buscamos en el registro RMI de la organización
                if (!thisHost.equalsIgnoreCase(hostRMIOrganizacionconfigurado))
                                resultadoBusqueda = getRemoteEntityFromRegistryRMIOg(identObjetoBusqueda);
              }
        if ( resultadoBusqueda != null ){
                 addElement2LocalRegRMI(identObjetoBusqueda, resultadoBusqueda);        
         } else
                   trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI", "No consigo encontrar la interfaz  del agente remoto : "+
                        identAgente , NivelTraza.debug));
       }catch(Exception ex) {
            System.err.println("Fallo buscaAgenteRemoto\n"+ ex.getMessage());

            trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI", "No consigo encontrar encontrar la interfaz  del agente remoto :  "+
                        identAgente , NivelTraza.error));
//            Logger.getLogger(ComunicacionAgentes.class.getName()).log(Level.SEVERE, null, ex);
            return null;
         }
       return resultadoBusqueda;
    }
public static Remote getItfComponenteRemoto(String identComponente, String tipoInterfaz) throws java.rmi.RemoteException {

        // Se busca en el Registro local primero y si no esta se busca en el registro del nodo del recurso
        String regbusqueda = null ;

        if (!(tipoInterfaz.equalsIgnoreCase(NombresPredefinidos.ITF_GESTION))&&!(tipoInterfaz.equalsIgnoreCase(NombresPredefinidos.ITF_USO))){
            logger.error("El tipo de interfaz no es correcto:  No se puede realizar la busqueda : Revisar el tipo de interfaz" );
                    trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI",
						"El tipo de interfaz no es correcto:  No se puede realizar la busqueda : Revisar el tipo de interfaz"  ,
						InfoTraza.NivelTraza.error));
                    return null;
        }
//        ItfUsoAgenteReactivo agenteRemoto = null;
//        ItfUsoRecursoTrazas trazas = Directorio.getRecursoTrazas();
        Remote resultadoBusqueda = null;
        String identObjetoBusqueda = tipoInterfaz+identComponente;
        String registroRMI;
     try {
        if (!registroRMILocalCreado) inicializar ();    
      // Se busca en le registro local
          resultadoBusqueda=getRemoteEntityFromLocalRegistryRMI(identObjetoBusqueda);
//      Si no lo encontramos se busca en el nodo de la entidad
        if (resultadoBusqueda == null){// buscamos en el nodo del agente
            String identHostComponente= configuracionOrganizacion.getHostComponente(identComponente);
            if (identHostComponente!= null)
                resultadoBusqueda= getRemoteEntityFromHost(identHostComponente, puertoRMI, identObjetoBusqueda);
            
            else // lo buscamos en el registro RMI de la organización
                if (!thisHost.equalsIgnoreCase(hostRMIOrganizacionconfigurado))
                                resultadoBusqueda = getRemoteEntityFromRegistryRMIOg(identObjetoBusqueda);
              }
        if ( resultadoBusqueda != null ){
                 addElement2LocalRegRMI(identObjetoBusqueda, resultadoBusqueda);        
         } else
                   trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI", "No consigo encontrar la interfaz  del agente remoto : "+
                        identComponente , NivelTraza.debug));
       }catch(Exception ex) {
            System.err.println("Fallo buscaAgenteRemoto\n"+ ex.getMessage());

            trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI", "No consigo encontrar encontrar la interfaz  del agente remoto :  "+
                        identComponente , NivelTraza.error));
//            Logger.getLogger(ComunicacionAgentes.class.getName()).log(Level.SEVERE, null, ex);
            return null;
         }
       return resultadoBusqueda;
    }
    /* ---------------------------------------------------------------------- */
   
    /* ********************************************************************** */
    public static ItfUsoAgenteReactivo getItfAgteReactRemoto(String host, int puerto, String nombreAgente) throws java.rmi.RemoteException {
        Registry regCliente = LocateRegistry.getRegistry(host, puerto);
        ItfUsoAgenteReactivo agenteRemoto = null;
//        ItfUsoRecursoTrazas trazas = Directorio.getRecursoTrazas();
        try {            
            if (regCliente == null) {
                System.err.println("buscarAgenteRemoto regCliente == null");
                if (trazas != null)
                trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI", "No consigo encontrar al agente: "+
                        nombreAgente + " en Host :" + host + " puerto:" + puerto, NivelTraza.error));
            }
            agenteRemoto = (ItfUsoAgenteReactivo) regCliente.lookup(nombreAgente);
            return agenteRemoto;
        } catch (Exception ex) {
            System.err.println("Fallo buscaAgenteRemoto\n"+ ex.getMessage());
            if (trazas != null)
            trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI", "No consigo encontrar al agente: "+
                        nombreAgente + " en Host :" + host + " puerto:" + puerto, NivelTraza.debug));
//            Logger.getLogger(ComunicacionAgentes.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

     public static Remote getRemoteEntityFromHost(String host, int puerto, String identEntity) throws java.rmi.RemoteException {
        Registry regCliente = LocateRegistry.getRegistry(host, puerto);
        Object remoteEntity = null;
//        ItfUsoRecursoTrazas trazas = Directorio.getRecursoTrazas();
        try {
            if (regCliente == null) {
//                System.err.println("buscarAgenteRemoto regCliente == null");
//                if (trazas != null)
                trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI", " No se puede obtener la entidad : "+
                        identEntity + " No se consigue encontrar el  registro RMI del Host :" + host + " puerto:" + puerto, NivelTraza.debug));
            }
            return  regCliente.lookup(identEntity);

        } catch (Exception ex) {
            System.err.println("Fallo buscaAgenteRemoto\n"+ ex.getMessage());
            trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI", " No se puede obtener la entidad : "+
                      identEntity + " No se consigue encontrar el  registro RMI del Host :" + host + " puerto:"+ puerto , NivelTraza.debug));
//            Logger.getLogger(ComunicacionAgentes.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    /* ********************************************************************** */
    public static ItfUsoAgenteReactivo getItfAgteReactRemoto(String host, String nombreAgente) throws RemoteException, NotBoundException {
//        Registry regCliente = LocateRegistry.getRegistry(ip, 1099);
//        ItfUsoAgenteReactivo agenteRemoto = null;
//        if (regCliente == null) return null;
//        else {
//            agenteRemoto = (ItfUsoAgenteReactivo) regCliente.lookup(nombreAgente);
//            return agenteRemoto;
//        }
        return getItfAgteReactRemoto (host, 1099, nombreAgente);
    }

//    public static ItfUsoAgenteReactivo getItfUsoAgteReactRemoto( String nombreAgente) {
//
//     try {
//       if ( registroRMIOrganizacion != null )
//                    return (ItfUsoAgenteReactivo)registroRMIOrganizacion.lookup(NombresPredefinidos.ITF_USO+nombreAgente);
//           registroRMIOrganizacion = getRegistroRMIOrganizacion();
//        if ( registroRMIOrganizacion != null )
//                    return (ItfUsoAgenteReactivo)registroRMIOrganizacion.lookup(NombresPredefinidos.ITF_USO+nombreAgente);
//        else if ( registroRMILocal != null )
//                 return (ItfUsoAgenteReactivo)registroRMILocal.lookup(NombresPredefinidos.ITF_USO+nombreAgente);
//                  registroRMILocal = getRegistroRMInodoLocal();
//                  if ( registroRMILocal != null )
//                    return (ItfUsoAgenteReactivo)registroRMILocal.lookup(NombresPredefinidos.ITF_USO+nombreAgente);
//                   trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI", "No consigo encontrar la interfaz de uso del agente remoto : "+
//                        nombreAgente , NivelTraza.error));
//             }catch(Exception ex) {
//            System.err.println("Fallo buscaAgenteRemoto\n"+ ex.getMessage());
//
//            trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI", "No consigo encontrar encontrar la interfaz de uso del agente remoto :  "+
//                        nombreAgente , NivelTraza.error));
////            Logger.getLogger(ComunicacionAgentes.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//         }
//        return null;
//    }

    public static ItfUsoAgenteReactivo getItfUsoAgteReactRemoto( String nombreAgente) {
    Remote resultadoBusqueda;
    String identObjetoBusqueda = NombresPredefinidos.ITF_USO+nombreAgente;
    String registroRMI;

     try {
       String identHostAgente= configuracionOrganizacion.getDescInstanciaAgenteAplicacion(nombreAgente).getNodo().getNombreUso();
       if (identHostAgente.equals(thisHost)){
           resultadoBusqueda=getRemoteEntityFromLocalRegistryRMI(identObjetoBusqueda);
           return (ItfUsoAgenteReactivo)resultadoBusqueda;
        }
         else {
                 resultadoBusqueda= getRemoteEntityFromHost(identHostAgente, puertoRMI, identObjetoBusqueda);
                if ( resultadoBusqueda != null ){
                    addElement2LocalRegRMI(identObjetoBusqueda, resultadoBusqueda);
                    return (ItfUsoAgenteReactivo)resultadoBusqueda;
                    }
                }

          trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI", "No consigo encontrar la interfaz de uso del agente remoto : "+
                        nombreAgente , NivelTraza.error));
         }catch(Exception ex) {
            System.err.println("Fallo buscaAgenteRemoto\n"+ ex.getMessage());

            trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI", "No consigo encontrar encontrar la interfaz de uso del agente remoto :  "+
                        nombreAgente , NivelTraza.debug));
//            Logger.getLogger(ComunicacionAgentes.class.getName()).log(Level.SEVERE, null, ex);
            return null;
         }
        return null;
    }

    public static Remote getRemoteEntityFromRegistryRMIOg(String identEntity){

        try {
       if ( registroRMIOrganizacion != null )
                     return registroRMIOrganizacion.lookup(identEntity);
         else registroRMIOrganizacion = getRegistroRMIOrganizacion();
        if ( registroRMIOrganizacion != null )
                    return registroRMIOrganizacion.lookup(identEntity);

                   trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI", "No consigo encontrar el registro de la organizacion : "+
                        identEntity , NivelTraza.debug));
             }catch(Exception ex) {
            System.err.println("Fallo buscaAgenteRemoto\n"+ ex.getMessage());

            trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI", "Error en la busqueda en el registro de la organizacion. No puedo encontrar  :  "+
                        identEntity + "\n Revisar la descripcion de la organizacion", NivelTraza.error));
//            Logger.getLogger(ComunicacionAgentes.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        return null;
    }
    public static Remote getRemoteEntityFromLocalRegistryRMI(String identEntity){

        try {
       if ( registroRMILocal != null )
                     return registroRMILocal.lookup(identEntity);
         else registroRMILocal = getRegistroRMInodoLocal();
        if ( registroRMILocal != null )
                    return registroRMILocal.lookup(identEntity);

                   trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI", "No consigo encontrar el registro Local : "+
                        identEntity , NivelTraza.debug));
             }catch(Exception ex) {
            System.err.println("Fallo buscaAgenteRemoto\n"+ ex.getMessage());

            trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI", "Error en la busqueda en el registro Local. No puedo encontrar  :  "+
                        identEntity , NivelTraza.debug));
//            Logger.getLogger(ComunicacionAgentes.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        return null;
    }



     public static InterfazGestion getItfGestionAgteReactRemoto( String nombreAgente) {
      try {
         if ( registroRMIOrganizacion != null )
                    return (InterfazGestion)registroRMIOrganizacion.lookup(NombresPredefinidos.ITF_GESTION+nombreAgente);
           registroRMIOrganizacion = getRegistroRMIOrganizacion();
        if ( registroRMIOrganizacion != null )
                    return (InterfazGestion)registroRMIOrganizacion.lookup(NombresPredefinidos.ITF_GESTION+nombreAgente);
        else if ( registroRMILocal != null )
                 return (InterfazGestion)registroRMILocal.lookup(NombresPredefinidos.ITF_GESTION+nombreAgente);
                  registroRMILocal = getRegistroRMInodoLocal();
                  if ( registroRMILocal != null )
                   return (InterfazGestion)registroRMILocal.lookup(NombresPredefinidos.ITF_GESTION+nombreAgente);
                   trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI", "No consigo encontrar la interfaz de gestion del agente remoto : "+
                        nombreAgente , NivelTraza.error));

         }catch(Exception ex) {
            System.err.println("Fallo buscaAgenteRemoto\n"+ ex.getMessage());

            trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI", "No consigo encontrar encontrar la interfaz de gestion del agente remoto :  "+
                        nombreAgente , NivelTraza.debug));
//            Logger.getLogger(ComunicacionAgentes.class.getName()).log(Level.SEVERE, null, ex);
            return null;
         }
         return null;

    }
      public static InterfazGestion getItfGestionEntidadRemota(String identHost, String identEntity) throws RemoteException, NotBoundException {
            Registry regCliente = LocateRegistry.getRegistry(identHost);
            InterfazGestion itfGesagenteRemoto = null;
            if (regCliente == null) return null;
             Remote  objInRegistry =  regCliente.lookup(NombresPredefinidos.ITF_GESTION+identEntity);
             if (objInRegistry != null) itfGesagenteRemoto = (InterfazGestion) objInRegistry;
            return itfGesagenteRemoto;

//        return (InterfazGestion)regCliente.lookup(NombresPredefinidos.ITF_GESTION+identEntity);
    }
      public static Remote getItfEntidadRemota(String identHost, String identEntity) throws RemoteException, NotBoundException {
            Registry regCliente = LocateRegistry.getRegistry(identHost);
         //   InterfazGestion itfGesagenteRemoto = null;
            if (regCliente == null) return null;
             return   regCliente.lookup(NombresPredefinidos.ITF_GESTION+identEntity);
  //           if (objInRegistry != null) itfGesagenteRemoto = (InterfazGestion) objInRegistry;
   //         return itfGesagenteRemoto;

//        return (InterfazGestion)regCliente.lookup(NombresPredefinidos.ITF_GESTION+identEntity);
    }

       public static InterfazUsoAgente getItfUsoAgenteRemoto(String identHost, String nombreAgente) throws RemoteException, NotBoundException {
            Registry regCliente = LocateRegistry.getRegistry(identHost);
            InterfazGestion agenteRemoto = null;
            if (regCliente == null) return null;
         else
//            agenteRemoto = (ItfUsoAgenteReactivo) regCliente.lookup(nombreAgente);
//            return agenteRemoto;
//        }
        return (InterfazUsoAgente)regCliente.lookup(NombresPredefinidos.ITF_USO+nombreAgente);
    }
       public static Remote getItfUsoRecursoRemoto(String identHost, String nombreRecurso) throws RemoteException, NotBoundException {
            Registry regCliente = LocateRegistry.getRegistry(identHost);
            InterfazGestion agenteRemoto = null;
            if (regCliente == null) return null;
         else
//            agenteRemoto = (ItfUsoAgenteReactivo) regCliente.lookup(nombreAgente);
//            return agenteRemoto;
//        }
        return regCliente.lookup(NombresPredefinidos.ITF_USO+nombreRecurso);
    }
public static  Boolean esAgenteRemoto ( String identAgente){
        try {
            String nodoAgente;
            if (configuracionOrganizacion == null) {
                NombresPredefinidos.RECURSO_TRAZAS_OBJ.trazar("AdaptadorRMI", "El recurso de configuracion no ha sido creado", InfoTraza.NivelTraza.debug );
                return false;
            }else{
                nodoAgente = configuracionOrganizacion.getDescInstanciaAgenteAplicacion(identAgente).getNodo().getNombreUso();
                if (nodoAgente.equals(thisHost)) return false;
                 else return true;
            }
        } catch (ExcepcionEnComponente ex) {
            java.util.logging.Logger.getLogger(AdaptadorRegRMI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            java.util.logging.Logger.getLogger(AdaptadorRegRMI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
         return false;
}
public static Boolean esRecursoRemoto ( String identRecurso){
    String nodoRecurso;
        try {
            if (configuracionOrganizacion == null) {
                NombresPredefinidos.RECURSO_TRAZAS_OBJ.trazar("AdaptadorRMI", "El recurso de configuracion no ha sido creado", InfoTraza.NivelTraza.debug );
                return false;
            }else{
            nodoRecurso = configuracionOrganizacion.getDescInstanciaRecursoAplicacion(identRecurso).getNodo().getNombreUso();
            if (nodoRecurso.equals(thisHost))return false;
            else return true;
            }
         } catch (ExcepcionEnComponente ex) {
            java.util.logging.Logger.getLogger(AdaptadorRegRMI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            java.util.logging.Logger.getLogger(AdaptadorRegRMI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
  return false;
}

    /* ********************************************************************** */

//    public static boolean enviarInputRemoto(String input, String nombreRecurso, ItfUsoAgenteReactivo remoto) {
//        try {
//            remoto.aceptaEvento(new EventoRecAgte(input, nombreRecurso, "peticion remota:" + nombreRecurso));
//        } catch (Exception ex) {
//            Logger.getLogger(ComunicacionAgentes.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return true;
//    }
//    /* ********************************************************************** */
//
//    public static boolean enviarInputRemoto(String input, Object param, String nombreRecurso, ItfUsoAgenteReactivo remoto) {
//        try {
//            remoto.aceptaEvento(new EventoRecAgte(input, param, nombreRecurso, "peticion remota:" + nombreRecurso));
//        } catch (Exception ex) {
//            Logger.getLogger(ComunicacionAgentes.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return true;
//    }
//    /* ********************************************************************** */
//
//    public static boolean enviarInputRemoto(String input, Object[] param, String nombreRecurso, ItfUsoAgenteReactivo remoto) {
//        try {
//            remoto.aceptaEvento(new EventoRecAgte(input, param, nombreRecurso, "peticion remota:" + nombreRecurso));
//        } catch (Exception ex) {
//            Logger.getLogger(ComunicacionAgentes.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return true;
//    }
    /* ********************************************************************** */
    // </editor-fold>
    /* ********************************************************************** */

    public static String getIPLocal () {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        }
        catch (Exception e) {
            return "ERROR";
        }
    }

 public static Nodo getDescrNodo (String IdentHost) {


     try {
          Nodo descNodo = new Nodo() ;
            descNodo.setNombreCompletoHost( InetAddress.getByName(IdentHost).getHostAddress());
            descNodo.setNombreUso(IdentHost);
            return descNodo;
        }
        catch (Exception e) {
            return null;
        }
    }

    public static void trazarError (String infoTraza) {

         logger.error( infoTraza);
                    trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI",
						infoTraza ,
						InfoTraza.NivelTraza.error));
    }
    public static void trazarInfo (String infoTraza) {

         logger.info( infoTraza);
                    trazas.aceptaNuevaTraza(new InfoTraza("AdaptadorRegRMI",
						infoTraza ,
						InfoTraza.NivelTraza.info));
    }

    /* Auxiliares */
   
    
}
