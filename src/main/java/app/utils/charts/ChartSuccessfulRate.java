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
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.Map;

public class ChartSuccessfulRate implements Chart{
    private JFreeChart chart;

    public ChartSuccessfulRate(Collector collector, Map<Thread, SimContext> threadContextMap) {
        chart= createChart(createDataset(collector,threadContextMap));
    }

    public static JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Successful rate",
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
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        return chart;

    }

    public static XYDataset createDataset(Collector collector, Map<Thread, SimContext> threadContextMap) {

        XYSeries series = new XYSeries("Successful rate");
        for (int j = 0; j < H20Sim.N_SAMPLES; j++) {
            double successfullRateAcc = 0;
            for (Thread t : threadContextMap.keySet()) {
                successfullRateAcc += collector.getSourceSamples(t.getName()).get(j).getSuccessfullRate() * 100;
            }
            series.add(j, successfullRateAcc / H20Sim.NTHREADS);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return dataset;
    }

    public JFreeChart getChart() {
        return chart;
    }
}
