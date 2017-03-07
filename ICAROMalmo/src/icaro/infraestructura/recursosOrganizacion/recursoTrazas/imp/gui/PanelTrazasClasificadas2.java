/*//GEN-FIRST:event_listaComponentesMouseClicked
 * To change this template, choose Tools | Templates//GEN-LAST:event_listaComponentesMouseClicked
 * and open the template in the editor.
 */

/*
 * PanelTrazasClasificadas1.java
 *
 * Created on 18-abr-2009, 14:20:06
 */

package icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.gui;

import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.NotificacionesRecTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.ClasificadorVisual;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoPanelesEspecificos;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

/**
 *
 * @author Francisco J Garijo
 */
public class PanelTrazasClasificadas2 extends javax.swing.JFrame {
    private NotificacionesRecTrazas notificador;
    private ClasificadorVisual clasificadorV;
    private InfoPanelesEspecificos infoPanelesEspecifics;
    private ArrayList<String> listaElementosTrazables;
    private String ultimaEntidadEmisora = null;
    ItfUsoAgenteReactivo itfGestorTerminacion;
    /** Creates new form PanelTrazasClasificadas1 */
    public PanelTrazasClasificadas2( NotificacionesRecTrazas notif,InfoPanelesEspecificos infoPaneles) {
        initComponents();
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        notificador = notif;
  //      clasificadorV = c;
        infoPanelesEspecifics = infoPaneles;
        listaElementosTrazables = new ArrayList<String>();
//        this.listaElementosTrazables =listaElementosaTrazar;
 //      if (listaElementosaTrazar != null ){
//           this.visualizar_componentes_trazables(listaElementosaTrazar);
 //       }

    }
     public void cierraVentana(){
    	this.setVisible(false);
    }

  /*  public void visualizar_componentes_trazables(List<String> listaElementosaTrazar)
    {
        listaElementosTrazables = listaElementosaTrazar.toArray();
        this.listaComponentes.setListData(listaElementosaTrazar.toArray());
    }*/

