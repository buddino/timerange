package net.sparkworks.mapper.netsens;

import javax.xml.bind.annotation.XmlAttribute;


public class Measurement {
    private String name = null;
    private Double value = null;

    public String getName() {
        return name;
    }

    @XmlAttribute
    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value.toString();
    }

    @XmlAttribute
    public void setValue(String value) {
        this.value = Double.parseDouble(value);

    }

    @Override
    public String toString() {
        return "Measurement{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
