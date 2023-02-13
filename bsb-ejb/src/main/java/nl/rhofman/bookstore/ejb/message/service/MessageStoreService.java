package nl.rhofman.bookstore.ejb.message.service;

import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.xml.service.XmlValidationService;
import nl.rhofman.bookstore.ejb.message.domain.MessageMetadata;
import nl.rhofman.bookstore.ejb.message.event.MessageReceived;

public abstract class MessageStoreService {
    protected XmlValidationService xmlValidationService;

    @Inject
    private MetadataExtractor metadataExtractor;

    @Inject
    private Event<MessageReceived> event;

    protected abstract void processMessageReceived(String xmlMessage);

    protected void process(String message) {
        // 1. Validate XML
        xmlValidationService.validateXml(message);

        // 2. Get message headerDTO
        MessageMetadata metadata = metadataExtractor.extractHeader(message);
        System.out.println("Metadata" + metadata.toString());

        Long messageID = 1L;
        Object domainObject = null;

//        // 3. Save message
//        Long messageID = messageService.saveMessage(message);
//        System.out.println("[MessageService] - MessageID: " + messageID);
//
//        // 4. Unmarshall
//        message = XmlUtil.normalizeXml(message);  // Remove empty elements and leading and trailing spaces
//        Object jaxbObject = jaxbService.unmarshall(message);
//
//        // 5. Convert to internal message
//        Object domainObject = assemblerService.toDomain(jaxbObject);
//        System.out.println("Domain object: " + domainObject.getClass());

        // 6. Send event
        MessageReceived messageReceived = new MessageReceived(messageID, metadata, domainObject);
        event.fire(messageReceived);
    }
}
