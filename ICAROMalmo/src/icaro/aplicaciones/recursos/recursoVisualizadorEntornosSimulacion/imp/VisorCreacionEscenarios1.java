/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ControlCenterGui2.java
 *
 * Created on 29-dic-2011, 20:42:57
 */
package icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp;

import icaro.aplicaciones.Rosace.informacion.RobotStatus1;
import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.imp.ReadXMLTestRobots;
import icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.imp.ReadXMLTestSequence;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.animator.SceneAnimator;
import org.netbeans.api.visual.model.ObjectScene;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;
import org.openide.util.Exceptions;
import org.openide.util.Utilities;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author FGarijo
 */
public class VisorCreacionEscenarios1 extends javax.swing.JFrame {

    /** Creates new form ControlCenterGui2 */
    private NotificadorInfoUsuarioSimulador notifEvts;
    private int intervaloSecuencia = 10000; // valor por defecto. Eso deberia ponerse en otro sitio
    private int numMensajesEnviar = 3;
    private boolean primeraVictima = true;
    private ArrayList identsRobotsEquipo ;
    private javax.swing.JLabel jLabelAux;
    private String directorioTrabajo;
     private String tituloVentanaVisor = "ROSACE Scenario Visor";
    private String rutassrc = "src/";   //poner "src/main/java" si el proyecto de icaro se monta en un proyecto maven
    private String rutapaqueteConstructorEscenariosROSACE = "utilsDiseniaEscenariosRosace/";
    private static  Image IMAGErobot,IMAGEmujer,IMAGEmujerRes ;
    private String rutaIconos = "\\src\\utilsDiseniaEscenariosRosace\\";
//    private String rutaPersistenciaEscenario = "\\src\\persistenciaEscenarios\\";
    private String directorioPersistencia = VocabularioRosace.NombreDirectorioPersistenciaEscenarios+File.separator;
     private String imageniconoHombre = "Hombre.png";
    private String imageniconoMujer = "Mujer.png";
    private String imageniconoMujerRescatada = "MujerRescatada.png";
    private String imageniconoHombreRescatado = "HombreRescatado.png";
    private String imageniconoRobot = "Robot.png";
    private String modeloOrganizativoInicial = "Igualitario";
    private Map<String, JLabel> tablaEntidadesEnEscenario;
    private ArrayList <JLabel> listaEntidadesEnEscenario;
    private JPanel panelVisor;
    private ObjectScene scene;
    private LayerWidget layer;
    JLabel entidadSeleccionada=null;
    private WidgetAction moveAction = ActionFactory.createMoveAction ();
    private Point ultimoPuntoClic ;
    private SceneAnimator animator ;
    private boolean intencionUsuarioCrearRobot;
    private boolean intencionUsuarioCrearVictima;
    private boolean entidadSeleccionadaParaMover;
    private int numeroRobots, mumeroVictimas;
    private volatile GestionEscenariosSimulacion gestionEscComp;
    private volatile EscenarioSimulacionRobtsVictms escenarioActualComp;
    private ComponentMover moverComp;
    private ControladorGestionEscenariosRosace controladorSim;
    private volatile PersistenciaVisualizadorEscenarios persistencia;
    private String modeloOrganizativo;
    private String identEquipoActual;
    private File ultimoFicheroEscenarioSeleccionado;

     public  VisorCreacionEscenarios1(ControladorGestionEscenariosRosace controlador) throws Exception {
//        super("visor Escenario ");
        initComponents();
        initEscenario();
        moverComp =new ComponentMover();
        moverComp.addMenuAcciones(jPopupMenuAcionEntidad);
        controladorSim = controlador;
        
        
//        identEquipoActual = escenarioActualComp.getIdentEscenario();
//        actualizarInfoEquipoEnEscenario();
//        leerInfoEscenario();
        directorioTrabajo = System.getProperty("user.dir");
        numeroRobots=0;  mumeroVictimas=0;
//        tablaEntidadesEnEscenario = new HashMap<String, JLabel>();
        listaEntidadesEnEscenario = new ArrayList < JLabel>();

    }
   
    private void initEscenario(){
        String rutaIconoRobot =   rutapaqueteConstructorEscenariosROSACE + imageniconoRobot;
        IMAGErobot = Utilities.loadImage (rutaIconoRobot);
        IMAGEmujerRes = Utilities.loadImage ( rutapaqueteConstructorEscenariosROSACE +imageniconoMujerRescatada); 
        IMAGEmujer = Utilities.loadImage ( rutapaqueteConstructorEscenariosROSACE +imageniconoMujer);
//        JLabel label = new javax.swing.JLabel("RobotPrueba");
//            String rutaIconoRobot = directorioTrabajo + "/" + rutassrc + rutapaqueteConstructorEscenariosROSACE + imageniconoRobot;

//       label.setIcon(new javax.swing.ImageIcon(rutaIconoRobot));     //System.out.println("Ruta->" + rutaIconoRobot);
//       label.createImage(10,10);
//            this.getRootPane().add(label);
//            this.repaint();
    }

