//package com.my.mert;
//
//import androidx.appcompat.app.AppCompatActivity;
//import android.animation.*;
//        import android.app.*;
//        import android.app.Activity;
//import android.content.*;
//        import android.content.SharedPreferences;
//import android.content.res.*;
//        import android.graphics.*;
//        import android.graphics.drawable.*;
//        import android.media.*;
//        import android.net.*;
//        import android.os.*;
//        import android.text.*;
//        import android.text.style.*;
//        import android.util.*;
//        import android.view.*;
//        import android.view.View;
//import android.view.View.*;
//        import android.view.animation.*;
//        import android.webkit.*;
//        import android.widget.*;
//
//        import androidx.annotation.*;
//        import androidx.appcompat.app.AppCompatActivity;
//
//import com.my.mert.R;
//import com.my.mert.SataMatka;
//
//import java.io.*;
//        import java.text.*;
//        import java.util.*;
//        import java.util.regex.*;
//
//        import org.json.*;
//public class main2activity extends AppCompatActivity {
//
//    private String duple = "";
//    private LinearLayout back, header, inputlayer, linear3, output;
//    private ScrollView vscroll1;
//    private TextView textview65;
//    private LinearLayout linear5, linear24, linear26, linear1, linear2;
//    private EditText[] et = new EditText[36];
//    private LinearLayout linear23;
//    private Button reset;
//    private Button equal;
//    private Button trk;
//    private LinearLayout linear4, linear8, linear10, linear13, linear14, linear7;
//    private TextView outputy;
//
//    private SharedPreferences sh;
//
//    private TextView[] grid;
//    private static final int[] DIGIT_COLORS = {0xFFFFFF00, // 0,5
//            0xFF00E5FF, /* 1,6*/    0xFF00FF00, /*2,7*/
//            0xFFFF6D00, /* 3,8*/   0xFFFF4081  /*4,9*/};
//    SataMatka sm = new SataMatka();
//
//    @Override
//    protected void onCreate(Bundle _savedInstanceState) {
//        super.onCreate(_savedInstanceState);
//        //setContentView(R.layout.main);
//        initialize(_savedInstanceState);
//        initializeLogic();
//    }
//
//    private void initialize(Bundle _savedInstanceState) {
//        _bindLinearLayouts((ViewGroup) getWindow().getDecorView());
//        vscroll1 = findViewById(R.id.vscroll1);
//        textview65 = findViewById(R.id.textview65);
//        for (int i = 0; i < 18; i++) {
//            et[i] = findViewById(getResources().getIdentifier(String.valueOf((char) ('a' + i)), "id", getPackageName()));
//            et[i + 18] = findViewById(getResources().getIdentifier("edittext" + (i + 1), "id", getPackageName()));
//        }
//        reset = findViewById(R.id.reset);
//        equal = findViewById(R.id.equal);
//        trk = findViewById(R.id.trk);
//        outputy = findViewById(R.id.outputy);
//        sh = getSharedPreferences("sh", Activity.MODE_PRIVATE);
//
//        reset.setOnClickListener(_v -> {
//            _clearAllTextViews(output);
//            _clearAllTextViews(inputlayer);
//            et[0].requestFocus();
//        });
//
//        equal.setOnClickListener(_v -> {
//            duple = "0";
//            _totaladd();
//            //sm.convertVisibleTextViews(Arrays.copyOfRange(grid, 1, 65));
//            sm.span(Arrays.copyOfRange(grid, 1, 65));
//            _backupRestore(inputlayer, "backup");
//        });
//
//        trk.setOnClickListener(_v -> {
//            if (duple.equals("0")) {
//                duple = "1";
//                applyAxisCutScan(grid);
//                sm.mirrorPair(Arrays.copyOfRange(grid, 1, 65));
//            }
//        });
//    }
//
//    private void initializeLogic() {
//        initGrid();
//        new LakiActivity(grid);
//        addTextWatcherToAll(et);
//        _clearAllTextViews(inputlayer);
//        _clearAllTextViews(output);
//        et[0].requestFocus();
//        _backupRestore(inputlayer, "restore");
//    }
//
//    public void _totaladd() {
//        try {
//            for (int k = 0; k < 2; k++) {
//                int etOff = k * 18;
//                int grOff = k * 32;
//                for (int i = 0; i < 8; i++) {
//                    grid[i + 1 + grOff].setText(_nor(et[etOff + 2 * i], "+", et[etOff + 2 * i + 2]) + _nor(et[etOff + 2 * i + 1], "-", et[etOff + 2 * i + 3]));
//                    grid[i + 9 + grOff].setText(_nor(et[etOff + 2 * i], "-", et[etOff + 2 * i + 2]) + _nor(et[etOff + 2 * i + 1], "+", et[etOff + 2 * i + 3]));
//                    grid[i + 17 + grOff].setText(_nor(et[etOff + i], "+", et[etOff + i + 2]) + _nor(et[etOff + i + 8], "+", et[etOff + i + 10]));
//                    grid[i + 25 + grOff].setText(_nor(et[etOff + i], "-", et[etOff + i + 2]) + _nor(et[etOff + i + 8], "-", et[etOff + i + 10]));
//                }
//            }
//            sm.span(Arrays.copyOfRange(grid, 1, 65));
//        } catch (Exception laki) {
//            Toast.makeText(getApplicationContext(), laki.toString(), Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public void _req(final EditText _e, final String _a) {
//        if (_a.equals("")) _e.requestFocus();
//    }
//
//    public void _sed(final EditText _e, final String _s) {
//        if (_s.length() == 1) _e.requestFocus();
//    }
//
//    public void _clearAllTextViews(final ViewGroup _parent) {
//        for (int i = 0; i < _parent.getChildCount(); i++) {
//            View child = _parent.getChildAt(i);
//            if (child instanceof TextView) {
//                // If the child is a TextView, set its text to an empty string
//                ((TextView) child).setText("");
//            } else if (child instanceof ViewGroup) {
//                // If the child is a ViewGroup (e.g., LinearLayout), recursively traverse it
//                _clearAllTextViews((ViewGroup) child);
//            }
//        }
//    }
//
//    public String _nor(final TextView _a, final String _k, final TextView _b) {
//        if (_a != null && !_a.getText().toString().isEmpty() && _b != null && !_b.getText().toString().isEmpty()) {
//            try {
//                double valueA = Double.parseDouble(_a.getText().toString());
//                double valueB = Double.parseDouble(_b.getText().toString());
//                if (_k.equals("-")) {                        // Get absolute value for subtraction
//                    long result = Math.abs((long) (valueA - valueB));
//                    return String.valueOf(result);
//                } else if (_k.equals("+")) {
//                    long result = (long) (valueA + valueB);                        // Apply mod 10 if result has a length of 2
//                    if (String.valueOf(result).length() == 2) {
//                        result %= 10;
//                    }
//                    return String.valueOf(result);
//                } else {
//                    return ""; // Handle unexpected operators
//                }
//            } catch (NumberFormatException e) {
//                return ""; // Handle invalid number format
//            }
//        }
//        return ""; // Handle empty or null input
//    }
//
//    private void _bindLinearLayouts(ViewGroup parent) {
//        for (int i = 0; i < parent.getChildCount(); i++) {
//            View v = parent.getChildAt(i);
//            if (v instanceof LinearLayout && v.getId() != View.NO_ID) {
//                try {
//                    String idName = getResources().getResourceEntryName(v.getId());
//                    java.lang.reflect.Field field = getClass().getDeclaredField(idName);
//                    field.setAccessible(true);
//                    field.set(this, v);
//                } catch (Exception ignored) {}
//            }
//            if (v instanceof ViewGroup) _bindLinearLayouts((ViewGroup) v);
//        }
//    }
//
//    private void addTextWatcherToAll(EditText[] editTexts) {
//        for (int i = 0; i < editTexts.length; i++) {
//            int currentIndex = i;
//            editTexts[i].setHint("");
//            editTexts[i].addTextChangedListener(new TextWatcher() {
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    // Paste allowed only in first EditText
//                    if (currentIndex == 0 && s != null && s.length() > 1) {
//                        _pasteNumbers(s.toString(), editTexts);
//                        return;
//                    }
//                    // Prevent more than one digit in all EditTexts
//                    if (s != null && s.length() > 1) {
//                        editTexts[currentIndex].setText(
//                                String.valueOf(s.charAt(0)));
//                        editTexts[currentIndex].setSelection(1);
//                        return;
//                    }
//                    String charSeq = s.toString();
//                    if (currentIndex > 0) {
//                        _req(editTexts[currentIndex - 1], charSeq);
//                    }
//                    if (currentIndex < editTexts.length - 1) {
//                        _sed(editTexts[currentIndex + 1], charSeq);
//                    }
//                    if (s == null || s.length() == 0) {
//                        return;
//                    }
//                    int digit = s.charAt(0) - '0';
//                    editTexts[currentIndex].setTextColor(DIGIT_COLORS[digit % 5]);
//                }
//
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                }
//            });
//        }
//    }
//
//    public void _backupRestore(final ViewGroup _parent, final String _action) {
//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        boolean isBackup = _action.equalsIgnoreCase("backup");
//        for (int i = 0; i < _parent.getChildCount(); i++) {
//            View child = _parent.getChildAt(i);
//            if (child instanceof EditText) {
//                EditText editText = (EditText) child;
//                int viewId = editText.getId();
//                if (viewId != View.NO_ID) {
//                    String idName = getResources().getResourceEntryName(viewId);
//                    if (isBackup) {
//                        String data = editText.getText().toString();
//                        if (!data.isEmpty()) {
//                            editor.putString(idName, data);
//                        }
//                    } else {
//                        if (sharedPreferences.contains(idName)) {
//                            editText.setText(sharedPreferences.getString(idName, ""));
//                        }
//                    }
//                }
//            }
//            if (child instanceof ViewGroup) {
//                _backupRestore((ViewGroup) child, _action); // Recursive call for nested ViewGroup
//            }
//        }
//        if (isBackup) {
//            editor.apply();
//        }
//    }
//
//    public void initGrid() {
//        grid = new TextView[65];
//        for (int i = 1; i <= 64; i++) {
//            String no = i + "";
//            int id = getResources().getIdentifier("textview" + i, "id", getPackageName());
//            if (id != 0) {
//                grid[i] = findViewById(id);
//                if (grid[i] != null) {
//                    grid[i].setTextSize(25f);
//                    grid[i].setText(no);
//                }
//            }
//        }
//
//    }
//
//    public void _pasteNumbers(final String _copied, final EditText... _edits) {
//        if (_copied == null || _edits == null || _edits.length == 0) return;
//        String copy = _copied.trim();
//        int i = 0;
//        // Contains spaces, new lines, /, -, etc.
//        if (copy.matches(".*[^0-9].*")) {
//            Matcher m = Pattern.compile("\\d").matcher(copy);
//            while (m.find() && i < _edits.length) {
//                _edits[i].setText(m.group());
//                i++;
//            }
//        } else {    // All digits joined together
//            for (int pos = 0; pos < copy.length() && i < _edits.length; pos++) {
//                _edits[i].setText(String.valueOf(copy.charAt(pos)));
//                i++;
//            }
//        }
//    }
//
//    private void applyAxisCutScan(TextView... grid) {
//        // Reset grid to colorful state first
//        sm.span(Arrays.copyOfRange(grid, 1, 65));
//        // Store original values before we start modifying the grid
//        String[] originalValues = new String[65];
//        for (int i = 1; i <= 64; i++) {
//            originalValues[i] = grid[i].getText().toString().trim();
//        }
//        for (int i = 1; i <= 64; i++) {
//            String val = originalValues[i];
//            if (val.length() != 2) continue; // Only process pairs
//            char d1 = val.charAt(0);
//            char d2 = val.charAt(1);
//            char c1 = getCut(d1);
//            char c2 = getCut(d2);
//            int row = (i - 1) / 8;
//            int col = (i - 1) % 8;
//            HashSet<Character> axisDigits = new HashSet<>();
//            // Scan Vertical Axis (Column)
//            for (int r = 0; r < 8; r++) {
//                if (r == row) continue;
//                String nVal = originalValues[r * 8 + col + 1];
//                for (char ch : nVal.toCharArray()) axisDigits.add(ch);
//            }
//            // Scan Horizontal Axis (Row)
//            for (int c = 0; c < 8; c++) {
//                if (c == col) continue;
//                String nVal = originalValues[row * 8 + c + 1];
//                for (char ch : nVal.toCharArray()) axisDigits.add(ch);
//            }
//            // Build new text: only keep digit if its cut is NOT present in axis
//            StringBuilder result = new StringBuilder();
//            if (!axisDigits.contains(c1)) result.append(d1);
//            if (!axisDigits.contains(c2)) result.append(d2);
//            grid[i].setText(result.toString());
//        }
//        // Re-apply colorful spans to the remaining digits
//        sm.span(Arrays.copyOfRange(grid, 1, 65));
//    }
//
//    private char getCut(char d) {
//        switch (d) {
//            case '1': return '6';
//            case '6': return '1';
//            case '2': return '7';
//            case '7': return '2';
//            case '3': return '8';
//            case '8': return '3';
//            case '4': return '9';
//            case '9': return '4';
//            case '5': return '0';
//            case '0': return '5';
//            default: return d;
//        }
//    }
//}
