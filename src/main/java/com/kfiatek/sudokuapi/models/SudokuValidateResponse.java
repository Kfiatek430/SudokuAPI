package com.kfiatek.sudokuapi.models;

import java.util.List;

public record SudokuValidateResponse(boolean isValid, String message, List<int[]> invalidCells) {
}
