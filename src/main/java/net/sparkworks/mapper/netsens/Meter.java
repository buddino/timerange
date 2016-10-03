package net.sparkworks.mapper.netsens;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class Meter {
    private String id;

    private List<Measurement> measurement;

    public String getId() {
        return id;
    }

    @XmlAttribute(name = "id")
    public void setId(String id) {
        this.id = id;
    }

    public List<Measurement> getMeasurement() {
        return measurement;
    }

    @XmlElement(name = "measurement")
    public void setMeasurement(List<Measurement> value) {
        this.measurement = value;
    }

    @Override
    public String toString() {
        return "Meter{" +
                "id='" + id + '\'' +
                ", value=" + measurement +
                '}';
    }
}

