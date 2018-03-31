package app.model.impl;

import app.model.Sensor;
import com.jme3.math.Vector3f;
import org.apache.commons.math3.ml.distance.EuclideanDistance;

import java.util.List;

public class V3FSensor implements Sensor{
    private static final EuclideanDistance EUCLIDEAN_DISTANCE = new EuclideanDistance();
    private static final int N_DIMENSIONS =3;

    private Vector3f posVector;

    public V3FSensor(float x, float y, float z) {
        posVector = new Vector3f(x,y,z);
    }

    @Override
    public float getX() {
        return posVector.getX();
    }

    @Override
    public float getY() {
        return posVector.getY();
    }

    @Override
    public float getZ() {
        return posVector.getZ();
    }

    @Override
    public List<Sensor> getNeighbors() {
        return null;
    }

    @Override
    public double getEuclideanDistance(Sensor s) {
        Vector3f vs2= new Vector3f();
        vs2.add(s.getX(),s.getY(),s.getZ());
        return posVector.distance(vs2);
    }
}
