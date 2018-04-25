package app.utils.charts;

import app.H20Sim;
import app.sim.SimContext;
import app.stats.Collector;
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
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class ChartDepthSuccessRate implements Chart {

    private final JFreeChart chart;

    public ChartDepthSuccessRate (Collector collector, Map<Thread, SimContext> threadContextMap) {
        chart = createChart(createDataset(collector, threadContextMap));
    }

    public static JFreeChart createChart (XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Depth Successful rate",
                "Samples",
                "% Frames arrived",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesLinesVisible(0,false);

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        return chart;
    }


    private static XYDataset createDataset (Collector collector, Map<Thread, SimContext> threadContextMap) {
        Map<Float, Double> stats = new HashMap<>();
        XYSeries series = new XYSeries("Successful rate");

        for (int j = 0; j < H20Sim.N_SAMPLES; j++) {
            for (Thread thread : threadContextMap.keySet()) {
                collector.getSourceSamples(thread.getName()).get(j).getDeptArrivalSuccessRate().forEach((k, v) -> stats.merge(k, v, (t, u) -> (t + u) / 2));
            }
        }

        SortedSet<Float> keys = new TreeSet<>(stats.keySet());
        for (Float depth : keys) {
            series.add(depth, stats.get(depth));
            System.out.println(depth);
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return dataset;
    }


    @Override
    public JFreeChart getChart () {
        return chart;
    }
}
