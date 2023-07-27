# Initialize the Tic Tac Toe board
board = [' ' for _ in range(9)]

def draw_board(board):
    print("-------------")
    for i in range(0, 9, 3):
        print(f"| {board[i]} | {board[i+1]} | {board[i+2]} |")
        print("-------------")

winning_combinations = [
    [0, 1, 2], [3, 4, 5], [6, 7, 8],  # Rows
    [0, 3, 6], [1, 4, 7], [2, 5, 8],  # Columns
    [0, 4, 8], [2, 4, 6]              # Diagonals
]

def evaluate(board):
    for combination in winning_combinations:
        if board[combination[0]] == board[combination[1]] == board[combination[2]]:
            if board[combination[0]] == 'X':
                return 1
            elif board[combination[0]] == 'O':
                return -1
    return 0

def minimax(board, depth, is_maximizing):
    score = evaluate(board)

    if score == 1:
        return score - depth
    if score == -1:
        return score + depth

    if ' ' not in board:
        return 0

    if is_maximizing:
        max_eval = float('-inf')
        for i in range(9):
            if board[i] == ' ':
                board[i] = 'X'
                eval = minimax(board, depth + 1, False)
                board[i] = ' '
                max_eval = max(max_eval, eval)
        return max_eval
    else:
        min_eval = float('inf')
        for i in range(9):
            if board[i] == ' ':
                board[i] = 'O'
                eval = minimax(board, depth + 1, True)
                board[i] = ' '
                min_eval = min(min_eval, eval)
        return min_eval

def get_best_move(board):
    best_move = -1
    best_eval = float('-inf')
    for i in range(9):
        if board[i] == ' ':
            board[i] = 'X'
            move_eval = minimax(board, 0, False)
            board[i] = ' '
            if move_eval > best_eval:
                best_eval = move_eval
                best_move = i
    return best_move

def is_board_full(board):
    return ' ' not in board

def play_game():
    while True:
        draw_board(board)
        if is_board_full(board):
            print("It's a draw!")
            break

        player_move = int(input("Enter your move (1-9): "))-1
        if board[player_move] == ' ':
            board[player_move] = 'O'

            if evaluate(board) == -1:
                draw_board(board)
                print("You win!")
                break

            ai_move = get_best_move(board)
            board[ai_move] = 'X'

            if evaluate(board) == 1:
                draw_board(board)
                print("AI wins!")
                break
        else:
            print("That position is already taken. Try again!")

if __name__ == "__main__":
    play_game()
