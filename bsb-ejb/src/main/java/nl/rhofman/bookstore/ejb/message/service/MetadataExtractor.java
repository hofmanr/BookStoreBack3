package nl.rhofman.bookstore.ejb.message.service;

import jakarta.enterprise.context.Dependent;
import nl.rhofman.bookstore.ejb.message.domain.MessageMetadata;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.xpath.*;
import java.io.StringReader;

@Dependent
public class MetadataExtractor {

    public MessageMetadata extractHeader(String xml) {
        InputSource inputXML = new InputSource(new StringReader(xml));
        XPath xPath = XPathFactory.newInstance().newXPath();
        MessageMetadata metadata = new MessageMetadata();

        try {
            Node node = (Node) xPath.evaluate("//*[local-name()='header']", inputXML, XPathConstants.NODE);
            if (node == null) {
                return metadata;
            }
            String messageSender = xPath.evaluate(".//*[local-name()='sender']", node);
            String messageRecipient = xPath.evaluate(".//*[local-name()='recipient']", node);
            String messageID = xPath.evaluate(".//*[local-name()='messageId']", node);
            String correlationID = xPath.evaluate(".//*[local-name()='correlationId']", node);
            String dateOfPreparation = xPath.evaluate(".//*[local-name()='dateOfPreparation']", node);

            metadata.setMessageSender(messageSender);
            metadata.setMessageRecipient(messageRecipient);
            metadata.setMessageID(messageID);
            metadata.setCorrelationID(correlationID);
//            metadata.setTimestamp(LocalDateTime.parse(timestamp));
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return metadata;
    }
}
