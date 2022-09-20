package com.echo.blokus;

public class Piece{

    private static final int[][] i1 = {{2}};
    static final Piece I1 = new Piece(1, i1, "I1");

    private static final int[][] i2 = {{1, 0}, {2, 0}};
    static final Piece I2 = new Piece(2, i2, "I2");

    private static final int[][] i3 = {{1, 0, 0}, {1, 0, 0}, {2, 0, 0}};
    static final Piece I3 = new Piece(3, i3,"I3");

    private static final int[][] i4 = {{1,0,0,0},{1,0,0,0},{1,0,0,0},{2,0,0,0}};
    static final Piece I4 = new Piece(4,i4,"I4");

    private static final int[][] i5 = {{1,0,0,0,0},{1,0,0,0,0},{1,0,0,0,0},{1,0,0,0,0},{2,0,0,0,0}};
    static final Piece I5 = new Piece(5,i5,"I5");

    private static final int[][] v3 = {{1, 0}, {2, 1}};
    static final Piece V3 = new Piece(2, v3,"V3");

    private static final int[][] l4= {{1,0,0},{1,0,0},{2,1,0}};
    static final Piece L4 = new Piece(3,l4,"L4");

    private static final int[][] z4= {{0,1,1},{1,2,0},{0,0,0}};
    static final Piece Z4 = new Piece(3,z4,"Z4");

    private static final int[][] o4= {{1,1},{2,1}};
    static final Piece O4= new Piece(2,o4,"O5");

    private static final int[][] l5= {{1,0,0,0},{2,1,1,1},{0,0,0,0},{0,0,0,0}};
    static final Piece L5= new Piece(4,l5,"L5");

    private static final int[][] t5= {{0,1,0},{0,1,0},{1,2,1}};
    static final Piece T5= new Piece(3,t5,"T5");

    private static final int[][] v5= {{1,0,0},{1,0,0},{2,1,1}};
    static final Piece V5= new Piece(3,v5,"V5");

    private static final int[][] n= {{0,2,1,1},{1,1,0,0},{0,0,0,0},{0,0,0,0}};
    static final Piece N= new Piece(4,n,"N");

    private static final int[][] z5= {{0,0,1},{1,2,1},{1,0,0}};
    static final Piece Z5= new Piece(3,z5,"Z5");

    private static final int[][] t4= {{0,1,0},{1,2,1},{0,0,0}};
    static final Piece T4= new Piece(3,t4,"T4");

    private static final int[][] p= {{2,1,0},{1,1,0},{1,0,0}};
    static final Piece P= new Piece(3,p,"P");

    private static final int[][] w= {{0,1,1},{1,2,0},{1,0,0}};
    static final Piece W= new Piece(3,w,"W");

    private static final int[][] u= {{1,1,0},{2,0,0},{1,1,0}};
    static final Piece U= new Piece(3,u,"U");

    private static final int[][] f= {{0,1,1},{1,2,0},{0,1,0}};
    static final Piece F= new Piece(3,f,"F");

    private static final int[][] x= {{0,1,0},{1,2,1},{0,1,0}};
    static final Piece X= new Piece(3,x,"X");

    private static final int[][] y= {{0,1,0,0},{1,2,1,1},{0,0,0,0},{0,0,0,0}};
    static final Piece Y= new Piece(4,y,"Y");

    private final int len;
    private final int[][] array;
    private final String name;
    private Boolean firstplayer;

    public Piece(int len, int[][] array, String name) {
        this.len = len;
        this.array = array;
        this.name = name;
    }
    static Piece flipHor(Piece piece) {
        int[][] tempArray = new int[piece.len][piece.len];
        for (int row = 0; row < piece.len; row++) {
            for (int col = 0; col < piece.len; col++) {
                tempArray[row][col] = piece.array[row][piece.len - col - 1];
            }
        }
        return new Piece(piece.len, tempArray, piece.name);
    }

    static Piece transpose(Piece piece) {
        int[][] tempArray = new int[piece.len][piece.len];
        for (int row = 0; row < piece.len; row++) {
            for (int col = 0; col < piece.len; col++) {
                tempArray[row][col] = piece.array[col][row];
            }
        }
        return new Piece(piece.len, tempArray, piece.name);
    }

    static Piece rotate(Piece piece, char s) {
        Piece tempPiece;
        switch (s) {
            case ('R'):
                tempPiece = flipHor(transpose(piece));
                break;
            case ('D'):
                tempPiece = flipHor(transpose(flipHor(transpose(piece))));
                break;
            case ('L'):
                tempPiece = flipHor(transpose(flipHor(transpose(flipHor(transpose(piece))))));
                break;
            default:
                tempPiece = piece;
        }
        return tempPiece;
    }
    public Piece getPiece(String str)throws IllegalArgumentException{
        switch (str) {
            case "i1" : {return I1;}
            case "i2" : {return I2;}
            case "i3" : {return I3;}
            case "i4" : {return I4;}
            case "i5" : {return I5;}
            case "v3" : {return V3;}
            case "l4" : {return L4;}
            case "z4" : {return Z4;}
            case "o4" : {return O4;}
            case "l5" : {return L5;}
            case "t5" : {return T5;}
            case "v5" : {return V5;}
            case "n"  : {return N;}
            case "z5" : {return Z5;}
            case "t4" : {return T4;}
            case "p"  : {return P;}
            case "w"  : {return W;}
            case "u"  : {return U;}
            case "f"  : {return F;}
            case "x"  : {return X;}
            case "y"  : {return Y;}

            default : {throw new IllegalArgumentException("not exist");}
        }
    }


    public int getLen() {
        return len;
    }


    public int[][] getArray() {
        return array;
    }


    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        String Symbol="X";
        for (int row = 0; row < this.len; row++) {

            for (int col = 0; col < this.len; col++) {
                if(this.array[row][col]!=0) {
                    s.append(String.format("%2s",Symbol));
                } else {
                    s.append(String.format("%2s"," "));
                }

            }
            s.append("\n");
        }
        return String.valueOf(s);
    }


}
