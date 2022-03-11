package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private String type;
    private String status;
    private int receiverId;
    private BigDecimal transferAmount;

    public int getTransferId() {
        return transferId;
    }
    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public int getReceiverId() {
        return receiverId;
    }
    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }
    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }



}
