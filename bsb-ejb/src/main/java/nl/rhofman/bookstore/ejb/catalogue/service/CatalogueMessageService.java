package nl.rhofman.bookstore.ejb.catalogue.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.message.domain.Catalogue;
import nl.rhofman.bookstore.ejb.xml.Catalog;
import nl.rhofman.bookstore.ejb.xml.service.AssemblerService;
import nl.rhofman.bookstore.ejb.xml.service.JaxbService;
import nl.rhofman.bookstore.ejb.xml.service.XmlValidationService;
import nl.rhofman.bookstore.ejb.message.service.MessageStoreService;

@Dependent
public class CatalogueMessageService extends MessageStoreService<Catalogue> {

    @Inject
    public CatalogueMessageService(@Catalog XmlValidationService xmlValidationService,
                                   @Catalog JaxbService jaxbService,
                                   @Catalog AssemblerService assemblerService) {
        this.xmlValidationService = xmlValidationService;
        this.jaxbService = jaxbService;
        this.assemblerService = assemblerService;
    }

    @Override
    public void processMessageReceived(String xmlMessage) {
        System.out.println("[MessageService] - Incoming catalog message");

        Catalogue catalogue = this.extract(xmlMessage);
        System.out.println("[MessageService] - Number of books: " + (catalogue == null ? "Unknown" : catalogue.getBooks().size()));
    }
}
