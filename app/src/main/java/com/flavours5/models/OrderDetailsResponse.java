package com.flavours5.models;

import java.util.List;

public class OrderDetailsResponse {


    /**
     * Message : Your Order List !!!
     * Status : Success
     * Data : [{"OrderId":"5F579145112","ProductName":"Kala Chana 500 g","ProductImage":"http://5flavours.co/Images/Productimages/20204252149414674674146746746741467467467.jpg","Unit":"500 g","Quantity":2,"DiscountedPrice":43,"Price":63},{"OrderId":"5F579145112","ProductName":"Kabuli Chana 500 g","ProductImage":"http://5flavours.co/Images/Productimages/20204252158403933934039339339340393393393.JPG","Unit":"500 g","Quantity":5,"DiscountedPrice":56,"Price":69},{"OrderId":"5F579145112","ProductName":"white Til 500 g","ProductImage":"http://5flavours.co/Images/Productimages/20204299263931313931313139313131.JPG","Unit":"500 g","Quantity":2,"DiscountedPrice":121,"Price":144},{"OrderId":"5F579145112","ProductName":"Rajma Chitra 500 g","ProductImage":"http://5flavours.co/Images/Productimages/2020429956115315311531531531153153153.JPG","Unit":"500 g","Quantity":3,"DiscountedPrice":65,"Price":79}]
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
         * OrderId : 5F579145112
         * ProductName : Kala Chana 500 g
         * ProductImage : http://5flavours.co/Images/Productimages/20204252149414674674146746746741467467467.jpg
         * Unit : 500 g
         * Quantity : 2
         * DiscountedPrice : 43.0
         * Price : 63.0
         */

        private String OrderId;
        private String ProductName;
        private String ProductImage;
        private String Unit;
        private int Quantity;
        private double DiscountedPrice;
        private double Price;

        public String getOrderId() {
            return OrderId;
        }

        public void setOrderId(String OrderId) {
            this.OrderId = OrderId;
        }

        public String getProductName() {
            return ProductName;
        }

        public void setProductName(String ProductName) {
            this.ProductName = ProductName;
        }

        public String getProductImage() {
            return ProductImage;
        }

        public void setProductImage(String ProductImage) {
            this.ProductImage = ProductImage;
        }

        public String getUnit() {
            return Unit;
        }

        public void setUnit(String Unit) {
            this.Unit = Unit;
        }

        public int getQuantity() {
            return Quantity;
        }

        public void setQuantity(int Quantity) {
            this.Quantity = Quantity;
        }

        public double getDiscountedPrice() {
            return DiscountedPrice;
        }

        public void setDiscountedPrice(double DiscountedPrice) {
            this.DiscountedPrice = DiscountedPrice;
        }

        public double getPrice() {
            return Price;
        }

        public void setPrice(double Price) {
            this.Price = Price;
        }
    }
}
