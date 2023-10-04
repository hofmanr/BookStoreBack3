package nl.rhofman.bookstore.ejb.message.domain;

import jakarta.xml.bind.JAXBElement;
import nl.rhofman.bookstore.ejb.xml.XmlUtil;
import nl.rhofman.bookstore.ejb.xml.service.AssemblerService;
import nl.rhofman.bookstore.ejb.xml.service.JaxbService;
import nl.rhofman.bookstore.ejb.xml.service.XmlValidationService;
import nl.rhofman.bookstore.persist.service.MessageService;

public abstract class MessageBuilder {

    private MessageService messageService;
    private XmlValidationService xmlValidationService;
    private JaxbService jaxbService;
    private AssemblerService assemblerService;
    private String direction;

    protected String xml;
    protected Object domainObject;

    public MessageBuilder(XmlValidationService xmlValidationService,
                          JaxbService jaxbService,
                          AssemblerService assemblerService,
                          MessageService messageService) {
        this.xmlValidationService = xmlValidationService;
        this.jaxbService = jaxbService;
        this.assemblerService = assemblerService;
        this.messageService = messageService;
    }

    public abstract MessageBuilder withXml(String xml);

    public abstract MessageBuilder withDomainObject(Object domainObject);

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
        Object dto = assemblerService.toDomain(jaxbObject);

        Header header = new Header.HeaderBuilder(xml).build();

        return new Message(messageService, direction, dto, xml, header);
    }

    private Message dtoToMessage() {
        // Construct xml
        Object jaxbObject = assemblerService.toMessage(domainObject);
        // Transform JAXB-object to (XML) string
        xml = jaxbService.marshall(getJaxbElement(jaxbObject));

        return new Message(messageService, direction, domainObject, xml, null);
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
