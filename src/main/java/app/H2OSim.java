package app;


import app.core.scheduler.impl.DefaultScheduler;
import app.sim.impl.GraphicSim;
import app.sim.impl.SimulationInstance;
import app.stats.impl.BaseCollector;
import org.apache.commons.math3.random.MersenneTwister;

import java.util.ArrayList;
import java.util.List;


public class H2OSim {

    //parametri simulazione
    public static final int MU = 3;
    public static final int LAMDA = 3;
    public static final int NTHREADS = 1;
    public static final int NEVENTS = 100;
    public static final boolean CANVAS_MODE = true;

    //risorse condivise
    public static final MersenneTwister MERSENNE_TWISTER = new MersenneTwister();

    //pattern singleton per avere accesso alle risorse condivise
    private static H2OSim ourInstance = new H2OSim();

    public static H2OSim getInstance() {
        return ourInstance;
    }

    public static void main(String[] args) throws InterruptedException {


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
