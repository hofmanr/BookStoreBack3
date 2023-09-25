package nl.rhofman.bookstore.ejb.xml.service.catalog;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBContext;
import nl.rhofman.bookstore.ejb.xml.Catalog;
import nl.rhofman.bookstore.ejb.xml.dao.JaxbConfiguration;
import nl.rhofman.bookstore.ejb.xml.service.JaxbService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Dependent
@Catalog
public class CatalogJaxbService extends JaxbService {

    // Cache JAXB contexts for unmarshallers
    private static final Map<String, JAXBContext> unmarshallContexts = new ConcurrentHashMap<>();

    // Cache JAXB contexts for marshallers
    private static final Map<String, JAXBContext> marshallContexts = new ConcurrentHashMap<>();

    private JaxbConfiguration configuration;

    @Inject
    public CatalogJaxbService(@Catalog JaxbConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected String getJaxbPackage(String messageType) {
        return configuration.getJaxbPackage(messageType);
    }

    @Override
    protected Map<String, JAXBContext> unmarshallContexts() {
        return unmarshallContexts;
    }

    @Override
    protected Map<String, JAXBContext> marshallContexts() {
        return marshallContexts;
    }
}
