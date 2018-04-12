package app;

import app.model.Frame;
import app.model.Sensor;
import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.*;
import com.jme3.scene.shape.Sphere;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.HillHeightMap;
import com.jme3.texture.Texture;

import java.util.*;

public class Canvas extends SimpleApplication {
    private Vector3f campo;
    private boolean charged = false;
    private HashMap<Frame, List<Geometry>> frameListGeometryHashMap = new HashMap<>();
    private HashMap<Sensor, Spatial> sensorSpatialHashMap = new HashMap<>();

    public void simpleInitApp () {
        campo = new Vector3f(200, 100, 200);

        viewPort.setBackgroundColor(new ColorRGBA(1f / 255f * 60f, 1f / 255f * 102f, 1f / 255f * 140f, 1f));

        DirectionalLight dl = new DirectionalLight();
        dl.setColor(ColorRGBA.White);
        dl.setDirection(new Vector3f(-.5f, -.5f, -.5f).normalizeLocal());
        rootNode.addLight(dl);

        Node pivot = new Node("pivot");
        rootNode.attachChild(pivot); // put this node in the scene

        Vector3f cam_position = new Vector3f(0, 30, 20);
        cam.setLocation(cam_position);
        flyCam.setMoveSpeed(100);

        grid(true);
        //generateTerrain();
        charged = true;
    }

