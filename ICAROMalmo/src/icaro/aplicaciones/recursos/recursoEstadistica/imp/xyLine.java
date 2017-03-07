package icaro.aplicaciones.recursos.recursoEstadistica.imp;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class xyLine{
public static void main(String arg[]){
	XYSeries series = new XYSeries("Average Weight");
	series.add(20, 20);
	series.add(40, 25);
	series.add(55, 50);
	series.add(70, 65);

//	series.add(20.0, 20.0);
//	series.add(40.0, 25.0);
//	series.add(55.0, 50.0);
//	series.add(70.0, 65.0);
	XYDataset xyDataset = new XYSeriesCollection(series);
	JFreeChart chart = ChartFactory.createXYLineChart
		      ("XYLine Chart using JFreeChart", "Age", "Weight", xyDataset, PlotOrientation.VERTICAL, true, true, false);
	ChartFrame frame1=new ChartFrame("XYLine Chart",chart);
	frame1.setVisible(true);
	frame1.setSize(300,300);
	}
}