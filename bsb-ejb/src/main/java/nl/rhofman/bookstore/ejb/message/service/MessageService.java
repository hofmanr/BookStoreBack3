package nl.rhofman.bookstore.ejb.message.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.message.domain.Message;
import nl.rhofman.bookstore.persist.model.Metadata;
import nl.rhofman.bookstore.persist.model.XmlMessage;
import nl.rhofman.bookstore.persist.service.XmlMessageService;

@Dependent
public class MessageService {

    @Inject
    XmlMessageService xmlMessageService;

    public void persist(Message message) {
        if (message.isMessageStored()) {
            return; // the message is already stored (idempotent)
        }

        XmlMessage storedXmlMessage = xmlMessageService.saveMessage(message.getXml());
        message.storedWithID(storedXmlMessage.getId());
        Metadata metadata = message.constructMetadata();
        // Store the message metadata
        xmlMessageService.saveMetadata(metadata);
    }

}
