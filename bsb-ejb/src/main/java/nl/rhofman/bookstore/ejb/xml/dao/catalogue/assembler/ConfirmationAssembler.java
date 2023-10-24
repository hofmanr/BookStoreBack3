package nl.rhofman.bookstore.ejb.xml.dao.catalogue.assembler;

import nl.rhofman.bookstore.ejb.catalogue.domain.JmsConformation;
import nl.rhofman.bookstore.ejb.xml.dao.Assembler;
import nl.rhofman.bookstore.ejb.xml.dao.BaseAssembler;
import nl.rhofman.bookstore.jaxb.v1.catalogue.ConfirmationType;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalTime;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class ConfirmationAssembler extends BaseAssembler implements Assembler<JmsConformation, ConfirmationType> {
    public static ConfirmationAssembler instance() {
        return Mappers.getMapper(ConfirmationAssembler.class);
    }

    @Override
    @Mapping(target = "sender", source="header.sender")
    @Mapping(target = "recipient", source="header.recipient")
    @Mapping(target = "messageID", source="header.messageId")
    @Mapping(target = "correlationID", source="header.correlationId")
    @Mapping(target = "domainObject.successful", source="result.success")
    @Mapping(target = "domainObject.errorMessage", source="result.error")
    @Mapping(target = "timestamp", ignore = true)
    public abstract JmsConformation toDomain(ConfirmationType jaxb);

    @Override
    @InheritInverseConfiguration
    public abstract ConfirmationType toMessage(JmsConformation domainObject);

    @AfterMapping
    protected void setHeaderDateAndTime(@MappingTarget ConfirmationType message) {
        message.getHeader().setDateOfPreparation(LocalDate.now());
        message.getHeader().setTimeOfPreparation(LocalTime.now());
    }

    @AfterMapping
    protected void setTimestamp(@MappingTarget JmsConformation domainObject, ConfirmationType jaxbObject) {
        domainObject.setTimestamp(getTimestamp(jaxbObject.getHeader()));
    }

    @AfterMapping
    protected void setDateAndTime(@MappingTarget ConfirmationType jaxbObject, JmsConformation domainObject) {
        setHeaderTimestamp(jaxbObject.getHeader(), domainObject.getTimestamp());
    }

}
