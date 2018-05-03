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
import java.util.stream.Collectors;

public class HandleArrival implements Action {

    /**
     * Crea il frame e decide l'owner del frame creato
     *
     * @param event evento su cui viene chiamata la action
     */
    @Override
    public void execute (Event event) {
        SimContext context = event.getContext();
        List<Sensor> possibleOwners = context.getSensors().stream().filter(sensor -> !sensor.isOccupied() && !sensor.isSink()).collect(Collectors.toList());

        if (possibleOwners.size() > 0) {
            Sensor owner = possibleOwners.get(context.getMarsenneTwister().nextInt(possibleOwners.size()));
            double x = context.getMarsenneTwister().nextDouble();
            double packetSize = x < H20Sim.MAX_FRAME_RATE ? H20Sim.MAX_FRAME_SIZE : H20Sim.MAX_FRAME_SIZE * (1 - x); // Dimensione del pacchetto

            Frame frame = context.getModelFactory().getFrame(packetSize, owner, context.getSimTime());  //Crea il frame
            context.getFrames().add(frame);

            Event e = context.getCoreFactory().getEvent(EventTypes.TransmissionEvent, 0, context, frame, owner, 0); // Passa il frame al prossimo evento
            context.getScheduler().addEvent(e);

            // STATS
            context.getFramesArrived().put(frame, new LinkedList<>());
        } else {
            //System.out.println(ConsoleColors.RED + "WARNING: No sensors available to handle the ArrivalEvent " + this + ConsoleColors.RESET);
        }
    }
}
