package nl.rhofman.bookstore.ejb.xml.dao;

import java.util.List;

public interface JaxbConfiguration {

    /**
     * The directory in the project where the XSD's can be found
     * @return
     */
    String getXsdDirectory();

    /**
     * The list of the XSD files
     * @return
     */
    List<String> getXsdFiles();

    /**
     * The package where the generated JAXB-objects can be found
     * @param messageType
     * @return
     */
    String getJaxbPackage(String messageType);

    /**
     * Get the (MAPSTRUCT) assembler for mapping the JAXB-object to the (internal) domain objects
     * @param jaxbObject
     * @return
     * @param <T>
     * @param <U>
     */
    <T,U> Assembler<T,U> getJaxbAssembler(Object jaxbObject);

    /**
     * The (MAPSTRUCT) assembler for mapping de (internal) domain object to the JAXB-objects
     * @param dto
     * @return
     * @param <T>
     * @param <U>
     */
    <T,U> Assembler<T,U> getDomainAssembler(Object dto);
}
