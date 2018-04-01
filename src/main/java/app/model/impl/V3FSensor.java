package app.model.impl;

import app.model.Sensor;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

import java.util.List;
import java.util.concurrent.Callable;

import static app.H2OSim.d;

public class V3FSensor implements Sensor {
    private static final int N_DIMENSIONS = 3;

    private Geometry sensor;

    public V3FSensor(float x, float y, float z) {
        Vector3f position = new Vector3f(x, y, z);

        sensor = d.sphereWithTexture(
                30,
                30,
                0.5f,
                position,
                null,
                new ColorRGBA(0f / 255f, 96f / 255f, 255f / 255f, 1f)
        );
    }

    @Override
    public float getX() {
        return sensor.getLocalTranslation().getX();
    }

    @Override
    public void setX(float x) {
        setPosition(x, sensor.getLocalTranslation().y, sensor.getLocalTranslation().z);
    }

    @Override
    public float getY() {
        return sensor.getLocalTranslation().getY();
    }

    @Override
    public void setY(float y) {
        setPosition(sensor.getLocalTranslation().x, y, sensor.getLocalTranslation().z);
    }

    @Override
    public float getZ() {
        return sensor.getLocalTranslation().getZ();
    }

    @Override
    public void setZ(float z) {
        setPosition(sensor.getLocalTranslation().x, sensor.getLocalTranslation().y, z);
    }

    @Override
    public void setPosition(float x, float y, float z) {
        d.enqueue((Callable<Spatial>) () -> {
            sensor.setLocalTranslation(new Vector3f(x, y, z));
            return sensor;
        });
    }

    public void setPositionVector3f(Vector3f position) {
        setPosition(position.x, position.y, position.z);
    }

    public Vector3f getPosition() {
        return sensor.getLocalTranslation();
    }

    @Override
    public List<Sensor> getNeighbors() {
        return null;
    }

    @Override
    public double getEuclideanDistance(Sensor s) {
        Vector3f vs2 = new Vector3f(s.getX(), s.getY(), s.getZ());
        return sensor.getLocalTranslation().distance(vs2);
    }

    public void offsetPosition(float x, float y, float z) {
        setPositionVector3f(sensor.getLocalTranslation().add(x, y, z));
    }

    public Geometry getGeometry() {
        return sensor;
    }

    @Override
    public boolean isSink() {
        return false;
    }


}
