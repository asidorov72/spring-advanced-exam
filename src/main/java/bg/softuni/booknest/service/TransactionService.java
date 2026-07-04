package bg.softuni.booknest.service;

import bg.softuni.booknest.model.entity.Transaction;
import bg.softuni.booknest.repository.TransactionRepository;
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