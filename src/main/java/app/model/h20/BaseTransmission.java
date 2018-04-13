package app.model.h20;

import app.model.Sensor;
import app.model.Transmission;

public class BaseTransmission extends AbstractTransmission {

    public BaseTransmission(Sensor sender, Sensor receiver) {
        super(sender, receiver);
    }

    public BaseTransmission(Transmission transmission) {
        super(transmission.getSender(), transmission.getReceiver());
    }
}
