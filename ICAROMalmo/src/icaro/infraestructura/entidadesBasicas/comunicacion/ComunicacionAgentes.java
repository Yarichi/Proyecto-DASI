// *****************************************************************************
package icaro.infraestructura.entidadesBasicas.comunicacion;
/* ********************************************************************** */
 
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import org.apache.log4j.Logger;




public class ComunicacionAgentes {
   
    private InterfazUsoAgente itfUsoAgente = null;
    private String agentePropietario = null;
    private ItfUsoRecursoTrazas trazas ;
    private ItfUsoRepositorioInterfaces repoInterfaces ;
    private IdentityHashMap itfsAgtesConLosQComunico;
    private Integer initialmMxSizeOfAgtesConLosQComunico = 8; // Esto es arbitrario
    protected Logger logger ;
    public ComunicacionAgentes (String propietario) {
        agentePropietario = propietario;
        itfsAgtesConLosQComunico = new IdentityHashMap( initialmMxSizeOfAgtesConLosQComunico) ;
        trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        repoInterfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ ;
    }
    
    public  boolean existItfAgte (String nombreAgente) {
        try {
//            Busco la interfaz en la tabla de interfaces particular
             itfUsoAgente = (InterfazUsoAgente) itfsAgtesConLosQComunico.get(nombreAgente) ;
             if (itfUsoAgente == null) { // busco la interfaz en los repositorios      
            /* Busco en el repositorio el interfaz del agente que necesito */
                itfUsoAgente = (InterfazUsoAgente) repoInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgente);   
                if (itfUsoAgente == null)               
                    itfUsoAgente =(InterfazUsoAgente)  AdaptadorRegRMI.getItfAgenteRemoto(nombreAgente, NombresPredefinidos.ITF_USO);
             }
                 if (itfUsoAgente == null) return false;
                 else {
                     itfsAgtesConLosQComunico.put(nombreAgente, itfUsoAgente);
                     return true;
                 }
        }
        catch (Exception e) {
            System.err.printf ("Error al buscar interfaz del agente: [%s]",nombreAgente);
            e.printStackTrace();
            return false;
        }
    }

    public  boolean buscarInterfazEnRegistroRMI (String nombreAgente) {
        try {
            /* Busco en el repositorio el interfaz del agente que necesito */
            itfUsoAgente = (InterfazUsoAgente) repoInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgente);
            /* Envio el input si puedo */
            if (itfUsoAgente != null) return true;
            else return false;
        }
        catch (Exception e) {
            System.err.printf ("Error al buscar interfaz del agente: [%s]",nombreAgente);
            e.printStackTrace();
            return false;
        }
    }
    
public synchronized  boolean enviarInfoAotroAgente (Object infoAEnviar,  String identAgteReceptor) {
        try {
            if (infoAEnviar==null){
                trazas.aceptaNuevaTraza(new InfoTraza(agentePropietario,
						"No se envian objetos  : " +infoAEnviar +" Al agente: " + identAgteReceptor ,InfoTraza.NivelTraza.error));
                return false;
            }
            if (existItfAgte (identAgteReceptor)) {
                MensajeSimple mensajeAenviar = new MensajeSimple(infoAEnviar, agentePropietario,identAgteReceptor);
               itfUsoAgente.aceptaMensaje(mensajeAenviar);
          //     trazas.aceptaNuevaTraza(new InfoTraza(agentePropietario,
	//					"Se envia  la informacion : " +infoAEnviar +" Al agente: " + identAgteReceptor ,
	//					InfoTraza.NivelTraza.debug));
               trazas.aceptaNuevaTrazaEnviarMensaje(mensajeAenviar);
                return true;
            }
              else {
                logger.error("El agente: "+ agentePropietario + "-- no pudo   enviar la informacion : " +infoAEnviar +" Al agente: " + identAgteReceptor + ".No se encontro la interfaz del agente Receptor");
                trazas.aceptaNuevaTraza(new InfoTraza(agentePropietario,
						"No se pudo enviar la informacion : " +infoAEnviar +" Al agente: " + identAgteReceptor + ". No se encontro la interfaz del agente Receptor",
						InfoTraza.NivelTraza.error));
                return false;
            }
        }
        catch (Exception e) {
            logger.error("El agente: "+ agentePropietario + "-- no pudo   enviar la informacion : " +infoAEnviar +" Al agente: " + identAgteReceptor + ".Hubo un error en la busqueda de la interfaz del agente Receptor");
            trazas.aceptaNuevaTraza(new InfoTraza(agentePropietario,
						"No se pudo enviar la informacion : " +infoAEnviar +" Al agente: " + identAgteReceptor + ". Hubo un error en la busqueda de la interfaz del agente Receptor",
						InfoTraza.NivelTraza.error));
            e.printStackTrace();
            return false;
        }
    }
