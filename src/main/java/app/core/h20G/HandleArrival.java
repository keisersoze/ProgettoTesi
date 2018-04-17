package app.core.h20G;

import app.Canvas;
import app.H2OSim;
import app.core.Action;
import app.core.Event;
import app.factory.h20.EventTypes;
import app.model.Frame;
import app.model.Sensor;
import app.sim.SimContext;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class HandleArrival implements Action {

    private Canvas canvas;

    public HandleArrival (Canvas canvas) {
        this.canvas = canvas;
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

        Sensor owner;
        do {
            owner = sensors.get(context.getMarsenneTwister().nextInt(sensors.size())); //prendo un sensore a caso
        }while (owner.isTransmitting()||owner.isSink());


        double x = context.getMarsenneTwister().nextDouble();
        double packetSize = x < H2OSim.MAX_FRAME_RATE ? H2OSim.MAX_FRAME_SIZE : H2OSim.MAX_FRAME_SIZE * (1 - x);

        Frame frame = context.getModelFactory().getFrame(packetSize, owner);
        try {
            canvas.enqueue(() -> canvas.newFrame(frame)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        context.getFrames().add(frame);

        Event e = context.getCoreFactory().getEvent(EventTypes.TransmissionEvent, 0, context, frame, owner, 0);

        context.getScheduler().addEvent(e);
    }

}
