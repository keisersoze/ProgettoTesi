package app.factory.h20;

import app.utils.Canvas;
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
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;

public class GraphicCoreFactory extends MyCoreFactory {
    private Canvas canvas;

    public GraphicCoreFactory (Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public Event getEvent (String type, double time, SimContext context) {
        if (type == null) {
            return null;
        }
        Event e = null;
        if (type.equalsIgnoreCase(EventTypes.ArrivalEvent)) {
            e = new BaseEvent(time, context);
            e.addAction(new HandleArrival(canvas));
            e.addAction(new RescheduleExpRandom());
        }
        if (e == null) {
            return super.getEvent(type, time, context);
        } else { return e; }
    }

    @Override
    public Event getEvent (String type, double time, SimContext context, Transmission transmission) {
        if (type == null) {
            return null;
        }

        Event e = null;
        if (type.equalsIgnoreCase(EventTypes.EndReceptionEvent)) {
            e = new TransmissionEvent(time, context, transmission);
            e.addAction(new HandleEndReception(canvas));
            e.addAction(getAction(ActionTypes.UpdateSNR));
        }

        if (e == null) {
            return super.getEvent(type, time, context, transmission);
        } else { return e; }
    }

    @Override
    public Event getEvent (String type, double time, SimContext context, Frame frame, Sensor sensor, int hop) {
        if (type == null) {
            return null;
        }
        Event e = null;
        if (type.equalsIgnoreCase(EventTypes.TransmissionEvent)) {
            e = new SensorFrameEvent(time, context, frame, sensor, hop);
            e.addAction(new HandleTransmission(canvas));
            e.addAction(getAction(ActionTypes.UpdateSNR));
        }

        if (e == null) {
            return super.getEvent(type, time, context, frame, sensor, hop);
        } else { return e; }
    }
}
