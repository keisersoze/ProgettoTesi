package app.core.actions.impl.logic;

import app.core.events.Event;
import app.sim.SimContext;
import app.core.actions.Action;
import app.core.events.impl.TrasmissionEvent;
import app.model.Frame;


public class HandleEndTrasmission implements Action {

    public static final String id="HANDLEENDTRASMISSION";

    public HandleEndTrasmission() {
    }

    @Override
    public void execute(Event event) {

        SimContext context = event.getContext();
        Frame frame = event.getFrame();

        if (frame.getCurrentTransmission().isSuccessfull()) {//se la trasmissione è andata a buon fine
            if (!frame.getCurrentTransmission().getReceiver().isSink()) { // se non è ancora arrivato

                frame.setCurrentOwner(frame.getCurrentTransmission().getReceiver()); // il frame arriva a destinazione
                frame.setCurrentTransmission(null);// nessuna trasmissione è più associata al frame

                context.getScheduler().addEvent(new TrasmissionEvent(0, context, frame));

            } else {
                frame.setArrived(true);
                frame.setCurrentTransmission(null);
                context.frameArrived(frame);// aggiorna il context segnalando che un frame è arrivato
            }
        }

    }
}
