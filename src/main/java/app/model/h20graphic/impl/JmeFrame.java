package app.model.h20graphic.impl;

import app.model.Sensor;
import app.model.h20.AbstractFrame;
import app.model.h20graphic.GraphicFrame;
import app.model.h20graphic.GraphicSensor;
import app.model.h20graphic.GraphicTransmission;

import java.util.List;

public class JmeFrame extends AbstractFrame implements GraphicFrame {

    public JmeFrame(double size, Sensor sender, Sensor currentOwner) {
        super(size, sender, currentOwner);
    }

    public JmeFrame(double size, GraphicSensor sender, GraphicSensor currentOwner) {
        super(size, sender, currentOwner);
    }


    @Override
    public GraphicTransmission getCurrentGraphicTransmission() throws ClassCastException {
        return (GraphicTransmission)getCurrentTransmission();
    }

    @Override
    public GraphicSensor getGraphicSender() throws ClassCastException {
        return (GraphicSensor) getSender();
    }

    @Override
    public GraphicSensor getCurrentGraphicOwner() throws ClassCastException {
        return (GraphicSensor) getCurrentOwner();
    }

    @Override
    public List<GraphicTransmission> getGraphicTransmissionHistory() throws ClassCastException {
        return (List<GraphicTransmission>) (List<?>) getGraphicTransmissionHistory();
    }
}
