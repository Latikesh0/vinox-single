package com.my.mert;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import androidx.annotation.*;
//import laki.witcher.TextViewUtils;
//import laki.witcher.Cutoff;
//import laki.witcher.PairMatch;
//import laki.witcher.PairTotal;
//import laki.witcher.PairFarak0as10;
//import laki.witcher.PairTotalFarak;
//import laki.witcher.FamilyAnalyzer;
import java.util.*;

public class LakiActivity {

    private static final Cutoff CUTOFF = new Cutoff();
    private static RowCol ROWCOL;
	
	public LakiActivity() {
	}
    /*
    Rowcolumn class
    */
		public LakiActivity(TextView[] grid) {
			ROWCOL = new RowCol(grid);
		}
		
		public static TextView[] getRowCol(int id) {
			return ROWCOL.getRowCol(id);
		}
		
		public static TextView[] getHorizontal(int id) {
			return ROWCOL.getHorizontal(id);
		}

		public static TextView[] getVertical(int id) {
			return ROWCOL.getVertical(id);
		}
		/*--------------------------------------------------
* TextViewUtils
*--------------------------------------------------*/
		
		public static String plus(TextView a, TextView b) {
			return TextViewUtils.plusOrMinus(a, "+", b);
		}
		
		public static String minus(TextView a, TextView b) {
			return TextViewUtils.plusOrMinus(a, "-", b);
		}
		
		public static String calculate(TextView a, String operator, TextView b) {
			return TextViewUtils.plusOrMinus(a, operator, b);
		}
		
		public static void span(TextView... txts) {
			TextViewUtils.span(txts);
		}
		
		public static void keep(int keep, TextView... txts) {
			TextViewUtils.keep(keep, txts);
		}
		
		public static void erase(int keep, TextView... txts) {
			TextViewUtils.erase(keep, txts);
		}
		
		/*--------------------------------------------------
* Cutoff
*--------------------------------------------------*/	
		
		public static void cutoff(TextView... txts) {
			CUTOFF.applyCutoff(txts);
		}
		
		public static void cutoffSingle(TextView... txts) {
			CUTOFF.applyCutoffSingle(txts);
		}
		
		public static void cutoffRepeat(TextView... txts) {
			Cutoff.applyCutoffsame(txts);
		}
		
		/*--------------------------------------------------
* Pair Match
*--------------------------------------------------*/	
		
		public static ArrayList<String> plusplus(String pair) {
			return PairMatch.plusplus(pair);
		}
		
		public static ArrayList<String> minusminus(String pair) {
			return PairMatch.minusminus(pair);
		}
		
		public static ArrayList<String> plusminus(String pair) {
			return PairMatch.plusminus(pair);
		}
		
		public static ArrayList<String> minusplus(String pair) {
			return PairMatch.minusplus(pair);
		}
		
		/*--------------------------------------------------
* Pair Total
*--------------------------------------------------*/	
		
		public static int totalOf(String pair) {
			return PairTotal.whatIsTotalOf(pair);
		}
		
		public static ArrayList<String> totalPairs(String pair) {
			return PairTotal.getListOfTotal(pair);
		}
		
		public static ArrayList<String> totalPairs(int total) {
			return PairTotal.getListOfTotal(total);
		}
		
		public static boolean hasTotal(String pair) {
			return PairTotal.contains(pair);
		}
		
		/*--------------------------------------------------
* Pair Farak
*--------------------------------------------------*/	
		
		public static int farakOf(String pair) {
			return PairFarak0as10.whatIsFarakOf(pair);
		}
		
		public static ArrayList<String> farakPairs(String pair) {
			return PairFarak0as10.getListOfFarak(pair);
		}
		
		public static ArrayList<String> farakPairs(int farak) {
			return PairFarak0as10.getListOfFarak(farak);
		}
		
		public static boolean hasFarak(String pair) {
			return PairFarak0as10.contains(pair);
		}
		
		/*--------------------------------------------------
* Pair Total Farak
*--------------------------------------------------*/	
		
		public static ArrayList<String> totalFarakPairs(String pair) {
			return PairTotalFarak.get(pair);
		}
		
		public static boolean hasTotalFarak(String pair) {
			return PairTotalFarak.contains(pair);
		}
		
		/*--------------------------------------------------
* Family
*--------------------------------------------------*/	
		
		public static Map<String, List<TextView>> families(TextView... txts) {
			return FamilyAnalyzer.getFamilies(txts);
		}
		
		public static int familyCount(TextView... txts) {
			return FamilyAnalyzer.countFamilies(txts);
		}
		
		public static int familyCount(String family, TextView... txts) {
			return FamilyAnalyzer.getFamilyCount(family, txts);
		}
		
		public static List<TextView> familyMembers(String family, TextView... txts) {
			return FamilyAnalyzer.getFamilyMembers(family, txts);
		}
		
		public static Map.Entry<String, List<TextView>> largestFamily(TextView... txts) {
			return FamilyAnalyzer.largestFamily(txts);
		}
		
		public static Map.Entry<String, List<TextView>> smallestFamily(TextView... txts) {
			return FamilyAnalyzer.smallestFamily(txts);
		}
		
		public static boolean hasFamily(int size, TextView... txts) {
			return FamilyAnalyzer.hasFamily(size, txts);
		}
		
		public static Map<String, List<TextView>> familiesBySize(int min, TextView... txts) {
			return FamilyAnalyzer.getFamiliesBySize(min, txts);
		}
		
		public static void clearFamiliesLessThan(int min, TextView... txts) {
			FamilyAnalyzer.clearFamiliesLessThan(min, txts);
		}
		
		public static void clearFamiliesGreaterThan(int max, TextView... txts) {
			FamilyAnalyzer.clearFamiliesGreaterThan(max, txts);
		}
		
		public static void keepLargestFamily(TextView... txts) {
			FamilyAnalyzer.keepLargestFamily(txts);
		}
		
		public static void keepSmallestFamily(TextView... txts) {
			FamilyAnalyzer.keepSmallestFamily(txts);
		}
		
}
