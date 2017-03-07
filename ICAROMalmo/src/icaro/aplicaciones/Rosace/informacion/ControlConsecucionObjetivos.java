/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.Rosace.informacion;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

/**
 *
 * @author FGarijo
 */
public class ControlConsecucionObjetivos {
    public String identAgente;
    public String faseCosecucionObj;
    public Objetivo tipoObjetivo;
    public Objetivo objetivoFocalizado;
    
    public ControlConsecucionObjetivos(String identAgte) {
        identAgente = identAgte;
    }
    public ControlConsecucionObjetivos(String identAgte,String faseCosecObj) {
        identAgente = identAgte;
        faseCosecucionObj = faseCosecObj;
    }
    public void setidentAgente (String identAgte){
        identAgente = identAgte;
    }
    public String getidentAgente(){
        return identAgente;
    }
    public void setfaseCosecucionObj (String faseCosecObj){
        faseCosecucionObj = faseCosecObj;
    }
    public String getfaseCosecucionObj(){
        return faseCosecucionObj;
    }
    public void settipoObjetivo (Objetivo tipoObj){
        tipoObjetivo = tipoObj;
    }
    public Objetivo gettipoObjetivo(){
        return tipoObjetivo;
    }
    public void setobjetivoFocalizado(Objetivo objFocalizado){
        objetivoFocalizado = objFocalizado;
    }
    public Objetivo getobjetivoFocalizado(){
        return objetivoFocalizado;
    }
}
