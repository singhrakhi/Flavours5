package com.flavours5.models;

public class DeliveryChargeData {


    /**
     * Message : Product Data !!!
     * Status : Success
     * Data : {"ID":1,"shipingmoney":"299","DeliveryCharge":49,"MinOrderValue":99}
     */

    private String Message;
    private String Status;
    private DataBean Data;

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

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * ID : 1
         * shipingmoney : 299
         * DeliveryCharge : 49
         * MinOrderValue : 99
         */

        private int ID;
        private String shipingmoney;
        private double DeliveryCharge;
        private double MinOrderValue;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getShipingmoney() {
            return shipingmoney;
        }

        public void setShipingmoney(String shipingmoney) {
            this.shipingmoney = shipingmoney;
        }

        public double getDeliveryCharge() {
            return DeliveryCharge;
        }

        public void setDeliveryCharge(int DeliveryCharge) {
            this.DeliveryCharge = DeliveryCharge;
        }

        public double getMinOrderValue() {
            return MinOrderValue;
        }

        public void setMinOrderValue(int MinOrderValue) {
            this.MinOrderValue = MinOrderValue;
        }
    }
}
