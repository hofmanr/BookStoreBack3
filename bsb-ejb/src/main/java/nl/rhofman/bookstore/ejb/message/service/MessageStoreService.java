package nl.rhofman.bookstore.ejb.message.service;

import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.xml.XmlUtil;
import nl.rhofman.bookstore.ejb.xml.service.AssemblerService;
import nl.rhofman.bookstore.ejb.xml.service.JaxbService;
import nl.rhofman.bookstore.ejb.xml.service.XmlValidationService;
import nl.rhofman.bookstore.ejb.message.domain.MessageMetadata;
import nl.rhofman.bookstore.ejb.message.event.MessageReceived;

public abstract class MessageStoreService<T> {
    protected XmlValidationService xmlValidationService;
    protected JaxbService jaxbService;
    protected AssemblerService assemblerService;

    @Inject
    private MetadataExtractor metadataExtractor;

    @Inject
    private Event<MessageReceived> event;

    private MessageMetadata metadata;
    private Long messageID;

    protected abstract void processMessageReceived(String xmlMessage);

    protected T extract(String message) {
        // 1. Validate XML against the schema
        xmlValidationService.validateXml(message);

        // 2. Get message header
        metadata = metadataExtractor.extractHeader(message);
        System.out.println("Metadata" + metadata.toString());

        // 3. Unmarshall
        message = XmlUtil.normalizeXml(message);  // Remove empty elements and leading and trailing spaces
        Object jaxbObject = jaxbService.unmarshall(message);

        // 4. Convert to internal message
        return assemblerService.toDomain(jaxbObject);
    }

    protected void storeMessage(T domainObject) {
        // TODO
        messageID = 1L;
//        Long messageID = messageService.saveMessage(message);
//        System.out.println("[MessageService] - MessageID: " + messageID);
    }

    protected void nextStep(T domainObject) {
        MessageReceived messageReceived = new MessageReceived(messageID, metadata, domainObject);
        event.fire(messageReceived);
    }
}
