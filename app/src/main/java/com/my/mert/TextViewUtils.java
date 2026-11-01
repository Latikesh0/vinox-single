package com.my.mert;

import android.view.*;
import android.widget.*;
import java.io.*;
import java.text.*;
import android.text.*;
import android.text.style.*;
import java.util.*;

public class TextViewUtils {
	
	public static String plusOrMinus(final TextView a, final String k, final TextView b) {
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

	public static void span(TextView... txts) {
		int[] colors = {
			0xFFFFFF00, // 0,5
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
						new ForegroundColorSpan(color),
						i,
						i + 1,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
					);
				}
			}

			txt.setText(ssb);
			txt.setVisibility(View.VISIBLE);
		}
	}

	public static void keep(int keep, TextView... txts) {
		if (txts == null) return;

		int count = 0;

		for (TextView tv : txts) {
			if (tv == null) continue;
			String s = tv.getText().toString().trim();
			if (!s.isEmpty()) count++;
		}

		boolean allowed = String.valueOf(keep).contains(String.valueOf(count));

		if (!allowed) {
			for (TextView tv : txts) {
				if (tv != null) {
					tv.setText("");
				}
			}
		}
	}

	public static void erase(int keep, TextView... txts) {
		if (txts == null) return;

		int count = 0;

		for (TextView tv : txts) {
			if (tv == null) continue;
			String s = tv.getText().toString().trim();
			if (!s.isEmpty()) count++;
		}

		boolean allowed = String.valueOf(keep).contains(String.valueOf(count));

		if (allowed) {
			for (TextView tv : txts) {
				if (tv != null) {
					tv.setText("");
				}
			}
		}
	}
}