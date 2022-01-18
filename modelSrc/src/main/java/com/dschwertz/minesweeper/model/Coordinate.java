package com.dschwertz.minesweeper.model;

public class Coordinate{
    int x;
    int y;

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        return x;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Coordinate)){
            return false;
        }
        Coordinate other = (Coordinate) o;

        return (other.x == x && other.y == y);
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
}
