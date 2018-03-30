package app;

import app.core.scheduler.DefaultScheduler;
import app.stats.BaseCollector;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;

import java.util.ArrayList;
import java.util.List;


public class H2OSim {
    //parametri simulazione
    public static final int NTHREADS = 10;
    public static final int NEVENTS = 100;
    public static final double SAMPLING_INTERVAL = 50; // descrive il tempo che intercorre i campionamenti per la statistiche
    public static final boolean DEMO_MODE = true;
    public static boolean FINISHED = false;
    private static H2OSim ourInstance = new H2OSim();

    public static H2OSim getInstance() {
        return ourInstance;
    }

    public static void main(String[] args) throws InterruptedException {
        if (DEMO_MODE) {
            Demo d = new Demo();
            d.start();

            while (d.getArray_line() == null || !d.isCharged()) { Thread.sleep(0,1); }

//            while (true) {
//                Material lineMaterial = new Material(d.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
//
//                lineMaterial.setColor("Color", ColorRGBA.randomColor());
//                Geometry geo = d.getArray_line().get((int) (Math.random() * d.getArray_line().size()));
//                geo.setMaterial(lineMaterial);
//                Thread.sleep(100,1);
//            }
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
