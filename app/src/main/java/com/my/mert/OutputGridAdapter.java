package com.my.mert;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import laki.vinoxsingle.v1.R;

public class OutputGridAdapter extends BaseAdapter {

    public static final int[] INPUT_COLORS = {
        0xFF00E5FF, // Input 1: Cyan
        0xFFFF6D00, // Input 2: Orange
        0xFF00FF00, // Input 3: Green
        0xFFFF4081, // Input 4: Pink
        0xFFFFFF00, // Input 5: Yellow
        0xFFD500F9  // Input 6: Purple
    };

    public static final int[] INPUT_FILL_COLORS = {
        0x5500E5FF, // Input 1 fill
        0x55FF6D00, // Input 2 fill
        0x5500FF00, // Input 3 fill
        0x55FF4081, // Input 4 fill
        0x55FFFF00, // Input 5 fill
        0x55D500F9  // Input 6 fill
    };

    private final List<String> outputValues;
    private final LayoutInflater inflater;
    private int cellWidth = -1;
    private int cellHeight = -1;
    private int selectedPosition = -1;
    private final Map<Integer, Integer> positionColorMap = new HashMap<>();

    public OutputGridAdapter(Context context, List<String> outputValues) {
        this.outputValues = outputValues;
        this.inflater = LayoutInflater.from(context);
    }

    public void setCellDimensions(int width, int height) {
        this.cellWidth = width;
        this.cellHeight = height;
    }

    public void setSelectedPosition(int position) {
        if (this.selectedPosition == position) {
            this.selectedPosition = -1; // Toggle off if clicked again
        } else {
            this.selectedPosition = position;
        }
        notifyDataSetChanged();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setPositionColorMap(Map<Integer, Integer> colorMap) {
        this.positionColorMap.clear();
        if (colorMap != null) {
            this.positionColorMap.putAll(colorMap);
        }
        notifyDataSetChanged();
    }

    public void clearHighlights() {
        this.positionColorMap.clear();
        notifyDataSetChanged();
    }

    public Map<Integer, Integer> getPositionColorMap() {
        return positionColorMap;
    }

    public static GradientDrawable createHighlightDrawable(int strokeColor, int fillColor) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(6f);
        drawable.setColor(fillColor);
        drawable.setStroke(4, strokeColor);
        return drawable;
    }

    public List<String> getOutputValues() {
        return outputValues;
    }

    @Override
    public int getCount() {
        return outputValues.size();
    }

    @Override
    public Object getItem(int position) {
        return outputValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView tvCell;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_output_cell, parent, false);
            holder = new ViewHolder();
            holder.tvCell = convertView.findViewById(R.id.tvOutputCell);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Set dimensions if specified
        if (cellWidth > 0 && cellHeight > 0) {
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(cellWidth, cellHeight);
            convertView.setLayoutParams(params);
        }

        // Bind calculated output text value to the TextView
        holder.tvCell.setText(outputValues.get(position));
        LakiActivity.span(holder.tvCell);

        Integer colorIndex = positionColorMap.get(position);

        // Highlight selected cell and its vertical, horizontal, and diagonal neighbours
        if (selectedPosition != -1) {
            int selectedRow = selectedPosition / 8;
            int selectedCol = selectedPosition % 8;
            int currentRow = position / 8;
            int currentCol = position % 8;

            boolean isHorizontalOrVertical = (currentRow == selectedRow || currentCol == selectedCol);
            boolean isLeftCross = ((currentRow - currentCol) == (selectedRow - selectedCol));
            boolean isRightCross = ((currentRow + currentCol) == (selectedRow + selectedCol));

            if (position == selectedPosition) {
                holder.tvCell.setBackgroundResource(R.drawable.cell_border_selected);
            } else if (isHorizontalOrVertical || isLeftCross || isRightCross) {
                holder.tvCell.setBackgroundResource(R.drawable.cell_border_axis);
            } else if (colorIndex != null && colorIndex >= 0 && colorIndex < INPUT_COLORS.length) {
                holder.tvCell.setBackground(createHighlightDrawable(INPUT_COLORS[colorIndex], INPUT_FILL_COLORS[colorIndex]));
            } else {
                holder.tvCell.setBackgroundResource(R.drawable.cell_border_output);
            }
        } else if (colorIndex != null && colorIndex >= 0 && colorIndex < INPUT_COLORS.length) {
            holder.tvCell.setBackground(createHighlightDrawable(INPUT_COLORS[colorIndex], INPUT_FILL_COLORS[colorIndex]));
        } else {
            holder.tvCell.setBackgroundResource(R.drawable.cell_border_output);
        }

        return convertView;
    }
}
