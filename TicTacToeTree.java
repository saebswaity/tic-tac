import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class TicTacToeTree {
    public static Set<Node> finalNodes;

    public static class Node {
        public int value;
        public char[][] board;
        public Node parent;
        public char player;
        public int[] index;
        public Set<Node> children;
        public Node(char[][] board, Node parent, char player, int value, Set<Node> children, int[] index) {
            this.board = cloneBoard(board);
            this.parent = parent;
            this.player = player;
            this.value = value;
            this.index = index;
            this.children = children;
        }
    }

    public static void buildTreeX(Node node, char currentPlayer) {
        if (isGameOver(node.board)) {
            if (isBoardFull(node.board)) {
                boolean xv = checkForWinner(node.board,'X') ;
                boolean ov = checkForWinner(node.board,'O') ;
                node.value = xv? 1: 0;
                node.value = ov? -1: node.value ; 
            }
            else {
                node.value = checkForWinner(node.board,'X') ? 1 : -1;
            }
            fillvalue(node);
            //finalNodes.add(node);
            return;
        }

        // Game is not over, generate children
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (node.board[i][j] == ' ') {
                    // Empty cell, create a new child node
                    char[][] childBoard = cloneBoard(node.board);
                    childBoard[i][j] = currentPlayer;
                    int[] index = new int[2];
                    index[0] = i; index[1] = j;
                    Node childNode = new Node(childBoard, node, currentPlayer, 0, new HashSet<>(),index);
                    node.children.add(childNode);

                    // Toggle player for the next move
                    char nextPlayer = (currentPlayer == 'X') ? 'O' : 'X';

                    // Recursively build the tree for the child node
                    buildTreeX(childNode, nextPlayer);
                }
            }
        }
    }

    private static void fillvalue(TicTacToeTree.Node node) {
        if (node.parent == null) {
            return;
        }

    
        if (node.parent.player == 'O') {
            int maxValue = Integer.MIN_VALUE;
            for (TicTacToeTree.Node child : node.parent.children) {
                maxValue = Math.max(maxValue, child.value);
            }
            node.parent.value = maxValue;

        } else if (node.parent.player == 'X') {
            int minValue = Integer.MAX_VALUE;
            for (TicTacToeTree.Node child : node.parent.children) {
                minValue = Math.min(minValue, child.value);
            }
            node.parent.value = minValue;

        }
    
        fillvalue(node.parent);
    }

    private static char[][] cloneBoard(char[][] board) {
        char[][] clone = new char[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(board[i], 0, clone[i], 0, 3);
        }
        return clone;
    }

    public static int evaluateBoard(char[][] board) {
        if (checkForWinner(board, 'X')) {
            return 1; // 'X' wins
        } else if (checkForWinner(board, 'O')) {
            return -1; // 'O' wins
        } else {
            return 0; // Draw
        }
    }

    public static boolean isGameOver(char[][] board) {
        return isBoardFull(board) || checkForWinner(board, 'X') || checkForWinner(board, 'O');
    }

    private static boolean isBoardFull(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false; // Game is not over, there is an empty cell
                }
            }
        }
        return true;
    }

    private static boolean checkForWinner(char[][] board, char player) {
        return checkRows(board, player) || checkColumns(board, player) || checkDiagonals(board, player);
    }

    private static boolean checkRows(char[][] board, char player) {
        for (int i = 0; i < 3; i++) {
            if (checkThreeCells(board[i][0], board[i][1], board[i][2], player)) {
                return true; // Row has a winner
            }
        }
        return false;
    }

    private static boolean checkColumns(char[][] board, char player) {
        for (int j = 0; j < 3; j++) {
            if (checkThreeCells(board[0][j], board[1][j], board[2][j], player)) {
                return true; // Column has a winner
            }
        }
        return false;
    }

    private static boolean checkDiagonals(char[][] board, char player) {
        return checkThreeCells(board[0][0], board[1][1], board[2][2], player) ||
                checkThreeCells(board[0][2], board[1][1], board[2][0], player);
    }

    private static boolean checkThreeCells(char cell1, char cell2, char cell3, char player) {
        return cell1 == player && cell2 == player && cell3 == player;
    }

     public static TicTacToeTree.Node selectChildWithSameValue(TicTacToeTree.Node node) {
        List<TicTacToeTree.Node> nodesWithSameValue = new ArrayList<>();

        for (TicTacToeTree.Node child : node.children) {
            if (child.value == 0) {
                nodesWithSameValue.add(child);
                
            }
        }

        if (!nodesWithSameValue.isEmpty()) {
            // Randomly select one of the nodes with the same value
            Random random = new Random();
            return nodesWithSameValue.get(random.nextInt(nodesWithSameValue.size()));
        }

        

        return null;
    }

    public static TicTacToeTree.Node selectNextNode(TicTacToeTree.Node node,int val) {
        List<TicTacToeTree.Node> nodesWithSameValue = new ArrayList<>();
        List<TicTacToeTree.Node> drawValue = new ArrayList<>();

        for (TicTacToeTree.Node child : node.children) {
            System.out.println("Child: Row: " + child.index[0] + ", Col: " + child.index[1] + ", Evaluation: " + child.value);


            if (child.value == val) {
                nodesWithSameValue.add(child);

                
            }
            else if(child.value == 0) {
                drawValue.add(child);
                
            }

        }
        Node selectedNode;

        if (!nodesWithSameValue.isEmpty()) {
            // Randomly select one of the nodes with the same value
            Random random = new Random();
            selectedNode  = nodesWithSameValue.get(random.nextInt(nodesWithSameValue.size()));
            System.out.println("Selected Node: Row: " + selectedNode.index[0] + ", Col: " + selectedNode.index[1] + ", Evaluation: " + selectedNode.value);
            return selectedNode;
        }
        else         if (!drawValue.isEmpty()) {
            // Randomly select one of the nodes with the same value
            Random random = new Random();
             selectedNode  = drawValue.get(random.nextInt(drawValue.size()));
             System.out.println("Selected Node: Row: " + selectedNode.index[0] + ", Col: " + selectedNode.index[1] + ", Evaluation: " + selectedNode.value);
            return selectedNode;
        }

        

        return null;
    }


    private static void printBoard(char[][] board) {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println("\n-------------");
        }
    }

    public static void printNodeRecursive(TicTacToeTree.Node node, int depth) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indent.append("  ");
        }

        System.out.println(indent + "Value: " + node.value);
        System.out.println(indent + "Board:");
        printBoard(node.board);

        // Select a child with the same value at this depth
        TicTacToeTree.Node selectedChild = selectChildWithSameValue(node);

        if (selectedChild != null) {
            System.out.println(indent + "Selected Child:");
            printNodeRecursive(selectedChild, depth + 1);
        } else {
            System.out.println(indent + "No child with the same value.");
        }
    }

    public static void main(String[] args) {
        char[][] initialBoard = {
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };

        TicTacToeTree.Node rootNode = new TicTacToeTree.Node(initialBoard, null, 'X', 0, new HashSet<>(),new int[2]);
        TicTacToeTree.buildTreeX(rootNode, 'X');

        // Assume it's player X's turn
        System.out.println("Root Node:");
        printNodeRecursive(rootNode, 0);
    }
}
