package net.sparkworks.mapper.netsens;

/**
 * Created by cuffaro on 04/10/2016.
 */
public class MeasureMapper {
    String newName;
    Double scaleFactor;
    String measuringUnit;

    public MeasureMapper(String newName, Double scaleFactor, String measuringUnit) {
        this.newName = newName;
        this.scaleFactor = scaleFactor;
        this.measuringUnit = measuringUnit;
    }

    public String getNewName() {
        return newName;
    }

    public Double getScaleFactor() {
        return scaleFactor;
    }

    public String getMeasuringUnit() {
        return measuringUnit;
    }
}
