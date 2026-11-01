package com.my.mert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PairFarak0as10 {

    // Farak No -> All Pairs
    private static final HashMap<Integer, ArrayList<String>> farakMap = new HashMap<>();

    // Pair -> Farak No
    private static final HashMap<String, Integer> pairMap = new HashMap<>();

    static {

        add(0, "00","11","22","33","44","55","66","77","88","99");

        add(1, "09","90","12","21","23","32","34","43","45","54","56","65","67","76","78","87","89","98");

        add(2, "08","80","13","31","24","42","35","53","46","64","57","75","68","86","79","97");

        add(3, "07","70","14","41","25","52","36","63","47","74","58","85","69","96");

        add(4, "06","60","15","51","26","62","37","73","48","84","59","95");

        add(5, "05","50","16","61","27","72","38","83","49","94");

        add(6, "04","40","17","71","28","82","39","93");

        add(7, "03","30","18","81","29","92");

        add(8, "02","20","19","91");

        add(9, "01","10");
    }

    private static void add(int farak, String... pairs) {

        ArrayList<String> list = new ArrayList<>(Arrays.asList(pairs));
        farakMap.put(farak, list);

        for (String pair : pairs) {
            pairMap.put(pair, farak);
        }
    }

    // Returns Farak No of Pair
    public static int whatIsFarakOf(String pair) {
        Integer farak = pairMap.get(pair);
        return farak == null ? -1 : farak;
    }

    // Pass Pair -> Returns same Farak pairs (owner removed)
    public static ArrayList<String> getListOfFarak(String pair) {

        Integer farak = pairMap.get(pair);

        if (farak == null)
            return new ArrayList<>();

        ArrayList<String> list = new ArrayList<>(farakMap.get(farak));
        //list.remove(pair);

        return list;
    }

    // Pass Farak No -> Returns all pairs of that Farak
    public static ArrayList<String> getListOfFarak(int farak) {

        ArrayList<String> list = farakMap.get(farak);

        return list == null
                ? new ArrayList<>()
                : new ArrayList<>(list);
    }

    public static boolean contains(String pair) {
        return pairMap.containsKey(pair);
    }
}