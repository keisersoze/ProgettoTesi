package app.core.actions.impl.logic;

import app.SimContext;
import app.core.actions.Action;
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
            if (!frame.getCurrentTransmission().getReceiver().isSink()){ // se non è ancora arrivato

            }
        }
    }
}
