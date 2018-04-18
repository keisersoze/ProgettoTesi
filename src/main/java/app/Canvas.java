package app;

import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;
import app.sim.h20.GraphicSim;
import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.*;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.debug.Grid;
import com.jme3.scene.shape.Sphere;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.HillHeightMap;
import com.jme3.texture.Texture;

import java.util.*;

public class Canvas extends SimpleApplication {
    private static Vector3f field;
    protected SimContext context;
    private boolean charged;
    private HashMap<Frame, HashMap<Transmission, Geometry>> frameListGeometryHashMap;
    private HashMap<Frame, ColorRGBA> colorRGBAHashMap;
    private HashMap<Sensor, Spatial> sensorSpatialHashMap;
    private BitmapText hudText;

    public Canvas (SimContext context) {
        this.context = context;
        frameListGeometryHashMap = new HashMap<>();
        sensorSpatialHashMap = new HashMap<>();
        colorRGBAHashMap = new HashMap<>();
        field = new Vector3f(200, 100, 200);
        charged = false;
    }

    private static Vector3f pointBetween (Vector3f inizio, Vector3f fine, float percentuale) {
        if (percentuale > 1.0f) percentuale = 1.0f;
        Vector3f point = new Vector3f();
        point.setX(inizio.x + percentuale * (fine.x - inizio.x));
        point.setY(inizio.y + percentuale * (fine.y - inizio.y));
        point.setZ(inizio.z + percentuale * (fine.z - inizio.z));
        return point;
    }

    public void simpleInitApp () {
        initLight();
        initCam();
        initHUD();
        initKeys();

        attachCoordinateAxes(Vector3f.ZERO);
        attachGrid(field.x, field.y, field.z, 10f, ColorRGBA.White);
        viewPort.setBackgroundColor(new ColorRGBA(1f / 255f * 60f, 1f / 255f * 102f, 1f / 255f * 140f, 1f));
        //generateTerrain();

        charged = true;
    }

    private void initLight () {
        DirectionalLight dl = new DirectionalLight();
        dl.setColor(ColorRGBA.White);
        dl.setDirection(new Vector3f(-.5f, -.5f, -.5f).normalizeLocal());
        rootNode.addLight(dl);
        Node pivot = new Node("pivot");
        rootNode.attachChild(pivot); // put this node in the scene
    }

    private void initCam () {
        Vector3f cam_position = new Vector3f(field.x, field.y / 2, field.z);
        cam.setLocation(cam_position);
        cam.lookAt(Vector3f.ZERO, Vector3f.ZERO);
        flyCam.setMoveSpeed(100);
    }

    private void initHUD () {
        BitmapFont font = assetManager.loadFont("Interface/Fonts/roboto.fnt");
        hudText = new BitmapText(font, false);
        hudText.setBox(new Rectangle(0, settings.getHeight() / 2, 300, 90));
        hudText.setSize(18);      // font size
        hudText.setColor(ColorRGBA.Black);                             // font color
        hudText.setLocalTranslation(0, 0, 0); // position
        guiNode.attachChild(hudText);
    }

