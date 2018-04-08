package app.model.jme3;

import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;

import java.util.List;

public interface GraphicFrame extends Frame {

    public GraphicTransmission getCurrentGraphicTransmission();

    public GraphicSensor getGraphicSender();

    public GraphicSensor getCurrentGraphicOwner();

    public List<GraphicTransmission> getGraphicTransmissionHistory();


}
