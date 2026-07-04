package bg.softuni.booknest.model.entity;

import bg.softuni.booknest.model.enums.TransactionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Reservation reservation;

    public BigDecimal getAmount() {
        return amount;
    }

    public Transaction setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public Transaction setStatus(TransactionStatus status) {
        this.status = status;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Transaction setUser(User user) {
        this.user = user;
        return this;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public Transaction setReservation(Reservation reservation) {
        this.reservation = reservation;
        return this;
    }
}