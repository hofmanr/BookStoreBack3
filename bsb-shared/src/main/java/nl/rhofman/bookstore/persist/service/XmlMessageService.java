package nl.rhofman.bookstore.persist.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import nl.rhofman.bookstore.persist.model.XmlMessage;
import nl.rhofman.bookstore.persist.model.Metadata;
import nl.rhofman.bookstore.persist.repository.MessageBuilder;
import nl.rhofman.bookstore.persist.repository.XmlMessageRepository;
import nl.rhofman.bookstore.persist.repository.MetadataRepository;
import nl.rhofman.persist.Service.AbstractService;

import static jakarta.transaction.Transactional.TxType.REQUIRED;
import static jakarta.transaction.Transactional.TxType.SUPPORTS;

@Dependent
@Transactional(SUPPORTS)
public class XmlMessageService extends AbstractService {
    @Inject
    private XmlMessageRepository xmlMessageRepository;
    @Inject
    private MetadataRepository metadataRepository;

    @Transactional(REQUIRED)
    public XmlMessage saveMessage(String xmlMessage) {
        XmlMessage message = new MessageBuilder(xmlMessage).build();
        return execute(() -> xmlMessageRepository.create(message), "Error creating a new Message in XmlMesssageService");
    }

    @Transactional(REQUIRED)
    public Metadata saveMetadata (Metadata metadata) {
        return execute(() -> metadataRepository.create(metadata), "Error creating metadata in XmlMessageService");
    }


}
