package edu.gatech.cs6310.Components;

public class Storm {

    String stormId;
    int size;
    int intensity;
    Address stormLocation;

    public Storm(String stormId, int size, int intensity, Address stormLocation) {
        this.stormId = stormId;
        this.size = size;
        this.intensity = intensity;
        this.stormLocation = stormLocation;
    }
    
    public String toString() {
    	return "stormId:"+stormId+",size:"+size+",intensity:"+intensity+",location:"+stormLocation;
    }
}
