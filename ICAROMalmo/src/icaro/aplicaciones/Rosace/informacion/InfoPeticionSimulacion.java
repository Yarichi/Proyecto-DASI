/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.Rosace.informacion;

import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.EscenarioSimulacionRobtsVictms;

/**
 *
 * @author FGarijo
 */
public class InfoPeticionSimulacion {
   private EscenarioSimulacionRobtsVictms escenarioSimulacion;
   private String modalidadSimulacion;
   private String idVictima;
   private int interevaloSecuencia;
    public InfoPeticionSimulacion (EscenarioSimulacionRobtsVictms escenario,String modSimulacion,String victimId ){
        escenarioSimulacion =escenario;
        modalidadSimulacion=modSimulacion;
        idVictima=victimId;
    }
    public InfoPeticionSimulacion (EscenarioSimulacionRobtsVictms escenario,String modSimulacion,int interv ){
        escenarioSimulacion =escenario;
        modalidadSimulacion=modSimulacion;
        interevaloSecuencia=interv;
    }

    public EscenarioSimulacionRobtsVictms getEscenarioSimulacion(){
        return escenarioSimulacion;
    }
    public String getModalidadSimulacion(){
        return modalidadSimulacion;
    }
    public String getIdVictima(){
        return idVictima;
    }
    public int getInterevaloSecuencia(){
        return interevaloSecuencia;
    }
}
