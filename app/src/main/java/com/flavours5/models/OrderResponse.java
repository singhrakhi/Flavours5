package com.flavours5.models;

public class OrderResponse {

    /**
     * Message : Order Placed Successfully !!!
     * Status : Success
     * data : {"ID":8,"Userid":10,"OrderId":"5F306909764","paymentMethod":"COD","PaymentStatus":"Pending","DeliveryStatus":"PENDING","OrderTotal":"774.0","Address":"Newdelhi, Test, Bheem Nagar, Test, Haryana, 110096","Mobilenumber":"9354674477","Iscancelled":false,"Createdate":"2020-05-06T11:51:45.4314123","Modified":"2020-05-05T23:21:45.4314123-07:00","City":"Test","Pendingamount":"774.0","Deleverydate":"07-May-2020","Deleverytime":"09%3A00%20AM%20To%2011%3A00%20AM","Locality":"Bheem Nagar","DeliveryType":"E-Commerce%20Mode","SpecialIntraction":null,"PersonName":null,"DeliveryCharge":50}
     */

    private String Message;
    private String Status;
    private DataBean data;

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
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ID : 8
         * Userid : 10
         * OrderId : 5F306909764
         * paymentMethod : COD
         * PaymentStatus : Pending
         * DeliveryStatus : PENDING
         * OrderTotal : 774.0
         * Address : Newdelhi, Test, Bheem Nagar, Test, Haryana, 110096
         * Mobilenumber : 9354674477
         * Iscancelled : false
         * Createdate : 2020-05-06T11:51:45.4314123
         * Modified : 2020-05-05T23:21:45.4314123-07:00
         * City : Test
         * Pendingamount : 774.0
         * Deleverydate : 07-May-2020
         * Deleverytime : 09%3A00%20AM%20To%2011%3A00%20AM
         * Locality : Bheem Nagar
         * DeliveryType : E-Commerce%20Mode
         * SpecialIntraction : null
         * PersonName : null
         * DeliveryCharge : 50.0
         */

        private int ID;
        private int Userid;
        private String OrderId;
        private String paymentMethod;
        private String PaymentStatus;
        private String DeliveryStatus;
        private String OrderTotal;
        private String Address;
        private String Mobilenumber;
        private boolean Iscancelled;
        private String Createdate;
        private String Modified;
        private String City;
        private String Pendingamount;
        private String Deleverydate;
        private String Deleverytime;
        private String Locality;
        private String DeliveryType;
        private Object SpecialIntraction;
        private Object PersonName;
        private double DeliveryCharge;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getUserid() {
            return Userid;
        }

        public void setUserid(int Userid) {
            this.Userid = Userid;
        }

        public String getOrderId() {
            return OrderId;
        }

        public void setOrderId(String OrderId) {
            this.OrderId = OrderId;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getPaymentStatus() {
            return PaymentStatus;
        }

        public void setPaymentStatus(String PaymentStatus) {
            this.PaymentStatus = PaymentStatus;
        }

        public String getDeliveryStatus() {
            return DeliveryStatus;
        }

        public void setDeliveryStatus(String DeliveryStatus) {
            this.DeliveryStatus = DeliveryStatus;
        }

        public String getOrderTotal() {
            return OrderTotal;
        }

        public void setOrderTotal(String OrderTotal) {
            this.OrderTotal = OrderTotal;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getMobilenumber() {
            return Mobilenumber;
        }

        public void setMobilenumber(String Mobilenumber) {
            this.Mobilenumber = Mobilenumber;
        }

        public boolean isIscancelled() {
            return Iscancelled;
        }

        public void setIscancelled(boolean Iscancelled) {
            this.Iscancelled = Iscancelled;
        }

        public String getCreatedate() {
            return Createdate;
        }

        public void setCreatedate(String Createdate) {
            this.Createdate = Createdate;
        }

        public String getModified() {
            return Modified;
        }

        public void setModified(String Modified) {
            this.Modified = Modified;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String City) {
            this.City = City;
        }

        public String getPendingamount() {
            return Pendingamount;
        }

        public void setPendingamount(String Pendingamount) {
            this.Pendingamount = Pendingamount;
        }

        public String getDeleverydate() {
            return Deleverydate;
        }

        public void setDeleverydate(String Deleverydate) {
            this.Deleverydate = Deleverydate;
        }

        public String getDeleverytime() {
            return Deleverytime;
        }

        public void setDeleverytime(String Deleverytime) {
            this.Deleverytime = Deleverytime;
        }

        public String getLocality() {
            return Locality;
        }

        public void setLocality(String Locality) {
            this.Locality = Locality;
        }

        public String getDeliveryType() {
            return DeliveryType;
        }

        public void setDeliveryType(String DeliveryType) {
            this.DeliveryType = DeliveryType;
        }

        public Object getSpecialIntraction() {
            return SpecialIntraction;
        }

        public void setSpecialIntraction(Object SpecialIntraction) {
            this.SpecialIntraction = SpecialIntraction;
        }

        public Object getPersonName() {
            return PersonName;
        }

        public void setPersonName(Object PersonName) {
            this.PersonName = PersonName;
        }

        public double getDeliveryCharge() {
            return DeliveryCharge;
        }

        public void setDeliveryCharge(double DeliveryCharge) {
            this.DeliveryCharge = DeliveryCharge;
        }
    }
}
