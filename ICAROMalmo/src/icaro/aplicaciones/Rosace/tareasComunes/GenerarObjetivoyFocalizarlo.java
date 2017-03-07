/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.Rosace.tareasComunes;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 *
 * @author Francisco J Garijo
 */
public class GenerarObjetivoyFocalizarlo extends TareaSincrona{

   @Override
   public void ejecutar(Object... params) {
	   try {
             Objetivo objetivoaGenerar = (Objetivo)params[0];
             MisObjetivos misObjetivosActuales = (MisObjetivos)params[1];
             Focus miFocoActual = (Focus) params[2];
             objetivoaGenerar.setSolving();
             miFocoActual.setFoco(objetivoaGenerar);
             misObjetivosActuales.addObjetivo(objetivoaGenerar);
             this.getEnvioHechos().insertarHecho(objetivoaGenerar);
             this.getEnvioHechos().insertarHechoWithoutFireRules(misObjetivosActuales);
             this.getEnvioHechos().actualizarHecho(miFocoActual);
             this.trazas.aceptaNuevaTrazaEjecReglas(this.identAgente," Ejecucion de la tarea : " + this.getIdentTarea()+ "  Se genera el objetivo : " +objetivoaGenerar );      	        	      
       } catch (Exception e) {
	e.printStackTrace();
       }
   }

}
