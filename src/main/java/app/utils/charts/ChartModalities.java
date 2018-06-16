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

public class ChartModalities implements Chart {

    private final JFreeChart chart;

    public ChartModalities (List<AbstractSimInstance> instances) {
        chart = createChart(createDataset(instances));
    }

    private static JFreeChart createChart (XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart("Sensor status rate", "Samples", "Rate", dataset, PlotOrientation.VERTICAL, true, true, false);

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(2, Color.RED);
        renderer.setSeriesPaint(0, Color.blue);
        renderer.setSeriesPaint(1, Color.green);
        renderer.setSeriesStroke(0, new BasicStroke(1.2f));
        renderer.setShapesVisible(false);

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        /*chart.setTitle(new TextTitle("Average Salary per Age",
                        new Font("Serif", java.awt.Font.BOLD, 18)
                )
        );*/
        try {
            ChartUtilities.saveChartAsPNG(new File("charts/SSR.png"), chart, 850, 600);
        } catch (Exception e) {
            System.out.println("Error.");
        }
        return chart;

    }

    private static XYDataset createDataset (List<AbstractSimInstance> instances) {

        XYSeries series = new XYSeries("Transmission status rate");
        double mean = 0;
        for (int j = 0; j < H20Sim.N_SAMPLES; j++) {
            double transmissionModeRateAcc = 0;
            for (AbstractSimInstance context : instances) {
                transmissionModeRateAcc += context.getCollector().getSourceSamples(context.getName()).get(j).getTransmittingModeRate();
            }
            if(j >= H20Sim.N_SAMPLES/2){
                mean += transmissionModeRateAcc / H20Sim.NTHREADS;
            }
            series.add(j, transmissionModeRateAcc / H20Sim.NTHREADS);
        }
        System.out.println("transmitting: " + mean/(H20Sim.N_SAMPLES/2));

        XYSeries series2 = new XYSeries("Receiving status rate");
        mean = 0;
        for (int j = 0; j < H20Sim.N_SAMPLES; j++) {
            double receivingModeRateAcc = 0;
            for (AbstractSimInstance context : instances) {
                receivingModeRateAcc += context.getCollector().getSourceSamples(context.getName()).get(j).getReceivingModeRate();
            }
            if(j >= H20Sim.N_SAMPLES/2){
                mean += receivingModeRateAcc / H20Sim.NTHREADS;
            }
            series2.add(j, receivingModeRateAcc / H20Sim.NTHREADS);
        }
        System.out.println("receiving: " + mean/(H20Sim.N_SAMPLES/2));

        XYSeries series3 = new XYSeries("Idle status rate");
        mean = 0;
        for (int j = 0; j < H20Sim.N_SAMPLES; j++) {
            double sleepModeRateAcc = 0;
            for (AbstractSimInstance context : instances) {
                sleepModeRateAcc += context.getCollector().getSourceSamples(context.getName()).get(j).getSleepModeRate();
            }
            if (j >= H20Sim.N_SAMPLES / 2) {
                mean += sleepModeRateAcc / H20Sim.NTHREADS;
            }
            series3.add(j, sleepModeRateAcc / H20Sim.NTHREADS);
        }
        System.out.println("sleep: " + mean/(H20Sim.N_SAMPLES/2));

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(series3);
        dataset.addSeries(series2);
        return dataset;
    }

    public JFreeChart getChart () {
        return chart;
    }
}
