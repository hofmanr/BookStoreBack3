package nl.rhofman.bookstore.ejb.xml.service.catalog;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.xml.Catalog;
import nl.rhofman.bookstore.ejb.xml.dao.JaxbConfiguration;
import nl.rhofman.bookstore.ejb.xml.service.XmlValidationService;

import java.util.List;

@Dependent
@Catalog
public class CatalogXmlValidationService extends XmlValidationService {

    @Inject
    public CatalogXmlValidationService(@Catalog JaxbConfiguration configuration) {
        List<String> xsdFiles = configuration.getXsdFiles();
        loadSchemas(configuration.getXsdDirectory(), xsdFiles.toArray(new String[xsdFiles.size()]));
    }

}
