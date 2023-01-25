package nl.rhofman.bookstore.persist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@DiscriminatorValue("R")  // type ReturnShipment
@NamedQueries({
        @NamedQuery(name = Timer.FIND_RETURNSHIPMENT, query = "SELECT t FROM ReturnShipmentTimer t WHERE t.returnShipment = :returnShipment")
})
public class ReturnShipmentTimer extends Timer implements Serializable {

    @JoinColumn(name = "returnshipment_id", updatable = false)
    @OneToOne
    @NotNull
    private ReturnShipment returnShipment;

    public ReturnShipmentTimer() {
    }

    public ReturnShipmentTimer(ReturnShipment returnShipment, @Future Date timerDate) {
        this.returnShipment = returnShipment;
        this.timerDate = timerDate;
    }

    public ReturnShipment getReturnShipment() {
        return returnShipment;
    }

    public void setReturnShipment(ReturnShipment returnShipment) {
        this.returnShipment = returnShipment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReturnShipmentTimer)) return false;
        if (!super.equals(o)) return false;
        ReturnShipmentTimer that = (ReturnShipmentTimer) o;
        return returnShipment.equals(that.returnShipment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), returnShipment);
    }

    @Override
    public String toString() {
        return "ReturnShipmentTimer{" +
                "returnShipment=" + returnShipment +
                ", timerDate=" + timerDate +
                '}';
    }
}
