package com.kfiatek.sudokuapi.services;

import com.kfiatek.sudokuapi.enums.Difficulty;
import com.kfiatek.sudokuapi.models.SudokuBoard;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Service
public class SudokuService {
  public SudokuBoard generateSudoku(String difficulty) {
    Difficulty level = Difficulty.fromString(difficulty);
    SudokuBoard board = new SudokuBoard();
    solveSudoku(board);
    removeNumbers(board, level);
    return board;
  }

  public SudokuBoard solveSudoku(SudokuBoard board) {
    return isValidBoard(board) && solveSudokuRecursive(board) ? board : null;
  }

  public boolean solveSudokuRecursive(SudokuBoard board) {
    for(int i = 0; i < 9; i++) {
      for(int j = 0; j < 9; j++) {
        if(board.getValue(i, j) == 0) {
          for(int num : generateShuffledNumbers()) {
            if(isValidMove(board, i, j, num)) {
              board.setValue(i, j, num);
              if(solveSudokuRecursive(board)) {
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

  public void removeNumbers(SudokuBoard board, Difficulty level) {
    int toRemove = switch (level) {
      case EASY -> 30;
      case MEDIUM -> 40;
      case HARD -> 50;
    };

    Random random = new Random();
    while (toRemove > 0) {
      int row = random.nextInt(9);
      int col = random.nextInt(9);

      if(board.getValue(row, col) != 0) {
        board.setValue(row, col, 0);
        toRemove--;
      }
    }
  }
}
