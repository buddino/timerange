package net.sparkworks.mapper.netsens;

/**
 * Created by cuffaro on 04/10/2016.
 */
public class MeasureMapper {
    Measure measure;
    String prefix;
    String id;

    public MeasureMapper(Measure measure, String prefix) {
        this.measure = measure;
        this.prefix = prefix;
        this.id = "";
    }

    public MeasureMapper(Measure measure, String prefix, int id) {
        this.measure = measure;
        this.prefix = prefix;
        this.id = "/" + id;
    }

    public String getName() {
        return prefix + "/" + measure.getEncodedName() + id;
    }

    public double getScaleFactor() {
        return measure.getScaleFactor();
    }

}
