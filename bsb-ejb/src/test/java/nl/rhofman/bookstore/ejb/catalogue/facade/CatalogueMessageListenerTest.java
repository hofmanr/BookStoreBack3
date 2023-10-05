package nl.rhofman.bookstore.ejb.catalogue.facade;

import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import nl.rhofman.bookstore.ejb.catalogue.service.CatalogueMessageService;
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
    private CatalogueMessageService messageService;

    @InjectMocks
    private CatalogueMessageListener catalogueMessageListener;

    @Test
    void whenHandlingValidMessage() throws JMSException {
        doReturn("<tag>Hello World</tag>").when(textMessage).getText();
        catalogueMessageListener.onMessage(textMessage);

        verify(messageService).processMessageReceived("<tag>Hello World</tag>");
    }

    @Test
    void whenHandlingServiceException() throws JMSException {
        ServiceException serviceException = new ServiceException(new ExceptionOrigin("ORIGIN", "My Origin"), new ExceptionReason("REASON", "My Reason"), "Error message");
        doThrow(serviceException).when(textMessage).getText();

        catalogueMessageListener.onMessage(textMessage);

        verify(messageService, never()).processMessageReceived(anyString());
    }

}