public synchronized boolean enviarInfoConMomentoCreacionAotroAgente (Object infoAEnviar,  String identAgteReceptor) {
    
    return (enviarMsgaOtroAgente(new MensajeSimpleConInfoTemporal(infoAEnviar,agentePropietario,identAgteReceptor)));
}
public synchronized boolean enviarMsgaOtroAgente (MensajeSimple mensajeAenviar) {
        try {
            if (existItfAgte ((String)mensajeAenviar.getReceptor())) {
 //               MensajeSimple mensajeAenviar = new MensajeSimple(infoAEnviar, agentePropietario,identAgteReceptor);
               itfUsoAgente.aceptaMensaje(mensajeAenviar);
          //     trazas.aceptaNuevaTraza(new InfoTraza(agentePropietario,
	//					"Se envia  la informacion : " +infoAEnviar +" Al agente: " + identAgteReceptor ,
	//					InfoTraza.NivelTraza.debug));
               trazas.aceptaNuevaTrazaEnviarMensaje(mensajeAenviar);
                return true;
            }
              else {
                logger.error("El agente: "+ agentePropietario + "-- no pudo   enviar el mensaje  : " +mensajeAenviar + ".No se encontro la interfaz del agente Receptor");
                trazas.aceptaNuevaTraza(new InfoTraza(agentePropietario,
						"No se pudo enviar la informacion : " +mensajeAenviar+ ". No se encontro la interfaz del agente Receptor",
						InfoTraza.NivelTraza.error));
                return false;
            }
        }
        catch (Exception e) {
            logger.error("El agente: "+ agentePropietario + "-- no pudo   enviar el mensaje  : " +mensajeAenviar + ".No se encontro la interfaz del agente Receptor");
                trazas.aceptaNuevaTraza(new InfoTraza(agentePropietario,
						"No se pudo enviar la informacion : " +mensajeAenviar+ ". No se encontro la interfaz del agente Receptor",
						InfoTraza.NivelTraza.error));
            return false;
        }
    }

    public synchronized void informaraOtroAgenteReactivo(InfoContEvtMsgAgteReactivo infoAEnviar,String identAgenteReceptor, ItfUsoAgenteReactivo itfUsoAgenteReceptor ){

   // Este método crea un evento con la información de entrada y se le envía al agente REACTIVO que se indique por medio de
  // la  interfaz de uso
//        EventoRecAgte eventoaEnviar = null;
   // Se verifica que la interfaz del agente no es vacia
    try {
        if (itfUsoAgenteReceptor != null){
             MensajeSimple mensajeAenviar = new MensajeSimple(infoAEnviar, agentePropietario,identAgenteReceptor);
             itfUsoAgenteReceptor.aceptaMensaje(mensajeAenviar);
        }else {
            
            logger.error("El agente: "+ agentePropietario + "-- no puedo   enviar la informacion : " +infoAEnviar +" Al agente: " + identAgenteReceptor + " La interfaz del agente Receptor es null ");
            trazas.aceptaNuevaTraza(new InfoTraza(agentePropietario,
						"No se pudo enviar la informacion : " +infoAEnviar +" Al agente: " + identAgenteReceptor + ". La interfaz del agente Receptor es null",
						InfoTraza.NivelTraza.error));

            }

		}
		catch (Exception e) {
			logger.error("Ha habido un problema enviar un  evento al agente "+identAgenteReceptor);
			trazas.aceptaNuevaTraza(new InfoTraza(agentePropietario,
						 "Hubo un error en la busqueda de la interfaz del agente Receptor ---"+infoAEnviar +" -- al   agente : "+identAgenteReceptor,
															  InfoTraza.NivelTraza.error));
			}

       
        }
    
     public synchronized void informaraOtroAgenteReactivo(InfoContEvtMsgAgteReactivo infoAEnviar,String identAgenteReceptor){

   // Este método crea un evento con la información de entrada y se le envía al agente REACTIVO que se indique por medio de
  // la  interfaz de uso
        EventoRecAgte eventoaEnviar = null;

  // Se busca la interfaz del agente al que se pretende enviar la informacion


   // Se verifica que la interfaz del aegente no es vacia
//       if (itfUsoAgenteReceptor == null){
        try {
		 if (existItfAgte (identAgenteReceptor)) {
                /* evento: input, datos[], origen, destino */
                MensajeSimple mensajeAenviar = new MensajeSimple(infoAEnviar, agentePropietario,identAgenteReceptor);
            
                itfUsoAgente.aceptaMensaje(mensajeAenviar);

            }
                else {
            logger.error("El agente: "+ agentePropietario + "-- no puedo   enviar la informacion : " +infoAEnviar +" Al agente: " + identAgenteReceptor + ".No se encontro la interfaz del agente Receptor");
            trazas.aceptaNuevaTraza(new InfoTraza(agentePropietario,
						"No se pudo enviar la informacion : " +infoAEnviar +" Al agente: " + identAgenteReceptor + ". No se encontro la interfaz del agente Receptor",
						InfoTraza.NivelTraza.error));

            }

		}
		catch (Exception e) {
			logger.error("Ha habido un problema enviar un  evento al agente "+identAgenteReceptor);
			trazas.aceptaNuevaTraza(new InfoTraza(agentePropietario,
						 "Hubo un error en la busqueda de la interfaz del agente Receptor ---"+infoAEnviar +" -- al   agente : "+identAgenteReceptor,
															  InfoTraza.NivelTraza.error));
			}

       }
     
