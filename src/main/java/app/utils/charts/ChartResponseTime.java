package app.utils.charts;

import app.H20Sim;
import app.sim.h20.AbstractSimInstance;
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
import java.util.List;

public class ChartResponseTime implements Chart {
    private JFreeChart chart;

    public ChartResponseTime (Collector collector, List<AbstractSimInstance> instances) {
        chart = createChart(createDataset(collector, instances));
    }

    static JFreeChart createChart (XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart("Response time", "Samples", "Response Time (s)", dataset, PlotOrientation.VERTICAL, true, true, false);

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

        return chart;
    }

    private static XYDataset createDataset (Collector collector, List<AbstractSimInstance> instances) {
        XYSeries series = new XYSeries("Response time");
        for (int j = 0; j < H20Sim.N_SAMPLES; j++) {
            double successfullRateAcc = 0;
            for (Thread t : instances) {
                successfullRateAcc += collector.getSourceSamples(t.getName()).get(j).getAvgResponseTime();
            }
            series.add(j, successfullRateAcc / H20Sim.NTHREADS);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return dataset;
    }

    public JFreeChart getChart () {
        return chart;
    }
}