    public void visualizarElementoTrazable(String elemento)
    {
       // listaComponentes.add
        if(!listaElementosTrazables.contains(elemento)){
            listaElementosTrazables.add(elemento);
            this.listaComponentes.setListData(listaElementosTrazables.toArray());
        }
        
    }
//    public void setItfAgenteAReportar(ItfUsoAgenteReactivo itfAgente){
//        itfGestorTerminacion = itfAgente;
//    }
 public void muestraMensaje(InfoTraza traza){

    	String nivel = "";
    	Color c = new Color(0);
    	Font f = new Font("Trebuchet",Font.PLAIN,14);
        String identEntidadEmisora  = traza.getEntidadEmisora();
        // En el panel principal solo mostramos la informacion de las entidades que envian informacion
        
                Boolean identEntidadIgualAnterior = identEntidadEmisora.equals(ultimaEntidadEmisora);
 
/*  
        if (traza.getNivel() == InfoTraza.NivelTraza.debug ){
            if ( !identEntidadIgualAnterior ) {
            nivel = "DEBUG";
    		c = Color.LIGHT_GRAY;
    		
    		areaDebugMensaje.setFont(f);
    		areaDebugMensaje.setForeground(c);
            //areaDebugMensaje.append(traza.getEntidadEmisora()+"\t"+traza.getMensaje()+"\n");
                areaInfoMensaje.append(traza.getEntidadEmisora()+"\t"+"Nueva informacion: ver detalles en ventana especifica"+"\n");
            
    	}
    	else if (traza.getNivel() == InfoTraza.NivelTraza.info){
            if ( !identEntidadIgualAnterior ) {	
            nivel = "INFO";
    		c = Color.BLUE;
    		areaInfoMensaje.setFont(f);
    		areaInfoMensaje.setForeground(c);
    		//areaInfoMensaje.append(traza.getEntidadEmisora()+"\t"+traza.getMensaje()+"\n");
              //  areaInfoMensaje.append(traza.getEntidadEmisora()+"\t"+"Nueva informacion: ver detalles en ventana especifica"+"\n");
    		areaGeneralMensaje.append(traza.getEntidadEmisora()+"\t"+traza.getMensaje()+"\n");
            }
            
    	}
        */
    	 if (traza.getNivel() == InfoTraza.NivelTraza.error){
    		nivel = "ERROR";
    		c = Color.RED;
    		areaErrorMensaje.setFont(f);
    		areaErrorMensaje.setForeground(c);
    		areaErrorMensaje.append(traza.getEntidadEmisora()+"\t"+traza.getMensaje()+"\n");
    		areaGeneralMensaje.append(traza.getEntidadEmisora()+"\t"+traza.getMensaje()+"\n");
    		areaErrorMensaje.setVisible(true);
                panelPrincipal.setSelectedIndex(3); // Viusualiza el panel de error
            /*areaErrorMensaje.setText(areaErrorMensaje.getText()+"\n"+
    				traza.getNombre()+"\t"+traza.getMensaje());
    				*/
    	}
   /*      
    	else  if(traza.getNivel() == InfoTraza.NivelTraza.asignacion){ //fatal
    		nivel = "FATAL";
    		c = Color.DARK_GRAY;
    		areaFatalMensaje.setFont(f);
    		areaFatalMensaje.setForeground(c);
    		areaFatalMensaje.append(traza.getEntidadEmisora()+"\t"+traza.getMensaje()+"\n");
    		// areaGeneralMensaje.append(traza.getEntidadEmisora()+"\t"+traza.getMensaje()+"\n");
    		areaFatalMensaje.setVisible(true);
                panelPrincipal.setSelectedIndex(4);
                /*areaFatalMensaje.setText(areaFatalMensaje.getText()+"\n"+
    				traza.getNombre()+"\t"+traza.getMensaje());
         
         
               }*/else if ( !identEntidadIgualAnterior ) areaGeneralMensaje.append(traza.getEntidadEmisora()+"\t"+traza.getMensaje()+"\n");	
        
        ultimaEntidadEmisora = identEntidadEmisora;
    	/*
		c = Color.BLACK;

    	areaGeneralMensaje.setFont(f);
    	areaGeneralMensaje.setForeground(c);
    	areaGeneralMensaje.setText(areaGeneralMensaje.getText()+"\n"+
    			traza.getNombre()+"\t"+traza.getMensaje());
    	*/
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        jPanel1 = new javax.swing.JPanel();
        panelPrincipal = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        areaGeneralMensaje = new java.awt.TextArea();
        Componente = new java.awt.Label();
        label2 = new java.awt.Label();
        jPanel3 = new javax.swing.JPanel();
        areaInfoMensaje = new java.awt.TextArea();
        Componente1 = new java.awt.Label();
        label3 = new java.awt.Label();
        jPanel4 = new javax.swing.JPanel();
        areaDebugMensaje = new java.awt.TextArea();
        Componente2 = new java.awt.Label();
        label4 = new java.awt.Label();
        jPanel5 = new javax.swing.JPanel();
        areaErrorMensaje = new java.awt.TextArea();
        Componente3 = new java.awt.Label();
        label5 = new java.awt.Label();
//        jPanel6 = new javax.swing.JPanel();
        areaFatalMensaje = new java.awt.TextArea();
        Componente4 = new java.awt.Label();
//        label6 = new java.awt.Label();
        label1 = new java.awt.Label();
        jSeparator1 = new javax.swing.JSeparator();
        button1 = new java.awt.Button();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaComponentes = new javax.swing.JList();

        jScrollPane3.setViewportView(jTextPane2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 255, 255));
        setForeground(new java.awt.Color(204, 255, 255));

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        jPanel2.setBackground(new java.awt.Color(223, 237, 175));
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        areaGeneralMensaje.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        Componente.setText("Componente");

