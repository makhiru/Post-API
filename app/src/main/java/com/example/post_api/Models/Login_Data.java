package com.example.post_api.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login_Data {
    @SerializedName("connection")
    @Expose
    private Integer connection;
    @SerializedName("result")
    @Expose
    private Integer result;
    @SerializedName("userdata")
    @Expose
    private User_Data userdata;

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

    public User_Data getUserdata() {
        return userdata;
    }

    public void setUserdata(User_Data userdata) {
        this.userdata = userdata;
    }

    public class User_Data{

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("gmail")
        @Expose
        private String gmail;
        @SerializedName("password")
        @Expose
        private String password;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGmail() {
            return gmail;
        }

        public void setGmail(String gmail) {
            this.gmail = gmail;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
