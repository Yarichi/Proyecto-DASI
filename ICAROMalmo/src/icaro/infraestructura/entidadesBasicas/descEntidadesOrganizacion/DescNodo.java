/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion;

import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.Nodo;
import org.apache.log4j.Logger;
import java.util.logging.Level;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Francisco J Garijo
 */
public class DescNodo extends Nodo{
    private ArrayList<String> identificadoresGestores,identificadoresInstanciasAgentesAplicacion,identificadoresInstanciasRecursos ;
    private String direcionIPDeEsteNodo;
    private Logger logger = Logger
			.getLogger(this.getClass().getCanonicalName());

    public Boolean setGestoresDefinidos (String nodoId, ArrayList<String> identsGestores){

        if (this.nombreUso == null){
            nombreUso= nodoId;
            identificadoresGestores = identsGestores;
            return true;
           }
            else 
                if (nombreUso == nodoId){
                    identificadoresGestores = identsGestores;
                    return true;
                }
                 else {
                     logger.fatal("El identificador de este nodo :" + nombreUso
					+ "Es diferente del nodo al que se deben asignar   los gestores" + nodoId
					+ ".\n Compruebe la asignacion de gestores  a nodos.");
                     return false;
                }
    }
    public ArrayList<String>  getGestoresDefinidos (String nodoId){


                if (nombreUso == nodoId)
                    return identificadoresGestores ;
                 else {
                     logger.fatal("El identificador de este nodo :" + nombreUso
					+ "Es diferente del nodo del que se piden los agentes" + nodoId
					+ ".\n Compruebe la asignacion de agentes a nodos.");
                     return null;
                }
    }

    public Boolean setAgentesDefinidos (String nodoId, ArrayList<String> identsInstanciasAgentesAplicacion){

        if (this.nombreUso == null){
            nombreUso= nodoId;
            identificadoresInstanciasAgentesAplicacion = identsInstanciasAgentesAplicacion;
            return true;
           }
            else 
                if (nombreUso != nodoId){
                    identificadoresInstanciasAgentesAplicacion = identsInstanciasAgentesAplicacion;
                    return true;
                }else
                    {
                     logger.fatal("El identificador de este nodo :" + nombreUso
					+ "Es diferente del nodo al que se asignan  los agentes" + nodoId
					+ ".\n Compruebe la asignacion de agentes a nodos.");
                     return false;
                }
    }
    public ArrayList<String>  getAgentesDefinidos (String nodoId){
        
        
                if (nombreUso == nodoId)
                    return identificadoresInstanciasAgentesAplicacion ;
                 else {
                     logger.fatal("El identificador de este nodo :" + nombreUso
					+ "Es diferente del nodo del que se piden los agentes" + nodoId
					+ ".\n Compruebe la asignacion de agentes a nodos.");
                     return null;
                }
    }

public Boolean setRecursosDefinidos (String nodoId, ArrayList<String> identsInstanciasRecursosAplicacion){

        if (this.nombreUso == null){
            nombreUso= nodoId;
            identificadoresInstanciasAgentesAplicacion = identsInstanciasRecursosAplicacion;
            return true;
           }
            else 
                if (nombreUso != nodoId){
                    identificadoresInstanciasAgentesAplicacion = identsInstanciasRecursosAplicacion;
                    return true;
                }
                 else{
                     logger.fatal("El identificador de este nodo :" + nombreUso
					+ "Es diferente del nodo al que se asignan  los recrusos" + nodoId
					+ ".\n Compruebe la asignacion de recrusos a nodos.");
                    return false;
                }
    }
    public ArrayList<String>  getRecursosDefinidos (String nodoId){


                if (nombreUso == nodoId)
                    return identificadoresInstanciasRecursos;
                 else {
                     logger.fatal("El identificador de este nodo :" + nombreUso
					+ "Es diferente del nodo del que se piden los agentes" + nodoId
					+ ".\n Compruebe la asignacion de agentes a nodos.");
                     return null;
                }
    }
    public Boolean setDireccionIP ( String dirIpDefinida) {
        if (validarDireccionIPDefinida(dirIpDefinida)){
            direcionIPDeEsteNodo = dirIpDefinida;
            return true;
            }else
                {
            logger.fatal("La direccion IP definida  :" + dirIpDefinida
					+ "No es correcta  "
					+ ".\n Compruebe la direccion IP .");
            return false;
            
            }
    }
    private boolean validarDireccionIPDefinida(String iPaddress){
//        final Pattern IP_PATTERN =
//              Pattern.compile("b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).)"
//                                    + "{3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)b");
//    return IP_PATTERN.matcher(iPaddress).matches();
    String ipPattern = "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})(:(\\d+))?";
//  String ipV6Pattern = "\\[([a-zA-Z0-9:]+)\\]:(\\d+)";
//  String hostPattern = "([\\w\\.\\-]+):(\\d+)";  // note will allow _ in host name
//    Pattern p = Pattern.compile( ipPattern + "|" + ipV6Pattern + "|" + hostPattern );
    Pattern p = Pattern.compile( ipPattern );
    Matcher m = p.matcher( iPaddress );
    return m.matches ();

//    if( m.matches() )
//    {
//    if( m.group(1) != null ) {
//        // group(1) IP address, group(2) is port
//    } else if( m.group(3) != null ) {
//        // group(3) is IPv6 address, group(4) is port
//    } else if( m.group(5) != null ) {
//        // group(5) is hostname, group(6) is address
//    } else {
//        // Not a valid address
//    }
}

}
