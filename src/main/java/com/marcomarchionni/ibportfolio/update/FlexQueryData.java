package com.marcomarchionni.ibportfolio.update;

import com.marcomarchionni.ibportfolio.models.Dividend;
import com.marcomarchionni.ibportfolio.models.FlexStatement;
import com.marcomarchionni.ibportfolio.models.Position;
import com.marcomarchionni.ibportfolio.models.Trade;

import java.util.List;

public class FlexQueryData {

    private FlexStatement flexStatement;
    private List<Position> positions;
    private List<Trade> trades;
    private List<Dividend> dividends;

    public FlexStatement getFlexStatement() {
        return flexStatement;
    }

    public void setFlexStatement(FlexStatement flexStatement) {
        this.flexStatement = flexStatement;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public List<Trade> getTrades() {
        return trades;
    }

    public void setTrades(List<Trade> trades) {
        this.trades = trades;
    }

    public List<Dividend> getDividends() {
        return dividends;
    }

    public void setDividends(List<Dividend> dividends) {
        this.dividends = dividends;
    }
}
