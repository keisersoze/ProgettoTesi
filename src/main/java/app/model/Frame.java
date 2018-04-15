package app.model;

import java.util.List;

public interface Frame {

    double getSize();

    Transmission getCurrentTransmission();

    void setCurrentTransmission(Transmission currentTransmission);

    Sensor getSender();

    Sensor getCurrentOwner();

    void setCurrentOwner(Sensor currentOwner);

    List<Transmission> getTransmissionHistory();

    boolean isArrived();

    void setArrived(boolean arrived);

}
