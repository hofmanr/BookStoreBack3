package nl.rhofman.bookstore.ejb.validate.domain;


import jakarta.enterprise.util.AnnotationLiteral;

public class ValidationMessageLiteral extends AnnotationLiteral<ValidationMessage> implements ValidationMessage {
    final Class<?> expectedMessage;

    public ValidationMessageLiteral(Class<?> expectedMessage) {
        this.expectedMessage = expectedMessage;
    }

    @Override
    public Class<?> value() {
        return expectedMessage;
    }
}
