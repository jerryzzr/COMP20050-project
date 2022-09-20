package model;

import java.util.HashMap;
import java.util.Map;

public class GamepieceSet{

    HashMap<String,Gamepiece> pieces;

    // Gamepiece shapes
    static int[] I1 = {0,0};
    static int[] I2 = {0,0,0,1};
    static int[] I3 = {0,0,0,1,0,2};
    static int[] I4 = {0,0,0,1,0,2,0,3};
    static int[] I5 = {0,0,0,1,0,2,0,3,0,4};
    static int[] V3 = {0,0,0,1,1,0};
    static int[] L4 = {0,0,0,1,0,2,1,0};
    static int[] Z4 = {0,0,0,1,1,1,-1,0};
    static int[] O4 = {0,0,0,1,1,1,1,0};
    static int[] L5 = {0,0,0,1,1,0,2,0,3,0};
    static int[] T5 = {0,0,1,0,-1,0,0,1,0,2};
    static int[] V5 = {0,0,0,1,0,2,1,0,2,0};
    static int[] N  = {0,0,0,-1,-1,-1,1,0,2,0};
    static int[] Z5 = {0,0,-1,0,-1,-1,1,0,1,1};
    static int[] T4 = {0,0,-1,0,1,0,0,1};
    static int[] P  = {0,0,1,0,0,-1,1,-1,0,-2};
    static int[] W  = {0,0,-1,0,-1,-1,0,1,1,1};
    static int[] U  = {0,0,0,1,1,1,0,-1,1,-1};
    static int[] F  = {0,0,-1,0,0,-1,0,1,1,1};
    static int[] X  = {0,0,1,0,-1,0,0,1,0,-1};
    static int[] Y  = {0,0,0,1,1,0,2,0,-1,0};

    public GamepieceSet(int playerNo) {
        pieces = new HashMap<String,Gamepiece>();
        initialise(playerNo);
    }

    public void initialise(int playerNo) {
        pieces.clear();
        pieces.put("I1", new Gamepiece( I1, playerNo));
        pieces.put("I2", new Gamepiece( I2, playerNo));
        pieces.put("I3", new Gamepiece( I3, playerNo));
        pieces.put("I4", new Gamepiece( I4, playerNo));
        pieces.put("I5", new Gamepiece( I5, playerNo));
        pieces.put("V3", new Gamepiece( V3, playerNo));
        pieces.put("L4", new Gamepiece( L4, playerNo));
        pieces.put("Z4", new Gamepiece( Z4, playerNo));
        pieces.put("O4", new Gamepiece( O4, playerNo));
        pieces.put("L5", new Gamepiece( L5, playerNo));
        pieces.put("T5", new Gamepiece( T5, playerNo));
        pieces.put("V5", new Gamepiece( V5, playerNo));
        pieces.put("N", new Gamepiece( N, playerNo));
        pieces.put("Z5", new Gamepiece( Z5, playerNo));
        pieces.put("T4", new Gamepiece( T4, playerNo));
        pieces.put("P", new Gamepiece( P, playerNo));
        pieces.put("W", new Gamepiece( W, playerNo));
        pieces.put("U", new Gamepiece( U, playerNo));
        pieces.put("F", new Gamepiece( F, playerNo));
        pieces.put("X", new Gamepiece( X, playerNo));
        pieces.put("Y", new Gamepiece( Y, playerNo));
    }

    public Gamepiece get(String gamepieceName) {
        return pieces.get(gamepieceName);
    }

    public Gamepiece remove(String gamepieceName) {
        return pieces.remove(gamepieceName);
    }

    public Map<String, Gamepiece> getPieces() {
        return pieces;
    }

    // Test methods

    @Override
    public String toString() {
        StringBuilder mapAsString = new StringBuilder(" {");
        for (String key : pieces.keySet()) {
            mapAsString.append(key + "=" + pieces.get(key) + ", ");
        }
        mapAsString.delete(mapAsString.length()-2, mapAsString.length()).append("}");
        return super.toString() + mapAsString.toString();
    }
}
