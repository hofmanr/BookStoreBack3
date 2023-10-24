package nl.rhofman.bookstore.ejb.scheduler.domain;


import jakarta.enterprise.util.AnnotationLiteral;

public class EventTypeLiteral extends AnnotationLiteral<EventType> implements EventType {
    private final SchedulerEvent event;

    public EventTypeLiteral(SchedulerEvent event) {
        this.event = event;
    }

    @Override
    public SchedulerEvent value() {
        return event;
    }
}
