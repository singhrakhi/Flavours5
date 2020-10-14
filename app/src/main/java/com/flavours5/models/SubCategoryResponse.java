package com.flavours5.models;

import java.util.List;

public class SubCategoryResponse {

    /**
     * Message : Sub-Category Data !!!
     * Status : Success
     * Data : [{"ID":15,"Subcategoryname":"Pulses ","categoryid":2,"CreateDate":"27-Sep-2018"},{"ID":29,"Subcategoryname":"Edible Oils, Ghee & Vasnaspati","categoryid":2,"CreateDate":"30-Jan-2019"},{"ID":30,"Subcategoryname":"Atta, Besan & Other Flours","categoryid":2,"CreateDate":"21-Oct-2018"},{"ID":33,"Subcategoryname":"Rice, Poha & Other Grains","categoryid":2,"CreateDate":"29-Jan-2019"},{"ID":35,"Subcategoryname":"Dry Fruits, Nuts & Seeds","categoryid":2,"CreateDate":"30-Jan-2019"},{"ID":41,"Subcategoryname":"Salt, Sugar & Jaggery ","categoryid":2,"CreateDate":"30-Jan-2019"},{"ID":54,"Subcategoryname":"Noodles, Sauces & Instant Food","categoryid":2,"CreateDate":"14-Feb-2019"},{"ID":55,"Subcategoryname":"Biscuits, Snacks & Chocolates","categoryid":2,"CreateDate":"11-May-2019"},{"ID":57,"Subcategoryname":"Beverages","categoryid":2,"CreateDate":"24-Apr-2019"},{"ID":67,"Subcategoryname":"Spices & More...","categoryid":2,"CreateDate":"24-Apr-2020"},{"ID":68,"Subcategoryname":"Milk & Bread ","categoryid":2,"CreateDate":"25-Apr-2020"}]
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
         * ID : 15
         * Subcategoryname : Pulses
         * categoryid : 2
         * CreateDate : 27-Sep-2018
         */

        private int ID;
        private String Subcategoryname;
        private int categoryid;
        private String CreateDate;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getSubcategoryname() {
            return Subcategoryname;
        }

        public void setSubcategoryname(String Subcategoryname) {
            this.Subcategoryname = Subcategoryname;
        }

        public int getCategoryid() {
            return categoryid;
        }

        public void setCategoryid(int categoryid) {
            this.categoryid = categoryid;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }
    }
}
