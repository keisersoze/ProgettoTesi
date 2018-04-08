package app.model.h20;

import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractFrame implements Frame{
    private double size;
    private boolean arrived;
    private Sensor sender;
    private Transmission currentTransmission;
    private Sensor currentOwner;
    private List<Transmission> transmissions; // Backtrace dei possessori del frame

    public AbstractFrame(double size, Sensor sender, Sensor currentOwner) {
        this.size = size;
        arrived = false;
        this.sender = sender;
        this.currentOwner = currentOwner;
        transmissions = new LinkedList<>();
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

    @Override
    public Transmission getCurrentTransmission() {
        return currentTransmission;
    }

    public void setCurrentTransmission(Transmission currentTransmission) {
        this.currentTransmission = currentTransmission;
        transmissions.add(currentTransmission);
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

    public List<Transmission> getTransmissionHistory() {
        return transmissions;
    }
}
