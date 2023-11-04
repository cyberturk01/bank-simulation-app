package org.yigit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.yigit.enums.AccountType;
import org.yigit.model.Account;
import org.yigit.service.AccountService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Controller
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/index")
    public String getIndexPage(Model model) {
        model.addAttribute("accountList", accountService.listAllAccount());
        return "account/index";
    }

    @GetMapping("/create-form")
    public String getCreateForm(Model model) {
        //We need to provide empty Account Object to fill it up with data
        model.addAttribute("account", Account.builder().build());
        //We need to provide Account Type
        model.addAttribute("types", AccountType.values());
        return "account/create-account";
    }

    @PostMapping("/create")
    public String createAccount(@Valid @ModelAttribute("account") Account account, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            //Dropdown values will be loaded
            model.addAttribute("types", AccountType.values());
            return "account/create-account";
        }
        accountService.createNewAccount(account.getBalance(), new Date(), account.getAccountType(), account.getUserId());
        //return to which page to redirect, might be different endpoint not only folder structure
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteForm(@PathVariable("id") UUID id) {
        accountService.deleteById(id);
        return "redirect:/index";
    }
    @GetMapping("/activate/{id}")
    public String activate(@PathVariable("id") UUID id) {
        accountService.activateById(id);
        return "redirect:/index";
    }

}
