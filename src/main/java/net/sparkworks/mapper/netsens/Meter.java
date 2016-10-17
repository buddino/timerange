package net.sparkworks.mapper.netsens;

import javax.xml.bind.annotation.XmlAttribute;

public class Meter {
    private String id;
    private String unit;
    private String value;
    private String timestamp;

    public String getId() {
        return id;
    }

    @XmlAttribute(name = "Meter")
    public void setId(String id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    @XmlAttribute(name = "Unit")
    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getValue() {
        return value;
    }

    @XmlAttribute(name = "Value")
    public void setValue(String value) {
        this.value = value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @XmlAttribute(name = "Timestamp")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Meter{" +
                "id='" + id + '\'' +
                ", unit='" + unit + '\'' +
                ", value=" + value +
                ", timestamp=" + timestamp +
                '}';
    }
}

