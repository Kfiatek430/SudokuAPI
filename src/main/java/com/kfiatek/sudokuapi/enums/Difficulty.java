package com.kfiatek.sudokuapi.enums;

public enum Difficulty {
  EASY, MEDIUM, HARD;

  public static Difficulty fromString(String value) {
    try {
      return Difficulty.valueOf(value.toUpperCase());
    } catch (IllegalArgumentException e) {
      return MEDIUM;
    }
  }
}
