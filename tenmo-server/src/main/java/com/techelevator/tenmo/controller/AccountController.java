package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(method = RequestMethod.GET)
    public BigDecimal getBalance(Principal principal){
        int userId = userDao.findIdByUsername(principal.getName());
        return accountDao.getBalance(userId);
    }

    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public void transferMoney(@RequestBody Transfer transfer, Principal principal){
        int userId = userDao.findIdByUsername(principal.getName());
        accountDao.updateBalance(transfer.getType(), transfer.getStatus(),
                userId, transfer.getReceiverId(), transfer.getTransferAmount());
    }

    @RequestMapping(path = "/transfer", method = RequestMethod.GET)
    public List<Transfer> getTransfers(Principal principal){
        int userId = userDao.findIdByUsername(principal.getName());
        List<Transfer> listOfTransfers;
        listOfTransfers = accountDao.getTransfers(userId);
        return listOfTransfers;
    }

//    @PreAuthorize()
    @RequestMapping(path = "/transfer/{transferId}", method = RequestMethod.GET)
    public Transfer getTransferById(@PathVariable int transferId){
        Transfer transfer;
        transfer = accountDao.getTransferById(transferId);
        return transfer;
    }

}
