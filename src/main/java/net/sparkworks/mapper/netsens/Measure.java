package net.sparkworks.mapper.netsens;

/**
 * Created by cuffaro on 17/10/2016.
 */
public class Measure {

    private String name;
    private String encodedName;
    private String unit;
    private String aggregation;
    private int sampling;
    private double scaleFactor;

    public Measure(String encodedName, double scaleFactor) {
        this.encodedName = encodedName;
        this.scaleFactor = scaleFactor;
    }

    public Measure(String encodedName, String unit, double scaleFactor) {
        this.encodedName = encodedName;
        this.unit = unit;
        this.scaleFactor = scaleFactor;
    }

    public Measure(String name, String encodedName, String unit, String aggregation, int sampling, double scaleFactor) {
        this.name = name;
        this.encodedName = encodedName;
        this.unit = unit;
        this.aggregation = aggregation;
        this.sampling = sampling;
        this.scaleFactor = scaleFactor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEncodedName() {
        return encodedName;
    }

    public void setEncodedName(String encodedName) {
        this.encodedName = encodedName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAggregation() {
        return aggregation;
    }

    public void setAggregation(String aggregation) {
        this.aggregation = aggregation;
    }

    public int getSampling() {
        return sampling;
    }

    public void setSampling(int sampling) {
        this.sampling = sampling;
    }

    public double getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(double scaleFactor) {
        this.scaleFactor = scaleFactor;
    }
}
