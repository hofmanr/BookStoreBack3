package nl.rhofman.bookstore.persist.model;

import jakarta.persistence.*;
import nl.rhofman.persist.model.BaseEntityVersion;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Timers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "timer_type", discriminatorType = DiscriminatorType.CHAR, length = 1)
@NamedQueries({
        @NamedQuery(name = Timer.FIND_EXPIRED, query = "SELECT t FROM Timer t WHERE t.timerDate <= CURRENT_DATE"),
        @NamedQuery(name = Timer.FIND_ORDER, query = "SELECT t FROM PaymentTimer t WHERE t.order = :order")
})
public class Timer extends BaseEntityVersion implements Serializable {
    private static final long serialVersionUID = 3766632083928336l;

    public static final String FIND_EXPIRED = "Timer.findByDate";
    public static final String FIND_ORDER = "Timer.findByOrder";

    @ManyToOne
    protected Order order;

    @Column(name = "timer_date")
    @Temporal(TemporalType.DATE)
    protected Date timerDate;

    public Date getTimerDate() {
        return timerDate;
    }

    public void setTimerDate(Date timerDate) {
        this.timerDate = timerDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Timer)) return false;
        if (!super.equals(o)) return false;
        Timer timer = (Timer) o;
        return timerDate.equals(timer.timerDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), timerDate);
    }
}
