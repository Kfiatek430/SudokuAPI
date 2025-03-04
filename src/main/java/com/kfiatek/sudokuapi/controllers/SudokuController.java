package com.kfiatek.sudokuapi.controllers;

import com.kfiatek.sudokuapi.enums.Difficulty;
import com.kfiatek.sudokuapi.models.SudokuBoard;
import com.kfiatek.sudokuapi.models.SudokuGenerateResponse;
import com.kfiatek.sudokuapi.models.SudokuSolveResponse;
import com.kfiatek.sudokuapi.models.SudokuValidateResponse;
import com.kfiatek.sudokuapi.services.SudokuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
  @PostMapping("/solve")
  public ResponseEntity<SudokuSolveResponse> solveSudoku(@RequestBody SudokuBoard board) {
    SudokuBoard solvedBoard = sudokuService.solveSudoku(board);
    if(solvedBoard == null) {
      return ResponseEntity.badRequest().body(new SudokuSolveResponse(null, "Board contains errors"));
    }
    return ResponseEntity.ok(new SudokuSolveResponse(solvedBoard.getBoard(), "Solved successfully"));
  }

  @PostMapping("/validate")
  public ResponseEntity<SudokuValidateResponse> validateSudoku(@RequestBody SudokuBoard board) {
    List<int[]> invalidCells = sudokuService.getInvalidCells(board);

    if(invalidCells.isEmpty()) {
      return ResponseEntity.ok(new SudokuValidateResponse(true, "Board is valid", invalidCells));
    } else {
      return ResponseEntity.ok(new SudokuValidateResponse(false, "Board contains errors", invalidCells));
    }
  }
}
