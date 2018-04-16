package app.model.h20;

import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;

public abstract class AbstractTransmission implements Transmission {
    private Sensor sender;
    private Sensor receiver;
    private double snr;
    private boolean isSuccessfull;
    private Frame frame;
    private int hop;


    public AbstractTransmission(Sensor sender, Sensor receiver, Frame frame, int hop) {
        this.sender = sender;
        this.receiver = receiver;
        isSuccessfull = true;
        this.frame = frame;
        this.hop = hop;
    }

    @Override
    public void updateSnr(double x) {

    }

    @Override
    public boolean isSuccessfull() {
        return isSuccessfull;
    }

    public void setSuccessfull(boolean stato) {
        isSuccessfull = stato;
    }

    public Sensor getSender() {
        return sender;
    }

    public Sensor getReceiver() {
        return receiver;
    }

    public double getSnr() {
        return snr;
    }

    public Frame getFrame() {
        return frame;
    }

    @Override
    public int getHop () {
        return hop;
    }
}
