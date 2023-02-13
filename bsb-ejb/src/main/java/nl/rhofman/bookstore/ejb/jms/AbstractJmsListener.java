package nl.rhofman.bookstore.ejb.jms;

import jakarta.jms.*;
import nl.rhofman.exception.dao.ServiceException;
import nl.rhofman.exception.domain.ExceptionOrigin;

import java.nio.charset.StandardCharsets;

public abstract class AbstractJmsListener implements MessageListener {
    public static final String DATA_ORIGIN = "MLR";
    private static final ExceptionOrigin EXCEPTION_ORIGIN = new ExceptionOrigin(DATA_ORIGIN, "Error receiving message");

    protected String getPayload(Message msg) {
        try {
            if (msg instanceof TextMessage) {
                return ((TextMessage) msg).getText();
            }
            if (msg instanceof BytesMessage) {
                BytesMessage bytesMessage = (BytesMessage) msg;
                byte[] data = new byte[(int) bytesMessage.getBodyLength()];
                return new String(data, StandardCharsets.UTF_16);
            }
        } catch (JMSException ex) {
            throw new ServiceException(EXCEPTION_ORIGIN, JmsExceptionReason.GENERAL_EXCEPTION, "Error getting JMS message");
        }
        throw new ServiceException(EXCEPTION_ORIGIN, JmsExceptionReason.UNSUPPORTED_MESSAGE_TYPE, "Message type not supported");
    }

}
