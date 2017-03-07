/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.gestorAcciones;
import icaro.infraestructura.entidadesBasicas.acciones.Accion;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionSincrona;

/**
 *
 * @author 
 */
public interface ItfGestorAcciones {
    public Accion crearAccion(Class clase) throws Exception;
    public AccionSincrona crearAccionSincrona(Class clase) throws Exception;
    public AccionSincrona crearAccionAsincrona(Class clase) throws Exception;
    void ejecutar(Object... params) throws Exception;
    void ejecutarMetodo(Class accion,String identMetodo, Object... params) throws Exception;
    void ejecutarMetodoThread(Class accion,String identMetodo, Object... params) throws Exception;
    void ejecutarAccion(Class accion, Object... params) throws Exception;
    
}