public synchronized void informaraGrupoAgentes(Object infoAEnviar,ArrayList<String> identsAgteReceptores){
    enviarMsgaGrupoAgentes(new MensajeSimple(infoAEnviar,this.agentePropietario, null),identsAgteReceptores);
}

public synchronized void informarConMomentoCreacionaGrupoAgentes(Object infoAEnviar,ArrayList<String> identsAgteReceptores){
    enviarMsgaGrupoAgentes(new MensajeSimpleConInfoTemporal(infoAEnviar,this.agentePropietario, null),identsAgteReceptores);
}
/*
 public void informaraGrupoAgentes(Object infoAEnviar,ArrayList<String> identsAgteReceptores){
         String agenteReceptor = null;
         try {               
                //mandamos el mensaje a todos los agentes del sistema, menos a los gestores
      //          String aux;
      //          ArrayList paquete = new ArrayList();
       //          trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
             int numeroAgentesEquipo = identsAgteReceptores.size();
             if(numeroAgentesEquipo > 0){
                for(int i = 0; i< numeroAgentesEquipo; i++){
                     agenteReceptor = (String)identsAgteReceptores.get(i);               
                    this.enviarInfoAotroAgente(infoAEnviar, agenteReceptor );
                }
             }else trazas.aceptaNuevaTraza(new InfoTraza(agentePropietario,
					"No se pudo enviar la informacion : " +infoAEnviar +
                                        " porque el grupo de agentes a los que hay que enviar la informacion esta vacio ", InfoTraza.NivelTraza.error));
                 
            } catch (Exception ex) {
               logger.error("El agente: "+ agentePropietario + "-- no pudo   enviar la informacion : " +infoAEnviar +" Al agente: " + agenteReceptor + ".No se encontro la interfaz del agente Receptor");
               trazas.aceptaNuevaTraza(new InfoTraza(agentePropietario,
						"No se pudo enviar la informacion : " +infoAEnviar +" Al agente: " + agenteReceptor + ". No se encontro la interfaz del agente Receptor",
						InfoTraza.NivelTraza.error));
            }
     }
     * 
     */
 public synchronized void enviarMsgaGrupoAgentes(MensajeSimple msgAEnviar,ArrayList<String> identsAgteReceptores){
   //      String agenteReceptor = null;
         try {               
             int numeroAgentesEquipo = identsAgteReceptores.size();
             if(numeroAgentesEquipo > 0){
                for(int i = 0; i< numeroAgentesEquipo; i++){
                     msgAEnviar.setReceptor(identsAgteReceptores.get(i));               
                    this.enviarMsgaOtroAgente(msgAEnviar);
                }
             }else trazas.aceptaNuevaTraza(new InfoTraza(agentePropietario,
					"No se pudo enviar el mensaje : " +msgAEnviar +
                                        " porque el grupo de agentes a los que hay que enviar la informacion esta vacio ", InfoTraza.NivelTraza.error));       
            } catch (Exception ex) {
               logger.error("El agente: "+ agentePropietario + "-- no pudo   enviar el mensaje : " +msgAEnviar );
               trazas.aceptaNuevaTraza(new InfoTraza(agentePropietario,
						"No se pudo enviar la informacion : " +msgAEnviar ,
						InfoTraza.NivelTraza.error));
            }
     }
 
    }
        