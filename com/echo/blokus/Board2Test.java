package com.echo.blokus;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Board2Test {

    @Test
    void getBoard() {
        Board2 board2=new Board2();
        assertEquals(14,board2.getBoard().length);
    }

    @Test
    void getElemByRowCol() {
        Board2 board2=new Board2();
        assertEquals('.',board2.getElemByRowCol(3,5));
        assertEquals('*',board2.getElemByRowCol(4,9));
        assertEquals('*',board2.getElemByRowCol(9,4));
    }

    @Test
    void setElemByRowCol() {
        Board2 board2=new Board2();
        board2.setElemByRowCol('p',3,3);
        assertEquals('p',board2.getElemByRowCol(3,3));
    }

}