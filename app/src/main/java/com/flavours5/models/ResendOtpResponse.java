package com.flavours5.models;

public class ResendOtpResponse {

    /**
     * Message : OTP Re-Send On Mobile Number !!!
     * Status : Success
     */

    private String Message;
    private String Status;

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
}
