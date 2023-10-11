package nl.rhofman.bookstore.ejb.catalogue.dao;

import jakarta.inject.Inject;
import nl.rhofman.bookstore.ejb.catalogue.domain.Book;
import nl.rhofman.bookstore.persist.model.Category;
import nl.rhofman.bookstore.persist.service.BookService;
import org.mapstruct.*;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.JAKARTA, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class BookMapper {

    @Inject
    private BookService bookService;

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    public abstract nl.rhofman.bookstore.persist.model.Book mapToPersistentBook(Book book);

    @AfterMapping
    protected void mapOtherValues(@MappingTarget nl.rhofman.bookstore.persist.model.Book targetBook, Book sourceBook) {
        Category category = bookService.findCategory("Business");
        targetBook.setCategory(category);
    }

}
