package app.model;

import java.util.List;

public interface Sensor {

    double[] getPosVector();

    double getX();

    double getY();

    double getZ();

    List<Sensor> getNeighbors();

    double getEuclideanDistance(Sensor s);

}
