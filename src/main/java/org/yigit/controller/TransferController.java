package org.yigit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.yigit.dto.AccountDTO;
import org.yigit.dto.TransactionDTO;
import org.yigit.service.AccountService;
import org.yigit.service.TransactionService;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

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
        model.addAttribute("transaction", new TransactionDTO());
        model.addAttribute("accounts", accountService.listAllAccount());
        model.addAttribute("transactionList", transactionService.last10Transaction());
        return "/transaction/make-transfer";
    }

    @PostMapping("/make-transfer")
    public String makeTransaction(@Valid @ModelAttribute("transaction") TransactionDTO transactionDTO, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("accounts", accountService.listAllAccount());
            model.addAttribute("transactionList", transactionService.last10Transaction());
            return "/transaction/make-transfer";
        }
        AccountDTO sender = accountService.findById(transactionDTO.getSender().getId());
        AccountDTO receiver = accountService.findById(transactionDTO.getReceiver().getId());
        transactionService.makeTransfer(sender, receiver, transactionDTO.getAmount(), new Date(), transactionDTO.getMessage());
        return "redirect:/make-transfer";
    }

    @GetMapping("/transaction/{id}")
    public String getTransactionData(@PathVariable("id") Long id, Model model) {
        List<TransactionDTO> transactionDTOListById = transactionService.findTransactionListById(id);
        model.addAttribute("transactions", transactionDTOListById);

        System.out.println(id);
        return "transaction/transaction";
    }
}
