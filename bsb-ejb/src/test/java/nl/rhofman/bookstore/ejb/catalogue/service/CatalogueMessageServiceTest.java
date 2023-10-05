package nl.rhofman.bookstore.ejb.catalogue.service;

import jakarta.enterprise.event.Event;
import nl.rhofman.bookstore.ejb.catalogue.domain.Catalogue;
import nl.rhofman.bookstore.ejb.message.domain.MessageBuilder;
import nl.rhofman.bookstore.ejb.message.domain.Message;
import nl.rhofman.bookstore.ejb.message.domain.Header;
import nl.rhofman.bookstore.ejb.message.domain.MessageStub;
import nl.rhofman.bookstore.ejb.message.event.MessageReceived;
import nl.rhofman.bookstore.ejb.message.service.MessageService;
import nl.rhofman.bookstore.ejb.xml.Catalog;
import nl.rhofman.bookstore.persist.model.XmlMessage;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CatalogueMessageServiceTest {

    @Mock
    Event<MessageReceived> event;

    @Mock
    MessageService messageService;

    @Mock
    @Catalog
    MessageBuilder messageBuilder;

    @InjectMocks
    CatalogueMessageService catalogueMessageService;

    String catalogueMessage;
    Header header;

    @BeforeEach
    void init() throws IOException {
        ClassLoader classLoader = CatalogueMessageServiceTest.class.getClassLoader();

        File catalogue = new File(classLoader.getResource("Catalogue.xml").getFile());
        catalogueMessage = FileUtils.readFileToString(catalogue, "UTF-8");

        header = new Header.HeaderBuilder(catalogueMessage).build();
    }

    @Test
    void whenProcessMessageReceived() {
        // Prepare
        Catalogue catalogue = new Catalogue();
        catalogue.setBooks(Collections.emptyList());
        Message receivedMessage = new MessageStub("IN", catalogue, catalogueMessage, header);
        when(messageBuilder.incoming()).thenReturn(messageBuilder);
        when(messageBuilder.withXml(anyString())).thenReturn(messageBuilder);
        when(messageBuilder.build()).thenReturn(receivedMessage);
        XmlMessage storedXmlMessage = new XmlMessage();
        storedXmlMessage.setId(1L);

        // Execute
        catalogueMessageService.processMessageReceived(catalogueMessage);

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
//        Header messageHeader = eventMessage.getHeader();
//        assertThat(messageHeader, hasToString(header.toString()));
    }
}