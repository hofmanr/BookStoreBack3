package nl.rhofman.bookstore.ejb.message.dao;

import jakarta.xml.bind.JAXBElement;
import nl.rhofman.bookstore.ejb.message.domain.Header;
import nl.rhofman.bookstore.ejb.message.domain.Message;
import nl.rhofman.bookstore.ejb.xml.XmlUtil;
import nl.rhofman.bookstore.ejb.xml.service.AssemblerService;
import nl.rhofman.bookstore.ejb.xml.service.JaxbService;
import nl.rhofman.bookstore.ejb.xml.service.XmlValidationService;

public abstract class MessageBuilder {

    private XmlValidationService xmlValidationService;
    private JaxbService jaxbService;
    private AssemblerService assemblerService;

    protected String xml;
    protected Object domainObject;

    public MessageBuilder(XmlValidationService xmlValidationService, JaxbService jaxbService, AssemblerService assemblerService) {
        this.xmlValidationService = xmlValidationService;
        this.jaxbService = jaxbService;
        this.assemblerService = assemblerService;
    }

    protected abstract MessageBuilder withXml(String xml);

    protected abstract MessageBuilder withDomainObject(Object domainObject);

    protected abstract JAXBElement getJaxbElement(Object jaxbObject);

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

        return new Message(dto, xml, header);
    }

    private Message dtoToMessage() {
        // Construct xml
        Object jaxbObject = assemblerService.toMessage(domainObject);
        // Transform JAXB-object to (XML) string
        xml = jaxbService.marshall(getJaxbElement(jaxbObject));

        return new Message(domainObject, xml, null);
    }

    public Message build() {
        return xml != null ? xmlToMessage() : dtoToMessage();
    }
}
