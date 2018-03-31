package app.model.impl;

import app.model.Frame;
import app.model.Sensor;
import app.model.Trasmission;

public class BaseFrame implements Frame{
    private double size;
    private Sensor sender;
    private Trasmission currentTransmission;
    private Sensor currentOwner;

    public BaseFrame(double size, Sensor sender,Sensor s) {
        this.size = size;
        this.sender = sender;
        this.currentOwner = s;
    }


    @Override
    public double getSize() {
        return 0;
    }

    @Override
    public Trasmission getCurrentTransmission() {
        return currentTransmission;
    }

    public Sensor getSender() {
        return sender;
    }

    public Sensor getCurrentOwner() {
        return currentOwner;
    }

    public void setCurrentTransmission(Trasmission currentTransmission) {
        this.currentTransmission = currentTransmission;
    }

    public void setCurrentOwner(Sensor currentOwner) {
        this.currentOwner = currentOwner;
    }
}
