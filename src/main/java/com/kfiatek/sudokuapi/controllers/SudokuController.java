package com.kfiatek.sudokuapi.controllers;

import com.kfiatek.sudokuapi.models.SudokuBoard;
import com.kfiatek.sudokuapi.services.SudokuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sudoku")
public class SudokuController {
  private final SudokuService sudokuService;

  @Autowired
  public SudokuController(SudokuService sudokuService) {
    this.sudokuService = sudokuService;
  }

  @GetMapping("/generate")
  public SudokuBoard generateSudoku(@RequestParam(defaultValue = "medium") String difficulty) {
    return sudokuService.generateSudoku(difficulty);
  }
}
