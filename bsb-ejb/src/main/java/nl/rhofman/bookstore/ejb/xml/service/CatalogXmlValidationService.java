package nl.rhofman.bookstore.ejb.xml.service;

import jakarta.enterprise.context.Dependent;
import nl.rhofman.bookstore.ejb.xml.Catalog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Dependent
@Catalog
public class CatalogXmlValidationService extends XmlValidationService {
    private List<String> xsdFiles = new ArrayList<>(Arrays.asList("Header.xsd", "Book.xsd", "Catalogue.xsd"));

    public CatalogXmlValidationService() {
        loadSchemas("/xsd/cat/v1", xsdFiles.toArray(new String[xsdFiles.size()]));
    }

}
