/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.infraestructura.entidadesBasicas.procesadorCognitivo;

import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import java.util.ArrayList;

/**
 *
 * @author FGarijo
 */
public class TareaComunicacion extends Tarea{
     private ComunicacionAgentes comunicacion;
     private ArrayList <String> agentesEquipo;
     private String identAgenteReceptor;
     private String identAgenteEmisor;
     
 @Override
 public  void ejecutar(Object... params){
     
 }
 public void informaraOtroAgente(Object contenido,String identAgenteReceptor){
    identAgenteEmisor = this.getAgente().getIdentAgente();
    comunicacion = new ComunicacionAgentes(identAgenteEmisor);
   try {
    comunicacion.enviarInfoAotroAgente(contenido, identAgenteReceptor);
   }
   catch (Exception e) {
//	e.printStackTrace();
        this.trazas.aceptaNuevaTraza(new InfoTraza(identAgenteEmisor, "Error al enviar mensaje al agente : "+ identAgenteReceptor + " Contenido :  "+ contenido, InfoTraza.NivelTraza.error));
        this.generarInformeConCausaTerminacion(this.getIdentTarea(), null, identAgenteEmisor, contenido.toString(), CausaTerminacionTarea.ERROR);
                    }
    
}
 public void informaraGrupoAgentes(Object contenido,ArrayList <String> agentesEquipo){
    identAgenteEmisor = this.getAgente().getIdentAgente();
    comunicacion = new ComunicacionAgentes(identAgenteEmisor);
   try {
    comunicacion.informaraGrupoAgentes(contenido, agentesEquipo);
   }
   catch (Exception e) {
//	e.printStackTrace();
        this.trazas.aceptaNuevaTraza(new InfoTraza(identAgenteEmisor, "Error al enviar mensaje al grupo de agentes : "+ agentesEquipo + " Contenido :  "+ contenido, InfoTraza.NivelTraza.error));
        this.generarInformeConCausaTerminacion(this.getIdentTarea(), null, identAgenteEmisor, contenido.toString(), CausaTerminacionTarea.ERROR);
                    }
    
}
}
