package nl.rhofman.bookstore.ejb.xml.dao.catalogue.assembler;

import nl.rhofman.bookstore.ejb.catalogue.domain.Author;
import nl.rhofman.bookstore.ejb.catalogue.domain.Book;
import nl.rhofman.bookstore.ejb.catalogue.domain.Catalogue;
import nl.rhofman.bookstore.ejb.xml.dao.Assembler;
import nl.rhofman.bookstore.ejb.xml.dao.BaseAssembler;
import nl.rhofman.bookstore.jaxb.v1.catalogue.AuthorType;
import nl.rhofman.bookstore.jaxb.v1.catalogue.BookType;
import nl.rhofman.bookstore.jaxb.v1.catalogue.CatalogueType;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class CatalogAssembler extends BaseAssembler implements Assembler<Catalogue, CatalogueType> {
    public static CatalogAssembler instance() {
        return Mappers.getMapper(CatalogAssembler.class);
    }

    @Override
    @Mapping(target="books", source="books")
    @Mapping(target="sender", source="header.sender")
    @Mapping(target="recipient", source="header.recipient")
    @Mapping(target="messageID", source="header.messageId")
    @Mapping(target="correlationID", source="header.correlationId")
    @Mapping(target="timestamp", ignore = true)
    public abstract Catalogue toDomain(CatalogueType jaxbObject);

    @Override
    @Mapping(target="header.dateOfPreparation", ignore=true)
    @Mapping(target="header.timeOfPreparation", ignore=true)
    @InheritInverseConfiguration
    public abstract CatalogueType toMessage(Catalogue domainObject);

    @Mapping(target="initials", ignore = true)
    protected abstract Author toAuthor(AuthorType author);

    @Mapping(target="imageURL", source = "imageUrl")
    public abstract BookType mapToBookType(Book book);

    @InheritInverseConfiguration
    public abstract Book mapToBook(BookType book);

    @AfterMapping
    protected void setTimestamp(@MappingTarget Catalogue domainObject, CatalogueType jaxbObject) {
        domainObject.setTimestamp(getTimestamp(jaxbObject.getHeader()));
    }

    @AfterMapping
    protected void setDateAndTime(@MappingTarget CatalogueType jaxbObject, Catalogue domainObject) {
        setHeaderTimestamp(jaxbObject.getHeader(), domainObject.getTimestamp());
    }
}
