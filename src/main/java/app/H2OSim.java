package app;


import app.core.scheduler.impl.DefaultScheduler;
import app.model.impl.V3FSensor;
import app.stats.impl.BaseCollector;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Spatial;
import org.apache.commons.math3.random.MersenneTwister;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;


public class H2OSim {

    //parametri simulazione
    public static final int MU = 3;
    public static final int LAMDA = 3;
    public static final int NTHREADS = 10;
    public static final int NEVENTS = 100;
    public static final double SAMPLING_INTERVAL = 50; // descrive il tempo che intercorre i campionamenti per la statistiche
    public static final boolean DEMO_MODE = true;
    //risorse condivise
    public static final MersenneTwister MERSENNE_TWISTER = new MersenneTwister();
    public static ArrayList<V3FSensor> arraySensor;
    public static Demo d;
    //pattern singleton per avere accesso alle risorse condivise
    private static H2OSim ourInstance = new H2OSim();

    public static H2OSim getInstance() {
        return ourInstance;
    }

    public static void main(String[] args) throws InterruptedException {


        if (DEMO_MODE) {
            d = new Demo(null, null);
            V3FSensor s1, s2;

            d.start();

            while (!d.isCharged()) {
                Thread.sleep(1);
            }

            s1 = new V3FSensor(0, 0, 0);
            s2 = new V3FSensor(0, 0, 0);

            arraySensor = new ArrayList<>();

            arraySensor.add(s1);
            arraySensor.add(s2);

            /* Muove i sensori avanti e indietro all'infinito*/
            double i = 0;
            while (true) {
                s1.setPosition((float) Math.cos(i) * 5, 0, (float) Math.sin(i) * 5);                                         // Offset dal centro
                d.enqueue((Callable<Spatial>) () -> d.linkBetweenGeometries(s1.getGeometry(), s2.getGeometry(), ColorRGBA.Green));    // Crea una line
                Thread.sleep(16, 666);                                                                                  //  Velocità di refresh
                i += 0.05;                                                                                                           //  Velocità di rotazione
            }

        } else {
            //inizializzazione
            BaseCollector collector = new BaseCollector();
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
