package nl.rhofman.bookstore.persist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@DiscriminatorValue("P")  // type Payment
public class PaymentTimer extends Timer implements Serializable {

    @Column(name = "reminder", nullable = false, columnDefinition = "smallint")
    @NotNull
    private Integer reminder;

    public PaymentTimer() {
    }

    public PaymentTimer(Order order, @Future Date timerDate, Boolean reminder) {
        this.order = order;
        this.timerDate = timerDate;
        this.reminder = reminder ? 1 : 0;
    }

    public Integer getReminder() {
        return reminder;
    }

    public void setReminder(Integer reminder) {
        this.reminder = reminder;
    }

    @Override
    public String toString() {
        return "PaymentTimer{" +
                "order=" + order +
                ", reminder=" + reminder +
                ", timerDate=" + timerDate +
                '}';
    }
}
