package app.model.impl;

import app.model.Transmission;

public class BaseTransmission implements Transmission {
    private BaseSensor sender;
    private BaseSensor receiver;
    private double snr;

    public BaseTransmission(BaseSensor sender, BaseSensor receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }


    @Override
    public void updateSnr(double x) {

    }
}
