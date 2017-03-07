/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.imp;

import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.FactoriaAbstrCompInterno;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.InfoCompMovimiento;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.ItfUsoMovimientoCtrl;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.ItfUsoRecursoVisualizadorEntornosSimulacion;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ItfProcesadorObjetivos;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FGarijo
 */
public class FactoriaRIntMovimientoCtrl extends FactoriaAbstrCompInterno{
    private ItfUsoRecursoTrazas trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
    private ItfUsoRecursoVisualizadorEntornosSimulacion itfUsoRecVisEntornosSimul;
    public FactoriaRIntMovimientoCtrl(){
        
    }
   
    @Override
    public InfoCompMovimiento crearComponenteInterno(String identComponenteInterno, ItfProcesadorObjetivos procObj) {
        // se crea un componente movimiento en estado parado
       
        String identComponenteAcrear = procObj.getAgentId()+identComponenteInterno;
        MaquinaEstadoMovimientoCtrl maquinaEstados = new MaquinaEstadoMovimientoCtrl();
        maquinaEstados.SetComponentId(identComponenteAcrear);
        maquinaEstados.SetInfoContexto(procObj);
        try {
         ItfUsoRepositorioInterfaces repoItfs = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
         itfUsoRecVisEntornosSimul = (ItfUsoRecursoVisualizadorEntornosSimulacion)repoItfs.obtenerInterfazUso(VocabularioRosace.IdentRecursoVisualizadorEntornosSimulacion);
         maquinaEstados.SetItfUsoRecursoVisualizadorEntornosSimulacion(itfUsoRecVisEntornosSimul);
         maquinaEstados.cambiarEstado(MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot.RobotParado);
        } catch (Exception ex) {
            Logger.getLogger(FactoriaRIntMovimientoCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
//        ItfUsoMovimientoCtrl itfMov =(ItfUsoMovimientoCtrl) maquinaEstados.cambiarEstado(MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot.RobotParado);
        ItfUsoMovimientoCtrl itfMov =(ItfUsoMovimientoCtrl) maquinaEstados;
        InfoCompMovimiento infoCompCreado = new InfoCompMovimiento (identComponenteAcrear);
        infoCompCreado.setitfAccesoComponente(itfMov);
        return infoCompCreado;
 
    }
    
    @Override
     public  boolean verificarExisteInterfazUsoComponente(String identClaseQueImplementaInterfaz){
         return true;
     }
     
}
