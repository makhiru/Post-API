package com.example.post_api.Models;

import java.util.List;

public class Product_data {

    private Integer connection;
    private Integer result;
    private List<Productdatas> productdata;

    public Integer getConnection() {
        return connection;
    }

    public void setConnection(Integer connection) {
        this.connection = connection;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public List<Productdatas> getProductdata() {
        return productdata;
    }

    public void setProductdata(List<Productdatas> productdata) {
        this.productdata = productdata;
    }

    public class Productdatas {
        private String Id;
        private String UID;
        private String PRO_NAME;
        private String PRO_PRICE;
        private String PRO_DES;
        private String PRO_IMAGE;

        public String getID() {
            return Id;
        }

        public void setID(String ID) {
            this.Id = ID;
        }

        public String getUID() {
            return UID;
        }

        public void setUID(String UID) {
            this.UID = UID;
        }

        public String getPRO_NAME() {
            return PRO_NAME;
        }

        public void setPRO_NAME(String PRO_NAME) {
            this.PRO_NAME = PRO_NAME;
        }

        public String getPRO_PRICE() {
            return PRO_PRICE;
        }

        public void setPRO_PRICE(String PRO_PRICE) {
            this.PRO_PRICE = PRO_PRICE;
        }

        public String getPRO_DES() {
            return PRO_DES;
        }

        public void setPRO_DES(String PRO_DES) {
            this.PRO_DES = PRO_DES;
        }

        public String getPRO_IMAGE() {
            return PRO_IMAGE;
        }

        public void setPRO_IMAGE(String PRO_IMAGE) {
            this.PRO_IMAGE = PRO_IMAGE;
        }
    }
}
