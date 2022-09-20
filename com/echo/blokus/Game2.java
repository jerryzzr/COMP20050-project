package com.echo.blokus;

import java.util.Arrays;
import java.util.Scanner;

//For com.echo.blokus.Board2, two dimensional version
//Wrapper class for board, manages setup and moves
public class Game2 {

    public Game2(int option) {
        if (option == 1) {
            System.out.print("Enter the name of PLayer(X): ");
            Player player1 = new Player(true);
            System.out.print("Enter the name of PLayer(O): ");
            Player player2 = new Player(false);
            System.out.println("Player " + player1.getName() + " (" + player1.getToken() + ")" + "goes first!");
            Board2 board2 = new Board2();
            System.out.println();
            System.out.println("BLOKUS DUO");
            System.out.println();
            board2.print();
            System.out.println();
            System.out.println("Player " + player1.getName() + " (X) gamepieces: " + Arrays.toString(player1.getPieceNames()));
            System.out.println("Player " + player2.getName() + " (O) gamepieces: " + Arrays.toString(player2.getPieceNames()));
            System.out.println();
            player1.makeMove(board2);
            board2.print();
            player2.makeMove(board2);
            board2.print();
            System.out.println(player1.getName() + " (X) gamepieces: " + Arrays.toString(player1.getPieceNames()));
            System.out.println(player2.getName() + " (O) gamepieces: " + Arrays.toString(player2.getPieceNames()));
        }
        else if(option==2){
            System.out.print("Enter the name of PLayer(O): ");
            Player player1 = new Player(true);
            System.out.print("Enter the name of PLayer(X): ");
            Player player2 = new Player(false);
            System.out.println("Player " + player1.getName() + " (" + player1.getToken() + ")" + "goes first!");
            Board2 board2 = new Board2();
            System.out.println();
            System.out.println("BLOKUS DUO");
            System.out.println();
            board2.print();
            System.out.println();
            System.out.println("Player " + player1.getName() + " (O) gamepieces: " + Arrays.toString(player1.getPieceNames()));
            System.out.println("Player " + player2.getName() + " (X) gamepieces: " + Arrays.toString(player2.getPieceNames()));
            System.out.println();
            player1.makeMove(board2);
            board2.print();
            player2.makeMove(board2);
            board2.print();
            System.out.println(player1.getName() + " (O) gamepieces: " + Arrays.toString(player1.getPieceNames()));
            System.out.println(player2.getName() + " (X) gamepieces: " + Arrays.toString(player2.getPieceNames()));
        }

    }

    public static void main(String[] args) {
        if (args.length>=1) {
            if (args[1].compareTo("-X")==0) {
                //player 1 use symbol 'X'
                Game2 game = new Game2(1);
                //force to start at (4,9)
            } else if (args[1].compareTo("-O")==0) {
                //player 1 use symbol 'O'
                Game2 game = new Game2(2);
                //force to start at (9,4)
            } else {
                //throw new
                System.out.println("invalid command line argument");
            }
        }else {
            Game2 game2=new Game2(1);
        }
    }
}