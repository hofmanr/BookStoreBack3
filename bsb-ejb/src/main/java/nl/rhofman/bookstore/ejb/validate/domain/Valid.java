package nl.rhofman.bookstore.ejb.validate.domain;

import jakarta.inject.Qualifier;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Qualifier
public @interface Valid {
}
