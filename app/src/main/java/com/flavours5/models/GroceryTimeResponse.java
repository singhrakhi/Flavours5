package com.flavours5.models;

import java.util.List;

public class GroceryTimeResponse {

    /**
     * Message : Time Slot Data !!!
     * Status : Success
     * Data : [{"Id":89,"TotalSlotInThisTime":6,"dateId":11,"SlotEndTime":"11:00 AM","SlotStartTime":"09:00 AM"},{"Id":90,"TotalSlotInThisTime":6,"dateId":11,"SlotEndTime":"12:00 PM","SlotStartTime":"11:00 AM"},{"Id":91,"TotalSlotInThisTime":5,"dateId":11,"SlotEndTime":"05:00 PM","SlotStartTime":"03:00 PM"},{"Id":92,"TotalSlotInThisTime":5,"dateId":11,"SlotEndTime":"07:00 PM","SlotStartTime":"05:00 PM"},{"Id":93,"TotalSlotInThisTime":4,"dateId":11,"SlotEndTime":"08:00 PM","SlotStartTime":"07:00 PM"}]
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
         * Id : 89
         * TotalSlotInThisTime : 6
         * dateId : 11
         * SlotEndTime : 11:00 AM
         * SlotStartTime : 09:00 AM
         */

        private int Id;
        private int TotalSlotInThisTime;
        private int dateId;
        private String SlotEndTime;
        private String SlotStartTime;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public int getTotalSlotInThisTime() {
            return TotalSlotInThisTime;
        }

        public void setTotalSlotInThisTime(int TotalSlotInThisTime) {
            this.TotalSlotInThisTime = TotalSlotInThisTime;
        }

        public int getDateId() {
            return dateId;
        }

        public void setDateId(int dateId) {
            this.dateId = dateId;
        }

        public String getSlotEndTime() {
            return SlotEndTime;
        }

        public void setSlotEndTime(String SlotEndTime) {
            this.SlotEndTime = SlotEndTime;
        }

        public String getSlotStartTime() {
            return SlotStartTime;
        }

        public void setSlotStartTime(String SlotStartTime) {
            this.SlotStartTime = SlotStartTime;
        }
    }
}
