package nl.rhofman.bookstore.ejb.catalogue.service;

import jakarta.enterprise.event.Event;
import nl.rhofman.bookstore.ejb.catalogue.domain.Catalogue;
import nl.rhofman.bookstore.ejb.catalogue.domain.JmsCatalogue;
import nl.rhofman.bookstore.ejb.message.domain.Message;
import nl.rhofman.bookstore.ejb.message.domain.MessageStub;
import nl.rhofman.bookstore.ejb.message.event.MessageReceived;
import nl.rhofman.bookstore.ejb.message.service.MessageService;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.Matchers.hasToString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CatalogueMessageServiceTest {

    @Mock
    Event<MessageReceived> event;

    @Mock
    MessageService messageService;

    @InjectMocks
    CatalogueMessageService catalogueMessageService;

    String catalogueMessage;

    @BeforeEach
    void init() throws IOException {
        ClassLoader classLoader = CatalogueMessageServiceTest.class.getClassLoader();

        File catalogue = new File(classLoader.getResource("Catalogue.xml").getFile());
        catalogueMessage = FileUtils.readFileToString(catalogue, "UTF-8");
    }

    @Test
    void whenProcessMessageReceived() {
        // Prepare
        Catalogue catalogue = new Catalogue();
        catalogue.setBooks(Collections.emptyList());
        JmsCatalogue jmsCatalogue = new JmsCatalogue();
        jmsCatalogue.setDomainObject(catalogue);
        Message receivedMessage = new MessageStub("IN", jmsCatalogue, catalogueMessage);

        // Execute
        catalogueMessageService.processMessageReceived(receivedMessage);

        // Verify
        verify(messageService).persist(any(Message.class));

        ArgumentCaptor<MessageReceived> captor = ArgumentCaptor.forClass(MessageReceived.class);
        verify(event).fire(captor.capture());
        MessageReceived messageReceived = captor.getValue();
        assertNotNull(messageReceived);
        Message eventMessage = messageReceived.getMessage();
        assertThat(eventMessage.getDomainObject().getClass().getSimpleName(), is("Catalogue"));
        Catalogue messageCatalogue = eventMessage.getDomainObject();
        assertThat(messageCatalogue.getBooks().size(), is(0));
    }
}