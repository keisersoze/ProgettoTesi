package app.core.h20.actions.logic;

import app.H20Sim;
import app.core.Action;
import app.core.Event;
import app.factory.EventTypes;
import app.model.Frame;
import app.model.Sensor;
import app.sim.SimContext;

import java.util.LinkedList;
import java.util.List;

public class HandleArrival implements Action {

    public HandleArrival () {
    }

    /**
     * Crea il frame e decide l'owner del frame creato
     *
     * @param event evento su cui viene chiamata la action
     */
    @Override
    public void execute (Event event) {
        SimContext context = event.getContext();

        List<Sensor> sensors = context.getSensors();

        Sensor owner = null;
        do {
            owner = sensors.get(context.getMarsenneTwister().nextInt(sensors.size())); //prendo un sensore a caso
        } while (owner.isTransmitting() || owner.isSink()); // accertarsi di avere "tanti" sensori

        double x = context.getMarsenneTwister().nextDouble();
        double packetSize = x < H20Sim.MAX_FRAME_RATE ? H20Sim.MAX_FRAME_SIZE : H20Sim.MAX_FRAME_SIZE * (1 - x);

        Frame frame = context.getModelFactory().getFrame(packetSize, owner, context.getSimTime());
        context.getFrames().add(frame);

        Event e = context.getCoreFactory().getEvent(EventTypes.TransmissionEvent, 0, context, frame, owner, 0);

        context.getScheduler().addEvent(e);


        // STATS

        context.getFramesArrived().put(frame, new LinkedList<>());

    }

}