        public synchronized void cambiarIconoVictimaARescatada(String valor_idVictima) {
        System.out.println("se cambia el icono de la victima a rescatada: "+valor_idVictima );
//        ((IconNodeWidget)scene.findWidget(valor_idVictima)).setImage(IMAGEmujerRes);
 
//        int numeroIdVictima = Integer.parseInt(numeroVictima);
//
//        JLabel jlabelVictima = new JLabel();
//
//        jlabelVictima = victimaslabel.get(numeroVictima);
//
//        if (jlabelVictima != null) {
//
//            //String rutaAbsolutaIconoVictima = jlabelVictima.getIcon().toString();			
//            //System.out.println("victima " + numeroVictima + "  , " + jlabelVictima.getIcon().toString());
//
//            if (numeroIdVictima % 2 == 0) {
//                jlabelVictima.setIcon(new javax.swing.ImageIcon(directorioTrabajo + "/" + rutassrc + rutapaqueteConstructorEscenariosROSACE + "HombreRescatado.png"));
//                //System.out.println("Es un hombre");
//            } else {
//                jlabelVictima.setIcon(new javax.swing.ImageIcon(directorioTrabajo + "/" + rutassrc + rutapaqueteConstructorEscenariosROSACE + "MujerRescatada.png"));
//                //System.out.println("Es una mujer");
//            }
//
//        } else {
//            System.out.println("jlabelVictima nulo");
//        }
    System.out.println("se cambia el icono de la victima a rescatada");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialogAvisoErrorDefNumEntidades = new javax.swing.JDialog();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPopupMenuAcionEntidad = new javax.swing.JPopupMenu();
        jMenuItemEliminar = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItemGuardar = new javax.swing.JMenuItem();
        jPopupMenuAddEntidades = new javax.swing.JPopupMenu();
        jMenuItemAddRobot = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItemAddVictima = new javax.swing.JMenuItem();
        jFileChooser1 = new javax.swing.JFileChooser();
        jOptionPane1 = new javax.swing.JOptionPane();
        jTextFieldModeloOrganizacion = new javax.swing.JTextField();
        robotIcon = new javax.swing.JLabel();
        intervalNumRobots = new javax.swing.JTextField();
        victimaIcon1 = new javax.swing.JLabel();
        intervalNumVictimas = new javax.swing.JTextField();
        jTextFieldIdentEquipo = new javax.swing.JTextField();
        jButtonGuardarEscenario = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JSeparator();
        jLabelOrganizacion = new javax.swing.JLabel();
        jLabelIdentEquipo = new javax.swing.JLabel();
        GestionEscenarios = new javax.swing.JMenuBar();
        jMenuEditarEscenario = new javax.swing.JMenu();
        jMenuItemNuevoEscenario = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItemAbrir = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuItemEliminarEscenario = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        jMenuItemGuardarEscenario = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItemSalir = new javax.swing.JMenuItem();
        jMenuOrganizacion = new javax.swing.JMenu();
        jMenuItemModeloJerarquico = new javax.swing.JMenuItem();
        jMenuItemModeloIgualitario = new javax.swing.JMenuItem();
        jMenuItemModeloOtros = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItemCrearRobot = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItemCrearVictima = new javax.swing.JMenuItem();

        jDialogAvisoErrorDefNumEntidades.setTitle("Error: Definición de entidades en el escenario");
        jDialogAvisoErrorDefNumEntidades.setBounds(new java.awt.Rectangle(20, 20, 335, 88));
        jDialogAvisoErrorDefNumEntidades.setType(java.awt.Window.Type.POPUP);

        jButton1.setText("Aceptar");

        jLabel1.setText("El numero de entidades no puede ser mayor que 20");

        javax.swing.GroupLayout jDialogAvisoErrorDefNumEntidadesLayout = new javax.swing.GroupLayout(jDialogAvisoErrorDefNumEntidades.getContentPane());
        jDialogAvisoErrorDefNumEntidades.getContentPane().setLayout(jDialogAvisoErrorDefNumEntidadesLayout);
        jDialogAvisoErrorDefNumEntidadesLayout.setHorizontalGroup(
            jDialogAvisoErrorDefNumEntidadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogAvisoErrorDefNumEntidadesLayout.createSequentialGroup()
                .addGap(122, 122, 122)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jDialogAvisoErrorDefNumEntidadesLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jDialogAvisoErrorDefNumEntidadesLayout.setVerticalGroup(
            jDialogAvisoErrorDefNumEntidadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialogAvisoErrorDefNumEntidadesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1))
        );

