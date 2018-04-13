package app.model.h20;

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
        if (this.currentTransmission != null) {
            transmissions.add(0, this.currentTransmission);
        }
    }

    @Override
    public List<BaseTransmission> getTransmissionHistory() {
        return transmissions;
    }
}
