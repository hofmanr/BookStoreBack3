package nl.rhofman.bookstore.ejb.validate.domain;

import jakarta.inject.Qualifier;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Qualifier
public @interface ValidationMessage {
    Class<?> value();
}
