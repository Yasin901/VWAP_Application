package com.vwapapplication.model;

public class Trade {

    private final String epic;
    private final String isin;
    private final String tradeRef;
    private final String tradeType;
    private final int quantity;
    private final double price;

    public Trade(String epic, String isin, String tradeRef, String tradeType, int quantity, double price) {
        this.epic = epic;
        this.isin = isin;
        this.tradeRef = tradeRef;
        this.tradeType = tradeType;
        this.quantity = quantity;
        this.price = price;
    }

    public String getEpic() {
        return epic;
    }
    public String getIsin() {
        return isin;
    }
    public String getTradeRef() {
        return tradeRef;
    }
    public String getTradeType() {
        return tradeType;
    }
    public int getQuantity() {
        return quantity;
    }
    public double getPrice() {
        return price;
    }
}
