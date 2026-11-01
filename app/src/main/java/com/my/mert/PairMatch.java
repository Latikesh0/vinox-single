package com.my.mert;

import java.util.ArrayList;

public class PairMatch {

    // ++ (Total -> Total)
    public static ArrayList<String> plusplus(String pair) {
        return PairTotal.getListOfTotal(pair);
    }

    // -- (Farak -> Farak)
    public static ArrayList<String> minusminus(String pair) {
        return PairFarak0as10.getListOfFarak(pair);
    }

    // +- (Total -> Farak)
    public static ArrayList<String> plusminus(String pair) {

        int total = PairTotal.whatIsTotalOf(pair);

        if (total == -1)
            return new ArrayList<>();

        ArrayList<String> list = PairFarak0as10.getListOfFarak(total);
        list.remove(pair);

        return list;
    }

    // -+ (Farak -> Total)
    public static ArrayList<String> minusplus(String pair) {

        int farak = PairFarak0as10.whatIsFarakOf(pair);

        if (farak == -1)
            return new ArrayList<>();

        ArrayList<String> list = PairTotal.getListOfTotal(farak);
        list.remove(pair);

        return list;
    }
}