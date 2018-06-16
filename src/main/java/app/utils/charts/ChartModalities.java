package app.utils.charts;

import app.H20Sim;
import app.sim.h20.AbstractSimInstance;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
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

        /*chart.setTitle(new TextTitle("Average Salary per Age",
                        new Font("Serif", java.awt.Font.BOLD, 18)
                )
        );*/

        return chart;

    }

    private static XYDataset createDataset (List<AbstractSimInstance> instances) {

        XYSeries series = new XYSeries("Transmission status rate");
        for (int j = 0; j < H20Sim.N_SAMPLES; j++) {
            double transmissionModeRateAcc = 0;
            for (AbstractSimInstance context : instances) {
                transmissionModeRateAcc += context.getCollector().getSourceSamples(context.getName()).get(j).getTransmittingModeRate();
            }
            series.add(j, transmissionModeRateAcc / H20Sim.NTHREADS);
        }

        XYSeries series2 = new XYSeries("Receiving status rate");
        for (int j = 0; j < H20Sim.N_SAMPLES; j++) {
            double receivingModeRateAcc = 0;
            for (AbstractSimInstance context : instances) {
                receivingModeRateAcc += context.getCollector().getSourceSamples(context.getName()).get(j).getReceivingModeRate();
            }
            series2.add(j, receivingModeRateAcc / H20Sim.NTHREADS);
        }

        XYSeries series3 = new XYSeries("Idle status rate");
        for (int j = 0; j < H20Sim.N_SAMPLES; j++) {
            double sleepModeRateAcc = 0;
            for (AbstractSimInstance context : instances) {
                sleepModeRateAcc += context.getCollector().getSourceSamples(context.getName()).get(j).getSleepModeRate();
            }
            series3.add(j, sleepModeRateAcc / H20Sim.NTHREADS);
        }

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
