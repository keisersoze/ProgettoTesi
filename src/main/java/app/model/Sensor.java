package app.model;

import com.jme3.math.Vector3f;

import java.util.List;

public interface Sensor {

    float getX();

    float getY();

    float getZ();

    List<Sensor> getNeighbors();

    double getEuclideanDistance(Sensor s);

    boolean isSink();


}