        jPopupMenuAcionEntidad.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                jPopupMenuAcionEntidadPopupMenuWillBecomeVisible(evt);
            }
        });
        jPopupMenuAcionEntidad.addMenuKeyListener(new javax.swing.event.MenuKeyListener() {
            public void menuKeyTyped(javax.swing.event.MenuKeyEvent evt) {
            }
            public void menuKeyPressed(javax.swing.event.MenuKeyEvent evt) {
                jPopupMenuAcionEntidadMenuKeyPressed(evt);
            }
            public void menuKeyReleased(javax.swing.event.MenuKeyEvent evt) {
            }
        });

        jMenuItemEliminar.setText("Eliminar");
        jMenuItemEliminar.setToolTipText("");
        jMenuItemEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEliminarActionPerformed(evt);
            }
        });
        jPopupMenuAcionEntidad.add(jMenuItemEliminar);
        jPopupMenuAcionEntidad.add(jSeparator3);

        jMenuItemGuardar.setText("Guardar");
        jMenuItemGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemGuardarActionPerformed(evt);
            }
        });
        jPopupMenuAcionEntidad.add(jMenuItemGuardar);

        jMenuItemAddRobot.setText("Añadir Robot");
        jMenuItemAddRobot.setActionCommand("AddRobot");
        jMenuItemAddRobot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAddRobotActionPerformed(evt);
            }
        });
        jPopupMenuAddEntidades.add(jMenuItemAddRobot);
        jPopupMenuAddEntidades.add(jSeparator4);

        jMenuItemAddVictima.setText("Añadir Victima");
        jMenuItemAddVictima.setActionCommand("AddVictima");
        jMenuItemAddVictima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAddVictimaActionPerformed(evt);
            }
        });
        jPopupMenuAddEntidades.add(jMenuItemAddVictima);

        jFileChooser1.setDialogTitle("Seleccion de escenario");
        jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooser1ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Creación de Escenarios");
        setMinimumSize(new java.awt.Dimension(30, 30));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jTextFieldModeloOrganizacion.setName("Modelo Organizacion"); // NOI18N

        robotIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilsDiseniaEscenariosRosace/Robot.png"))); // NOI18N
        robotIcon.setText("Robots");
        robotIcon.setIconTextGap(2);

        intervalNumRobots.setText("0");
        intervalNumRobots.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                intervalNumRobotsActionPerformed(evt);
            }
        });

        victimaIcon1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        victimaIcon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilsDiseniaEscenariosRosace/Mujer.png"))); // NOI18N
        victimaIcon1.setText("Victimas");
        victimaIcon1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        victimaIcon1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        victimaIcon1.setIconTextGap(2);

        intervalNumVictimas.setText("0");
        intervalNumVictimas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                intervalNumVictimasActionPerformed(evt);
            }
        });

        jTextFieldIdentEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldIdentEquipoActionPerformed(evt);
            }
        });

        jButtonGuardarEscenario.setText("Guardar");
        jButtonGuardarEscenario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonGuardarEscenarioMousePressed(evt);
            }
        });
        jButtonGuardarEscenario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarEscenarioActionPerformed(evt);
            }
        });

        jLabelOrganizacion.setText("Organización");

        jLabelIdentEquipo.setText("Ident Escenario");

        jMenuEditarEscenario.setText("Edición");

        jMenuItemNuevoEscenario.setText("Nuevo Escenario");
        jMenuItemNuevoEscenario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNuevoEscenarioActionPerformed(evt);
            }
        });
        jMenuEditarEscenario.add(jMenuItemNuevoEscenario);
        jMenuEditarEscenario.add(jSeparator5);

        jMenuItemAbrir.setText("Abrir");
        jMenuItemAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAbrirActionPerformed(evt);
            }
        });
        jMenuEditarEscenario.add(jMenuItemAbrir);
        jMenuEditarEscenario.add(jSeparator6);

        jMenuItemEliminarEscenario.setText("Eliminar");
        jMenuItemEliminarEscenario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEliminarEscenarioActionPerformed(evt);
            }
        });
        jMenuEditarEscenario.add(jMenuItemEliminarEscenario);
        jMenuEditarEscenario.add(jSeparator8);

        jMenuItemGuardarEscenario.setText("Guardar");
        jMenuItemGuardarEscenario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemGuardarEscenarioActionPerformed(evt);
            }
        });
        jMenuEditarEscenario.add(jMenuItemGuardarEscenario);
        jMenuEditarEscenario.add(jSeparator1);

        jMenuItemSalir.setText("Salir");
        jMenuEditarEscenario.add(jMenuItemSalir);

        GestionEscenarios.add(jMenuEditarEscenario);

        jMenuOrganizacion.setText("Organizacion del equipo");

        jMenuItemModeloJerarquico.setText("Jerarquico");
        jMenuItemModeloJerarquico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemModeloJerarquicoActionPerformed(evt);
            }
        });
        jMenuOrganizacion.add(jMenuItemModeloJerarquico);

        jMenuItemModeloIgualitario.setText("Igualitario");
        jMenuItemModeloIgualitario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemModeloIgualitarioActionPerformed(evt);
            }
        });
        jMenuOrganizacion.add(jMenuItemModeloIgualitario);

        jMenuItemModeloOtros.setText("Otros");
        jMenuOrganizacion.add(jMenuItemModeloOtros);

        GestionEscenarios.add(jMenuOrganizacion);

        jMenu3.setText("Añadir entidad");

        jMenuItemCrearRobot.setText("Robot");
        jMenuItemCrearRobot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCrearRobotActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItemCrearRobot);
        jMenu3.add(jSeparator2);

        jMenuItemCrearVictima.setText("Victima");
        jMenuItemCrearVictima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCrearVictimaActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItemCrearVictima);

        GestionEscenarios.add(jMenu3);

        setJMenuBar(GestionEscenarios);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelOrganizacion, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldModeloOrganizacion, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(robotIcon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(intervalNumRobots, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(victimaIcon1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(intervalNumVictimas, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelIdentEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldIdentEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonGuardarEscenario)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(intervalNumVictimas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(victimaIcon1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(intervalNumRobots, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(robotIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldIdentEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonGuardarEscenario)
                    .addComponent(jTextFieldModeloOrganizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelOrganizacion)
                    .addComponent(jLabelIdentEquipo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(538, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here
        if (evt.getButton()==3){
            jPopupMenuAddEntidades.show(this,evt.getX(),evt.getY());
            ultimoPuntoClic = new Point(evt.getX(),evt.getY());
        }else {
        String tipoEntidad="Robot";
         if(intencionUsuarioCrearVictima)tipoEntidad="Victima";
         Point puntoCursor = MouseInfo.getPointerInfo().getLocation();
         this.crearIconoRobVict(tipoEntidad,puntoCursor.x,puntoCursor.y );
        }
    }//GEN-LAST:event_formMouseClicked

    private void jButtonGuardarEscenarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarEscenarioActionPerformed
        // TODO add your handling code here:
        System.out.println("Ha pulsado el botón Guardar escenario");
        actualizarCoordenadasEntidades();
        controladorSim.peticionGuardarEscenario(escenarioActualComp);
//        String valor ;
//        setLocationRelativeTo(this);
//        escenarioActualComp.setIdentEscenario(jTextFieldIdentEquipo.getText());
//        persistencia.guardarInfoEscenarioSimulacion(directorioPersistencia, escenarioActualComp);
//
//        //         String smsg = "Puede cambiar el valor en milisegundos en que deben enviarse las coordenadas";
//
//        String smsg = "Se va a guardar el escenario: " +jTextFieldIdentEquipo.getText() ;
//        JOptionPane.showConfirmDialog(rootPane, smsg,"Confirmar GuardarEscenario",JOptionPane.OK_CANCEL_OPTION );
        //         jOptionPaneAvisoError.setToolTipText(smsg);
        //         jOptionPaneAvisoError.setLocation(20,20);
        //         jOptionPaneAvisoError.setVisible(true);
        //         this.add(jOptionPaneAvisoError);
        //         this.validate();

    }//GEN-LAST:event_jButtonGuardarEscenarioActionPerformed

    private void intervalNumRobotsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_intervalNumRobotsActionPerformed
        // TODO add your handling code here:
        int num1 = Integer.parseInt(intervalNumRobots.getText());
        System.out.println("Numero de robots");
                if (num1>20 ){
//                    JOptionPane.showInputDialog(rootPane,"El numero de robots tiene que ser menor que 20");
           JOptionPane.showMessageDialog(rootPane,"El numero de robots tiene que ser menor que 20","Error en Numero Entidades", JOptionPane.ERROR_MESSAGE);
               
                }else System.out.println("Definido el numero de robots : "+ num1);

    }//GEN-LAST:event_intervalNumRobotsActionPerformed

    private void jMenuItemGuardarEscenarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemGuardarEscenarioActionPerformed
        // TODO add your handling code here:
         // TODO add your handling code here:
        actualizarCoordenadasEntidades();
        controladorSim.peticionGuardarEscenario (escenarioActualComp);
//         System.out.println("Ha pulsado el botón Aceptar valores Robots y victimas");
//        String valor ;
//        setLocationRelativeTo(this);
//        escenarioActualComp.setIdentEscenario(jTextFieldIdentEquipo.getText());
//        persistencia.guardarInfoEscenarioSimulacion(directorioPersistencia, escenarioActualComp);
//
//        //         String smsg = "Puede cambiar el valor en milisegundos en que deben enviarse las coordenadas";
//
//        String smsg = "Se va a guardar el escenario: " +jTextFieldIdentEquipo.getText() ;
//        JOptionPane.showConfirmDialog(rootPane, smsg,"Confirmar GuardarEscenario",JOptionPane.OK_CANCEL_OPTION );
//        //         jOptionPaneAvisoError.setToolTipText(smsg);
    }//GEN-LAST:event_jMenuItemGuardarEscenarioActionPerformed

    private void jButtonGuardarEscenarioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonGuardarEscenarioMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonGuardarEscenarioMousePressed

    private void intervalNumVictimasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_intervalNumVictimasActionPerformed
        // TODO add your handling code here:
        int num1 = Integer.parseInt(intervalNumVictimas.getText());
        System.out.println("Numero de victimas");
                if (num1>20 ){
                    JOptionPane.showMessageDialog(rootPane,"El numero de victimas tiene que ser menor que 20","Error en Numero Entidades", JOptionPane.ERROR_MESSAGE);
                    
                }else System.out.println("Definido el numero de victimas : "+ num1);
    }//GEN-LAST:event_intervalNumVictimasActionPerformed

    private void jMenuItemCrearRobotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCrearRobotActionPerformed
        // TODO add your handling code here:
        intencionUsuarioCrearRobot = true;
        intencionUsuarioCrearVictima = false;
    }//GEN-LAST:event_jMenuItemCrearRobotActionPerformed

    private void jMenuItemCrearVictimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCrearVictimaActionPerformed
        // TODO add your handling code here:
         intencionUsuarioCrearVictima = true;
         intencionUsuarioCrearRobot = false;
    }//GEN-LAST:event_jMenuItemCrearVictimaActionPerformed

    private void jTextFieldIdentEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldIdentEquipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldIdentEquipoActionPerformed

    private void jMenuItemEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEliminarActionPerformed
        // TODO add your handling code here:
        System.out.println("Eliminar menu: "+ "  entidad = "   );
//        JLabel compAeliminar =(JLabel)moverComp.getUltimoComponenteSeleccionado();
//        compAeliminar.setVisible(false);
        eliminarEntidadSeleccionada();
       
    }//GEN-LAST:event_jMenuItemEliminarActionPerformed

    private void jMenuItemGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemGuardarActionPerformed
        // TODO add your handling code here:
         System.out.println("Ha pulsado el botón Guardar Escenario");
         actualizarCoordenadasEntidades();
         controladorSim.peticionGuardarEscenario(escenarioActualComp);
