package nl.rhofman.bookstore.ejb.scheduler.service;

import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;
import jakarta.ejb.Timer;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.scheduler.domain.EventTypeLiteral;
import nl.rhofman.bookstore.ejb.scheduler.domain.SchedulerEvent;
import nl.rhofman.bookstore.ejb.scheduler.event.TimerEvent;

@Stateless(name = "SchedulerTimerServiceEJB")
public class SchedulerTimerService {

    @Inject
    private Event<TimerEvent> timerEvent;

    public SchedulerTimerService() {
    }

    /**
     * This method is asynchronous, so that the timerBean is not blocked.
     * Besides that, this EJB has its own resources (see ejb-jar.xml).
     * The timerBean is 'clean' and has no associated resources.
     * @param timer
     */
    @Asynchronous
    public void fireEvent(Timer timer) {
        SchedulerEvent schedulerEvent = SchedulerEvent.fromValue(timer.getInfo().toString());

        timerEvent.select(new EventTypeLiteral(schedulerEvent)).fire(new TimerEvent());
    }

}
