def is_valid(board, row, col):
    for i in range(row):
        if board[i] == col or \
           board[i] - i == col - row or \
           board[i] + i == col + row:
            return False
    return True

def forward_check(board, row, n, assigned_cols):
    for i in range(row + 1, n):
        assigned_col = assigned_cols[i]
        if assigned_col is not None:
            col_diff = abs(board[row] - assigned_col)
            if col_diff == 0 or col_diff == (row - i):
                return False
    return True

def print_solution(board):
    n = len(board)
    for row in range(n):
        line = ""
        for col in range(n):
            if board[row] == col:
                line += "Q "
            else:
                line += "- "
        print(line.strip())
    print('+'*((n*2)-1))
        
def solve_n_queens(board, row, n, assigned_cols):
    if row == n:
        
        print_solution(board)
        return

    for col in range(n):
        if is_valid(board, row, col):
            # Place queen
            board[row] = col
            assigned_cols[row] = col

            # Check forward for consistency
            if forward_check(board, row, n, assigned_cols):
                # Recur to place queens in the next rows
                solve_n_queens(board, row + 1, n, assigned_cols)

            # Backtrack
            board[row] = -1
            assigned_cols[row] = None

if __name__ == "__main__":
    n = 4
    board = [-1] * n
    assigned_cols = [None] * n

    solve_n_queens(board, 0, n, assigned_cols)
