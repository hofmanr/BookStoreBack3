package nl.rhofman.bookstore.ejb.scheduler.domain;

public enum SchedulerEvent {
    SERVICE_1(Values.SERVICE1),
    SERVICE_2(Values.SERVICE2);

    private final String value;

    SchedulerEvent(String value) {
        this.value = value;
    }

    public static SchedulerEvent fromValue(String v) {
        for (SchedulerEvent e : SchedulerEvent.values()) {
            if (e.value.equals(v)) {
                return e;
            }
        }
        throw new IllegalArgumentException(v);
    }

    public static class Values {
        public static final String SERVICE1 = "Service1";
        public static final String SERVICE2 = "Service2";
    }
}
