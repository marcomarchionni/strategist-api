package com.marcomarchionni.ibportfolio.models;

import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Entity(name="dividends")
public class Dividend {

    @Id
    private Long dividend_id;
    private Long con_id;
    private String symbol;
    private LocalDate ex_date;
    private LocalDate pay_date;
    private BigDecimal gross_rate;
    private BigDecimal quantity;
    private BigDecimal gross_amount;
    private BigDecimal tax;
    private BigDecimal net_amount;
    private String open_closed;
    static private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ENGLISH);

    public void setDividendId(String conid, String exDate) {
        this.dividend_id = Long.parseLong(conid + exDate );
    }

    public void setConid(String conid) {
        this.con_id = Long.parseLong(conid);
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setExDate(String exDate) {
        this.ex_date = LocalDate.parse(exDate, formatter);
    }

    public void setPayDate(String payDate) {
        this.pay_date = LocalDate.parse(payDate, formatter);
    }

    public void setGrossRate(String grossRate) {
        this.gross_rate = new BigDecimal(grossRate);
    }

    public void setQuantity(String quantity) {
        this.quantity = new BigDecimal(quantity);
    }

    public void setGrossAmount(String grossAmount) {
        this.gross_amount = new BigDecimal(StringUtils.deleteAny(grossAmount, "-"));
    }

    public void setOpenClosed(String openClosed) {
        this.open_closed = openClosed;
    }

    public void setTax(String tax) {
        this.tax = new BigDecimal(StringUtils.deleteAny(tax, "-"));
    }

    public void setNetAmount(String netAmount) {
        this.net_amount = new BigDecimal(StringUtils.deleteAny(netAmount, "-"));
    }
}
