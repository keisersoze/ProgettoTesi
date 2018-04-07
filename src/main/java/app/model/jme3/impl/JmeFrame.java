package app.model.jme3.impl;

import app.model.Sensor;
import app.model.Transmission;
import app.model.impl.AbstractFrame;
import app.model.jme3.GraphicFrame;
import app.model.jme3.GraphicSensor;
import app.model.jme3.GraphicTransmission;

import java.util.LinkedList;
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
