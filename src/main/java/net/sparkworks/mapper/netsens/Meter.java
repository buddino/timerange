package net.sparkworks.mapper.netsens;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

public class Meter {
    private String id;
    private String unit;
    private long value;
    private long timestamp;

    private List<Measurement> measurement;

    public String getId() {
        return id;
    }

    @XmlAttribute(name = "Meter")
    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute(name = "Value")
    public void setValue(long value) {
        this.value = value;
    }

    @XmlAttribute(name = "Timestamp")
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @XmlAttribute(name = "Unit")
    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Meter{" +
                "id='" + id + '\'' +
                ", unit='" + unit + '\'' +
                ", value=" + value +
                ", timestamp=" + timestamp +
                ", measurement=" + measurement +
                '}';
    }
}

