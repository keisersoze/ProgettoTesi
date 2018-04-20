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

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;


public class H20Sim extends Application {
    public static final int N_SAMPLES = 200;
    //parametri simulazione
    public static final double MU = 3;
    public static final double LAMDA = 0.1;
    public static final double MOVEMENT_SPEED = 0.5;

    private static final int NTHREADS = 1;
    public static final int NEVENTS = 700000;

    public static final double MAX_DISTANCE = 50;
    public static final double SCALE = 10;

    public static final int SENSOR_BANDWIDTH = 1000; // b/s
    public static final int MAX_FRAME_SIZE = 1000; //bit (200-1600)
    public static final double MAX_FRAME_RATE = 0.9;

    public static final int THRESHOLD = 20;

    public static final double SENSIBILITY = -110; //dBm
    public static final double SENSOR_POWER = -130; //dB
    public static final double SENSOR_FREQUENCY = 2400; //HZ

    public static final String DEPLOYMENT_TYPE = DeploymentTypes.LayerProportionalDeployment;

    //variabili endogene
    public static final int SOUND_SPEED = 343; // m/s
    public static final double GAMMA = 1; //TODO guardare come si chiama

    private static final boolean CANVAS_MODE = false;

    private static BaseCollector collector = new BaseCollector();
    private static Map<Thread, SimContext> threadContextMap = new HashMap<>();

    public static void main(String[] args) {
        settings();

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

    private static void settings() {
        // set look and feel to the system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new Settings().setVisible(true));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void start(Stage stage) {
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


        for (int j = 0; j < N_SAMPLES; j++) {
            double successfullRateAcc = 0;
            for (Thread t : threadContextMap.keySet()) {
                successfullRateAcc += collector.getSourceSamples(t.getName()).get(j).getSuccessfullRate() * 100;
            }
            series.getData().add(new XYChart.Data(j, successfullRateAcc / NTHREADS));
        }


        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.getData().add(series);

        stage.setScene(scene);
        stage.show();
    }
}
