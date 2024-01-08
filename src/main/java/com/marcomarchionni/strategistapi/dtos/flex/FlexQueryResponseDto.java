package com.marcomarchionni.strategistapi.dtos.flex;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlexQueryResponseDto implements FlexResponse {
    @NotNull
    @JsonProperty("FlexStatements")
    private FlexStatements flexStatements;
    private String queryName;
    private String type;

    @Override
    public boolean isPopulated() {
        return getQueryName() != null && getFlexStatements().getFlexStatement() != null;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FlexStatements {
        @NotNull
        @JsonProperty("FlexStatement")
        private FlexStatement flexStatement;
        private int count;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FlexStatement {
        @JsonProperty("AccountInformation")
        private AccountInformation accountInformation;
        @JsonProperty("OpenPositions")
        private OpenPositions openPositions;
        @JsonProperty("Trades")
        private Trades trades;
        @JsonProperty("ChangeInDividendAccruals")
        private ChangeInDividendAccruals changeInDividendAccruals;
        @JsonProperty("OpenDividendAccruals")
        private OpenDividendAccruals openDividendAccruals;
        @NotNull
        private String accountId;
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate fromDate;
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate toDate;
        private String period;
        private LocalDateTime whenGenerated;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
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
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate dateOpened;
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate dateFunded;
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate dateClosed;
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
    }

    @Data
    public static class OpenPositions {
        @JsonProperty("OpenPosition")
        private List<OpenPosition> openPositionList;

        // allows non-consecutive elements to be grouped in the same list
        @SuppressWarnings("unused")
        public void setOpenPositionList(List<FlexQueryResponseDto.OpenPosition> values) {
            if (openPositionList == null) {
                openPositionList = new ArrayList<>(values.size());
            }
            openPositionList.addAll(values);
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OpenPosition {
        private String accountId;
        private String acctAlias;
        private String model;
        private String currency;
        private BigDecimal fxRateToBase;
        private String assetCategory;
        private String symbol;
        private String description;
        private Long conid;
        private String securityID;
        private String securityIDType;
        private String cusip;
        private String isin;
        private Long underlyingConid;
        private String underlyingSymbol;
        private String issuer;
        private int multiplier;
        private BigDecimal strike;
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate expiry;
        private String putCall;
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate reportDate;
        private BigDecimal position;
        private BigDecimal markPrice;
        private BigDecimal positionValue;
        private BigDecimal openPrice;
        private BigDecimal costBasisPrice;
        private BigDecimal costBasisMoney;
        private BigDecimal percentOfNAV;
        private BigDecimal fifoPnlUnrealized;
        private String side;
        private String levelOfDetail;
        private LocalDateTime openDateTime;
        private String holdingPeriodDateTime;
        private String code;
        private String originatingOrderID;
        private String originatingTransactionID;
        private BigDecimal accruedInt;
        private BigDecimal principalAdjustFactor;
        private String listingExchange;
        private String underlyingSecurityID;
        private String underlyingListingExchange;
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate vestingDate;
        private String serialNumber;
        private String deliveryType;
        private String commodityType;
        private BigDecimal fineness;
        private String weight;
    }

    @Data
    public static class Trades {
        @JsonProperty("Trade")
        private List<Trade> tradeList;
        @JsonProperty("Order")
        private List<Order> orderList;

        // allows non-consecutive elements to be grouped in the same list
        @SuppressWarnings("unused")
        public void setTradeList(List<Trade> values) {
            if (tradeList == null) {
                tradeList = new ArrayList<>(values.size());
            }
            tradeList.addAll(values);
        }

        @SuppressWarnings("unused")
        public void setOrderList(List<Order> values) {
            if (orderList == null) {
                orderList = new ArrayList<>(values.size());
            }
            orderList.addAll(values);
        }
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Trade {
        private String accountId;
        private String acctAlias;
        private String model;
        private String currency;
        private BigDecimal fxRateToBase;
        private String assetCategory;
        private String symbol;
        private String description;
        private Long conid;
        private String securityID;
        private String securityIDType;
        private String cusip;
        private String isin;
        private String underlyingConid;
        private String underlyingSymbol;
        private String issuer;
        private int multiplier;
        private BigDecimal strike;
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate expiry;
        private String putCall;
        private Long tradeID;
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate reportDate;
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate tradeDate;
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate settleDateTarget;
        private String transactionType;
        private String exchange;
        private BigDecimal quantity;
        private BigDecimal tradePrice;
        private BigDecimal tradeMoney;
        private BigDecimal proceeds;
        private BigDecimal taxes;
        private BigDecimal ibCommission;
        private String ibCommissionCurrency;
        private BigDecimal closePrice;
        private String openCloseIndicator;
        private String notes;
        private BigDecimal cost;
        private BigDecimal fifoPnlRealized;
        private BigDecimal mtmPnl;
        private BigDecimal origTradePrice;
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate origTradeDate;
        private String origTradeID;
        private String origOrderID;
        private String clearingFirmID;
        private String transactionID;
        private String buySell;
        private String ibOrderID;
        private String ibExecID;
        private String brokerageOrderID;
        private String orderReference;
        private String volatilityOrderLink;
        private String exchOrderId;
        private String extExecID;
        private LocalDateTime orderTime;
        private LocalDateTime openDateTime;
        private LocalDateTime holdingPeriodDateTime;
        private String whenRealized;
        private String whenReopened;
        private String levelOfDetail;
        private BigDecimal changeInPrice;
        private BigDecimal changeInQuantity;
        private BigDecimal netCash;
        private String orderType;
        private String traderID;
        private String isAPIOrder;
        private String principalAdjustFactor;
        private String listingExchange;
        private String underlyingSecurityID;
        private String underlyingListingExchange;
        private LocalDateTime dateTime;
        private BigDecimal fxPnl;
        private BigDecimal accruedInt;
        private String serialNumber;
        private String deliveryType;
        private String commodityType;
        private BigDecimal fineness;
        private String weight;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Order {
        private String accountId;
        private String acctAlias;
        private String model;
        private String currency;
        private BigDecimal fxRateToBase;
        private String assetCategory;
        private String symbol;
        private String description;
        private Long conid;
        private String securityID;
        private String securityIDType;
        private String cusip;
        private String isin;
        private String underlyingConid;
        private String underlyingSymbol;
        private String issuer;
        private int multiplier;
        private BigDecimal strike;
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate expiry;
        private String putCall;
        private Long tradeID;
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate reportDate;
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate tradeDate;
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate settleDateTarget;
        private String transactionType;
        private String exchange;
        private BigDecimal quantity;
        private BigDecimal tradePrice;
        private BigDecimal tradeMoney;
        private BigDecimal proceeds;
        private BigDecimal taxes;
        private BigDecimal ibCommission;
        private String ibCommissionCurrency;
        private BigDecimal closePrice;
        private String openCloseIndicator;
        private String notes;
        private BigDecimal cost;
        private BigDecimal fifoPnlRealized;
        private BigDecimal mtmPnl;
        private BigDecimal origTradePrice;
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate origTradeDate;
        private String origTradeID;
        private String origOrderID;
        private String clearingFirmID;
        private String transactionID;
        private String buySell;
        private Long ibOrderID;
        private String ibExecID;
        private String brokerageOrderID;
        private String orderReference;
        private String volatilityOrderLink;
        private String exchOrderId;
        private String extExecID;
        private LocalDateTime orderTime;
        private LocalDateTime openDateTime;
        private LocalDateTime holdingPeriodDateTime;
        private String whenRealized;
        private String whenReopened;
        private String levelOfDetail;
        private BigDecimal changeInPrice;
        private BigDecimal changeInQuantity;
        private BigDecimal netCash;
        private String orderType;
        private String traderID;
        private String isAPIOrder;
        private String principalAdjustFactor;
        private String listingExchange;
        private String underlyingSecurityID;
        private String underlyingListingExchange;
        private LocalDateTime dateTime;
        private BigDecimal fxPnl;
        private BigDecimal accruedInt;
        private String serialNumber;
        private String deliveryType;
        private String commodityType;
        private BigDecimal fineness;
        private String weight;
    }

    @Data
    public static class ChangeInDividendAccruals {
        @JsonProperty("ChangeInDividendAccrual")
        private List<ChangeInDividendAccrual> changeInDividendAccrualList;

        // allows non-consecutive elements to be grouped in the same list
        @SuppressWarnings("unused")
        public void setChangeInDividendAccrualList(List<ChangeInDividendAccrual> values) {
            if (changeInDividendAccrualList == null) {
                changeInDividendAccrualList = new ArrayList<>(values.size());
            }
            changeInDividendAccrualList.addAll(values);
        }
    }
    @Data
    @SuperBuilder
    @NoArgsConstructor
    public static abstract class DividendAccrual {
        private String accountId;
        private String acctAlias;
        private String model;
        private String currency;
        private BigDecimal fxRateToBase;
        private String assetCategory;
        private String symbol;
        private String description;
        private Long conid;
        private String securityID;
        private String securityIDType;
        private String cusip;
        private String isin;
        private String underlyingConid;
        private String underlyingSymbol;
        private String issuer;
        private int multiplier;
        private BigDecimal strike;
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate expiry;
        private String putCall;
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate exDate;
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate payDate;
        private BigDecimal quantity;
        private BigDecimal tax;
        private BigDecimal fee;
        private BigDecimal grossRate;
        private BigDecimal grossAmount;
        private BigDecimal netAmount;
        private String code;
        private String fromAcct;
        private String toAcct;
        private String principalAdjustFactor;
        private String listingExchange;
        private String underlyingSecurityID;
        private String underlyingListingExchange;
        private String actionID;
        private String serialNumber;
        private String deliveryType;
        private String commodityType;
        private BigDecimal fineness;
        private String weight;

    }
    @EqualsAndHashCode(callSuper = true)
    @Data
    @SuperBuilder
    @NoArgsConstructor
    public static class ChangeInDividendAccrual extends DividendAccrual {
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate date;
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate reportDate;
        private String levelOfDetail;

    }
    @Data
    public static class OpenDividendAccruals {

        @JsonProperty("OpenDividendAccrual")
        private List<OpenDividendAccrual> openDividendAccrualList;
        @SuppressWarnings("unused")
        public void setChangeInDividendAccrualList(List<OpenDividendAccrual> values) {
            if (openDividendAccrualList == null) {
                openDividendAccrualList = new ArrayList<>(values.size());
            }
            openDividendAccrualList.addAll(values);
        }

    }
    @EqualsAndHashCode(callSuper = true)
    @Data
    @SuperBuilder
    @NoArgsConstructor
    public static class OpenDividendAccrual extends DividendAccrual {

    }

    // helper methods to avoid null checks
    public FlexQueryResponseDto.FlexStatement nullSafeGetFlexStatement() {
        return Optional.ofNullable(flexStatements)
                .map(FlexStatements::getFlexStatement)
                .orElse(null);
    }

    public List<FlexQueryResponseDto.OpenPosition> nullSafeGetOpenPositions() {
        return Optional.ofNullable(flexStatements)
                .map(FlexStatements::getFlexStatement)
                .map(FlexStatement::getOpenPositions)
                .map(OpenPositions::getOpenPositionList)
                .orElse(Collections.emptyList());
    }

    public List<FlexQueryResponseDto.Order> nullSafeGetOrders() {
        return Optional.ofNullable(flexStatements)
                .map(FlexStatements::getFlexStatement)
                .map(FlexStatement::getTrades)
                .map(Trades::getOrderList)
                .orElse(Collections.emptyList());
    }

    public List<FlexQueryResponseDto.Trade> nullSafeGetTrades() {
        return Optional.ofNullable(flexStatements)
                .map(FlexStatements::getFlexStatement)
                .map(FlexStatement::getTrades)
                .map(Trades::getTradeList)
                .orElse(Collections.emptyList());
    }

    public List<FlexQueryResponseDto.ChangeInDividendAccrual> nullSafeGetChangeInDividendAccruals() {
        return Optional.ofNullable(flexStatements)
                .map(FlexStatements::getFlexStatement)
                .map(FlexStatement::getChangeInDividendAccruals)
                .map(ChangeInDividendAccruals::getChangeInDividendAccrualList)
                .orElse(Collections.emptyList());
    }

    public List<FlexQueryResponseDto.OpenDividendAccrual> nullSafeGetOpenDividendAccruals() {
        return Optional.ofNullable(flexStatements)
                .map(FlexStatements::getFlexStatement)
                .map(FlexStatement::getOpenDividendAccruals)
                .map(OpenDividendAccruals::getOpenDividendAccrualList)
                .orElse(Collections.emptyList());
    }
}

