package app;


import app.core.h20.scheduler.DefaultScheduler;
import app.sim.SimContext;
import app.sim.h20.GraphicSim;
import app.sim.h20.SimulationInstance;
import app.stats.Sample;
import app.stats.h20.BaseCollector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class H2OSim extends Application {

    //parametri simulazione
    public static final double MU = 3;
    public static final double LAMDA = 0.1;
    public static final int NTHREADS = 2;
    public static final int NEVENTS = 200000;
    public static final double MAX_DISTANCE = 50;
    public static final double SCALE = 10;


    public static final boolean CANVAS_MODE = false;

    public static final int SENSOR_BANDWIDTH = 1000; // b/s
    public static final int MAX_FRAME_SIZE = 1000; //bit (200-1600)
    public static final double MAX_FRAME_RATE = 0.9;
    public static final double BATTERY_LIFE = 1000; // mA
    public static final int THRESHOLD = 10;
    public static final double SENSIBILITY = -106; //dBm
    public static final double SENSOR_POWER = 5; //dB
    public static final double SENSOR_FREQUENCY = 2400; //HZ

    //variabili endogene
    public static final int SOUND_SPEED = 343; // m/s
    public static final double T = 1; //TODO guardare come si chiama


    public static BaseCollector collector = new BaseCollector();
    public static Map<String, SimContext> contextList = new HashMap<>();

    public static void main(String[] args) {

        if (CANVAS_MODE) {
            new GraphicSim(collector, new DefaultScheduler()).run();

        } else {
            //inizializzazione

            List<Thread> instances = new ArrayList<>();

            //avvio dei thread
            for (int i = 0; i < NTHREADS; i++) {
                String instance_name = String.valueOf(i);
                collector.addStatSource(instance_name);
                SimulationInstance context = new SimulationInstance(collector, new DefaultScheduler());
                instances.add(new Thread(context, instance_name));
                contextList.put(instance_name, context);
                instances.get(i).start();
            }


            //aspetta che tutte le istanze siano terminate
            for (int i = 0; i < NTHREADS; i++) {
                try {
                    instances.get(i).join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //stampo le statistiche
            for (int i = 0; i < NTHREADS; i++) {
                System.out.println(collector.getSourceSamples(String.valueOf(i)));
            }

            launch(args);

        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Line Chart Sample");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of Month");
        //creating the chart
        final LineChart<Number, Number> lineChart =
                new LineChart<Number, Number>(xAxis, yAxis);

        lineChart.setTitle("Stock Monitoring, 2010");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");
        //populating the series with data

        int xCont = 0;
        int nMinSamples = -1;

        for (int i = 0; i < NTHREADS; i++) {
            int x = (int) (contextList.get(String.valueOf(i)).getSimTime() * LAMDA);
            if (x < nMinSamples || nMinSamples == -1)
                nMinSamples = x;
        }

        for (int j = 0; j < nMinSamples; j++) {
            double successfullRateAcc = 0;
            for (int i = 0; i < NTHREADS; i++) {
                successfullRateAcc += collector.getSourceSamples(String.valueOf(i)).get(j).getSuccessfullRate();
            }
            series.getData().add(new XYChart.Data(xCont, successfullRateAcc / NTHREADS));
            xCont++;

        }


        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.getData().add(series);

        stage.setScene(scene);
        stage.show();
    }
}
