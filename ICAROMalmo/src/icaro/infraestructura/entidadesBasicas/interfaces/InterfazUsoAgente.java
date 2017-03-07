/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.entidadesBasicas.interfaces;

import java.rmi.RemoteException;

/**
 *
 * @author Francisco J Garijo
 */
public interface  InterfazUsoAgente extends ItfEventoSimpe, ItfMensajeSimple {

    public String getIdentAgente() throws RemoteException;

}
