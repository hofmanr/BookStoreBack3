package nl.rhofman.bookstore.persist.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import nl.rhofman.bookstore.persist.model.Message;
import nl.rhofman.bookstore.persist.model.Metadata;
import nl.rhofman.bookstore.persist.repository.MessageBuilder;
import nl.rhofman.bookstore.persist.repository.MessageRepository;
import nl.rhofman.bookstore.persist.repository.MetadataRepository;
import nl.rhofman.persist.Service.AbstractService;

import static jakarta.transaction.Transactional.TxType.REQUIRED;
import static jakarta.transaction.Transactional.TxType.SUPPORTS;

@Dependent
@Transactional(SUPPORTS)
public class MessageService extends AbstractService {
    @Inject
    private MessageRepository messageRepository;
    @Inject
    private MetadataRepository metadataRepository;

    @Transactional(REQUIRED)
    public Message saveMessage(String xmlMessage) {
        Message message = new MessageBuilder(xmlMessage).build();
        return execute(() -> messageRepository.create(message), "Error creating a new Message in MesssageService");
    }

    @Transactional(REQUIRED)
    public Metadata saveMetadata (Metadata metadata) {
        return execute(() -> metadataRepository.create(metadata), "Error creating metadata in MessageService");
    }


}
