package app;


import app.core.h20.scheduler.DefaultScheduler;
import app.factory.DeploymentTypes;
import app.sim.h20.AbstractSimInstance;
import app.sim.h20.SimulationInstance;
import app.sim.h20.ThreesholdGraphicSim;
import app.sim.h20G.GraphicSim;
import app.stats.h20.BaseCollector;
import app.utils.Settings;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class H20Sim {
    public static final double K = 1.5;
    public static boolean SERIAL_SIM = false;
    public static boolean SLOW_RETRANSMITION = true;
    public static final double ACK_TIMEOUT = 100; //s
    public static final int ACK_SIZE = 0; //bit
    public static String PROTOCOL = "Deterministic"; //Deterministic  Probabilistic
    public static float FIELD_X = 5000;
    public static float FIELD_Y = 3000;
    public static float FIELD_Z = 5000;
    public static volatile boolean START = false;
    public static boolean STOPPED = false;
    public static int NTHREADS = 1;
    public static int N_SAMPLES = 2000;
    //parametri simulazione
    public static double LAMDA = 1;
    public static double CSMA_STRENGTH = 3;
    public static double N_SENSORS = 300;
    public static double MOVEMENT_SPEED = 0.5; // m/s
    public static double MOVE_REFRESH = 30; // unità di sim time
    public static double MOVE_RADIUS = 200; //m
    public static float SCALE = 10f;
    public static int SENSOR_BANDWIDTH = 1000; // b/s
    public static int MAX_FRAME_SIZE = 1000; //bit (200-1600)
    public static double MAX_FRAME_RATE = 0.9;
    public static int THRESHOLD = 0;
    public static double SENSIBILITY = -110; //dBm
    public static double SENSOR_POWER = -104; //dB
    public static double SENSOR_FREQUENCY = 25000; //HZ
    public static String DEPLOYMENT_TYPE = DeploymentTypes.BaseDeployment;

    //variabili endogene
    public static int SOUND_SPEED = 1440; // m/s in acqua
    public static double GAMMA = 1;

    public static boolean CANVAS_MODE = true;

    private static List<AbstractSimInstance> instances = new ArrayList<>();
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
                instances.add(context);
                thread.start();
            } else {
                //inizializzazione
                //avvio dei thread
                for (int i = 0; i < NTHREADS; i++) {
                    String instance_name = String.valueOf(i);
                    collector.addStatSource(instance_name);
                    AbstractSimInstance context;
                    if (H20Sim.SERIAL_SIM) {
                        context = new ThreesholdGraphicSim(collector, new DefaultScheduler(), instance_name);
                    } else {
                        context = new SimulationInstance(collector, new DefaultScheduler(), instance_name);
                    }
                    instances.add(context);
                    context.start();
                }
            }

            //aspetta che tutte le istanze siano terminate
            for (AbstractSimInstance t : instances) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //stampo le statistiche
            if (!STOPPED) {
                Settings.drawCharts(collector, instances);
                Settings.buttonStart.setEnabled(true);
                Settings.buttonStop.setEnabled(false);
            }
            instances.clear();
        }
    }

    private static void settings () {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        SwingUtilities.invokeLater(Settings::createAndShowGUI);
    }
}
