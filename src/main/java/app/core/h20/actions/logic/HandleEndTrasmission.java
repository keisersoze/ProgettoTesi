package app.core.h20.actions.logic;

import app.core.Event;
import app.core.h20.EventTypes;
import app.sim.SimContext;
import app.core.Action;
import app.model.Frame;


public class HandleEndTrasmission implements Action {

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

                Event e = context.getCoreComponentsFactory().getEvent(EventTypes.TrasmissionEvent,0, context, frame);
                context.getScheduler().addEvent(e);

            } else {
                frame.setArrived(true);
                frame.setCurrentTransmission(null);
                context.frameArrived(frame);// aggiorna il context segnalando che un frame è arrivato
            }
        }

    }

}
