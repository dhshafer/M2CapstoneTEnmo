package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface AccountDao {

    BigDecimal getBalance(int account_id);

    void updateBalance(int userId, int receiverId, BigDecimal transferAmount);
}
