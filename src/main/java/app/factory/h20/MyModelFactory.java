package app.factory.h20;

import app.factory.DeploymentTypes;
import app.factory.ModelFactory;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.model.h20.BaseFrame;
import app.model.h20.BaseSensor;
import app.model.h20.BaseTransmission;
import app.sim.MyLib;

import java.util.ArrayList;
import java.util.List;

public class MyModelFactory implements ModelFactory {
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
    public List<Sensor> deploySensors(String deploymentType) {
        if (deploymentType.equalsIgnoreCase(DeploymentTypes.BaseDeployment)){
            return baseDeployment();
        }else
            return  null;

    }

    private List<Sensor> baseDeployment() {
        List<Sensor> sensors = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            Sensor s1 = getSensor(MyLib.random(0, 200), MyLib.random(0, 80), MyLib.random(0, 200));
            sensors.add(s1);
        }

        Sensor s1 = getSensor(MyLib.random(0, 200), 90, MyLib.random(0, 200));
        sensors.add(s1);

        Sensor s2 = getSensor(MyLib.random(0, 200), 90, MyLib.random(0, 200));
        sensors.add(s2);

        Sensor s3 = getSensor(MyLib.random(0, 200), 90, MyLib.random(0, 200));
        sensors.add(s3);

        s1.setSink(true);
        s2.setSink(true);
        s3.setSink(true);

        return sensors;
    }

}
