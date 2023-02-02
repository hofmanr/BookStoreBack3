package nl.rhofman.bookstore.web.book.assembler;

import nl.rhofman.bookstore.persist.model.Author;
import nl.rhofman.bookstore.persist.model.Book;
import nl.rhofman.bookstore.persist.model.Category;
import nl.rhofman.bookstore.persist.model.Publisher;
import nl.rhofman.bookstore.web.book.domain.AuthorDTO;
import nl.rhofman.bookstore.web.book.domain.BookDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedSourcePolicy = ReportingPolicy.WARN, unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class BookAssembler {
    public static BookAssembler instance() {
        return Mappers.getMapper(BookAssembler.class);
    }

    @Mapping(target = "version", ignore = true)
    public abstract Book mapToBook(BookDTO bookDTO);

    @InheritInverseConfiguration
    public abstract BookDTO mapToBookDTO(Book book);

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "books", ignore = true)
    abstract Author mapToAuthor(AuthorDTO author);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "name", expression = "java(category)")
    abstract Category mapToCategory(String category);

    String mapToString(Category category) {
        return category == null ? null : category.getName();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "name", expression = "java(publisher)")
    abstract Publisher mapToPublisher(String publisher);

    String mapToString(Publisher publisher) {
        return publisher == null ? null : publisher.getName();
    }

}