    private void initKeys () {
        inputManager.addMapping("More", new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("Less", new KeyTrigger(KeyInput.KEY_K));

        inputManager.addListener(analogListener, "More", "Less");
    }


    private final AnalogListener analogListener = (name, value, tpf) -> {
        if (name.equals("More")) {
            GraphicSim.speed += 10;
        }
        if (name.equals("Less")) {
            GraphicSim.speed -= 10;
            if (GraphicSim.speed < 0)
                GraphicSim.speed = 0;
        }
    };

    public Spatial drawSensors (Collection<? extends Sensor> sensors) {
        for (Sensor sensor : sensors) {
            drawSensor(sensor);
        }
        return null;
    }

    private void drawSensor (Sensor sensor) {
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
    }

    private void attachGrid (float x, float y, float z, float lineDist, ColorRGBA color) {
        Geometry x_grid = gridGeometry(x, z, lineDist, color);
        Geometry y_grid = gridGeometry(y, z, lineDist, color);
        Geometry z_grid = gridGeometry(x, y, lineDist, color);

        Quaternion roll90 = new Quaternion();
        roll90.fromAngleAxis(FastMath.PI / 2, new Vector3f(0, 0, 1));
        z_grid.setLocalRotation(roll90);

        roll90.fromAngleAxis(-FastMath.PI / 2, new Vector3f(1, 0, 0));
        y_grid.setLocalRotation(roll90);

        rootNode.attachChild(z_grid);
        rootNode.attachChild(y_grid);
        rootNode.attachChild(x_grid);
    }

    private Geometry gridGeometry (float x, float y, float lineDist, ColorRGBA color) {
        int lineX = (int) (x / lineDist) + 1;
        int lineY = (int) (y / lineDist) + 1;
        Geometry g = new Geometry("wireframe grid", new Grid(lineX, lineY, lineDist));
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.getAdditionalRenderState().setWireframe(true);
        mat.setColor("Color", color);
        g.setMaterial(mat);
        return g;
    }

    private void attachCoordinateAxes (Vector3f pos) {
        Arrow arrow = new Arrow(Vector3f.UNIT_X);
        putShape(arrow, ColorRGBA.Red).setLocalTranslation(pos);

        arrow = new Arrow(Vector3f.UNIT_Y);
        putShape(arrow, ColorRGBA.Green).setLocalTranslation(pos);

        arrow = new Arrow(Vector3f.UNIT_Z);
        putShape(arrow, ColorRGBA.Blue).setLocalTranslation(pos);
    }

    private Geometry putShape (Mesh shape, ColorRGBA color) {
        Geometry g = new Geometry("coordinate axis", shape);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.getAdditionalRenderState().setWireframe(true);
        mat.getAdditionalRenderState().setLineWidth(4);
        mat.setColor("Color", color);
        g.setMaterial(mat);
        g.setLocalScale(10f);
        rootNode.attachChild(g);
        return g;
    }

    private void generateTerrain () {

        //* 1. Create terrain material and load four textures into it. *//*
        Material mat_terrain = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");

        Texture floortexture = assetManager.loadTexture("Textures/sand.jpg");
        floortexture.setWrap(Texture.WrapMode.Repeat);

        //* 1.1) Add ALPHA map (for red-blue-green coded splat textures) *//*
        mat_terrain.setTexture("DiffuseMap", floortexture);
        //* 2. Create the height map *//*
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

        //* 4. We give the terrain its material, position & scale it, and attach it. *//*
        terrain.setMaterial(mat_terrain);
        terrain.setLocalTranslation(0, -100, 0);
        terrain.setLocalScale(0.779f, 1f, 0.779f);
        rootNode.attachChild(terrain);

        //* 5. The LOD (level of detail) depends on were the camera is: *//*
        TerrainLodControl control = new TerrainLodControl(terrain, getCamera());
        terrain.addControl(control);
    }

    public boolean isCharged () {
        return charged;
    }

    public boolean newFrame (Frame frame) {
        if (!frameListGeometryHashMap.containsKey(frame)) {
            frameListGeometryHashMap.put(frame, new HashMap<>());
        }
        return true;
    }

    public void newTransmission (Frame frame, Transmission transmission) {
        if (!frameListGeometryHashMap.get(frame).containsKey(transmission)) {

            Vector3f position_1 = sensorSpatialHashMap.get(transmission.getSender()).getLocalTranslation();
            //Vector3f position_2 = sensorSpatialHashMap.get(transmission.getReceiver()).getLocalTranslation();

            Mesh lineMesh = new Mesh();
            lineMesh.setMode(Mesh.Mode.Lines);
            lineMesh.setBuffer(VertexBuffer.Type.Position, 3, new float[]{position_1.x, position_1.y, position_1.z, position_1.x, position_1.y, position_1.z});
            lineMesh.setBuffer(VertexBuffer.Type.Index, 2, new short[]{0, 1});

            Geometry lineGeometry = new Geometry("link", lineMesh);
            Material lineMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

            if (!colorRGBAHashMap.containsKey(frame)) {
                ColorRGBA colorRGBA = ColorRGBA.randomColor();
                lineMaterial.setColor("Color", colorRGBA);
                colorRGBAHashMap.put(frame, colorRGBA);
            } else {
                lineMaterial.setColor("Color", colorRGBAHashMap.get(frame));
            }

            //lineMaterial.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);

            lineGeometry.setMaterial(lineMaterial);
            lineGeometry.updateModelBound();
            rootNode.attachChild(lineGeometry);

            frameListGeometryHashMap.get(frame).put(transmission, lineGeometry);
        }
    }

    public void simpleUpdate (float tpf) {
        hudText.setText("- Sim Time: " + context.getSimTime() + "\n- Frame in circolo: " + context.getFrames().size() + "\n- Numero di sensori:" + context.getSensors().size() +
                "\n Speed: " + GraphicSim.speed);
        updateSensors();
        updateLinks();
    }

    private void updateSensors () {
        for (Map.Entry<Sensor, Spatial> entry : sensorSpatialHashMap.entrySet()) {
            entry.getValue().setLocalTranslation(entry.getKey().getPosition());
            if (!entry.getKey().isSink()) {
                if (!entry.getKey().isTransmitting()) {
                    updateSensorColor(entry.getKey(), ColorRGBA.Blue);
                } else {
                    updateSensorColor(entry.getKey(), ColorRGBA.Red);
                }
            }

        }
    }

    private void updateSensorColor (Sensor sensor, ColorRGBA colorRGBA) {
        Geometry sender = (Geometry) sensorSpatialHashMap.get(sensor);

        sender.getMaterial().setColor("Color", colorRGBA);
        sender.updateModelBound();
    }

    private void updateLinks () {
        List<Map.Entry<Frame, Transmission>> toDelete = new ArrayList<>();

        for (Map.Entry<Frame, HashMap<Transmission, Geometry>> frameTransmissions : frameListGeometryHashMap.entrySet()) {
            for (Map.Entry<Transmission, Geometry> transmission : frameTransmissions.getValue().entrySet()) {
                if (!transmission.getKey().isArrived()) {
                    Sensor sender = transmission.getKey().getSender();
                    Sensor receiver = transmission.getKey().getReceiver();

                    double distance = H2OSim.SOUND_SPEED * (context.getSimTime() - transmission.getKey().getTime());
                    double total = sender.getEuclideanDistance(receiver);
                    Vector3f point = Canvas.pointBetween(sender.getPosition(), receiver.getPosition(), (float) (distance / total));

                    transmission.getValue().getMesh().setBuffer(VertexBuffer.Type.Position, 3, new float[]{sender.getX(), sender.getY(), sender.getZ(), point.x, point.y, point.z});
                    transmission.getValue().updateModelBound();
                } else {
                    toDelete.add(new AbstractMap.SimpleEntry<>(frameTransmissions.getKey(), transmission.getKey()));
                }
            }
        }

        for (Map.Entry<Frame, Transmission> entry : toDelete) {
            deleteTransmission(entry.getKey(), entry.getValue());
        }
    }

    private void deleteTransmission (Frame frame, Transmission transmission) {
        if (frameListGeometryHashMap.containsKey(frame) && frameListGeometryHashMap.get(frame).containsKey(transmission)) {
            frameListGeometryHashMap.get(frame).get(transmission).removeFromParent();
            frameListGeometryHashMap.get(frame).remove(transmission);
        }
    }

}
