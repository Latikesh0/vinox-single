package com.my.mert;

import java.util.*;

public class PairFarakNormal {

    private static final HashMap<String, ArrayList<String>> map = new HashMap<>();

    static {

        // Farak 0
        add("00","11","22","33","44","55","66","77","88","99");

        // Farak 1
        add("01","10","12","21","23","32","34","43","45","54","56","65","67","76","78","87","89","98","09","90");

        // Farak 2
        add("02","20","13","31","24","42","35","53","46","64","57","75","68","86","79","97","08","80","19","91");

        // Farak 3
        add("03","30","14","41","25","52","36","63","47","74","58","85","69","96","07","70","18","81","29","92");

        // Farak 4
        add("04","40","15","51","26","62","37","73","48","84","59","95","06","60","17","71","28","82","39","93");

        // Farak 5
        add("05","50","16","61","27","72","38","83","49","94");

        // Farak 6
        add("06","60","17","71","28","82","39","93","40","04","51","15");

        // Farak 7
        add("07","70","18","81","29","92","30","03","41","14","52","25");

        // Farak 8
        add("08","80","19","91","20","02","31","13","42","24","53","35");

        // Farak 9
        add("09","90","10","01","21","12","32","23","43","34","54","45");
    }

    private static void add(String... pairs) {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(pairs));
        for (String p : pairs) {
            ArrayList<String> others = new ArrayList<>(list);
            others.remove(p);
            map.put(p, others);
        }
    }

    public static ArrayList<String> get(String pair) {
        return map.containsKey(pair)
                ? new ArrayList<>(map.get(pair))
                : new ArrayList<>();
    }
}