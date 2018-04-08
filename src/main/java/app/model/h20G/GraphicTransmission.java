package app.model.h20G;

import app.model.Transmission;

public interface GraphicTransmission extends Transmission {

    boolean isTerminated();

    public GraphicSensor getGraphicSender();

    public GraphicSensor getGraphicReceiver();
}
