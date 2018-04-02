package app.model.impl;

import app.model.Frame;
import app.model.Sensor;
import app.model.Trasmission;

import java.util.LinkedList;

public class BaseFrame implements Frame {
    private double size;
    private Sensor sender;
    private Trasmission currentTransmission;
    private Sensor currentOwner;
    private LinkedList<Trasmission> trasmissions;           // Backtrace dei possessori del frame

    public BaseFrame(double size, Sensor sender, Sensor s) {
        this.size = size;
        this.sender = sender;
        this.currentOwner = s;
        trasmissions = new LinkedList<>();
    }

    @Override
    public double getSize() {
        return 0;
    }

    @Override
    public Trasmission getCurrentTransmission() {
        return currentTransmission;
    }

    public void setCurrentTransmission(Trasmission currentTransmission) {
        this.currentTransmission = currentTransmission;
        trasmissions.addFirst(currentTransmission);
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

    public Trasmission getLastEndedTrasmission() {
        if (trasmissions.size() > 1) {
            return trasmissions.get(1);                 // Mentre il frame è in circolo nella rete
        }
        return null;
    }
}
