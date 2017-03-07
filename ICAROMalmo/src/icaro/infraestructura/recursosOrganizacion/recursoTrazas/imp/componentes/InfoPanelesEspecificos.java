/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstancia;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.TipoAgente;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.gui.*;
import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FGarijo
 */
public class InfoPanelesEspecificos {
 //   private HashMap tablaInfoPanelesEspecificos;
	//arraylist de informacin ventanas de trazas
    private Map tablaPanelesEspecificos;
	//arraylist que contiene los paneles visualizados
    private InfoPanelEspecifico infoPanelEspecifico;
   private LinkedList<String> listaElementosTrazables  ;
    private Set identsTiposEntidades;
    private ItfUsoConfiguracion itfConfig ;
    public InfoPanelesEspecificos (){
        tablaPanelesEspecificos = new HashMap();      
        identsTiposEntidades = new HashSet();
        listaElementosTrazables = new LinkedList<String>();
        TipoAgente[] tg= TipoAgente.values();
        for (int i=0; i< tg.length ;i++ ){
            identsTiposEntidades.add(tg[i].value());
        }
        identsTiposEntidades.add(NombresPredefinidos.NOMBRE_ENTIDAD_RECURSO);
    }
    public void addNewPanelEspecifico (InfoPanelEspecifico infoPanel){
        tablaPanelesEspecificos.put(infoPanel.getIdentificador(), infoPanel);
        
    }
   
    public InfoPanelEspecifico getInfoPanel(String entityId) {
		return (InfoPanelEspecifico) tablaPanelesEspecificos.get(entityId);
	}
    public PanelTrazasAbstracto getPanelEspecifico(String entityId) {
		
//	infoPanelEspecifico =(InfoPanelEspecifico) tablaPanelesEspecificos.get(entityId);
  //      if (infoPanelEspecifico != null) return infoPanelEspecifico.getPanelEspecifico();
  //      else return null;
        return (PanelTrazasAbstracto)tablaPanelesEspecificos.get(entityId);
    }
   public Set getIdentsPenels() {
       return tablaPanelesEspecificos.keySet();
   }
   
   public PanelTrazasAbstracto crearPanelparaEntidad (String entityId){
    PanelTrazasAbstracto nuevoPanel = null;
    try {
            // Obtener el tipo de entidad
             if ((itfConfig == null)&& (NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ != null))
             itfConfig = (ItfUsoConfiguracion)NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz("Itf_Uso_Configuracion");
         } catch (Exception ex) {
            Logger.getLogger(ClasificadorVisual.class.getName()).log(Level.SEVERE, null, ex);
   //         nuevoPanel = new PanelTrazasAgteReactivo(entityId, "");
   //         tablaPanelesEspecificos.put(entityId, nuevoPanel);
        }
        try {
            if (itfConfig == null) nuevoPanel = new PanelTrazasAgteReactivo(entityId, "");
            else {
            DescInstancia descComponente =   itfConfig.getDescInstancia(entityId);
            if (descComponente == null)nuevoPanel = new PanelTrazasGenerico(entityId, "");
            else if (descComponente.getCategoriaComponente().equals(NombresPredefinidos.NOMBRE_ENTIDAD_AGENTE)){
                        if( descComponente.esDirigidoPorObjetivos())
                                nuevoPanel = new PanelTrazasAgteCognitivo(entityId, "");
                        else nuevoPanel = new PanelTrazasAgteReactivo(entityId, "");
            }
                else if  (descComponente.getCategoriaComponente().equals(NombresPredefinidos.NOMBRE_ENTIDAD_RECURSO))
                        nuevoPanel = new PanelTrazasRecurso(entityId, "");
                    else nuevoPanel = new PanelTrazasGenerico(entityId, "");
            }          
//            InfoPanelEspecifico infoPanel = new InfoPanelEspecifico(entityId,"");
 //           infoPanel.setPanelEspecifico(nuevoPanel);  
    //        tablaPanelesEspecificos.addNewPanelEspecifico(infoPanel);
            tablaPanelesEspecificos.put(entityId, nuevoPanel);
        } catch (ExcepcionEnComponente ex) {
            Logger.getLogger(ClasificadorVisual.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ClasificadorVisual.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nuevoPanel;
}
  public synchronized PanelTrazasAbstracto crearPanelparaEntidad (String entityId, NombresPredefinidos.TipoEntidad entityType){
      PanelTrazasAbstracto panel = (PanelTrazasAbstracto)tablaPanelesEspecificos.get(entityId);
      if(panel == null) {
          if (entityType.equals(NombresPredefinidos.TipoEntidad.noDefinido)) panel = new PanelTrazasGenerico(entityId, ""); 
          else if (entityType.equals(NombresPredefinidos.TipoEntidad.Reactivo) )panel = new PanelTrazasAgteReactivo(entityId, "");
            else if(entityType.equals(NombresPredefinidos.TipoEntidad.Recurso))panel = new PanelTrazasRecurso(entityId, "");
                else panel =new PanelTrazasAgteCognitivo(entityId, "");
          tablaPanelesEspecificos.put(entityId, panel);
          listaElementosTrazables.add(entityId);
      }
      return panel;
  }  
}
