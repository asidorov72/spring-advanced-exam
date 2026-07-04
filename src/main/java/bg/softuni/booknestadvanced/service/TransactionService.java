package bg.softuni.booknestadvanced.service;

import bg.softuni.booknestadvanced.model.entity.Transaction;
import bg.softuni.booknestadvanced.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAllByOrderByCreatedOnDesc();
    }

    public long getTransactionsCount() {
        return transactionRepository.count();
    }
}