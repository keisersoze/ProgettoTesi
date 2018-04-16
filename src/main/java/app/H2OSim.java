package app;


import app.core.h20.scheduler.DefaultScheduler;
import app.sim.h20.GraphicSim;
import app.sim.h20.SimulationInstance;
import app.stats.h20.BaseCollector;

import java.util.ArrayList;
import java.util.List;


public class H2OSim {

    //parametri simulazione
    public static final int MU = 3;
    public static final int LAMDA = 3;
    public static final int NTHREADS = 10;
    public static final int NEVENTS = 100000;
    public static final double MAX_DISTANCE = 500;
    public static final double SCALE = 10;


    public static final boolean CANVAS_MODE = true;

    public static final int SENSOR_BANDWIDTH = 1000; // b/s
    public static final int MAX_FRAME_SIZE = 1000; //bit (200-1600)
    public static final double MAX_FRAME_RATE = 0.9;
    public static final double BATTERY_LIFE = 1000; // mA
    public static final int THRESHOLD = 100;

    //variabili endogene
    public static final int SOUND_SPEED = 343; // m/s

    //pattern singleton per avere accesso alle risorse condivise
    private static H2OSim ourInstance = new H2OSim();

    public static H2OSim getInstance() {
        return ourInstance;
    }

    public static void main(String[] args) {

        BaseCollector collector = new BaseCollector();
        if (CANVAS_MODE) {
            new GraphicSim(collector, new DefaultScheduler()).run();

        } else {
            //inizializzazione

            List<Thread> instances = new ArrayList<>();

            //avvio dei thread
            for (int i = 0; i < NTHREADS; i++) {
                String instance_name = String.valueOf(i);
                collector.addStatSource(instance_name);
                instances.add(new Thread(new SimulationInstance(collector, new DefaultScheduler()), String.valueOf(i)));
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
        }
    }

}