//        String valor ;
//        setLocationRelativeTo(this);
////        escenarioActualComp.setIdentEscenario(jTextFieldIdentEquipo.getText());
//        escenarioActualComp.setIdentificadorNormalizado();
//        persistencia.guardarInfoEscenarioSimulacion(directorioPersistencia, escenarioActualComp);
//
//        //         String smsg = "Puede cambiar el valor en milisegundos en que deben enviarse las coordenadas";
//        jTextFieldIdentEquipo.setText(escenarioActualComp.getIdentEscenario());
//        String smsg = "Se va a guardar el escenario: " + escenarioActualComp.getIdentEscenario();
//        JOptionPane.showConfirmDialog(rootPane, smsg,"Confirmar GuardarEscenario",JOptionPane.OK_CANCEL_OPTION );
        //         jOptionPaneAvisoError.setToolTipText(smsg);
    }//GEN-LAST:event_jMenuItemGuardarActionPerformed

    private void jPopupMenuAcionEntidadPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jPopupMenuAcionEntidadPopupMenuWillBecomeVisible
        // TODO add your handling code here:
//         jPopupMenuAcionEntidad.show(, 200, 200);
    }//GEN-LAST:event_jPopupMenuAcionEntidadPopupMenuWillBecomeVisible

    private void jPopupMenuAcionEntidadMenuKeyPressed(javax.swing.event.MenuKeyEvent evt) {//GEN-FIRST:event_jPopupMenuAcionEntidadMenuKeyPressed
        // TODO add your handling code here:
//        jPopupMenuAcionEntidad.show(this, evt.getX(), e.getY());
    }//GEN-LAST:event_jPopupMenuAcionEntidadMenuKeyPressed

    private void jMenuItemAddRobotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAddRobotActionPerformed
        // TODO add your handling code here:
        intencionUsuarioCrearRobot = true;
        intencionUsuarioCrearVictima = false;
        this.crearIconoRobVict("Robot",ultimoPuntoClic.x,ultimoPuntoClic.y );
    }//GEN-LAST:event_jMenuItemAddRobotActionPerformed

    private void jMenuItemAddVictimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAddVictimaActionPerformed
        // TODO add your handling code here:
         intencionUsuarioCrearRobot = false;
        intencionUsuarioCrearVictima = true;
        this.crearIconoRobVict("Victima",ultimoPuntoClic.x,ultimoPuntoClic.y );
    }//GEN-LAST:event_jMenuItemAddVictimaActionPerformed

    private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser1ActionPerformed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jFileChooser1ActionPerformed

    private void jMenuItemAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAbrirActionPerformed
        // TODO add your handling code here:    
//      peticionAbrirEscenario();
        this.controladorSim.peticionAbrirEscenarioEdicion();
    }//GEN-LAST:event_jMenuItemAbrirActionPerformed

    private void jMenuItemNuevoEscenarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNuevoEscenarioActionPerformed
        // Usuario quiere crear un escenario
        // se abre una  ventana vacia , si tiene otra abierta se le debería avisar de que se guardar
        // lo que tiene
        if (escenarioActualComp.getNumRobots()> 0){
//            peticionGuardarEscenario();
            this.controladorSim.peticionGuardarEscenario(escenarioActualComp);
            // Se avisa de que el escenario actual se va a guardar antes de abrir el nuevo
//            escenarioActualComp.setIdentEscenario(jTextFieldIdentEquipo.getText());
//       
//
//        //         String smsg = "Puede cambiar el valor en milisegundos en que deben enviarse las coordenadas";
//
//        String smsg = "Se va a guardar el escenario: " +jTextFieldIdentEquipo.getText() ;
//        JOptionPane.showConfirmDialog(rootPane, smsg,"Confirmar GuardarEscenario",JOptionPane.OK_CANCEL_OPTION );
//         persistencia.guardarInfoEscenarioSimulacion(directorioPersistencia, escenarioActualComp);
        }
        escenarioActualComp = gestionEscComp.crearEscenarioSimulacion();
