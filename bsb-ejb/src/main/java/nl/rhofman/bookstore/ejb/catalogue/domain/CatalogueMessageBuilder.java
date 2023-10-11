package nl.rhofman.bookstore.ejb.catalogue.domain;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBElement;
import nl.rhofman.bookstore.ejb.message.domain.MessageBuilder;
import nl.rhofman.bookstore.ejb.xml.Catalog;
import nl.rhofman.bookstore.ejb.xml.service.AssemblerService;
import nl.rhofman.bookstore.ejb.xml.service.JaxbService;
import nl.rhofman.bookstore.ejb.xml.service.XmlValidationService;
import nl.rhofman.bookstore.jaxb.v1.catalogue.CatalogueType;
import nl.rhofman.bookstore.jaxb.v1.catalogue.ObjectFactory;
import nl.rhofman.bookstore.jaxb.v1.catalogue.ConfirmationType;

@Dependent
@Catalog
public class CatalogueMessageBuilder extends MessageBuilder {

    @Inject
    public CatalogueMessageBuilder(@Catalog XmlValidationService xmlValidationService,
                                   @Catalog JaxbService jaxbService,
                                   @Catalog AssemblerService assemblerService) {
        super(xmlValidationService, jaxbService, assemblerService);
    }

    @Override
    protected JAXBElement getJaxbElement(Object jaxbObject) {
        ObjectFactory factory = new ObjectFactory();
        return (jaxbObject instanceof CatalogueType) ?
                factory.createCatalogue((CatalogueType) jaxbObject) :
                factory.createConfirmation((ConfirmationType) jaxbObject);
    }

}
