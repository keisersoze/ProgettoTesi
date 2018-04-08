package app.model.impl;

import app.model.Sensor;
import app.model.Transmission;

public class BaseTransmission extends  AbstractTransmission {

    public BaseTransmission(Sensor sender, Sensor receiver) {
       super(sender,receiver);
    }

}
