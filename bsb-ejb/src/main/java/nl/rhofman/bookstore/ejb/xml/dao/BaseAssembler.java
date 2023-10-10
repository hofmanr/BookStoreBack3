package nl.rhofman.bookstore.ejb.xml.dao;

import nl.rhofman.bookstore.jaxb.v1.catalogue.HeaderType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class BaseAssembler {

    protected LocalDateTime getTimestamp(HeaderType header) {
        if (header == null) {
            return null;
        }
        LocalDate dateOfPrep = header.getDateOfPreparation();
        LocalTime timeOfPrep = header.getTimeOfPreparation();
        if (dateOfPrep == null) {
            return null;
        }
        return timeOfPrep == null ?
                LocalDateTime.of(dateOfPrep, LocalTime.MIDNIGHT) :
                LocalDateTime.of(dateOfPrep, timeOfPrep);
    }

    protected void setHeaderTimestamp(HeaderType header, LocalDateTime timestamp) {
        if (timestamp == null || header == null) {
            return;
        }
        header.setDateOfPreparation(timestamp.toLocalDate());
        header.setTimeOfPreparation(timestamp.toLocalTime());
    }
}
