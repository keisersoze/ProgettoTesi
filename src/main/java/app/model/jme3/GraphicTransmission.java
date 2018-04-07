package app.model.jme3;

import app.model.Sensor;
import app.model.Transmission;

public interface GraphicTransmission extends Transmission {

    boolean isTerminated();

    public GraphicSensor getGraphicSender();

    public GraphicSensor getGraphicReceiver();
}
