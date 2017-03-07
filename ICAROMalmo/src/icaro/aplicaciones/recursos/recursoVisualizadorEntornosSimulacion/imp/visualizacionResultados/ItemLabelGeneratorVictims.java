/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.visualizacionResultados;

import java.text.NumberFormat;
import org.jfree.chart.labels.AbstractCategoryItemLabelGenerator;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.ApplicationFrame;

/**
 *
 * @author FGarijo
 */
public class ItemLabelGeneratorVictims extends ApplicationFrame {

/**
* A custom label generator.
*/
static class LabelGenerator extends AbstractCategoryItemLabelGenerator
implements CategoryItemLabelGenerator {
/** The threshold. */
private double threshold;
/**
* Creates a new generator that only displays labels that are greater
* than or equal to the threshold value.
*
* @param threshold the threshold value.
*/
public LabelGenerator(double threshold) {
super("", NumberFormat.getInstance());
this.threshold = threshold;
}
/**
* Generates a label for the specified item. The label is typically a
* formatted version of the data value, but any text can be used.
*
* @param dataset the dataset (<code>null</code> not permitted).
* @param series the series index (zero-based).
* @param category the category index (zero-based).
*
* @return the label (possibly <code>null</code>).
*/
public String generateLabel(CategoryDataset dataset,
int series,
int category) {
String result = null;
Number value = dataset.getValue(series, category);
if (value != null) {
double v = value.doubleValue();
if (v > this.threshold) {
result = value.toString(); // could apply formatting here
}
}
return result;
}
}
/**
* Creates a new demo instance.
*
* @param title the frame title.
*/
public ItemLabelGeneratorVictims(String title) {
super(title);
//CategoryDataset dataset = createDataset();
//JFreeChart chart = createChart(dataset);
//ChartPanel chartPanel = new ChartPanel(chart);
//chartPanel.setPreferredSize(new Dimension(500, 270));
//setContentPane(chartPanel);
}    
}
