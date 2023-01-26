package nl.rhofman.bookstore.persist.repository;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import nl.rhofman.bookstore.persist.BSB;
import nl.rhofman.bookstore.persist.model.PaymentTimer;
import nl.rhofman.bookstore.persist.model.Timer;
import nl.rhofman.exception.domain.ExceptionOrigin;
import nl.rhofman.persist.repository.AbstractRepository;

import java.util.List;

@Dependent
public class PaymentTimerRepository extends AbstractRepository<PaymentTimer>  {
    private static final ExceptionOrigin EXCEPTION_ORIGIN = new ExceptionOrigin(DATA_ORIGIN, "Data interaction with PaymentTimer Entity");

    @Inject
    protected PaymentTimerRepository(@BSB EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected ExceptionOrigin getExceptionOrigin() {
        return EXCEPTION_ORIGIN;
    }

    public List<PaymentTimer> findExpiredTimers() {
        TypedQuery<PaymentTimer> query = em.createNamedQuery(Timer.FIND_EXPIRED, PaymentTimer.class);
        return execute(() -> query.getResultList());
    }

}
