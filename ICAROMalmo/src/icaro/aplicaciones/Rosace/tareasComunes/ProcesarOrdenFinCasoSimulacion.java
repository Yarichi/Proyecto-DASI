/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.Rosace.tareasComunes;
import icaro.aplicaciones.Rosace.informacion.InfoEquipo;
import icaro.aplicaciones.Rosace.informacion.InfoRolAgente;
import icaro.aplicaciones.Rosace.informacion.OrdenCentroControl;
import icaro.aplicaciones.Rosace.informacion.RobotStatus1;
import icaro.aplicaciones.Rosace.informacion.VictimsToRescue;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.InfoCompMovimiento;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.ItfUsoMovimientoCtrl;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Informe;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 *
 * @author FGarijo
 */
public class ProcesarOrdenFinCasoSimulacion extends TareaSincrona {
    
    @Override
    public void ejecutar(Object... params) {
            try {
             InfoEquipo miEquipo = (InfoEquipo) params[0];
             InfoCompMovimiento infoCompMov = (InfoCompMovimiento) params[1];
//             trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente, "Se Ejecuta la Tarea :"+ this.identTarea , InfoTraza.NivelTraza.info));
             trazas.aceptaNuevaTrazaEjecReglas(this.identAgente, "Se Procesa la orden  Fin de simulacion   recibida por el agente  "+"\n");
            // se inicializa objetivos, foco y victimas, y se vacia la memoria de trabajo y se me
             this.getItfMotorDeReglas().reinicializarSesion();
//             misObjs.inicializar();focoActual.inicializar();victims.inicializar();
//             this.getItfMotorDeReglas().assertFactWithoutFireRules(misObjs);
//             this.getItfMotorDeReglas().assertFactWithoutFireRules(focoActual);
//             this.getItfMotorDeReglas().assertFactWithoutFireRules(victims);
//             this.getEnvioHechos().actualizarHechoWithoutFireRules(miEquipo);
             this.getEnvioHechos().actualizarHecho(infoCompMov);
               }         
            catch(Exception e) {
                  e.printStackTrace();
            }
    }
}
