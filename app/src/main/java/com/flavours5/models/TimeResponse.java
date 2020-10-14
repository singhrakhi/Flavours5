package com.flavours5.models;

import java.util.List;

public class TimeResponse {

    /**
     * Messege : Time Slot Data !!!
     * Status : Success
     * Data : [{"ID":11,"Starttime":"10:00 AM","Endtime":"01:00 PM"},{"ID":12,"Starttime":"01:00 PM","Endtime":"03:00 PM"},{"ID":18,"Starttime":"09:00 AM","Endtime":"11:00 AM"},{"ID":19,"Starttime":"11:00 AM","Endtime":"01:00 PM"},{"ID":20,"Starttime":"03:00 PM","Endtime":"05:00 PM"},{"ID":21,"Starttime":"05:00 PM","Endtime":"07:00 PM"},{"ID":22,"Starttime":"04:00 PM","Endtime":"06:00 PM"}]
     */

    private String Messege;
    private String Status;
    private List<DataBean> Data;

    public String getMessege() {
        return Messege;
    }

    public void setMessege(String Messege) {
        this.Messege = Messege;
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
         * ID : 11
         * Starttime : 10:00 AM
         * Endtime : 01:00 PM
         */

        private int ID;
        private String Starttime;
        private String Endtime;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getStarttime() {
            return Starttime;
        }

        public void setStarttime(String Starttime) {
            this.Starttime = Starttime;
        }

        public String getEndtime() {
            return Endtime;
        }

        public void setEndtime(String Endtime) {
            this.Endtime = Endtime;
        }
    }
}
