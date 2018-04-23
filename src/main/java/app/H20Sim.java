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
    public static float FIELD_X = 2000;
    public static float FIELD_Y = 1000;
    public static float FIELD_Z = 2000;

    static volatile boolean START = false;
    public static boolean STOPPED = false;

    public static int N_SAMPLES = 2000;
    //parametri simulazione
    public static double LAMDA = 0.1;
    public static double MOVEMENT_SPEED = 0.5;

    static int NTHREADS = 1;

    static float SCALE = 10f;

    public static int SENSOR_BANDWIDTH = 100; // b/s
    public static int MAX_FRAME_SIZE = 1000; //bit (200-1600)
    public static double MAX_FRAME_RATE = 0.9;

    public static int THRESHOLD = 20;

    public static double SENSIBILITY = -110; //dBm
    public static double SENSOR_POWER = -115; //dB
    public static double SENSOR_FREQUENCY = 40000; //HZ

    public static String DEPLOYMENT_TYPE = DeploymentTypes.BaseDeployment;

    //variabili endogene
    public static int SOUND_SPEED = 343; // m/s
    public static double GAMMA = 1;

    static boolean CANVAS_MODE = true;
    private static Map<Thread, SimContext> threadContextMap = new HashMap<>();
    private static BaseCollector collector = new BaseCollector();

    public static void main (String[] args) {
        settings();

        while (true) {
            while (!START) {
                Thread.onSpinWait();
            }
            START = false;
            STOPPED = false;
            Settings.resetProgressBar();

            if (CANVAS_MODE) {
                GraphicSim context = new GraphicSim(collector, new DefaultScheduler());
                collector.addStatSource("0");
                Thread thread = new Thread(context, "0");
                threadContextMap.put(thread, context);
                thread.start();
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
            if (!STOPPED) {
                launch(args);
                Settings.buttonStop.setEnabled(false);
                Settings.buttonStart.setEnabled(false);
                break;
            } else {
                threadContextMap.clear();
            }
        }
    }

    private static void settings () {
        // set look and feel to the system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(Settings::createAndShowGUI);

    }

    @SuppressWarnings("unchecked")
    @Override
    public void start (Stage stage) {
        //stage.setTitle("Line Chart Sample");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Samples");
        yAxis.setLabel("% rate successfull");
        //creating the chart
        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

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
