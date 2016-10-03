package net.sparkworks.mapper.util;

import com.sensorflare.io.SummaryFactory;
import net.sparkworks.mapper.netsens.Measurement;
import net.sparkworks.mapper.netsens.Meter;
import net.sparkworks.mapper.netsens.NetsensClient;
import net.sparkworks.mapper.service.SenderService;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
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
    /**
     * LOGGER.
     */
    //private static final Logger LOGGER = Logger.getLogger(NetsensPollService.class);
    //TODO Client for REST API
    private final Set<String> synfieldDevices = new HashSet<>();
    private final SummaryFactory sf = new SummaryFactory();
    private final DateTimeFormatter dateStringFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    @Autowired
    SenderService senderService;

    //TODO Remove?
    @PostConstruct
    public void init() {
    }

    @Scheduled(fixedDelay = 100000) //TODO Set right request interval
    public void sendMeasurement() throws ParserConfigurationException, IOException, SAXException {


        //Request XML measurement
        String URI = "http://192.168.9.41/prova.xml";
        NetsensClient netsens = new NetsensClient(URI);
        List<Meter> meters = netsens.getLastMeasurement();

        for (Meter meter : meters) {
            for (Measurement measurement : meter.getMeasurement()) {
                System.out.println(meter.getId() + " " + measurement.getName() + " " + measurement.getValue());
            }
        }

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

    private void send(String gateway, String capability, double doubleValue, long timestamp) {
        senderService.sendMeasurement(gateway + "/" + capability, doubleValue, timestamp);
    }
}
