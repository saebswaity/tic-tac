import java.util.HashSet;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class TicTacToeApplication extends Application {

    private Game game;
    int mode;
    private GridPane gameGrid ;
    private GridPane infoGrid;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Game Settings");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        
        Label roundLabel = new Label("Enter Round Number:");
        TextField roundTextField = new TextField();

        Label player1Label = new Label("Enter Player 1 Name:");
        TextField player1TextField = new TextField();

        Label player2Label = new Label("Enter Player 2 Name:");
        TextField player2TextField = new TextField();



            // Create a list of items
        ObservableList<String> items1 = FXCollections.observableArrayList(
                "Dumb","Draw!!");

        // Create a ListView
        ListView<String> listView1 = new ListView<>(items1);
        listView1.setVisible(false);


                // Create a button to toggle the visibility of the ListView
                Button toggleButton1 = new Button("Computer X Computer");

                // Set an action when the button is clicked
                toggleButton1.setOnAction(event -> {
                    if (!listView1.isVisible()) {
                        listView1.setVisible(true);
                    } else {
                        listView1.setVisible(false);
                    }
                });

        // Set an action when an item is selected
        listView1.setOnMouseClicked(event -> {
            String selectedItem = listView1.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                switch (selectedItem) {
                    case "Dumb":
                    handleRandomPlaying(
                        roundTextField.getText(),
                        player1TextField.getText(),
                        player2TextField.getText(),
                        primaryStage
                );
                        // Action for Item 1
                        System.out.println("Action for Item 1");
                        break;
                    case "Draw!!":
                    handleMiniMaxPlaying(
                        roundTextField.getText(),
                        player1TextField.getText(),
                        player2TextField.getText(),
                        primaryStage
                );
                        System.out.println("Action for Item 2");
                        break;
                    // Add cases for other items as needed
                    default:
                        // Default action
                        System.out.println("Default action for: " + selectedItem);
                        break;
                }
            }

    });
        // Create a list of items
        ObservableList<String> items = FXCollections.observableArrayList(
                "easy","Moderate","IMPOSIBLE!!!");

        // Create a ListView
        ListView<String> listView = new ListView<>(items);
        listView.setVisible(false);


                // Create a button to toggle the visibility of the ListView
                Button toggleButton = new Button("Against Computer");

                // Set an action when the button is clicked
                toggleButton.setOnAction(event -> {
                    if (!listView.isVisible()) {
                        listView.setVisible(true);
                    } else {
                        listView.setVisible(false);
                    }
                });

        // Set an action when an item is selected
        listView.setOnMouseClicked(event -> {
            String selectedItem = listView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                switch (selectedItem) {
                    case "easy":
                    handleAgainstComputer(
                        roundTextField.getText(),
                        player1TextField.getText(),
                        player2TextField.getText(),
                        primaryStage,3
                );
                    case "Moderate":
                    handleAgainstComputer(
                        roundTextField.getText(),
                        player1TextField.getText(),
                        player2TextField.getText(),
                        primaryStage,4
                );
                        // Action for Item 1
                        System.out.println("Action for Item 1");
                        break;
                    case "IMPOSIBLE!!!":
                    handleAgainstComputer(
                        roundTextField.getText(),
                        player1TextField.getText(),
                        player2TextField.getText(),
                        primaryStage,2
                );
                        System.out.println("Action for Item 2");
                        break;
                    // Add cases for other items as needed
                    default:
                        // Default action
                        System.out.println("Default action for: " + selectedItem);
                        break;
                }
            }

    });
        // Add a button to submit the settings
        Button submitButton = new Button("Against friend");
        submitButton.setOnAction(e -> 
        handleAgainstFriend(
                roundTextField.getText(),
                player1TextField.getText(),
                player2TextField.getText(),
                primaryStage
        ));


// Assuming grid is a GridPane

// First column
grid.add(roundLabel, 0, 0);
grid.add(player1Label, 0, 1);
grid.add(player2Label, 0, 2);

// Second column
grid.add(roundTextField, 1, 0);
grid.add(player1TextField, 1, 1);
grid.add(player2TextField, 1, 2);

// Spanning two columns
grid.add(submitButton, 0, 3, 2, 1);
grid.add(listView, 0, 4, 2, 1);
grid.add(toggleButton, 0, 5, 2, 1);

