package com.flavours5.models;

import java.util.List;

public class OfferResponse {

    /**
     * Message : Offer List !!!
     * Status : Success
     * Data : [{"ID":3,"oferimages":"http://5flavours.co/Images/Offerimages/202059631517377375173773773751737737737.JPG","active":true,"createdate":"09-May-2020","url":"https://5flavours.co/Home/ProductList?cateid=2&subcatid=70","cateid":"2","subcatid":"70"},{"ID":4,"oferimages":"http://5flavours.co/Images/Offerimages/202059546521861865218618618652186186186.JPG","active":true,"createdate":"09-May-2020","url":"https://5flavours.co/Home/AtoZ?cateid=2","cateid":"2","subcatid":""}]
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
         * ID : 3
         * oferimages : http://5flavours.co/Images/Offerimages/202059631517377375173773773751737737737.JPG
         * active : true
         * createdate : 09-May-2020
         * url : https://5flavours.co/Home/ProductList?cateid=2&subcatid=70
         * cateid : 2
         * subcatid : 70
         */

        private int ID;
        private String oferimages;
        private boolean active;
        private String createdate;
        private String url;
        private String cateid;
        private String subcatid;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getOferimages() {
            return oferimages;
        }

        public void setOferimages(String oferimages) {
            this.oferimages = oferimages;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCateid() {
            return cateid;
        }

        public void setCateid(String cateid) {
            this.cateid = cateid;
        }

        public String getSubcatid() {
            return subcatid;
        }

        public void setSubcatid(String subcatid) {
            this.subcatid = subcatid;
        }
    }
}