//        jTextFieldIdentEquipo.setText()
        eliminarEntidadesEscenario();
        jTextFieldIdentEquipo.setText(escenarioActualComp.getIdentEscenario());
        intervalNumRobots.setText(""+0);
        intervalNumVictimas.setText(""+0);
        
    }//GEN-LAST:event_jMenuItemNuevoEscenarioActionPerformed

    private void jMenuItemModeloJerarquicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemModeloJerarquicoActionPerformed
        // TODO add your handling code here:
        modeloOrganizativo = "Jerarquico";
        jTextFieldModeloOrganizacion.setText(modeloOrganizativo);
        escenarioActualComp.setmodeloOrganizativo(modeloOrganizativo);
    }//GEN-LAST:event_jMenuItemModeloJerarquicoActionPerformed

    private void jMenuItemModeloIgualitarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemModeloIgualitarioActionPerformed
        // TODO add your handling code here:
         modeloOrganizativo = "Igualitario";
        jTextFieldModeloOrganizacion.setText(modeloOrganizativo);
        escenarioActualComp.setmodeloOrganizativo(modeloOrganizativo);
    }//GEN-LAST:event_jMenuItemModeloIgualitarioActionPerformed

    private void jMenuItemEliminarEscenarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEliminarEscenarioActionPerformed
        // TODO add your handling code here:
        peticionEliminarEscenario();
    }//GEN-LAST:event_jMenuItemEliminarEscenarioActionPerformed
    private void setIntervaloEnvioMensajesDesdeCC(int intervalo){
		intervaloSecuencia = intervalo ;
		int intervaloEnvioMensajesDesdeCC = 1000;
		String strintervaloEnvioMensajesDesdeCC = "";
    }
     private void jLabelMouseReleased(java.awt.event.MouseEvent evt) {                                   
        // TODO add your handling code here:
        // Si tiene una entidad seleccionada se suelta y se anotan la coordenadas
         System.out.println("Released" + " X= "+ evt.getX() +" Y = "+ evt.getY() );
         entidadSeleccionadaParaMover=false;
          evt.consume();
         
    }  

    public void visualizarIdentsEquipoRobot ( ArrayList<String> equipoIds){
//        eqipoIds = eqipoIds.toArray();
        identsRobotsEquipo = equipoIds;
//        this.listaComponentes.setListData(identsRobotsEquipo.toArray());
//        listaComponentes.setVisible(true);
}
    
    public JLabel crearIconoRobVict(String tipoEntidad, int coordX, int coordY){
        
        JLabel label = new JLabel();
        int correccionX=-30;
        int correccionY=-93;
//        int correccionX=0;
//        int correccionY=0;
        coordX=coordX+correccionX;
        coordY=coordY+correccionY;
        String rutaImagen;
        String identEntidad;
       if ( tipoEntidad.startsWith("Robot")){
           rutaImagen=directorioTrabajo+rutaIconos+imageniconoRobot;
           numeroRobots= escenarioActualComp.getNumRobots();
           
         
//           intervalNumRobots.setText(""+numeroRobots);
             numeroRobots++;
             identEntidad=tipoEntidad+numeroRobots;
           escenarioActualComp.addRoboLoc(identEntidad, new Point(coordX,coordY));
           intervalNumRobots.setText(""+numeroRobots);
//           label.setText(tipoEntidad+numeroRobots);
           
       }
       else{
        rutaImagen=directorioTrabajo+rutaIconos+imageniconoMujer;
        mumeroVictimas= escenarioActualComp.getNumVictimas();
        mumeroVictimas++;
//         intervalNumVictimas.setText(""+mumeroVictimas);
         identEntidad=tipoEntidad+mumeroVictimas;
         escenarioActualComp.addVictimLoc(identEntidad, new Point(coordX,coordY));
//         mumeroVictimas=escenrioSimComp.getNumVictimas();
         intervalNumVictimas.setText(""+mumeroVictimas);
//         identEntidad=tipoEntidad+mumeroVictimas;
//         label.setText(tipoEntidad+mumeroVictimas);
       }
        
        label.setText(identEntidad);
        label.setBounds(10, 10, 100, 100);
//        label.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
//            @Override
//            public void mouseDragged(java.awt.event.MouseEvent evt) {
//                jLabelMouseDragged(evt);
//            }
//            public void mouseReleased(java.awt.event.MouseEvent evt) {
//                jLabelMouseReleased(evt);
//            }
//        });
        
        this.add(label);
        label.setVisible(true);
        
//        label.setIcon(new ImageIcon("E:\\FicheroRed\\Github\\rosaceSIM\\src\\utilsDiseniaEscenariosRosace\\Robot.png"));
        label.setIcon(new ImageIcon(rutaImagen));
        label.setLocation(coordX, coordY);
        this.listaEntidadesEnEscenario.add(label);
        System.out.println( "Se crea la entidad : "+label.getText()+ " Coordenadas : X =" + coordX +" , Y = " +coordY );
//       tablaEntidadesEnEscenario.put(identEntidad, label);
//       listaEntidadesEnEscenario.add(label);
        
       moverComp.registerComponent(label);
        return label;
    }
    public void addEntidadEnEscenario (String rutaIcono, String idEntidad, Point puntoLoc){
         JLabel label = new JLabel();
       
//          String rutaImagen=directorioTrabajo+rutaIconos+imageniconoMujer;
//           numeroRobots= escenrioSimComp.getNumRobots();
//           numeroRobots++;
//           intervalNumRobots.setText(""+numeroRobots);
//           identEntidad=tipoEntidad+numeroRobots;
//           escenrioSimComp.addRoboLoc(identEntidad, new Point(coordX,coordY));
//           intervalNumRobots.setText(""+numeroRobots);
           label.setText(idEntidad);
        label.setBounds(10, 10, 100, 100);
        this.add(label);
        label.setVisible(true);
        label.setIcon(new ImageIcon(rutaIcono));
        label.setLocation(puntoLoc);
        moverComp.registerComponent(label);
        this.listaEntidadesEnEscenario.add(label);
        System.out.println( "Se crea la entidad : "+label.getText()+ " Punto : =" + puntoLoc );
//       tablaEntidadesEnEscenario.put(identEntidad, label);
    }
    public void eliminarEntidadSeleccionada (){
     JLabel entidadAeliminar=   (JLabel) moverComp.getUltimoComponenteSeleccionado();
//        escenrioSimComp.eliminarEntidad(((JLabel)moverComp.eliminarUltimoCompSeleccionado()).getName());
//     moverComp.deregisterComponent(entidadAeliminar);
     entidadAeliminar.setVisible(false);
       escenarioActualComp.eliminarEntidad(entidadAeliminar.getText());
       listaEntidadesEnEscenario.remove(entidadAeliminar);
        System.out.println( "Se elimina la entidad : "+entidadAeliminar.getText()+ " Coordenadas : X =" + entidadAeliminar.getX() +" , Y = " +entidadAeliminar.getY() );
    actualizarInfoEquipoEnEscenario ();
    }
    public void actualizarInfoEquipoEnEscenario (){
//        jTextFieldIdentEquipo= escenrioSimComp.get
        jTextFieldModeloOrganizacion.setText(""+escenarioActualComp.getmodeloOrganizativo());
        jTextFieldIdentEquipo.setText(""+escenarioActualComp.getIdentEscenario());
        intervalNumRobots.setText(""+escenarioActualComp.getNumRobots());
        intervalNumVictimas.setText(""+escenarioActualComp.getNumVictimas());
//        intervalNumVictimas.setVisible(true);
    }
