package nl.rhofman.bookstore.ejb.message.dao;

import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBElement;
import nl.rhofman.bookstore.ejb.message.domain.*;
import nl.rhofman.bookstore.ejb.xml.XmlUtil;
import nl.rhofman.bookstore.ejb.xml.service.AssemblerService;
import nl.rhofman.bookstore.ejb.xml.service.JaxbService;
import nl.rhofman.bookstore.ejb.xml.service.XmlValidationService;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class MessageBuilder {

    @Inject
    @Any
    Instance<JmsDomainObject> jmsDomainObjectInstances;

    private XmlValidationService xmlValidationService;
    private JaxbService jaxbService;
    private AssemblerService assemblerService;
    private String direction;
    private Long parentId;

    protected String xml;
    protected DomainObject domainObject;
    private Message receivedMessage;

    public MessageBuilder(XmlValidationService xmlValidationService,
                          JaxbService jaxbService,
                          AssemblerService assemblerService) {
        this.xmlValidationService = xmlValidationService;
        this.jaxbService = jaxbService;
        this.assemblerService = assemblerService;
    }

    public MessageBuilder withXml(String xml) {
        this.xml = xml;
        this.domainObject = null;
        return this;
    }

    public MessageBuilder withDomainObject(DomainObject domainObject) {
        this.domainObject = domainObject;
        this.xml = null;
        return this;
    }

    public MessageBuilder withReceivedMessage(Message receivedMessage) {
        // Only store the reference (ID) of the message in the database
        this.parentId = receivedMessage.id();
        return this;
    }

    protected abstract JAXBElement getJaxbElement(Object jaxbObject);

    public MessageBuilder incoming() {
        this.direction = "IN";
        return this;
    }

    public MessageBuilder outgoing() {
        this.direction = "OUT";
        return this;
    }

    private Message xmlToMessage() {
        // Validate XML against the schema
        xmlValidationService.validateXml(xml);

        // Remove empty elements and leading and trailing spaces
        xml = XmlUtil.normalizeXml(xml);
        String messageType = XmlUtil.getRootElementName(xml);

        // Unmarshall XML message
        Object jaxbObject = jaxbService.unmarshall(messageType, xml);

        // Transform to domain object
        JmsDomainObject dto = assemblerService.toDomain(jaxbObject);

        return new Message(direction, dto, xml, parentId);
    }

    private Message dtoToMessage() {
        Instance<JmsDomainObject> jmsDomainObjectInstance = jmsDomainObjectInstances.select(new DomainTypeLiteral(domainObject.getClass()));
        JmsDomainObject dto = jmsDomainObjectInstance.get();
        dto.setDomainObject(domainObject);
        Header header = new Header();
        if (receivedMessage != null) {
            Header receivedMessageHeader = receivedMessage.header();
            header.setSender("BookStore");
            header.setRecipient(receivedMessageHeader.getSender());
            header.setMessageID(UUID.randomUUID().toString());
            header.setCorrelationID(receivedMessageHeader.getMessageID());
            header.setTimestamp(LocalDateTime.now());

            dto.withHeader(header);
        }

        // Construct xml
        Object jaxbObject = assemblerService.toMessage(dto);
        // Transform JAXB-object to (XML) string
        xml = jaxbService.marshall(getJaxbElement(jaxbObject));

        Message message = new Message(direction, dto, xml, parentId);

        // Because of a memory issue in CDI:
        jmsDomainObjectInstance.destroy(dto);

        return message;
    }

    private void validate() {
        if (direction == null) {
            new NullPointerException("Direction is mandatory");
        }
        if (xml == null || domainObject == null) {
            new NullPointerException("XML or DomainObject must be present");
        }
    }

    public Message build() {
        validate();
        return xml != null ? xmlToMessage() : dtoToMessage();
    }
}
