package app.model.h20G.impl;

import app.model.Sensor;
import app.model.Transmission;
import app.model.h20.AbstractFrame;
import app.model.h20G.GraphicFrame;
import app.model.h20G.GraphicSensor;
import app.model.h20G.GraphicTransmission;

import java.util.LinkedList;
import java.util.List;

public class JmeFrame extends AbstractFrame implements GraphicFrame {

    private GraphicSensor sender;
    private GraphicTransmission currentTransmission;
    private GraphicSensor currentOwner;
    private List<GraphicTransmission> transmissions;

    public JmeFrame(double size, GraphicSensor sender, GraphicSensor currentOwner) {
        super(size);
        this.sender = sender;
        this.currentOwner = currentOwner;
        transmissions = new LinkedList<>();
    }



    @Override
    public GraphicTransmission getCurrentTransmission() {
        return currentTransmission;
    }

    @Override
    public void setCurrentTransmission(GraphicTransmission currentTransmission) {
        this.currentTransmission = currentTransmission;
        if (currentTransmission != null)
            transmissions.add(currentTransmission);
    }

    @Override
    public GraphicSensor getSender() {
        return sender;
    }


    @Override
    public GraphicSensor getCurrentOwner() {
        return currentOwner;
    }

    @Override
    public void setCurrentOwner(GraphicSensor currentOwner) {
        this.currentOwner = currentOwner;
    }

    @Override
    public List<GraphicTransmission> getTransmissionHistory() {
        return transmissions;
    }
}
