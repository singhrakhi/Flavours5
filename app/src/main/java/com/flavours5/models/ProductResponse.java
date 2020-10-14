package com.flavours5.models;

import java.util.List;

public class ProductResponse {

    /**
     * Message : Product Data !!!
     * Status : Success
     * Data : [{"ProductId":36,"ProductUnitId":60,"ProductName":"Kala Chana 500 g","CategoryId":2,"SubCategoryId":15,"Description":"Kala Chana 500 g","Active":true,"ProductImage":"http://5flavours.co/Images/Productimages/20204252149414674674146746746741467467467.jpg","Price":63,"DiscountPrice":43,"Stock":"54","Hsncode":"No HSNCODE","Unit":"500 g","Discount":32,"Cashback":"","Cashbacktype":"No Cashacktype","Capping":9},{"ProductId":38,"ProductUnitId":62,"ProductName":"Kabuli Chana 500 g","CategoryId":2,"SubCategoryId":15,"Description":"Kabuli Chana 500 g","Active":false,"ProductImage":"http://5flavours.co/Images/Productimages/20204252158403933934039339339340393393393.JPG","Price":69,"DiscountPrice":56,"Stock":"53","Hsncode":"No HSNCODE","Unit":"500 g","Discount":19,"Cashback":"","Cashbacktype":"No Cashacktype","Capping":6},{"ProductId":40,"ProductUnitId":64,"ProductName":"Arhar Dal 500 g","CategoryId":2,"SubCategoryId":15,"Description":"Arhar Dal 500 g","Active":false,"ProductImage":"http://5flavours.co/Images/Productimages/2020425227355695693556956956935569569569.JPG","Price":79,"DiscountPrice":59,"Stock":"83","Hsncode":"No HSNCODE","Unit":"500 g","Discount":26,"Cashback":"","Cashbacktype":"No Cashacktype","Capping":5}]
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
         * ProductId : 36
         * ProductUnitId : 60
         * ProductName : Kala Chana 500 g
         * CategoryId : 2
         * SubCategoryId : 15
         * Description : Kala Chana 500 g
         * Active : true
         * ProductImage : http://5flavours.co/Images/Productimages/20204252149414674674146746746741467467467.jpg
         * Price : 63.0
         * DiscountPrice : 43
         * Stock : 54
         * Hsncode : No HSNCODE
         * Unit : 500 g
         * Discount : 32
         * Cashback :
         * Cashbacktype : No Cashacktype
         * Capping : 9
         */

        private int ProductId;
        private int ProductUnitId;
        private String ProductName;
        private int CategoryId;
        private int SubCategoryId;
        private String Description;
        private boolean Active;
        private String ProductImage;
        private double Price;
        private int DiscountPrice;
        private String Stock;
        private String Hsncode;
        private String Unit;
        private int Discount;
        private String Cashback;
        private String Cashbacktype;
        private int Capping;

        public int getProductId() {
            return ProductId;
        }

        public void setProductId(int ProductId) {
            this.ProductId = ProductId;
        }

        public int getProductUnitId() {
            return ProductUnitId;
        }

        public void setProductUnitId(int ProductUnitId) {
            this.ProductUnitId = ProductUnitId;
        }

        public String getProductName() {
            return ProductName;
        }

        public void setProductName(String ProductName) {
            this.ProductName = ProductName;
        }

        public int getCategoryId() {
            return CategoryId;
        }

        public void setCategoryId(int CategoryId) {
            this.CategoryId = CategoryId;
        }

        public int getSubCategoryId() {
            return SubCategoryId;
        }

        public void setSubCategoryId(int SubCategoryId) {
            this.SubCategoryId = SubCategoryId;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public boolean isActive() {
            return Active;
        }

        public void setActive(boolean Active) {
            this.Active = Active;
        }

        public String getProductImage() {
            return ProductImage;
        }

        public void setProductImage(String ProductImage) {
            this.ProductImage = ProductImage;
        }

        public double getPrice() {
            return Price;
        }

        public void setPrice(double Price) {
            this.Price = Price;
        }

        public int getDiscountPrice() {
            return DiscountPrice;
        }

        public void setDiscountPrice(int DiscountPrice) {
            this.DiscountPrice = DiscountPrice;
        }

        public String getStock() {
            return Stock;
        }

        public void setStock(String Stock) {
            this.Stock = Stock;
        }

        public String getHsncode() {
            return Hsncode;
        }

        public void setHsncode(String Hsncode) {
            this.Hsncode = Hsncode;
        }

        public String getUnit() {
            return Unit;
        }

        public void setUnit(String Unit) {
            this.Unit = Unit;
        }

        public int getDiscount() {
            return Discount;
        }

        public void setDiscount(int Discount) {
            this.Discount = Discount;
        }

        public String getCashback() {
            return Cashback;
        }

        public void setCashback(String Cashback) {
            this.Cashback = Cashback;
        }

        public String getCashbacktype() {
            return Cashbacktype;
        }

        public void setCashbacktype(String Cashbacktype) {
            this.Cashbacktype = Cashbacktype;
        }

        public int getCapping() {
            return Capping;
        }

        public void setCapping(int Capping) {
            this.Capping = Capping;
        }
    }
}
