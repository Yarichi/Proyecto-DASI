/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.entidadesBasicas.procesadorCognitivo;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.PerformativaUsuario;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoSimple;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
//import java.util.logging.Logger;
import org.apache.log4j.Logger;
/**
 *Esta clase genera evidencias a partir de la informacion contenida en un evento simple
 * o posiblemente en varios eventos. Por ahora solo consideramos un evento
 * @author Francisco J Garijo
 */
public class InterpreteEventosSimples {
private Logger log = Logger.getLogger(InterpreteEventosSimples.class);
private ItfUsoRecursoTrazas trazas= NombresPredefinidos.RECURSO_TRAZAS_OBJ;
private String propietario;
private Evidencia evidencia ;
private ExtractedInfo inforExtracted ;
private Object contenidoEvento ;
public InterpreteEventosSimples ( String propietario){
    this. propietario = propietario;
}
public Evidencia generarEvidencia(EventoSimple evento) {
		
            if (evento == null)	{
//            log.error("El evento es null ");
              evidencia = null;
              trazas.aceptaNuevaTraza(new InfoTraza (this.propietario," El evento recibido para interpretar es null ",InfoTraza.NivelTraza.debug));
                }
                else {
           // evidencia = new Evidence();
                String origen = evento.getOrigen();
                String creador = propietario;
         //   evidencia.setOrigen(origen);
         //   evidencia.setCreador(propietario);
			//TODO Cambiar
//			evidencia.setContent(evento);
 
                 String identEmisor = evento.getOrigen();
//         Se extrae el contenido del evento y se transforma según sea su tipo

//                if ( identEmisor.contains("Visualiz")){
                  contenidoEvento =  evento.getContenido();
                 if ( contenidoEvento instanceof InfoContEvtMsgAgteReactivo ){
                 PerformativaUsuario infoEvento =  transformaInfoControlAgteReactivoEnPerformativaUsuario ((InfoContEvtMsgAgteReactivo)contenidoEvento);
                if (infoEvento != null){
                    //     evidencia.addElementToContent(contenidoEvento);
                         evidencia = new Evidencia(origen, creador, infoEvento);
                                }
                            } else // El emisor es otra entidad
                                {
                // Creamos un objeto generico eventInfo que contiene el tipo de evento y la
                // lista de pares atributo valor para su posible interpretacion
//                                EventInfo contenido = new EventInfo(evento);
                //    evidencia.addElementToContent(contenido);
                // Se crea una evidencia con el contenido del evento sin transformar
                                evidencia = new Evidencia(origen, creador, contenidoEvento);
                    }
 
            }

    return evidencia;
}
public ExtractedInfo generarExtractedInfo(EventoSimple evento) {
		
            if (evento == null)	{
//            log.error("El evento es null ");
              inforExtracted = null;
              trazas.aceptaNuevaTraza(new InfoTraza (this.propietario," El evento recibido para interpretar es null ",InfoTraza.NivelTraza.debug));
                }
                else {
                String origen = evento.getOrigen();
                String creador = propietario;
 
 //         Transformamos el atributo msg del evento y la lista de parámetros en un objeto
//          de la clase EvenInfo donde se incluyen los atributos del evento  y los valores de cada atributo
//          Si el que envia el evento es un recurso de visualiación transformamos el contenido
//          del evento en un objeto InfoUsuario que tiene un atributo petición y otro con la lista  de objetos
//          asociados a la peticion.
                 String identEmisor = evento.getOrigen();
//         Se extrae el contenido del evento y se transforma según sea su tipo
                contenidoEvento =  evento.getContenido();
//                if ( contenidoEvento instanceof InfoContEvtMsgAgteReactivo ){
//                PerformativaUsuario infoEvento =  transformaInfoControlAgteReactivoEnPerformativaUsuario ((InfoContEvtMsgAgteReactivo)contenidoEvento);
//               if (infoEvento != null){
//                        inforExtracted = new ExtractedInfo(origen, creador, infoEvento);
//                               }
//                           } else 
                                inforExtracted = new ExtractedInfo(origen, creador, contenidoEvento);
            }

    return inforExtracted;
}
public PerformativaUsuario transformaInfoControlAgteReactivoEnPerformativaUsuario( InfoContEvtMsgAgteReactivo eventContentInfo){
    String operacion = eventContentInfo.getInput();
    String performativa = "informacion";
//    String operacion = contenidoMSG;
    Object parametros = eventContentInfo.getvaloresParametrosAccion();
    if ((operacion.contains("peticion"))){
// la peticion de alta la transformamos en una peticion donde la  operacion sera dar de alta y a continuacion  pasamos los  paramentros de la operacion
         performativa = "peticion";
//        String operacion = contenidoMSG;
//       Object parametros = evento.getMsgElements();
        // Los parametros son los parametros del evento
//       return new PerformativaUsuario ( performativa,operacion, parametros );
}
    else{
        trazas.aceptaNuevaTraza(new InfoTraza (this.propietario," Hacemos una transformacion estandar  en performativa de usuario ",InfoTraza.NivelTraza.debug));
//        return null;
    }

    return new PerformativaUsuario ( performativa,operacion, parametros );
}
}
