package nl.rhofman.bookstore.ejb.receive.service;

import jakarta.enterprise.event.Event;
import nl.rhofman.bookstore.ejb.message.domain.Catalogue;
import nl.rhofman.bookstore.ejb.message.domain.Header;
import nl.rhofman.bookstore.ejb.message.domain.HeaderStub;
import nl.rhofman.bookstore.ejb.message.event.MessageReceived;
import nl.rhofman.bookstore.ejb.message.service.HeaderExtractor;
import nl.rhofman.bookstore.ejb.xml.Catalog;
import nl.rhofman.bookstore.ejb.xml.service.AssemblerService;
import nl.rhofman.bookstore.ejb.xml.service.JaxbService;
import nl.rhofman.bookstore.ejb.xml.service.XmlValidationService;
import nl.rhofman.bookstore.persist.model.Message;
import nl.rhofman.bookstore.persist.service.MessageService;
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
import java.time.LocalDateTime;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CatalogueMessageServiceTest {

    @Mock
    Event<MessageReceived> event;

    @Mock
    HeaderExtractor headerExtractor;

    @Mock
    MessageService messageService;

    @Mock
    @Catalog
    XmlValidationService xmlValidationService;

    @Mock
    @Catalog
    JaxbService jaxbService;

    @Mock
    @Catalog
    AssemblerService assemblerService;

    @InjectMocks
    CatalogueMessageService catalogueMessageService =
            new CatalogueMessageService(xmlValidationService, jaxbService, assemblerService);

    String catalogueMessage;
    Header header;

    @BeforeEach
    void init() throws IOException {
        ClassLoader classLoader = CatalogueMessageServiceTest.class.getClassLoader();

        File catalogue = new File(classLoader.getResource("Catalogue.xml").getFile());
        catalogueMessage = FileUtils.readFileToString(catalogue, "UTF-8");

        header = new HeaderStub();
    }

    @Test
    void whenProcessMessageReceived() {
        // Prepare
        Message storedMessage = new Message(LocalDateTime.now(), catalogueMessage);
        storedMessage.setId(1L);
        Catalogue catalogue = new Catalogue();
        catalogue.setBooks(Collections.emptyList());

        when(headerExtractor.extract(anyString())).thenReturn(header);
        when(messageService.saveMessage(anyString())).thenReturn(storedMessage);
        when(assemblerService.toDomain(any())).thenReturn(catalogue);

        // Execute
        catalogueMessageService.processMessageReceived(catalogueMessage);

        // Verify
        verify(xmlValidationService).validateXml(anyString());
        verify(jaxbService).unmarshall(anyString(), anyString());

        ArgumentCaptor<MessageReceived> captor = ArgumentCaptor.forClass(MessageReceived.class);
        verify(event).fire(captor.capture());
        MessageReceived messageReceived = captor.getValue();
        assertNotNull(messageReceived);
        assertThat(messageReceived.getMessageID(), is(1L));
        assertThat(messageReceived.getMessageType(), is("Catalogue"));
        assertThat(messageReceived.getDomainObject().getClass().getSimpleName(), is("Catalogue"));
        Catalogue messageCatalogue = (Catalogue) messageReceived.getDomainObject();
        assertThat(messageCatalogue.getBooks().size(), is(0));
        Header messageHeader = messageReceived.getHeader();
        assertThat(messageHeader, is(header));
    }
}