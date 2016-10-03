package net.sparkworks.mapper.netsens;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class NetsensClient {

    //TODO Scalare le misure
    URI endpoint;
    RestOperations restTemplate = new RestTemplate();
    Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();


    public NetsensClient(String url) {
        try {
            this.endpoint = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        unmarshaller.setClassesToBeBound(Meter.class, Measurement.class, Meters.class);
    }

    public List<Meter> getLastMeasurement() {
        String result = restTemplate.getForObject(endpoint, String.class);
        StringReader stringReader = new StringReader(result);
        StreamSource streamSource = new StreamSource(stringReader);
        Meters meters = (Meters) unmarshaller.unmarshal(streamSource);
        return meters.getMeters();
    }


}
