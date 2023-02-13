package nl.rhofman.bookstore.ejb.xml.dao.Catalogue;

import jakarta.enterprise.context.ApplicationScoped;
import nl.rhofman.bookstore.ejb.xml.Catalog;
import nl.rhofman.bookstore.ejb.xml.dao.Assembler;
import nl.rhofman.bookstore.ejb.xml.dao.JaxbConfiguration;

import java.util.*;

@ApplicationScoped
@Catalog
public class CatalogConfiguration implements JaxbConfiguration {
    // Package where the JAXB (Java) objects can be found
    private static final String BASE_PACKAGE = "nl.rhofman.bookstore.jaxb.catalogue.v1.";
    // Directory where the XSD's can be found
    private static final String XSD_DIRECTORY = "/xsd/cat/v1";

    // XSD's for the CatalogXmlValidator
    private List<String> xsdFiles = new ArrayList<>(Arrays.asList("Header.xsd", "Book.xsd", "Catalogue.xsd"));

    private Map<String,String> contextPaths = new HashMap<>();

    private Map<String, Class<?>> jaxbAssemblerMap = new HashMap<>();

    private Map<String, Class<?>> domainAssemblerMap = new HashMap<>();

    private CatalogConfiguration() {
        contextPaths.put("ARTISTS", "artist");
        contextPaths.put("BOOKCOLLECTION", "book");
        contextPaths.put("REFERENCEDATA", "reference");

//        jaxbAssemblerMap.put("ARTISTS", ArtistAssembler.class);
//        jaxbAssemblerMap.put("BOOKCOLLECTION", BookAssembler.class);
//        jaxbAssemblerMap.put("REFERENCEDATA", ReferenceDataAssembler.class);
//
//        domainAssemblerMap.put("ARTISTSDTO", ArtistAssembler.class);
//        domainAssemblerMap.put("BOOKSDTO", BookAssembler.class);
//        domainAssemblerMap.put("REFERENCEDATADTO", ReferenceDataAssembler.class);
    }

    @Override
    public String getXsdDirectory() {
        return XSD_DIRECTORY;
    }

    @Override
    public List<String> getXsdFiles() {
        return xsdFiles;
    }

    @Override
    public String getContextPath(String messageType) {
        return BASE_PACKAGE + contextPaths.get(messageType.toUpperCase());
    }

    @Override
    public Assembler getJaxbAssembler(Object jaxbObject) {
        String className = jaxbObject.getClass().getSimpleName().toUpperCase();
//        return (Assembler) Mappers.getMapper(jaxbAssemblerMap.get(className));
        return null;
    }

    @Override
    public Assembler getDomainAssembler(Object dto) {
        String className = dto.getClass().getSimpleName().toUpperCase();
//        return (Assembler) Mappers.getMapper(domainAssemblerMap.get(className));
        return null;
    }

}
