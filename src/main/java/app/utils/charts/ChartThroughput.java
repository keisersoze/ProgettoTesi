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

public class ChartThroughput implements Chart {
    private JFreeChart chart;

    public ChartThroughput (List<AbstractSimInstance> instances) {
        chart = createChart(createDataset(instances));
    }

    private static JFreeChart createChart (XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart("Throughput", "Samples", "Frame/s", dataset, PlotOrientation.VERTICAL, true, true, false);

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

    private static XYDataset createDataset (List<AbstractSimInstance> instances) {

        XYSeries series = new XYSeries("Throughput");
        for (int j = 0; j < H20Sim.N_SAMPLES; j++) {
            double successfullRateAcc = 0;
            for (AbstractSimInstance context : instances) {
                successfullRateAcc += context.getCollector().getSourceSamples(context.getName()).get(j).getGoodput();
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
