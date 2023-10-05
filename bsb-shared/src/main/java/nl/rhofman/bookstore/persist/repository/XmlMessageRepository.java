package nl.rhofman.bookstore.persist.repository;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import nl.rhofman.bookstore.persist.BSB;
import nl.rhofman.bookstore.persist.model.XmlMessage;
import nl.rhofman.exception.domain.ExceptionOrigin;
import nl.rhofman.persist.repository.AbstractRepository;

import java.util.Collection;

@Dependent
public class XmlMessageRepository extends AbstractRepository<XmlMessage> {
    private static final ExceptionOrigin EXCEPTION_ORIGIN = new ExceptionOrigin(DATA_ORIGIN, "Data interaction with Message Entity");

    @Inject
    protected XmlMessageRepository(@BSB EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected ExceptionOrigin getExceptionOrigin() {
        return EXCEPTION_ORIGIN;
    }

    @Override
    public XmlMessage update(XmlMessage entity) {
        throw new UnsupportedOperationException("Message can not be updated");
    }

    @Override
    public Collection<XmlMessage> findAll() {
        throw new UnsupportedOperationException("It is not allowed to get all messages");
    }
}
