/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
import javax.swing.JFrame;

/**
 *
 * @author FGarijo
 */
public class PanelTrazasClasificadas extends javax.swing.JFrame {

    private NotificacionesRecTrazas notificador;
    private ClasificadorVisual clasificadorV;
    private InfoPanelesEspecificos infoPanelesEspecifics;
    private ArrayList<String> listaElementosTrazables;
    private String ultimaEntidadEmisora = null;
    ItfUsoAgenteReactivo itfGestorTerminacion;
    /** Creates new form PanelTrazasClasificadas1 */
    public PanelTrazasClasificadas( NotificacionesRecTrazas notif,InfoPanelesEspecificos infoPaneles) {
        initComponents();
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        notificador = notif;
        this.setTitle("Visualizacion Recurso de Trazas");
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
    public void visualizarInfoGeneral(String infoAtrazar)
    {
        // Se traza la informocion en el panel General y en el info
        areaGeneralMensaje.append(infoAtrazar+"\n");
        areaInfoMensaje.append(infoAtrazar+"\n");
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        label1 = new java.awt.Label();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaComponentes = new javax.swing.JList();
        button1 = new java.awt.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(223, 237, 175));
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        areaGeneralMensaje.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        Componente.setText("Componente");

        label2.setAlignment(java.awt.Label.CENTER);
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
                        .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(areaGeneralMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)))
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
                .addComponent(areaGeneralMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelPrincipal.addTab("General", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 153));

        Componente1.setText("Componente");

        label3.setAlignment(java.awt.Label.CENTER);
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
                        .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(areaInfoMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)))
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
                .addComponent(areaInfoMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelPrincipal.addTab("Info", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 102));

        areaDebugMensaje.setName("areaDebugMensaje"); // NOI18N

        Componente2.setText("Componente");

        label4.setAlignment(java.awt.Label.CENTER);
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
                        .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(areaDebugMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)))
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
                .addComponent(areaDebugMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelPrincipal.addTab("Debug", jPanel4);

        jPanel5.setBackground(new java.awt.Color(252, 216, 143));

        Componente3.setText("Componente");

        label5.setAlignment(java.awt.Label.CENTER);
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
                        .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(areaErrorMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)))
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
                .addComponent(areaErrorMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelPrincipal.addTab("Error", jPanel5);

        label1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        label1.setText("Recurso de Trazas");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setText("Elementos Trazables");

        listaComponentes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaComponentesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(listaComponentes);

        button1.setBackground(new java.awt.Color(181, 171, 204));
        button1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        button1.setLabel("Terminar");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelPrincipal)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50))))
            .addGroup(layout.createSequentialGroup()
                .addGap(306, 306, 306)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelPrincipal))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listaComponentesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaComponentesMouseClicked
         if (evt.getClickCount() == 2) {
    	             int index = listaComponentes.locationToIndex(evt.getPoint());
    	             listaComponentes.setSelectedIndex(index);
    	       //      clasificadorV.muestraVentanaEspecifica(listaComponentes.getSelectedValue().toString());
                     PanelTrazasAbstracto panel = (PanelTrazasAbstracto)infoPanelesEspecifics.getPanelEspecifico(listaComponentes.getSelectedValue().toString());
					double a = Math.random() * 700;
					panel.setLocation((int) a, 550);
					panel.setVisible(true);	
    	          }
   }//GEN-LAST:event_listaComponentesMouseClicked

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        notificador.pedirTerminacionOrganizacion();
    }//GEN-LAST:event_button1ActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /*
//         * Set the Nimbus look and feel
//         */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /*
//         * If Nimbus (introduced in Java SE 6) is not available, stay with the
//         * default look and feel. For details see
//         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(PanelTrazasClasificadas1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(PanelTrazasClasificadas1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(PanelTrazasClasificadas1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(PanelTrazasClasificadas1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /*
//         * Create and display the form
//         */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//
//            public void run() {
//                new PanelTrazasClasificadas1().setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Label Componente;
    private java.awt.Label Componente1;
    private java.awt.Label Componente2;
    private java.awt.Label Componente3;
    private java.awt.TextArea areaDebugMensaje;
    private java.awt.TextArea areaErrorMensaje;
    private java.awt.TextArea areaGeneralMensaje;
    private java.awt.TextArea areaInfoMensaje;
    private java.awt.Button button1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private java.awt.Label label5;
    private javax.swing.JList listaComponentes;
    private javax.swing.JTabbedPane panelPrincipal;
    // End of variables declaration//GEN-END:variables
}
