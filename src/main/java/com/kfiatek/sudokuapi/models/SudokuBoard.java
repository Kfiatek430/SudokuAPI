package com.kfiatek.sudokuapi.models;

public class SudokuBoard {
  private int[][] board;

  public SudokuBoard() {
    this.board = new int[9][9];
  }

  public int[][] getBoard() {
    return board;
  }

  public void setBoard(int[][] board) {
    this.board = board;
  }

  public int getValue(int row, int col) {
    return board[row][col];
  }

  public void setValue(int row, int col, int value) {
    board[row][col] = value;
  }
}
