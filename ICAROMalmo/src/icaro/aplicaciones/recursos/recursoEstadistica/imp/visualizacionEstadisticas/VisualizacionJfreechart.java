/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.recursos.recursoEstadistica.imp.visualizacionEstadisticas;

import java.awt.BasicStroke;
import java.awt.Color;
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
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
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
    XYPlot plot;

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
        plot = chart1.getXYPlot();
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
    
    
    public void setColorChartPlotBackgroundPaint(Color c){
        plot = chart1.getXYPlot();
        plot.setBackgroundPaint(c); //El fondo interior del grafico sera del color pasado por parametro
    }
    
        
    public void setColorChartPlotDomainGridlinePaint(Color c){    	
        plot = chart1.getXYPlot();
        plot.setDomainGridlinePaint(c);  //Las lineas verticales de la cuadricula se pinta de color pasado por parametro
    }


    public void setColorChartPlotRangeGridlinePaint(Color c){
          plot = chart1.getXYPlot();
    	  plot.setRangeGridlinePaint(c);  //Las lineas horizontales de la cuadricula se pintan de color pasado por parametro
    }
        
    
    
    public void inicializacionJFreeChart(){    	
        // Creates a SegmentedTimeline instance, the time scope is "09:30-11:30，13:00-15:00".
          chart1 = ChartFactory.createXYLineChart(
          "Victim's Notification and Assignment to Team members ",      // chart title Titulo local del grafico
          "Victim's Notification",                      // x axis label
          "Time in seconds",                      // y axis label
          null,                  // data
          PlotOrientation.VERTICAL,
          true,                     // include legend
          true,                     // tooltips
          false                     // urls
      );

      // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
      chart1.setBackgroundPaint(Color.white);   //El fondo exterior del grafico sera blanco
      //      chart1.setBackgroundPaint(Color.green);

      // get a reference to the plot for further customisation...
      plot = chart1.getXYPlot();
      plot.setBackgroundPaint(Color.lightGray); //El fondo interior del grafico sera gris
      //plot.setBackgroundPaint(Color.blue);

      plot.setDomainGridlinePaint(Color.white);  //Las lineas verticales de la cuadricula se pinta de color blanco
      plot.setRangeGridlinePaint(Color.white);  //Las lineas horizontales de la cuadricula se pintan de color blanco
      
          
      ChartPanel chartPanel = new ChartPanel(chart1);
      chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
      setContentPane(chartPanel);
      this.pack();
      RefineryUtilities.centerFrameOnScreen(this);
      this.setVisible(true);
    }


    //por el momento no me hace caso para personalizar el aspecto de la serie (aspecto del punto, color de los puntos, puntos unidos con lineas o no, ....)
    public void addSerie(int indexSerie, Color color, XYSeries localXYSeries){    	
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

	//localXYSeries.getItemCount();	
	//System.out.println("\n\n\n\n Numero de items de la serie--->" + localXYSeries.getItemCount() + "\n\n\n");	
    //localXYSeries.getDataItem(index);
    

    
//--------------------------------------------------------------------------------------------
//-------------------------- LOS SIGUIENTES METODOS NO SE USAN AHORA -------------------------    
//--------------------------------------------------------------------------------------------
    
    private  XYDataset createDataset(XYSeries serieDatos) {
        XYDataset xyDataset = new XYSeriesCollection(serieDatos);
        return xyDataset;
   }
   
   private JFreeChart createChart(final XYDataset dataset)
           
           {
       
       // create the chart...
       final JFreeChart chart = ChartFactory.createXYLineChart(
           "Line Chart Demo 6",      // chart title
           "X",                      // x axis label
           "Y",                      // y axis label
           dataset,                  // data
           PlotOrientation.VERTICAL,
           true,                     // include legend
           true,                     // tooltips
           false                     // urls
       );

       // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
       chart.setBackgroundPaint(Color.white);

//       final StandardLegend legend = (StandardLegend) chart.getLegend();
 //      legend.setDisplaySeriesShapes(true);
       
       // get a reference to the plot for further customisation...
       final XYPlot plot = chart.getXYPlot();
       plot.setBackgroundPaint(Color.lightGray);
   //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
       plot.setDomainGridlinePaint(Color.white);
       plot.setRangeGridlinePaint(Color.white);
       
       final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
       renderer.setSeriesLinesVisible(0, false);
       renderer.setSeriesShapesVisible(1, false);
       plot.setRenderer(renderer);

       // change the auto tick unit selection to integer units only...
       final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
       rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
       // OPTIONAL CUSTOMISATION COMPLETED.
               
       return chart;
       
   }
    
    
    
    public void VisualizarEstadisticasLLegadayAsignacionVictimas(XYDataset dataset){
  //       XYDataset xyDataset = new XYSeriesCollection(serieDatos);
        
        // Creates a SegmentedTimeline instance, the time scope is "09:30-11:30，13:00-15:00".
          JFreeChart  chart = ChartFactory.createXYLineChart(
            "Victim's Notification and Assignment to Team members ",      // chart title
            "Victim's Notification",                      // x axis label
            "Time in seconds",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );
          
          
          // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);

//        final StandardLegend legend = (StandardLegend) chart.getLegend();
  //      legend.setDisplaySeriesShapes(true);
        
        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
    //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));

        
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);
        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
  //      final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);                                               
    }
    
}
