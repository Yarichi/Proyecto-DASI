package icaro.infraestructura.recursosOrganizacion.comunicacionesOrganizacion.imp.RMI;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Alejandro Fernandez
 * @author Andres Picazo
 * @author Arturo Mazon
 */
public class ControlRMI {
    /* ---------------------------------------------------------------------- */
    private static Registry registroRMI = null;
    private static int puertoRMI = 1099;
    public static ItfUsoRecursoTrazas trazas;
    /* ---------------------------------------------------------------------- */
    public static int setPuertoRMI(int puerto) {
        puertoRMI = puerto;
        return puertoRMI;
    }
    /* ---------------------------------------------------------------------- */
    public static boolean startRMI() {
        try {
            registroRMI = LocateRegistry.createRegistry(puertoRMI);
            System.err.println("Registro listo en " + puertoRMI);
  
            return true;
        } catch (Exception e) {
            System.err.println("No se ha podido crear el registro RMI");
            try {
                registroRMI = LocateRegistry.getRegistry(puertoRMI);
                System.err.println ("El registro ya existia (RMI listo)");
 
                return true;
            }
            catch (Exception ex) {
                System.err.println ("RMI no disponible!!\n" + ex.getMessage());
                return false;
            }
        }
    }
    /* ---------------------------------------------------------------------- */
    public static boolean export(String element, Remote obj) {
        try {
            registroRMI = LocateRegistry.getRegistry(puertoRMI);
            registroRMI.rebind(element, obj);
            System.err.println("Elemento exportado con exito: " + element);            
//            trayIcon.getPopupMenu().add(new MenuItem(element));
//            trayIcon.getPopupMenu().add(new MenuItemIcaro(element));
//            updateMenu();
            return true;
        } catch (Exception e) {
            System.err.println("No se ha podido exportar: " + element);
            System.err.println(e.getMessage());
            return false;
        }
    }
    /* ---------------------------------------------------------------------- */
    public static boolean fire(String element){
        try{
            registroRMI.unbind(element);
            return  true;
        }catch(Exception e){
            return false;
        }
    }
    /* ---------------------------------------------------------------------- */
    public static boolean fireAll () {
        if (registroRMI == null) return true;
//        ItfUsoRecursoTrazas trazas = Directorio.getRecursoTrazas();
             trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ ;
//            NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ = repositorioInterfaces;
        try {
            if (trazas != null) trazas.aceptaNuevaTraza(new InfoTraza ("ControlRMI", "Vaciando registro RMI", NivelTraza.info));
            String [] listaRemotos = registroRMI.list();
            for (String obj : listaRemotos) registroRMI.unbind(obj);
            return true;
        } catch (Exception e) {
            if (trazas != null) trazas.aceptaNuevaTraza(new InfoTraza("ControlRMI", "Fallo al vaciar registro RMI", NivelTraza.error));
            return false;
        }
    }
    /* ---------------------------------------------------------------------- */
    
   
    /* ********************************************************************** */
    /* ********************************************************************** */
    public static ItfUsoAgenteReactivo buscarAgenteRemoto(String ip, int puerto, String nombreAgente) throws java.rmi.RemoteException {
        Registry regCliente = LocateRegistry.getRegistry(ip, puerto);
        ItfUsoAgenteReactivo agenteRemoto = null;
//        ItfUsoRecursoTrazas trazas = Directorio.getRecursoTrazas();
        try {            
            if (regCliente == null) {
                System.err.println("buscarAgenteRemoto regCliente == null");
                if (trazas != null)
                trazas.aceptaNuevaTraza(new InfoTraza("ControlRMI (null)", "No consigo encontrar al agente: "+
                        nombreAgente + " en ip:" + ip + " puerto:" + puerto, NivelTraza.error));
            }
            agenteRemoto = (ItfUsoAgenteReactivo) regCliente.lookup(nombreAgente);
            return agenteRemoto;
        } catch (Exception ex) {
            System.err.println("Fallo buscaAgenteRemoto\n"+ ex.getMessage());
            if (trazas != null)
            trazas.aceptaNuevaTraza(new InfoTraza("ControlRMI (Excepcion)", "No consigo encontrar al agente: "+
                        nombreAgente + " en ip:" + ip + " puerto:" + puerto, NivelTraza.error));
//            Logger.getLogger(ComunicacionAgentes.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    /* ********************************************************************** */
    public static ItfUsoAgenteReactivo buscarAgenteRemoto(String ip, String nombreAgente) throws RemoteException, NotBoundException {
//        Registry regCliente = LocateRegistry.getRegistry(ip, 1099);
//        ItfUsoAgenteReactivo agenteRemoto = null;
//        if (regCliente == null) return null;
//        else {
//            agenteRemoto = (ItfUsoAgenteReactivo) regCliente.lookup(nombreAgente);
//            return agenteRemoto;
//        }
        return buscarAgenteRemoto (ip, 1099, nombreAgente);
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

    /* Auxiliares */
   
   
   
}
