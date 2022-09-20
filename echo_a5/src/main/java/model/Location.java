package model;

public class Location {

    int x;
    int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Location(Location location) {
        x = location.x;
        y = location.y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    // Test methods

    @Override
    public boolean equals(Object object) {

        if (object == this) {
            return true;
        }

        if (!(object instanceof Location)) {
            return false;
        }

        Location location = (Location) object;

        return (x == location.getX()) && (y == location.getY());
    }

    @Override
    public String toString() {
        return super.toString()+"(x="+Integer.toString(getX())+",y="+Integer.toString(getY())+")";
    }
}
