package com.echo.blokus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    @Test
    void getPiece() {
        int[][] i2={{2, 0},{1, 0}};
        Piece p=new Piece(2,i2,"I2");
        assertEquals("[2,0]\n[1,0]\n",p.getPiece("i2"));
    }

    @Test
    void getLen() {
        int[][] i2={{2, 0},{1, 0}};
        Piece p=new Piece(2,i2,"I2");
        assertEquals(2,p.getLen());
    }

    @Test
    void getArray() {
        int[][] i2={{2, 0},{1, 0}};
        Piece p=new Piece(2,i2,"I2");
        assertEquals(2,p.getArray().length);
    }

    @Test
    void flipHor() {
        int[][] i2={{2, 0},{1, 0}};
        Piece p=new Piece(2,i2, "I2");
//        assertEquals("[0,2]\n[0,1]\n",flipHor(p));
    }

    @Test
    void transpose() {
        int[][] i2={{2, 0},{1, 0}};
        Piece p = new Piece(2, i2, "I2");
//        assertEquals("[2,1]\n[0,0]\n",transpose(p));

    }

    @Test
    void rotate() {
        int[][] i2={{2, 0},{1, 0}};
        Piece p=Piece.rotate(new Piece(2,i2,"I2"),'R');
        assertEquals("[1,2]\n[0,0]\n",p);
    }
    @Test
    void getName() {
        int[][] i2={{2, 0},{1, 0}};
        Piece p=new Piece(2,i2,"I2");
        assertEquals("I2",p.getName());

    }

    @Test
    void testToString() {
        int[][] i2={{2, 0},{1, 0}};
        Piece p=new Piece(2,i2,"I2");
        assertEquals("[2,0]\n[1,0]\n",p.toString());
    }
}