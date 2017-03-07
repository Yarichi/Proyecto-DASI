/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.Rosace.tareasComunes;
import icaro.aplicaciones.Rosace.informacion.InfoEquipo;
import icaro.aplicaciones.Rosace.informacion.InfoRolAgente;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
/**
 *
 * @author FGarijo
 */
public class ProcesarInfoRolAgente extends TareaSincrona {
    
    @Override
    public void ejecutar(Object... params) {
            try {
                InfoEquipo  miEquipo = (InfoEquipo)params[0];
                InfoRolAgente infoRolRecibido = (InfoRolAgente)params[1];
                miEquipo.procesarInfoRolAgente(infoRolRecibido);
                // Si el equipo es jerarquico y el Rol es de agente asignador de tareas entonces
                // se obtiene su identificador y se le pone como  jefe en el equipo
                  if (infoRolRecibido.getidentRolAgte().equals(VocabularioRosace.IdentRolAgteDistribuidorTareas))
                                                miEquipo.setidentAgenteJefeEquipo(infoRolRecibido.getAgteIniciadorId());
                  this.getEnvioHechos().eliminarHechoWithoutFireRules(infoRolRecibido);
                  this.getEnvioHechos().actualizarHecho(miEquipo);
                  trazas.trazar(this.getIdentAgente(), "Se procesa el InfoRol recibido :  "+ infoRolRecibido.toString(), InfoTraza.NivelTraza.debug);
               }         
            catch(Exception e) {
                  e.printStackTrace();
            }
    }
}
