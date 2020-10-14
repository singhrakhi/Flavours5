package com.flavours5.models;

import java.util.List;

public class LocalityResponse {

    /**
     * Message : Locality Data !!!
     * Status : Success
     * Data : [{"ID":1,"localityname":"Locality"},{"ID":2,"localityname":"Ardee City"},{"ID":3,"localityname":"Bheem Nagar"},{"ID":4,"localityname":"Arjun Nagar "},{"ID":5,"localityname":"Civil Lines Road"},{"ID":6,"localityname":"Chakkarpur"},{"ID":7,"localityname":"Cyber City"},{"ID":8,"localityname":"DLF City Phase 1"},{"ID":9,"localityname":"DLF City Phase 2"},{"ID":10,"localityname":"DLF City Phase 3"},{"ID":11,"localityname":"DLF City Phase 4"},{"ID":12,"localityname":"DLF City Phase 5"},{"ID":13,"localityname":"DLF World Tech Park "},{"ID":14,"localityname":"Dundahera"},{"ID":15,"localityname":"Ecehelon Institutional Are"},{"ID":16,"localityname":"Galleria Market"},{"ID":17,"localityname":"Golf Course Road"},{"ID":18,"localityname":"Guru Droncharya Metro "},{"ID":19,"localityname":"Gwal Pahari"},{"ID":20,"localityname":"Heritage City"},{"ID":21,"localityname":"Jyoti Park "},{"ID":22,"localityname":"LIC Sec 18"},{"ID":23,"localityname":"MG Road"},{"ID":24,"localityname":"New Palam Vihar"},{"ID":25,"localityname":"Nirvana"},{"ID":26,"localityname":"Old Judicial Complex"},{"ID":27,"localityname":"Old Railway Road"},{"ID":28,"localityname":"Palam Vihar"},{"ID":29,"localityname":"Pratap Nagar"},{"ID":30,"localityname":"Ramnagar"},{"ID":31,"localityname":"Rattan Garden"},{"ID":32,"localityname":"Sadar Bazar"},{"ID":33,"localityname":"Sec 10"},{"ID":34,"localityname":"Sec 10A"},{"ID":35,"localityname":"Sec 10B"},{"ID":36,"localityname":"Sec 11"},{"ID":37,"localityname":"Sec 11A"},{"ID":38,"localityname":"Sec 12 "},{"ID":39,"localityname":"Sec 12A"},{"ID":40,"localityname":"Sec 13 "},{"ID":41,"localityname":"Sec 14"},{"ID":42,"localityname":"Sec 15"},{"ID":43,"localityname":"Sec 15A"},{"ID":44,"localityname":"Sec 15B"},{"ID":45,"localityname":"Sec 16"},{"ID":46,"localityname":"Sec 17"},{"ID":47,"localityname":"Sec 18"},{"ID":48,"localityname":"Sec 19"},{"ID":49,"localityname":"Sec 20"},{"ID":50,"localityname":"Sec 21"},{"ID":51,"localityname":"Sec 22"},{"ID":52,"localityname":"Sec 23"},{"ID":53,"localityname":"Sec 23A"},{"ID":54,"localityname":"Sec 25"},{"ID":55,"localityname":"Sec 26"},{"ID":56,"localityname":"Sec 27"},{"ID":57,"localityname":"Sec 28"},{"ID":58,"localityname":"Sec 29"},{"ID":59,"localityname":"Sec 3"},{"ID":60,"localityname":"Sec 30"},{"ID":61,"localityname":"Sec 31"},{"ID":62,"localityname":"Sec 32"},{"ID":63,"localityname":"Sec 32 (Ciena)"},{"ID":64,"localityname":"Sec 33"},{"ID":65,"localityname":"Sec 34"},{"ID":66,"localityname":"Sec 36"},{"ID":67,"localityname":"Sec 37"},{"ID":68,"localityname":"Sec 37A"},{"ID":69,"localityname":"Sec 37B"},{"ID":70,"localityname":"Sec 37C"},{"ID":71,"localityname":"Sec 37D"},{"ID":72,"localityname":"Sec 38"},{"ID":73,"localityname":"Sec 39"},{"ID":74,"localityname":"Sec 4"},{"ID":75,"localityname":"Sec 40"},{"ID":76,"localityname":"Sec 41"},{"ID":77,"localityname":"Sec 42"},{"ID":78,"localityname":"Sec 43"},{"ID":79,"localityname":"Sec 44"},{"ID":80,"localityname":"Sec 45"},{"ID":81,"localityname":"Sec 46"},{"ID":82,"localityname":"Sec 47"},{"ID":83,"localityname":"Sec 48"},{"ID":84,"localityname":"Sec 49"},{"ID":85,"localityname":"Sec 5"},{"ID":86,"localityname":"Sec 50"},{"ID":87,"localityname":"Sec 51"},{"ID":88,"localityname":"Sec 52"},{"ID":89,"localityname":"Sec 52A"},{"ID":90,"localityname":"Sec 53"},{"ID":91,"localityname":"Sec 54"},{"ID":92,"localityname":"Sec 55"},{"ID":93,"localityname":"Sec 56"},{"ID":94,"localityname":"Sec 57"},{"ID":95,"localityname":"Sec 6"},{"ID":96,"localityname":"Sec 66"},{"ID":97,"localityname":"Sec 69"},{"ID":98,"localityname":"Sec 7"},{"ID":99,"localityname":"Sec 70"},{"ID":100,"localityname":"Sec 71"},{"ID":101,"localityname":"Sec 72"},{"ID":102,"localityname":"Sec 79"},{"ID":103,"localityname":"Sec 8"},{"ID":104,"localityname":"Sec 80"},{"ID":105,"localityname":"Sec 83"},{"ID":106,"localityname":"Sec 86"},{"ID":107,"localityname":"Sec 9"},{"ID":108,"localityname":"Sec 91"},{"ID":109,"localityname":"Shiv Puri Old Gurgaon"},{"ID":110,"localityname":"Shivaji Nagar"},{"ID":111,"localityname":"Sikandarpur"},{"ID":112,"localityname":"Silokehra Gurgaon"},{"ID":113,"localityname":"Sohna Road"},{"ID":114,"localityname":"South City 1"},{"ID":115,"localityname":"South City 2"},{"ID":116,"localityname":"Surya Vihar Apt."},{"ID":117,"localityname":"Sushant Lok 1"},{"ID":118,"localityname":"Sushant Lok 2"},{"ID":119,"localityname":"Sushant Lok 3"},{"ID":120,"localityname":"Udyog Vihar Phase 1"},{"ID":121,"localityname":"Udyog Vihar Phase 2"},{"ID":122,"localityname":"Udyog Vihar Phase 3"},{"ID":123,"localityname":"Udyog Vihar Phase 4"},{"ID":124,"localityname":"Udyog Vihar Phase 5"},{"ID":125,"localityname":"Vijay Park"},{"ID":126,"localityname":"Vipul Trade Centre"}]
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
         * localityname : Locality
         */

        private int ID;
        private String localityname;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getLocalityname() {
            return localityname;
        }

        public void setLocalityname(String localityname) {
            this.localityname = localityname;
        }
    }
}