        label2.setText("Mensaje");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(Componente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(123, 123, 123)
                        .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(areaGeneralMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Componente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(areaGeneralMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                .addContainerGap())
        );

        Componente.getAccessibleContext().setAccessibleName("jlabel1");

        panelPrincipal.addTab("General", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 153));

        Componente1.setText("Componente");

        label3.setText("Mensaje");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(Componente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(123, 123, 123)
                        .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(areaInfoMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Componente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(areaInfoMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelPrincipal.addTab("Info", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 102));

        areaDebugMensaje.setName("areaDebugMensaje"); // NOI18N

        Componente2.setText("Componente");

        label4.setText("Mensaje");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(Componente2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(123, 123, 123)
                        .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(areaDebugMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Componente2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(areaDebugMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelPrincipal.addTab("Debug", jPanel4);

        jPanel5.setBackground(new java.awt.Color(252, 216, 143));

        Componente3.setText("Componente");

        label5.setText("Mensaje");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(Componente3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(123, 123, 123)
                        .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(areaErrorMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Componente3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(areaErrorMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelPrincipal.addTab("Error", jPanel5);

//        jPanel6.setBackground(new java.awt.Color(252, 216, 143));

        Componente4.setText("Componente");

//        label6.setText("Mensaje");

//        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
//        jPanel6.setLayout(jPanel6Layout);
//        jPanel6Layout.setHorizontalGroup(
//            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel6Layout.createSequentialGroup()
//                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addGroup(jPanel6Layout.createSequentialGroup()
//                        .addGap(21, 21, 21)
//                        .addComponent(Componente4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                        .addGap(123, 123, 123)
//                        .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
//                    .addGroup(jPanel6Layout.createSequentialGroup()
//                        .addContainerGap()
//                        .addComponent(areaFatalMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE)))
//                .addContainerGap())
//        );
//        jPanel6Layout.setVerticalGroup(
//            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel6Layout.createSequentialGroup()
//                .addContainerGap()
//                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
//                    .addComponent(Componente4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addComponent(areaFatalMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
//                .addContainerGap())
//        );
//
//        panelPrincipal.addTab("Asignacion", jPanel6);

        label1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        label1.setText("Recurso de Trazas");

        button1.setBackground(new java.awt.Color(181, 171, 204));
        button1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        button1.setLabel("Terminar");
        button1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setText("Elementos Trazables");

        listaComponentes.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaComponentesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(listaComponentes);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelPrincipal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(20, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(300, 300, 300)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 540, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 984, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(206, 206, 206))
                    .addComponent(panelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {                                        
        notificador.pedirTerminacionOrganizacion();
    }                                       

    private void listaComponentesMouseClicked(java.awt.event.MouseEvent evt) {                                              
        
       if (evt.getClickCount() == 2) {
    	             int index = listaComponentes.locationToIndex(evt.getPoint());
    	             listaComponentes.setSelectedIndex(index);
    	       //      clasificadorV.muestraVentanaEspecifica(listaComponentes.getSelectedValue().toString());
                     PanelTrazasAbstracto panel = (PanelTrazasAbstracto)infoPanelesEspecifics.getPanelEspecifico(listaComponentes.getSelectedValue().toString());
					double a = Math.random() * 700;
					panel.setLocation((int) a, 550);
					panel.setVisible(true);	
    	          }
    }                                             


    // Variables declaration - do not modify                     
    private java.awt.Label Componente;
    private java.awt.Label Componente1;
    private java.awt.Label Componente2;
    private java.awt.Label Componente3;
    private java.awt.Label Componente4;
    private java.awt.TextArea areaDebugMensaje;
    private java.awt.TextArea areaErrorMensaje;
    private java.awt.TextArea areaFatalMensaje;
    private java.awt.TextArea areaGeneralMensaje;
    private java.awt.TextArea areaInfoMensaje;
    private java.awt.Button button1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
//    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextPane jTextPane2;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private java.awt.Label label5;
//    private java.awt.Label label6;
    private javax.swing.JList listaComponentes;
    private javax.swing.JTabbedPane panelPrincipal;
    // End of variables declaration                   

}
