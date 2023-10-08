package org.yigit.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.yigit.model.Account;
import org.yigit.model.Transaction;
import org.yigit.service.AccountService;
import org.yigit.service.TransactionService;

import java.util.Date;

@Controller
public class TransferController {

    TransactionService transactionService;
    AccountService accountService;

    public TransferController(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @GetMapping("/transaction")
    public String getTransaction(Model model) {
        model.addAttribute("transactionList", transactionService.findAllTransaction());
        return "/transaction/transaction";
    }

    @GetMapping("/make-transfer")
    public String makeTransaction(Model model) {
        model.addAttribute("transaction", Transaction.builder().build());
        model.addAttribute("accounts", accountService.listAllAccount());
        model.addAttribute("transactionList", transactionService.last10Transaction());
        return "/transaction/make-transfer";
    }

    @PostMapping("/make-transfer")
    public String makeTransaction(Transaction transaction) {
        Account sender = accountService.findById(transaction.getSender());
        Account receiver = accountService.findById(transaction.getReceiver());
        transactionService.makeTransfer(sender, receiver, transaction.getAmount(), new Date(), transaction.getMessage());
        return "redirect:/make-transfer";
    }
}
