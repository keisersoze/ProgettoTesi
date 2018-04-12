package app.factory;

import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;

public interface ModelFactory {

    Sensor getSensor (float x, float y, float z);

    Transmission getTransmission (Sensor sender, Sensor receiver);

    Frame getFrame (double size, Sensor sender, Sensor currentOwner);
}
