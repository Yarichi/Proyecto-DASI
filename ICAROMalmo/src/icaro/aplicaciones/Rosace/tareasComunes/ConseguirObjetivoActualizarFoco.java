/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.Rosace.tareasComunes;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

/**
 *
 * @author FGarijo
 */
public class ConseguirObjetivoActualizarFoco extends TareaSincrona{
    @Override
   public void ejecutar(Object... params) {
	   try {
             MisObjetivos misObjs = (MisObjetivos) params[0];
             Objetivo objetivoConseguido = (Objetivo)params[1];
             Focus focoActual = (Focus)params[2];
         //       this.getEnvioHechos().eliminarHechoWithoutFireRules(objetivoAeliminar);
                objetivoConseguido.setSolved();
                if (misObjs.existeObjetivoConEsteIdentRef(objetivoConseguido.getobjectReferenceId()) )misObjs.deleteObjetivosSolved();
                if (focoActual != null ){
                    focoActual.setFocusToObjetivoMasPrioritario(misObjs);
                    this.getEnvioHechos().actualizarHecho(focoActual);
                }
              this.getEnvioHechos().actualizarHechoWithoutFireRules(misObjs);
                this.getEnvioHechos().actualizarHecho(objetivoConseguido);
                
             trazas.aceptaNuevaTrazaEjecReglas(this.identAgente, "Se ejecuta la tarea " + this.identTarea+
                                    " Se actualiza el  foco:  "+ focoActual +"\n" );
            System.out.println("\n"+this.identAgente +"Se ejecuta la tarea " + this.getIdentTarea()+ " Se consigue el  objetivo:  "+ objetivoConseguido+"\n\n" );
                                      
       } catch (Exception e) {
			 e.printStackTrace();
       }
   }

}

