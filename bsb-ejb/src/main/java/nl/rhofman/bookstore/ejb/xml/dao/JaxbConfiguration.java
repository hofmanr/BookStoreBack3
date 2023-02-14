package nl.rhofman.bookstore.ejb.xml.dao;

import java.util.List;

public interface JaxbConfiguration {

    String getXsdDirectory();

    List<String> getXsdFiles();

    String getJaxbPackage(String messageType);

    <T,U> Assembler<T,U> getJaxbAssembler(Object jaxbObject);

    <T,U> Assembler<T,U> getDomainAssembler(Object dto);
}
