package app.core.h20G;

import app.utils.Canvas;
import app.core.Event;

public class HandleEndTransmission extends app.core.h20.actions.logic.HandleEndTransmission {
    private Canvas canvas;

    public HandleEndTransmission (Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void execute (Event e) {
        super.execute(e);
    }
}
