package app.model.h20;

import app.model.Frame;
import app.model.Sensor;

public abstract class AbstractFrame implements Frame {
    private double size;
    private boolean arrived;
    private Sensor sender;
    private Sensor currentOwner;


    public AbstractFrame(double size, Sensor sender, Sensor currentOwner) {
        this.size = size;
        arrived = false;
        this.sender = sender;
        this.currentOwner = currentOwner;
    }

    @Override
    public boolean isArrived() {
        return arrived;
    }

    @Override
    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }

    @Override
    public double getSize() {
        return size;
    }

    public Sensor getSender() {
        return sender;
    }

    public Sensor getCurrentOwner() {
        return currentOwner;
    }

    public void setCurrentOwner(Sensor currentOwner) {
        this.currentOwner = currentOwner;
    }
}
