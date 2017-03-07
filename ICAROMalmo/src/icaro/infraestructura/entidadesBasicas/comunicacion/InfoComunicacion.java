/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.entidadesBasicas.comunicacion;

/**
 *
 * @author FGarijo
 * En esta clase encapsulamos la información necesaria para que el control del agente realice acciones de comunicación
 * esto incluye enviar mensajes a un agente local o remoto y/o enviar mensajes a grupos de agentes
 * Esta clase permite tambien almacenar información de los agentes con los que un agente se comunica
 */
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.ShowMessage;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.RepositorioInterfacesImpLocal;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InfoComunicacion {
   private String identAgenteEmisor;
   private  ItfUsoRepositorioInterfaces itfUsoRepositorio ;
   private  ItfUsoRecursoTrazas trazas ;
   private HashMap InterfacesAgentes ;
   private ItfUsoAgenteReactivo itfUsoAgenteReceptor = null;
   private Registry regCliente = null;


   public InfoComunicacion (String identAgente){
        identAgenteEmisor = identAgente;
        itfUsoRepositorio= NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
        trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        InterfacesAgentes = new HashMap();

    }
    public  Object obtenerInterfaz (String IdentAgenteReceptor) {

        try {
            /* Busco en el repositorio el interfaz del agente que necesito */
            itfUsoAgenteReceptor = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.ITF_USO + identAgenteEmisor);
            /* Envio el input si puedo */

                  itfUsoAgenteReceptor = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
                    (NombresPredefinidos.ITF_USO+IdentAgenteReceptor);
                    return itfUsoAgenteReceptor;

               }
               catch (Exception ex) {
    			Logger.getLogger(InfoComunicacion.class.getName()).log(Level.SEVERE, null, ex);
    			trazas.aceptaNuevaTraza(new InfoTraza(identAgenteEmisor,
				"Ha habido un problema enviar al buscar la interfaz del  agente "+IdentAgenteReceptor,
					  InfoTraza.NivelTraza.error));
               }
         return false;
    }

     public  ItfUsoAgenteReactivo obtenerInterfazDeMisInterfacesGuardadas (String IdentAgenteReceptor) {

        return  itfUsoAgenteReceptor = (ItfUsoAgenteReactivo) InterfacesAgentes.get(IdentAgenteReceptor);
                    
     }
     public  ItfUsoAgenteReactivo obtenerInterfazDelRepositorio (String IdentAgenteReceptor) {

         try {
            /* Busco en el repositorio el interfaz del agente que necesito */
                itfUsoAgenteReceptor = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
                    (NombresPredefinidos.ITF_USO+IdentAgenteReceptor);
                    return itfUsoAgenteReceptor;
               }
               catch (Exception ex) {
    			Logger.getLogger(InfoComunicacion.class.getName()).log(Level.SEVERE, null, ex);
    			trazas.aceptaNuevaTraza(new InfoTraza(identAgenteEmisor,
				"Ha habido un problema enviar al buscar la interfaz del  agente "+IdentAgenteReceptor,
					  InfoTraza.NivelTraza.error));
               }
            return null;
     }
     
      public  ItfUsoAgenteReactivo buscarAgenteRemoto ( String IdentAgenteReceptor) throws java.rmi.RemoteException {
 //       Obtenemos información sobre el nodo donde se encuentra el agente

//          Registry regCliente = LocateRegistry.getRegistry(ip, puerto);
        ItfUsoAgenteReactivo agenteRemoto = null;
        try {
             if (regCliente == null) {
             trazas.aceptaNuevaTraza(new InfoTraza(identAgenteEmisor,
				"El Registro RMI es null - No se ha activado  No se puede buscar la interfaz remota del agente  "+IdentAgenteReceptor,
                                InfoTraza.NivelTraza.error));
             }
            /* Busco en el repositorio el interfaz del agente que necesito */
                itfUsoAgenteReceptor = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
                    (NombresPredefinidos.ITF_USO+IdentAgenteReceptor);
                    return itfUsoAgenteReceptor;
               }
               catch (Exception ex) {
    			Logger.getLogger(InfoComunicacion.class.getName()).log(Level.SEVERE, null, ex);
    			trazas.aceptaNuevaTraza(new InfoTraza(identAgenteEmisor,
				"Ha habido un problema enviar al buscar la interfaz del  agente "+IdentAgenteReceptor,
					  InfoTraza.NivelTraza.error));
               }
            return null;
//        try {
//            if (regCliente == null) ShowMessage.Info("buscarAgenteRemoto", "regCliente == null");
////            ShowMessage.Info("BuscarAgenteRemoto", nombreAgente);
//            agenteRemoto = (ItfUsoAgenteReactivo) regCliente.lookup (IdentAgenteReceptor);
//            return agenteRemoto;
//        } catch (Exception ex) {
//            ShowMessage.Warning("Fallo buscaAgenteRemoto", ex.getMessage());
//            Logger.getLogger(ComunicacionAgentes.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }
    }


     public void mandarEventoAAgenteId(String input, Object infoComplementaria,String IdentAgenteReceptor){

            // Este método crea un evento con la información de entrada y se le envía al agente REACTIVO que se indique por medio de
            // la  interfaz de uso
            EventoRecAgte eventoaEnviar = null;
            ItfUsoAgenteReactivo itfUsoAgenteReceptor = null;

            // Se verifica que la interfaz del agente no es vacia

            try {
                  itfUsoAgenteReceptor = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
                    (NombresPredefinidos.ITF_USO+IdentAgenteReceptor);
               }
               catch (Exception ex) {
    			Logger.getLogger(InfoComunicacion.class.getName()).log(Level.SEVERE, null, ex);
    			trazas.aceptaNuevaTraza(new InfoTraza(identAgenteEmisor,
				"Ha habido un problema enviar el evento con informacion "+ input+"al   agente "+IdentAgenteReceptor,
					  InfoTraza.NivelTraza.error));
               }


            // En primer lugar se crea el evento con  la informacion de entrada
            if (infoComplementaria == null){
                eventoaEnviar = new EventoRecAgte(input, identAgenteEmisor, IdentAgenteReceptor);
             }
            else{eventoaEnviar = new EventoRecAgte(input,infoComplementaria, identAgenteEmisor, IdentAgenteReceptor);}
             // Obtener la interfaz de uso del agente reactivo con el que se quiere comunicar
             try {
			itfUsoAgenteReceptor.aceptaEvento(eventoaEnviar);
            }catch (Exception ex) {
		Logger.getLogger(InfoComunicacion.class.getName()).log(Level.SEVERE, null, ex);
		trazas.aceptaNuevaTraza(new InfoTraza(identAgenteEmisor,
			  "Ha habido un problema enviar el evento con informacion "+ input+"al   agente "+IdentAgenteReceptor,
                        	  InfoTraza.NivelTraza.error));
            }

    }
 /*       public void mandaMensajeAAgenteItf(String input, Object infoComplementaria,ItfUsoAgenteReactivo itfUsoAgenteReceptor){
            // Este método crea un evento con la información de entrada y se le envía al agente REACTIVO que se indique por medio de
            // la  interfaz de uso
            EventoRecAgte eventoaEnviar = null;

            if (itfUsoAgenteReceptor == null){
                trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
			  "La interfaz a la que se quiere enviar el mensaje es nula ",
                        	  InfoTraza.NivelTraza.error));
                return;
            }
            // En primer lugar se crea el evento con  la informacion de entrada
            if (infoComplementaria == null){
                eventoaEnviar = new EventoRecAgte(input, nombreAgente, itfUsoAgenteReceptor.toString());
             }
            else{eventoaEnviar = new EventoRecAgte(input,infoComplementaria, nombreAgente, itfUsoAgenteReceptor.toString());}
             // Obtener la interfaz de uso del agente reactivo con el que se quiere comunicar
             try {
			itfUsoAgenteReceptor.aceptaEvento(eventoaEnviar);
            }catch (Exception e) {
		logger.error("Ha habido un problema enviar un  evento al agente "+itfUsoAgenteReceptor.toString());
		trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
			  "Ha habido un problema enviar el evento con informacion "+ input+"al   agente "+itfUsoAgenteReceptor.toString(),
                        	  InfoTraza.NivelTraza.error));
            }
        }*/
