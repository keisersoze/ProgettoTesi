package app.model.h20;

import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;

public class BaseTransmission extends AbstractTransmission {

    public BaseTransmission(Sensor sender, Sensor receiver , Frame frame) {
        super(sender, receiver, frame);
    }

}
