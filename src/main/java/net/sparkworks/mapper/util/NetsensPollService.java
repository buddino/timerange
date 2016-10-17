package net.sparkworks.mapper.util;

import com.sensorflare.io.SummaryFactory;
import net.sparkworks.mapper.netsens.Meter;
import net.sparkworks.mapper.netsens.NetSensConverter;
import net.sparkworks.mapper.netsens.NetsensClient;
import net.sparkworks.mapper.service.SenderService;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class NetsensPollService {
    //private static final Logger LOGGER = Logger.getLogger(NetsensPollService.class);


    //TODO Client for REST API
    private final Set<String> synfieldDevices = new HashSet<>();
    private final SummaryFactory sf = new SummaryFactory();
    private final DateTimeFormatter dateStringFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    @Value("${netsens.url}")
    String url;
    @Value("${netsens.username}")
    String username;
    @Value("${netsens.password}")
    String password;
    @Value("${netsens.station}")
    String station;
    @Autowired
    SenderService senderService;

    //TODO Remove?
    @PostConstruct
    public void init() {
    }

    @Scheduled(fixedDelay = 300000) //TODO Set right request interval
    public void sendMeasurement() throws ParserConfigurationException, IOException, SAXException {


        //Request XML measurement
        NetSensConverter netSensConverter = new NetSensConverter();
        String webserviceURI = "https://www.live.netsens.it/export/xml_export_2A.php?user=cnit&password=cnit_pwd&station=723&meter=last_reading";
        NetsensClient netsens = new NetsensClient(webserviceURI);
        List<Meter> meterList = netsens.getLastMeasurement();
        for (Meter meter : meterList) {
            System.out.println(netSensConverter.getURI(meter.getId()));
        }
        /*
        NetSensConverter netSensConverter = new NetSensConverter();

        String dummyMeter = "TEST";
        String dummySensor = "Current 1";
        String dummyValue = "123456";
        String dummyTimestamp = "784536487";

        System.out.println(netSensConverter.getURI(dummyMeter,dummySensor));
        System.out.println(netSensConverter.getValue(dummySensor,dummyValue));
        String URI = netSensConverter.getURI(dummyMeter,dummySensor);
        Double value = netSensConverter.getValue(dummySensor,dummyValue);
        Long timestamp = Long.parseLong(dummyTimestamp);
        send(URI, value, timestamp);
*/
        /*
        List<Meter> meters = netsens.getLastMeasurement();

        for (Meter meter : meters) {
            for (Measurement measurement : meter.getMeasurement()) {
                System.out.println(meter.getId() + " " + measurement.getName() + " " + measurement.getValue());
            }
        }
        */
        /////
        /*
        for (final String mac : synfieldDevices) {
            LOGGER.info("polling " + mac);
            try {
                final List<SynfieldSensor> sensors = synfield.getSensors(mac).getResponse().getSensors();
                final Map<String, Long> uris = new HashMap<>();
                for (final SynfieldSensor sensor : sensors) {
                    final String uri = "synfield-" + mac + "/" + WordUtils.capitalize(sensor.getService());
                    final URL summaryURL = new URL(sparkWorksStorage + uri.replaceAll(" ", "%20"));
                    LOGGER.info(summaryURL);
                    uris.put(uri, sf.fromURL(summaryURL).getLatestTime().getMillis());
                }

                for (final String key : uris.keySet()) {
                    LOGGER.info(key);
                    final String gateway = key.split("/")[0];
                    final String capability = key.split("/")[1];
                    if (uris.get(key) == 0) {
                        final SynfieldMeasurementsPage measurementsForGateway = synfield.getMeasurement(mac);
                        for (final SynfieldMeasurement synfieldMeasurement : measurementsForGateway.getResponse().getMeasurement()) {
                            if (synfieldMeasurement.getService().toLowerCase().endsWith(capability.toLowerCase())) {
                                send(gateway, capability, synfieldMeasurement.getDoubleValue(), dateStringFormat.parseMillis(synfieldMeasurement.getTimestamp()));
                            }

                        }
                    } else {
                        final DateTime then = new DateTime(uris.get(key));
                        final DateTime now = new DateTime();
                        final SynfieldMeasurementsPage measurementsForGateway = synfield.getMeasurement(mac,
                                then.getYear() + "-" + then.getMonthOfYear() + "-" + then.getDayOfMonth()
                                , now.getYear() + "-" + now.getMonthOfYear() + "-" + now.getDayOfMonth());
                        for (final SynfieldMeasurement synfieldMeasurement : measurementsForGateway.getResponse().getMeasurement()) {
                            if (synfieldMeasurement.getService().toLowerCase().endsWith(capability.toLowerCase())) {
                                final DateTime measurementTime = new DateTime(dateStringFormat.parseMillis(synfieldMeasurement.getTimestamp()));
                                if (measurementTime.isAfter(then)) {
                                    send(gateway, capability, synfieldMeasurement.getDoubleValue(), dateStringFormat.parseMillis(synfieldMeasurement.getTimestamp()));
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error(e, e);
            }
        }
*/
    }

    private void send(String URI, double value, long timestamp) {
        senderService.sendMeasurement(URI, value, timestamp);
    }
}
