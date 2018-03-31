package app;

import app.core.scheduler.impl.DefaultScheduler;
import app.stats.impl.BaseCollector;

import java.util.ArrayList;
import java.util.List;


public class H2OSim {
    //parametri simulazione
    public static final int NTHREADS = 10;
    public static final int NEVENTS = 100;
    public static final double SAMPLING_INTERVAL = 50; // descrive il tempo che intercorre i campionamenti per la statistiche
    public static final boolean DEMO_MODE = true;
    private static H2OSim ourInstance = new H2OSim();

    public static H2OSim getInstance() {
        return ourInstance;
    }

    public static void main(String[] args) {

        if (DEMO_MODE) {
            Demo d = new Demo();
            d.start();

        } else {
            //inizializzazione
            BaseCollector collector = new BaseCollector();
            List<Thread> istances = new ArrayList();

            //avvio dei thread
            for (int i = 0; i < NTHREADS; i++) {
                String istance_name = String.valueOf(i);
                collector.addStatSource(istance_name);
                istances.add(new Thread(new SimulationInstance(collector, new DefaultScheduler()), String.valueOf(i)));
                istances.get(i).start();
            }


            //aspetta che tutte le istanze siano terminate
            for (int i = 0; i < NTHREADS; i++) {
                try {
                    istances.get(i).join();
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
