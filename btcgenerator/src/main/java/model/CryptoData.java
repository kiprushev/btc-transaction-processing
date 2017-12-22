package model;

/**
 * Created by Kyrylo_Kiprushev on 12/21/2017.
 */
public class CryptoData {
    private String exchange;
    private String cryptoCurrency;
    private String baseCurrency;
    private String type;
    private String price;
    private String size;
    private String bid;
    private String ask;
    private String open;
    private String high;
    private String low;
    private String volume;
    private String timestamp;

    public CryptoData(String exchange, String cryptoCurrency, String baseCurrency, String type, String price, String size, String bid, String ask, String open, String high, String low, String volume, String timestamp) {
        this.exchange = exchange;
        this.cryptoCurrency = cryptoCurrency;
        this.baseCurrency = baseCurrency;
        this.type = type;
        this.price = price;
        this.size = size;
        this.bid = bid;
        this.ask = ask;
        this.open = open;
        this.high = high;
        this.low = low;
        this.volume = volume;
        this.timestamp = timestamp;
    }
}