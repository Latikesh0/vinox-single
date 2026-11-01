package com.my.mert;

import android.view.*;
import android.widget.*;
import java.io.*;
import java.text.*;
import android.text.*;
import android.text.style.*;
import java.util.*;

public class SataMatka{
	//kiti dakhvyche
	public void keepOnly(int keep, TextView... txts) {
		if (txts == null) return;
		int count = 0;
		for (TextView tv : txts) {
			if (tv == null) continue;
			
			String s = tv.getText().toString().trim();
			if (!s.isEmpty()) {
				count++;
			}
		}
		boolean allowed =
		String.valueOf(keep).contains(String.valueOf(count));
		if (!allowed) {
			for (TextView tv : txts) {
				if (tv != null) {
					tv.setText("");
				}
			}
		}
	}//doublepair means 11,22,33
	public void doublePair(TextView... txts) {
		if (txts == null) return;
		for (int i = 0; i < txts.length; i++) {
			TextView tv1 = txts[i];
			if (tv1 == null || tv1.getText() == null)
			continue;
			String s1 = tv1.getText().toString().trim();
			// Only allow exactly 2 digits
			if (!s1.matches("\\d{2}"))
			continue;
			// Ignore 11,22,33...99,00
			if (s1.charAt(0) == s1.charAt(1))
			continue;
			String reverse ="" + s1.charAt(1) + s1.charAt(0);
			for (int j = i + 1; j < txts.length; j++) {
				TextView tv2 = txts[j];
				if (tv2 == null || tv2.getText() == null)
				continue;
				String s2 = tv2.getText().toString().trim();
				if (s2.equals(reverse)) {
					tv1.setText("");
					tv2.setText("");
					break;
				}
			}
		}
	}//mirror pair 12,21
	public void mirrorPair(TextView... txts) {
		for (int i = 0; i < txts.length; i++) {
			TextView tv1 = txts[i];
			if (tv1 == null) continue;
			String s1 = tv1.getText().toString().trim();
			if (s1.length() != 2) continue;
			// Ignore 11, 22, 33, ... 00
			if (s1.charAt(0) == s1.charAt(1)) continue;
			for (int j = i + 1; j < txts.length; j++) {
				TextView tv2 = txts[j];
				if (tv2 == null) continue;
				String s2 = tv2.getText().toString().trim();
				if (s2.length() != 2) continue;
				// Ignore 11, 22, 33, ... 00
				if (s2.charAt(0) == s2.charAt(1)) continue;
				// Mirror pair: 12↔21, 47↔74, etc.
				if (s1.charAt(0) == s2.charAt(1) && s1.charAt(1) == s2.charAt(0)) {
					tv1.setText("");
					tv2.setText("");
				}
			}
		}
	}
	private void showRowColumn(TextView[] grid, int pos) {
		ArrayList<TextView> list = new ArrayList<>();
		int row = (pos - 1) % 8;
		int col = (pos - 1) / 8;
		// Add row (1,9,17,25...)
		for (int i = 0; i < 8; i++) {
			int index = row + 1 + i * 8;
			list.add(grid[index]);
		}
		// Add column (1,2,3,4...)
		int start = col * 8 + 1;
		for (int i = 0; i < 8; i++) {
			int index = start + i;
			// avoid duplicate of clicked cell
			if (!list.contains(grid[index])) {
				list.add(grid[index]);
			}
		}    
		applyCutoffSingle(list.toArray(new TextView[0]));
	}
	private void showColumn(TextView[] grid, int pos) {
		ArrayList<TextView> list = new ArrayList<>();
		int start = ((pos - 1) / 8) * 8 + 1;
		for (int i = 0; i < 8; i++) {
			list.add(grid[start + i]);
		}
		applyCutoffSingle(list.toArray(new TextView[0]));
	}
	private void showRow(TextView[] grid, int pos) {
		ArrayList<TextView> list = new ArrayList<>();
		int row = (pos - 1) % 8;
		for (int i = 0; i < 8; i++) {
			list.add(grid[row + 1 + i * 8]);
		}
		applyCutoffSingle(list.toArray(new TextView[0]));
	}
	public void convertVisibleTextViews(TextView... txts) {
		for (TextView tv : txts) {
			if (tv != null && tv.getText() != null) {
				String value = tv.getText().toString().trim();
				if (value.matches("\\d{2}")) {
					int a = Character.getNumericValue(value.charAt(0));
					int b = Character.getNumericValue(value.charAt(1));
					tv.setText(String.valueOf((a + b) % 10));
				}
			}
		}
	}
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
	public String plusOrMinus(final TextView a, final String k, final TextView b) {
		if (a == null || b == null) return "";
		String sa = a.getText().toString().trim();
		String sb = b.getText().toString().trim();
		if (sa.isEmpty() || sb.isEmpty()) return "";
		try {
			int valueA = Integer.parseInt(sa);
			int valueB = Integer.parseInt(sb);
			if ("+".equals(k)) {
				return String.valueOf((valueA + valueB) % 10);
			}
			if ("-".equals(k)) {
				int x = (valueA == 0) ? 10 : valueA;
				int y = (valueB == 0) ? 10 : valueB;
				return String.valueOf(Math.abs(x - y) % 10);
			}
			return "";
		} catch (NumberFormatException e) {
			return "";
		}
	}
	public void span(TextView... txts) {
		int[] colors = {0xFFFFFF00, // 0,5
			0xFF00E5FF, // 1,6
			0xFF00FF00, // 2,7
			0xFFFF6D00, // 3,8
			0xFFFF4081  // 4,9
		};
		for (TextView txt : txts) {
			if (txt == null) continue;
			String text = txt.getText().toString();
			SpannableStringBuilder ssb = new SpannableStringBuilder(text);
			for (int i = 0; i < text.length(); i++) {
				char ch = text.charAt(i);
				if (Character.isDigit(ch)) {
					int digit = ch - '0';
					int color = colors[digit % 5];
					ssb.setSpan(
					new ForegroundColorSpan(color), i,
					i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
			}
			txt.setText(ssb);
            txt.setVisibility(View.VISIBLE);
		}
	}
}
