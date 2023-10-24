package nl.rhofman.bookstore.ejb.scheduler.event;

import java.io.Serializable;

public class TimerEvent implements Serializable {
    private static final long serialVersionUID = 7438475545843l;

    private final long time = System.currentTimeMillis();

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "TimerEvent{" +
                "time=" + time +
                '}';
    }
}
