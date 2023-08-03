package nl.rhofman.bookstore.persist.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import nl.rhofman.bookstore.persist.model.Message;
import nl.rhofman.bookstore.persist.repository.MessageRepository;
import nl.rhofman.persist.Service.AbstractService;

import static jakarta.transaction.Transactional.TxType.REQUIRED;
import static jakarta.transaction.Transactional.TxType.SUPPORTS;

@Dependent
@Transactional(SUPPORTS)
public class MessageService extends AbstractService {
    @Inject
    private MessageRepository messageRepository;

    @Transactional(REQUIRED)
    public Message saveMessage(String message) {
        return execute(() -> messageRepository.saveMessage(message), "Error creating a new Book in BookService");
    }
}
