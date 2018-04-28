package app.utils.charts;

import app.H20Sim;
import app.sim.SimContext;
import app.stats.Collector;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.Map;

public class ChartModalities implements Chart {

    private final JFreeChart chart;

    public ChartModalities (Collector collector, Map<Thread, SimContext> threadContextMap) {
        chart = createChart(createDataset(collector, threadContextMap));
    }

    private static JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Modalities rate",
                "Samples",
                "Rate",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(.5f));

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

    private static XYDataset createDataset(Collector collector, Map<Thread, SimContext> threadContextMap) {

        XYSeries series = new XYSeries("Trasmission modality rate");
        for (int j = 0; j < H20Sim.N_SAMPLES; j++) {
            double transmissionModeRateAcc = 0;
            for (Thread t : threadContextMap.keySet()) {
                transmissionModeRateAcc += collector.getSourceSamples(t.getName()).get(j).getTransmittingModeRate();
            }
            series.add(j, transmissionModeRateAcc / H20Sim.NTHREADS);
        }

        XYSeries series2 = new XYSeries("Receiving modality rate");
        for (int j = 0; j < H20Sim.N_SAMPLES; j++) {
            double receivingModeRateAcc = 0;
            for (Thread t : threadContextMap.keySet()) {
                receivingModeRateAcc += collector.getSourceSamples(t.getName()).get(j).getReceivingModeRate();
            }
            series2.add(j, receivingModeRateAcc / H20Sim.NTHREADS);
        }

        XYSeries series3 = new XYSeries("Sleep modality rate");
        for (int j = 0; j < H20Sim.N_SAMPLES; j++) {
            double sleepModeRateAcc = 0;
            for (Thread t : threadContextMap.keySet()) {
                sleepModeRateAcc += collector.getSourceSamples(t.getName()).get(j).getSleepModeRate();
            }
            series3.add(j, sleepModeRateAcc / H20Sim.NTHREADS);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        return dataset;
    }

    public JFreeChart getChart() {
        return chart;
    }
}
