package com.egartech.sppi.model;

public class Product {
    private String productCode;
    private String firstQuestionCode;

    public Product(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getFirstQuestionCode() {
        return firstQuestionCode;
    }

    public void setFirstQuestionCode(String firstQuestionCode) {
        this.firstQuestionCode = firstQuestionCode;
    }
}
