package nl.rhofman.bookstore.ejb.message.service;

import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.message.domain.Header;
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

    protected String getMessageType(String message) {
        return XmlUtil.getRootElementName(message);
    }

    protected Object getDomainObject(String messageType, String message) {
        // Validate XML against the schema
        xmlValidationService.validateXml(message);

        // Remove empty elements and leading and trailing spaces
        message = XmlUtil.normalizeXml(message);
        // Unmarshall XML message
        Object jaxbObject = jaxbService.unmarshall(messageType, message);
        // Transform to domain object
        return assemblerService.toDomain(jaxbObject);
    }

    protected Header getHeader(String message) {
        return new Header.HeaderBuilder(message).build();
    }

}
