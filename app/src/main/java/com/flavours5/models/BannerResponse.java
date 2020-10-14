package com.flavours5.models;

import java.util.List;

public class BannerResponse {

    /**
     * Message : Banner Data !!!
     * Status : Success
     * Data : [{"ID":1,"Bannerurl":"https://5flavours.co.in/Home/AtoZ?cateid=1","Bannerimage":"http://5flavours.co/Images/Bannerimages/20194231459511611651161161165116116116.jpg","Bannertext":null,"CreateDate":"23-Apr-2019","active":true}]
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
         * ID : 1
         * Bannerurl : https://5flavours.co.in/Home/AtoZ?cateid=1
         * Bannerimage : http://5flavours.co/Images/Bannerimages/20194231459511611651161161165116116116.jpg
         * Bannertext : null
         * CreateDate : 23-Apr-2019
         * active : true
         */

        private int ID;
        private String Bannerurl;
        private String Bannerimage;
        private Object Bannertext;
        private String CreateDate;
        private boolean active;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getBannerurl() {
            return Bannerurl;
        }

        public void setBannerurl(String Bannerurl) {
            this.Bannerurl = Bannerurl;
        }

        public String getBannerimage() {
            return Bannerimage;
        }

        public void setBannerimage(String Bannerimage) {
            this.Bannerimage = Bannerimage;
        }

        public Object getBannertext() {
            return Bannertext;
        }

        public void setBannertext(Object Bannertext) {
            this.Bannertext = Bannertext;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }
    }
}
