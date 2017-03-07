/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.Rosace.informacion;

/**
 *
 * @author FGarijo
 */
public class InfoEstatusComunicacionConOtroAgente {
    private String identOtroAgente;
    private String identAgteInformante;
    private Boolean hayComunicAgteInformanteConOtroAgte;
    public InfoEstatusComunicacionConOtroAgente (String idAgteinformante,String idAgteObjetoDeInformacion){
        identAgteInformante = idAgteinformante;
        identOtroAgente = idAgteObjetoDeInformacion;
    }
    public String getidentAgteInformante (){
        return identAgteInformante;
    }
    public void setidentidentOtroAgente (String idAgteEstatus){
        identOtroAgente = idAgteEstatus;
    }
    public String getidentidentOtroAgente (){
        return identOtroAgente;
    }
    public void sethayComunicAgteInformanteConOtroAgte (Boolean hayComunicacion){
        hayComunicAgteInformanteConOtroAgte = hayComunicacion;
    }
    public Boolean gethayComunicAgteInformanteConOtroAgte (){
        return hayComunicAgteInformanteConOtroAgte;
    }
}
