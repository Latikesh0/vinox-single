package com.my.mert;

import android.view.*;
import android.widget.*;
import java.io.*;
import java.text.*;
import android.text.*;
import android.text.style.*;
import java.util.*;

public class Cutoff {
	
	public void applyCutoffSingle(TextView... tvs) {
		HashMap<Character, Character> pair = new HashMap<>();
		pair.put('1', '6');
		pair.put('6', '1');
		pair.put('2', '7');
		pair.put('7', '2');
		pair.put('3', '8');
		pair.put('8', '3');
		pair.put('4', '9');
		pair.put('9', '4');
		pair.put('5', '0');
		pair.put('0', '5');		// Collect all digits present
		HashSet<Character> present = new HashSet<>();
		for (TextView tv : tvs) {
			String txt = tv.getText().toString().trim();
			if (txt.length() != 1 || !Character.isDigit(txt.charAt(0))) {
				continue;
			}
			present.add(txt.charAt(0));
		}		// Find digits to remove
		HashSet<Character> removeDigits = new HashSet<>();
		for (Character d : present) {
			Character p = pair.get(d);
			if (p != null && present.contains(p)) {
				removeDigits.add(d);
				removeDigits.add(p);
			}
		}		// Hide matching digits
		for (TextView tv : tvs) {
			String txt = tv.getText().toString().trim();
			if (txt.length() != 1 || !Character.isDigit(txt.charAt(0))) {
				continue;
			}
			char c = txt.charAt(0);
			if (removeDigits.contains(c)) {
				tv.setText("");
			}
		}
	}
	public void applyCutoff(TextView... tvs) {
		
		HashMap<Character, Character> pair = new HashMap<>();
		pair.put('1', '6');
		pair.put('6', '1');
		pair.put('2', '7');
		pair.put('7', '2');
		pair.put('3', '8');
		pair.put('8', '3');
		pair.put('4', '9');
		pair.put('9', '4');
		pair.put('5', '0');
		pair.put('0', '5');
		// Collect all digits present
		HashSet<Character> present = new HashSet<>();
		for (TextView tv : tvs) {
			String txt = tv.getText().toString().trim();						
			if (!txt.matches("\\d{2}")) {
				continue;
			}
			present.add(txt.charAt(0));
			present.add(txt.charAt(1));
		}	// Determine which digits must be removed
		HashSet<Character> removeDigits = new HashSet<>();
		for (Character d : present) {
			Character p = pair.get(d);
			if (p != null && present.contains(p)) {
				removeDigits.add(d);
				removeDigits.add(p);
			}
		}
		// Remove those digits from TextViews
		for (TextView tv : tvs) {
			String txt = tv.getText().toString().trim();
			if (!txt.matches("\\d{2}")) {
				continue;
			}
			StringBuilder result = new StringBuilder();
			for (int i = 0; i < txt.length(); i++) {
				char c = txt.charAt(i);
				if (!removeDigits.contains(c)) {
					result.append(c);
				}
			}
			tv.setText(result.toString());
		}
	}
	
	public static void applyCutoffsame(TextView... tvs) {
		// same same removing
		if (tvs == null) return;
		
		HashMap<Character, Character> pair = new HashMap<>();
		pair.put('1', '6');
		pair.put('6', '1');
		pair.put('2', '7');
		pair.put('7', '2');
		pair.put('3', '8');
		pair.put('8', '3');
		pair.put('4', '9');
		pair.put('9', '4');
		pair.put('5', '0');
		pair.put('0', '5');
		
		// ---------- First Cutoff ----------
		HashSet<Character> present = new HashSet<>();
		
		for (TextView tv : tvs) {
			if (tv == null) continue;
			
			String s = tv.getText().toString().trim();
			if (!s.matches("\\d{2}")) continue;
			
			present.add(s.charAt(0));
			present.add(s.charAt(1));
		}
		
		HashSet<Character> remove = new HashSet<>();
		
		for (char c : present) {
			Character p = pair.get(c);
			if (p != null && present.contains(p)) {
				remove.add(c);
				remove.add(p);
			}
		}
		
		for (TextView tv : tvs) {
			
			if (tv == null) continue;
			
			String s = tv.getText().toString().trim();
			if (!s.matches("\\d{2}")) continue;
			
			StringBuilder out = new StringBuilder();
			
			for (int i = 0; i < 2; i++) {
				char ch = s.charAt(i);
				if (!remove.contains(ch)) {
					out.append(ch);
				}
			}
			
			tv.setText(out.toString());
		}
		
		// ---------- Repeat until stable ----------
		boolean changed;
		
		do {
			
			changed = false;
			
			HashMap<Character, Integer> count = new HashMap<>();
			
			// Count in how many TextViews each digit appears
			for (TextView tv : tvs) {
				
				if (tv == null) continue;
				
				String s = tv.getText().toString().trim();
				if (s.isEmpty()) continue;
				
				HashSet<Character> once = new HashSet<>();
				
				for (int i = 0; i < s.length(); i++) {
					once.add(s.charAt(i));
				}
				
				for (char c : once) {
					count.put(c, count.getOrDefault(c, 0) + 1);
				}
			}
			
			// Remove digits appearing in more than one TextView
			for (TextView tv : tvs) {
				
				if (tv == null) continue;
				
				String s = tv.getText().toString().trim();
				if (s.isEmpty()) continue;
				
				StringBuilder out = new StringBuilder();
				
				for (int i = 0; i < s.length(); i++) {
					
					char c = s.charAt(i);
					
					if (count.getOrDefault(c, 0) <= 1) {
						out.append(c);
					} else {
						changed = true;
					}
				}
				
				tv.setText(out.toString());
			}
			
		} while (changed);
	}
}
