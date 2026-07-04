package bg.softuni.booknest.service;

import bg.softuni.booknest.mapper.ReservationMapper;
import bg.softuni.booknest.model.entity.Book;
import bg.softuni.booknest.model.entity.Reservation;
import bg.softuni.booknest.model.entity.Transaction;
import bg.softuni.booknest.model.entity.User;
import bg.softuni.booknest.model.enums.BookStatus;
import bg.softuni.booknest.model.enums.Genre;
import bg.softuni.booknest.model.enums.TransactionStatus;
import bg.softuni.booknest.repository.BookRepository;
import bg.softuni.booknest.repository.ReservationRepository;
import bg.softuni.booknest.repository.TransactionRepository;
import bg.softuni.booknest.repository.UserRepository;
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