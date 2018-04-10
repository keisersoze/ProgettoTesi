package app.model.jme3;

import app.model.Frame;
import java.util.List;

public interface GraphicFrame extends Frame {

    GraphicTransmission getCurrentTransmission();

    GraphicSensor getSender();

    GraphicSensor getCurrentOwner();

    List<GraphicTransmission> getTransmissionHistory();
}
