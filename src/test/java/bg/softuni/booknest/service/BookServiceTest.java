package bg.softuni.booknest.service;

import bg.softuni.booknest.mapper.BookMapper;
import bg.softuni.booknest.model.dto.BookDto;
import bg.softuni.booknest.model.entity.Book;
import bg.softuni.booknest.model.enums.BookStatus;
import bg.softuni.booknest.model.enums.Genre;
import bg.softuni.booknest.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertThrows;
import bg.softuni.booknest.model.dto.BookEditRequest;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookService(bookRepository, new BookMapper());
    }

    @Test
    void searchActiveBooksShouldReturnActiveBooksWhenQueryIsBlank() {
        Book book = createBook("Dune", "Frank Herbert", "Desert planet");

        when(bookRepository.findAllByStatus(BookStatus.ACTIVE))
                .thenReturn(List.of(book));

        List<BookDto> result = bookService.searchActiveBooks("");

        assertEquals(1, result.size());
        assertEquals("Dune", result.get(0).getTitle());

        verify(bookRepository).findAllByStatus(BookStatus.ACTIVE);
    }

    @Test
    void searchActiveBooksShouldSearchByQueryWhenQueryIsProvided() {
        Book book = createBook("Hamlet", "William Shakespeare", "A tragedy");

        when(bookRepository.searchByTitleAuthorOrGenre(BookStatus.ACTIVE, "Hamlet"))
                .thenReturn(List.of(book));

        List<BookDto> result = bookService.searchActiveBooks(" Hamlet ");

        assertEquals(1, result.size());
        assertEquals("Hamlet", result.get(0).getTitle());
        assertEquals("William Shakespeare", result.get(0).getAuthor());

        verify(bookRepository).searchByTitleAuthorOrGenre(BookStatus.ACTIVE, "Hamlet");
    }

    private Book createBook(String title, String author, String description) {
        return new Book()
                .setTitle(title)
                .setAuthor(author)
                .setDescription(description)
                .setGenre(Genre.DRAMA)
                .setStatus(BookStatus.ACTIVE)
                .setBookImage("/images/test.jpg")
                .setReleaseYear(1965)
                .setRentalPrice(BigDecimal.TEN)
                .setFeatured(false);
    }

    @Test
    void getBookByIdShouldReturnBookWhenBookExists() {
        Book book = createBook("Dune", "Frank Herbert", "Desert planet");

        when(bookRepository.findById(book.getId()))
                .thenReturn(Optional.of(book));

        BookDto result = bookService.getBookById(book.getId());

        assertEquals("Dune", result.getTitle());
        assertEquals("Frank Herbert", result.getAuthor());

        verify(bookRepository).findById(book.getId());
    }

    @Test
    void getBookByIdShouldThrowExceptionWhenBookDoesNotExist() {
        UUID bookId = UUID.randomUUID();

        when(bookRepository.findById(bookId))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> bookService.getBookById(bookId)
        );

        assertEquals("Book not found", exception.getMessage());

        verify(bookRepository).findById(bookId);
    }

    @Test
    void createBookShouldSaveBook() {
        BookEditRequest request = createBookEditRequest();

        bookService.createBook(request);

        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void updateBookShouldUpdateExistingBook() {
        UUID bookId = UUID.randomUUID();
        Book existingBook = createBook("Old Title", "Old Author", "Old description");
        BookEditRequest request = createBookEditRequest();

        when(bookRepository.findById(bookId))
                .thenReturn(Optional.of(existingBook));

        bookService.updateBook(bookId, request);

        assertEquals(request.getTitle(), existingBook.getTitle());
        assertEquals(request.getAuthor(), existingBook.getAuthor());
        assertEquals(request.getDescription(), existingBook.getDescription());
        assertEquals(request.getGenre(), existingBook.getGenre());
        assertEquals(request.getStatus(), existingBook.getStatus());
        assertEquals(request.getBookImage(), existingBook.getBookImage());
        assertEquals(request.getReleaseYear(), existingBook.getReleaseYear());
        assertEquals(request.getRentalPrice(), existingBook.getRentalPrice());

        verify(bookRepository).findById(bookId);
        verify(bookRepository).save(existingBook);
    }

    private BookEditRequest createBookEditRequest() {
        return new BookEditRequest()
                .setTitle("New Book")
                .setAuthor("New Author")
                .setDescription("New description")
                .setGenre(Genre.HISTORY)
                .setStatus(BookStatus.ACTIVE)
                .setBookImage("/images/new-book.jpg")
                .setReleaseYear(2024)
                .setRentalPrice(BigDecimal.valueOf(12.50))
                .setFeatured(true);
    }
}
