package bg.softuni.booknestadvanced.service;

import bg.softuni.booknestadvanced.mapper.ReservationMapper;
import bg.softuni.booknestadvanced.model.entity.Book;
import bg.softuni.booknestadvanced.model.entity.Reservation;
import bg.softuni.booknestadvanced.model.entity.Transaction;
import bg.softuni.booknestadvanced.model.entity.User;
import bg.softuni.booknestadvanced.model.enums.BookStatus;
import bg.softuni.booknestadvanced.model.enums.Genre;
import bg.softuni.booknestadvanced.model.enums.TransactionStatus;
import bg.softuni.booknestadvanced.repository.BookRepository;
import bg.softuni.booknestadvanced.repository.ReservationRepository;
import bg.softuni.booknestadvanced.repository.TransactionRepository;
import bg.softuni.booknestadvanced.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReservationMapper reservationMapper;

    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationService(
                reservationRepository,
                transactionRepository,
                bookRepository,
                userRepository,
                reservationMapper
        );
    }

    @Test
    void reserveBookShouldCreateReservationAndTransaction() {
        UUID bookId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        Book book = createBook();
        User user = createUser();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(reservationRepository.existsByBookIdAndUserId(bookId, userId)).thenReturn(false);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        reservationService.reserveBook(bookId, userId);

        ArgumentCaptor<Reservation> reservationCaptor = ArgumentCaptor.forClass(Reservation.class);
        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);

        verify(reservationRepository).save(reservationCaptor.capture());
        verify(transactionRepository).save(transactionCaptor.capture());

        Reservation savedReservation = reservationCaptor.getValue();
        Transaction savedTransaction = transactionCaptor.getValue();

        assertEquals(book, savedReservation.getBook());
        assertEquals(user, savedReservation.getUser());
        assertEquals(book.getRentalPrice(), savedTransaction.getAmount());
        assertEquals(TransactionStatus.SUCCESSFUL, savedTransaction.getStatus());
        assertEquals(user, savedTransaction.getUser());
        assertEquals(savedReservation, savedTransaction.getReservation());
    }

    @Test
    void reserveBookShouldThrowExceptionWhenBookDoesNotExist() {
        UUID bookId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.reserveBook(bookId, userId)
        );

        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    void reserveBookShouldThrowExceptionWhenBookIsInactive() {
        UUID bookId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        Book book = createBook().setStatus(BookStatus.INACTIVE);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> reservationService.reserveBook(bookId, userId)
        );

        assertEquals("Book is not available for reservation", exception.getMessage());
    }

    @Test
    void reserveBookShouldThrowExceptionWhenUserAlreadyReservedBook() {
        UUID bookId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        Book book = createBook();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(reservationRepository.existsByBookIdAndUserId(bookId, userId)).thenReturn(true);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> reservationService.reserveBook(bookId, userId)
        );

        assertEquals("You have already reserved this book", exception.getMessage());
    }

    private Book createBook() {
        return new Book()
                .setTitle("Dune")
                .setAuthor("Frank Herbert")
                .setDescription("Desert planet")
                .setGenre(Genre.SCIENCE_FICTION)
                .setStatus(BookStatus.ACTIVE)
                .setBookImage("/images/dune.jpg")
                .setReleaseYear(1965)
                .setRentalPrice(BigDecimal.TEN)
                .setFeatured(false);
    }

    private User createUser() {
        return new User()
                .setUsername("test")
                .setEmail("test@test.com")
                .setPassword("123");
    }
}