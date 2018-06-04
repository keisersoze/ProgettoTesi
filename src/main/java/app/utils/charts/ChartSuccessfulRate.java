package app.utils.charts;

import app.H20Sim;
import app.sim.h20.AbstractSimInstance;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.io.File;
import java.util.List;

public class ChartSuccessfulRate implements Chart {
    private JFreeChart chart;

    public ChartSuccessfulRate (List<AbstractSimInstance> instances) {
        chart = createChart(createDataset(instances));
    }

    static JFreeChart createChart (XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart("Packet Delivery Ratio", "Samples", "% Frames arrived", dataset, PlotOrientation.VERTICAL, true, true, false);

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(1.2f));
        renderer.setShapesVisible(false);

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        try {
            ChartUtilities.saveChartAsPNG(new File("charts/PDR.png"), chart, 850, 600);
        } catch (Exception e) {
            System.out.println("Error.");
        }

        return chart;
    }

    private static XYDataset createDataset (List<AbstractSimInstance> instances) {

        XYSeries series = new XYSeries("Successful rate");
        double mean = 0;
        for (int j = 0; j < H20Sim.N_SAMPLES; j++) {
            double successfullRateAcc = 0;
            for (AbstractSimInstance context : instances) {
                successfullRateAcc += context.getCollector().getSourceSamples(context.getName()).get(j).getSuccessfullRate() * 100;
            }
            if(j >= H20Sim.N_SAMPLES/2){
                mean += successfullRateAcc / H20Sim.NTHREADS;
            }
            series.add(j, successfullRateAcc / H20Sim.NTHREADS);
        }
        System.out.println("pdr: " + mean/(H20Sim.N_SAMPLES/2));
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return dataset;
    }

    public JFreeChart getChart () {
        return chart;
    }
}
