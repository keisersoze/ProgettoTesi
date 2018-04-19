package app.stats.h20;

import app.sim.SimContext;
import app.stats.Sample;

import java.util.LinkedList;

public class StatsSample implements Sample {
    private double nFrames;
    private double nFramesArrived;

    public StatsSample(SimContext context) {
        int cont = 0;
        nFrames = context.getFrames().size();
        for(LinkedList<Double> list : context.getFramesArrived().values()){
            if(list.size() > 0){
                cont++;
            }
        }
        nFramesArrived = cont;
    }

    @Override
    public double getSuccessfullRate() {
        return nFramesArrived / nFrames;
    }

    @Override
    public String toString() {
        return getSuccessfullRate() + " ";
    }
}
