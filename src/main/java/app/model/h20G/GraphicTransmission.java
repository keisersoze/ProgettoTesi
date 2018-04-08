package app.model.h20G;

import app.model.Transmission;

public interface GraphicTransmission extends Transmission<GraphicSensor> {

    boolean isTerminated();

}
