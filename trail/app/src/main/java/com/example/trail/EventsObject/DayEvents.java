package com.example.trail.EventsObject;

import java.util.Vector;

import lombok.Data;

@Data
public class DayEvents {
    private Vector<Event> events;
    public boolean addEvent(Event e)
    {
        return events.add(e);
    }
    public Event getFirstEvent()
    {
        return events.firstElement();
    }
    public Event getLastEvent()
    {
        return events.lastElement();
    }
}
