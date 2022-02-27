package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Position;
import com.marcomarchionni.ibportfolio.models.Trade;

import java.util.ArrayList;
import java.util.List;

public class IBUpdate {
    private List<Position> positions = new ArrayList<>();
    private List<Trade> trades = new ArrayList<>();

    public List<Position> getPositions() {
        return positions;
    }

    public List<Trade> getTrades() {
        return trades;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public void setTrades(List<Trade> trades) {
        this.trades = trades;
    }

    public void addPosition(Position position){
        positions.add(position);
    }

    public void addTrade(Trade trade){
        trades.add(trade);
    }

    public void print(){
        positions.forEach(System.out::println);
        trades.forEach(System.out::println);
    }

    public void saveToDb(){

    }
}
