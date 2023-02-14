package nl.rhofman.bookstore.ejb.xml.service;

import jakarta.validation.constraints.NotNull;

public abstract class AssemblerService {
    /**
     * Transform JAXB-object (external message) to DOMAIN-object
     * @param jaxb
     * @param <T>
     * @param <U>
     * @return
     */
    public abstract <T, U> T toDomain(@NotNull U jaxb);

    /**
     * Transform DOMAIN-object to JAXB-object (external message)
     * @param dto
     * @param <T>
     * @param <U>
     * @return
     */
    public abstract <T, U> U toMessage(@NotNull T dto);
}
