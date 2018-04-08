package app.model.h20;

import app.model.Sensor;
import com.jme3.math.Vector3f;

import java.util.List;

public class BaseSensor extends AbstractSensor implements Sensor<Sensor>{

    public BaseSensor(float x, float y, float z) {
        super(x, y, z);
    }

    @Override
    public List<Sensor> getNeighbors() {
        return null;
    }

    @Override
    public double getEuclideanDistance(Sensor s) {
        return position.distance(new Vector3f(s.getX(),s.getY(),s.getZ()));
    }
}
