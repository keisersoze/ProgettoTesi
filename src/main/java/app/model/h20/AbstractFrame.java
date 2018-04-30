package app.model.h20;

import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFrame implements Frame {
    private double arrivalTime;
    private double size;
    private Sensor owner;
    private List<Transmission> transmissions;

    AbstractFrame (double size, Sensor owner, double arrivalTime) {
        this.size = size;
        this.owner = owner;
        transmissions = new ArrayList<>();
        this.arrivalTime = arrivalTime;
    }


    @Override
    public double getSize () {
        return size;
    }

    @Override
    public Sensor getOwner () {
        return owner;
    }

    @Override
    public synchronized List<Transmission> getTransmissions() {
        return transmissions;
    }

    @Override
    public double getArrivalTime () {
        return arrivalTime;
    }
}
