package com.my.mert;

import java.util.*;

public final class FamilyGenerator {

    private static final Map<String, List<String>> MAP = new HashMap<>();
    private static final List<List<String>> FAMILIES = new ArrayList<>();

    static {

        // Black home
        add("01","10","15","51","56","65","06","60");
        add("02","20","07","70","25","52","75","57");
        add("03","30","08","80","35","53","85","58");
        add("04","40","45","54","59","95","09","90");
        add("12","21","71","17","62","26","76","67");
        add("13","31","36","63","86","68","81","18");
        add("14","41","46","64","96","69","19","91");
        add("23","32","28","82","73","37","78","87");
        add("24","42","29","92","79","97","47","74");
        add("34","43","48","84","89","98","93","39");

        // Red home
        add("16","61","11","66");
        add("27","72","22","77");
        add("38","83","33","88");
        add("94","49","44","99");
        add("05","50","00","55");

    }

    private FamilyGenerator(){}

    private static void add(String... nums){
        List<String> family = Collections.unmodifiableList(Arrays.asList(nums));
        FAMILIES.add(family);
        for(String n : nums){
            MAP.put(n,family);
        }
    }

    public static boolean isValid(String n){
        return n != null && n.matches("\\d{2}");
    }

    public static String normalize(String n){
        return n == null ? "" : n.trim();
    }

    public static List<String> getFamily(String n){
        n = normalize(n);
        return MAP.getOrDefault(n, Collections.emptyList());
    }

    public static String[] getFamilyArray(String n){
        return getFamily(n).toArray(new String[0]);
    }

    public static boolean isFamily(String a,String b){
        return getFamily(a).contains(normalize(b));
    }

    public static boolean contains(String family,String value){
        return getFamily(family).contains(normalize(value));
    }

    public static int familySize(String n){
        return getFamily(n).size();
    }

    public static List<List<String>> getAllFamilies(){
        return Collections.unmodifiableList(FAMILIES);
    }

    public static Set<String> getAllNumbers(){
        return Collections.unmodifiableSet(MAP.keySet());
    }

    public static int countFamilies(String... nums){
        return group(nums).size();
    }

    public static Map<String,List<String>> group(String... nums){

        Map<String,List<String>> result = new LinkedHashMap<>();

        if(nums == null) return result;

        for(String s : nums){

            s = normalize(s);

            if(!isValid(s)) continue;

            List<String> family = getFamily(s);

            if(family.isEmpty()) continue;

            String key = family.get(0);

            result.computeIfAbsent(key,k->new ArrayList<>()).add(s);
        }

        return result;
    }

    public static int getFamilyCount(String family,String... nums){

        family = normalize(family);

        if(!isValid(family)) return 0;

        int count = 0;

        for(String s : nums){

            if(isFamily(family,s))
                count++;
        }

        return count;
    }

    public static List<String> getMembers(String family,String... nums){

        List<String> list = new ArrayList<>();

        for(String s : nums){

            if(isFamily(family,s))
                list.add(s);
        }

        return list;
    }

    public static Map.Entry<String,List<String>> largestFamily(String... nums){

        Map<String,List<String>> map = group(nums);

        Map.Entry<String,List<String>> best = null;

        for(Map.Entry<String,List<String>> e : map.entrySet()){

            if(best == null || e.getValue().size() > best.getValue().size())
                best = e;
        }

        return best;
    }

    public static Map.Entry<String,List<String>> smallestFamily(String... nums){

        Map<String,List<String>> map = group(nums);

        Map.Entry<String,List<String>> best = null;

        for(Map.Entry<String,List<String>> e : map.entrySet()){

            if(best == null || e.getValue().size() < best.getValue().size())
                best = e;
        }

        return best;
    }

    public static boolean hasFamily(int size,String... nums){

        for(List<String> list : group(nums).values()){

            if(list.size() == size)
                return true;
        }

        return false;
    }

    public static Map<String,List<String>> getFamiliesBySize(int min,String... nums){

        Map<String,List<String>> out = new LinkedHashMap<>();

        for(Map.Entry<String,List<String>> e : group(nums).entrySet()){

            if(e.getValue().size() >= min)
                out.put(e.getKey(),e.getValue());
        }

        return out;
    }
}