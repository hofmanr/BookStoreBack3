package nl.rhofman.bookstore.ejb.message.service;

import jakarta.enterprise.context.Dependent;
import nl.rhofman.bookstore.ejb.message.domain.Metadata;
import nl.rhofman.exception.dao.ServiceException;
import nl.rhofman.exception.domain.ExceptionOrigin;
import nl.rhofman.exception.domain.ExceptionReason;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.xpath.*;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Dependent
public class MetadataExtractor {
    private static final ExceptionReason EXCEPTION_REASON = new ExceptionReason("XPT", "XPath expression failed");
    private static final ExceptionOrigin EXCEPTION_ORIGIN =
            new ExceptionOrigin("EMD", "Extract Metadata");

    private InputSource inputXML;
    private XPath xPath;

    public Metadata extractHeader(String xml) {
        inputXML = new InputSource(new StringReader(xml));
        xPath = XPathFactory.newInstance().newXPath();

        try {
            return getMetadata();
        } catch (XPathExpressionException e) {
            throw new ServiceException(EXCEPTION_ORIGIN, EXCEPTION_REASON, e.getMessage(), e);
        }
    }

    private Metadata getMetadata() throws XPathExpressionException {
        Metadata metadata = new Metadata();

        Node node = (Node) xPath.evaluate("//*[local-name()='header']", inputXML, XPathConstants.NODE);
        if (node == null) {
            return metadata;
        }
        String messageSender = xPath.evaluate(".//*[local-name()='sender']", node);
        String messageRecipient = xPath.evaluate(".//*[local-name()='recipient']", node);
        String messageID = xPath.evaluate(".//*[local-name()='messageId']", node);
        String correlationID = xPath.evaluate(".//*[local-name()='correlationId']", node);
        String dateOfPreparation = xPath.evaluate(".//*[local-name()='dateOfPreparation']", node);
        String timeOfPreparation = xPath.evaluate(".//*[local-name()='timeOfPreparation']", node);

        metadata.setMessageSender(messageSender);
        metadata.setMessageRecipient(messageRecipient);
        metadata.setMessageID(messageID);
        metadata.setCorrelationID(correlationID);
        LocalDate date = LocalDate.parse(dateOfPreparation);
        LocalTime time = LocalTime.parse(timeOfPreparation);
        metadata.setTimestamp(LocalDateTime.of(date,time));

        return metadata;
    }
}
