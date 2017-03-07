/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.visualizacionResultados;

import icaro.aplicaciones.recursos.recursoEstadistica.imp.visualizacionEstadisticas.*;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.visualizacionResultados.ItemLabelGeneratorVictims.LabelGenerator;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;

import jxl.Range;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author FGarijo
 */
//public class VisualizacionJfreechart extends ApplicationFrame{

public class VisualizacionJfreechart extends JFrame{

    JFreeChart  chart1;
    JFreeChart  chartNotifAsigResc;
    JFreeChart chartTiemposRescatePorRobot;
//    XYPlot plot;

    public VisualizacionJfreechart(final String title){
        super(title);           
    }

    
    public void inicializacionJFreeChart(String title, String xAxisLabel, String yAxisLabel,
    		                             PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls) {    	
        chart1 = ChartFactory.createXYLineChart(
        			title,      // chart title Titulo local del grafico
        			xAxisLabel,                      // x axis label
        			yAxisLabel,                      // y axis label
        			null,                  // data
        			orientation,
        			legend,                     // include legend
        			tooltips,                     // tooltips
        			urls                     // urls
                );        
        
        // get a reference to the plot for further customisation...
     XYPlot   plot = chart1.getXYPlot();
    }
    
    
    public void showJFreeChart(int coordX, int coordY){
        //Mostrar el chart
        ChartPanel chartPanel = new ChartPanel(chart1);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);           
                
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);               
        
        addWindowListener(
        		new WindowAdapter(){
        		        public void WindowClosing (WindowEvent e){
                           System.out.println("No quiero cerrar la ventana !!!\n");
        		        }	
        		}
        );

        
        this.pack();
