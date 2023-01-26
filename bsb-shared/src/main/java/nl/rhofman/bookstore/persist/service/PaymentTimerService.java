package nl.rhofman.bookstore.persist.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import nl.rhofman.bookstore.persist.model.PaymentTimer;
import nl.rhofman.bookstore.persist.repository.PaymentTimerRepository;
import nl.rhofman.persist.Service.AbstractService;

import java.util.List;

import static jakarta.transaction.Transactional.TxType.REQUIRED;
import static jakarta.transaction.Transactional.TxType.SUPPORTS;

@Dependent
@Transactional(SUPPORTS)
public class PaymentTimerService extends AbstractService {
    @Inject
    private PaymentTimerRepository paymentTimerRepository;

    @Transactional(REQUIRED)
    public PaymentTimer create(PaymentTimer paymentTimer) {
        return execute(() -> paymentTimerRepository.create(paymentTimer), "Error creating a new PaymentTimer");
    }

    public List<PaymentTimer> getExpiredTimers() {
        return execute(() -> paymentTimerRepository.findExpiredTimers(), "Could not get expired PaymentTimers");
    }

    @Transactional(REQUIRED)
    public void deleteTimer(PaymentTimer paymentTimer) {
        execute(() -> paymentTimerRepository.remove(paymentTimer), "Unable to delete PaymentTimer");
    }

}
