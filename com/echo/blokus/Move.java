package com.echo.blokus;

import java.util.ArrayList;
import java.util.Scanner;

public class Move {
    private Piece piece;
//Checking if the first move is at starting point
    public boolean isValidFirstMove(Board2 board, Piece movingPiece, int row, int col) {
        int[] squareof2 = find2(movingPiece);
        int rowof2 = squareof2[0];
        int colof2 = squareof2[1];
        boolean valid = false;
        for (int i = 0; i < movingPiece.getLen(); i++) {
            for (int j = 0; j < movingPiece.getLen(); j++) {
                if(movingPiece.getArray()[i][j] != 0){
                    char elem = board.getElemByRowCol(i - rowof2 + row, j - colof2 + col);
                    if (elem == '*'){
                        valid = true;
                        break;
                    }
                }
            }
        }
        return valid;
    }




    private boolean isMoveValid(Board2 board, Piece movingPiece, int row, int col) {
        boolean valid = false;
        if(isNotOutOfBounds(board,movingPiece, row, col)){
            if(isOnTopOfOtherPiece(board,movingPiece, row, col)){
                if(isTouchingSameColorAtLeastOneCorner(board,movingPiece, row, col)){
                    if(isNotTouchingSameColorOnFlatEdge(board,movingPiece, row, col)){
                        if (isValidFirstMove(board,movingPiece, row, col)){
                            valid = true;
                        }
                    }

                }

            }

        }


        return valid;
    }

    private boolean isNotTouchingSameColorOnFlatEdge(Board2 board, Piece movingPiece, int row, int col) {
        int[] squareof2 = find2(movingPiece);
        int rowof2 = squareof2[0];
        int colof2 = squareof2[1];

        boolean valid = false;
        for (int i = 0; i < movingPiece.getLen(); i++) {
            for (int j = 0; j < movingPiece.getLen(); j++) {
                if(movingPiece.getArray()[i][j] != 0){
                    //TODO: Implement method to check that no part of the piece is touching another square of the same colour.
                    valid = true;
                    break;

                }
            }
        }

        return valid;

    }


    private boolean isTouchingSameColorAtLeastOneCorner(Board2 board, Piece movingPiece, int row, int col) {
        int[] squareof2 = find2(movingPiece);
        int rowof2 = squareof2[0];
        int colof2 = squareof2[1];

        boolean valid = false;
        for (int i = 0; i < movingPiece.getLen(); i++) {
            for (int j = 0; j < movingPiece.getLen(); j++) {
                if(movingPiece.getArray()[i][j] != 0){
                    // TODO: Implement isDiagonalPointOfPiece and check here
                    valid = true;
                    break;
                }
            }
        }

        return valid;

    }


    private boolean isOnTopOfOtherPiece(Board2 board, Piece movingPiece, int row, int col) {
        int[] squareof2 = find2(movingPiece);
        int rowof2 = squareof2[0];
        int colof2 = squareof2[1];

        boolean valid = true;
        for (int i = 0; i < movingPiece.getLen(); i++) {
            for (int j = 0; j < movingPiece.getLen(); j++) {
                if(movingPiece.getArray()[i][j] != 0){
                    char elem = board.getElemByRowCol(i - rowof2 + row, j - colof2 + col);
                    if (elem == 'x' || elem == 'o'){
                        valid = false;
                        break;
                    }
                }
            }
        }

        return valid;
    }

    private boolean isNotOutOfBounds(Board2 board, Piece movingPiece, int row, int col) {
        int[] squareof2 = find2(movingPiece);
        int rowof2 = squareof2[0];
        int colof2 = squareof2[1];

        boolean valid = true;
        for (int i = 0; i < movingPiece.getLen(); i++) {
            for (int j = 0; j < movingPiece.getLen(); j++) {
                if(movingPiece.getArray()[i][j] != 0){
                    if (i - rowof2 + row >14 || i - rowof2 + row < 0 ){
                        valid = false;
                        break;

                    } else if(j - colof2 + col >14 || j - colof2 + col < 0 ){
                        valid = false;
                        break;
                    }
                }
            }
        }

        return valid;
    }

    public Piece makeMove(Board2 board, ArrayList<Piece> pieces, char token) {
        //returns piece to be removed
        Piece pieceToBeRemoved = null;
        boolean validMove = true;
        Piece movingPiece = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please chose the gamepiece you would like to use :");
        String pieceToBeMoved = scanner.nextLine();
        if(hasPieceAvailableToMove(pieces, pieceToBeMoved)) {
            movingPiece = getPieceOnceValid(pieces, pieceToBeMoved);
        }else{
            validMove= false;
        }
        System.out.println(movingPiece.toString());
        String action = " ";
        while(action!="p") {
            System.out.println("Enter 'r' to rotate, 'f' to flip, or 'p' to place the gamepiece:");
            action = scanner.next();
            if (action.compareTo("f") == 0) {
                movingPiece = Piece.flipHor(movingPiece);
                System.out.println(movingPiece.toString());
            } else if (action.compareTo("r") == 0) {
                movingPiece = Piece.rotate(movingPiece, 'R');
                System.out.println(movingPiece.toString());
            } else if (action.compareTo("p") == 0) {
                System.out.println(movingPiece.toString());
                break;
            }
            else{
                System.out.println("input wrong, Please input 'r' 'f 'p' " );
            }
        }
            //take the row and col the player would like to put
        System.out.println("Please enter row you would like to put your chosen gamepiece:" );
        int startRow = scanner.nextInt();
        System.out.println("Please enter column you would like to put your chosen gamepiece:" );
        int startCol = scanner.nextInt();
        if (!validMove){
            System.out.println("Move not valid, please try again");
            makeMove(board, pieces, token);
        }else{
            updateBoard(board, movingPiece, startRow, startCol, token);
            pieceToBeRemoved = movingPiece;
        }

        return pieceToBeRemoved;
    }

    private void updateBoard(Board2 board, Piece movingPiece, int startRow, int startCol, char token) {
        int[] squareof2 = find2(movingPiece);
        int rowof2 = squareof2[0];
        int colof2 = squareof2[1];

        boolean valid = true;
        for (int i = 0; i < movingPiece.getLen(); i++) {
            for (int j = 0; j < movingPiece.getLen(); j++) {
                if(movingPiece.getArray()[i][j] != 0){
                    board.setElemByRowCol(token, rowof2 - i + startRow, j - colof2 + startCol);

                }
            }
        }
    }



    private boolean hasPieceAvailableToMove(ArrayList<Piece> pieces, String choose) {
        boolean available = false;
        for (Piece piece: pieces){
            if (choose.equalsIgnoreCase(piece.getName())){
                available = true;
            }
        }
        return available;
    }

    private Piece getPieceOnceValid(ArrayList<Piece> pieces, String choose){
        Piece movingPiece = null;

        for (Piece piece: pieces){
            if (choose.equalsIgnoreCase(piece.getName())){
                movingPiece = piece;

            }
        }

        return movingPiece;
    }

    private static int[] find2(Piece piece){
        for (int i = 0; i < piece.getLen(); i++) {
            for (int j = 0; j < piece.getLen(); j++) {
                if(2 == piece.getArray()[i][j]){
                    return new int[]{i, j};
                }
            }
        }
        int[] ints = new int[]{-1,-1};
        return ints;
    }

}
