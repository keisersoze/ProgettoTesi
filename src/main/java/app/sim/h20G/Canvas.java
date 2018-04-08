package app.sim.h20G;

import app.model.Frame;
import app.model.Transmission;
import app.model.h20G.GraphicFrame;
import app.model.h20G.GraphicSensor;
import app.model.h20G.GraphicTransmission;
import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.*;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;

import java.util.HashMap;
import java.util.List;

public class Canvas extends SimpleApplication {


    private Vector3f campo;
    private boolean charged = false;
    private HashMap<Transmission, Geometry> lines = new HashMap<>();

    public void simpleInitApp() {
        campo = new Vector3f(100, 30, 100);
        viewPort.setBackgroundColor(new ColorRGBA(1f/255f * 60f, 1f/255f * 102f, 1f/255f * 140f, 1f));
        DirectionalLight dl = new DirectionalLight();
        dl.setColor(ColorRGBA.White);
        dl.setDirection(new Vector3f(-.5f, -.5f, -.5f).normalizeLocal());
        rootNode.addLight(dl);
        setDisplayFps(true);
        setDisplayStatView(true);
        Node pivot = new Node("pivot");
        rootNode.attachChild(pivot); // put this node in the scene

        Spatial teapot = assetManager.loadModel("Models/HarborBuoy.obj");
        teapot.scale(5f);
        rootNode.attachChild(teapot);

        Vector3f cam_position = new Vector3f(0, 30, 20);
        cam.setLocation(cam_position);
        flyCam.setMoveSpeed(30);

        grid(true);
        charged = true;
    }

    public float random(float min, float max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    public Geometry updatePositions(List<GraphicSensor> sensorList) {
        for (GraphicSensor sensor : sensorList) {
            sensor.getGeometry().setLocalTranslation(sensor.getPosition());
        }
        return null;
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

    public Geometry deleteLinkTransmission(GraphicFrame frame) {
        for (GraphicTransmission transmission : frame.getTransmissionHistory()) {
            if (transmission != null && lines.containsKey(transmission)) {
                lines.get(transmission).removeFromParent();
                lines.remove(transmission);
            }
        }
        return null;
    }

    public Geometry linkTransmission(GraphicTransmission transmission, ColorRGBA color) {
        Vector3f position_1 = transmission.getSender().getGeometry().getLocalTranslation();
        Vector3f position_2 = transmission.getReceiver().getGeometry().getLocalTranslation();

        if (!lines.containsKey(transmission)) {

            Mesh lineMesh = new Mesh();
            lineMesh.setMode(Mesh.Mode.Lines);
            lineMesh.setBuffer(VertexBuffer.Type.Position, 3, new float[]{position_1.x, position_1.y, position_1.z, position_2.x, position_2.y, position_2.z});
            lineMesh.setBuffer(VertexBuffer.Type.Index, 2, new short[]{0, 1});

            Geometry geo = new Geometry("link", lineMesh); // using the custom mesh
            Material lineMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            lineMaterial.setBoolean("VertexColor", true);
            int colorIndex = 0;

            float[] colorArray = new float[2 * 4];

            for (int i = 0; i < 2; i++) {
                // Red value (is increased by .2 on each next vertex here)
                colorArray[colorIndex++] = 0.0f;
                // Green value (is reduced by .2 on each next vertex)
                colorArray[colorIndex++] = 0.0f + (1.0f * i);
                // Blue value (remains the same in our case)
                colorArray[colorIndex++] = 1.0f + (0.0f - i);
                // Alpha value (no transparency set here)
                colorArray[colorIndex++] = 1.0f;
            }
            lineMesh.setBuffer(VertexBuffer.Type.Color, 4, colorArray);
            lineMaterial.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            geo.setMaterial(lineMaterial);

            /*Geometry lineGeometry = new Geometry("link", lineMesh);
            Material lineMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

            lineMaterial.setColor("Color", color);
            lineMaterial.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);

            lineGeometry.setMaterial(lineMaterial);*/
            geo.updateModelBound();
            geo.setQueueBucket(RenderQueue.Bucket.Translucent);
            rootNode.attachChild(geo);
            lines.put(transmission, geo);

        } else {
            lines.get(transmission).getMaterial().setColor("Color", color);
            lines.get(transmission).getMesh().setBuffer(VertexBuffer.Type.Position, 3, new float[]{position_1.x, position_1.y, position_1.z, position_2.x, position_2.y, position_2.z});
        }

        return lines.get(transmission);
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

    public Geometry fadeTransmission(GraphicFrame frame) {
        float step = 1.0f / frame.getTransmissionHistory().size();
        float alpha = 1.0f;
        System.out.println(step);
        for (GraphicTransmission t : frame.getTransmissionHistory()) {
            if(t != null) {
                int colorIndex = 0;
                alpha -= step;

                float[] colorArray = new float[2 * 4];

                for (int i = 0; i < 2; i++) {
                    // Red value (is increased by .2 on each next vertex here)
                    colorArray[colorIndex++] = 0.0f;
                    // Green value (is reduced by .2 on each next vertex)
                    colorArray[colorIndex++] = 0.0f + (1.0f * i);
                    // Blue value (remains the same in our case)
                    colorArray[colorIndex++] = 1.0f + (0.0f - i);
                    // Alpha value (no transparency set here)
                    colorArray[colorIndex++] = alpha;
                }
                lines.get(t).getMesh().setBuffer(VertexBuffer.Type.Color, 4, colorArray);
            }
        }
        return null;
    }

    private Vector3f pointBetween(Vector3f inizio, Vector3f fine, float percentuale) {
        Vector3f point = new Vector3f();
        point.setX(inizio.x + percentuale * (fine.x - inizio.x));
        point.setY(inizio.y + percentuale * (fine.y - inizio.y));
        point.setZ(inizio.z + percentuale * (fine.z - inizio.z));
        return point;
    }

    public boolean isCharged() {
        return charged;
    }

}
