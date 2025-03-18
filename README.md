# SudokuAPI

## Overview
SudokuAPI is a simple RESTful API for generating, solving, and validating Sudoku puzzles. It provides endpoints to create new Sudoku boards, solve given puzzles, and validate board correctness.

## Features
- Generate Sudoku puzzles with different difficulty levels
- Solve a given Sudoku puzzle
- Validate a Sudoku board to check for correctness

## Endpoints
### Generate Sudoku Board

POST `/sudoku/generate`

**Request Body (Optional):**

```json
{
  "difficulty": "easy" | "medium" | "hard"
}
```

If no difficulty is specified, the default is "medium".

**Response:**

```json
{
  "board": [[...]],
  "difficulty": "medium"
}
```

### Solve Sudoku Puzzle

POST `/sudoku/solve`

**Request Body:**

```json
{
  "board": [[...]]
}
```

**Response:**

```json
{
  "board": [[...]],
  "message": "Solved successfully"
}
```

If the board contains errors, the response will be:

```json
{
  "board": null,
  "message": "Board contains errors"
}
```


### Validate Sudoku Board

POST `/sudoku/validate`

**Request Body:**

```json
{
  "board": [[...]]
}
```

**Response (Valid Board):**

```json
{
  "isValid": true,
  "message": "Board is valid",
  "invalidCells": []
}
```

**Response (Invalid Board):**

```json
{
  "isValid": false,
  "message": "Board contains errors",
  "invalidCells": [[row, col], [row, col], ...]
}
```

## Setup & Running

1. Clone the repository:
    ```bash
    git clone https://github.com/your-repo/sudokuapi.git
    ```

2. Navigate to the project directory:
    ```bash
    cd sudokuapi
    ```

3. Build and run the application:
    ```bash
    mvn spring-boot:run
    ```

The API will be available at http://localhost:9090/sudoku

## License

This project is licensed under the [MIT License](LICENSE.md).