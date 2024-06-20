def is_valid(board, row, col, num):
    # Check if placing 'num' at board[row][col] is valid
    for i in range(9):
        if board[row][i] == num or board[i][col] == num or board[3 * (row // 3) + i // 3][3 * (col // 3) + i % 3] == num:
            return False
    return True

def ac3(board, queue):
    while queue:
        row, col = queue.pop(0)

        for i in range(1, 10):
            if is_valid(board, row, col, i):
                if board[row][col] == 0:
                    board[row][col] = i
                else:
                    continue

                queue.extend(get_empty_cells(board, row, col))

def get_empty_cells(board, row, col):
    empty_cells = []
    for i in range(9):
        if board[row][i] == 0:
            empty_cells.append((row, i))
        if board[i][col] == 0:
            empty_cells.append((i, col))
        if board[3 * (row // 3) + i // 3][3 * (col // 3) + i % 3] == 0:
            empty_cells.append((3 * (row // 3) + i // 3, 3 * (col // 3) + i % 3))
    return empty_cells

def solve_sudoku(board):
    queue = [(i, j) for i in range(9) for j in range(9) if board[i][j] == 0]

    ac3(board, queue)

if __name__ == "__main__":
    # Example Sudoku puzzle (0 represents an empty cell)
    sudoku_board = [
        [5, 3, 0, 0, 7, 0, 0, 0, 0],
        [6, 0, 0, 1, 9, 5, 0, 0, 0],
        [0, 9, 8, 0, 0, 0, 0, 6, 0],
        [8, 0, 0, 0, 6, 0, 0, 0, 3],
        [4, 0, 0, 8, 0, 3, 0, 0, 1],
        [7, 0, 0, 0, 2, 0, 0, 0, 6],
        [0, 6, 0, 0, 0, 0, 2, 8, 0],
        [0, 0, 0, 4, 1, 9, 0, 0, 5],
        [0, 0, 0, 0, 8, 0, 0, 7, 9]
    ]

    print("Sudoku Puzzle:")
    for row in sudoku_board:
        print(row)

    solve_sudoku(sudoku_board)

    print("\nSudoku Solution:")
    for row in sudoku_board:
        print(row)
