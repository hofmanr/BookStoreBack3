package nl.rhofman.bookstore.ejb.catalogue.dao;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBElement;
import nl.rhofman.bookstore.ejb.message.dao.MessageBuilder;
import nl.rhofman.bookstore.ejb.xml.Catalog;
import nl.rhofman.bookstore.ejb.xml.service.AssemblerService;
import nl.rhofman.bookstore.ejb.xml.service.JaxbService;
import nl.rhofman.bookstore.ejb.xml.service.XmlValidationService;
import nl.rhofman.bookstore.jaxb.v1.catalogue.CatalogueType;
import nl.rhofman.bookstore.jaxb.v1.catalogue.ObjectFactory;
import nl.rhofman.bookstore.jaxb.v1.catalogue.ConfirmationType;

@Dependent
public class CatalogueMessageBuilder extends MessageBuilder {

    @Inject
    public CatalogueMessageBuilder(@Catalog XmlValidationService xmlValidationService,
                                   @Catalog JaxbService jaxbService,
                                   @Catalog AssemblerService assemblerService) {
        super(xmlValidationService, jaxbService, assemblerService);
    }

    public CatalogueMessageBuilder withXml(String xml) {
        this.xml = xml;
        this.domainObject = null;
        return this;
    }

    public CatalogueMessageBuilder withDomainObject(Object domainObject) {
        this.domainObject = domainObject;
        this.xml = null;
        return this;
    }

    @Override
    protected JAXBElement getJaxbElement(Object jaxbObject) {
        ObjectFactory factory = new ObjectFactory();
        return (jaxbObject instanceof CatalogueType) ?
                factory.createConfirmation((ConfirmationType)jaxbObject) :
                factory.createCatalogue((CatalogueType) jaxbObject);
    }

}
