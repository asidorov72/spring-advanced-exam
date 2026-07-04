package bg.softuni.booknestadvanced.service;

import bg.softuni.booknestadvanced.mapper.ReservationMapper;
import bg.softuni.booknestadvanced.model.dto.ReservationDto;
import bg.softuni.booknestadvanced.model.dto.ReservationEditRequest;
import bg.softuni.booknestadvanced.model.entity.Book;
import bg.softuni.booknestadvanced.model.entity.Reservation;
import bg.softuni.booknestadvanced.model.entity.Transaction;
import bg.softuni.booknestadvanced.model.entity.User;
import bg.softuni.booknestadvanced.model.enums.BookStatus;
import bg.softuni.booknestadvanced.model.enums.TransactionStatus;
import bg.softuni.booknestadvanced.repository.BookRepository;
import bg.softuni.booknestadvanced.repository.ReservationRepository;
import bg.softuni.booknestadvanced.repository.TransactionRepository;
import bg.softuni.booknestadvanced.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TransactionRepository transactionRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ReservationMapper reservationMapper;

    public ReservationService(ReservationRepository reservationRepository,
                              TransactionRepository transactionRepository,
                              BookRepository bookRepository,
                              UserRepository userRepository,
                              ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.transactionRepository = transactionRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.reservationMapper = reservationMapper;
    }

    @Transactional
    public void reserveBook(UUID bookId, UUID userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        if (book.getStatus() != BookStatus.ACTIVE) {
            throw new IllegalStateException("Book is not available for reservation");
        }

        if (reservationRepository.existsByBookIdAndUserId(bookId, userId)) {
            throw new IllegalStateException("You have already reserved this book");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Reservation reservation = new Reservation()
                .setUser(user)
                .setBook(book)
                .setReservationDate(LocalDate.now())
                .setReturnDate(LocalDate.now().plusDays(14));

        reservationRepository.save(reservation);

        Transaction transaction = new Transaction()
                .setUser(user)
                .setReservation(reservation)
                .setAmount(book.getRentalPrice())
                .setStatus(TransactionStatus.SUCCESSFUL);

        transactionRepository.save(transaction);
    }

    public boolean isReservedByCurrentUser(UUID bookId, UUID userId) {
        return reservationRepository.existsByBookIdAndUserId(bookId, userId);
    }

    public List<ReservationDto> getUserReservations(UUID userId) {
        return reservationRepository
                .findAllByUserIdOrderByReservationDateDesc(userId)
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    public long getReservationCount(UUID userId) {
        return reservationRepository.countByUserId(userId);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAllByOrderByReservationDateDesc();
    }

    public Reservation getReservationById(UUID id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
    }

    public void updateReservation(UUID id, ReservationEditRequest request) {
        Reservation reservation = getReservationById(id);

        reservation
                .setReservationDate(request.getReservationDate())
                .setReturnDate(request.getReturnDate());

        reservationRepository.save(reservation);
    }

    @Transactional
    public void deleteReservation(UUID id) {
        transactionRepository.deleteByReservationId(id);
        reservationRepository.deleteById(id);
    }

    public long getReservationsCount() {
        return reservationRepository.count();
    }
}