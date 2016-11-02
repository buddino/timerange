package net.sparkworks.mapper.util;

import net.sparkworks.mapper.netsens.Meter;
import net.sparkworks.mapper.netsens.NetSensConverter;
import net.sparkworks.mapper.netsens.NetsensClient;
import net.sparkworks.mapper.service.SenderService;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class NetsensPollService {

    private static final Logger LOGGER = Logger.getLogger(NetSensConverter.class);

    @Value("${netsens.url}")
    String url;
    @Value("${netsens.username}")
    String username;
    @Value("${netsens.password}")
    String password;
    @Value("${netsens.station}")
    String station;
    @Value("${netsens.meter}")
    String meter;

    @Autowired
    SenderService senderService;

    @Autowired
    NetSensConverter netSensConverter;

    @PostConstruct
    public void init() {
        BasicConfigurator.configure();
        System.setProperty("jsse.enableSNIExtension", "false");
    }

    @Scheduled(fixedDelay = 30000) //TODO Set right request interval
    public void sendMeasurement() throws ParserConfigurationException, IOException, SAXException {
        Date now = new Date();
        LOGGER.info("Request: " + now.getTime());
        final String webserviceURI = url + "?user=" + username + "&password=" + password + "&station=" + station + "&meter=" + meter;
        System.out.println("URL: " + webserviceURI);
        NetsensClient netsens = new NetsensClient(webserviceURI);
        List<Meter> meterList = netsens.getLastMeasurement();
        System.out.print("Start sending to the queue...");
        for (Meter meter : meterList) {
            double value = netSensConverter.getValue(meter.getId(), Double.parseDouble(meter.getValue()));
            long timestamp = Long.parseLong(meter.getTimestamp()) / 1000;
            String meterURI = netSensConverter.getURI(meter.getId());
            //System.out.println(meterURI+"\t"+value+"\t"+timestamp);
            send(meterURI, value, timestamp);
        }
        System.out.println("Done");


    }

    private void send(String URI, double value, long timestamp) {
        senderService.sendMeasurement(URI, value, timestamp);
    }


}
