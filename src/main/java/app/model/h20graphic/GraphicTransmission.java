package app.model.h20graphic;

import app.model.Transmission;

public interface GraphicTransmission extends Transmission {

    boolean isTerminated();

    public GraphicSensor getGraphicSender();

    public GraphicSensor getGraphicReceiver();
}
