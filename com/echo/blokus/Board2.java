package com.echo.blokus;

public class Board2 {
//implemented as a two dimensional char array
    char[][] board2 = new char[14][14];

    public Board2(){
        for (int row = 0; row < board2.length; row++) {
            for (int col = 0; col < board2[row].length; col++){
                setElemByRowCol('.', row, col);
            }
        }
        setElemByRowCol('o', 4, 9);//changes for set up the origin point
        setElemByRowCol('o', 9, 4);
    }

    public char[][] getBoard() {
        return board2;
    }

    public char getElemByRowCol( int row, int col){ //converts from two to one dimension
        return getBoard()[row][col];

    }

    public void setElemByRowCol(char newElem, int row, int col ){ // setter and converter
        board2[row][col] = newElem;
    }

    public void print(){ //prints out board with indexes for rows and columns
        for (int row=13;row>=0; row--) { // board implemented as a one-dimensional array
            //changes for make the order correct
            if (row >= 10){
                System.out.print(row + " ");
            }else{
                System.out.print(" " + row + " ");
            }
            for (int col = 0; col < board2[row].length; col++){
                System.out.print(getElemByRowCol(row, col) + "  ");
                }
            System.out.println();

            }

        System.out.println("   0  1  2  3  4  5  6  7  8  9 10 11 12 13");

    }

    public static void main(String[] args) {
        Board2 b2 = new Board2();
        b2.print();
    }





}



