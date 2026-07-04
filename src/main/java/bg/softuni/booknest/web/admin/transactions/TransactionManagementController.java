package bg.softuni.booknest.web.admin.transactions;

import bg.softuni.booknest.model.entity.Transaction;
import bg.softuni.booknest.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin/transactions")
public class TransactionManagementController {

    private final TransactionService transactionService;

    public TransactionManagementController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ModelAndView transactions() {

        List<Transaction> transactions =
                transactionService.getAllTransactions();

        return new ModelAndView("admin/transactions")
                .addObject("transactions", transactions)
                .addObject("activePage", "transactions");
    }
}