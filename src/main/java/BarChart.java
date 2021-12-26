import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.Color;
import java.util.Map;

public class BarChart extends JFrame {

    public BarChart(Map<String, Double> tickets) {
        initUI(tickets);
    }

    public void initUI(Map<String, Double> tickets) {
        CategoryDataset dataset = createDataset(tickets);

        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Bar chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private CategoryDataset createDataset(Map<String, Double> tickets) {
        var dataset = new DefaultCategoryDataset();
        tickets.forEach((key, value) -> dataset.setValue(value, "Cost tickets", key));
        return dataset;
    }

    private JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart barChart = ChartFactory.createBarChart(
                "Cost tickets by gender",
                "",
                "Cost",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);

        return barChart;
    }
}