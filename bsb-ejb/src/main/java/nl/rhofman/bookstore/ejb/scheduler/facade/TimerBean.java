package nl.rhofman.bookstore.ejb.scheduler.facade;

import jakarta.ejb.*;
import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.scheduler.domain.SchedulerEvent;
import nl.rhofman.bookstore.ejb.scheduler.service.SchedulerTimerService;

@Singleton(name = "TimerEJB")
@Startup
public class TimerBean {

    @Inject
    private SchedulerTimerService timerService;

    public TimerBean() {
    }

    /**
     * Persistent false means that the status of the timer is not saved when terminating the application
     * When persistent true then timers will be saved in a database. The advantage is that when more than
     * one instance is running, only one instance gets the event.
     * @param timer
     */
    @Lock(LockType.READ)
    @Schedule(minute = "*/1", hour = "*", persistent = false, info = SchedulerEvent.Values.SERVICE1)
    // Every minute
    public void executeService1(Timer timer) {
        timerService.fireEvent(timer);
    }

    @Lock(LockType.READ)
    @Schedule(minute = "*/2", hour = "*", persistent = false, info = SchedulerEvent.Values.SERVICE2)
    // Every 2 minutes
    public void executeService2(Timer timer) {
        timerService.fireEvent(timer);
    }
}
