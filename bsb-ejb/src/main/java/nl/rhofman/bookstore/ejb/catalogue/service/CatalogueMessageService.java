package nl.rhofman.bookstore.ejb.catalogue.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.xml.Catalog;
import nl.rhofman.bookstore.ejb.xml.service.XmlValidationService;
import nl.rhofman.bookstore.ejb.message.service.MessageStoreService;

@Dependent
public class CatalogueMessageService extends MessageStoreService {

    @Inject
    public CatalogueMessageService(@Catalog XmlValidationService xmlValidationService) {
        this.xmlValidationService = xmlValidationService;
    }

    @Override
    public void processMessageReceived(String xmlMessage) {
        System.out.println("[MessageService] - Incoming catalog message");

        this.process(xmlMessage);
    }
}
