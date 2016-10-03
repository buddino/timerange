package net.sparkworks.mapper.netsens;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "meters")
public class Meters {

    private List<Meter> meters;

    public List<Meter> getMeters() {
        return meters;
    }

    @XmlElement(name = "meter")
    public void setMeters(List<Meter> meters) {
        this.meters = meters;
    }

    @Override
    public String toString() {
        return "Meters{" +
                "meters=" + meters +
                '}';
    }
}
