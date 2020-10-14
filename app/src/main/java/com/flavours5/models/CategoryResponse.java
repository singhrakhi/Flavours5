package com.flavours5.models;

import java.util.List;

public class CategoryResponse {

    /**
     * Message : Category Data !!!
     * Status : Success
     * Data : [{"ID":2,"Categoryname":"FOOD ITEMS","Categoryrimage":"5flavours.coImages/Categoryimages/20194221332262782782627827827826278278278.JPG","CreateDate":"24-Apr-2020","categoryPoss":1},{"ID":3,"Categoryname":"NON-FOOD ITEMS","Categoryrimage":"5flavours.coImages/Categoryimages/20191301551565965956596596595659659659.jpg","CreateDate":"24-Apr-2020","categoryPoss":2},{"ID":5,"Categoryname":"HEALTH & WELLNESS","Categoryrimage":"5flavours.coImages/Categoryimages/20191301910474944944749449449447494494494.jpg","CreateDate":"24-Apr-2020","categoryPoss":3},{"ID":6,"Categoryname":"OFFERS","Categoryrimage":"5flavours.coImages/Categoryimages/20194221340233333323333333332333333333.JPG","CreateDate":"20-Aug-2019","categoryPoss":4}]
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
         * Categoryname : FOOD ITEMS
         * Categoryrimage : 5flavours.coImages/Categoryimages/20194221332262782782627827827826278278278.JPG
         * CreateDate : 24-Apr-2020
         * categoryPoss : 1
         */

        private int ID;
        private String Categoryname;
        private String Categoryrimage;
        private String CreateDate;
        private int categoryPoss;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getCategoryname() {
            return Categoryname;
        }

        public void setCategoryname(String Categoryname) {
            this.Categoryname = Categoryname;
        }

        public String getCategoryrimage() {
            return Categoryrimage;
        }

        public void setCategoryrimage(String Categoryrimage) {
            this.Categoryrimage = Categoryrimage;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }

        public int getCategoryPoss() {
            return categoryPoss;
        }

        public void setCategoryPoss(int categoryPoss) {
            this.categoryPoss = categoryPoss;
        }
    }
}
