package app.model;

import java.util.List;

public interface Frame {

    int getSerial ();

    double getSize ();

    Sensor getOwner ();

    List<Transmission> getTransmissionHistory ();

    double getArrivalTime();
}
