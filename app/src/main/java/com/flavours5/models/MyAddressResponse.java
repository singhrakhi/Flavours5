package com.flavours5.models;

import java.util.List;

public class MyAddressResponse {

    /**
     * Message : Address List !!!
     * Status : Success
     * Data : [{"ID":2,"Userid":10,"DeliveryPersonName":"test","DeliveryPersonMOB":"9354674477","mainAddress":"Newdelhi","Pincode":110096,"Statename":"Haryana","City":"Test","Landmark":"Test","Locality":"Bheem Nagar"},{"ID":3,"Userid":10,"DeliveryPersonName":"test","DeliveryPersonMOB":"6549870321","mainAddress":"Vdb","Pincode":98989898,"Statename":"Haryana","City":"Bxbxb","Landmark":"Bxbx","Locality":"DLF City Phase 2"},{"ID":4,"Userid":10,"DeliveryPersonName":"gsgs","DeliveryPersonMOB":"1234567890","mainAddress":"Vxbx","Pincode":8989899,"Statename":"Haryana","City":"Bxbxb","Landmark":"Bdbdxb","Locality":"DLF City Phase 2"},{"ID":5,"Userid":10,"DeliveryPersonName":"test","DeliveryPersonMOB":"9494949494","mainAddress":"Bxbxbd","Pincode":98959595,"Statename":"Haryana","City":"Bdbdb","Landmark":"Dbdb","Locality":"Civil Lines Road"}]
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
         * ID : 2
         * Userid : 10
         * DeliveryPersonName : test
         * DeliveryPersonMOB : 9354674477
         * mainAddress : Newdelhi
         * Pincode : 110096
         * Statename : Haryana
         * City : Test
         * Landmark : Test
         * Locality : Bheem Nagar
         */

        private int ID;
        private int Userid;
        private String DeliveryPersonName;
        private String DeliveryPersonMOB;
        private String mainAddress;
        private int Pincode;
        private String Statename;
        private String City;
        private String Landmark;
        private String Locality;

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

        public String getDeliveryPersonName() {
            return DeliveryPersonName;
        }

        public void setDeliveryPersonName(String DeliveryPersonName) {
            this.DeliveryPersonName = DeliveryPersonName;
        }

        public String getDeliveryPersonMOB() {
            return DeliveryPersonMOB;
        }

        public void setDeliveryPersonMOB(String DeliveryPersonMOB) {
            this.DeliveryPersonMOB = DeliveryPersonMOB;
        }

        public String getMainAddress() {
            return mainAddress;
        }

        public void setMainAddress(String mainAddress) {
            this.mainAddress = mainAddress;
        }

        public int getPincode() {
            return Pincode;
        }

        public void setPincode(int Pincode) {
            this.Pincode = Pincode;
        }

        public String getStatename() {
            return Statename;
        }

        public void setStatename(String Statename) {
            this.Statename = Statename;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String City) {
            this.City = City;
        }

        public String getLandmark() {
            return Landmark;
        }

        public void setLandmark(String Landmark) {
            this.Landmark = Landmark;
        }

        public String getLocality() {
            return Locality;
        }

        public void setLocality(String Locality) {
            this.Locality = Locality;
        }
    }
}
