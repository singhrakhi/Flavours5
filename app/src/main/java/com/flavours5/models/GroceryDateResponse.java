package com.flavours5.models;

import java.util.List;

public class GroceryDateResponse {

    /**
     * Message : Category Data !!!
     * Status : Success
     * Data : [{"Id":10,"SlotDate":"04-May-2020"}]
     */

    private String Message;
    private String Status;
    private List<DataBean> Data;

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

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * Id : 10
         * SlotDate : 04-May-2020
         */

        private int Id;
        private String SlotDate;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getSlotDate() {
            return SlotDate;
        }

        public void setSlotDate(String SlotDate) {
            this.SlotDate = SlotDate;
        }
    }
}
