package app.model.impl;

import app.Canvas;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class V3FSensor extends AbstractSensor {
    private Geometry geometry;
    private Canvas canvas;

    public V3FSensor(float x, float y, float z, Canvas canvas) {
        super(x, y, z);
        Vector3f position = new Vector3f(x, y, z);
        this.canvas = canvas;
        try {
            geometry = canvas.enqueue(() -> canvas.sphereWithTexture(
                    30,
                    30,
                    0.5f,
                    position,
                    null,
                    new ColorRGBA(0f / 255f, 96f / 255f, 255f / 255f, 1f))
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Geometry getGeometry() {
        return geometry;
    }

    @Override
    public void updatePosition() {
        try {
            canvas.enqueue((Callable<Spatial>) () -> {
                geometry.setLocalTranslation(new Vector3f(getPosition()));
                return geometry;
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setSink(boolean x) {
        super.setSink(x);
        geometry.getMaterial().setColor("Color", ColorRGBA.Red);
    }
}