//    public void actualizarCoordenadasEntidades(){
//        System.out.println( "Se actualizan las coordenadas. Numero de entidades : "+listaEntidadesEnEscenario.size());
//        for (int i=0; i<listaEntidadesEnEscenario.size();){
//            JLabel entidadLabel = listaEntidadesEnEscenario.get(i);
//            String identEntidad = entidadLabel.getText();
//            if (identEntidad.contains("Robot"))
//                                        escenarioActualComp.addRoboLoc(identEntidad, entidadLabel.getLocation());
//            else escenarioActualComp.addVictimLoc(identEntidad, entidadLabel.getLocation());
//            System.out.println( "Se actualiza la entidad : "+identEntidad+ " Coordenadas : X =" + entidadLabel.getX() +" , Y = " +entidadLabel.getY() );
//        }
//    }
    public void actualizarCoordenadasEntidades(){
        System.out.println( "Se actualizan las coordenadas. Numero de entidades : "+listaEntidadesEnEscenario.size());
        int numRobots= 0; int numVictims= 0;
        for (JLabel entidadLabel : listaEntidadesEnEscenario) {
            String identEntidad = entidadLabel.getText();
            if (identEntidad.contains("Robot")||identEntidad.contains("robot")){
                escenarioActualComp.addRoboLoc(identEntidad, entidadLabel.getLocation());
                numRobots++;
            }
            else if (identEntidad.contains("Victim")){
                escenarioActualComp.addVictimLoc(identEntidad, entidadLabel.getLocation());
                numVictims++;
            }
            System.out.println( "Se actualiza la entidad : "+identEntidad+ " Coordenadas : X =" + entidadLabel.getX() +" , Y = " +entidadLabel.getY() );
            System.out.println( "Robots y victimas despues de la actualizacion. Num Robots :  "+numRobots+ " Num Victimas : " + numVictims );
        }
        escenarioActualComp.setNumRobots(numRobots);
        escenarioActualComp.setNumVictimas(numVictims);
       System.out.println( "Robots y victimas despues de la actualizacion. Num Robots :  "+escenarioActualComp.getNumRobots()+ " Num Vicitimas : " + escenarioActualComp.getNumVictimas() );
    }
    private void setEscenarioActualComp(EscenarioSimulacionRobtsVictms escenActualComp){
        escenarioActualComp = escenActualComp;
    }
     private void setPersistencia(PersistenciaVisualizadorEscenarios persistEscenario) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    persistencia = persistEscenario;
     }
     public void visualizarEscenario(EscenarioSimulacionRobtsVictms infoEscenario ){
         eliminarEntidadesEscenario();
         escenarioActualComp= infoEscenario;
         int numRobots = infoEscenario.getNumRobots();
         int numVictims = infoEscenario.getNumVictimas();
         jTextFieldIdentEquipo.setText(""+infoEscenario.getIdentEscenario());
         jTextFieldModeloOrganizacion.setText(""+infoEscenario.getmodeloOrganizativo());
         intervalNumRobots.setText(""+numRobots);
         intervalNumVictimas.setText(""+numVictims);
         
         String rutaImagen;
         Set entidades;
         Iterator entries;
         if (numRobots>0) {
         rutaImagen=directorioTrabajo+rutaIconos+imageniconoRobot;
          entidades = infoEscenario.getRobots();
//          entidades.remove("robotInit");
          entries = entidades.iterator();
          Entry thisEntry;
          RobotStatus1 robotInfo;
        while (entries.hasNext()) {
             thisEntry = (Entry) entries.next();
             robotInfo = (RobotStatus1)thisEntry.getValue();
//            addEntidadEnEscenario(rutaImagen,(String)thisEntry.getKey(),(Point)thisEntry.getValue());
            addEntidadEnEscenario(rutaImagen,(String)thisEntry.getKey(),robotInfo.getLocPoint());
//         intervalNumRobots.setText(""+infoEscenario.getNumRobots());
//         intervalNumVictimas.setText(""+escenrioSimComp.getNumVictimas());
        }
         }
         if (numVictims>0) {
            rutaImagen=directorioTrabajo+rutaIconos+imageniconoMujer;
            entidades = infoEscenario.getSetVictims();
//            entidades.remove("victimInit");
            entries = entidades.iterator();
            Entry thisEntry ;
            Victim victimInfo ;
            while (entries.hasNext()) {
                 thisEntry = (Entry) entries.next();
                 victimInfo = (Victim)thisEntry.getValue();
                addEntidadEnEscenario(rutaImagen,(String)thisEntry.getKey(),victimInfo.getLocPoint());
//         intervalNumRobots.setText(""+infoEscenario.getNumRobots());
//         intervalNumVictimas.setText(""+escenrioSimComp.getNumVictimas());
            }
         }
         this.setLocation(100,100);
         this.setVisible(true);
     }
     private void peticionGuardarEscenario (){
         setLocationRelativeTo(this);
//        escenarioActualComp.setIdentEscenario(jTextFieldIdentEquipo.getText());
        escenarioActualComp.setIdentificadorNormalizado();
      
        jTextFieldIdentEquipo.setText(escenarioActualComp.getIdentEscenario());
        String smsg = "Se va a guardar el escenario: " + escenarioActualComp.getIdentEscenario();
       int respuesta= JOptionPane.showConfirmDialog(rootPane, smsg,"Confirmar GuardarEscenario",JOptionPane.OK_CANCEL_OPTION );
        //         jOptionPaneAvisoError.setToolTipText(smsg);
       if (respuesta==JOptionPane.OK_OPTION){
           gestionEscComp.addEscenario(escenarioActualComp);
           this.actualizarCoordenadasEntidades();
             persistencia.guardarInfoEscenarioSimulacion(directorioPersistencia, escenarioActualComp);
       }
     }
     public int confirmarPeticionGuardarEscenario (String msgConfirmacion){
         escenarioActualComp.setIdentificadorNormalizado();
         jTextFieldIdentEquipo.setText(escenarioActualComp.getIdentEscenario());
        String smsg = msgConfirmacion + jTextFieldIdentEquipo.getText();
//       int respuesta= JOptionPane.showConfirmDialog(rootPane, smsg,"Confirmar GuardarEscenario",JOptionPane.OK_CANCEL_OPTION );
        //         jOptionPaneAvisoError.setToolTipText(smsg);
       return JOptionPane.showConfirmDialog(rootPane, smsg,"Confirmar GuardarEscenario",JOptionPane.OK_CANCEL_OPTION );
//       if (respuesta==JOptionPane.OK_OPTION){
//           gestionEscComp.addEscenario(escenarioActualComp);
//             persistencia.guardarInfoEscenarioSimulacion(directorioPersistencia, escenarioActualComp);
//       }
     }
     private void eliminarEntidadesEscenario(){
//         Set entidades;
//         Iterator entries;
//         if (infoEscenario.getNumRobots()>0) {
//         rutaImagen=directorioTrabajo+rutaIconos+imageniconoRobot;
//          entidades = infoEscenario.getRobots();
//          entries = entidades.iterator();
//            while (entries.hasNext()) {
//                Entry thisEntry = (Entry) entries.next();
//                Point punto =(Point)thisEntry.getValue();
//            addEntidadEnEscenario(rutaImagen,(String)thisEntry.getKey(),(Point)thisEntry.getValue());
//             ((JLabel) this.findComponentAt((Point)thisEntry.getValue())).setVisible(false);
         JLabel labelActual;             
         for( Component comp : this.getContentPane().getComponents() ){
                 if (comp instanceof JLabel){
                      labelActual = (JLabel)comp;
                     if (!labelActual.equals(jLabelIdentEquipo)&&!labelActual.equals(jLabelOrganizacion)
                             &&!labelActual.equals(robotIcon)&&!labelActual.equals(victimaIcon1)){                    
                         comp.setVisible(false);
                         remove(comp);
                         listaEntidadesEnEscenario.remove(labelActual);
                 System.out.println( "Se borra la entidad : "+ " Coordenadas :  =" + comp.getLocation() );         
                     }
                };
         } 
 
     }
     public int selecciondeFichero(){
      FileNameExtensionFilter filter = new FileNameExtensionFilter("ficheros xml","xml","txt" );
      jFileChooser1.setFileFilter(filter);
      File dir = jFileChooser1.getCurrentDirectory();
     int returnVal = jFileChooser1.showOpenDialog(this);
      jFileChooser1.setFileSelectionMode(JFileChooser.FILES_ONLY);
       jFileChooser1.setCurrentDirectory(dir);
//       int returnVal = jFileChooser1.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
        ultimoFicheroEscenarioSeleccionado = jFileChooser1.getSelectedFile();
     }return returnVal; // no ha seleccionado nada
     }
     
     public File getUltimoFicheroEscenarioSeleccionado(){
         return ultimoFicheroEscenarioSeleccionado;
     }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VisorCreacionEscenarios1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VisorCreacionEscenarios1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VisorCreacionEscenarios1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VisorCreacionEscenarios1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
            String  directorioPersistencia = VocabularioRosace.NombreDirectorioPersistenciaEscenarios+File.separator;
            VisorCreacionEscenarios1 visor;
            PersistenciaVisualizadorEscenarios persistencia= new PersistenciaVisualizadorEscenarios();
            GestionEscenariosSimulacion gestionEscComp= new GestionEscenariosSimulacion();
            gestionEscComp.setIdentsEscenariosSimulacion(persistencia.obtenerIdentsEscenarioSimulacion(directorioPersistencia));
                try {
                    gestionEscComp = new GestionEscenariosSimulacion();
                    gestionEscComp.setIdentsEscenariosSimulacion(persistencia.obtenerIdentsEscenarioSimulacion(directorioPersistencia));
//        escenarioActualComp = gestionEscComp.crearEscenarioSimulación();
//                    visor = new VisorCreacionEscenarios1(new ControladorVisualizacionSimulRosace(notifEvts));
//             
////                    persistencia= new PersistenciaVisualizadorEscenarios();
//                    visor.setPersistencia(persistencia);
//                    visor.setGestorEscenarionComp(gestionEscComp);
//                    visor.setEscenarioActualComp(gestionEscComp.crearEscenarioSimulación());
//                    visor.actualizarInfoEquipoEnEscenario();
//                    visor.setVisible(true);
                } catch (Exception ex) {
                    Exceptions.printStackTrace(ex);
                }
                
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar GestionEscenarios;
    private javax.swing.JTextField intervalNumRobots;
    private javax.swing.JTextField intervalNumVictimas;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonGuardarEscenario;
    private javax.swing.JDialog jDialogAvisoErrorDefNumEntidades;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelIdentEquipo;
    private javax.swing.JLabel jLabelOrganizacion;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenuEditarEscenario;
    private javax.swing.JMenuItem jMenuItemAbrir;
    private javax.swing.JMenuItem jMenuItemAddRobot;
    private javax.swing.JMenuItem jMenuItemAddVictima;
    private javax.swing.JMenuItem jMenuItemCrearRobot;
    private javax.swing.JMenuItem jMenuItemCrearVictima;
    private javax.swing.JMenuItem jMenuItemEliminar;
    private javax.swing.JMenuItem jMenuItemEliminarEscenario;
    private javax.swing.JMenuItem jMenuItemGuardar;
    private javax.swing.JMenuItem jMenuItemGuardarEscenario;
    private javax.swing.JMenuItem jMenuItemModeloIgualitario;
    private javax.swing.JMenuItem jMenuItemModeloJerarquico;
    private javax.swing.JMenuItem jMenuItemModeloOtros;
    private javax.swing.JMenuItem jMenuItemNuevoEscenario;
    private javax.swing.JMenuItem jMenuItemSalir;
    private javax.swing.JMenu jMenuOrganizacion;
    private javax.swing.JOptionPane jOptionPane1;
    private javax.swing.JPopupMenu jPopupMenuAcionEntidad;
    private javax.swing.JPopupMenu jPopupMenuAddEntidades;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JTextField jTextFieldIdentEquipo;
    private javax.swing.JTextField jTextFieldModeloOrganizacion;
    private javax.swing.JLabel robotIcon;
    private javax.swing.JLabel victimaIcon1;
    // End of variables declaration//GEN-END:variables

    public void setGestorEscenarionComp(GestionEscenariosSimulacion gestEscComp ) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    this.gestionEscComp=gestEscComp;
    }

