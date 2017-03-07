package icaro.infraestructura.patronRecursoSimple;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 *@author     Francisco J Garijo
 *@created    30 de septiembre de 2009
 */

public interface ItfUsoRecursoSimple extends Remote{
    public void setIdentAgenteAReportar(String identAgenteAReportar)throws RemoteException;
}
