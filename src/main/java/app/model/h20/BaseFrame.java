package app.model.h20;

import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;

import java.util.LinkedList;
import java.util.List;

public class BaseFrame extends AbstractFrame  implements Frame<Sensor,Transmission>{
    private Sensor sender;
    private Transmission currentTransmission;
    private Sensor currentOwner;
    private List<Transmission> transmissions; // Backtrace dei possessori del frame


    public BaseFrame(double size, Sensor sender, Sensor currentOwner) {
        super(size);
        this.sender = sender;
        this.currentOwner = currentOwner;
        transmissions = new LinkedList<>();
    }

    @Override
    public Transmission getCurrentTransmission() {
        return currentTransmission;
    }

    @Override
    public void setCurrentTransmission(Transmission currentTransmission) {
        this.currentTransmission = currentTransmission;
        if (currentTransmission != null)
            transmissions.add(currentTransmission);
    }

    @Override
    public Sensor getSender() {
        return sender;
    }


    @Override
    public Sensor getCurrentOwner() {
        return currentOwner;
    }

    @Override
    public void setCurrentOwner(Sensor currentOwner) {
        this.currentOwner = currentOwner;
    }

    @Override
    public List<Transmission> getTransmissionHistory() {
        return transmissions;
    }

}
