package com.marcomarchionni.ibportfolio.models;

import lombok.Data;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
@Entity(name="dividend")
public class Dividend {

    @Id
    @Column(name="dividend_id")
    private Long dividendId;

    @Column(name="con_id")
    private Long conId;

    @Column(name="symbol")
    private String symbol;

    @Column(name="ex_date")
    private LocalDate exDate;

    @Column(name="pay_date")
    private LocalDate payDate;

    @Column(name="gross_rate")
    private BigDecimal grossRate;

    @Column(name="quantity")
    private BigDecimal quantity;

    @Column(name="gross_amount")
    private BigDecimal grossAmount;

    @Column(name="tax")
    private BigDecimal tax;

    @Column(name="net_amount")
    private BigDecimal netAmount;

    @Column(name="open_closed")
    private String openClosed;

    static private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ENGLISH);

    public void setDividendId(String conId, String exDate) {
        this.dividendId = Long.parseLong(conId + exDate );
    }

////    public void setConId(String conId) {
//        this.conId = Long.parseLong(conId);
//    }

//    public void setSymbol(String symbol) {
//        this.symbol = symbol;
//    }

//    public void setExDate(String exDate) {
//        this.exDate = LocalDate.parse(exDate, formatter);
//    }

//    public void setPayDate(String payDate) {
//        this.payDate = LocalDate.parse(payDate, formatter);
//    }

//    public void setGrossRate(String grossRate) {
//        this.grossRate = new BigDecimal(grossRate);
//    }

//    public void setQuantity(String quantity) {
//        this.quantity = new BigDecimal(quantity);
//    }

//    public void setGrossAmount(String grossAmount) {
//        this.grossAmount = new BigDecimal(StringUtils.deleteAny(grossAmount, "-"));
//    }

//    public void setOpenClosed(String openClosed) {
//        this.openClosed = openClosed;
//    }

//    public void setTax(String tax) {
//        this.tax = new BigDecimal(StringUtils.deleteAny(tax, "-"));
//    }

//    public void setNetAmount(String netAmount) {
//        this.netAmount = new BigDecimal(StringUtils.deleteAny(netAmount, "-"));
//    }
}
