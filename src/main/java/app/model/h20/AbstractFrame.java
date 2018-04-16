package app.model.h20;

import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractFrame implements Frame {
    private double size;
    private Sensor owner;
    private List<Transmission> transmissions;

    public AbstractFrame (double size, Sensor owner) {
        this.size = size;
        this.owner = owner;
        transmissions = new CopyOnWriteArrayList<>();
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
    public synchronized List<Transmission> getTransmissionHistory () {
        return transmissions;
    }
}
