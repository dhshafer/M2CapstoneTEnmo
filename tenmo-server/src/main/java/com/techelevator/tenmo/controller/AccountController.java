package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
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

    @RequestMapping(path = "/details", method = RequestMethod.GET)
    public Account getDetails(Principal principal){
//      Go to database to fetch stuff
        Account account = new Account(BigDecimal.valueOf(100.00), 5, 6);
        return account;
    }

    @RequestMapping(path = "/transfer", method = RequestMethod.PUT)
    public void transferMoney(@RequestBody int receiverId, BigDecimal transferAmount, Principal principal){
        int userId = userDao.findIdByUsername(principal.getName());
        accountDao.updateBalance(userId, receiverId, transferAmount);
        // as "POST", combine receiverId and transferAmount into object, see "login" inside AuthenticationController

//        Create new Model "Transfer" that contains receiver and transferAmount
//
    }

}
