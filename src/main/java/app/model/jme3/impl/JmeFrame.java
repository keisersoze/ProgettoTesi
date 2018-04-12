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
    private List<GraphicTransmission> transmissions; // Backtrace dei possessori del frame
    private GraphicTransmission currentTransmission;


    public JmeFrame (double size, Sensor sender, Sensor currentOwner) {
        super(size, sender, currentOwner);
        transmissions = new LinkedList<>();
    }

    public JmeFrame (double size, GraphicSensor sender, GraphicSensor currentOwner) {
        super(size, sender, currentOwner);
        transmissions = new LinkedList<>();
    }

    @Override
    public GraphicTransmission getCurrentTransmission () {
        return currentTransmission;
    }

    @Override
    public void setCurrentTransmission (Transmission currentTransmission) {
        this.currentTransmission = (GraphicTransmission) currentTransmission;
        transmissions.add(this.currentTransmission);
    }

    @Override
    public GraphicSensor getSender () {
        return (GraphicSensor) super.getSender();
    }

    @Override
    public GraphicSensor getCurrentOwner () {
        return (GraphicSensor) super.getCurrentOwner();
    }

    @Override
    public List<GraphicTransmission> getTransmissionHistory () {
        return transmissions;
    }
}
