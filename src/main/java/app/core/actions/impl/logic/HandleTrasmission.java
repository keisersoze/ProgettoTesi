package app.core.actions.impl.logic;

import app.H2OSim;
import app.SimContext;
import app.core.actions.Action;
import app.core.events.Event;
import app.core.events.impl.EndTrasmissionEvent;
import app.model.Frame;
import app.model.Sensor;
import app.model.Trasmission;
import app.model.impl.BaseTrasmission;

import static org.apache.commons.math3.util.FastMath.log;

public class HandleTrasmission implements Action{
    Frame frame;
    Event event;

    public HandleTrasmission(Frame frame, Event event) {
        this.frame = frame;
        this.event = event;
    }

    @Override
    public void execute(SimContext context) {

        if(true){//qua andrà modellato il CSMA
            Sensor sender = frame.getCurrentOwner();
            Sensor receiver = null; //verrà deciso in base alla tabelle di multihop dei sensori

            Trasmission trasmission = new BaseTrasmission(sender,receiver); // bisogna decidere se il receiver è sempre lo stesso in caso di bloccaggio

            context.getScheduler().addEvent(new EndTrasmissionEvent(frame.getSize(),context,frame));
        }else {

            //per adesso il tempo da aspettare è una variabile casuale exp
            event.updateTime(-log(H2OSim.MERSENNE_TWISTER.nextDouble()) / H2OSim.LAMDA);
            context.getScheduler().addEvent(event);

        }

    }
}
