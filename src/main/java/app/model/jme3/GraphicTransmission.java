package app.model.jme3;

import app.model.Transmission;

public interface GraphicTransmission extends Transmission {

    boolean isTerminated ();

    GraphicSensor getSender ();

    GraphicSensor getReceiver ();
}
