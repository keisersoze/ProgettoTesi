package app.core.actions.impl.logic;

import app.H2OSim;
import app.core.events.Event;
import app.sim.SimContext;
import app.core.actions.Action;
import app.core.events.impl.EndTrasmissionEvent;
import app.core.events.impl.TrasmissionEvent;
import app.model.Frame;
import app.model.Sensor;
import app.model.Trasmission;
import app.model.impl.BaseTrasmission;

import java.util.List;

import static org.apache.commons.math3.util.FastMath.log;

public class HandleTrasmission implements Action {

    public HandleTrasmission() {
    }

    @Override
    public void execute(Event event) {

        SimContext context = event.getContext();
        Frame frame = event.getFrame();

        if (true) {//qua andrà modellato il CSMA
            Sensor sender = frame.getCurrentOwner();

            List<Sensor> sensors = event.getContext().getSensors();
            Sensor receiver = sensors.get(H2OSim.MERSENNE_TWISTER.nextInt(sensors.size())); // per adesso ne prendo uno a caso

            Trasmission trasmission = new BaseTrasmission(sender, receiver); // bisogna decidere se il receiver è sempre lo stesso in caso di bloccaggio

            frame.setCurrentTransmission(trasmission);

            context.getScheduler().addEvent(new EndTrasmissionEvent(frame.getSize(), context, frame));

        } else {

            //per adesso il tempo da aspettare è una variabile casuale exp
            context.getScheduler().addEvent(new TrasmissionEvent(-log(H2OSim.MERSENNE_TWISTER.nextDouble()) / H2OSim.LAMDA, context, frame));

        }

    }
}
