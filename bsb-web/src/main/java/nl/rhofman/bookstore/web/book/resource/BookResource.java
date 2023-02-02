package nl.rhofman.bookstore.web.book.resource;

import jakarta.inject.Inject;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.rhofman.bookstore.persist.model.Book;
import nl.rhofman.bookstore.persist.service.BookService;
import nl.rhofman.bookstore.web.book.assembler.BookAssembler;
import nl.rhofman.bookstore.web.book.domain.BookDTO;

import java.util.List;
import java.util.stream.Collectors;

@Path("books")
public class BookResource {
    @Inject
    private BookService bookService;

    private BookAssembler bookAssembler = BookAssembler.instance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response allBooks() {
        List<Book> books = bookService.getAll();
        if (books.isEmpty()) {
            return Response.noContent().build();
        }
        List<BookDTO> bookDTOs = books.stream()
                .map(b -> bookAssembler.mapToBookDTO(b))
                .collect(Collectors.toList());
        return Response.ok(bookDTOs).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBook(@NotNull BookDTO book) {
        Book createdBook = bookService.create(bookAssembler.mapToBook(book));
        return Response.ok(bookAssembler.mapToBookDTO(createdBook)).build();
    }

    /**
     * Get book by ID
     * @param id
     * @return
     */
    @GET
    @Path("/{id : \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("id") @Min(1) Long id) {
        // TODO
        return Response.ok().build();
    }

    /**
     * Get the number of copies of the book
     * Return: inStock, ordered, total
     * @param id
     * @return
     */
    @GET
    @Path("/{id : \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNrOfCopies(@PathParam("id") @Min(1) Long id) {
        // TODO
        return Response.ok().build();
    }



}
