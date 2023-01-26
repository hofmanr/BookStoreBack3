package nl.rhofman.bookstore.persist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;

import java.io.Serializable;
import java.util.Date;

@Entity
@DiscriminatorValue("R")  // type ReturnShipment
public class ReturnShipmentTimer extends Timer implements Serializable {

    public ReturnShipmentTimer() {
    }

    public ReturnShipmentTimer(Order order, @Future Date timerDate) {
        this.order = order;
        this.timerDate = timerDate;
    }

    @Override
    public String toString() {
        return "ReturnShipmentTimer{" +
                "order=" + order +
                ", timerDate=" + timerDate +
                '}';
    }
}
