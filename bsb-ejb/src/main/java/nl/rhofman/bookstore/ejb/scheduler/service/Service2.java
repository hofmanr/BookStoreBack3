package nl.rhofman.bookstore.ejb.scheduler.service;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import nl.rhofman.bookstore.ejb.scheduler.domain.EventType;
import nl.rhofman.bookstore.ejb.scheduler.domain.SchedulerEvent;
import nl.rhofman.bookstore.ejb.scheduler.event.TimerEvent;

@Dependent
@Transactional
public class Service2 {

    public void consumeEvent(@Observes @EventType(SchedulerEvent.SERVICE_2) TimerEvent event) {
        // Do some work here
        System.out.println("Service2 event.toString() = " + event.toString());
    }
}
