package app.stats.h20;

import app.H20Sim;
import app.model.Frame;
import app.model.Sensor;
import app.sim.SimContext;
import app.stats.Sample;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class StatsSample implements Sample {
    private double nFrames;
    private double nFramesArrived;
    private double simTime;
    private double avgResponseTime;

    private Map<Integer, Double[]> intervalToSuccessfulRate;
    private Map<Integer, Double> intervalToSuccessful2Rate;

    private double nTransmitting;
    private double nReceiving;
    private double nSleep;

    public StatsSample(SimContext context) {

        //throughput
        simTime = context.getSimTime();

        //successfull rate + response time
        nFrames = context.getFrames().size();
        avgResponseTime = 0;
        nFramesArrived = 0;
        for (Map.Entry<Frame, LinkedList<Double>> entry : context.getFramesArrived().entrySet()) {
            if (entry.getValue().size() > 0) {
                nFramesArrived++;
                avgResponseTime += entry.getValue().getFirst();
            }
        }
        avgResponseTime /= nFramesArrived;

        // Tiene traccia dei frame partiti da una certa profondità in relazione al successo di arrivo ai sink
        intervalToSuccessfulRate = new HashMap<>();
        for (Map.Entry<Frame, LinkedList<Double>> entry : context.getFramesArrived().entrySet()) {
            double y = entry.getKey().getOwner().getY();
            if (!intervalToSuccessfulRate.containsKey(heightToIndex(y))) {
                intervalToSuccessfulRate.put(heightToIndex(y), new Double[]{0d, 0d});
            }
            intervalToSuccessfulRate.get(heightToIndex(y))[0]++;
            if (entry.getValue().size() > 0) {
                intervalToSuccessfulRate.get(heightToIndex(y))[1]++;
            }
        }
        intervalToSuccessful2Rate = new HashMap<>();
        intervalToSuccessfulRate.forEach((k, v) -> intervalToSuccessful2Rate.put(k, v[1] / v[0] * 100));

        //
        nTransmitting = 0;
        nReceiving = 0;
        nSleep = 0;

        for (Sensor s: context.getSensors()){
            if (!s.isSink())
                if (s.isTransmitting())
                    nTransmitting ++;
                else if (s.isReceiving())
                    nReceiving ++;
                else
                    nSleep++;
        }
        System.out.println(nSleep/H20Sim.N_SENSORS);
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
    public Map<Integer, Double> getDeptArrivalSuccessRate() {
        return intervalToSuccessful2Rate;
    }

    @Override
    public double getTransmittingModeRate() {
        return nTransmitting/H20Sim.N_SENSORS *100;
    }

    @Override
    public double getReceivingModeRate() {
        return nReceiving/H20Sim.N_SENSORS *100;
    }

    @Override
    public double getSleepModeRate() {
        return nSleep/H20Sim.N_SENSORS *100;
    }

    @Override
    public String toString() {
        return getSuccessfullRate() + " ";
    }

    private static int heightToIndex(double height) {
        return (int) height / 100;
    }


}
