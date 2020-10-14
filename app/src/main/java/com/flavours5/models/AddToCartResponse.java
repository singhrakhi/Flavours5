package com.flavours5.models;

public class AddToCartResponse {

    /**
     * Message : Kart Quantity Increase !!!
     * Status : Success
     * data : null
     */

    private String Message;
    private String Status;
    private Object data;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
