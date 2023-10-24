package nl.rhofman.bookstore.ejb.catalogue.facade;

import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import nl.rhofman.bookstore.ejb.catalogue.domain.JmsCatalogue;
import nl.rhofman.bookstore.ejb.catalogue.service.CatalogueMessageService;
import nl.rhofman.bookstore.ejb.message.domain.Message;
import nl.rhofman.bookstore.ejb.message.dao.MessageBuilder;
import nl.rhofman.bookstore.ejb.message.domain.MessageStub;
import nl.rhofman.bookstore.ejb.xml.Catalog;
import nl.rhofman.exception.dao.ServiceException;
import nl.rhofman.exception.domain.ExceptionOrigin;
import nl.rhofman.exception.domain.ExceptionReason;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatalogueMessageListenerTest {

    @Mock
    private TextMessage textMessage;

    @Mock
    @Catalog
    MessageBuilder messageBuilder;

    @Mock
    private CatalogueMessageService messageService;

    @InjectMocks
    private CatalogueMessageListener catalogueMessageListener;

    @Test
    void whenHandlingValidMessage() throws JMSException {
        String xml = "<tag>Hello World</tag>";
        doReturn(xml).when(textMessage).getText();
        JmsCatalogue jmsCatalogue = new JmsCatalogue();
        Message receivedMessage = new MessageStub("IN", jmsCatalogue, xml);
        when(messageBuilder.incoming()).thenReturn(messageBuilder);
        when(messageBuilder.withXml(anyString())).thenReturn(messageBuilder);
        when(messageBuilder.build()).thenReturn(receivedMessage);


        catalogueMessageListener.onMessage(textMessage);

        verify(messageService).processMessageReceived(receivedMessage);
    }

    @Test
    void whenHandlingServiceException() throws JMSException {
        ServiceException serviceException = new ServiceException(new ExceptionOrigin("ORIGIN", "My Origin"), new ExceptionReason("REASON", "My Reason"), "Error message");
        doThrow(serviceException).when(textMessage).getText();

        catalogueMessageListener.onMessage(textMessage);

        verify(messageService, never()).processMessageReceived(any(Message.class));
    }

}