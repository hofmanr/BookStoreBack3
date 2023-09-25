package nl.rhofman.bookstore.ejb.message.domain;

import nl.rhofman.exception.dao.ServiceException;
import nl.rhofman.exception.domain.ExceptionOrigin;
import nl.rhofman.exception.domain.ExceptionReason;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Header {
    private String messageSender;
    private String messageRecipient;
    private String messageID;
    private String correlationID;
    private LocalDateTime timestamp;

    public Header() {
    }

    public String getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(String messageSender) {
        this.messageSender = messageSender;
    }

    public String getMessageRecipient() {
        return messageRecipient;
    }

    public void setMessageRecipient(String messageRecipient) {
        this.messageRecipient = messageRecipient;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getCorrelationID() {
        return correlationID;
    }

    public void setCorrelationID(String correlationID) {
        this.correlationID = correlationID;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public static class HeaderBuilder {
        private static final ExceptionReason EXCEPTION_REASON = new ExceptionReason("XPT", "XPath expression failed");
        private static final ExceptionOrigin EXCEPTION_ORIGIN =
                new ExceptionOrigin("EHR", "Extract Header");

        private String messageSender;
        private String messageRecipient;
        private String messageID;
        private String correlationID;
        private LocalDateTime timestamp;

        private InputSource inputXML;
        private XPath xPath;

        public HeaderBuilder(String xml) {
            inputXML = new InputSource(new StringReader(xml));
            xPath = XPathFactory.newInstance().newXPath();
        }

        private String nullValue(String value) {
            return isNullOrEmpty(value) ? null : value;
        }

        private boolean isNullOrEmpty(String value) {
            return Objects.isNull(value) || value.isEmpty();
        }

        private void extractFields() throws XPathExpressionException {
            Node node = (Node) xPath.evaluate("//*[local-name()='header']", inputXML, XPathConstants.NODE);
            if (node == null) {
                return;
            }
            messageSender = xPath.evaluate(".//*[local-name()='sender']", node);
            messageRecipient = xPath.evaluate(".//*[local-name()='recipient']", node);
            messageID = xPath.evaluate(".//*[local-name()='messageId']", node);
            correlationID = xPath.evaluate(".//*[local-name()='correlationId']", node);
            String dateOfPreparation = xPath.evaluate(".//*[local-name()='dateOfPreparation']", node);
            String timeOfPreparation = xPath.evaluate(".//*[local-name()='timeOfPreparation']", node);

            LocalDate date = isNullOrEmpty(dateOfPreparation) ? null : LocalDate.parse(dateOfPreparation);
            LocalTime time = isNullOrEmpty(timeOfPreparation) ? null : LocalTime.parse(timeOfPreparation);
            if (date != null) {
                if (time != null) {
                    timestamp = LocalDateTime.of(date, time);
                } else {
                    timestamp = LocalDateTime.of(date, LocalTime.MIDNIGHT);
                }
            }
        }

        public Header build() {
            Header header = new Header();

            if (inputXML != null && xPath != null) {
                try {
                    extractFields();
                } catch (XPathExpressionException e) {
                    throw new ServiceException(EXCEPTION_ORIGIN, EXCEPTION_REASON, e.getMessage(), e);
                }
            }

            header.setMessageSender(nullValue(messageSender));
            header.setMessageRecipient(nullValue(messageRecipient));
            header.setMessageID(nullValue(messageID));
            header.setCorrelationID(nullValue(correlationID));
            header.setTimestamp(timestamp);

            return header;
        }
    }

    @Override
    public String toString() {
        return "MessageMetadata{" +
                "messageSender='" + messageSender + '\'' +
                ", messageRecipient='" + messageRecipient + '\'' +
                ", messageID='" + messageID + '\'' +
                ", correlationID='" + correlationID + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
