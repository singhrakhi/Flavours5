package com.flavours5.models;

import java.util.List;

public class CartDetailsResponse {

    /**
     * Message : Kart Product List !!!
     * Status : Success
     * Data : [{"ID":10,"ProductUnitId":64,"ProductID":40,"UserID":10,"Price":79,"Quantity":1,"Discount":26,"ProductName":"Arhar Dal 500 g","ProductImages":"http://5flavours.co/Images/Productimages/2020425227355695693556956956935569569569.JPG","Unit":"500 g","cashback":"","Cashbacktype":"No Cashacktype","DiscountedPrice":59},{"ID":11,"ProductUnitId":60,"ProductID":36,"UserID":10,"Price":63,"Quantity":1,"Discount":32,"ProductName":"Kala Chana 500 g","ProductImages":"http://5flavours.co/Images/Productimages/20204252149414674674146746746741467467467.jpg","Unit":"500 g","cashback":"","Cashbacktype":"No Cashacktype","DiscountedPrice":43},{"ID":12,"ProductUnitId":36,"ProductID":22,"UserID":10,"Price":85,"Quantity":1,"Discount":16,"ProductName":"Maida 1 Kg","ProductImages":"http://5flavours.co/Images/Productimages/2020425119298908902989089089029890890890.JPG","Unit":"1 Kg","cashback":"","Cashbacktype":"No Cashbacktype","DiscountedPrice":72}]
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
         * ID : 10
         * ProductUnitId : 64
         * ProductID : 40
         * UserID : 10
         * Price : 79.0
         * Quantity : 1
         * Discount : 26
         * ProductName : Arhar Dal 500 g
         * ProductImages : http://5flavours.co/Images/Productimages/2020425227355695693556956956935569569569.JPG
         * Unit : 500 g
         * cashback :
         * Cashbacktype : No Cashacktype
         * DiscountedPrice : 59
         */

        private int ID;
        private int ProductUnitId;
        private int ProductID;
        private int UserID;
        private double Price;
        private int Quantity;
        private int Discount;
        private String ProductName;
        private String ProductImages;
        private String Unit;
        private String cashback;
        private String Cashbacktype;
        private int DiscountedPrice;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getProductUnitId() {
            return ProductUnitId;
        }

        public void setProductUnitId(int ProductUnitId) {
            this.ProductUnitId = ProductUnitId;
        }

        public int getProductID() {
            return ProductID;
        }

        public void setProductID(int ProductID) {
            this.ProductID = ProductID;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public double getPrice() {
            return Price;
        }

        public void setPrice(double Price) {
            this.Price = Price;
        }

        public int getQuantity() {
            return Quantity;
        }

        public void setQuantity(int Quantity) {
            this.Quantity = Quantity;
        }

        public int getDiscount() {
            return Discount;
        }

        public void setDiscount(int Discount) {
            this.Discount = Discount;
        }

        public String getProductName() {
            return ProductName;
        }

        public void setProductName(String ProductName) {
            this.ProductName = ProductName;
        }

        public String getProductImages() {
            return ProductImages;
        }

        public void setProductImages(String ProductImages) {
            this.ProductImages = ProductImages;
        }

        public String getUnit() {
            return Unit;
        }

        public void setUnit(String Unit) {
            this.Unit = Unit;
        }

        public String getCashback() {
            return cashback;
        }

        public void setCashback(String cashback) {
            this.cashback = cashback;
        }

        public String getCashbacktype() {
            return Cashbacktype;
        }

        public void setCashbacktype(String Cashbacktype) {
            this.Cashbacktype = Cashbacktype;
        }

        public int getDiscountedPrice() {
            return DiscountedPrice;
        }

        public void setDiscountedPrice(int DiscountedPrice) {
            this.DiscountedPrice = DiscountedPrice;
        }
    }
}