// Another set of components
grid.add(listView1, 2, 4, 2, 1);
grid.add(toggleButton1, 2, 5, 2, 1);





        Scene scene = new Scene(grid, 500, 300);

        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();



    }

    

    private void setupGameAndShowStage(String roundNumber, String player1Name, String player2Name, Stage stage, int mode) {
        // Create a new Game instance with the entered settings
        int rounds = Integer.parseInt(roundNumber);
        game = new Game(rounds, player1Name, player2Name);
        this.mode = mode;
    
        stage.close();
    
        // Create a new stage for the main grid
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Tic Tac Toe");
    
        // Create a new main grid with the updated game settings
        GridPane mainGrid = new GridPane();
        gameGrid = createGrid();
        infoGrid = createInfoGrid();
    
        Button playButton = new Button("Play");
    
        if (mode == 1) {
            playButton.setOnAction(e -> handlePlayButtonClick());
        } else if (mode == 3) {
            playButton.setOnAction(e -> handleMiniMaxPlayButtonClick());
        }
    
        infoGrid.add(playButton, 1, 3);
    
        mainGrid.add(gameGrid, 0, 0);
        mainGrid.add(infoGrid, 1, 0);
    
        Scene scene = new Scene(mainGrid, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void handleAgainstFriend(String roundNumber, String player1Name, String player2Name, Stage stage) {
        setupGameAndShowStage(roundNumber, player1Name, player2Name, stage, 0);
    }
    
    private void handleMiniMaxPlaying(String roundNumber, String player1Name, String player2Name, Stage stage) {
        setupGameAndShowStage(roundNumber, player1Name, player2Name, stage, 3);
    }
    
    private void handleAgainstComputer(String roundNumber, String player1Name, String player2Name, Stage stage, int mode) {
        setupGameAndShowStage(roundNumber, player1Name, player2Name, stage, mode);
    }
    
    private void handleRandomPlaying(String roundNumber, String player1Name, String player2Name, Stage stage) {
        setupGameAndShowStage(roundNumber, player1Name, player2Name, stage, 1);
    }

    private GridPane createInfoGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        javafx.scene.text.Text roundText = new javafx.scene.text.Text("Round: ");
        javafx.scene.text.Text player1WinsText = new javafx.scene.text.Text(game.player1.getName() 
        + " Wins: ");
        javafx.scene.text.Text player2WinsText = new javafx.scene.text.Text(game.player2.getName() + "Wins: ");

        javafx.scene.text.Text roundNumber = new javafx.scene.text.Text("1");
        javafx.scene.text.Text player1Wins = new javafx.scene.text.Text("0");
        javafx.scene.text.Text player2Wins = new javafx.scene.text.Text("0");

        grid.addRow(0, roundText, roundNumber);
        grid.addRow(1, player1WinsText, player1Wins);
        grid.addRow(2, player2WinsText, player2Wins);



        return grid;
    }
    

    private void handleMiniMaxPlayButtonClick() {
        handleAIMove(getAINextMove(game));
    }
    
    private void handlePlayButtonClick() {
            handleAIMove(game.ticTacToe.getRandomMove());
    }
    
    private void handleAIMove(int[] move) {
        if (move != null) {
            int row = move[0];
            int col = move[1];
    
            drawMove(row, col);
    
            if (setMove(row, col)) {
                endRound(row, col);
                game.setCurrentPlayer();
            } else {
                game.setCurrentPlayer();
            }
        }
    }
    
    

    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Rectangle cell = createCell(row, col);
                grid.add(cell, col, row);
            }
        }

        return grid;
    }



    private Rectangle createCell(int row, int col) {
        Rectangle cell = new Rectangle(90, 90);
        cell.setFill(Color.WHITE);
        cell.setStroke(Color.BLACK);

        switch(mode) {
            case 0:
            cell.setOnMouseClicked(e -> handleMouseClick(cell, row, col));
            break;
            case 1:
            return cell;
            case 2:
 
                cell.setOnMouseClicked(e -> handleMouseClick(cell, row, col));
            break;
            case 3:
            return cell;
            case 4:
 
            cell.setOnMouseClicked(e -> handleMouseClick(cell,row, col));
            break;
              // code block
          }


        return cell;
    }

    

    private boolean isAIPlaying() {
        return mode == 2 || mode == 3 || mode == 4;
    }
    
    private void handleMouseClick(Rectangle cell,int row, int col) {
        cell.setMouseTransparent(true);
        if (setMove(row, col)) {
            endRound(row, col);
        } else {
            drawMoveAndSetPlayer(row, col);
    
            if (isAIPlaying()) {
                handleAIMove();
            }
        }
    }
    
    private void handleAIMove() {
        int[] nextMove;
    
        switch (mode) {
            case 2:
                nextMove = getAINextMove(game);
                break;
            case 3:
                nextMove = game.ticTacToe.getRandomMove();
                break;
            case 4:
            nextMove = game.ticTacToe.getBestMove(game.getCurrentPlayerSymbol());
            break;
            default:
                throw new IllegalArgumentException("Unsupported mode: " + mode);
        }
    
        if (setMove(nextMove[0], nextMove[1])) {
            endRound(nextMove[0], nextMove[1]);
            game.setCurrentPlayer(); // User always starts playing
        } else {
            drawMoveAndSetPlayer(nextMove[0], nextMove[1]);
        }
    }
    private void drawMoveAndSetPlayer(int row, int col) {
        drawMove(row, col);
        game.setCurrentPlayer();
    }    

    public static int[] getAINextMove(Game game) {
        char symbol = game.getCurrentPlayerSymbol();
        int val = (symbol == 'O')? -1: 1;
        TicTacToeTree.Node newNode = new TicTacToeTree.Node(game.ticTacToe.board, null, symbol, 0, new HashSet<>(), new int[2]);
        TicTacToeTree.buildTreeX(newNode, symbol);
        System.out.println(newNode.children.size());
        newNode = TicTacToeTree.selectNextNode(newNode,val);
        
        int row = newNode.index[0];
        int col = newNode.index[1];
        
        return new int[]{row, col};
    }

    private void endRound(int row, int col) {
        drawMove(row, col);
        showResult(false);
        game.setCurrentPlayer();
        resetGame();
    }

    private boolean setMove(int row, int col) {
        game.makeMove(row, col);
        if(game.isGameOver()){

            return true;
        }

        return false;
    }

    private void resetGame() {
        clearGrid(gameGrid);
        if (game.getCurrentRound().getRoundNumber() == game.getRoundCounts()){
           System.out.println("End th GAME ");
           /// close the  grigd 

        // Close the game window
        Stage stage = (Stage) gameGrid.getScene().getWindow();
        stage.close();
        }
        else{
            game.startNewRound();
            updateInfoGrid();       
        }

    }

    
    private void updateInfoGrid() {
        // Update round number
        javafx.scene.text.Text roundNumberText = (javafx.scene.text.Text) infoGrid.getChildren().get(1);
        roundNumberText.setText(Integer.toString(game.getCurrentRound().getRoundNumber()));

        // Update Player 1 wins
        javafx.scene.text.Text player1WinsText = (javafx.scene.text.Text) infoGrid.getChildren().get(3);
        player1WinsText.setText(Integer.toString(game.getPlayer1().getWinsCount()));

        // Update Player 2 wins
        javafx.scene.text.Text player2WinsText = (javafx.scene.text.Text) infoGrid.getChildren().get(5);
        player2WinsText.setText(Integer.toString(game.getPlayer2().getWinsCount()));
    }



    private void clearGrid(GridPane grid) {
        grid.getChildren().clear();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Rectangle cell = createCell(row, col);
                grid.add(cell, col, row);
            }
        }
    }

    private void drawMove(int row, int col) {
        char currentPlayerSymbol = game.getCurrentPlayerSymbol();
        String symbol = (currentPlayerSymbol == 'X') ? "X" : "O";

        Rectangle cell = (Rectangle) gameGrid.getChildren().get(row * 3 + col);

        cell.setFill(Color.LIGHTGRAY);

        javafx.scene.text.Text text = new javafx.scene.text.Text(symbol);
        text.setStyle("-fx-font-size: 40;");

        gameGrid.add(text, col, row);
    }





    private void showResult(boolean minimax) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Game Over");
    
        if (game.getRoundCounts() == game.getCurrentRound().roundNumber) {
            showOverallWinnerResult(alert);
        } else {
            showRoundResult(alert);
        }
    
        alert.showAndWait();
    }
    
    private void showOverallWinnerResult(Alert alert) {
        int player1Wins = game.getPlayer1().getWinsCount();
        int player2Wins = game.getPlayer2().getWinsCount();
    
        String winnerName;
        if (player1Wins > player2Wins) {
            winnerName = game.getPlayer1().getName();
        } else if (player1Wins < player2Wins) {
            winnerName = game.getPlayer2().getName();
        } else {
            alert.setHeaderText("It's a tie! No overall winner. Rounds played: " + game.getRoundCounts() +
                                "\nPlayer " + game.getPlayer1().getName() + " wins: " + player1Wins +
                                "\nPlayer " + game.getPlayer2().getName() + " wins: " + player2Wins);
            return;
        }
    
        alert.setHeaderText("Player " + winnerName + " is the overall winner! Rounds played: " + game.getRoundCounts() +
                            "\nPlayer " + game.getPlayer1().getName() + " wins: " + player1Wins +
                            "\nPlayer " + game.getPlayer2().getName() + " wins: " + player2Wins);
    }
    
    
    private void showRoundResult(Alert alert) {
        if (game.checkForWinner()) {
            alert.setHeaderText("Player " + game.getCurrentPlayer().getName() + " wins Round " + game.getCurrentRound().getRoundNumber() + "!");
        } else {
            alert.setHeaderText("It's a draw in Round " + game.getCurrentRound().getRoundNumber() + "!");
        }
    }
    
    

    // Inner classes
    private class Round {
        private Player player1;
        private Player player2;
        private int roundNumber;
        private Player winner;

        public Round(int roundNumber,Player player1,Player player2) {
            this.player1 = player1;
            this.player2 = player2;
            this.roundNumber = roundNumber;
        }

        public int getRoundNumber() {
            return roundNumber;
        }

        public Player getWinner() {
            return winner;
        }

        public void setWinner(Player winner) {
            this.winner = winner;
            winner.setWinsCount();

        }
    }

    private class Player {
        private String name;
        private char symbol;
        private int winsCount;

        public Player(String name, char symbol) {
            this.name = name;
            this.symbol = symbol;
            this.winsCount = 0;
        }

        public String getName() {
            return name;
        }

        public char getSymbol() {
            return symbol;
        }

        public void setWinsCount(){
             winsCount++;
        }

        public int getWinsCount(){
            return winsCount;
        }
    }

    private class Game {
        private TicTacToe ticTacToe;
        private Round currentRound;
        private Player currentPlayer;
        private Player player1;
        private Player player2;
        public int roundCounts;
    
        public Game(int roundCounts,String playerName1,String playerName2) {
            this.roundCounts = roundCounts;
            player1 = new Player(playerName1, 'X');
            player2 = new Player(playerName2, 'O');
            currentPlayer = player1;
            ticTacToe = new TicTacToe(currentPlayer.getSymbol());
            currentRound = new Round(1,player1,player2);
        }

        public int getRoundCounts(){
            return roundCounts;
        }

        public void setCurrentPlayer(){
            currentPlayer = (currentPlayer.getSymbol() == player1.getSymbol()) ? player2 : player1;
            ticTacToe.setCurrentPlayerSymbol(currentPlayer.getSymbol());

        }

        public void startNewRound() {
            currentPlayer = (currentPlayer.getSymbol() == player1.getSymbol()) ? player2 : player1;
            ticTacToe = new TicTacToe(currentPlayer.getSymbol());
            currentRound = new Round(currentRound.getRoundNumber() + 1,player1,player2);

        }

        public boolean makeMove(int row, int col) {
            if (ticTacToe.validMove(row, col)) {
                ticTacToe.makeMove(row,col);
                if (ticTacToe.isGameOver()) {
                    if (game.checkForWinner()) {
                        game.getCurrentRound().setWinner(currentPlayer);
                    }
                }
                return true;
            }
            return false;
        }

        public boolean checkForWinner() {
            return ticTacToe.checkForWinner();
        }

        public boolean isGameOver() {
            return ticTacToe.isGameOver();
        }

        public Player getCurrentPlayer() {
            return currentPlayer;
        }

        public char getCurrentPlayerSymbol() {
            return currentPlayer.getSymbol();
        }

        public Round getCurrentRound() {
            return currentRound;
        }

        public Player getPlayer1(){
            return player1;
        }

        public Player getPlayer2(){
            return player2;
        }


    }
}
