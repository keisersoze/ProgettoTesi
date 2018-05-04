package app.factory.h20G;

import app.core.Action;
import app.core.Event;
import app.core.h20.actions.utility.RescheduleExpRandom;
import app.core.h20.events.BaseEvent;
import app.core.h20.events.SensorFrameEvent;
import app.core.h20.events.TransmissionEvent;
import app.core.h20G.HandleArrival;
import app.core.h20G.HandleEndReception;
import app.core.h20G.HandleTransmission;
import app.factory.ActionTypes;
import app.factory.EventTypes;
import app.factory.h20.MyCoreFactory;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;
import app.utils.Canvas;

public class GraphicCoreFactory extends MyCoreFactory {
    private Canvas canvas;
    Action handleEndReception;
    Action handleTransmission;
    Action handleArrival;

    public GraphicCoreFactory (Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public Action getAction(String type) {
        if (type == null) {
            return null;
        }

        if (type.equalsIgnoreCase(ActionTypes.HandleEndReception)) {
            if (handleEndReception == null) {
                handleEndReception = new HandleEndReception(canvas);
            }
            return handleEndReception;
        } else if (type.equalsIgnoreCase(ActionTypes.HandleTransmission)) {
            if (handleTransmission == null) {
                handleTransmission = new HandleTransmission(canvas);
            }
            return handleTransmission;
        } else if (type.equalsIgnoreCase(ActionTypes.HandleArrival)) {
            if (handleArrival == null) {
                handleArrival = new HandleArrival(canvas);
            }
            return handleArrival;
        }

        return super.getAction(type);
    }

}
