package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getBalance(int user_id) {
        String sql = "select balance from account where user_id = ?;";
        BigDecimal balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, user_id);
        return balance;
    }

    @Override
    public void updateBalance(int typeId, int statusId, int userId, int receiverId, BigDecimal transferAmount) {
        String sql = "UPDATE account SET balance = balance - ? WHERE user_id = ?;";
        jdbcTemplate.update(sql, transferAmount, userId);

        String sql2 = "UPDATE account SET balance = balance + ? WHERE user_id = ?;";
        jdbcTemplate.update(sql2, transferAmount, receiverId);

        String sql3 = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                        "VALUES (?, ?, ?, ?, ?); RETURNING transfer_id";
        jdbcTemplate.update(sql3, typeId, statusId, userId, receiverId, transferAmount);
    }

}
