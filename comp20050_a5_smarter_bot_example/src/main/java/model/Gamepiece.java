package model;

import java.util.Arrays;

public class Gamepiece {

    Location locations[];
    int playerNo;

    public Gamepiece(int coordinates[], int playerNo) {
        locations = new Location[coordinates.length / 2];
        for (int i=0; i<locations.length; i++)
        {
            locations[i] = new Location(coordinates[i*2],coordinates[i*2+1]);
        }
        this.playerNo = playerNo;
    }

    public Gamepiece(Gamepiece gamepiece) {
        playerNo = gamepiece.playerNo;
        locations = new Location[gamepiece.locations.length];
        for (int i=0; i<gamepiece.locations.length; i++) {
            locations[i] = new Location(gamepiece.locations[i]);
        }
    }

    public void flipAlongY() {
        for (Location loc : locations) {
            loc.x = -loc.x;
        }
    }

    public void rotateRight() {
        for (Location loc : locations) {
            int temp = loc.y;
            loc.y = -loc.x;
            loc.x = temp;
        }
    }

    public Location[] getLocations() {
        return locations;
    }

    public int getPlayerNo() {
        return playerNo;
    }

    // Test methods

    @Override
    public boolean equals(Object object) {

        if (object == this) {
            return true;
        }

        if (!(object instanceof Gamepiece)) {
            return false;
        }

        Gamepiece gamepiece = (Gamepiece) object;

        return Arrays.equals(gamepiece.getLocations(), locations);
    }

    @Override
    public String toString() {
        return super.toString()+"(Locations ="+ Arrays.toString(getLocations())+",playerNo="+getPlayerNo()+")";
    }

}

