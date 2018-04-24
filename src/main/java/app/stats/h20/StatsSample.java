package app.stats.h20;

import app.model.Frame;
import app.sim.SimContext;
import app.stats.Sample;

import java.util.LinkedList;
import java.util.Map;

public class StatsSample implements Sample {
    private double nFrames;
    private double nFramesArrived;
    private double simTime;
    private double avgResponseTime;

    public StatsSample(SimContext context) {

        // successfull rate
        int cont = 0;
        nFrames = context.getFrames().size();
        for (LinkedList<Double> list : context.getFramesArrived().values()) {
            if (list.size() > 0) {
                cont++;
            }
        }
        nFramesArrived = cont;

        //throughput
        simTime = context.getSimTime();

        //response time
        avgResponseTime = 0;
        for (Map.Entry<Frame, LinkedList<Double>> entry : context.getFramesArrived().entrySet()) {
            if (entry.getValue().size() > 0) {
                avgResponseTime += entry.getValue().get(0) - entry.getKey().getArrivalTime();
            }
        }
        avgResponseTime /= nFramesArrived;


    }

    @Override
    public double getSuccessfullRate() {
        return nFramesArrived / nFrames;
    }

    @Override
    public double getGoodput() {
        return nFramesArrived / simTime;
    }

    @Override
    public double getAvgResponseTime() {
        return avgResponseTime;
    }

    @Override
    public String toString() {
        return getSuccessfullRate() + " ";
    }


}
