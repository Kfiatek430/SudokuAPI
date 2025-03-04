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
  public SudokuBoard generateSudoku(Difficulty difficulty) {
    SudokuBoard board = new SudokuBoard();
    solveSudokuRecursive(board);
    removeNumbers(board, difficulty);
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

  public boolean isValidBoard(SudokuBoard board) {
    return hasValidStructure(board) && getInvalidCells(board).isEmpty();
  }

  public boolean hasValidStructure(SudokuBoard board) {
    int[][] grid = board.getBoard();
    if (grid.length != 9) return false;
    for (int[] row : grid) {
      if(row.length != 9) return false;
      for (int num : row) {
        if (num < 0 || num > 9) return false;
      }
    }
    return true;
  }

  public List<int[]> getInvalidCells(SudokuBoard board) {
    List<int[]> invalidCells = new ArrayList<>();
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        int num = board.getValue(i, j);
        if (num < 0 || num > 9) {
          invalidCells.add(new int[]{i, j});
          continue;
        }
        if (num != 0 && hasConflict(board, i, j, num)) {
          invalidCells.add(new int[]{i, j});
        }
      }
    }
    return invalidCells;
  }

  private boolean hasConflict(SudokuBoard board,  int row, int col, int num) {
    board.setValue(row, col, 0);
    boolean conflict = !isValidMove(board, row, col, num);
    board.setValue(row, col, num);
    return conflict;
  }

  public boolean isValidMove(SudokuBoard board, int row, int col, int num) {
    return !existsInRow(board, row, num) && !existsInColumn(board, col, num) && !existsInBox(board, row, col, num);
  }

  private boolean existsInRow(SudokuBoard board, int row, int num) {
    for (int c = 0; c < 9; c++) {
      if (board.getValue(row, c) == num) {
        return true;
      }
    }
    return false;
  }

  private boolean existsInColumn(SudokuBoard board, int col, int num) {
    for (int r = 0; r < 9; r++) {
      if (board.getValue(r, col) == num) {
        return true;
      }
    }
    return false;
  }

  private boolean existsInBox(SudokuBoard board, int row, int col, int num) {
    int boxRow = row - row % 3;
    int boxCol = col - col % 3;

    for (int r = boxRow; r < boxRow + 3; r++) {
      for (int c = boxCol; c < boxCol + 3; c++) {
        if (board.getValue(r, c) == num) {
          return true;
        }
      }
    }
    return false;
  }

  private List<Integer> generateShuffledNumbers() {
    List<Integer> numbers = new ArrayList<>(IntStream.rangeClosed(1, 9).boxed().toList());
    Collections.shuffle(numbers);
    return numbers;
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
