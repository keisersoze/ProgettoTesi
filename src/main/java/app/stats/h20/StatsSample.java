package app.stats.h20;

import app.model.Frame;
import app.sim.SimContext;
import app.stats.Sample;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class StatsSample implements Sample {
    private double nFrames;
    private double nFramesArrived;
    private double simTime;
    private double avgResponseTime;

    private Map<Float, Map.Entry<Double, Double>> percentageToY = new HashMap<>();

    public StatsSample (SimContext context) {
        //throughput
        simTime = context.getSimTime();

        //successfull rate + response time
        nFrames = context.getFrames().size();
        avgResponseTime = 0;
        nFramesArrived = 0;
        for (Map.Entry<Frame, LinkedList<Double>> entry : context.getFramesArrived().entrySet()) {
            if (entry.getValue().size() > 0) {
                nFramesArrived++;
                avgResponseTime += entry.getValue().get(0) - entry.getKey().getArrivalTime();
            }
        }
        avgResponseTime /= nFramesArrived;

        // Tiene traccia dei frame partiti da una certa profondità in relazione al successo di arrivo ai sink
        for (Map.Entry<Frame, LinkedList<Double>> entry : context.getFramesArrived().entrySet()) {
            if (percentageToY.containsKey(entry.getKey().getOwner().getY())) {
                double prevNumberOfArrivalAtY = percentageToY.get(entry.getKey().getOwner().getY()).getKey();
                double prevNumberOfSuccessAtY = percentageToY.get(entry.getKey().getOwner().getY()).getValue();

                prevNumberOfSuccessAtY += entry.getValue().size() > 0 ? 1 : 0;
                Map.Entry<Double, Double> newEntry = new AbstractMap.SimpleEntry<>(prevNumberOfArrivalAtY + 1, prevNumberOfSuccessAtY);

                percentageToY.put(entry.getKey().getOwner().getY(), newEntry);
            } else {
                Map.Entry<Double, Double> newEntry = new AbstractMap.SimpleEntry<>(1d, entry.getValue().size() > 0 ? 1d : 0d);
                percentageToY.put(entry.getKey().getOwner().getY(), newEntry);
            }
        }
    }

    @Override
    public double getSuccessfullRate () {
        return nFramesArrived / nFrames;
    }

    @Override
    public double getGoodput () {
        return nFramesArrived / simTime;
    }

    @Override
    public double getAvgResponseTime () {
        return avgResponseTime;
    }

    @Override
    public Map<Float, Double> getDeptArrivalSuccessRate () {
        Map<Float, Double> stats = new HashMap<>();
        for (Map.Entry<Float, Map.Entry<Double, Double>> entry : percentageToY.entrySet()) {
            stats.put(entry.getKey(), entry.getValue().getValue()/entry.getValue().getKey() * 100);
        }
        return stats;
    }

    @Override
    public String toString () {
        return getSuccessfullRate() + " ";
    }


}
