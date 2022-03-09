package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface AccountDao {

    BigDecimal getBalance(int user_id, int account_id);

    void transferFunds(int sender_id, int receiver_id, BigDecimal amount);



}
