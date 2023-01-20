package nl.rhofman.bookstore.web.book;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.rhofman.bookstore.persist.model.Book;
import nl.rhofman.bookstore.persist.service.BookService;

import java.util.List;

@Path("books")
public class BookResource {
    @Inject
    private BookService bookService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response allBooks() {
        List<Book> books = bookService.getAll();
        if (books.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(books).build();
    }

}
