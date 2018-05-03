package app.utils.charts;

import app.H20Sim;
import app.sim.h20.AbstractSimInstance;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ChartDepthSuccessRate implements Chart {

    private final JFreeChart chart;

    public ChartDepthSuccessRate (List<AbstractSimInstance> instances) {
        chart = createChart(createDataset(instances));
    }

    public static JFreeChart createChart (IntervalXYDataset dataset) {

        final JFreeChart chart = ChartFactory.createXYBarChart("Depth Succesuful Rate", "Depth", false, "% Frame Arrived", dataset, PlotOrientation.VERTICAL, true, true, false);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        final XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setShadowVisible(false);
        renderer.setMargin(0);
        chart.setAntiAlias(true);
        chart.setTextAntiAlias(true);

        return chart;
    }


    private static IntervalXYDataset createDataset ( List<AbstractSimInstance> instances) {
        Map<Integer, Double> stats = new HashMap<>();
        XYSeries series = new XYSeries("Successful rate");

        for (int j = 0; j < H20Sim.N_SAMPLES; j++) {
            for (AbstractSimInstance context : instances) {
                context.getCollector().getSourceSamples(context.getName()).get(j).getDeptArrivalSuccessRate().forEach((k, v) -> stats.merge(k, v, (t, u) -> (t + u) / 2));
            }
        }

        SortedSet<Integer> keys = new TreeSet<>(stats.keySet());
        for (Integer depth : keys) {
            series.add(H20Sim.FIELD_Y - depth * 100, stats.get(depth));
        }
        return new XYSeriesCollection(series);
    }


    @Override
    public JFreeChart getChart () {
        return chart;
    }
}
