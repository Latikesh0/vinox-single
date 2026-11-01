package com.my.mert;

import android.widget.TextView;
import android.graphics.Color;
import java.util.*;

public final class FamilyAnalyzer {

    public FamilyAnalyzer(){}

    public static Map<String,List<TextView>> getFamilies(TextView... txts){

        Map<String,List<TextView>> result = new LinkedHashMap<>();

        if(txts == null) return result;

        for(TextView tv : txts){

            if(tv == null) continue;

            String s = tv.getText().toString().trim();

            if(!FamilyGenerator.isValid(s))
                continue;

            List<String> family = FamilyGenerator.getFamily(s);

            if(family.isEmpty())
                continue;

            String key = family.get(0);

            result.computeIfAbsent(key,k->new ArrayList<>()).add(tv);
        }

        return result;
    }

    public static int countFamilies(TextView... txts){
        return getFamilies(txts).size();
    }

    public static int getFamilyCount(String family,TextView... txts){

        List<TextView> list = getFamilyMembers(family,txts);

        return list.size();
    }

    public static List<TextView> getFamilyMembers(String family,TextView... txts){

        List<TextView> out = new ArrayList<>();

        if(txts == null) return out;

        for(TextView tv : txts){

            if(tv == null) continue;

            String s = tv.getText().toString().trim();

            if(FamilyGenerator.isFamily(family,s))
                out.add(tv);
        }

        return out;
    }

    public static Map.Entry<String,List<TextView>> largestFamily(TextView... txts){

        Map<String,List<TextView>> map = getFamilies(txts);

        Map.Entry<String,List<TextView>> best = null;

        for(Map.Entry<String,List<TextView>> e : map.entrySet()){

            if(best == null || e.getValue().size() > best.getValue().size())
                best = e;
        }

        return best;
    }

    public static Map.Entry<String,List<TextView>> smallestFamily(TextView... txts){

        Map<String,List<TextView>> map = getFamilies(txts);

        Map.Entry<String,List<TextView>> best = null;

        for(Map.Entry<String,List<TextView>> e : map.entrySet()){

            if(best == null || e.getValue().size() < best.getValue().size())
                best = e;
        }

        return best;
    }

    public static boolean hasFamily(int size,TextView... txts){

        for(List<TextView> list : getFamilies(txts).values()){

            if(list.size() == size)
                return true;
        }

        return false;
    }

    public static Map<String,List<TextView>> getFamiliesBySize(int min,TextView... txts){

        Map<String,List<TextView>> out = new LinkedHashMap<>();

        for(Map.Entry<String,List<TextView>> e : getFamilies(txts).entrySet()){

            if(e.getValue().size() >= min)
                out.put(e.getKey(),e.getValue());
        }

        return out;
    }

    public static void clearFamiliesLessThan(int min,TextView... txts){

        for(List<TextView> list : getFamilies(txts).values()){

            if(list.size() < min){

                for(TextView tv : list){

                    tv.setText("");
                }
            }
        }
    }

    public static void clearFamiliesGreaterThan(int max,TextView... txts){

        for(List<TextView> list : getFamilies(txts).values()){

            if(list.size() > max){

                for(TextView tv : list){

                    tv.setText("");
                }
            }
        }
    }

    public static void keepLargestFamily(TextView... txts){

        Map.Entry<String,List<TextView>> best = largestFamily(txts);

        if(best == null) return;

        Set<TextView> keep = new HashSet<>(best.getValue());

        for(TextView tv : txts){

            if(tv != null && !keep.contains(tv))
                tv.setText("");
        }
    }

    public static void keepSmallestFamily(TextView... txts){

        Map.Entry<String,List<TextView>> best = smallestFamily(txts);

        if(best == null) return;

        Set<TextView> keep = new HashSet<>(best.getValue());

        for(TextView tv : txts){

            if(tv != null && !keep.contains(tv))
                tv.setText("");
        }
    }

    public static void showFamily(String family, TextView... txts) {
        if (txts == null || family == null) return;
        for (TextView tv : txts) {
            if (tv == null) continue;
            tv.setTextColor(Color.BLACK);
            if (FamilyGenerator.isFamily(family, tv.getText().toString().trim())) {
                tv.setTextColor(Color.RED);
            }
        }
    }
}