public void visualizarConsejo (String titulo, String msgConsejo, String recomendacion){
         String nl = System.getProperty("line.separator");
        Object msgConsjyRec = msgConsejo+nl+recomendacion;
         JOptionPane.showMessageDialog(rootPane,msgConsjyRec, titulo,2);
     }
    private void peticionEliminarEscenario() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    File ficheroseleccionado= peticionUsuarioSeleccionarFichero(directorioPersistencia,"Seleccionar Fichero a Eliminar");
    if(ficheroseleccionado!=null){ 
//        confirmar eliminacion del fichero y si lo confirma 
        String smsg = "Se va a eliminar el escenario: " + ficheroseleccionado.getName();
      int respuesta=  JOptionPane.showConfirmDialog(rootPane, smsg,"Confirmar EliminarEscenario",JOptionPane.OK_CANCEL_OPTION );
      if ( respuesta== JOptionPane.OK_OPTION) {
      ficheroseleccionado.delete();
        gestionEscComp.eliminarEscenario(ficheroseleccionado.getName());
         System.out.println("Se elimina el fichero :  "+ficheroseleccionado.getName());
      }
    }    
        
           
    }
private void peticionAbrirEscenario() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    FileNameExtensionFilter filter = new FileNameExtensionFilter("ficheros xml","xml","txt" );
   
      jFileChooser1.setFileFilter(filter);
      jFileChooser1.setCurrentDirectory(new File(directorioPersistencia));
      jFileChooser1.setFileSelectionMode(JFileChooser.FILES_ONLY);
       int returnVal = jFileChooser1.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
        File selectedFile = jFileChooser1.getSelectedFile();
        escenarioActualComp = persistencia.obtenerInfoEscenarioSimulacion(selectedFile.getAbsolutePath());
        escenarioActualComp.setGestorEscenarios(gestionEscComp);
        visualizarEscenario(escenarioActualComp);
        
