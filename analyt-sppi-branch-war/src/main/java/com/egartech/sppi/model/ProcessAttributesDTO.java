package com.egartech.sppi.model;

public class ProcessAttributesDTO {
    private String idType;
    private String idISIN;
    private String idGRN;
    private String priceInPercent;
    private String stockPurchasingPrice;
    private String datePurchasing;
    private String additionalInfo;

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdISIN() {
        return idISIN;
    }

    public void setIdISIN(String idISIN) {
        this.idISIN = idISIN;
    }

    public String getIdGRN() {
        return idGRN;
    }

    public void setIdGRN(String idGRN) {
        this.idGRN = idGRN;
    }

    public String getPriceInPercent() {
        return priceInPercent;
    }

    public void setPriceInPercent(String priceInPercent) {
        this.priceInPercent = priceInPercent;
    }

    public String getStockPurchasingPrice() {
        return stockPurchasingPrice;
    }

    public void setStockPurchasingPrice(String stockPurchasingPrice) {
        this.stockPurchasingPrice = stockPurchasingPrice;
    }

    public String getDatePurchasing() {
        return datePurchasing;
    }

    public void setDatePurchasing(String datePurchasing) {
        this.datePurchasing = datePurchasing;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
