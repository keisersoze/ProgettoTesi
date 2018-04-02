package app.core.actions.impl.logic;

import app.SimContext;
import app.core.actions.Action;
import app.core.events.impl.TrasmissionEvent;
import app.model.Frame;


public class HandleEndTrasmission implements Action {
    SimContext context;
    Frame frame;

    public HandleEndTrasmission(SimContext context, Frame frame) {
        this.context = context;
        this.frame = frame;
    }

    @Override
    public void execute(SimContext context) {
        if (frame.getCurrentTransmission().isSuccessfull()) {//se la trasmissione è andata a buon fine
            if (!frame.getCurrentTransmission().getReceiver().isSink()) { // se non è ancora arrivato

                frame.setCurrentOwner(frame.getCurrentTransmission().getReceiver()); // il frame arriva a destinazione
                frame.setCurrentTransmission(null);// nessuna trasmissione è più associata al frame

                context.getScheduler().addEvent(new TrasmissionEvent(0, context, frame));

            } else {
                context.frameArrived();// aggiorna il context segnalando che un frame è arrivato
            }
        }

    }
}
