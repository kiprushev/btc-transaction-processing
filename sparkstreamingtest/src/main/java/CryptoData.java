import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Kyrylo_Kiprushev on 12/21/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoData implements Serializable {
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public void setCryptocurrency(String cryptocurrency) {
        this.cryptocurrency = cryptocurrency;
    }

    public void setBasecurrency(String basecurrency) {
        this.basecurrency = basecurrency;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    private String exchange;
    private String cryptocurrency;
    private String basecurrency;
    private String type;
    private String price;
    private String size;
    private String bid;
    private String ask;
    private String open;
    private String high;
    private String low;

    public CryptoData() {
    }

    public String getExchange() {

        return exchange;
    }

    public String getCryptocurrency() {
        return cryptocurrency;
    }

    public String getBasecurrency() {
        return basecurrency;
    }

    public String getType() {
        return type;
    }

    public String getPrice() {
        return price;
    }

    public String getSize() {
        return size;
    }

    public String getBid() {
        return bid;
    }

    public String getAsk() {
        return ask;
    }

    public String getOpen() {
        return open;
    }

    public String getHigh() {
        return high;
    }

    public String getLow() {
        return low;
    }

    public String getVolume() {
        return volume;
    }

    public String getTimestamp() {
        return timestamp;
    }

    private String volume;
    private String timestamp;

    @Override
    public String toString() {
        return "CryptoData{" +
                "exchange='" + exchange + '\'' +
                ", cryptocurrency='" + cryptocurrency + '\'' +
                ", basecurrency='" + basecurrency + '\'' +
                ", type='" + type + '\'' +
                ", price='" + price + '\'' +
                ", size='" + size + '\'' +
                ", bid='" + bid + '\'' +
                ", ask='" + ask + '\'' +
                ", open='" + open + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", volume='" + volume + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    public CryptoData(String exchange, String cryptoCurrency, String baseCurrency, String type, String price, String size, String bid, String ask, String open, String high, String low, String volume, String timestamp) {
        this.exchange = exchange;
        this.cryptocurrency = cryptoCurrency;
        this.basecurrency = baseCurrency;
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