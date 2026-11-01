package com.my.mert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public final class PairTotalFarak { //ekch pair mhade +,-

    // TotalFarak -> All Pairs
    private static final HashMap<Integer, ArrayList<String>> totalMap = new HashMap<>();
    // Pair -> TotalFarak
    private static final HashMap<String, Integer> pairMap = new HashMap<>();

    static {

        add(13, "21","12","74","47","76","67");                 // 1x3
        add(15, "83","38","32","23","78","87");                 // 1x5
        add(17, "92","29","43","34","98","89");                 // 1x7
        add(19, "10","01","90","09","54","45");                 // 1x9

        add(24, "31","13","86","68","84","48");                 // 2x4
        add(28, "20","02","80","08","53","35");                 // 2x8
        add(20, "11","66","64","46");                           // 2x0

        add(35, "94","49","41","14","96","69");                 // 3x5
        add(39, "63","36");                                     // 3x9
        add(40, "22","77","73","37");                           // 4x0
        add(68, "71","17");                                     // 6x8
        add(60, "33","88","82","28");                           // 6x0
        add(62, "42","24","97","79","39","93");                 // 6x2
        add(64, "40","04","51","15","60","06");                 // 6x4
        add(79, "81","18");                                     // 7x9
        add(73, "30","03","70","07","52","25");                 // 7x3
        add(75, "61","16");                                     // 7x5
        add(80, "44","99","91","19");                           // 8x0
        add(84, "62","26");                                     // 8x4
        add(95, "72","27");                                     // 9x5

        add(11, "56","65");                                     // 1x1
        add(22, "75","57");                                     // 2x2
        add(33, "58","85");                                     // 3x3
        add(44, "59","95");                                     // 4x4
        add(55, "50","05");                                     // 5x5
        add(0,  "00","55");                                     // 0x0
    }

    public PairTotalFarak() {}

    private static void add(int totalFarak, String... pairs) {

        ArrayList<String> list = new ArrayList<>(Arrays.asList(pairs));
        totalMap.put(totalFarak, list);

        for (String pair : pairs) {
            pairMap.put(pair, totalFarak);
        }
    }

    // Pair -> TotalFarak
    public static int getTotalFarak(String pair) {
        Integer n = pairMap.get(pair);
        return n == null ? -1 : n;
    }

    // Pair -> All pairs of same TotalFarak
    public static ArrayList<String> get(String pair) {
        Integer n = pairMap.get(pair);
        if (n == null) return new ArrayList<>();
        return new ArrayList<>(totalMap.get(n));
    }

    // TotalFarak -> All pairs
    public static ArrayList<String> get(int totalFarak) {
        ArrayList<String> list = totalMap.get(totalFarak);
        return list == null ? new ArrayList<>() : new ArrayList<>(list);
    }

    // Pair -> Same group except itself
    public static ArrayList<String> getOthers(String pair) {

        ArrayList<String> list = get(pair);
        list.remove(pair);
        return list;
    }

    // Pair -> Does exist?
    public static boolean contains(String pair) {
        return pairMap.containsKey(pair);
    }

    // TotalFarak -> Exists?
    public static boolean contains(int totalFarak) {
        return totalMap.containsKey(totalFarak);
    }

    // Pair -> Is pair valid?
    public static boolean isValid(String pair) {
        return pair != null
                && pair.length() == 2
                && pair.matches("\\d{2}")
                && contains(pair);
    }

    // Pair -> Count of same-group pairs
    public static int size(String pair) {
        return get(pair).size();
    }

    // TotalFarak -> Count of pairs
    public static int size(int totalFarak) {
        return get(totalFarak).size();
    }

    // All TotalFarak values
    public static ArrayList<Integer> totals() {
        return new ArrayList<>(totalMap.keySet());
    }

    // All registered pairs
    public static ArrayList<String> pairs() {
        return new ArrayList<>(pairMap.keySet());
    }

    // TotalFarak -> First pair
    public static String first(int totalFarak) {
        ArrayList<String> list = get(totalFarak);
        return list.isEmpty() ? "" : list.get(0);
    }

    // Pair -> First pair in same group
    public static String first(String pair) {
        return first(getTotalFarak(pair));
    }

    // Pair -> Unmodifiable list
    public static List<String> view(String pair) {
        return Collections.unmodifiableList(get(pair));
    }

    // Pair -> Compare TotalFarak
    public static boolean sameGroup(String pair1, String pair2) {
        return contains(pair1)
                && contains(pair2)
                && getTotalFarak(pair1) == getTotalFarak(pair2);
    }
}