//        RefineryUtilities.centerFrameOnScreen(this);
        
        this.setLocation(coordX, coordY);
        
        this.setVisible(true);    	    	
    }
        
    
    public void setColorChartBackgroundPaint(Color c){
    	chart1.setBackgroundPaint(c);   //El fondo exterior del grafico sera del color pasado por parametro
    }
    
    
   
        
    public void crearChartNotifAsigResc(){
        chartNotifAsigResc = ChartFactory.createXYLineChart(
          "Peticion,Asignacion y Rescate por el equipo",      // chart title Titulo local del grafico
          "Victimas en Entorno",                      // x axis label
          "Tiempo milisegundos",                      // y axis label
          null,                  // data
          PlotOrientation.VERTICAL,
          true,                     // include legend
          true,                     // tooltips
          false                     // urls
      );
    
      // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
      chartNotifAsigResc.setBackgroundPaint(Color.white);   //El fondo exterior del grafico sera blanco
      //      chart1.setBackgroundPaint(Color.green);

      // get a reference to the plot for further customisation...
     XYPlot plot = chartNotifAsigResc.getXYPlot();
      plot.setBackgroundPaint(Color.lightGray); //El fondo interior del grafico sera gris
      //plot.setBackgroundPaint(Color.blue);

      plot.setDomainGridlinePaint(Color.white);  //Las lineas verticales de la cuadricula se pinta de color blanco
      plot.setRangeGridlinePaint(Color.white);  //Las lineas horizontales de la cuadricula se pintan de color blanco
      
          
      ChartPanel chartPanel = new ChartPanel(chartNotifAsigResc);
      chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
      setContentPane(chartPanel);
      this.pack();
      RefineryUtilities.centerFrameOnScreen(this);
      this.setVisible(true);
    }
    public void crearChartTiemposRescatePorRobot(){
        chartTiemposRescatePorRobot = ChartFactory.createXYLineChart(
          "Tiempos de Rescate por Robot ",      // chart title Titulo local del grafico
          "Robots en Entorno",                      // x axis label
          "Tiempo milisegundos",                      // y axis label
          null,                  // data
          PlotOrientation.VERTICAL,
          true,                     // include legend
          true,                     // tooltips
          false                     // urls
      );
        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
      chartTiemposRescatePorRobot.setBackgroundPaint(Color.white);   //El fondo exterior del grafico sera blanco
      //      chart1.setBackgroundPaint(Color.green);

      // get a reference to the plot for further customisation...
    XYPlot  plot = chartTiemposRescatePorRobot.getXYPlot();
      plot.setBackgroundPaint(Color.lightGray); //El fondo interior del grafico sera gris
      //plot.setBackgroundPaint(Color.blue);

      plot.setDomainGridlinePaint(Color.white);  //Las lineas verticales de la cuadricula se pinta de color blanco
      plot.setRangeGridlinePaint(Color.white);  //Las lineas horizontales de la cuadricula se pintan de color blanco
      
          
      ChartPanel chartPanel = new ChartPanel(chartTiemposRescatePorRobot);
      chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
      setContentPane(chartPanel);
      this.pack();
      RefineryUtilities.centerFrameOnScreen(this);
      this.setVisible(true);
    }
    
    //por el momento no me hace caso para personalizar el aspecto de la serie (aspecto del punto, color de los puntos, puntos unidos con lineas o no, ....)
    public void addSerie(int indexSerie, Color color, XYSeries localXYSeries){ 
        XYPlot plot=null;
        XYSeriesCollection localXYSeriesCollection = new XYSeriesCollection();
        localXYSeriesCollection.addSeries(localXYSeries);     //Se aniade la serie

        Ellipse2D.Double localDouble = new Ellipse2D.Double(-4.0D, -4.0D, 8.0D, 8.0D); //Forma de la anotacion del punto x,y
        
        XYLineAndShapeRenderer localXYLineAndShapeRenderer = new XYLineAndShapeRenderer();

        localXYLineAndShapeRenderer.setSeriesLinesVisible(indexSerie,true);        //provoca que se pinten lineas rectas que unen los puntos x,y que conforman la serie
        localXYLineAndShapeRenderer.setSeriesShapesVisible(indexSerie,false);       //provoca que se pinte la forma asociada al punto x,y       

        plot.setDataset(indexSerie, localXYSeriesCollection);  //Se aniade la serie al plot
        plot.setRenderer(indexSerie, localXYLineAndShapeRenderer);

        localXYLineAndShapeRenderer.setSeriesShape(indexSerie, new Ellipse2D.Double(-4.0, -4.0, 8.0, 8.0));
        
//        localXYLineAndShapeRenderer.setSeriesShape(indexSerie, localDouble);
        
        
        localXYLineAndShapeRenderer.setSeriesPaint(indexSerie, color);
        //localXYLineAndShapeRenderer.setSeriesFillPaint(indexSerie, Color.yellow);
        //localXYLineAndShapeRenderer.setSeriesOutlinePaint(indexSerie, Color.gray);
        localXYLineAndShapeRenderer.setUseFillPaint(false);//true
        localXYLineAndShapeRenderer.setUseOutlinePaint(false);//true
        localXYLineAndShapeRenderer.setDrawOutlines(false);
        localXYLineAndShapeRenderer.setDrawSeriesLineAsPath(true);                       
    }


 public void crearBarChartCosteEnergiaRescateVictimas(CategoryDataset dataset) {
         JFreeChart chart = ChartFactory.createBarChart(
        "Energía consumida para salvar las victimas ", // chart title
        "Victimas ordenadas por tiempo de rescate", // domain axis label
        "Energía consumida", // range axis label
        dataset, // data
        PlotOrientation.VERTICAL, // orientation
        true, // include legend
        true, // tooltips?
        false // URLs?
        );
        ChartPanel chartPanel = new ChartPanel(chart);
        chart.setBackgroundPaint(Color.white);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setUpperMargin(0.15);
        CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setItemLabelGenerator(new LabelGenerator(50.0));
        renderer.setItemLabelFont(new Font("Serif", Font.PLAIN, 8));
        renderer.setItemLabelsVisible(true);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
      setContentPane(chartPanel);
      this.pack();
      RefineryUtilities.centerFrameOnScreen(this);
      this.setVisible(true);
//        return chart;
            }

   public  JFreeChart crearChartAsignRescateVict(CategoryDataset dataset) {
       
       chartNotifAsigResc = ChartFactory.createLineChart(
          "Tiempos de Rescate de Victimas ",      // chart title Titulo local del grafico
          "Victimas Rescatadas  en Entorno",                      // x axis label
          "Tiempo milisegundos",                      // y axis label
          dataset,                  // data
          PlotOrientation.VERTICAL,
          true,                     // include legend
          true,                     // tooltips
          false                     // urls
      );

      // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
      ChartPanel chartPanel = new ChartPanel(chartNotifAsigResc,false);
        chartNotifAsigResc.setBackgroundPaint(Color.white);
//       CategoryPlot plot = (CategoryPlot) chartNotifAsigResc.getPlot();
        CategoryPlot plot = chartNotifAsigResc.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setUpperMargin(0.15);
        CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setItemLabelGenerator(new LabelGenerator(50.0));
        renderer.setItemLabelFont(new Font("Serif", Font.PLAIN, 8));
        renderer.setItemLabelsVisible(true);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
      setContentPane(chartPanel);
      this.pack();
      RefineryUtilities.centerFrameOnScreen(this);
      this.setVisible(true);
      return chartNotifAsigResc;
    }
   public  JFreeChart crearLineChartAsignRescateVict(CategoryDataset dataset) {
       
       chartNotifAsigResc = ChartFactory.createLineChart(
          "Tiempos de Notificación, asiganción y Rescate de Victimas ",      // chart title Titulo local del grafico
          "Victimas Ordenadas por tiempo de rescate ",                      // x axis label
          "Tiempo milisegundos",                      // y axis label
          dataset,                  // data
          PlotOrientation.VERTICAL,
          true,                     // include legend
          true,                     // tooltips
          false                     // urls
      );

      // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
      ChartPanel chartPanel = new ChartPanel(chartNotifAsigResc);
//        chartNotifAsigResc.setBackgroundPaint(Color.white);
       CategoryPlot plot = (CategoryPlot) chartNotifAsigResc.getPlot();
//        CategoryPlot plot = chartNotifAsigResc.getCategoryPlot();
//        plot.setBackgroundPaint(Color.lightGray);
//        plot.setDomainGridlinePaint(Color.white);
//        plot.setRangeGridlinePaint(Color.white);
//        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
//        rangeAxis.setUpperMargin(0.15);
        CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setItemLabelGenerator(new LabelGenerator(50.0));
        renderer.setItemLabelFont(new Font("Serif", Font.PLAIN, 8));
        renderer.setItemLabelsVisible(true);
//      CategoryPlot plot = (CategoryPlot) chartNotifAsigResc.getPlot();
            LineAndShapeRenderer rendererl = (LineAndShapeRenderer) plot.getRenderer();
            rendererl.setShapesVisible(true);
            rendererl.setDrawOutlines(true);
            rendererl.setUseFillPaint(true);
            rendererl.setFillPaint(Color.white);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
      setContentPane(chartPanel);
      this.pack();
      RefineryUtilities.centerFrameOnScreen(this);
      this.setVisible(true);
      return chartNotifAsigResc;
    }
   public void  visualizarSerieChartAsignRescateVict(Color color,CategoryDataset dataset) {
        ChartPanel chartPanel = new ChartPanel(chartNotifAsigResc);
        chartNotifAsigResc.setBackgroundPaint(Color.white);
       CategoryPlot plot = (CategoryPlot) chartNotifAsigResc.getPlot();
        plot.setBackgroundPaint(color);
        plot.setDataset(dataset);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setUpperMargin(0.15);
        CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setItemLabelGenerator(new LabelGenerator(50.0));
        renderer.setItemLabelFont(new Font("Serif", Font.PLAIN, 8));
        renderer.setItemLabelsVisible(true);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
      setContentPane(chartPanel);
      this.pack();
      RefineryUtilities.centerFrameOnScreen(this);
      this.setVisible(true);
   }
   public void  visualizarSeriesTiemposRescateVictPorRobots(CategoryDataset dataset) {
       JFreeChart chart = ChartFactory.createBarChart(
        "Tiempos de Rescate de Victimas por cada robot ", // chart title
        "Robots en el entorno", // domain axis label
        "Tiempo milisegundos", // range axis label
        dataset, // data
        PlotOrientation.VERTICAL, // orientation
        true, // include legend
        true, // tooltips?
        false // URLs?
        );
        ChartPanel chartPanel = new ChartPanel(chart);
        chart.setBackgroundPaint(Color.white);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setUpperMargin(0.15);
        CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setItemLabelGenerator(new LabelGenerator(50.0));
        renderer.setItemLabelFont(new Font("Serif", Font.PLAIN, 8));
        renderer.setItemLabelsVisible(true);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        this.visualizar(chartPanel);
   }
    public void  visualizarSeriesEnergRescateVictPorRobots(CategoryDataset dataset) {
       JFreeChart chart = ChartFactory.createBarChart(
        "Tiempos de Rescate de Victimas por cada robot ", // chart title
        "Robots en el entorno", // domain axis label
        "Unidades de energía", // range axis label
        dataset, // data
        PlotOrientation.VERTICAL, // orientation
        true, // include legend
        true, // tooltips?
        false // URLs?
        );
        ChartPanel chartPanel = new ChartPanel(chart);
        chart.setBackgroundPaint(Color.white);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setUpperMargin(0.15);
        CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setItemLabelGenerator(new LabelGenerator(50.0));
        renderer.setItemLabelFont(new Font("Serif", Font.PLAIN, 8));
        renderer.setItemLabelsVisible(true);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        this.visualizar(chartPanel);
   }
     void crearChartCombinadoAsignRescateVict(CategoryDataset dataset1,CategoryDataset dataset2) {
        
//        CategoryDataset dataset1 = createDataset1();
        NumberAxis rangeAxis1 = new NumberAxis("Tiempos en milisegundos");
        rangeAxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        LineAndShapeRenderer renderer1 = new LineAndShapeRenderer();
        renderer1.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
        CategoryPlot subplot1 = new CategoryPlot(dataset1, null, rangeAxis1, renderer1);
        subplot1.setDomainGridlinesVisible(true);
//        CategoryDataset dataset2 = createDataset2();
        NumberAxis rangeAxis2 = new NumberAxis("Unidades de energía");
        rangeAxis2.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        BarRenderer renderer2 = new BarRenderer();
        renderer2.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
        CategoryPlot subplot2 = new CategoryPlot(dataset2, null, rangeAxis2, renderer2);
        subplot2.setDomainGridlinesVisible(true);
        CategoryAxis domainAxis = new CategoryAxis("Victimas ordenadas por tiempos de rescate");
        CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot(domainAxis);
        plot.add(subplot1, 2);
        plot.add(subplot2, 1);
        JFreeChart result = new JFreeChart(
        "Tiempos de Rescate y Energía consumida para salvar las victimas",
        new Font("SansSerif", Font.BOLD, 12),
        plot,
        true
        );
        ChartPanel chartPanel = new ChartPanel(result);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        this.visualizar(chartPanel);
//        setContentPane(chartPanel);
//        this.pack();
//        RefineryUtilities.centerFrameOnScreen(this);
//        this.setVisible(true);
     }
     private void visualizar(ChartPanel panel){
         setContentPane(panel);
         this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
     }
}
