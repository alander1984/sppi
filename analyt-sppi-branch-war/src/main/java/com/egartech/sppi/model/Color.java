package com.egartech.sppi.model;

public enum Color {
    GRAY("Серый"), WHITE("Белый"), BLACK("Чёрный");
    private String code;

    Color(String code) {this.code = code;}

    public String getCode() {
        return code;
    }




}
