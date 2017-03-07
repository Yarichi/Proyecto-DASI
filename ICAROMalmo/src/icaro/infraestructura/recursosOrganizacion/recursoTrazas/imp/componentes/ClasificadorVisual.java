package icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoSimple;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstancia;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.NotificacionesRecTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.gui.*;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClasificadorVisual implements Serializable{

	private PanelTrazasClasificadas panelTrazasNiveles;
	//panel principal de trazas
	private HashMap tablaInfoPanelesEspecificos;
	//arraylist de informacin ventanas de trazas
	private InfoPanelesEspecificos tablaPanelesEspecificos;
	//arraylist que contiene los paneles visualizados
        public static int PANEL_GENERAl= 0;
        public static int PANEL_AGENTE_REACTIVO= 1;
        public static int PANEL_AGENTE_COGNITIVO= 2;
        public static int PANEL_RECURSO= 3;
        public static int PANEL_GENERICO= 3;
        private ItfUsoConfiguracion itfConfig ;
        private  List<String> listaElementosTrazables;
	private NotificacionesRecTrazas notificador;
        private PanelTrazasAbstracto panelActual ;

	
	public ClasificadorVisual(NotificacionesRecTrazas notifEventos){
		
//		List<String> listaElementosTrazables = getElementosTrazables();
//      TreeSet   listaElementosTrazables = getElementosTrazables();
//		notificador = new NotificacionesRecTrazas(this);
        notificador = notifEventos;
        listaElementosTrazables = new LinkedList<String>() ;
 //       tablaInfoPanelesEspecificos = new HashMap () ;
        tablaPanelesEspecificos = new InfoPanelesEspecificos() ;
		//pasamos la lista de elementos trazables a la interfaz grfica para permitir su traza
		panelTrazasNiveles = new PanelTrazasClasificadas( notificador, tablaPanelesEspecificos );

        panelTrazasNiveles.setVisible(true);
         
	/*
		//construimos los paneles especificos para que vayan trazando
		listaInfoPanelesEspecificos = new LinkedList<InfoPanelEspecifico>();
		for(int i=0;i<listaElementosTrazables.size();i++){
			InfoPanelEspecifico panel = new InfoPanelEspecifico(listaElementosTrazables.get(i).toString(),"");
			//panel.setVisible(false);
			listaInfoPanelesEspecificos.add(panel);
		}
		
		listaPanelesEspecificos = new LinkedList<PanelTrazasEspecificas>();
	*/
	}


    public void visualizarElementosTrazables (List<String> listaElementosTrazables){
     //     tablaInfoPanelesEspecificos = getElementosTrazables();
      if (listaElementosTrazables != null){
         tablaPanelesEspecificos = construirPanelesEspecificos( listaElementosTrazables);
      }
          /*
       //construimos los paneles especificos para que vayan trazando
      tablaInfoPanelesEspecificos = new HashMap();

      Iterator<String> iterador = conjuntoElementosTrazables.iterator();
      while(iterador.hasNext()){
          String identEntidad =  iterador.next();
        InfoPanelEspecifico panel = new InfoPanelEspecifico(identEntidad,"");
			//panel.setVisible(false);
			tablaInfoPanelesEspecificos.put(identEntidad, panel);
      }
      */
     //  tablaPanelesEspecificos = new HashMap();
    }
	
	public void setPanelPrincipal(PanelTrazasClasificadas p){this.panelTrazasNiveles = p;}
//	public void setArrayInfoPaneles(HashMap tabla){this.tablaInfoPanelesEspecificos = tabla;}
//	public void setArrayPaneles(HashMap tabla){this.tablaPanelesEspecificos = tabla;}
	
	public PanelTrazasClasificadas getPanelPrincipal(){return this.panelTrazasNiveles;}
//	public HashMap getArrayInfoPaneles(){return this.tablaInfoPanelesEspecificos;}
//	public HashMap getArrayPaneles(){return this.tablaPanelesEspecificos;}
	


    public void cerrarVentanas() {
		//elimino todas las ventanas
		this.getPanelPrincipal().dispose();
	//	HashMap tablaPaneles = this.getArrayPaneles();
        if (tablaPanelesEspecificos !=null ){
        String identPanel="";
	    Set conjIdentPanels = tablaPanelesEspecificos.getIdentsPenels();
        Iterator<String> iter = conjIdentPanels.iterator();
	    while (iter.hasNext()) {
	       identPanel = iter.next();
            PanelTrazasAgteReactivo panel = (PanelTrazasAgteReactivo) tablaPanelesEspecificos.getPanelEspecifico(identPanel);
            panel.dispose();
                }
            }
       }
  private InfoPanelesEspecificos construirPanelesEspecificos(List<String> listaElementosTrazables){
      // Se crea una lista de paneles y una tabla con las descripciones de
      // los paneles donde el identificador del panel es el identificador que se pasa en la lista
  //  HashMap tablaInfoPaneles = null;
      if (listaElementosTrazables != null){
         //construimos los paneles especificos para que se pueda  trazar en ellos
       //  tablaInfoPaneles = new HashMap();
       //   tablaInfoPaneles = new InfoPanelesEspecificos ();
//      tablaPanelesEspecificos = new HashMap();
            Iterator<String> iterador = listaElementosTrazables.iterator();
            while(iterador.hasNext()){
                String identEntidad =  iterador.next();
                InfoPanelEspecifico panel = new InfoPanelEspecifico(identEntidad,"");
                panel.setPanelEspecifico( new PanelTrazasAgteReactivo(identEntidad, ""));
                //panel.setVisible(false);
                tablaPanelesEspecificos.addNewPanelEspecifico( panel);
                
            }
          }
      return tablaPanelesEspecificos;
  }
public void muestraTrazaEnviarMensaje(MensajeSimple trazaMensaje){
    //muestraTraza(traza);
     panelActual = (PanelTrazasAbstracto)tablaPanelesEspecificos.getPanelEspecifico(trazaMensaje.getEmisor().toString());
    //panelActual.muestraMensaje(traza);
    // Se debe mostrar en el panel del agente emisor del mensaje
    panelActual.muestraMensajeEnviado(trazaMensaje);

}
public void muestraTrazaEnviarEvento(EventoSimple trazaEvento){
    //muestraTraza(traza);
     panelActual = (PanelTrazasAbstracto)tablaPanelesEspecificos.getPanelEspecifico(trazaEvento.getOrigen().toString());
    //panelActual.muestraMensaje(traza);
    // Se debe mostrar en el panel del agente emisor del mensaje
    panelActual.muestraEventoEnviado(trazaEvento);

}
public void muestraTrazaMensajeRecibido(MensajeSimple trazaMensaje){
    //muestraTraza(traza);
     panelActual = (PanelTrazasAbstracto)tablaPanelesEspecificos.getPanelEspecifico(trazaMensaje.getReceptor().toString());
    //panelActual.muestraMensaje(traza);
    panelActual.muestraMensajeRecibido(trazaMensaje);

}
public void muestraTrazaEventoRecibido(EventoSimple trazaEvento){
    //muestraTraza(traza);
     panelActual = (PanelTrazasAbstracto)tablaPanelesEspecificos.getPanelEspecifico(trazaEvento.getOrigen().toString());
    //panelActual.muestraMensaje(traza);
    panelActual.muestraEventoRecibido(trazaEvento);

}


public void muestraTrazaMensajeEvento(String id, EventoSimple ev){
    //muestraTraza(traza);
     panelActual = (PanelTrazasAbstracto)tablaPanelesEspecificos.getPanelEspecifico(id);
    //panelActual.muestraMensaje(traza);
    panelActual.muestraEventoRecibido(ev);

}
public void muestraTrazaActivacionReglas(String entityId, String infoAtrazar){
    //muestraTraza(traza);
     panelActual = (PanelTrazasAbstracto)tablaPanelesEspecificos.getPanelEspecifico(entityId);
    //panelActual.muestraMensaje(traza);
    panelActual.muestraTrazaActivacionReglas(infoAtrazar);

}
public void muestraTrazaEjecucionReglas(String entityId, String infoAtrazar){
    //muestraTraza(traza);
     panelActual = (PanelTrazasAbstracto)tablaPanelesEspecificos.getPanelEspecifico(entityId);
    //panelActual.muestraMensaje(traza);
    panelActual.muestraTrazaEjecucionReglas(infoAtrazar);

}
public synchronized void muestraTraza(InfoTraza traza) {
		// actualiza la nueva traza en todos los paneles

//		this.getPanelPrincipal().muestraMensaje(traza);
        panelTrazasNiveles.muestraMensaje(traza);
  //      if (tablaInfoPanelesEspecificos != null ) {
        String identElementTraza = traza.getEntidadEmisora();
        
        InfoPanelEspecifico panelInfoActual = (InfoPanelEspecifico) tablaPanelesEspecificos.getInfoPanel(identElementTraza);
        if (panelInfoActual != null){
         //   panelInfoActual.setContenido(panelInfoActual.getContenido()
	//					+ getMensajeNormalizado(traza) + "\n");
             panelActual = (PanelTrazasAbstracto) tablaPanelesEspecificos.getPanelEspecifico(identElementTraza);
    //        panelActual.muestraMensaje(traza);
        }
   /*     
        if (tablaInfoPanelesEspecificos.containsKey(identElementTraza)){
          InfoPanelEspecifico panelInfoActual = (InfoPanelEspecifico) tablaInfoPanelesEspecificos.get(identElementTraza);
       
          panelInfoActual.setContenido(panelInfoActual.getContenido()
						+ getMensajeNormalizado(traza) + "\n");
          PanelTrazasEspecificas panelActual = (PanelTrazasEspecificas)tablaPanelesEspecificos.get(identElementTraza);
                panelActual.muestraMensaje(traza);
                } */
                else {
               // creamos el info panel y visualizamos la ventana
         
    //                InfoPanelEspecifico infoPanel = new InfoPanelEspecifico(identElementTraza,"");
    //                PanelTrazasEspecificas panelEsp = new PanelTrazasEspecificas(identElementTraza, "");
    //                infoPanel.setPanelEspecifico(panelEsp);  
     //               tablaPanelesEspecificos.addNewPanelEspecifico(infoPanel);
     //               panelEsp.muestraMensaje(traza);
                    panelActual =  crearPanelparaEntidad (identElementTraza); 
                    panelTrazasNiveles.visualizarElementoTrazable( identElementTraza);
                
                  
 //                   muestraVentanaEspecifica(identElementTraza);
                     
   
         }
           panelActual.muestraInfoTraza(traza);     

      }
private PanelTrazasAbstracto crearPanelparaEntidad (String entityId){
    PanelTrazasAbstracto nuevoPanel = null;
    try {
            // Obtener el tipo de entidad
             if ((itfConfig == null)&& (NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ != null))
             itfConfig = (ItfUsoConfiguracion)NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz("Itf_Uso_Configuracion");
         } catch (Exception ex) {
            Logger.getLogger(ClasificadorVisual.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (itfConfig == null) nuevoPanel = new PanelTrazasAgteReactivo(entityId, "");
            else {
            DescInstancia descComponente =   itfConfig.getDescInstancia(entityId);
            if (descComponente == null)nuevoPanel = new PanelTrazasGenerico(entityId, "");
            else if (descComponente.getCategoriaComponente().equals(NombresPredefinidos.NOMBRE_ENTIDAD_AGENTE))
                    if( descComponente.getTipoComponente().equalsIgnoreCase("Cognitivo"))
                        nuevoPanel = new PanelTrazasAgteCognitivo(entityId, "");
                    else nuevoPanel = new PanelTrazasAgteReactivo(entityId, "");
                else if  (descComponente.getCategoriaComponente().equals(NombresPredefinidos.NOMBRE_ENTIDAD_RECURSO))
                        nuevoPanel = new PanelTrazasRecurso(entityId, "");
                    else nuevoPanel = new PanelTrazasGenerico(entityId, "");
            }          
            InfoPanelEspecifico infoPanel = new InfoPanelEspecifico(entityId,"");
            infoPanel.setPanelEspecifico(nuevoPanel);  
            tablaPanelesEspecificos.addNewPanelEspecifico(infoPanel);
        } catch (ExcepcionEnComponente ex) {
            Logger.getLogger(ClasificadorVisual.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ClasificadorVisual.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nuevoPanel;
}

private String getMensajeNormalizado(InfoTraza traza) {
		String nivel = "";

		if (traza.getNivel() == InfoTraza.NivelTraza.debug) {
			nivel = "DEBUG";
		} else if (traza.getNivel() == InfoTraza.NivelTraza.info) {
			nivel = "INFO";
		} else if (traza.getNivel() == InfoTraza.NivelTraza.error) {
			nivel = "ERROR";
		} else { // fatal
			nivel = "FATAL";
		}

		return nivel + " : " + traza.getMensaje();

	}

    public void cierraVentanaPrincipal() {
		this.panelTrazasNiveles.setVisible(false);

	}
    public void setItfConfiguracion(ItfUsoConfiguracion itfConf) {
	// lo define el iniciador via la clase de implementacion	
        this.itfConfig =itfConf;
                

	}
	public void cierraVentanaEspecifica(String nombreVentana) {

           InfoPanelEspecifico panelActual = tablaPanelesEspecificos.getInfoPanel(nombreVentana);
            if (panelActual !=null){
                panelActual.getPanelEspecifico().setVisible(false);
//       if (tablaPanelesEspecificos.containsKey(nombreVentana)){
//            PanelTrazasEspecificas panelActual = (PanelTrazasEspecificas)tablaPanelesEspecificos.get(nombreVentana);
//                panelActual.setVisible(false);
            
			
            
            }
}
  
}
