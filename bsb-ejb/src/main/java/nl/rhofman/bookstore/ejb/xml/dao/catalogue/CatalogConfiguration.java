package nl.rhofman.bookstore.ejb.xml.dao.catalogue;

import jakarta.enterprise.context.ApplicationScoped;
import nl.rhofman.bookstore.ejb.xml.Catalog;
import nl.rhofman.bookstore.ejb.xml.dao.Assembler;
import nl.rhofman.bookstore.ejb.xml.dao.JaxbConfiguration;
import nl.rhofman.bookstore.ejb.xml.dao.catalogue.assembler.CatalogAssembler;
import nl.rhofman.bookstore.ejb.xml.dao.catalogue.assembler.ConfirmationAssembler;
import org.mapstruct.factory.Mappers;

import java.util.*;

@ApplicationScoped
@Catalog
public class CatalogConfiguration implements JaxbConfiguration {
    // Package where the JAXB (Java) objects can be found
    private static final String BASE_PACKAGE = "nl.rhofman.bookstore.jaxb.v1.";
    // Directory where the XSD's can be found
    private static final String XSD_DIRECTORY = "/xsd/cat/v1";

    // XSD's for the CatalogXmlValidationService
    private static final List<String> XSD_FILES = new ArrayList<>(Arrays.asList("Header.xsd", "Book.xsd", "Catalogue.xsd", "Confirmation.xsd"));

    // Map a Jaxb object to a corresponding assembler (for generating internal objects)
    private static final Map<String, Class<?>> JAXB_ASSEMBLER_MAP = new HashMap<>();

    // Map an internal object to a corresponding assembler (for generating Jaxb objects)
    private static final Map<String, Class<?>> DOMAIN_ASSEMBLER_MAP = new HashMap<>();


    static {
        JAXB_ASSEMBLER_MAP.put("CATALOGUETYPE", CatalogAssembler.class);
        JAXB_ASSEMBLER_MAP.put("CONFIRMATIONTYPE", ConfirmationAssembler.class);

        DOMAIN_ASSEMBLER_MAP.put("CATALOGUE", CatalogAssembler.class);
        DOMAIN_ASSEMBLER_MAP.put("CONFIRMATION", ConfirmationAssembler.class);
    }

    public CatalogConfiguration() {
    }

    @Override
    public String getXsdDirectory() {
        return XSD_DIRECTORY;
    }

    @Override
    public List<String> getXsdFiles() {
        return XSD_FILES;
    }

    /**
     * Maps de root element in the XML to the Jaxb-generated Java object
     *    e.g. if the XML is something like '<Catalogue>...</Catalogue>', then the root element is 'Catalogue'
     *         the root element 'Catalogue' will be mapped to BASE_PACKAGE + MAPPED_PACKAGE = 'nl.rhofman.bookstore.jaxb.v1.catalogue'
     * @param messageType the root element of the XML-document
     * @return the package where the (Jaxb) generated object can be found
     */
    @Override
    public String getJaxbPackage(String messageType) {
        return BASE_PACKAGE + messageType.toLowerCase();
    }

    @Override
    public Assembler getJaxbAssembler(Object jaxbObject) {
        String className = jaxbObject.getClass().getSimpleName().toUpperCase();
        return (Assembler) Mappers.getMapper(JAXB_ASSEMBLER_MAP.get(className));
    }

    @Override
    public Assembler getDomainAssembler(Object dto) {
        String className = dto.getClass().getSimpleName().toUpperCase();
        return (Assembler) Mappers.getMapper(DOMAIN_ASSEMBLER_MAP.get(className));
    }

}
