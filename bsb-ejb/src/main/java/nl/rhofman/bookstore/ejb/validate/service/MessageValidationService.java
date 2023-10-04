package nl.rhofman.bookstore.ejb.validate.service;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.message.domain.DomainTypeLiteral;
import nl.rhofman.bookstore.ejb.message.domain.Message;
import nl.rhofman.bookstore.ejb.message.event.MessageReceived;
import nl.rhofman.bookstore.ejb.message.event.MessageValidated;
import nl.rhofman.bookstore.ejb.validate.domain.Valid;
import nl.rhofman.bookstore.ejb.validate.domain.Invalid;
import nl.rhofman.bookstore.ejb.validate.domain.ValidationResult;

import java.util.List;

@Dependent
public class MessageValidationService {

    @Inject
    private ValidationService validationService;

    @Inject
    @Valid
    private Event<MessageValidated> validEvent;

    @Inject
    @Invalid
    private Event<MessageValidated> invalidEvent;

    public void processMessageReceivedEvent(@Observes MessageReceived messageReceived) {
        Message message = messageReceived.getMessage();
        Object domainObject = message.getDomainObject();
        System.out.println("Validate message " + (domainObject.getClass().getSimpleName()));
        List<ValidationResult> results = validationService.validate(domainObject);
        results.forEach(System.out::println);

        if (results.isEmpty() || results.stream().allMatch(ValidationResult::isValid)) {
            MessageValidated messageValid = new MessageValidated(message);
            validEvent.select(new DomainTypeLiteral(domainObject.getClass())).fire(messageValid);
        } else {
            MessageValidated messageValidated = new MessageValidated(message, results);
            invalidEvent.select(new DomainTypeLiteral(domainObject.getClass())).fire(messageValidated);
        }
    }

}
