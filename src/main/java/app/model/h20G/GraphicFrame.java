package app.model.h20G;

import app.model.Frame;

import java.util.List;

public interface GraphicFrame extends Frame {

    public GraphicTransmission getCurrentGraphicTransmission();

    public GraphicSensor getGraphicSender();

    public GraphicSensor getCurrentGraphicOwner();

    public List<GraphicTransmission> getGraphicTransmissionHistory();


}
