package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private UserDao userDao;

    public AccountController(AccountDao accountDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

//    @RequestMapping(path = "", method = RequestMethod.GET)
//    public BigDecimal getBalance(@PathVariable int account_id){
//        return accountDao.getBalance(account_id);
//    }

    @RequestMapping(method = RequestMethod.GET)
    public BigDecimal getBalance(Principal principal){
        int userId = userDao.findIdByUsername(principal.getName());
        return accountDao.getBalance(userId);
    }

//    @RequestMapping(path = "/details", method = RequestMethod.GET)
//    public Account getDetails(Principal principal){
////      Go to database to fetch stuff
//        Account account = new Account(BigDecimal.valueOf(100.00), 5, 6);
//        return account;
//    }

    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public void transferMoney(@RequestBody Transfer transfer, Principal principal){
        int userId = userDao.findIdByUsername(principal.getName());
        accountDao.updateBalance(transfer.getTypeId(), transfer.getStatusId(), userId, transfer.getReceiverId(), transfer.getTransferAmount());
    }


}
