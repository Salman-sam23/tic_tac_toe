package com.example.tictactoegame;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class TicTacToePlay extends Application {
    // create an array of 2D for storing  a, b player values
     private Button buttons[][] = new Button[3][3];

    // Creating two players with scores as attachment to display
    private Label PlayerBresult, PlayerAresult;

    // To fill player a,b when button is clicked, we need to check whose turn it is
    private boolean PlayerAturn=true;

    private int Ascore=0,Bscore=0;


    private BorderPane createContent(){
        BorderPane root = new BorderPane();
        // BorderPane means ui looks like a canvas having top, bottom, side boxes used for tic tac toe game, or different
        // purposes according to needs


        // GAME BOARD

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        for (int i = 0; i <3; i++) {
            for (int j = 0; j < 3; j++) {
                // create a new button
                Button button = new Button();
                button.setPrefSize(200, 200);
                button.setStyle("-fx-font-size:56px;-fx-font-weight:bold;");
                // add a functionality , when button is clicked, then players mark is visible on button
                button.setOnAction(event -> ButtonClicked(button));

                buttons[i][j] = button;
                gridPane.add(button, j, i);
            }
        }

                // now this gridpane should be visible in our game board,
                // gameBoard of type BorderPane , so set this grid pane in center of border pane;
                root.setCenter(gridPane);
//        BorderPane.setAlignment(gridPane, Pos.CENTER);
                gridPane.setAlignment(Pos.CENTER);






        //TITLE FOR BOARD
        Label titleLabel = new Label("TIC TAC TOE");
        titleLabel.setStyle("-fx-font-size:60px;-fx-font-weight:bold;");
        root.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);






        //SCOREBOARD FOR UPDATING SCORE AS PER PLAY
        HBox ScoreCard = new HBox(20);
        ScoreCard.setAlignment(Pos.CENTER);
        PlayerAresult = new Label("Player-A : 0");
        PlayerAresult.setStyle("-fx-font-size:26px;-fx-font-weight:bold;");


        PlayerBresult = new Label("Player-B : 0");
        PlayerBresult.setStyle("-fx-font-size:26px;-fx-font-weight:bold;");

        ScoreCard.getChildren().addAll(PlayerAresult, PlayerBresult);
        root.setBottom(ScoreCard);
        BorderPane.setAlignment(ScoreCard,Pos.CENTER);



        return root;


    }
    private void ButtonClicked(Button button){
        if(button.getText().equals("")){
            //came to button if it is empty, then we can fill name of player a or b, according to turn;
            if(PlayerAturn){
                button.setText("A");
            }else{
                button.setText("B");
            }
            PlayerAturn=!PlayerAturn;
            // check for winner, if row or column or diagonal is occupied by same player
            CheckWinner();
        }
    }


    private void CheckWinner() {
        // rows
        for (int row = 0; row < 3; row++) {
            if   (buttons[row][0].getText().equals(buttons[row][1].getText())
                    &&buttons[row][1].getText().equals(buttons[row][2].getText()) && !buttons[row][0].getText().isEmpty()
                 ){
                   String winner=buttons[row][0].getText();
                   ShowWinnerMessage(winner);
                   UpdateScores(winner);
                   ResetBoard();
                   return;
            }
        }

        // check for columns
        for (int col = 0; col < 3; col++) {
            if   (buttons[0][col].getText().equals(buttons[1][col].getText())
                    &&buttons[1][col].getText().equals(buttons[2][col].getText()) && !buttons[0][col].getText().isEmpty()
            ){
                String winner=buttons[0][col].getText();
                ShowWinnerMessage(winner);
                UpdateScores(winner);
                ResetBoard();
                return;
            }
        }

        //diagonal check
         if(buttons[0][0].getText().equals(buttons[1][1].getText())
                 && buttons[1][1].getText().equals(buttons[2][2].getText()) && !buttons[0][0].getText().isEmpty()
             ){
               String winner=buttons[0][0].getText();
               ShowWinnerMessage(winner);
               UpdateScores(winner);
               ResetBoard();

         } else if (buttons[0][2].getText().equals(buttons[1][1].getText())
                 && buttons[1][1].getText().equals(buttons[2][0].getText()) && !buttons[1][1].getText().isEmpty()
         ) {
             String winner=buttons[1][1].getText();
             ShowWinnerMessage(winner);
             UpdateScores(winner);
             ResetBoard();
             return;
         }

         // check for tie condition
        boolean tie=true;

         for(Button row[]:buttons){
             for(Button button:row){
                 if(button.getText().isEmpty()){
                     tie=false;
                     break;
                 }
             }
         }
         if(tie){
             TieMessage();
             ResetBoard();
         }

    }

    private void TieMessage(){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("TIE");
        alert.setContentText("Game is Tie, See you in next Game");
        alert.setHeaderText("");
        alert.showAndWait();

    }

    private void ShowWinnerMessage(String winner){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("WINNER");
        alert.setContentText("Hurray!  Player "+winner+" won the game");
        alert.setHeaderText("");
        alert.showAndWait();

    }

    private void UpdateScores(String winner){
         if(winner.equals("A")){
             // uodate AScoreevariable, display in borderpane
             Ascore++;
             PlayerAresult.setText("Player-A : "+Ascore);

         }else{
             Bscore++;
             PlayerBresult.setText("Player-B : "+Bscore);
         }
    }

    private void ResetBoard(){
        for(Button row[]:buttons){
            for(Button button:row){
                button.setText("");
            }
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
//        Above line is html for GUI, instead of that using java fx for user interface, through built in functions
        Scene scene = new Scene(createContent());
        stage.setTitle("Tic Tac Toe Fun Game");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}