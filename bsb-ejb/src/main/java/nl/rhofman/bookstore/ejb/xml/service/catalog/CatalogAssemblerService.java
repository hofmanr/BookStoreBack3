package nl.rhofman.bookstore.ejb.xml.service.catalog;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.xml.Catalog;
import nl.rhofman.bookstore.ejb.xml.dao.Assembler;
import nl.rhofman.bookstore.ejb.xml.dao.JaxbConfiguration;
import nl.rhofman.bookstore.ejb.xml.service.AssemblerService;

@Dependent
@Catalog
public class CatalogAssemblerService extends AssemblerService {

    @Inject
    @Catalog
    private JaxbConfiguration configuration;

    @Override
    public <T, U> T toDomain(U jaxb) {
        Assembler<T,U> assembler = configuration.getJaxbAssembler(jaxb);
        return assembler.toDomain(jaxb);
    }

    @Override
    public <T, U> U toMessage(T dto) {
        Assembler<T,U> assembler = configuration.getDomainAssembler(dto);
        return assembler.toMessage(dto);
    }
}
