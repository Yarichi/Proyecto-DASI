/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.entidadesBasicas.procesadorCognitivo;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.PerformativaUsuario;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeACLSimple;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
//import java.util.logging.Logger;
import java.util.List;
import java.util.ListIterator;
import org.apache.log4j.Logger;
/**
 *
 * @author Francisco J Garijo
 */
public class InterpreteMensajesSimples {



/**
 *Esta clase genera evidencias a partir de la informacion contenida en un evento simple
 * o posiblemente en varios eventos. Por ahora solo consideramos un evento
 * @author Francisco J Garijo
 */

private Logger log = Logger.getLogger(InterpreteEventosSimples.class);
private ItfUsoRecursoTrazas trazas= NombresPredefinidos.RECURSO_TRAZAS_OBJ;
private String propietario;
private String emisorMensaje;
private Evidencia evidencia = null;
private ExtractedInfo inforExtracted ;
//private List<AtributoValor> parametros;
public InterpreteMensajesSimples ( String propietario){
    this. propietario = propietario;
}
/*
public Evidencia generarEvidencia(MensajeACLSimple mensaje) {

    Object contenido = mensaje.getContenido();
    emisorMensaje = mensaje.getSource();
    evidencia = new  Evidencia ( );
    evidencia.setOrigen(emisorMensaje);
    evidencia.setCreador(propietario);
    if ((mensaje.getNamePerformative() == "REQUEST")& (mensaje.getSource().contains ("gestor"))){
    OrdenDeOtroAgente orden =  transformaMsgInfoEnOrden (contenido) ;

    evidencia.addElementToContent(orden);
    }
    else {
       evidencia.addElementToContent(contenido);
    }
return  evidencia ;
 }

 
public ExtractedInfo extractInfo(MensajeACLSimple mensaje) {

    Object contenido = mensaje.getContenido();
    emisorMensaje = mensaje.getSource();
    inforExtracted = new  ExtractedInfo ( );
    inforExtracted.setOrigen(emisorMensaje);
    inforExtracted.setCreador(propietario);
 //   if ((mensaje.getNamePerformative() == "REQUEST")& (mensaje.getSource().contains ("gestor"))){
  //  OrdenDeOtroAgente orden =  transformaMsgInfoEnOrden (contenido) ;

    inforExtracted.addElementToContent(orden);
    }
    else {
       inforExtracted.addElementToContent(contenido);
    }
return  inforExtracted ;
 }
*/
public ExtractedInfo extractInfo(MensajeSimple mensaje) {

//    Object contenido = mensaje.getContenido();
    emisorMensaje = (String ) mensaje.getEmisor();
    inforExtracted = new  ExtractedInfo ( );
    inforExtracted.setOrigen(emisorMensaje);
    inforExtracted.setCreador(propietario);
//    if (contenido.getClass().isArray()){
//        inforExtracted.setContentCollection((Array)contenido);
//    }
    if ( mensaje.isContenidoColection())
    inforExtracted.setContentCollection(mensaje.getColeccionContenido());
    else inforExtracted.setContenido(mensaje.getContenido());

return  inforExtracted ;
 }
public Evidencia generarEvidencia(MensajeSimple mensaje) {

    Object contenido = mensaje.getContenido();
    emisorMensaje = (String ) mensaje.getEmisor();
    evidencia = new  Evidencia ( );
    evidencia.setOrigen(emisorMensaje);
    evidencia.setCreador(propietario);
    evidencia.addElementToContent(mensaje.getContenido());

return  evidencia ;
 }
}