package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @Transactional
    @Override
    public void updateBalance(String typeId, String statusId, int userId, int receiverId, BigDecimal transferAmount) {
        String sql = "UPDATE account SET balance = balance - ? WHERE user_id = ?;";
        jdbcTemplate.update(sql, transferAmount, userId);

        String sql2 = "UPDATE account SET balance = balance + ? WHERE user_id = ?;";
        jdbcTemplate.update(sql2, transferAmount, receiverId);

        String sql3 = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                " VALUES ((select transfer_type_id from transfer_type where transfer_type_desc = ?), " +
                "(select transfer_status_id from transfer_status where transfer_status_desc = ?), " +
                "(select account_id from account where user_id = ?), "+
                "(select account_id from account where user_id = ?), ?); ";
        int transferId = jdbcTemplate.update(sql3, typeId, statusId, userId, receiverId, transferAmount);

        // SELECT
    }

    @Override
    public List<Transfer> getTransfers(int user_id) {
        String sql = "select transfer_id, transfer_type_desc, transfer_status_desc, user_id, amount from transfer\n" +
                "join transfer_type on transfer.transfer_type_id = transfer_type.transfer_type_id\n" +
                "join transfer_status on transfer.transfer_status_id = transfer_status.transfer_status_id\n" +
                "join account on transfer.account_from = account.account_id\n" +
                "where account_from = (select account_id from account where user_id = ?);";

        List<Transfer> listOfTransfers = new ArrayList<>();
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, user_id);

        while(result.next()){
            Transfer transfer = mapRowToTransfer(result);
            listOfTransfers.add(transfer);
        }
        return listOfTransfers;
    }

    public Transfer getTransferById(int transferId){
        String sql = "select transfer_id, transfer_type_desc, transfer_status_desc, user_id, amount from transfer\n" +
                "join transfer_type on transfer.transfer_type_id = transfer_type.transfer_type_id\n" +
                "join transfer_status on transfer.transfer_status_id = transfer_status.transfer_status_id\n" +
                "join account on transfer.account_from = account.account_id\n" +
                "WHERE transfer_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);

        Transfer transfer = new Transfer();
        if (result.next()){
            transfer = mapRowToTransfer(result);
        }
        return transfer;
    }

    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setType(rs.getString("transfer_type_desc"));
        transfer.setStatus(rs.getString(("transfer_status_desc")));
        transfer.setReceiverId(rs.getInt("user_id"));
        transfer.setTransferAmount(rs.getBigDecimal("amount"));
        return transfer;
    }


}
