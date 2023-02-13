package nl.rhofman.bookstore.ejb.xml.dao;

public interface Assembler<T, U> {
    T toDomain(U jaxb);

    U toMessage(T dto);
}