//               fileName = selectedFile.getName();
        // enviariamos el fichero a la persistencia para que nos diera el contenido
        // se visualiza un escenario a partir de la información almacenada
        System.out.println("Ejecuto  accion sobre el fichero "+selectedFile.getAbsolutePath());
    } else {
        System.out.println("File access cancelled by user.");
    }
    }
   public File peticionUsuarioSeleccionarFichero(String directorio, String motivo){
       FileNameExtensionFilter filter = new FileNameExtensionFilter("ficheros xml","xml","txt" );
      jFileChooser1.setDialogTitle(motivo);
      jFileChooser1.setFileFilter(filter);
      jFileChooser1.setCurrentDirectory(new File(directorioPersistencia));
      jFileChooser1.setFileSelectionMode(JFileChooser.FILES_ONLY);
       int returnVal = jFileChooser1.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
        File selectedFile = jFileChooser1.getSelectedFile();
        return selectedFile;
   }else{
        // mensaje diciendo que no se ha seleccionado ningun fichero
        return null;
    }
   }
    public boolean hayFicherosCreados(){
          File dir = jFileChooser1.getCurrentDirectory();
      int numFiles = dir.list().length ;
      return(numFiles > 0);
      }
     
     public File solicitarSeleccionFichero(){
      FileNameExtensionFilter filter = new FileNameExtensionFilter("ficheros xml","xml","txt" );
      jFileChooser1.setFileFilter(filter);
      File dir = jFileChooser1.getCurrentDirectory();
     int returnVal = jFileChooser1.showOpenDialog(this);
      jFileChooser1.setFileSelectionMode(JFileChooser.FILES_ONLY);
       jFileChooser1.setCurrentDirectory(dir);
//       int returnVal = jFileChooser1.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
        return jFileChooser1.getSelectedFile();
     }return null; // no ha seleccionado nada
     }
     
     public boolean setDirectorioPersistencia(String dirPersistencia){
         FileNameExtensionFilter filter = new FileNameExtensionFilter("ficheros xml","xml","txt" );
          jFileChooser1.setFileFilter(filter);
        jFileChooser1.setFileSelectionMode(JFileChooser.FILES_ONLY);
        try {
       jFileChooser1.setCurrentDirectory(new File (dirPersistencia));
       return true;
        } catch (Exception ex) {
            return false;
        }
     }
}
