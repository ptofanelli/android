package com.ptofanelli.sorteador.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Pio Tofanelli on 28-Nov-17.
 */

public class DiceRollResult implements Serializable{
    private List<Integer> dicesRollResult;

    public List<Integer> getDicesRollResult() {
        return dicesRollResult;
    }

    public int getTotal() {
        int total = 0;

        if(dicesRollResult != null) {
            for (Integer i : dicesRollResult) {
                total += i;
            }
        }

        return total;
    }

    public void setDicesRollResult(List<Integer> dicesRollResult) {
        this.dicesRollResult = dicesRollResult;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("|");
        for ( Integer roll : dicesRollResult ) {
            builder.append(" " + roll + " |");
        }

        return builder.toString();
    }
}
