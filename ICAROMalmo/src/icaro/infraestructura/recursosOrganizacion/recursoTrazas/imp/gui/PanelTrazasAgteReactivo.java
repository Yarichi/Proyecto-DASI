/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelTrazasEspecificas1.java
 *
 * Created on 01-dic-2010, 14:01:49
 */

package icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.gui;

import icaro.infraestructura.entidadesBasicas.comunicacion.EventoSimple;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author FGarijo
 */
public class PanelTrazasAgteReactivo extends PanelTrazasAbstracto  {

    private String nombreComponente; //identificacin de la ventana

    /** Creates new form PanelTrazasEspecificas1 */
    public PanelTrazasAgteReactivo(String nombre, String contenido) {
        initComponents();
        this.nombreComponente = nombre;
        this.setTitle(nombreComponente);
  //      this.labelTitulo.setText(nombreComponente);
        this.areaTrazaGeneral.setText(contenido);
        this.setResizable(true);
        
       
    }

    @Override
    public void cierraVentana(){
   	this.setVisible(false);
    }

    public String getIdentificador(){
    	return nombreComponente;
    }

    @Override
    public synchronized void muestraInfoTraza(InfoTraza traza){

    	String nivel;
    	Color c = new Color(0);
  //      c = Color.GREEN;
    	if (traza.getNivel() == InfoTraza.NivelTraza.debug){
    		nivel = "DEBUG";
    		c = Color.BLUE;
    	}
    	else if (traza.getNivel() == InfoTraza.NivelTraza.info){
    		nivel = "INFO";
    		c = Color.GREEN;
    	}
    	else if (traza.getNivel() == InfoTraza.NivelTraza.error){
    		nivel = "ERROR";
    		c = Color.ORANGE;
    	}
    	else { //fatal
    		nivel = "FATAL";
    		c = Color.RED;
    	}
    	Font f = new Font("Trebuchet",Font.PLAIN,12);
    	areaTrazaGeneral.setFont(f);
    	areaTrazaGeneral.setForeground(Color.BLUE);
    	//Concateno el nuevo mensaje con el que habia antes

    	areaTrazaGeneral.append(nivel+" : "+traza.getMensaje()+"\n");
    	//si escribo null,borra lo anterior
    }
    
    @Override
    public synchronized void muestraMensajeRecibido(MensajeSimple m){

    	String nivel = "";
    	Color c = new Color(0);
    	
    	Font f = new Font("Times",Font.PLAIN,12);
    	areaTrazaMensajes.setFont(f);
    	areaTrazaMensajes.setForeground(Color.black);
    	//Concateno el nuevo mensaje con el que habia antes

    	areaTrazaMensajes.append("Mensaje Recibido --> Emisor : "+m.getEmisor()+". Clase del Contenido: "+m.getContenido().getClass().getSimpleName()+"\n"
                        + "      Valores Contenido : "+"\n" +m.getContenido()+"\n");//+". Entidad emisora: "+traza.getEntidadEmisora()+"\n");
    	//si escribo null,borra lo anterior
    }
    @Override
    public synchronized void muestraMensajeEnviado(MensajeSimple m){

    	String nivel = "";
    	Color c = new Color(0);
    	c = Color.GREEN;
    	Font f = new Font("Times",Font.ITALIC,12);
        
    	areaTrazaMensajes.setFont(f);
    	areaTrazaMensajes.setForeground(Color.black);
    	//Concateno el nuevo mensaje con el que habia antes

    	areaTrazaMensajes.append("Mensaje Enviado--> Emisor : "+m.getEmisor()+"  envia   mensaje al agente : "+m.getReceptor()+ " Clase del Contenido: "+m.getContenido().getClass().getSimpleName()+"\n"
                        + "      Valores Contenido : "+"\n" +m.getContenido()+"\n");//+". Entidad emisora: "+traza.getEntidadEmisora()+"\n");
    	//si escribo null,borra lo anterior
       
    }

    @Override
    public synchronized void muestraEventoRecibido(EventoSimple m){

    	String nivel = "";
    	Color c = new Color(0);

    	Font f = new Font("Trebuchet",Font.PLAIN,12);
    	areaTrazaEventos.setFont(f);
    	areaTrazaEventos.setForeground(Color.BLUE);
    	//Concateno el nuevo mensaje con el que habia antes
        Object contenido = m.getContenido();
        if(contenido!=null){
//            if contenido.getClass().getSimpleName().equalsIgnoreCase(nivel)
            areaTrazaEventos.append("Evento Recibido.  Emisor : "+m.getOrigen()+". Tipo Contenido: "+m.getContenido().getClass().getSimpleName()+""
                            + ". Contenido : " +contenido.toString()+"\n");//+". Entidad emisora: "+traza.getEntidadEmisora()+"\n");
        }
    }
    
    @Override
    public synchronized void muestraEventoEnviado(EventoSimple m){
        Object contenido = m.getContenido();
        if(contenido!=null){
//            if contenido.getClass().getSimpleName().equalsIgnoreCase(nivel)
            areaTrazaEventos.append("Evento enviado.  Emisor : "+m.getOrigen()+". Tipo Contenido: "+m.getContenido().getClass().getSimpleName()+""
                            + ". Contenido : " +contenido.toString()+"\n");//+". Entidad emisora: "+traza.getEntidadEmisora()+"\n");
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane4 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaTrazas = new javax.swing.JTextArea();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        areaTrazaGeneral = new java.awt.TextArea();
        jPanel2 = new javax.swing.JPanel();
        areaTrazaMensajes = new java.awt.TextArea();
        jPanel3 = new javax.swing.JPanel();
        areaTrazaEventos = new java.awt.TextArea();

        areaTrazas.setColumns(20);
        areaTrazas.setRows(5);
        jScrollPane1.setViewportView(areaTrazas);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        areaTrazaGeneral.setBackground(new java.awt.Color(220, 247, 247));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(areaTrazaGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(areaTrazaGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Traza General", jPanel1);

        areaTrazaMensajes.setBackground(new java.awt.Color(230, 247, 230));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(areaTrazaMensajes, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(areaTrazaMensajes, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Traza de mensajes", jPanel2);

        areaTrazaEventos.setBackground(new java.awt.Color(246, 246, 220));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(areaTrazaEventos, javax.swing.GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(areaTrazaEventos, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Traza de eventos", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new PanelTrazasEspecificas1().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.TextArea areaTrazaEventos;
    private java.awt.TextArea areaTrazaGeneral;
    private java.awt.TextArea areaTrazaMensajes;
    private javax.swing.JTextArea areaTrazas;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables

}