public void mandarMensajeAAgenteId(Object contenido,String identAgenteReceptor){
            // Este método crea un mensaje con la información de entrada y se le envía al agente REACTIVO que se indique por medio de
            // la  interfaz de uso
            MensajeSimple mensajeaEnviar = null;
            InterfazUsoAgente itfUsoAgenteReceptor = null;

//           Se verifica que la interfaz del aegente no es vacia

        try {
                itfUsoAgenteReceptor = (InterfazUsoAgente) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz
                    (NombresPredefinidos.ITF_USO+identAgenteReceptor);
               }
               catch (Exception e) {
                   Logger.getLogger(InfoComunicacion.class.getName()).log(Level.SEVERE, null, e);
//                   logger.error("Ha habido un problema enviar un  evento al agente "+IdentAgenteReceptor);
    			trazas.aceptaNuevaTraza(new InfoTraza(identAgenteEmisor,
				"Ha habido un problema al enviar el mensaje con informacion "+ contenido.toString()+"al   agente "+identAgenteReceptor,
					  InfoTraza.NivelTraza.error));
               }


         //   En primer lugar se crea el mensaje con  la informacion de entrada
            if (mensajeaEnviar == null){
                mensajeaEnviar = new MensajeSimple(contenido, identAgenteEmisor, identAgenteReceptor);
             }
        //    else{eventoaEnviar = new EventoRecAgte(input,infoComplementaria, nombreAgenteEmisor, IdentAgenteReceptor);}
             // Obtener la interfaz de uso del agente reactivo con el que se quiere comunicar
             try {
			itfUsoAgenteReceptor.aceptaMensaje(mensajeaEnviar);
                        trazas.aceptaNuevaTraza(new InfoTraza(identAgenteEmisor, "Se manda 1 mensaje de  " + contenido.toString() + " al agente  "+ identAgenteReceptor, InfoTraza.NivelTraza.debug));
            }catch (Exception e) {
	//	logger.error("Ha habido un problema enviar un  evento al agente "+IdentAgenteReceptor, e);
		trazas.aceptaNuevaTraza(new InfoTraza(identAgenteEmisor,
			  "Ha habido un problema enviar el mensaje con informacion "+ contenido.toString()+"al   agente "+identAgenteReceptor,
                        	  InfoTraza.NivelTraza.error));
            }
        }
        //en la evaluacion va el Agente que lo ha enviado y la evaluacion en un ArrayList
        public void mandarMensajeAGrupo( Object infoAEnviar,  ArrayList<String> identificadoresAgentesGrupo){
            try {
                //mandamos el mensaje a todos los agentes del sistema, menos a los gestores
      //          String aux;
      //          ArrayList paquete = new ArrayList();
      //           trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
      //          String aux =""+ evaluacion;
      //          paquete.add(aux);
      //          paquete.add(nombreAgenteEmisor);
      //          EvaluacionAgente evalAgente = new EvaluacionAgente(nombreAgenteEmisor,evaluacion);
                for(int i = 0; i< identificadoresAgentesGrupo.size(); i++){
                    String agenteReceptor = (String)identificadoresAgentesGrupo.get(i);

                    this.mandarMensajeAAgenteId(infoAEnviar, agenteReceptor );
                }
            } catch (Exception ex) {
                Logger.getLogger(InfoComunicacion.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
}
