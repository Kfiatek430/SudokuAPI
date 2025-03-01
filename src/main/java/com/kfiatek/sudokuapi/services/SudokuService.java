package com.kfiatek.sudokuapi.services;

import com.kfiatek.sudokuapi.models.SudokuBoard;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class SudokuService {
  public SudokuBoard generateSudoku() {
    SudokuBoard board = new SudokuBoard();
    solveSudoku(board);

    return board;
  }

  public boolean solveSudoku(SudokuBoard board) {
    for(int i = 0; i < 9; i++) {
      for(int j = 0; j < 9; j++) {
        if(board.getValue(i, j) == 0) {
          List<Integer> numbers = generateShuffledNumbers();

          for(int num : numbers) {
            if(isValidMove(board, i, j, num)) {
              board.setValue(i, j, num);

              if(solveSudoku(board)) {
                return true;
              }

              board.setValue(i, j, 0);
            }
          }

          return false;
        }
      }
    }

    return true;
  }

  private List<Integer> generateShuffledNumbers() {
    List<Integer> numbers = new ArrayList<>(IntStream.rangeClosed(1, 9).boxed().toList());
    Collections.shuffle(numbers);
    return numbers;
  }

  public boolean isValidMove(SudokuBoard board, int row, int col, int num) {
    for (int i = 0; i < 9; i++) {
      if(board.getValue(row, i) == num) {
        return false;
      }
    }

    for (int i = 0; i < 9; i++) {
      if(board.getValue(i, col) == num) {
        return false;
      }
    }

    int boxRowStart = (row / 3) * 3;
    int boxColStart = (col / 3) * 3;

    for(int i = boxRowStart; i < boxRowStart + 3; i++) {
      for(int j = boxColStart; j < boxColStart + 3; j++) {
        if(board.getValue(i, j) == num) {
          return false;
        }
      }
    }

    return true;
  }
}
