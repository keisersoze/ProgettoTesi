package app.core.actions;

import app.sim.SimContext;

public interface Action {
    void execute(SimContext context);
}
