package nl.rhofman.bookstore.persist.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import nl.rhofman.bookstore.persist.model.Publisher;
import nl.rhofman.bookstore.persist.repository.PublisherRepository;
import nl.rhofman.persist.Service.AbstractService;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;

@Dependent
@Transactional(SUPPORTS)
public class PublisherService extends AbstractService {

    @Inject
    private PublisherRepository publisherRepository;

    public Publisher getReference(@NotNull Long id) {
        return execute(() -> publisherRepository.getReference(id), "Error getting Publisher by ID");
    }

    public Publisher findByName(String name) {
        return execute(() -> publisherRepository.findByName(name), "Error getting Publisher by Name");
    }

    public Publisher create(Publisher publisher) {
        return execute(() -> publisherRepository.create(publisher), "Error creating new Publisher");
    }
}
