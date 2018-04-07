package app.core.actions.impl.logic;

import app.H2OSim;
import app.core.events.Event;
import app.factory.EventTypes;
import app.sim.SimContext;
import app.core.actions.Action;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.model.impl.BaseTransmission;

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
            Sensor receiver = sensors.get(context.getMarsenneTwister().nextInt(sensors.size())); // per adesso ne prendo uno a caso TODO risorsa condivisa?

            Transmission transmission = context.getModelComponentsFactory().getTransmission(sender, receiver); // bisogna decidere se il receiver è sempre lo stesso in caso di bloccaggio

            frame.setCurrentTransmission(transmission);

            Event e = context.getCoreComponentsFactory().getEvent(EventTypes.EndTrasmissionEvent,frame.getSize(),context,frame);
            context.getScheduler().addEvent(e);

        } else {

            //per adesso il tempo da aspettare è una variabile casuale exp
            Event e = context.getCoreComponentsFactory().getEvent(EventTypes.TrasmissionEvent,-log(context.getMarsenneTwister().nextDouble()) / H2OSim.LAMDA, context, frame);
            context.getScheduler().addEvent(e);

        }

    }

    public Sensor pickReceiver(){
        return null;
    }
}
