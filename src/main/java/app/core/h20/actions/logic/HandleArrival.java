package app.core.h20.actions.logic;

import app.H2OSim;
import app.MyLib;
import app.core.Action;
import app.core.Event;
import app.factory.h20.EventTypes;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;

import java.util.List;

public class HandleArrival implements Action {

    public HandleArrival() {
    }

    @Override
    public void execute(Event event) {


        SimContext context = event.getContext();

        List<Sensor> sensors = context.getSensors();
        Sensor sender = sensors.get(context.getMarsenneTwister().nextInt(sensors.size())); //prendo un sensore a caso

        /*
        double x = context.getMarsenneTwister().nextDouble();

        double packetSize = x < H2OSim.MAX_FRAME_RATE ? H2OSim.MAX_FRAME_SIZE : H2OSim.MAX_FRAME_SIZE * (1-x); //riutilizzo x

        System.out.println(packetSize);

        Frame newFrame = context.getModelFactory().getFrame(packetSize, s, s);
        Event e = context.getCoreFactory().getEvent(EventTypes.TrasmissionEvent, 0, context, newFrame);
        context.getScheduler().addEvent(e);
        context.addFrame(newFrame);
        */

        List<Sensor> list = MyLib.calculateNeighbors(sender, context);

        double x = context.getMarsenneTwister().nextDouble();

        double packetSize = x < H2OSim.MAX_FRAME_RATE ? H2OSim.MAX_FRAME_SIZE : H2OSim.MAX_FRAME_SIZE * (1 - x);

        Frame frame = context.getModelFactory().getFrame(packetSize, sender);
        for (Sensor receiver :
                list) {

            Transmission transmission = context.getModelFactory().getTransmission(sender, receiver, frame);
            double time = sender.getEuclideanDistance(receiver) / H2OSim.SOUND_SPEED;
            Event e = context.getCoreFactory().getEvent(EventTypes.ReceivingTransmissionEvent, time, context, transmission);
            context.getScheduler().addEvent(e);
        }


    }

}
