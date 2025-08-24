package org.example.model;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;

public class ChartDrawer extends ApplicationFrame {

    public ChartDrawer(String title, double[][] distribution) {
        super(title);
        JFreeChart barChart = ChartFactory.createBarChart(
                "Distribution of values",
                "Range",
                "Count",
                createDataset(distribution)
        );

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset(double[][] distribution) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (double[] entry : distribution) {
            double start = entry[0];
            double end = entry[1];
            double count = entry[2];
            String rangeLabel = String.format("%.1f-%.1f", start, end);
            dataset.addValue(count, "Values", rangeLabel);
        }
        return dataset;
    }
}
