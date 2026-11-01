package com.my.mert;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import laki.vinoxsingle.v1.R;

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {

    private GridView inputGridView;
    private GridView outputGridView;
    private ImageView btnReset;
    private Button btnEqual;
    private Button btnTrk;
    private ImageView btnHistory;
    private TextView resultText;

    private InputGridAdapter inputAdapter;
    private OutputGridAdapter outputAdapter;

    private final List<String> inputData = new ArrayList<>();
    private final List<String> outputData = new ArrayList<>();
    private boolean isInitialFocusDone = false;
    private String duple = "0";

    public String getDuple() {
        return duple;
    }

    public void setDuple(String duple) {
        this.duple = duple;
    }

    public void _req(final EditText _e, final String _a) {
        if (_e != null) {
            _e.setFocusable(true);
            _e.setFocusableInTouchMode(true);
            _e.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(_e, InputMethodManager.SHOW_IMPLICIT);
            }
        }
    }

    public void _sed(final EditText _e, final String _s) {
        if (_e != null && _s.length() == 1) {
            _e.setFocusable(true);
            _e.setFocusableInTouchMode(true);
            _e.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(_e, InputMethodManager.SHOW_IMPLICIT);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        initData();
        setupGridDimensions();
        setupAdapters();
        setupListeners();

        _backupRestore("restore");
    }

    private void focusFirstVisibleCellOnStartup() {
        if (!isInitialFocusDone && inputGridView != null) {
            inputGridView.postDelayed(() -> {
                int firstVisible = inputGridView.getFirstVisiblePosition();
                View firstChild = inputGridView.getChildAt(12 - firstVisible);
                if (firstChild != null) {
                    EditText firstEt = firstChild.findViewById(R.id.etInputCell);
                    if (firstEt != null) {
                        isInitialFocusDone = true;
                        _req(firstEt, inputData.get(12));
                    }
                }
            }, 150);
        }
    }

    private void initViews() {
        inputGridView = findViewById(R.id.inputGridView);
        outputGridView = findViewById(R.id.outputGridView);
        btnReset = findViewById(R.id.reset);
        btnEqual = findViewById(R.id.equal);
        btnTrk = findViewById(R.id.trk);
        btnHistory = findViewById(R.id.btnHistory);
        resultText = findViewById(R.id.resultText);
        
    }

    private void initData() {
        inputData.clear();
        for (int i = 0; i < 48; i++) {
            inputData.add("");
        }

        // Initialize all 64 output TextView cells as empty on startup
        outputData.clear();
        for (int i = 0; i < 64; i++) {
            outputData.add("");
        }
    }

    private void setupGridDimensions() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float density = metrics.density;

        int paddingPx = (int) (32 * density);
        int availableWidth = metrics.widthPixels - paddingPx;
        int spacingPx = (int) (2 * density);

        int inputCellWidth = (availableWidth - (11 * spacingPx)) / 12;
        int inputCellHeight = (int) (44 * density);

        int outputCellWidth = (availableWidth - (7 * spacingPx)) / 8;
        int outputCellHeight = (int) (44 * density);

        int inputGridHeight = (3 * inputCellHeight) + (2 * spacingPx) + (int) (4 * density);
        ViewGroup.LayoutParams inputParams = inputGridView.getLayoutParams();
        if (inputParams != null) {
            inputParams.height = inputGridHeight;
            inputGridView.setLayoutParams(inputParams);
        }

        int outputGridHeight = (8 * outputCellHeight) + (7 * spacingPx) + (int) (8 * density);
        ViewGroup.LayoutParams outputParams = outputGridView.getLayoutParams();
        if (outputParams != null) {
            outputParams.height = outputGridHeight;
            outputGridView.setLayoutParams(outputParams);
        }

        inputAdapter = new InputGridAdapter(this, inputData);
        inputAdapter.setCellDimensions(inputCellWidth, inputCellHeight);

        outputAdapter = new OutputGridAdapter(this, outputData);
        outputAdapter.setCellDimensions(outputCellWidth, outputCellHeight);
    }

    private void setupAdapters() {
        inputGridView.setAdapter(inputAdapter);
        outputGridView.setAdapter(outputAdapter);

        focusFirstVisibleCellOnStartup();
    }

    private void setupListeners() {
        if (btnReset != null) {
            btnReset.setOnClickListener(v -> {
                duple = "0";
                for (int i = 0; i < inputData.size(); i++) {
                    inputData.set(i, "");
                }
                for (int i = 0; i < outputData.size(); i++) {
                    outputData.set(i, "");
                }
                if (outputAdapter != null) {
                    outputAdapter.setSelectedPosition(-1);
                    outputAdapter.clearHighlights();
                }
                if (inputAdapter != null) inputAdapter.notifyDataSetChanged();
                if (outputAdapter != null) outputAdapter.notifyDataSetChanged();
                if (resultText != null) {
                    resultText.setText("Press TRK to run 4-Axis Cut scan on output grid.");
                }
                isInitialFocusDone = false;
                focusFirstVisibleCellOnStartup();
                Toast.makeText(this, "Cleared all inputs", Toast.LENGTH_SHORT).show();
            });
        }

        if (inputGridView != null) {
            inputGridView.setOnItemLongClickListener((parent, view, position, id) -> {
                showInputOptionsPopup(view);
                return true;
            });
        }

        if (btnHistory != null) {
            btnHistory.setOnClickListener(v -> showHistoryDialog());
        }

        if (btnEqual != null) {
            btnEqual.setOnClickListener(v -> {
                duple = "0";
                if (outputAdapter != null) {
                    outputAdapter.clearHighlights();
                }
                _totaladd();
                _backupRestore("backup");
                saveCurrentInputsToHistory();
            });
        }

        if (btnTrk != null) {
            btnTrk.setOnClickListener(v -> {
                if ("0".equals(duple)) {
                    executeCutScan();
                    executeCutAndSameScan();
                    duple = "1";
                }
            });
        }

        // Tap any cell in output grid to highlight that clicked cell and its vertical & horizontal axis neighbours
        if (outputGridView != null) {
            outputGridView.setOnItemClickListener((parent, view, position, id) -> {
                if (outputAdapter != null) {
                    outputAdapter.setSelectedPosition(position);
                }
            });
        }
    }

    private static final String PREF_HISTORY_KEY = "input_history_list";

    private List<String> getHistoryList() {
        SharedPreferences sp = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String raw = sp.getString(PREF_HISTORY_KEY, "");
        List<String> list = new ArrayList<>();
        if (raw != null && !raw.isEmpty()) {
            String[] items = raw.split(";;;");
            for (String item : items) {
                if (item != null && !item.isEmpty()) {
                    list.add(item);
                }
            }
        }
        return list;
    }

    private void saveHistoryList(List<String> list) {
        SharedPreferences sp = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) sb.append(";;;");
            sb.append(list.get(i));
        }
        sp.edit().putString(PREF_HISTORY_KEY, sb.toString()).apply();
    }

    private void saveCurrentInputsToHistory() {
        StringBuilder sb = new StringBuilder();
        boolean hasData = false;

        for (int i = 12; i < 48; i++) {
            String val = inputData.get(i);
            val = (val == null) ? "" : val.trim();
            sb.append(val).append(",");
            if (!val.isEmpty()) hasData = true;
        }

        if (!hasData) return;

        String currentEntry = sb.toString();
        List<String> history = getHistoryList();

        // Remove duplicate entry if it already exists
        history.remove(currentEntry);

        // Insert at index 0 (top / latest)
        history.add(0, currentEntry);

        // Keep maximum 5 items
        while (history.size() > 5) {
            history.remove(history.size() - 1);
        }

        saveHistoryList(history);
    }

    private void showHistoryDialog() {
        if (isFinishing() || isDestroyed()) return;

        List<String> historyList = getHistoryList();
        if (historyList.isEmpty()) {
            Toast.makeText(this, "No history available. Press = to save inputs.", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] displayItems = new String[historyList.size()];
        for (int i = 0; i < historyList.size(); i++) {
            String entry = historyList.get(i);
            String[] parts = entry.split("\\|", -1);
            String gridPart = parts.length >= 1 ? parts[0] : entry;
            StringBuilder sb = new StringBuilder();
            sb.append("#").append(i + 1).append("  Grid: ");

            String[] gridParts = gridPart.split(",", -1);
            int count = 0;
            for (String g : gridParts) {
                if (g != null && !g.trim().isEmpty() && count < 12) {
                    sb.append(g.trim()).append(" ");
                    count++;
                }
            }
            if (count == 0) sb.append("(empty)");
            displayItems[i] = sb.toString();
        }

        com.google.android.material.dialog.MaterialAlertDialogBuilder builder =
                new com.google.android.material.dialog.MaterialAlertDialogBuilder(this);
        builder.setTitle("Input History (Last 5)");
        builder.setItems(displayItems, (dialog, which) -> {
            if (which >= 0 && which < historyList.size()) {
                restoreHistoryEntry(historyList.get(which));
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.setOnShowListener(d -> {
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(
                        OutputGridAdapter.createHighlightDrawable(0xFFFF6D00, 0xFF181818)
                );
            }
            android.widget.Button negButton = dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE);
            if (negButton != null) {
                negButton.setTextColor(0xFF00E5FF);
                negButton.setTextSize(16f);
            }
            android.widget.TextView titleView = dialog.findViewById(androidx.appcompat.R.id.alertTitle);
            if (titleView != null) {
                titleView.setTextColor(0xFFFF6D00);
                titleView.setTextSize(18f);
            }
        });
        dialog.show();
    }

    private void restoreHistoryEntry(String entry) {
        String[] parts = entry.split("\\|", -1);
        String gridPart = parts.length >= 1 ? parts[0] : entry;
        String[] gridParts = gridPart.split(",", -1);
        for (int i = 0; i < 36; i++) {
            String val = (i < gridParts.length) ? gridParts[i] : "";
            inputData.set(12 + i, val);
        }
        if (inputAdapter != null) inputAdapter.notifyDataSetChanged();
        _backupRestore("backup");
        Toast.makeText(this, "History inputs restored!", Toast.LENGTH_SHORT).show();
    }

    /**
     * TRK 4-Axis Position-Specific Cut Scan:
     * For each of the 64 output cells (owner):
     * 1. Right Digit (d2) is scanned against Right Digits of 4-axis neighbors:
     *    - If Cut(d2) is found in neighbor Right Digits, d2 is removed.
     * 2. Left Digit (d1) is scanned against Left Digits of 4-axis neighbors:
     *    - If Cut(d1) is found in neighbor Left Digits, d1 is removed.
     * 3. All 64 cells are scanned against the original snapshot before applying erasures at the end.
     */
    public void executeCutScan() {
        List<String> snapshotData = new ArrayList<>(outputData);
        String[] newOutputValues = new String[64];
        int modifiedCount = 0;

        for (int pos = 0; pos < 64; pos++) {
            String val = snapshotData.get(pos) != null ? snapshotData.get(pos).trim() : "";
            if (val.length() != 2) {
                newOutputValues[pos] = val;
                continue;
            }

            char d1 = val.charAt(0); // Left digit
            char d2 = val.charAt(1); // Right digit
            char c1 = getCutDigit(d1);
            char c2 = getCutDigit(d2);

            int row = pos / 8;
            int col = pos % 8;

            Set<Character> neighborLeftDigits = new HashSet<>();
            Set<Character> neighborRightDigits = new HashSet<>();

            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    if (r == row && c == col) continue; // Skip owner cell

                    boolean isHorizontal = (r == row);
                    boolean isVertical = (c == col);
                    boolean isLeftCross = ((r - c) == (row - col));
                    boolean isRightCross = ((r + c) == (row + col));

                    if (isHorizontal || isVertical || isLeftCross || isRightCross) {
                        String nVal = snapshotData.get(r * 8 + c) != null ? snapshotData.get(r * 8 + c).trim() : "";
                        if (nVal.length() == 2) {
                            neighborLeftDigits.add(nVal.charAt(0));
                            neighborRightDigits.add(nVal.charAt(1));
                        } else if (nVal.length() == 1) {
                            neighborLeftDigits.add(nVal.charAt(0));
                            neighborRightDigits.add(nVal.charAt(0));
                        }
                    }
                }
            }

            StringBuilder result = new StringBuilder();
            // Scan Left digit against neighbor Left digits
            if (!neighborLeftDigits.contains(c1)) {
                result.append(d1);
            }
            // Scan Right digit against neighbor Right digits
            if (!neighborRightDigits.contains(c2)) {
                result.append(d2);
            }

            String newVal = result.toString();
            newOutputValues[pos] = newVal;
            if (!newVal.equals(val)) {
                modifiedCount++;
            }
        }

        // Apply all erasures at the end after scanning all 64 cells
        for (int i = 0; i < 64; i++) {
            outputData.set(i, newOutputValues[i]);
        }

        if (outputAdapter != null) {
            outputAdapter.clearHighlights();
            outputAdapter.notifyDataSetChanged();
        }

        if (resultText != null) {
            resultText.setText("TRK Position-Specific Cut Scan Complete: Processed 64 cells across 4 axes (" + modifiedCount + " updated).");
        }
    }

    /**
     * TRK 4-Axis Position-Specific Cut & Same Scan:
     * For each of the 64 output cells (owner):
     * 1. Right Digit (d2) is scanned against Right Digits of 4-axis neighbors:
     *    - If Same(d2) OR Cut(d2) is found in neighbor Right Digits, d2 is removed.
     * 2. Left Digit (d1) is scanned against Left Digits of 4-axis neighbors:
     *    - If Same(d1) OR Cut(d1) is found in neighbor Left Digits, d1 is removed.
     * 3. All 64 cells are scanned against the current grid snapshot before applying erasures at the end.
     */
    public void executeCutAndSameScan() {
        List<String> snapshotData = new ArrayList<>(outputData);
        String[] newOutputValues = new String[64];
        int modifiedCount = 0;

        for (int pos = 0; pos < 64; pos++) {
            String val = snapshotData.get(pos) != null ? snapshotData.get(pos).trim() : "";
            if (val.isEmpty() || (val.length() != 1 && val.length() != 2)) {
                newOutputValues[pos] = val;
                continue;
            }

            int row = pos / 8;
            int col = pos % 8;

            Set<Character> neighborLeftDigits = new HashSet<>();
            Set<Character> neighborRightDigits = new HashSet<>();
            Set<Character> allNeighborDigits = new HashSet<>();

            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    if (r == row && c == col) continue; // Skip owner cell

                    boolean isHorizontal = (r == row);
                    boolean isVertical = (c == col);
                    boolean isLeftCross = ((r - c) == (row - col));
                    boolean isRightCross = ((r + c) == (row + col));

                    if (isHorizontal || isVertical || isLeftCross || isRightCross) {
                        String nVal = snapshotData.get(r * 8 + c) != null ? snapshotData.get(r * 8 + c).trim() : "";
                        if (nVal.length() == 2) {
                            neighborLeftDigits.add(nVal.charAt(0));
                            neighborRightDigits.add(nVal.charAt(1));
                            allNeighborDigits.add(nVal.charAt(0));
                            allNeighborDigits.add(nVal.charAt(1));
                        } else if (nVal.length() == 1) {
                            neighborLeftDigits.add(nVal.charAt(0));
                            neighborRightDigits.add(nVal.charAt(0));
                            allNeighborDigits.add(nVal.charAt(0));
                        }
                    }
                }
            }

            StringBuilder result = new StringBuilder();

            if (val.length() == 2) {
                char d1 = val.charAt(0); // Left digit
                char d2 = val.charAt(1); // Right digit
                char c1 = getCutDigit(d1);
                char c2 = getCutDigit(d2);

                // Keep Left digit d1 ONLY if NEITHER same digit (d1) NOR cut digit (c1) is present in neighbor Left digits
                boolean leftCutFound = neighborLeftDigits.contains(c1);
                boolean leftSameFound = neighborLeftDigits.contains(d1);
                if (!leftCutFound && !leftSameFound) {
                    result.append(d1);
                }

                // Keep Right digit d2 ONLY if NEITHER same digit (d2) NOR cut digit (c2) is present in neighbor Right digits
                boolean rightCutFound = neighborRightDigits.contains(c2);
                boolean rightSameFound = neighborRightDigits.contains(d2);
                if (!rightCutFound && !rightSameFound) {
                    result.append(d2);
                }
            } else if (val.length() == 1) {
                char d1 = val.charAt(0);
                char c1 = getCutDigit(d1);

                // For a 1-digit owner, check if same digit d1 OR cut digit c1 is present in neighbor digits
                boolean cutFound = allNeighborDigits.contains(c1);
                boolean sameFound = allNeighborDigits.contains(d1);
                if (!cutFound && !sameFound) {
                    result.append(d1);
                }
            }

            String newVal = result.toString();
            newOutputValues[pos] = newVal;
            if (!newVal.equals(val)) {
                modifiedCount++;
            }
        }

        // Apply all erasures at the end after scanning all 64 cells
        for (int i = 0; i < 64; i++) {
            outputData.set(i, newOutputValues[i]);
        }

        if (outputAdapter != null) {
            outputAdapter.clearHighlights();
            outputAdapter.notifyDataSetChanged();
        }

        if (resultText != null) {
            String currentTxt = resultText.getText() != null ? resultText.getText().toString() : "";
            resultText.setText(currentTxt + "\nCut & Same Scan: Updated " + modifiedCount + " cells.");
        }
    }

    private char getCutDigit(char d) {
        switch (d) {
            case '1': return '6';
            case '6': return '1';
            case '2': return '7';
            case '7': return '2';
            case '3': return '8';
            case '8': return '3';
            case '4': return '9';
            case '9': return '4';
            case '5': return '0';
            case '0': return '5';
            default: return d;
        }
    }

    public void _totaladd() {
        try {
            String[] etVal = new String[36];
            for (int i = 0; i < 36; i++) {
                etVal[i] = inputData.get(12 + i);
            }

            for (int i = 0; i < 8; i++) {
                for (int k = 0; k < 2; k++) {
                    int etOff = k * 18;
                    int colOffset = k * 4;

                    String val1 = _nor(etVal[etOff + 2 * i], "+", etVal[etOff + 2 * i + 2])
                            + _nor(etVal[etOff + 2 * i + 1], "-", etVal[etOff + 2 * i + 3]);

                    String val2 = _nor(etVal[etOff + 2 * i], "-", etVal[etOff + 2 * i + 2])
                            + _nor(etVal[etOff + 2 * i + 1], "+", etVal[etOff + 2 * i + 3]);

                    String val3 = _nor(etVal[etOff + i], "+", etVal[etOff + i + 2])
                            + _nor(etVal[etOff + i + 8], "+", etVal[etOff + i + 10]);

                    String val4 = _nor(etVal[etOff + i], "-", etVal[etOff + i + 2])
                            + _nor(etVal[etOff + i + 8], "-", etVal[etOff + i + 10]);

                    int baseIndex = (8 * i) + colOffset;
                    outputData.set(baseIndex, val1);
                    outputData.set(baseIndex + 1, val2);
                    outputData.set(baseIndex + 2, val3);
                    outputData.set(baseIndex + 3, val4);
                }
            }
            if (outputAdapter != null) outputAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public String _nor(final String sa, final String k, final String sb) {
        if (sa != null && !sa.trim().isEmpty() && sb != null && !sb.trim().isEmpty()) {
            try {
                double valueA = Double.parseDouble(sa.trim());
                double valueB = Double.parseDouble(sb.trim());
                if ("-".equals(k)) {
                    long result = Math.abs((long) (valueA - valueB));
                    return String.valueOf(result);
                } else if ("+".equals(k)) {
                    long result = (long) (valueA + valueB);
                    if (String.valueOf(result).length() == 2) {
                        result %= 10;
                    }
                    return String.valueOf(result);
                }
            } catch (NumberFormatException e) {
                return "";
            }
        }
        return "";
    }

    public void _backupRestore(final String _action) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean isBackup = _action.equalsIgnoreCase("backup");

        for (int i = 0; i < 36; i++) {
            String key = "edittext" + (i + 1);
            if (isBackup) {
                String data = inputData.get(12 + i);
                if (!data.isEmpty()) {
                    editor.putString(key, data);
                } else {
                    editor.remove(key);
                }
            } else {
                if (sharedPreferences.contains(key)) {
                    inputData.set(12 + i, sharedPreferences.getString(key, ""));
                } else {
                    inputData.set(12 + i, "");
                }
            }
        }

        if (isBackup) {
            editor.apply();
        } else {
            if (inputAdapter != null) inputAdapter.notifyDataSetChanged();
        }
    }

    public void _pasteNumbers(final String _copied) {
        _pasteNumbers(_copied, null);
    }

    public void _pasteNumbers(final String _copied, final View sourceView) {
        if (_copied == null) return;

        List<String> digits = new ArrayList<>();
        Matcher m = Pattern.compile("\\d").matcher(_copied.trim());
        while (m.find()) {
            digits.add(m.group());
        }

        if (digits.isEmpty()) return;

        for (int i = 0; i < 36 && i < digits.size(); i++) {
            inputData.set(12 + i, digits.get(i));
        }
        if (inputAdapter != null) {
            inputAdapter.notifyDataSetChanged();
        }
    }

    public void showInputOptionsPopup(View anchorView) {
        if (isFinishing() || isDestroyed()) return;

        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_input_options, null);

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setElevation(16f);

        popupView.findViewById(R.id.optionCopy).setOnClickListener(v -> {
            popupWindow.dismiss();
            copyAllInputs();
        });

        popupView.findViewById(R.id.optionPaste).setOnClickListener(v -> {
            popupWindow.dismiss();
            pasteInputsFromClipboard(anchorView);
        });

        popupView.findViewById(R.id.optionClear).setOnClickListener(v -> {
            popupWindow.dismiss();
            clearAllInputs();
        });

        if (anchorView != null && anchorView.isShown()) {
            try {
                int screenWidth = getResources().getDisplayMetrics().widthPixels;
                int screenHeight = getResources().getDisplayMetrics().heightPixels;
                int[] location = new int[2];
                anchorView.getLocationOnScreen(location);

                int popupWidth = (int) (260 * getResources().getDisplayMetrics().density);
                int xPos = location[0];
                if (xPos + popupWidth > screenWidth - 32) {
                    xPos = screenWidth - popupWidth - 32;
                }
                if (xPos < 32) xPos = 32;

                int yPos = location[1] + anchorView.getHeight() + 8;
                if (yPos > screenHeight - 200) {
                    yPos = location[1] - (int) (180 * getResources().getDisplayMetrics().density);
                }

                popupWindow.showAtLocation(findViewById(R.id.main), Gravity.TOP | Gravity.START, xPos, yPos);
            } catch (Exception e) {
                popupWindow.showAtLocation(findViewById(R.id.main), Gravity.CENTER, 0, 0);
            }
        } else {
            popupWindow.showAtLocation(findViewById(R.id.main), Gravity.CENTER, 0, 0);
        }
    }

    public void copyAllInputs() {
        StringBuilder sb = new StringBuilder();

        // Collect from 36 main grid inputs
        for (int i = 12; i < 48; i++) {
            String val = inputData.get(i);
            if (val != null && !val.isEmpty()) {
                sb.append(val);
            }
        }

        String textToCopy = sb.toString();
        if (textToCopy.isEmpty()) {
            Toast.makeText(this, "No inputs to copy", Toast.LENGTH_SHORT).show();
            return;
        }
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            ClipData clip = ClipData.newPlainText("Inputs", textToCopy);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "Inputs copied to clipboard", Toast.LENGTH_SHORT).show();
        }
    }

    public void pasteInputsFromClipboard(View sourceView) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null && clipboard.hasPrimaryClip()) {
            ClipData clip = clipboard.getPrimaryClip();
            if (clip != null && clip.getItemCount() > 0) {
                CharSequence text = clip.getItemAt(0).getText();
                if (text != null && text.length() > 0) {
                    _pasteNumbers(text.toString(), sourceView);
                    Toast.makeText(this, "Inputs pasted", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
        Toast.makeText(this, "Clipboard is empty", Toast.LENGTH_SHORT).show();
    }

    public void clearAllInputs() {
        for (int i = 12; i < 48; i++) {
            inputData.set(i, "");
        }
        if (inputAdapter != null) {
            inputAdapter.notifyDataSetChanged();
        }
        Toast.makeText(this, "Cleared all inputs", Toast.LENGTH_SHORT).show();
    }
}