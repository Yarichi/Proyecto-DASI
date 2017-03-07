/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE;

import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
/**
 *
 * @author Francisco J Garijo
 */
public class ExcepcionNoSePudoCrearAutomataEFE extends ExcepcionEnComponente {

protected String identComponente = "Automata de Estados Finitos";
protected String identSuperComponente = "patronAgenteReactivo";
protected String identParteAfectada;
public  ExcepcionNoSePudoCrearAutomataEFE (String parteAfectada, String causa, String contextoExcepcion){
super (parteAfectada,causa, contextoExcepcion);
}
}