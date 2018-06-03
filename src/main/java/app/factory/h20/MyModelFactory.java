package app.factory.h20;

import app.factory.DeploymentTypes;
import app.factory.ModelFactory;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.model.h20.BaseFrame;
import app.model.h20.BaseSensor;
import app.model.h20.BaseTransmission;
import app.sim.SimContext;
import app.utils.MyLib;

import java.util.ArrayList;
import java.util.List;

import static app.H20Sim.*;

public class MyModelFactory implements ModelFactory {
    private final SimContext context;

    public MyModelFactory (SimContext context) {
        this.context = context;
    }

    @Override
    public Sensor getSensor (float x, float y, float z) {
        return new BaseSensor(x, y, z);
    }

    @Override
    public Transmission getTransmission (Sensor sender, Sensor receiver, Frame frame, int hop) {
        return new BaseTransmission(sender, receiver, frame, hop);
    }

    @Override
    public Frame getFrame (double size, Sensor owner, double arrivalTime) {
        return new BaseFrame(size, owner, arrivalTime);
    }

    @Override
    public List<Sensor> deploySensors (String deploymentType) {
        if (deploymentType.equalsIgnoreCase(DeploymentTypes.BaseDeployment)) {
            return baseDeployment();
        } else if (deploymentType.equalsIgnoreCase(DeploymentTypes.LayerDeployment)) {
            return layerDeployment();
        } else if (deploymentType.equalsIgnoreCase(DeploymentTypes.LayerInvProportionalDeployment)) {
            return invProportionalLayerDeployment();
        }
        if (deploymentType.equalsIgnoreCase(DeploymentTypes.OneSDeployment)) {
            return oneSDeployment();
        }
        if (deploymentType.equalsIgnoreCase(DeploymentTypes.LayerProportionalDeployment)) {
            return proportionalLayerDeployment();
        } else {
            return null;
        }

    }


    private List<Sensor> oneSDeployment () {
        List<Sensor> sensors = new ArrayList<>();
        sensors.add(getSensor(0, 0, 0));
        return sensors;
    }

    private List<Sensor> invProportionalLayerDeployment () {

        int p1 = (int) (context.getSensorsNumber() / 2);
        int p2 = (int) (context.getSensorsNumber() / 3);
        int p3 = (int) context.getSensorsNumber() - p1 - p2;
        List<Sensor> sensors = threeLayerDeployment(p1, p2, p3);
        deploySink(sensors);

        return sensors;
    }

    private List<Sensor> proportionalLayerDeployment () {

        int p1 = (int) (context.getSensorsNumber() / 2);
        int p2 = (int) (context.getSensorsNumber() / 3);
        int p3 = (int) context.getSensorsNumber() - p1 - p2;
        List<Sensor> sensors = threeLayerDeployment(p3, p2, p1);
        deploySink(sensors);

        return sensors;
    }

    private List<Sensor> layerDeployment () {

        int p1 = (int) (context.getSensorsNumber() / 3);
        int p2 = (int) context.getSensorsNumber() - 2 * p1;
        List<Sensor> sensors = threeLayerDeployment(p1, p1, p2);
        deploySink(sensors);

        return sensors;
    }

    private List<Sensor> threeLayerDeployment (int p1, int p2, int p3) {
        List<Sensor> sensors = new ArrayList<>();

        for (int i = 0; i < p1; i++) {
            Sensor s1 = getSensor(MyLib.random(0, FIELD_X), MyLib.random(0, 100), MyLib.random(0, FIELD_Z));
            sensors.add(s1);
        }

        for (int i = 0; i < p2; i++) {
            Sensor s1 = getSensor(MyLib.random(0, FIELD_X), MyLib.random(1950, 2050), MyLib.random(0, FIELD_Z));
            sensors.add(s1);
        }

        for (int i = 0; i < p3; i++) {
            Sensor s1 = getSensor(MyLib.random(0, FIELD_X), MyLib.random(1050, 950), MyLib.random(0, FIELD_Z));
            sensors.add(s1);
        }

        return sensors;
    }

    /*private void deploySink (List<Sensor> sensors) {
        int startX = (int) FIELD_X / 10;
        int endX = (int) (FIELD_X - FIELD_X / 10);
        int startZ = (int) FIELD_Z / 10;
        int endZ = (int) (FIELD_Z - FIELD_Z / 10);
        int offsetX = (int) FIELD_X / 4;
        int offsetZ = (int) FIELD_Z / 4;

        for (int i = startX; i <= endX; i += offsetX) {
            for (int j = startZ; j <= endZ; j += offsetZ) {
                Sensor sensor = getSensor(i, FIELD_Y, j);
                sensor.setSink(true);
                sensors.add(sensor);
            }
        }
    }*/

    private void deploySink (List<Sensor> sensors) {
        for (int i = 0; i <= FIELD_X; i += 333) {
            for (int j = 0; j <= FIELD_Z; j += 333) {
                Sensor sensor = getSensor(i, FIELD_Y, j);
                sensor.setSink(true);
                sensors.add(sensor);
            }
        }
    }

    private List<Sensor> baseDeployment () {
        List<Sensor> sensors = new ArrayList<>();

        for (int i = 0; i < context.getSensorsNumber(); i++) {
            Sensor s1 = getSensor(MyLib.random(0, FIELD_X), MyLib.random(0, FIELD_Y - 10), MyLib.random(0, FIELD_Z));
            sensors.add(s1);
        }

        deploySink(sensors);

        return sensors;
    }

}
