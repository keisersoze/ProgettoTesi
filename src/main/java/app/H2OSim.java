package app;


import app.core.scheduler.impl.DefaultScheduler;
import app.model.Sensor;
import app.model.impl.BaseSensor;
import app.model.impl.V3FSensor;
import app.stats.impl.BaseCollector;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import org.apache.commons.math3.random.MersenneTwister;

import java.util.ArrayList;
import java.util.List;


public class H2OSim {

    //parametri simulazione
    public static final int MU = 3;
    public static final int LAMDA = 3;
    public static final int NTHREADS = 10;
    public static final int NEVENTS = 100;
    public static final double SAMPLING_INTERVAL = 50; // descrive il tempo che intercorre i campionamenti per la statistiche
    public static final boolean DEMO_MODE = false;

    //risorse condivise
    public static final MersenneTwister MERSENNE_TWISTER = new MersenneTwister();

    //pattern singleton per avere accesso alle risorse condivise
    private static H2OSim ourInstance = new H2OSim();

    public static H2OSim getInstance() {
        return ourInstance;
    }

    public static void main(String[] args) throws InterruptedException {

        Sensor s1,s2,s3,s4;
        s1= new V3FSensor(3,3,3);
        s2= new V3FSensor(0,0,0);

        s3= new BaseSensor(3,3,3);
        s4= new BaseSensor(0,0,0);

        System.out.println(s1.getEuclideanDistance(s2));
        System.out.println(s3.getEuclideanDistance(s4));

        if (DEMO_MODE) {
            Demo d = new Demo();
            d.start();

            while (d.getArray_line() == null || !d.isCharged()) {
                Thread.sleep(0, 1);
            }

            // Proviamo a fare un po' di discoteca
            int i = 0;
            while (i++ != 5000) {
                Material lineMaterial = new Material(d.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");

                lineMaterial.setColor("Color", ColorRGBA.randomColor());
                Geometry geo = d.getArray_line().get((int) (Math.random() * d.getArray_line().size()));
                geo.setMaterial(lineMaterial);
                Thread.sleep(30);
            }
        } else {
            //inizializzazione
            BaseCollector collector = new BaseCollector();
            List<Thread> istances = new ArrayList<>();

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
