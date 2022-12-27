package com.marcomarchionni.ibportfolio.model.dtos.flex;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;

//TODO: update Dto to include all level of detail
@XmlRootElement(name = "FlexQueryResponse")
public class FlexQueryResponseDto {

    @XmlElement(name = "FlexStatements")
    ArrayList<FlexStatements> FlexStatementsObject;

    private String queryName;

    private String type;


    // Getter Methods

    @XmlElement(name = "FlexStatements")
    public ArrayList<FlexStatements> getFlexStatements() {
        return FlexStatementsObject;
    }

    @XmlAttribute(name = "queryName")
    public String getQueryName() {
        return queryName;
    }

    @XmlAttribute(name = "type")
    public String getType() {
        return type;
    }

    // Setter Methods

    public void setFlexStatements(ArrayList<FlexStatements> FlexStatementsObject) {
        this.FlexStatementsObject = FlexStatementsObject;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class FlexStatements {
        @XmlElement(name = "FlexStatement")
        ArrayList<FlexStatement> FlexStatementObject;
        private String count;


        // Getter Methods

        public ArrayList<FlexStatement> getFlexStatement() {
            return FlexStatementObject;
        }

        @XmlAttribute(name = "count")
        public String getCount() {
            return count;
        }

        // Setter Methods
        public void setFlexStatement(ArrayList<FlexStatement> FlexStatementObject) {
            this.FlexStatementObject = FlexStatementObject;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }

    public static class FlexStatement {
        @XmlElement(name = "AccountInformation")
        AccountInformation AccountInformationObject;
        @XmlElement(name = "OpenPositions")
        OpenPositions OpenPositionsObject;
        @XmlElement(name = "Trades")
        Trades TradesObject;
        @XmlElement(name = "ChangeInDividendAccruals")
        ChangeInDividendAccruals ChangeInDividendAccrualsObject;
        @XmlElement(name="OpenDividendAccruals")
        OpenDividendAccruals OpenDividendAccrualsObject;
        private String accountId;
        private String fromDate;
        private String toDate;
        private String period;
        private String whenGenerated;


        // Getter Methods

        public AccountInformation getAccountInformation() {
            return AccountInformationObject;
        }

        public OpenPositions getOpenPositions() {
            return OpenPositionsObject;
        }

        public Trades getTrades() {
            return TradesObject;
        }

        public ChangeInDividendAccruals getChangeInDividendAccruals() {
            return ChangeInDividendAccrualsObject;
        }

        public OpenDividendAccruals getOpenDividendAccruals() {
            return OpenDividendAccrualsObject;
        }

        @XmlAttribute(name = "accountId")
        public String getAccountId() {
            return accountId;
        }

        @XmlAttribute(name = "fromDate")
        public String getFromDate() {
            return fromDate;
        }

        @XmlAttribute(name = "toDate")
        public String getToDate() {
            return toDate;
        }

        @XmlAttribute(name = "period")
        public String getPeriod() {
            return period;
        }

        @XmlAttribute(name = "whenGenerated")
        public String getWhenGenerated() {
            return whenGenerated;
        }

        // Setter Methods

        public void setAccountInformation(AccountInformation AccountInformationObject) {
            this.AccountInformationObject = AccountInformationObject;
        }

        public void setOpenPositions(OpenPositions OpenPositionsObject) {
            this.OpenPositionsObject = OpenPositionsObject;
        }

        public void setTrades(Trades TradesObject) {
            this.TradesObject = TradesObject;
        }

        public void setChangeInDividendAccruals(ChangeInDividendAccruals ChangeInDividendAccrualsObject) {
            this.ChangeInDividendAccrualsObject = ChangeInDividendAccrualsObject;
        }

        public void setOpenDividendAccrualsObject(OpenDividendAccruals openDividendAccrualsObject) {
            OpenDividendAccrualsObject = openDividendAccrualsObject;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public void setFromDate(String fromDate) {
            this.fromDate = fromDate;
        }

        public void setToDate(String toDate) {
            this.toDate = toDate;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public void setWhenGenerated(String whenGenerated) {
            this.whenGenerated = whenGenerated;
        }
    }

    public static class ChangeInDividendAccruals {
        @XmlElement(name = "ChangeInDividendAccrual")
        ArrayList<ChangeInDividendAccrual> ChangeInDividendAccrual = new ArrayList<>();


        // Getter Methods
        public ArrayList<FlexQueryResponseDto.ChangeInDividendAccrual> getChangeInDividendAccrual() {
            return ChangeInDividendAccrual;
        }

        // Setter Methods
        public void setChangeInDividendAccrual(ArrayList<FlexQueryResponseDto.ChangeInDividendAccrual> changeInDividendAccrual) {
            ChangeInDividendAccrual = changeInDividendAccrual;
        }
    }

    public static class ChangeInDividendAccrual {
        private String accountId = "";
        private String acctAlias = "";
        private String model = "";
        private String currency = "";
        private String fxRateToBase = "";
        private String assetCategory = "";
        private String symbol = "";
        private String description = "";
        private String conid = "";
        private String securityID = "";
        private String securityIDType = "";
        private String cusip = "";
        private String isin = "";
        private String underlyingConid = "";
        private String underlyingSymbol = "";
        private String issuer = "";
        private String multiplier = "";
        private String strike = "";
        private String expiry = "";
        private String putCall = "";
        private String date = "";
        private String exDate = "";
        private String payDate = "";
        private String quantity = "";
        private String tax = "";
        private String fee = "";
        private String grossRate = "";
        private String grossAmount = "";
        private String netAmount = "";
        private String code = "";
        private String fromAcct = "";
        private String toAcct = "";
        private String principalAdjustFactor = "";


        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        @XmlAttribute(name = "accountId")
        public String getAccountId() {
            return accountId;
        }

        public void setAcctAlias(String acctAlias) {
            this.acctAlias = acctAlias;
        }

        @XmlAttribute(name = "acctAlias")
        public String getAcctAlias() {
            return acctAlias;
        }

        public void setModel(String model) {
            this.model = model;
        }

        @XmlAttribute(name = "model")
        public String getModel() {
            return model;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        @XmlAttribute(name = "currency")
        public String getCurrency() {
            return currency;
        }

        public void setFxRateToBase(String fxRateToBase) {
            this.fxRateToBase = fxRateToBase;
        }

        @XmlAttribute(name = "fxRateToBase")
        public String getFxRateToBase() {
            return fxRateToBase;
        }

        public void setAssetCategory(String assetCategory) {
            this.assetCategory = assetCategory;
        }

        @XmlAttribute(name = "assetCategory")
        public String getAssetCategory() {
            return assetCategory;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        @XmlAttribute(name = "symbol")
        public String getSymbol() {
            return symbol;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @XmlAttribute(name = "description")
        public String getDescription() {
            return description;
        }

        public void setConid(String conid) {
            this.conid = conid;
        }

        @XmlAttribute(name = "conid")
        public String getConid() {
            return conid;
        }

        public void setSecurityID(String securityID) {
            this.securityID = securityID;
        }

        @XmlAttribute(name = "securityID")
        public String getSecurityID() {
            return securityID;
        }

        public void setSecurityIDType(String securityIDType) {
            this.securityIDType = securityIDType;
        }

        @XmlAttribute(name = "securityIDType")
        public String getSecurityIDType() {
            return securityIDType;
        }

        public void setCusip(String cusip) {
            this.cusip = cusip;
        }

        @XmlAttribute(name = "cusip")
        public String getCusip() {
            return cusip;
        }

        public void setIsin(String isin) {
            this.isin = isin;
        }

        @XmlAttribute(name = "isin")
        public String getIsin() {
            return isin;
        }

        public void setUnderlyingConid(String underlyingConid) {
            this.underlyingConid = underlyingConid;
        }

        @XmlAttribute(name = "underlyingConid")
        public String getUnderlyingConid() {
            return underlyingConid;
        }

        public void setUnderlyingSymbol(String underlyingSymbol) {
            this.underlyingSymbol = underlyingSymbol;
        }

        @XmlAttribute(name = "underlyingSymbol")
        public String getUnderlyingSymbol() {
            return underlyingSymbol;
        }

        public void setIssuer(String issuer) {
            this.issuer = issuer;
        }

        @XmlAttribute(name = "issuer")
        public String getIssuer() {
            return issuer;
        }

        public void setMultiplier(String multiplier) {
            this.multiplier = multiplier;
        }

        @XmlAttribute(name = "multiplier")
        public String getMultiplier() {
            return multiplier;
        }

        public void setStrike(String strike) {
            this.strike = strike;
        }

        @XmlAttribute(name = "strike")
        public String getStrike() {
            return strike;
        }

        public void setExpiry(String expiry) {
            this.expiry = expiry;
        }

        @XmlAttribute(name = "expiry")
        public String getExpiry() {
            return expiry;
        }

        public void setPutCall(String putCall) {
            this.putCall = putCall;
        }

        @XmlAttribute(name = "putCall")
        public String getPutCall() {
            return putCall;
        }

        public void setDate(String date) {
            this.date = date;
        }

        @XmlAttribute(name = "date")
        public String getDate() {
            return date;
        }

        public void setExDate(String exDate) {
            this.exDate = exDate;
        }

        @XmlAttribute(name = "exDate")
        public String getExDate() {
            return exDate;
        }

        public void setPayDate(String payDate) {
            this.payDate = payDate;
        }

        @XmlAttribute(name = "payDate")
        public String getPayDate() {
            return payDate;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        @XmlAttribute(name = "quantity")
        public String getQuantity() {
            return quantity;
        }

        public void setTax(String tax) {
            this.tax = tax;
        }

        @XmlAttribute(name = "tax")
        public String getTax() {
            return tax;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        @XmlAttribute(name = "fee")
        public String getFee() {
            return fee;
        }

        public void setGrossRate(String grossRate) {
            this.grossRate = grossRate;
        }

        @XmlAttribute(name = "grossRate")
        public String getGrossRate() {
            return grossRate;
        }

        public void setGrossAmount(String grossAmount) {
            this.grossAmount = grossAmount;
        }

        @XmlAttribute(name = "grossAmount")
        public String getGrossAmount() {
            return grossAmount;
        }

        public void setNetAmount(String netAmount) {
            this.netAmount = netAmount;
        }

        @XmlAttribute(name = "netAmount")
        public String getNetAmount() {
            return netAmount;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @XmlAttribute(name = "code")
        public String getCode() {
            return code;
        }

        public void setFromAcct(String fromAcct) {
            this.fromAcct = fromAcct;
        }

        @XmlAttribute(name = "fromAcct")
        public String getFromAcct() {
            return fromAcct;
        }

        public void setToAcct(String toAcct) {
            this.toAcct = toAcct;
        }

        @XmlAttribute(name = "toAcct")
        public String getToAcct() {
            return toAcct;
        }

        public void setPrincipalAdjustFactor(String principalAdjustFactor) {
            this.principalAdjustFactor = principalAdjustFactor;
        }

        @XmlAttribute(name = "principalAdjustFactor")
        public String getPrincipalAdjustFactor() {
            return principalAdjustFactor;
        }

    }

    public static class OpenDividendAccruals {
        @XmlElement(name = "OpenDividendAccrual")
        ArrayList<OpenDividendAccrual> OpenDividendAccrual = new ArrayList<>();

        public ArrayList<FlexQueryResponseDto.OpenDividendAccrual> getOpenDividendAccrual() {
            return OpenDividendAccrual;
        }

        public void setOpenDividendAccrual(ArrayList<FlexQueryResponseDto.OpenDividendAccrual> openDividendAccrual) {
            OpenDividendAccrual = openDividendAccrual;
        }
    }

    public static class OpenDividendAccrual {
        String accountId = "";
        String acctAlias = "";
        String model = "";
        String currency = "";
        String fxRateToBase = "";
        String assetCategory = "";
        String symbol = "";
        String description = "";
        String conid = "";
        String securityID = "";
        String securityIDType = "";
        String cusip = "";
        String isin = "";
        String underlyingConid = "";
        String underlyingSymbol = "";
        String issuer = "";
        String multiplier = "";
        String strike = "";
        String expiry = "";
        String putCall = "";
        String exDate = "";
        String payDate = "";
        String quantity = "";
        String tax = "";
        String fee = "";
        String grossRate = "";
        String grossAmount = "";
        String netAmount = "";
        String code = "";
        String fromAcct = "";
        String toAcct = "";
        String principalAdjustFactor = "";

        @XmlAttribute(name = "accountId")
        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        @XmlAttribute(name = "acctAlias")
        public String getAcctAlias() {
            return acctAlias;
        }

        public void setAcctAlias(String acctAlias) {
            this.acctAlias = acctAlias;
        }

        @XmlAttribute(name = "model")
        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        @XmlAttribute(name = "currency")
        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        @XmlAttribute(name = "fxRateToBase")
        public String getFxRateToBase() {
            return fxRateToBase;
        }

        public void setFxRateToBase(String fxRateToBase) {
            this.fxRateToBase = fxRateToBase;
        }

        @XmlAttribute(name = "assetCategory")
        public String getAssetCategory() {
            return assetCategory;
        }

        public void setAssetCategory(String assetCategory) {
            this.assetCategory = assetCategory;
        }

        @XmlAttribute(name = "symbol")
        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        @XmlAttribute(name = "description")
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @XmlAttribute(name = "conid")
        public String getConid() {
            return conid;
        }

        public void setConid(String conid) {
            this.conid = conid;
        }

        @XmlAttribute(name = "securityID")
        public String getSecurityID() {
            return securityID;
        }

        public void setSecurityID(String securityID) {
            this.securityID = securityID;
        }

        @XmlAttribute(name = "securityIDType")
        public String getSecurityIDType() {
            return securityIDType;
        }

        public void setSecurityIDType(String securityIDType) {
            this.securityIDType = securityIDType;
        }

        @XmlAttribute(name = "cusip")
        public String getCusip() {
            return cusip;
        }

        public void setCusip(String cusip) {
            this.cusip = cusip;
        }

        @XmlAttribute(name = "isin")
        public String getIsin() {
            return isin;
        }

        public void setIsin(String isin) {
            this.isin = isin;
        }

        @XmlAttribute(name = "underlyingConid")
        public String getUnderlyingConid() {
            return underlyingConid;
        }

        public void setUnderlyingConid(String underlyingConid) {
            this.underlyingConid = underlyingConid;
        }

        @XmlAttribute(name = "underlyingSymbol")
        public String getUnderlyingSymbol() {
            return underlyingSymbol;
        }

        public void setUnderlyingSymbol(String underlyingSymbol) {
            this.underlyingSymbol = underlyingSymbol;
        }

        @XmlAttribute(name = "issuer")
        public String getIssuer() {
            return issuer;
        }

        public void setIssuer(String issuer) {
            this.issuer = issuer;
        }

        @XmlAttribute(name = "multiplier")
        public String getMultiplier() {
            return multiplier;
        }

        public void setMultiplier(String multiplier) {
            this.multiplier = multiplier;
        }

        @XmlAttribute(name = "strike")
        public String getStrike() {
            return strike;
        }

        public void setStrike(String strike) {
            this.strike = strike;
        }

        @XmlAttribute(name = "expiry")
        public String getExpiry() {
            return expiry;
        }

        public void setExpiry(String expiry) {
            this.expiry = expiry;
        }

        @XmlAttribute(name = "putCall")
        public String getPutCall() {
            return putCall;
        }

        public void setPutCall(String putCall) {
            this.putCall = putCall;
        }

        @XmlAttribute(name = "exDate")
        public String getExDate() {
            return exDate;
        }

        public void setExDate(String exDate) {
            this.exDate = exDate;
        }

        @XmlAttribute(name = "payDate")
        public String getPayDate() {
            return payDate;
        }

        public void setPayDate(String payDate) {
            this.payDate = payDate;
        }

        @XmlAttribute(name = "quantity")
        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        @XmlAttribute(name = "tax")
        public String getTax() {
            return tax;
        }

        public void setTax(String tax) {
            this.tax = tax;
        }

        @XmlAttribute(name = "fee")
        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        @XmlAttribute(name = "grossRate")
        public String getGrossRate() {
            return grossRate;
        }

        public void setGrossRate(String grossRate) {
            this.grossRate = grossRate;
        }

        @XmlAttribute(name = "grossAmount")
        public String getGrossAmount() {
            return grossAmount;
        }

        public void setGrossAmount(String grossAmount) {
            this.grossAmount = grossAmount;
        }

        @XmlAttribute(name = "netAmount")
        public String getNetAmount() {
            return netAmount;
        }

        public void setNetAmount(String netAmount) {
            this.netAmount = netAmount;
        }

        @XmlAttribute(name = "code")
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @XmlAttribute(name = "fromAcct")
        public String getFromAcct() {
            return fromAcct;
        }

        public void setFromAcct(String fromAcct) {
            this.fromAcct = fromAcct;
        }

        @XmlAttribute(name = "toAcct")
        public String getToAcct() {
            return toAcct;
        }

        public void setToAcct(String toAcct) {
            this.toAcct = toAcct;
        }

        @XmlAttribute(name = "principalAdjustFactor")
        public String getPrincipalAdjustFactor() {
            return principalAdjustFactor;
        }

        public void setPrincipalAdjustFactor(String principalAdjustFactor) {
            this.principalAdjustFactor = principalAdjustFactor;
        }
    }

    public static class Trades {
        @XmlElement(name = "Trade")
        ArrayList<Trade> Trade = new ArrayList<>();

        // Getter Methods

        public ArrayList<FlexQueryResponseDto.Trade> getTrade() {
            return Trade;
        }

        // Setter Methods

        public void setTrade(ArrayList<FlexQueryResponseDto.Trade> trade) {
            Trade = trade;
        }
    }

    public static class Trade {
        String accountId = "";
        String acctAlias = "";
        String assetCategory = "";
        String brokerageOrderID = "";
        String buySell = "";
        String changeInPrice = "";
        String changeInQuantity = "";
        String clearingFirmID = "";
        String closePrice = "";
        String conid = "";
        String cost = "";
        String currency = "";
        String cusip = "";
        String description = "";
        String exchOrderId = "";
        String exchange = "";
        String expiry = "";
        String extExecID = "";
        String fifoPnlRealized = "";
        String fxRateToBase = "";
        String holdingPeriodDateTime = "";
        String ibCommission = "";
        String ibCommissionCurrency = "";
        String ibExecID = "";
        String ibOrderID = "";
        String isAPIOrder = "";
        String isin = "";
        String issuer = "";
        String levelOfDetail = "";
        String model = "";
        String mtmPnl = "";
        String multiplier = "";
        String netCash = "";
        String notes = "";
        String openCloseIndicator = "";
        String openDateTime = "";
        String orderReference = "";
        String orderTime = "";
        String orderType = "";
        String origOrderID = "";
        String origTradeDate = "";
        String origTradeID = "";
        String origTradePrice = "";
        String principalAdjustFactor = "";
        String proceeds = "";
        String putCall = "";
        String quantity = "";
        String reportDate = "";
        String securityID = "";
        String securityIDType = "";
        String settleDateTarget = "";
        String strike = "";
        String symbol = "";
        String taxes = "";
        String tradeDate = "";
        String tradeID = "";
        String tradeMoney = "";
        String tradePrice = "";
        String traderID = "";
        String transactionID = "";
        String transactionType = "";
        String underlyingConid = "";
        String underlyingSymbol = "";
        String volatilityOrderLink = "";
        String whenRealized = "";
        String whenReopened = "";
        String textContent = "";

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        @XmlAttribute(name = "accountId")
        public String getAccountId() {
            return accountId;
        }

        public void setAcctAlias(String acctAlias) {
            this.acctAlias = acctAlias;
        }

        @XmlAttribute(name = "acctAlias")
        public String getAcctAlias() {
            return acctAlias;
        }

        public void setAssetCategory(String assetCategory) {
            this.assetCategory = assetCategory;
        }

        @XmlAttribute(name = "assetCategory")
        public String getAssetCategory() {
            return assetCategory;
        }

        public void setBrokerageOrderID(String brokerageOrderID) {
            this.brokerageOrderID = brokerageOrderID;
        }

        @XmlAttribute(name = "brokerageOrderID")
        public String getBrokerageOrderID() {
            return brokerageOrderID;
        }

        public void setBuySell(String buySell) {
            this.buySell = buySell;
        }

        @XmlAttribute(name = "buySell")
        public String getBuySell() {
            return buySell;
        }

        public void setChangeInPrice(String changeInPrice) {
            this.changeInPrice = changeInPrice;
        }

        @XmlAttribute(name = "changeInPrice")
        public String getChangeInPrice() {
            return changeInPrice;
        }

        public void setChangeInQuantity(String changeInQuantity) {
            this.changeInQuantity = changeInQuantity;
        }

        @XmlAttribute(name = "changeInQuantity")
        public String getChangeInQuantity() {
            return changeInQuantity;
        }

        public void setClearingFirmID(String clearingFirmID) {
            this.clearingFirmID = clearingFirmID;
        }

        @XmlAttribute(name = "clearingFirmID")
        public String getClearingFirmID() {
            return clearingFirmID;
        }

        public void setClosePrice(String closePrice) {
            this.closePrice = closePrice;
        }

        @XmlAttribute(name = "closePrice")
        public String getClosePrice() {
            return closePrice;
        }

        public void setConid(String conid) {
            this.conid = conid;
        }

        @XmlAttribute(name = "conid")
        public String getConid() {
            return conid;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        @XmlAttribute(name = "cost")
        public String getCost() {
            return cost;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        @XmlAttribute(name = "currency")
        public String getCurrency() {
            return currency;
        }

        public void setCusip(String cusip) {
            this.cusip = cusip;
        }

        @XmlAttribute(name = "cusip")
        public String getCusip() {
            return cusip;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @XmlAttribute(name = "description")
        public String getDescription() {
            return description;
        }

        public void setExchOrderId(String exchOrderId) {
            this.exchOrderId = exchOrderId;
        }

        @XmlAttribute(name = "exchOrderId")
        public String getExchOrderId() {
            return exchOrderId;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }

        @XmlAttribute(name = "exchange")
        public String getExchange() {
            return exchange;
        }

        public void setExpiry(String expiry) {
            this.expiry = expiry;
        }

        @XmlAttribute(name = "expiry")
        public String getExpiry() {
            return expiry;
        }

        public void setExtExecID(String extExecID) {
            this.extExecID = extExecID;
        }

        @XmlAttribute(name = "extExecID")
        public String getExtExecID() {
            return extExecID;
        }

        public void setFifoPnlRealized(String fifoPnlRealized) {
            this.fifoPnlRealized = fifoPnlRealized;
        }

        @XmlAttribute(name = "fifoPnlRealized")
        public String getFifoPnlRealized() {
            return fifoPnlRealized;
        }

        public void setFxRateToBase(String fxRateToBase) {
            this.fxRateToBase = fxRateToBase;
        }

        @XmlAttribute(name = "fxRateToBase")
        public String getFxRateToBase() {
            return fxRateToBase;
        }

        public void setHoldingPeriodDateTime(String holdingPeriodDateTime) {
            this.holdingPeriodDateTime = holdingPeriodDateTime;
        }

        @XmlAttribute(name = "holdingPeriodDateTime")
        public String getHoldingPeriodDateTime() {
            return holdingPeriodDateTime;
        }

        public void setIbCommission(String ibCommission) {
            this.ibCommission = ibCommission;
        }

        @XmlAttribute(name = "ibCommission")
        public String getIbCommission() {
            return ibCommission;
        }

        public void setIbCommissionCurrency(String ibCommissionCurrency) {
            this.ibCommissionCurrency = ibCommissionCurrency;
        }

        @XmlAttribute(name = "ibCommissionCurrency")
        public String getIbCommissionCurrency() {
            return ibCommissionCurrency;
        }

        public void setIbExecID(String ibExecID) {
            this.ibExecID = ibExecID;
        }

        @XmlAttribute(name = "ibExecID")
        public String getIbExecID() {
            return ibExecID;
        }

        public void setIbOrderID(String ibOrderID) {
            this.ibOrderID = ibOrderID;
        }

        @XmlAttribute(name = "ibOrderID")
        public String getIbOrderID() {
            return ibOrderID;
        }

        public void setIsAPIOrder(String isAPIOrder) {
            this.isAPIOrder = isAPIOrder;
        }

        @XmlAttribute(name = "isAPIOrder")
        public String getIsAPIOrder() {
            return isAPIOrder;
        }

        public void setIsin(String isin) {
            this.isin = isin;
        }

        @XmlAttribute(name = "isin")
        public String getIsin() {
            return isin;
        }

        public void setIssuer(String issuer) {
            this.issuer = issuer;
        }

        @XmlAttribute(name = "issuer")
        public String getIssuer() {
            return issuer;
        }

        public void setLevelOfDetail(String levelOfDetail) {
            this.levelOfDetail = levelOfDetail;
        }

        @XmlAttribute(name = "levelOfDetail")
        public String getLevelOfDetail() {
            return levelOfDetail;
        }

        public void setModel(String model) {
            this.model = model;
        }

        @XmlAttribute(name = "model")
        public String getModel() {
            return model;
        }

        public void setMtmPnl(String mtmPnl) {
            this.mtmPnl = mtmPnl;
        }

        @XmlAttribute(name = "mtmPnl")
        public String getMtmPnl() {
            return mtmPnl;
        }

        public void setMultiplier(String multiplier) {
            this.multiplier = multiplier;
        }

        @XmlAttribute(name = "multiplier")
        public String getMultiplier() {
            return multiplier;
        }

        public void setNetCash(String netCash) {
            this.netCash = netCash;
        }

        @XmlAttribute(name = "netCash")
        public String getNetCash() {
            return netCash;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        @XmlAttribute(name = "notes")
        public String getNotes() {
            return notes;
        }

        public void setOpenCloseIndicator(String openCloseIndicator) {
            this.openCloseIndicator = openCloseIndicator;
        }

        @XmlAttribute(name = "openCloseIndicator")
        public String getOpenCloseIndicator() {
            return openCloseIndicator;
        }

        public void setOpenDateTime(String openDateTime) {
            this.openDateTime = openDateTime;
        }

        @XmlAttribute(name = "openDateTime")
        public String getOpenDateTime() {
            return openDateTime;
        }

        public void setOrderReference(String orderReference) {
            this.orderReference = orderReference;
        }

        @XmlAttribute(name = "orderReference")
        public String getOrderReference() {
            return orderReference;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        @XmlAttribute(name = "orderTime")
        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        @XmlAttribute(name = "orderType")
        public String getOrderType() {
            return orderType;
        }

        public void setOrigOrderID(String origOrderID) {
            this.origOrderID = origOrderID;
        }

        @XmlAttribute(name = "origOrderID")
        public String getOrigOrderID() {
            return origOrderID;
        }

        public void setOrigTradeDate(String origTradeDate) {
            this.origTradeDate = origTradeDate;
        }

        @XmlAttribute(name = "origTradeDate")
        public String getOrigTradeDate() {
            return origTradeDate;
        }

        public void setOrigTradeID(String origTradeID) {
            this.origTradeID = origTradeID;
        }

        @XmlAttribute(name = "origTradeID")
        public String getOrigTradeID() {
            return origTradeID;
        }

        public void setOrigTradePrice(String origTradePrice) {
            this.origTradePrice = origTradePrice;
        }

        @XmlAttribute(name = "origTradePrice")
        public String getOrigTradePrice() {
            return origTradePrice;
        }

        public void setPrincipalAdjustFactor(String principalAdjustFactor) {
            this.principalAdjustFactor = principalAdjustFactor;
        }

        @XmlAttribute(name = "principalAdjustFactor")
        public String getPrincipalAdjustFactor() {
            return principalAdjustFactor;
        }

        public void setProceeds(String proceeds) {
            this.proceeds = proceeds;
        }

        @XmlAttribute(name = "proceeds")
        public String getProceeds() {
            return proceeds;
        }

        public void setPutCall(String putCall) {
            this.putCall = putCall;
        }

        @XmlAttribute(name = "putCall")
        public String getPutCall() {
            return putCall;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        @XmlAttribute(name = "quantity")
        public String getQuantity() {
            return quantity;
        }

        public void setReportDate(String reportDate) {
            this.reportDate = reportDate;
        }

        @XmlAttribute(name = "reportDate")
        public String getReportDate() {
            return reportDate;
        }

        public void setSecurityID(String securityID) {
            this.securityID = securityID;
        }

        @XmlAttribute(name = "securityID")
        public String getSecurityID() {
            return securityID;
        }

        public void setSecurityIDType(String securityIDType) {
            this.securityIDType = securityIDType;
        }

        @XmlAttribute(name = "securityIDType")
        public String getSecurityIDType() {
            return securityIDType;
        }

        public void setSettleDateTarget(String settleDateTarget) {
            this.settleDateTarget = settleDateTarget;
        }

        @XmlAttribute(name = "settleDateTarget")
        public String getSettleDateTarget() {
            return settleDateTarget;
        }

        public void setStrike(String strike) {
            this.strike = strike;
        }

        @XmlAttribute(name = "strike")
        public String getStrike() {
            return strike;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        @XmlAttribute(name = "symbol")
        public String getSymbol() {
            return symbol;
        }

        public void setTaxes(String taxes) {
            this.taxes = taxes;
        }

        @XmlAttribute(name = "taxes")
        public String getTaxes() {
            return taxes;
        }

        public void setTradeDate(String tradeDate) {
            this.tradeDate = tradeDate;
        }

        @XmlAttribute(name = "tradeDate")
        public String getTradeDate() {
            return tradeDate;
        }

        public void setTradeID(String tradeID) {
            this.tradeID = tradeID;
        }

        @XmlAttribute(name = "tradeID")
        public String getTradeID() {
            return tradeID;
        }

        public void setTradeMoney(String tradeMoney) {
            this.tradeMoney = tradeMoney;
        }

        @XmlAttribute(name = "tradeMoney")
        public String getTradeMoney() {
            return tradeMoney;
        }

        public void setTradePrice(String tradePrice) {
            this.tradePrice = tradePrice;
        }

        @XmlAttribute(name = "tradePrice")
        public String getTradePrice() {
            return tradePrice;
        }

        public void setTraderID(String traderID) {
            this.traderID = traderID;
        }

        @XmlAttribute(name = "traderID")
        public String getTraderID() {
            return traderID;
        }

        public void setTransactionID(String transactionID) {
            this.transactionID = transactionID;
        }

        @XmlAttribute(name = "transactionID")
        public String getTransactionID() {
            return transactionID;
        }

        public void setTransactionType(String transactionType) {
            this.transactionType = transactionType;
        }

        @XmlAttribute(name = "transactionType")
        public String getTransactionType() {
            return transactionType;
        }

        public void setUnderlyingConid(String underlyingConid) {
            this.underlyingConid = underlyingConid;
        }

        @XmlAttribute(name = "underlyingConid")
        public String getUnderlyingConid() {
            return underlyingConid;
        }

        public void setUnderlyingSymbol(String underlyingSymbol) {
            this.underlyingSymbol = underlyingSymbol;
        }

        @XmlAttribute(name = "underlyingSymbol")
        public String getUnderlyingSymbol() {
            return underlyingSymbol;
        }

        public void setVolatilityOrderLink(String volatilityOrderLink) {
            this.volatilityOrderLink = volatilityOrderLink;
        }

        @XmlAttribute(name = "volatilityOrderLink")
        public String getVolatilityOrderLink() {
            return volatilityOrderLink;
        }

        public void setWhenRealized(String whenRealized) {
            this.whenRealized = whenRealized;
        }

        @XmlAttribute(name = "whenRealized")
        public String getWhenRealized() {
            return whenRealized;
        }

        public void setWhenReopened(String whenReopened) {
            this.whenReopened = whenReopened;
        }

        @XmlAttribute(name = "whenReopened")
        public String getWhenReopened() {
            return whenReopened;
        }

        public void setTextContent(String textContent) {
            this.textContent = textContent;
        }

        @XmlAttribute(name = "TextContent")
        public String getTextContent() {
            return textContent;
        }
    }


    public static class OpenPositions {
        @XmlElement(name = "OpenPosition")
        ArrayList<OpenPosition> OpenPosition = new ArrayList<>();


        // Getter Methods

        public ArrayList<OpenPosition> getOpenPosition() {
            return OpenPosition;
        }
// Setter Methods


        public void setOpenPosition(ArrayList<OpenPosition> openPosition) {
            OpenPosition = openPosition;
        }
    }

    public static class OpenPosition {
        String accountId = "";
        String accruedInt = "";
        String acctAlias = "";
        String assetCategory = "";
        String code = "";
        String conid = "";
        String costBasisMoney = "";
        String costBasisPrice = "";
        String currency = "";
        String cusip = "";
        String description = "";
        String expiry = "";
        String fifoPnlUnrealized = "";
        String fxRateToBase = "";
        String holdingPeriodDateTime = "";
        String isin = "";
        String issuer = "";
        String levelOfDetail = "";
        String markPrice = "";
        String model = "";
        String multiplier = "";
        String openDateTime = "";
        String openPrice = "";
        String originatingOrderID = "";
        String originatingTransactionID = "";
        String percentOfNAV = "";
        String position = "";
        String positionValue = "";
        String principalAdjustFactor = "";
        String putCall = "";
        String reportDate = "";
        String securityID = "";
        String securityIDType = "";
        String side = "";
        String strike = "";
        String symbol = "";
        String underlyingConid = "";
        String underlyingSymbol = "";
        String textContent = "";

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        @XmlAttribute(name = "accountId")
        public String getAccountId() {
            return accountId;
        }

        public void setAccruedInt(String accruedInt) {
            this.accruedInt = accruedInt;
        }

        @XmlAttribute(name = "accruedInt")
        public String getAccruedInt() {
            return accruedInt;
        }

        public void setAcctAlias(String acctAlias) {
            this.acctAlias = acctAlias;
        }

        @XmlAttribute(name = "acctAlias")
        public String getAcctAlias() {
            return acctAlias;
        }

        public void setAssetCategory(String assetCategory) {
            this.assetCategory = assetCategory;
        }

        @XmlAttribute(name = "assetCategory")
        public String getAssetCategory() {
            return assetCategory;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @XmlAttribute(name = "code")
        public String getCode() {
            return code;
        }

        public void setConid(String conid) {
            this.conid = conid;
        }

        @XmlAttribute(name = "conid")
        public String getConid() {
            return conid;
        }

        public void setCostBasisMoney(String costBasisMoney) {
            this.costBasisMoney = costBasisMoney;
        }

        @XmlAttribute(name = "costBasisMoney")
        public String getCostBasisMoney() {
            return costBasisMoney;
        }

        public void setCostBasisPrice(String costBasisPrice) {
            this.costBasisPrice = costBasisPrice;
        }

        @XmlAttribute(name = "costBasisPrice")
        public String getCostBasisPrice() {
            return costBasisPrice;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        @XmlAttribute(name = "currency")
        public String getCurrency() {
            return currency;
        }

        public void setCusip(String cusip) {
            this.cusip = cusip;
        }

        @XmlAttribute(name = "cusip")
        public String getCusip() {
            return cusip;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @XmlAttribute(name = "description")
        public String getDescription() {
            return description;
        }

        public void setExpiry(String expiry) {
            this.expiry = expiry;
        }

        @XmlAttribute(name = "expiry")
        public String getExpiry() {
            return expiry;
        }

        public void setFifoPnlUnrealized(String fifoPnlUnrealized) {
            this.fifoPnlUnrealized = fifoPnlUnrealized;
        }

        @XmlAttribute(name = "fifoPnlUnrealized")
        public String getFifoPnlUnrealized() {
            return fifoPnlUnrealized;
        }

        public void setFxRateToBase(String fxRateToBase) {
            this.fxRateToBase = fxRateToBase;
        }

        @XmlAttribute(name = "fxRateToBase")
        public String getFxRateToBase() {
            return fxRateToBase;
        }

        public void setHoldingPeriodDateTime(String holdingPeriodDateTime) {
            this.holdingPeriodDateTime = holdingPeriodDateTime;
        }

        @XmlAttribute(name = "holdingPeriodDateTime")
        public String getHoldingPeriodDateTime() {
            return holdingPeriodDateTime;
        }

        public void setIsin(String isin) {
            this.isin = isin;
        }

        @XmlAttribute(name = "isin")
        public String getIsin() {
            return isin;
        }

        public void setIssuer(String issuer) {
            this.issuer = issuer;
        }

        @XmlAttribute(name = "issuer")
        public String getIssuer() {
            return issuer;
        }

        public void setLevelOfDetail(String levelOfDetail) {
            this.levelOfDetail = levelOfDetail;
        }

        @XmlAttribute(name = "levelOfDetail")
        public String getLevelOfDetail() {
            return levelOfDetail;
        }

        public void setMarkPrice(String markPrice) {
            this.markPrice = markPrice;
        }

        @XmlAttribute(name = "markPrice")
        public String getMarkPrice() {
            return markPrice;
        }

        public void setModel(String model) {
            this.model = model;
        }

        @XmlAttribute(name = "model")
        public String getModel() {
            return model;
        }

        public void setMultiplier(String multiplier) {
            this.multiplier = multiplier;
        }

        @XmlAttribute(name = "multiplier")
        public String getMultiplier() {
            return multiplier;
        }

        public void setOpenDateTime(String openDateTime) {
            this.openDateTime = openDateTime;
        }

        @XmlAttribute(name = "openDateTime")
        public String getOpenDateTime() {
            return openDateTime;
        }

        public void setOpenPrice(String openPrice) {
            this.openPrice = openPrice;
        }

        @XmlAttribute(name = "openPrice")
        public String getOpenPrice() {
            return openPrice;
        }

        public void setOriginatingOrderID(String originatingOrderID) {
            this.originatingOrderID = originatingOrderID;
        }

        @XmlAttribute(name = "originatingOrderID")
        public String getOriginatingOrderID() {
            return originatingOrderID;
        }

        public void setOriginatingTransactionID(String originatingTransactionID) {
            this.originatingTransactionID = originatingTransactionID;
        }

        @XmlAttribute(name = "originatingTransactionID")
        public String getOriginatingTransactionID() {
            return originatingTransactionID;
        }

        public void setPercentOfNAV(String percentOfNAV) {
            this.percentOfNAV = percentOfNAV;
        }

        @XmlAttribute(name = "percentOfNAV")
        public String getPercentOfNAV() {
            return percentOfNAV;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        @XmlAttribute(name = "position")
        public String getPosition() {
            return position;
        }

        public void setPositionValue(String positionValue) {
            this.positionValue = positionValue;
        }

        @XmlAttribute(name = "positionValue")
        public String getPositionValue() {
            return positionValue;
        }

        public void setPrincipalAdjustFactor(String principalAdjustFactor) {
            this.principalAdjustFactor = principalAdjustFactor;
        }

        @XmlAttribute(name = "principalAdjustFactor")
        public String getPrincipalAdjustFactor() {
            return principalAdjustFactor;
        }

        public void setPutCall(String putCall) {
            this.putCall = putCall;
        }

        @XmlAttribute(name = "putCall")
        public String getPutCall() {
            return putCall;
        }

        public void setReportDate(String reportDate) {
            this.reportDate = reportDate;
        }

        @XmlAttribute(name = "reportDate")
        public String getReportDate() {
            return reportDate;
        }

        public void setSecurityID(String securityID) {
            this.securityID = securityID;
        }

        @XmlAttribute(name = "securityID")
        public String getSecurityID() {
            return securityID;
        }

        public void setSecurityIDType(String securityIDType) {
            this.securityIDType = securityIDType;
        }

        @XmlAttribute(name = "securityIDType")
        public String getSecurityIDType() {
            return securityIDType;
        }

        public void setSide(String side) {
            this.side = side;
        }

        @XmlAttribute(name = "side")
        public String getSide() {
            return side;
        }

        public void setStrike(String strike) {
            this.strike = strike;
        }

        @XmlAttribute(name = "strike")
        public String getStrike() {
            return strike;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        @XmlAttribute(name = "symbol")
        public String getSymbol() {
            return symbol;
        }

        public void setUnderlyingConid(String underlyingConid) {
            this.underlyingConid = underlyingConid;
        }

        @XmlAttribute(name = "underlyingConid")
        public String getUnderlyingConid() {
            return underlyingConid;
        }

        public void setUnderlyingSymbol(String underlyingSymbol) {
            this.underlyingSymbol = underlyingSymbol;
        }

        @XmlAttribute(name = "underlyingSymbol")
        public String getUnderlyingSymbol() {
            return underlyingSymbol;
        }

        public void setTextContent(String textContent) {
            this.textContent = textContent;
        }

        @XmlAttribute(name = "TextContent")
        public String getTextContent() {
            return textContent;
        }
    }

    public static class AccountInformation {
        private String accountId;
        private String acctAlias;
        private String model;
        private String currency;
        private String name;
        private String accountType;
        private String customerType;
        private String accountCapabilities;
        private String tradingPermissions;
        private String dateOpened;
        private String dateFunded;
        private String dateClosed;
        private String street;
        private String street2;
        private String city;
        private String state;
        private String country;
        private String postalCode;
        private String streetResidentialAddress;
        private String street2ResidentialAddress;
        private String cityResidentialAddress;
        private String stateResidentialAddress;
        private String countryResidentialAddress;
        private String postalCodeResidentialAddress;
        private String masterName;
        private String ibEntity;
        private String primaryEmail;


        // Getter Methods
        @XmlAttribute(name = "accountId")
        public String getaccountId() {
            return accountId;
        }

        @XmlAttribute(name = "acctAlias")
        public String getacctAlias() {
            return acctAlias;
        }

        @XmlAttribute(name = "model")
        public String getmodel() {
            return model;
        }

        @XmlAttribute(name = "currency")
        public String getcurrency() {
            return currency;
        }

        @XmlAttribute(name = "name")
        public String getname() {
            return name;
        }

        @XmlAttribute(name = "accountType")
        public String getaccountType() {
            return accountType;
        }

        @XmlAttribute(name = "customerType")
        public String getcustomerType() {
            return customerType;
        }

        @XmlAttribute(name = "accountCapabilities")
        public String getaccountCapabilities() {
            return accountCapabilities;
        }

        @XmlAttribute(name = "tradingPermissions")
        public String gettradingPermissions() {
            return tradingPermissions;
        }

        @XmlAttribute(name = "dateOpened")
        public String getdateOpened() {
            return dateOpened;
        }

        @XmlAttribute(name = "dateFunded")
        public String getdateFunded() {
            return dateFunded;
        }

        @XmlAttribute(name = "dateClosed")
        public String getdateClosed() {
            return dateClosed;
        }

        @XmlAttribute(name = "street")
        public String getstreet() {
            return street;
        }

        @XmlAttribute(name = "street2")
        public String getstreet2() {
            return street2;
        }

        @XmlAttribute(name = "city")
        public String getcity() {
            return city;
        }

        @XmlAttribute(name = "state")
        public String getstate() {
            return state;
        }

        @XmlAttribute(name = "country")
        public String getcountry() {
            return country;
        }

        @XmlAttribute(name = "postalCode")
        public String getpostalCode() {
            return postalCode;
        }

        @XmlAttribute(name = "streetResidentialAddress")
        public String getstreetResidentialAddress() {
            return streetResidentialAddress;
        }

        @XmlAttribute(name = "street2ResidentialAddress")
        public String getstreet2ResidentialAddress() {
            return street2ResidentialAddress;
        }

        @XmlAttribute(name = "cityResidentialAddress")
        public String getcityResidentialAddress() {
            return cityResidentialAddress;
        }

        @XmlAttribute(name = "stateResidentialAddress")
        public String getstateResidentialAddress() {
            return stateResidentialAddress;
        }

        @XmlAttribute(name = "countryResidentialAddress")
        public String getcountryResidentialAddress() {
            return countryResidentialAddress;
        }

        @XmlAttribute(name = "postalCodeResidentialAddress")
        public String getpostalCodeResidentialAddress() {
            return postalCodeResidentialAddress;
        }

        @XmlAttribute(name = "masterName")
        public String getmasterName() {
            return masterName;
        }

        @XmlAttribute(name = "ibEntity")
        public String getibEntity() {
            return ibEntity;
        }

        @XmlAttribute(name = "primaryEmail")
        public String getprimaryEmail() {
            return primaryEmail;
        }

        // Setter Methods

        public void setaccountId(String accountId) {
            this.accountId = accountId;
        }

        public void setacctAlias(String acctAlias) {
            this.acctAlias = acctAlias;
        }

        public void setmodel(String model) {
            this.model = model;
        }

        public void setcurrency(String currency) {
            this.currency = currency;
        }

        public void setname(String name) {
            this.name = name;
        }

        public void setaccountType(String accountType) {
            this.accountType = accountType;
        }

        public void setcustomerType(String customerType) {
            this.customerType = customerType;
        }

        public void setaccountCapabilities(String accountCapabilities) {
            this.accountCapabilities = accountCapabilities;
        }

        public void settradingPermissions(String tradingPermissions) {
            this.tradingPermissions = tradingPermissions;
        }

        public void setdateOpened(String dateOpened) {
            this.dateOpened = dateOpened;
        }

        public void setdateFunded(String dateFunded) {
            this.dateFunded = dateFunded;
        }

        public void setdateClosed(String dateClosed) {
            this.dateClosed = dateClosed;
        }

        public void setstreet(String street) {
            this.street = street;
        }

        public void setstreet2(String street2) {
            this.street2 = street2;
        }

        public void setcity(String city) {
            this.city = city;
        }

        public void setstate(String state) {
            this.state = state;
        }

        public void setcountry(String country) {
            this.country = country;
        }

        public void setpostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public void setstreetResidentialAddress(String streetResidentialAddress) {
            this.streetResidentialAddress = streetResidentialAddress;
        }

        public void setstreet2ResidentialAddress(String street2ResidentialAddress) {
            this.street2ResidentialAddress = street2ResidentialAddress;
        }

        public void setcityResidentialAddress(String cityResidentialAddress) {
            this.cityResidentialAddress = cityResidentialAddress;
        }

        public void setstateResidentialAddress(String stateResidentialAddress) {
            this.stateResidentialAddress = stateResidentialAddress;
        }

        public void setcountryResidentialAddress(String countryResidentialAddress) {
            this.countryResidentialAddress = countryResidentialAddress;
        }

        public void setpostalCodeResidentialAddress(String postalCodeResidentialAddress) {
            this.postalCodeResidentialAddress = postalCodeResidentialAddress;
        }

        public void setmasterName(String masterName) {
            this.masterName = masterName;
        }

        public void setibEntity(String ibEntity) {
            this.ibEntity = ibEntity;
        }

        public void setprimaryEmail(String primaryEmail) {
            this.primaryEmail = primaryEmail;
        }
    }
}
