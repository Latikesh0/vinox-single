# Project Rules & Instructions for AI Assistant

## Overview
This project contains Android application variants developed for different client requirements (e.g., `VINOXX`, `ACE`, and `DETECTIVE`).

## Branching & Versioning Strategy
- **`main` / `vinoxx` Branch**:
  - App Name: `VINOXX`
  - Base app icon and default styling.
- **`ace` Branch**:
  - App Name: `ACE`
  - ACE app icon and customized layouts.
  - Special backspace field-by-field navigation (36 -> 35 -> ... -> 1).
  - TRK Axis Scan filters for **Max (1st)** winning family only in debug result text and output grid.
- **`DETECTIVE` Branch**:
  - App Name: `DETECTIVE`
  - Package Name / Application ID: `laki.detective.v1`
  - **Selective Push Rule**: When pushing to the `DETECTIVE` branch, commit and push ONLY the core logic, adapter, custom views, and popup resource files (`MainActivity.java`, `InputGridAdapter.java`, `OutputGridAdapter.java`, `BackspaceEditText.java`, `ic_trick.xml`, `popup_input_options.xml`, `popup_option_bg.xml`). Do not include extraneous build/temp or unrelated files.

## Coding Standards & Behavior Rules
1. **Input Fields & Navigation**:
   - `inputGridView`: 36 active input cells starting at index 12.
   - Backspace key on soft keyboard clears current field text and immediately moves focus to previous field (`position - 1`) step-by-step.
   - `extraInputs[0..5]` (Special Inputs): 6 boxes with normal border styling (`@drawable/cell_border_output`), auto-advance on 2 digits, and backspace moves focus to `index - 1`.

2. **Long-Press Context Menu Options**:
   - Long-pressing on any input cell (`etInputCell` / `inputGridView`) or special input box (`etExtra1`..`etExtra6`) opens a dark popup menu (`popup_input_options.xml`).
   - Menu actions:
     - 📋 **Select & Copy All Inputs**: Collects digits from all 36 main grid cells and 6 special input boxes (12 digits) into system clipboard.
     - 📥 **Paste Inputs**: Fills 36 main grid cells and remaining digits into 6 special input boxes. Directly populates special inputs if long-pressed on a special input box with <= 12 digits.
     - 🧹 **Clear All Inputs**: Clears all 36 main grid cells and 6 special input boxes.

3. **TRK Axis Family Scan Logic (`executeOwnerAxisFamilyScan`)**:
   - Group grid cells by matching special input families without early `break`, allowing cells to belong to multiple input owner lists if families overlap.
   - Sort winning families by 14-neighbor scan count descending.
   - Display **1st Max** family in debug text (`resultText`).
   - Keep ONLY **1st Max** family cells in output grid (`outputData`), clearing all other non-top family cells.

4. **UI & Layout Guidelines**:
   - All 6 special input boxes (`etExtra1` .. `etExtra6`) use normal border background (`@drawable/cell_border_output`).
