package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    BigDecimal getBalance(int account_id);

    void updateBalance(String type, String status, int userId, int receiverId, BigDecimal transferAmount);

    List<Transfer> getTransfers(int user_id);

    Transfer getTransferById(int transferId);
}
