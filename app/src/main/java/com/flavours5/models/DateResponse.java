package com.flavours5.models;

import java.util.List;

public class DateResponse {

    /**
     * Message : Order Date Data !!!
     * Status : Success
     * Data : [{"Id":3,"Date":"02-May-2020"},{"Id":4,"Date":"01-May-2020"},{"Id":5,"Date":"03-May-2020"}]
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
         * Id : 3
         * Date : 02-May-2020
         */

        private int Id;
        private String Date;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getDate() {
            return Date;
        }

        public void setDate(String Date) {
            this.Date = Date;
        }
    }
}
