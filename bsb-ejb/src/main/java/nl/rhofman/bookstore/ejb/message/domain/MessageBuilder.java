package nl.rhofman.bookstore.ejb.message.domain;

import jakarta.xml.bind.JAXBElement;
import nl.rhofman.bookstore.ejb.xml.XmlUtil;
import nl.rhofman.bookstore.ejb.xml.service.AssemblerService;
import nl.rhofman.bookstore.ejb.xml.service.JaxbService;
import nl.rhofman.bookstore.ejb.xml.service.XmlValidationService;

public abstract class MessageBuilder {

    private XmlValidationService xmlValidationService;
    private JaxbService jaxbService;
    private AssemblerService assemblerService;
    private String direction;
    private Long parentId;

    protected String xml;
    protected DomainObject domainObject;

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
        this.parentId = receivedMessage.parentID();
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
        DomainObject dto = assemblerService.toDomain(jaxbObject);

        return new Message(direction, dto, xml, parentId);
    }

    private Message dtoToMessage() {
        // Construct xml
        Object jaxbObject = assemblerService.toMessage(domainObject);
        // Transform JAXB-object to (XML) string
        xml = jaxbService.marshall(getJaxbElement(jaxbObject));

        return new Message(direction, domainObject, xml, parentId);
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
