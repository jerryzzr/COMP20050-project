package com.echo.blokus;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private String name;
    private ArrayList<Piece> pieces = new ArrayList<Piece>(){
        {
            add(Piece.I1);
            add(Piece.I2);
            add(Piece.I3);
            add(Piece.I4);
            add(Piece.I5);
            add(Piece.V3);
            add(Piece.L4);
            add(Piece.Z4);
            add(Piece.O4);
            add(Piece.L5);
            add(Piece.T5);
            add(Piece.V5);
            add(Piece.N);
            add(Piece.Z5);
            add(Piece.T4);
            add(Piece.P);
            add(Piece.W);
            add(Piece.U);
            add(Piece.F);
            add(Piece.X);
            add(Piece.Y);
        }
    };
    private boolean firstPlayer;
    private boolean firstTurn;
    private char token;
    public Player(boolean firstPlayer) { //firstPlayer is true if they are playing first
        Scanner in = new Scanner(System.in);
        this.name = in.nextLine();
        this.firstPlayer = firstPlayer;
        this.firstTurn = true;
        if (firstPlayer){
            token = 'x';
        }else{
            token = 'o';
        }
        this.pieces = pieces;
    }

    public char getToken() {
        return token;
    }

    public void makeMove(Board2 board){
        Move playerMove = new Move();
        Piece pieceToBeRemoved = playerMove.makeMove(board, getPieces(), getToken());
        if (pieceToBeRemoved != null){
            pieces.remove(pieceToBeRemoved);
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public String[] getPieceNames(){
        String[] names = new String[pieces.size()];
        for (int i = 0; i < pieces.size(); i++) {
            names[i] = pieces.get(i).getName();
        }
        return names;
    }

    public static void main(String[] args) {
        Player p =new Player(false);
    }


}
