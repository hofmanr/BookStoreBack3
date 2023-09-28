package nl.rhofman.bookstore.ejb.xml.dao.catalogue.assembler;

import nl.rhofman.bookstore.ejb.catalogue.domain.Confirmation;
import nl.rhofman.bookstore.ejb.xml.dao.Assembler;
import nl.rhofman.bookstore.jaxb.v1.catalogue.ConfirmationType;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalTime;

@Mapper()
public abstract class ConfirmationAssembler implements Assembler<Confirmation, ConfirmationType> {
    public static ConfirmationAssembler instance() {
        return Mappers.getMapper(ConfirmationAssembler.class);
    }

    @Override
    @Mapping(source="header.sender", target = "sender")
    @Mapping(source="header.recipient", target = "recipient")
    @Mapping(source="header.messageId", target = "messageID")
    @Mapping(source="header.correlationId", target = "correlationID")
    @Mapping(source="confirmation.success", target = "successful")
    @Mapping(source="confirmation.error", target = "errorMessage")
    public abstract Confirmation toDomain(ConfirmationType jaxb);

    @Override
    @InheritInverseConfiguration
    public abstract ConfirmationType toMessage(Confirmation dto);

    @AfterMapping
    protected void setHeaderDateAndTime(@MappingTarget ConfirmationType message) {
        message.getHeader().setDateOfPreparation(LocalDate.now());
        message.getHeader().setTimeOfPreparation(LocalTime.now());
    }

}
