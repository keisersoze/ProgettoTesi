package app.factory;

import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;

import java.util.List;

public interface ModelFactory {

    Sensor getSensor (float x, float y, float z);

    Transmission getTransmission (Sensor sender, Sensor receiver, Frame frame, int hop);

    Frame getFrame (double size, Sensor owner, double arrivalTime);

    List<Sensor> deploySensors (String deploymentType);
}
