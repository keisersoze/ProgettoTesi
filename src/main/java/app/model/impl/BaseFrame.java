package app.model.impl;

import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;

import java.util.LinkedList;
import java.util.List;

public class BaseFrame extends AbstractFrame implements Frame {

    private List<BaseTransmission> transmissions; // Backtrace dei possessori del frame
    private BaseTransmission currentTransmission;

    public BaseFrame(double size, Sensor sender, Sensor currentOwner) {
        super(size, sender, currentOwner);
        transmissions = new LinkedList<>();
    }

    @Override
    public Transmission getCurrentTransmission() {
        return currentTransmission;
    }

    @Override
    public void setCurrentTransmission(Transmission currentTransmission) {
        this.currentTransmission = (BaseTransmission) currentTransmission;

        transmissions.add(this.currentTransmission);
    }

    @Override
    public List<BaseTransmission> getTransmissionHistory() {
        return transmissions;
    }
}
