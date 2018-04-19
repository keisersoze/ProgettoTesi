package app;


import app.core.h20.scheduler.DefaultScheduler;
import app.factory.DeploymentTypes;
import app.sim.SimContext;
import app.sim.h20.GraphicSim;
import app.sim.h20.SimulationInstance;
import app.stats.h20.BaseCollector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;


public class H20Sim extends Application {

    //parametri simulazione
    public static final double MU = 3;
    public static final double LAMDA = 0.1;

    private static final int NTHREADS = 5;
    public static final int NEVENTS = 200000;

    public static final double MAX_DISTANCE = 50;
    public static final double SCALE = 10;

    private static final boolean CANVAS_MODE = true;

    public static final int SENSOR_BANDWIDTH = 1000; // b/s
    public static final int MAX_FRAME_SIZE = 1000; //bit (200-1600)
    public static final double MAX_FRAME_RATE = 0.9;

    public static final int THRESHOLD = 1;
    public static final double SENSIBILITY = -106; //dBm
    public static final double SENSOR_POWER = 5; //dB
    public static final double SENSOR_FREQUENCY = 2400; //HZ

    public static final String DEPLOYMENT_TYPE = DeploymentTypes.BaseDeployment;

    //variabili endogene
    public static final int SOUND_SPEED = 343; // m/s
    public static final double T = 1; //TODO guardare come si chiama

    private static BaseCollector collector = new BaseCollector();
    private static Map<Thread, SimContext> threadContextMap = new HashMap<>();

    public static void main (String[] args) {

        if (CANVAS_MODE) {
            new GraphicSim(collector, new DefaultScheduler()).run();

        } else {
            //inizializzazione

            //avvio dei thread
            for (int i = 0; i < NTHREADS; i++) {
                String instance_name = String.valueOf(i);
                collector.addStatSource(instance_name);
                SimulationInstance context = new SimulationInstance(collector, new DefaultScheduler());
                Thread thread = new Thread(context, instance_name);
                threadContextMap.put(thread, context);
                thread.start();
            }


            //aspetta che tutte le istanze siano terminate
            for (Thread t : threadContextMap.keySet()) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //stampo le statistiche
            launch(args);

        }
    }

    @Override
    public void start (Stage stage) {
        //stage.setTitle("Line Chart Sample");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Samples");
        yAxis.setLabel("% rate successfull");
        //creating the chart
        final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

        lineChart.setTitle("Success rate (Frame)");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        //series.setName("My portfolio");
        //populating the series with data

        int xCont = 0;
        int nMinSamples = -1;

        for (Thread t : threadContextMap.keySet()) {
            int x = (int) (threadContextMap.get(t).getSimTime() * LAMDA);
            if (x < nMinSamples || nMinSamples == -1) { nMinSamples = x; }
        }

        for (int j = 0; j < nMinSamples; j++) {
            double successfullRateAcc = 0;
            for (Thread t : threadContextMap.keySet()) {
                successfullRateAcc += collector.getSourceSamples(t.getName()).get(j).getSuccessfullRate() * 100;
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
