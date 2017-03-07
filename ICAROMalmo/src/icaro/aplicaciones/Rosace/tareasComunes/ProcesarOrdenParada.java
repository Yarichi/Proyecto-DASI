/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.Rosace.tareasComunes;
import icaro.aplicaciones.Rosace.informacion.InfoEquipo;
import icaro.aplicaciones.Rosace.informacion.InfoEstadoAgente;
import icaro.aplicaciones.Rosace.informacion.InfoRolAgente;
import icaro.aplicaciones.Rosace.informacion.OrdenCentroControl;
import icaro.aplicaciones.Rosace.informacion.OrdenParada;
import icaro.aplicaciones.Rosace.informacion.RobotStatus1;
import icaro.aplicaciones.Rosace.informacion.VictimsToRescue;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.InfoCompMovimiento;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.ItfUsoMovimientoCtrl;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.imp.EstadoAbstractoMovRobot;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.imp.MaquinaEstadoMovimientoCtrl;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.imp.MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
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
public class ProcesarOrdenParada extends TareaSincrona {
    private ItfUsoMovimientoCtrl itfcompMov;
    @Override
    public void ejecutar(Object... params) {
            try {
             RobotStatus1 miEstatus = (RobotStatus1) params[0];
             InfoCompMovimiento infoCompMov = (InfoCompMovimiento) params[1];
             OrdenParada ordenCC = (OrdenParada)params[2];
//             trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente, "Se Ejecuta la Tarea :"+ this.identTarea , InfoTraza.NivelTraza.info));
             trazas.aceptaNuevaTrazaEjecReglas(this.identAgente, "Se Procesa la orden  Parada   recibida por el agente  "+"\n");
            // se inicializa objetivos, foco y victimas, y se vacia la memoria de trabajo y se me
            itfcompMov = (ItfUsoMovimientoCtrl)infoCompMov.getitfAccesoComponente();
//             this.informarControladorRescateVictima(victimaRescatadaId); // informamos al agente controlador
             // Se actualizan los objetivos, se da por conseguido el objetivo salvar a la victima
             // se supone que este objetivo era el mas prioritario, si no lo era hay un problema
//              Objetivo objetivoConseguido = misObjs.getobjetivoMasPrioritario();
              Thread accesoCompMovimiento = new Thread(){
				public void run(){
//                                    itfcompMov.initContadorGastoEnergia();
					itfcompMov.parar();
				}
			};
            accesoCompMovimiento.run();
//             this.getEnvioHechos().insertarHechoWithoutFireRules(miEquipo);
            miEstatus.setBloqueado(true);
            miEstatus.setRobotCoordinate(itfcompMov.getCoordenadasActuales());
            miEstatus.setestadoMovimiento(EstadoMovimientoRobot.RobotBloqueado.name());
            miEstatus.setcausaCambioEstado(VocabularioRosace.CausaCambioMovtoOrdenCC);
            trazas.aceptaNuevaTrazaEjecReglas(identAgente, 
                        "Se ejecuta la tarea : " + identTarea + " El robot esta en la posicion " + itfcompMov.getCoordenadasActuales() +
                        "estado del robot : "+EstadoMovimientoRobot.RobotBloqueado.name()+"\n" +
                        "esta bloqueado : "+ miEstatus.getBloqueado()+"\n" );
            // Se informa al agente controlador de la ejecucion de la orden de parada
            InfoEstadoAgente infoMiEstado = new InfoEstadoAgente(this.identAgente,EstadoMovimientoRobot.RobotBloqueado.name(),VocabularioRosace.CausaCambioMovtoOrdenCC);
            InfoContEvtMsgAgteReactivo msg = new InfoContEvtMsgAgteReactivo(VocabularioRosace.MsgeInfoEstadoAgente,infoMiEstado);
             this.getComunicator().enviarInfoAotroAgente(msg, VocabularioRosace.IdentAgteControladorSimulador);
             this.getEnvioHechos().eliminarHecho(miEstatus);
             this.getEnvioHechos().insertarHecho(miEstatus.clone());
               }         
            catch(Exception e) {
                  e.printStackTrace();
            }
    }
}
