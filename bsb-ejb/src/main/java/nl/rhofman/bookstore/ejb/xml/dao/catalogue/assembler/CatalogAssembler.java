package nl.rhofman.bookstore.ejb.xml.dao.catalogue.assembler;

import nl.rhofman.bookstore.ejb.message.domain.Catalogue;
import nl.rhofman.bookstore.ejb.xml.dao.Assembler;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper()
public abstract class CatalogAssembler implements Assembler<Catalogue, nl.rhofman.bookstore.jaxb.v1.catalogue.Catalogue> {
    public static CatalogAssembler instance() {
        return Mappers.getMapper(CatalogAssembler.class);
    }

    @Override
    @Mapping(target="books", source="books")
    public abstract Catalogue toDomain(nl.rhofman.bookstore.jaxb.v1.catalogue.Catalogue jaxbObject);

    @Override
    @Mapping(target="header", ignore=true)
    @InheritInverseConfiguration
    public abstract nl.rhofman.bookstore.jaxb.v1.catalogue.Catalogue toMessage(Catalogue domainObject);
}