    public float random (float min, float max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    public boolean updateSensorsPositions () {
        for (Map.Entry<Sensor, Spatial> entry : sensorSpatialHashMap.entrySet()) {
            entry.getValue().setLocalTranslation(entry.getKey().getPosition());     //TODO: Da testare se tine la referenza al sensor
        }
        return true;
    }

    public Spatial drawSensor (Sensor sensor) {
        if (!sensorSpatialHashMap.containsKey(sensor)) {
            if (!sensor.isSink()) {
                Sphere sphere = new Sphere(30, 30, 0.5f);
                Geometry sphere_geometry = new Geometry("Sphere", sphere);

                sphere_geometry.setLocalTranslation(sensor.getPosition());

                Material sphere_material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

                sphere_material.setColor("Color", ColorRGBA.Blue);

                sphere_geometry.setMaterial(sphere_material);
                sphere_geometry.updateModelBound();

                sensorSpatialHashMap.put(sensor, sphere_geometry);
                rootNode.attachChild(sphere_geometry);
            } else {
                Spatial sink = assetManager.loadModel("Models/HarborBuoy.obj");
                sink.scale(3f);
                sensorSpatialHashMap.put(sensor, sink);
                rootNode.attachChild(sink);
            }
        }
        return null;
    }

    public Spatial drawSensors (Collection<? extends Sensor> sensors) {
        for (Sensor sensor : sensors) {
            drawSensor(sensor);
        }
        return null;
    }

    public boolean deleteLinkTransmission (Frame frame) {
        List<Geometry> lines = frameListGeometryHashMap.get(frame);
        for (Geometry geometry : lines){
            geometry.removeFromParent();
        }
        frameListGeometryHashMap.remove(frame);
        return true;
    }

    public boolean linkTransmission (Frame frame, ColorRGBA color) {
        Vector3f position_1 = sensorSpatialHashMap.get(frame.getCurrentTransmission().getSender()).getLocalTranslation();
        Vector3f position_2 = sensorSpatialHashMap.get(frame.getCurrentTransmission().getReceiver()).getLocalTranslation();

        Mesh lineMesh = new Mesh();
        lineMesh.setMode(Mesh.Mode.Lines);
        lineMesh.setBuffer(VertexBuffer.Type.Position, 3, new float[]{position_1.x, position_1.y, position_1.z, position_2.x, position_2.y, position_2.z});
        lineMesh.setBuffer(VertexBuffer.Type.Index, 2, new short[]{0, 1});

        Geometry lineGeometry = new Geometry("link", lineMesh);
        Material lineMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

        lineMaterial.setColor("Color", color);
        //lineMaterial.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);

        lineGeometry.setMaterial(lineMaterial);
        lineGeometry.updateModelBound();
        //lineGeometry.setQueueBucket(RenderQueue.Bucket.Translucent);
        rootNode.attachChild(lineGeometry);

        if (!frameListGeometryHashMap.containsKey(frame)) {
            List<Geometry> queue = new ArrayList<>();
            queue.add(0, lineGeometry);
            frameListGeometryHashMap.put(frame, queue);
        } else {
            frameListGeometryHashMap.get(frame).add(0, lineGeometry);
            List<Geometry> lines = frameListGeometryHashMap.get(frame);
            //Con questo le linee si dovrebbero vedere aggiornate tutte in un frame
            speed = 0.f;
            lines.get(0).getMaterial().setColor("Color", new ColorRGBA(255 / 255f, 0 / 255f, 0 / 255f, 1f));
            if (lines.size() > 1) {
                lines.get(1).getMaterial().setColor("Color", new ColorRGBA(0 / 255f, 255 / 255f, 0 / 255f, 1f));
            }
            if (lines.size() > 2) {
                lines.get(2).getMaterial().setColor("Color", new ColorRGBA(0 / 255f, 0 / 255f, 0 / 255f, 1f));
            }
            if (lines.size() > 3) {
                lines.get(3).getMaterial().setColor("Color", new ColorRGBA(244 / 255f, 255 / 255f, 104 / 255f, 1f));
            }
            if (lines.size() == 5){
                lines.remove(4).removeFromParent();
            }
            speed = 1.f;

            /*for (int i = 0; i < 4 && i < lines.size(); i++) {
                //lines.get(i).getMesh().setBuffer(VertexBuffer.Type.Position, 3, new float[]{position_1.x, position_1.y, position_1.z, position_2.x, position_2.y, position_2.z}); TODO: da vedere come cambiare
            }*/

        }

        return true;
    }

    private void grid (boolean simple) {
        for (float i = -campo.x; i <= campo.x; i += campo.x / 10) {
            for (float j = -campo.y; j <= campo.y; j += campo.y / 10) {
                rootNode.attachChild(line(new Vector3f(i, j, -campo.z), new Vector3f(i, -j, -campo.z), new ColorRGBA(229, 239, 255, 0.001f)));
                if (!simple) {
                    rootNode.attachChild(line(new Vector3f(-i, j, -campo.z), new Vector3f(i, j, -campo.z), new ColorRGBA(229, 239, 255, 0.001f)));
                }

            }
        }
        for (float i = -campo.y; i <= campo.y; i += campo.y / 10) {
            for (float j = -campo.z; j <= campo.z; j += campo.z / 10) {
                rootNode.attachChild(line(new Vector3f(campo.x, i, j), new Vector3f(campo.x, i, -j), new ColorRGBA(229, 239, 255, 0.001f)));
                if (!simple) {
                    rootNode.attachChild(line(new Vector3f(campo.x, -i, j), new Vector3f(campo.x, i, j), new ColorRGBA(229, 239, 255, 0.001f)));
                }
            }
        }
        for (float i = -campo.x; i <= campo.x; i += campo.x / 10) {
            for (float j = -campo.z; j <= campo.z; j += campo.z / 10) {
                rootNode.attachChild(line(new Vector3f(i, -campo.y, j), new Vector3f(-i, -campo.y, j), new ColorRGBA(229, 239, 255, 0.001f)));
                if (!simple) {
                    rootNode.attachChild(line(new Vector3f(i, -campo.y, -j), new Vector3f(i, -campo.y, j), new ColorRGBA(229, 239, 255, 0.001f)));
                }
            }
        }

    }

    private Geometry line (Vector3f inizio, Vector3f fine, ColorRGBA colore) {
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

    private Vector3f pointBetween (Vector3f inizio, Vector3f fine, float percentuale) {
        Vector3f point = new Vector3f();
        point.setX(inizio.x + percentuale * (fine.x - inizio.x));
        point.setY(inizio.y + percentuale * (fine.y - inizio.y));
        point.setZ(inizio.z + percentuale * (fine.z - inizio.z));
        return point;
    }

    private void generateTerrain () {

        /* 1. Create terrain material and load four textures into it. */
        Material mat_terrain = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");

        Texture floortexture = assetManager.loadTexture("Textures/sand.jpg");
        floortexture.setWrap(Texture.WrapMode.Repeat);

        /* 1.1) Add ALPHA map (for red-blue-green coded splat textures) */
        mat_terrain.setTexture("DiffuseMap", floortexture);
        /* 2. Create the height map */
        HillHeightMap heightmap = null;
        HillHeightMap.NORMALIZE_RANGE = 100; // optional
        try {
            heightmap = new HillHeightMap(513, 5000, 200, 300, (byte) 5); // byte 3 is a random seed
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        heightmap.smooth(500.f);
        heightmap.load();

        TerrainQuad terrain = new TerrainQuad("my terrain", 65, 513, heightmap.getHeightMap());

        /* 4. We give the terrain its material, position & scale it, and attach it. */
        terrain.setMaterial(mat_terrain);
        terrain.setLocalTranslation(0, -100, 0);
        terrain.setLocalScale(0.779f, 1f, 0.779f);
        rootNode.attachChild(terrain);

        /* 5. The LOD (level of detail) depends on were the camera is: */
        TerrainLodControl control = new TerrainLodControl(terrain, getCamera());
        terrain.addControl(control);
    }

    private double map (double value, double low1, double high1, double low2, double high2) {
        return low2 + (high2 - low2) * (value - low1) / (high1 - low1);
    }

    public boolean isCharged () {
        return charged;
    }

}
