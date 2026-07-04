package bg.softuni.booknest.repository;

import bg.softuni.booknest.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    void deleteByReservationId(UUID reservationId);
    List<Transaction> findAllByOrderByCreatedOnDesc();
}
