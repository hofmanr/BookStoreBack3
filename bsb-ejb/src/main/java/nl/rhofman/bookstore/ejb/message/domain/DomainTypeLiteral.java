package nl.rhofman.bookstore.ejb.message.domain;


import jakarta.enterprise.util.AnnotationLiteral;

public class DomainTypeLiteral extends AnnotationLiteral<DomainType> implements DomainType {
    private final Class<?> messageType;

    public DomainTypeLiteral(Class<?> messageType) {
        this.messageType = messageType;
    }

    @Override
    public Class<?> value() {
        return messageType;
    }
}
