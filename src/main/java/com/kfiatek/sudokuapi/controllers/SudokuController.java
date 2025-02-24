package com.kfiatek.sudokuapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sudoku")
public class SudokuController {

  @GetMapping("/hello")
  public String sayHello() {
    return "Witaj w świecie sudoku!";
  }
}
