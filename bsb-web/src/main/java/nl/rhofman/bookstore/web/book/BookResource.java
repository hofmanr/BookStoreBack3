package nl.rhofman.bookstore.web.book;

import jakarta.inject.Inject;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.rhofman.bookstore.persist.model.Author;
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBook(Book book) {
        return Response.ok(bookService.create(book)).build();
    }

    /**
     * Add author to book
     * @param id Book ID
     * @param author the ID is mandatory
     * @return
     */
    @POST
    @Path("/{id : \\d+}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAuthor(@PathParam("id") @Min(1) Long id, Author author) {
        // TODO
        return Response.ok().build();
    }

}
