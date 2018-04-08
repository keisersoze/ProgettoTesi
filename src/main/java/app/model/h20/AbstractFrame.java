package app.model.h20;

import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractFrame{
    private double size;
    private boolean arrived;

    public AbstractFrame(double size) {
        this.size = size;
        arrived = false;
    }

    public boolean isArrived() {
        return arrived;
    }

    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }

    public double getSize() {
        return size;
    }

}
