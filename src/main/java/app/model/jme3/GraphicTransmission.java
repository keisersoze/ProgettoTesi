package app.model.jme3;

import app.model.Transmission;
import com.jme3.scene.Geometry;

public interface GraphicTransmission extends Transmission {

    boolean isTerminated();

    GraphicSensor getSender();

    GraphicSensor getReceiver();
}
