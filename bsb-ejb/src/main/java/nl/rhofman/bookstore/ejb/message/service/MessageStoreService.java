package nl.rhofman.bookstore.ejb.message.service;

import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.message.dao.MessageBuilder;
import nl.rhofman.bookstore.ejb.xml.XmlUtil;
import nl.rhofman.bookstore.ejb.xml.service.AssemblerService;
import nl.rhofman.bookstore.ejb.xml.service.JaxbService;
import nl.rhofman.bookstore.ejb.xml.service.XmlValidationService;
import nl.rhofman.bookstore.ejb.message.event.MessageReceived;

public abstract class MessageStoreService {
    protected XmlValidationService xmlValidationService;
    protected JaxbService jaxbService;
    protected AssemblerService assemblerService;

    @Inject
    private MetadataExtractor metadataExtractor;

    @Inject
    private Event<MessageReceived> event;

    @Inject
    public MessageStoreService(XmlValidationService xmlValidationService,
                               JaxbService jaxbService,
                               AssemblerService assemblerService) {
        this.xmlValidationService = xmlValidationService;
        this.jaxbService = jaxbService;
        this.assemblerService = assemblerService;
    }

    protected abstract void processMessageReceived(String xmlMessage);

    protected void fireEvent(MessageReceived message) {
        event.fire(message);
    }

    protected MessageBuilder assembleMessage(String message) {
        // Validate XML against the schema
        xmlValidationService.validateXml(message);

        // Remove empty elements and leading and trailing spaces
        message = XmlUtil.normalizeXml(message);
        String messageType = XmlUtil.getRootElementName(message);
        // Unmarshall XML message
        Object jaxbObject = jaxbService.unmarshall(messageType, message);

        return new MessageBuilder()
                .withMessageMetadata(metadataExtractor.extractHeader(message))
                .withMessageType(messageType)
                .withDomainObject(assemblerService.toDomain(jaxbObject));
    }
}
