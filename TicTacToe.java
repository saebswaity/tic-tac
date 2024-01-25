// TicTacToe.java

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TicTacToe {
    public char[][] board;
    private char currentPlayerSymbol;
    public TicTacToe(char currentPlayerSymbol) {
        this.currentPlayerSymbol = currentPlayerSymbol;
        board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
        //currentPlayer = 'X';

        // Initialize the board with empty cells
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    
    private int evaluateMove(char[][] board, int row, int col, char playerSymbol) {
        int scorec = 1;
        int scorer = 1;
        int scored1 = 1;
        int scored2 = 1;
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[row][i] == playerSymbol) {
                scorer *= 2;

            } else if (board[row][i] != ' ') {
                scorer *= -2;
            }
        }
    
        // Check columns
        for (int i = 0; i < 3; i++) {
            if (board[i][col] == playerSymbol) {
                scorec *= 2;
        }
     else if (board[i][col] != ' ') {
        scorec *= -2;
    }
    }


        for (int i = 0; i < 3; i++) {
            if (board[i][i] == playerSymbol) {
                scored1 *= 2;

            } else if (board[i][i] != ' ') {
                scored1 *= -2;
            }
        }


        for (int i = 0; i < 3; i++) {
            if (board[i][2-i] == playerSymbol) {
                scored2 *= 2;

            } else if (board[i][2-i] != ' ') {
                scored2 *= -2;
            }
        }
    
        // Check diagonals
        

    
        return Math.max(Math.max(Math.max(scorer, scorec), scored1),scored2);
    }




    public List<int[]> getEmptyIndices() {
    List<int[]> emptyIndices = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    emptyIndices.add(new int[]{i, j});
                }
            }
        }

        return emptyIndices;
    }


    public int[] getRandomMove() {
    List<int[]> emptyIndices = getEmptyIndices();

        if (!emptyIndices.isEmpty()) {
            Random random = new Random();
            return emptyIndices.get(random.nextInt(emptyIndices.size()));
        }

        // Return null or a special value if no empty indices are available
        return null;
}


public int[] getBestMove(char playerSymbol) {
    List<int[]> emptyIndices = getEmptyIndices();
    int bestScore = Integer.MIN_VALUE;
    int[] bestMove = null;

    for (int[] index : emptyIndices) {
        int row = index[0];
        int col = index[1];

        // Make a copy of the current board and simulate the move
        char[][] testBoard = cloneBoard();
        testBoard[row][col] = playerSymbol;

        // Evaluate the move using the heuristic function
        int moveScore = evaluateMove(testBoard, row, col, playerSymbol);

        // Print the evaluation for debugging purposes
        System.out.println("Move at (" + row + ", " + col + ") has score: " + moveScore);

        // If the evaluated score is better than the current best, update bestScore and bestMove
        if (moveScore > bestScore) {
            bestScore = moveScore;
            bestMove = index;
        }
    }

    return bestMove;
}



private char[][] cloneBoard() {
    char[][] clone = new char[board.length][];
    for (int i = 0; i < board.length; i++) {
        clone[i] = Arrays.copyOf(board[i], board[i].length);
    }
    return clone;
}


    

    public void setCurrentPlayerSymbol(char symbol){
        currentPlayerSymbol = symbol;
    }

    public void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]);
                if (j < 2) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if (i < 2) {
                System.out.println("---------");
            }
        }
        System.out.println();
    }

    public boolean validMove(int row, int col) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3 || board[row][col] != ' ') {
            return false; // Invalid move
        }
        return true;

    }

    public void makeMove(int row, int col) {


        board[row][col] = currentPlayerSymbol;
        //currentPlayer = (currentPlayer == 'X') ? 'O' : 'X'; // Switch player

    }

    public boolean isGameOver() {
        return isBoardFull() || checkForWinner();
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false; // Board is not full
                }
            }
        }
        return true; // Board is full
    }

    boolean checkForWinner() {
        // Check rows, columns, and diagonals for a winner
        return checkRows() || checkColumns() || checkDiagonals();
    }

    private boolean checkRows() {
        for (int i = 0; i < 3; i++) {
            if (checkThreeCells(board[i][0], board[i][1], board[i][2])) {
                return true; // Row has a winner
            }
        }
        return false;
    }

    private boolean checkColumns() {
        for (int j = 0; j < 3; j++) {
            if (checkThreeCells(board[0][j], board[1][j], board[2][j])) {
                return true; // Column has a winner
            }
        }
        return false;
    }

    private boolean checkDiagonals() {
        return checkThreeCells(board[0][0], board[1][1], board[2][2]) ||
               checkThreeCells(board[0][2], board[1][1], board[2][0]);
    }

    private boolean checkThreeCells(char cell1, char cell2, char cell3) {
        return cell1 != ' ' && cell1 == cell2 && cell2 == cell3;
    }


}
