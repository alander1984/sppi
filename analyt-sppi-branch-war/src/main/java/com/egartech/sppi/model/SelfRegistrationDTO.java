package com.egartech.sppi.model;

public class SelfRegistrationDTO {

    private String username;
    private String userFIO;
    private String userPersonnelNum;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserFIO() {
        return userFIO;
    }

    public void setUserFIO(String userFIO) {
        this.userFIO = userFIO;
    }

    public String getUserPersonnelNum() {
        return userPersonnelNum;
    }

    public void setUserPersonnelNum(String userPersonnelNum) {
        this.userPersonnelNum = userPersonnelNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
