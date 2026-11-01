package com.my.mert;

import android.widget.TextView;

public class RowCol {

    private final TextView[] grid;

    public RowCol(TextView[] grid) {
        this.grid = grid;
    }

    // Returns 14 TextViews (7 horizontal + 7 vertical), owner excluded
    public TextView[] getRowCol(int id) {

        if (grid == null || grid.length < 65 || id < 1 || id > 64)
            return new TextView[0];

        int row = (id - 1) / 8;
        int col = (id - 1) % 8;

        TextView[] result = new TextView[14];
        int k = 0;

        // Horizontal (excluding owner)
        for (int i = 0; i < 8; i++) {
            int index = row * 8 + i + 1;
            if (index != id)
                result[k++] = grid[index];
        }

        // Vertical (excluding owner)
        for (int i = 0; i < 8; i++) {
            int index = col + i * 8 + 1;
            if (index != id)
                result[k++] = grid[index];
        }

        return result;
    }

    // Returns 7 TextViews in the same row, owner excluded
    public TextView[] getHorizontal(int id) {

        if (grid == null || grid.length < 65 || id < 1 || id > 64)
            return new TextView[0];

        int row = (id - 1) / 8;

        TextView[] result = new TextView[7];
        int k = 0;

        for (int i = 0; i < 8; i++) {
            int index = row * 8 + i + 1;
            if (index != id)
                result[k++] = grid[index];
        }

        return result;
    }

    // Returns 7 TextViews in the same column, owner excluded
    public TextView[] getVertical(int id) {

        if (grid == null || grid.length < 65 || id < 1 || id > 64)
            return new TextView[0];

        int col = (id - 1) % 8;

        TextView[] result = new TextView[7];
        int k = 0;

        for (int i = 0; i < 8; i++) {
            int index = col + i * 8 + 1;
            if (index != id)
                result[k++] = grid[index];
        }

        return result;
    }
}