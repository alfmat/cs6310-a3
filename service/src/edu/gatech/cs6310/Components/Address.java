package edu.gatech.cs6310.Components;

public class Address {
    public int x;
    public int y;

    public Address(int x, int y) {

        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public double getDistance(Address address) {
        return Math.sqrt((address.y - y) * (address.y - y) + (address.x - x) * (address.x - x));
    }


}
