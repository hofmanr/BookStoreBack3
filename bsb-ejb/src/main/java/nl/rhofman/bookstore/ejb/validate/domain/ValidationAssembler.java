package nl.rhofman.bookstore.ejb.validate.domain;

import nl.rhofman.bookstore.ejb.validate.repository.Validator;

import java.util.List;

public interface ValidationAssembler<T> {

    List<Validator> map(T object);

}
