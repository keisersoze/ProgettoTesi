package app.model.h20;

import app.model.Frame;
import app.model.Sensor;

public abstract class AbstractFrame implements Frame {
    private double size;
    private Sensor owner;

    public AbstractFrame(double size, Sensor owner) {
        this.size = size;
        this.owner = owner;
    }


    @Override
    public double getSize() {
        return size;
    }


}
