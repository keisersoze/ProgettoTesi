package app;

import app.stats.SamplesCollector;
import app.stats.StatsSample;

import java.util.ArrayList;
import java.util.List;

public class H2OSim {
    private static H2OSim ourInstance = new H2OSim();

    public static H2OSim getInstance() {
        return ourInstance;
    }

    //parametri simulazione
    public static final int NTHREADS = 10;
    public static final int NEVENTS = 5;
    public static final double SAMPLING_INTERVAL = 50;


    public static void main(String[] args) {

        //inizializzazione
        SamplesCollector collector = new SamplesCollector();
        List <Thread> istances = new ArrayList<>();

        //avvio dei thread
        for (int i=0 ; i<NTHREADS ; i++ ){
            String istance_name = String.valueOf(i);
            collector.addStatSource(istance_name);
            istances.add(new Thread(new SimulationInstance(collector),String.valueOf(i)));
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
        for (int i=0 ; i<NTHREADS ; i++ ){
            System.out.println(collector.getSourceSamples(String.valueOf(i)));
        }


    }
}
