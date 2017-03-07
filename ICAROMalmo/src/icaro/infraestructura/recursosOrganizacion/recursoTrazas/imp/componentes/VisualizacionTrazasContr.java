/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoSimple;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.TipoAgente;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.NotificacionesRecTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.RecursoTrazasImp;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.gui.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author FGarijo
 */
public class VisualizacionTrazasContr {
    private Map tablaPanelesEspecificos;
    private InfoPanelEspecifico infoPanelEspecifico;
    private LinkedList<String> listaElementosTrazables  ;
    private Set identsTiposEntidades;
    private ItfUsoConfiguracion itfConfig ;
    private PanelTrazasClasificadas panelTrazasNiveles;
    private NotificacionesRecTrazas notificador;
    private Boolean activacionPanelTrazas = false;
    private InfoPanelesEspecificos infoPanels;
    private String identUltimaEntidad = null;
    private NombresPredefinidos.TipoEntidad tipoEntidadEmisora;
    private PanelTrazasAbstracto panelActual;
//    public static enum TipoEntidad {Cognitivo,ADO,DirigidoPorObjetivos,Reactivo,Recurso, noDefinido}
    
   public  VisualizacionTrazasContr (){
        tablaPanelesEspecificos = new HashMap();      
        identsTiposEntidades = new HashSet();
        listaElementosTrazables = new LinkedList<String>();
        TipoAgente[] tg= TipoAgente.values();
        for (int i=0; i< tg.length ;i++ ){
            identsTiposEntidades.add(tg[i].value());
        }
        identsTiposEntidades.add(NombresPredefinidos.NOMBRE_ENTIDAD_RECURSO);
        infoPanels = new InfoPanelesEspecificos();
        notificador = new NotificacionesRecTrazas ();
        panelTrazasNiveles= new PanelTrazasClasificadas( notificador,infoPanels);
       
       
   }
   public void setIdentAgenteAReportar(String nombreAgente){
        notificador.setIdentGestoraReportar(nombreAgente);
   }
    public void setItfAgenteAReportar(ItfUsoAgenteReactivo itfAgente){
//        panelTrazasNiveles.setItfGestoraReportar(itfAgente);
   }
   private PanelTrazasClasificadas  crearVisualizadorPrincipal (){
      if(  panelTrazasNiveles == null) panelTrazasNiveles= new PanelTrazasClasificadas( notificador,infoPanels);
      return panelTrazasNiveles;
   }
   public void activarVisualizacionTrazas (){
       activacionPanelTrazas = true;
   }
   public void desactivarVisualizacionTrazas (){
       activacionPanelTrazas = false;
   }   
    public synchronized void visualizarNuevaTraza(InfoTraza traza) {
         String idEntidad = traza.getEntidadEmisora(); 
         tipoEntidadEmisora= traza.getTipoEntidadEmisora();
         if ( tipoEntidadEmisora==null)tipoEntidadEmisora = NombresPredefinidos.TipoEntidad.noDefinido;
            if (activacionPanelTrazas){
                panelTrazasNiveles.setVisible(true);
                panelActual = this.getpanelParaVisualizar(tipoEntidadEmisora, idEntidad);   
                panelActual.muestraInfoTraza(traza);
                panelTrazasNiveles.muestraMensaje(traza);
   //           if (tablaPanelesEspecificos != null ) {
   //           panelTrazasNiveles.muestraMensaje(traza);             
      //        if(!idEntidad.equals(identUltimaEntidad))  panelTrazasNiveles.muestraMensaje(traza); 
            }else if (traza.getNivel()== InfoTraza.NivelTraza.error){
             //       activacionPanelTrazas = true;
                    panelTrazasNiveles.setVisible(true);
                    panelTrazasNiveles.muestraMensaje(traza);
                    panelActual = this.getpanelParaVisualizar(tipoEntidadEmisora, idEntidad);   
                    panelActual.muestraInfoTraza(traza);
            }
                   
    }   
    private PanelTrazasAbstracto  getpanelParaVisualizar(NombresPredefinidos.TipoEntidad tipoEnti,String idEntidad){
        if(!idEntidad.equals(identUltimaEntidad)){
            panelActual = (PanelTrazasAbstracto)tablaPanelesEspecificos.get(idEntidad);
            identUltimaEntidad = idEntidad;
        if (panelActual == null){
        //  if (tipoEnti.equals(NombresPredefinidos.TipoEntidad.noDefinido)) panelActual = new PanelTrazasGenerico(idEntidad, ""); 
        //  else if (tipoEnti.equals(NombresPredefinidos.TipoEntidad.Reactivo ))panelActual = new PanelTrazasAgteReactivo(idEntidad, "");
        //  else if(tipoEnti.equals(NombresPredefinidos.TipoEntidad.Reactivo)) panelActual =new PanelTrazasAgteCognitivo(idEntidad, "");
              //  tablaPanelesEspecificos.put(idEntidad, panelActual);
             panelActual= this.infoPanels.crearPanelparaEntidad(idEntidad, tipoEnti);
             panelTrazasNiveles.visualizarElementoTrazable( idEntidad);
      }
        }
      return panelActual;      
    }
    public synchronized void visualizarEnvioEvento(NombresPredefinidos.TipoEntidad tipoEnt,EventoSimple trazaEvento) {
        String identEntidad =  trazaEvento.getOrigen();
        panelActual = this.getpanelParaVisualizar(tipoEnt, identEntidad);
        panelActual.muestraEventoEnviado(trazaEvento);
    }
    public synchronized void visualizarEnvioMensaje(NombresPredefinidos.TipoEntidad tipoEnt,MensajeSimple trazaMensaje) {
        String identEntidad =  trazaMensaje.getEmisor().toString();
        panelActual = this.getpanelParaVisualizar(tipoEnt, identEntidad);
        panelActual.muestraMensajeEnviado(trazaMensaje);
    }
    public synchronized void visualizarRecibirMensaje(NombresPredefinidos.TipoEntidad tipoEnt,MensajeSimple trazaMensaje) {
        String identEntidad =  trazaMensaje.getReceptor().toString();
        panelActual = this.getpanelParaVisualizar(tipoEnt, identEntidad);
        panelActual.muestraMensajeRecibido(trazaMensaje);
    }
    public synchronized void visualizarRecibirEvento(NombresPredefinidos.TipoEntidad tipoEnt,EventoSimple trazaEvento) {
        String identEntidad =  trazaEvento.getOrigen();
        panelActual = this.getpanelParaVisualizar(tipoEnt, identEntidad);
        panelActual.muestraEventoRecibido(trazaEvento);
    }
    public synchronized void visualizarTrazaEjecReglas(String entityId, String  infoAtrazar) {
        panelActual = this.getpanelParaVisualizar(NombresPredefinidos.TipoEntidad.Cognitivo, entityId);
        panelActual.muestraTrazaEjecucionReglas(infoAtrazar);
    }
    public synchronized void visualizarTrazaActivacionReglas(String entityId, String  infoAtrazar) {
        panelActual = this.getpanelParaVisualizar(NombresPredefinidos.TipoEntidad.Cognitivo, entityId);
        panelActual.muestraTrazaActivacionReglas(infoAtrazar);
    }
     public void visualizarIdentFicheroDescrOrganizacion(){
          panelTrazasNiveles.visualizarInfoGeneral("Fichero Descripcion organizacion : "+ NombresPredefinidos.DESCRIPCION_XML_POR_DEFECTO);
     }
    public synchronized void pedirConfirmacionTerminacionAlUsuario() {
		int res = JOptionPane.showConfirmDialog(null, "Confirmar terminacion","Confirmar terminacion", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
		switch(res){
			case JOptionPane.YES_OPTION:{
				try {
//					ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) ClaseGeneradoraRepositorioInterfaces.instance()
//					.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
//					itfUsoAgente.aceptaEvento(new EventoRecAgte("terminacion_confirmada",NombresPredefinidos.RECURSO_TRAZAS,NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
                                        notificador.confirmarTerminacionOrganizacion();
				} catch (Exception e) {
					System.out.println("Ha habido un error al enviar el evento terminacion_confirmada al gestor de organizacin");
					e.printStackTrace();
				//	this.itfAutomata.transita("error");
				}
			}
			case JOptionPane.NO_OPTION:{
				try {
//					ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) ClaseGeneradoraRepositorioInterfaces.instance()
//					.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
//					itfUsoAgente.aceptaEvento(new EventoRecAgte("terminacion_anulada",NombresPredefinidos.RECURSO_TRAZAS,NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
				notificador.anularTerminacionOrganizacion();
                                } catch (Exception e) {
					System.out.println("Ha habido un error al enviar el evento terminacion_anulada al gestor de organizacin");
					e.printStackTrace();
				//	this.itfAutomata.transita("error");
				}
			}
		}
		
		
	}
    public void cerrarVentanas() {

        panelTrazasNiveles.cierraVentana();
    }
}
