/*
 * GenerarInfoTerminacion.java
 *
 * Created on 22 de mayo de 2007, 11:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.tareas;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Creencia;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Tarea;



/**
 *
 * @author Javi
 */
public class GenerarInfoTerminacion extends Tarea {

    /** Creates a new instance of GenerarInfoTerminacion */
  

    @Override
    public void ejecutar(Object... params) {
        Creencia cre = new Creencia();
        cre.setEmisor("Task:GenerarInfoTerminacion");
        cre.setReceptor("AgenteAcceso");
        cre.setContenido("Se va a terminar el servicio");
        this.getEnvioHechos().insertarHecho(cre);
    }
}
