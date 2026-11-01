package com.my.mert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PairTotal {

    // Total No -> All Pairs
    private static final HashMap<Integer, ArrayList<String>> totalMap = new HashMap<>();

    // Pair -> Total No
    private static final HashMap<String, Integer> pairMap = new HashMap<>();

    static {

        add(0, "00","19","91","28","82","37","73","46","64","55");
        add(1, "01","10","29","92","38","83","47","74","56","65");
        add(2, "02","20","11","39","93","48","84","57","75","66");
        add(3, "03","30","12","21","49","94","58","85","67","76");
        add(4, "04","40","13","31","22","59","95","68","86","77");
        add(5, "05","50","14","41","23","32","69","96","78","87");
        add(6, "06","60","15","51","24","42","33","79","97","88");
        add(7, "07","70","16","61","25","52","34","43","89","98");
        add(8, "08","80","17","71","26","62","35","53","44","99");
        add(9, "09","90","18","81","27","72","36","63","45","54");
    }

    private static void add(int total, String... pairs) {

        ArrayList<String> list = new ArrayList<>(Arrays.asList(pairs));
        totalMap.put(total, list);

        for (String pair : pairs) {
            pairMap.put(pair, total);
        }
    }

    // Returns Total No of Pair
    public static int whatIsTotalOf(String pair) {
        Integer total = pairMap.get(pair);
        return total == null ? -1 : total;
    }

    // Pass Pair -> Returns same Total pairs (owner removed)
    public static ArrayList<String> getListOfTotal(String pair) {

        Integer total = pairMap.get(pair);

        if (total == null)
            return new ArrayList<>();

        ArrayList<String> list = new ArrayList<>(totalMap.get(total));

        return list;
    }

    // Pass Total No -> Returns all pairs of that Total
    public static ArrayList<String> getListOfTotal(int total) {

        ArrayList<String> list = totalMap.get(total);

        return list == null
                ? new ArrayList<>()
                : new ArrayList<>(list);
    }

    public static boolean contains(String pair) {
        return pairMap.containsKey(pair);
    }
}