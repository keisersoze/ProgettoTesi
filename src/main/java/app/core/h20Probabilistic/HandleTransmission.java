/*
 * Copyright (c) 2018. Tesi di laurea in Informatica Università Ca' Foscari di Venezia. Creato da Alessio Del Conte e Filippo Maganza
 */

package app.core.h20Probabilistic;

import app.H20Sim;
import app.core.Action;
import app.core.Event;
import app.factory.EventTypes;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;
import app.utils.MyLib;

import java.util.HashMap;
import java.util.Map;

public class HandleTransmission implements Action {
    private Map<Sensor, Double> sensorTransmissionMap;

    public HandleTransmission() {
        this.sensorTransmissionMap = new HashMap<>();
    }

    protected static boolean CSMA (Sensor sender, SimContext context) {
        return MyLib.tomW(H20Sim.SENSOR_POWER) / MyLib.calculateNoise(sender, context) >= H20Sim.CSMA_STRENGTH;
    }

    @Override
    public void execute (Event event) {
        SimContext context = event.getContext();
        Frame frame = event.getFrame();
        Sensor sender = event.getSensor();
        int numHop = event.getInt();
        sender.setWaiting(false);
        sender.setTransmitting(true);

        sensorTransmissionMap.put(sender,context.getSimTime());
        for (Sensor receiver : sender.getNeighbors()) {     // Per tutti i sensori che possono ricevere viene creato un nuovo evento
            Transmission transmission = context.getModelFactory().getTransmission(sender, receiver, frame, numHop);
            transmission.setTime(context.getSimTime());
            frame.getTransmissions().add(transmission);

            double time = sender.getEuclideanDistance(receiver) / H20Sim.SOUND_SPEED;   // Tra quanto schedulo l'evento
            Event e = context.getCoreFactory().getEvent(EventTypes.ReceptionEvent, time, context, transmission);
            context.getScheduler().addEvent(e);
        }
        double time = frame.getSize() / H20Sim.SENSOR_BANDWIDTH;    // Schedulo tra quanto finisco di trasmettere
        Event e = context.getCoreFactory().getEvent(EventTypes.EndTransmissionEvent, time, context, sender);    // Creo un evento per la fine della trasmissione
            context.getScheduler().addEvent(e);
    }

    public Map<Sensor, Double> getSensorTransmissionMap() {
        return sensorTransmissionMap;
    }
}
