package bg.softuni.booknest.service;

import bg.softuni.booknest.mapper.BookMapper;
import bg.softuni.booknest.model.dto.BookDto;
import bg.softuni.booknest.model.dto.BookEditRequest;
import bg.softuni.booknest.model.entity.Book;
import bg.softuni.booknest.model.enums.BookStatus;
import bg.softuni.booknest.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository,
                       BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    public BookDto getBookById(UUID id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        return bookMapper.toDto(book);
    }

    public void updateBook(UUID id, BookEditRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setDescription(request.getDescription());
        book.setGenre(request.getGenre());
        book.setStatus(request.getStatus());
        book.setBookImage(request.getBookImage());
        book.setReleaseYear(request.getReleaseYear());
        book.setRentalPrice(request.getRentalPrice());
        book.setFeatured(request.isFeatured());

        bookRepository.save(book);
    }

    public void createBook(BookEditRequest request) {
        Book book = new Book()
                .setTitle(request.getTitle())
                .setAuthor(request.getAuthor())
                .setDescription(request.getDescription())
                .setGenre(request.getGenre())
                .setStatus(request.getStatus())
                .setFeatured(request.isFeatured())
                .setBookImage(request.getBookImage())
                .setReleaseYear(request.getReleaseYear())
                .setRentalPrice(request.getRentalPrice());

        bookRepository.save(book);
    }

    public void deleteBook(UUID id) {
        bookRepository.deleteById(id);
    }

    public List<BookDto> getRecentlyAddedBooks() {
        return bookRepository
                .findTop8ByStatusOrderByCreatedOnDesc(BookStatus.ACTIVE)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    public List<BookDto> getFeaturedBooks() {
        return bookRepository
                .findTop4ByFeaturedTrueAndStatusOrderByCreatedOnDesc(BookStatus.ACTIVE)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    // Popular Books = ACTIVE книги, отсортированные по rentalPrice DESC, максимум 8
    public List<BookDto> getPopularBooks() {
        return bookRepository.findAllByStatusOrderByRentalPriceDesc(BookStatus.ACTIVE)
                .stream()
                .limit(8)
                .map(bookMapper::toDto)
                .toList();
    }

    public BookDto getReadersChoiceBook() {
        List<Book> activeBooks = bookRepository.findAllByStatus(BookStatus.ACTIVE);

        if (activeBooks.isEmpty()) {
            return null;
        }

        Book randomBook = activeBooks.get(new Random().nextInt(activeBooks.size()));

        return bookMapper.toDto(randomBook);
    }

    public long getBooksCount() {
        return bookRepository.count();
    }

    public List<BookDto> searchActiveBooks(String query) {
        if (query == null || query.isBlank()) {
            return bookRepository.findAllByStatus(BookStatus.ACTIVE)
                    .stream()
                    .map(bookMapper::toDto)
                    .toList();
        }

        return bookRepository.searchByTitleAuthorOrGenre(BookStatus.ACTIVE, query.trim())
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }
}