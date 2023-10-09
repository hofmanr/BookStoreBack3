package nl.rhofman.bookstore.ejb.xml.dao.catalogue.assembler;

import nl.rhofman.bookstore.ejb.catalogue.domain.Catalogue;
import nl.rhofman.bookstore.ejb.xml.dao.Assembler;
import nl.rhofman.bookstore.jaxb.v1.catalogue.CatalogueType;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper()
public abstract class CatalogAssembler implements Assembler<Catalogue, CatalogueType> {
    public static CatalogAssembler instance() {
        return Mappers.getMapper(CatalogAssembler.class);
    }

    @Override
    @Mapping(target="books", source="books")
    @Mapping(target="sender", source="header.sender")
    @Mapping(target="recipient", source="header.recipient")
    @Mapping(target="messageID", source="header.messageId")
    @Mapping(target="correlationID", source="header.correlationId") // TODO Date/time of preparation
    public abstract Catalogue toDomain(CatalogueType jaxbObject);

    @Override
    @Mapping(target="header.dateOfPreparation", ignore=true)
    @Mapping(target="header.timeOfPreparation", ignore=true)
    @InheritInverseConfiguration
    public abstract CatalogueType toMessage(Catalogue domainObject);
}
