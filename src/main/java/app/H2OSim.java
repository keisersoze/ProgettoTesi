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
    public static final int NTHREADS = 2;
    public static final int NEVENTS = 100;
    public static final double SAMPLING_INTERVAL = 50; // descrive il tempo che intercorre i campionamenti per la statistiche
    public static final boolean CANVAS_MODE = false;
    //risorse condivise
    public static final MersenneTwister MERSENNE_TWISTER = new MersenneTwister();
    public static ArrayList<V3FSensor> arraySensor;
    public static Canvas canvas;
    //pattern singleton per avere accesso alle risorse condivise
    private static H2OSim ourInstance = new H2OSim();

    public static H2OSim getInstance() {
        return ourInstance;
    }

    public static void main(String[] args) throws InterruptedException {


        if (CANVAS_MODE) {
            canvas = new Canvas(null, null);
            V3FSensor s1, s2;

            canvas.start();

            while (!canvas.isCharged()) {
                Thread.sleep(1);
            }

            s1 = new V3FSensor(0, 0, 0, canvas);
            s2 = new V3FSensor(0, 0, 0, canvas);

            arraySensor = new ArrayList<>();

            arraySensor.add(s1);
            arraySensor.add(s2);

            /* Muove i sensori avanti e indietro all'infinito*/
            double i = 0;
            while (true) {
                s1.setPosition((float) Math.cos(i) * 5, (float) Math.sin(i) * 5, (float) Math.sin(i) * 5);
                s2.setPosition((float) Math.cos(i) * 15, (float) Math.sin(i) * 15, (float) Math.sin(i) * 15);
                canvas.enqueue((Callable<Spatial>) () -> canvas.linkBetweenGeometries(s1.getGeometry(), s2.getGeometry(), ColorRGBA.Green));
                Thread.sleep(16, 666);
                i += 0.05;
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
