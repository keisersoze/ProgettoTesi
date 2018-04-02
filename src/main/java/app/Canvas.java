package app;

import app.core.scheduler.Scheduler;
import app.model.Sensor;
import app.stats.Collector;
import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public class Canvas extends SimpleApplication  {


    private Vector3f campo;
    private boolean charged = false;
    private HashMap<Pair, Geometry> lines = new HashMap<>();

    public void simpleInitApp() {
        campo = new Vector3f(100, 30, 100);
        DirectionalLight dl = new DirectionalLight();
        dl.setColor(ColorRGBA.White);
        dl.setDirection(new Vector3f(-.5f, -.5f, -.5f).normalizeLocal());
        rootNode.addLight(dl);

        Node pivot = new Node("pivot");
        rootNode.attachChild(pivot); // put this node in the scene

        Vector3f cam_position = new Vector3f(0, 50, 0);
        cam.setLocation(cam_position);
        flyCam.setMoveSpeed(10);

        grid(true);
        charged = true;
    }

    private float random(float min, float max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    public Geometry sphereWithTexture(int zSamples, int radialSamples, float radius, Vector3f posizione, String texturePath, ColorRGBA color) {

        Sphere sphere = new Sphere(zSamples, radialSamples, radius);
        Geometry sphere_geometry = new Geometry("Sphere", sphere);

        sphere_geometry.setLocalTranslation(posizione);

        Material sphere_material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

        if (texturePath != null) {
            Texture sphere_texture = assetManager.loadTexture(texturePath);
            sphere_material.setTexture("ColorMap", sphere_texture);
        } else {
            sphere_material.setColor("Color", color);
        }

        sphere_geometry.setMaterial(sphere_material);
        sphere_geometry.updateModelBound();
        rootNode.attachChild(sphere_geometry);

        return sphere_geometry;
    }

    public Geometry linkBetweenGeometries(Geometry g1, Geometry g2, ColorRGBA color) {
        Vector3f position_1 = g1.getLocalTranslation();
        Vector3f position_2 = g2.getLocalTranslation();
        Pair<Geometry, Geometry> pair = new Pair<>(g1, g2);


        if (!lines.containsKey(pair)) {
            Mesh lineMesh = new Mesh();
            lineMesh.setMode(Mesh.Mode.Lines);
            lineMesh.setBuffer(VertexBuffer.Type.Position, 3, new float[]{position_1.x, position_1.y, position_1.z, position_2.x, position_2.y, position_2.z});
            lineMesh.setBuffer(VertexBuffer.Type.Index, 2, new short[]{0, 1});

            Geometry lineGeometry = lineGeometry = new Geometry("line", lineMesh);
            Material lineMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

            lineMaterial.setColor("Color", color);
            lineMaterial.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);

            lineGeometry.setMaterial(lineMaterial);
            lineGeometry.updateModelBound();
            lineGeometry.setQueueBucket(RenderQueue.Bucket.Translucent);
            rootNode.attachChild(lineGeometry);
            lines.put(pair, lineGeometry);
        } else {
            lines.get(pair).getMaterial().setColor("Color", color);
            lines.get(pair).getMesh().setBuffer(VertexBuffer.Type.Position, 3, new float[]{position_1.x, position_1.y, position_1.z, position_2.x, position_2.y, position_2.z});
        }

        return lines.get(pair);
    }

    private void grid(boolean simple) {
        for (float i = -campo.x; i <= campo.x; i += campo.x / 10) {
            for (float j = -campo.y; j <= campo.y; j += campo.y / 10) {
                rootNode.attachChild(line(
                        new Vector3f(i, j, -campo.z),
                        new Vector3f(i, -j, -campo.z),
                        new ColorRGBA(229, 239, 255, 0.001f))
                );
                if (!simple) {
                    rootNode.attachChild(line(
                            new Vector3f(-i, j, -campo.z),
                            new Vector3f(i, j, -campo.z),
                            new ColorRGBA(229, 239, 255, 0.001f))
                    );
                }

            }
        }
        for (float i = -campo.y; i <= campo.y; i += campo.y / 10) {
            for (float j = -campo.z; j <= campo.z; j += campo.z / 10) {
                rootNode.attachChild(line(
                        new Vector3f(campo.x, i, j),
                        new Vector3f(campo.x, i, -j),
                        new ColorRGBA(229, 239, 255, 0.001f))
                );
                if (!simple) {
                    rootNode.attachChild(line(
                            new Vector3f(campo.x, -i, j),
                            new Vector3f(campo.x, i, j),
                            new ColorRGBA(229, 239, 255, 0.001f))
                    );
                }
            }
        }
        for (float i = -campo.x; i <= campo.x; i += campo.x / 10) {
            for (float j = -campo.z; j <= campo.z; j += campo.z / 10) {
                rootNode.attachChild(line(
                        new Vector3f(i, -campo.y, j),
                        new Vector3f(-i, -campo.y, j),
                        new ColorRGBA(229, 239, 255, 0.001f))
                );
                if (!simple) {
                    rootNode.attachChild(line(
                            new Vector3f(i, -campo.y, -j),
                            new Vector3f(i, -campo.y, j),
                            new ColorRGBA(229, 239, 255, 0.001f))
                    );
                }
            }
        }

    }

    private Geometry line(Vector3f inizio, Vector3f fine, ColorRGBA colore) {
        Mesh lineMesh = new Mesh();
        lineMesh.setMode(Mesh.Mode.Lines);
        lineMesh.setBuffer(VertexBuffer.Type.Position, 3, new float[]{inizio.x, inizio.y, inizio.z, fine.x, fine.y, fine.z});
        lineMesh.setBuffer(VertexBuffer.Type.Index, 2, new short[]{0, 1});

        Geometry lineGeometry = new Geometry("line", lineMesh);
        Material lineMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

        lineMaterial.setColor("Color", colore);
        lineGeometry.setMaterial(lineMaterial);
        lineGeometry.updateModelBound();
        return lineGeometry;
    }

    public boolean isCharged() {
        return charged;
    }

}
