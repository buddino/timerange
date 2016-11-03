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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class NetsensPollService {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\u001B[36m";

    private static final Logger LOGGER = Logger.getLogger(NetSensConverter.class);

    @Value("#{'${meters}'.split(',')}")
    List<String> meterURIs;
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
    @Autowired
    ShutdownManager shutdownManager;

    String fromDateString = "01/10/2016";
    String lastDateString = "5/10/2016";
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date from, to, last;

    @PostConstruct
    public void init() throws ParseException {
        BasicConfigurator.configure();
        System.setProperty("jsse.enableSNIExtension", "false");
        from = sdf.parse(fromDateString);
        last = sdf.parse(lastDateString);
    }

    private String getRequestURI(String meter, long from, long to){
        return url + "?user=" +
                username + "&password=" + password +
                "&station=" + station + "&meter=" + meter +
                "&from=" + from + "&to=" + to;
    }

    @Scheduled(fixedDelay = 10000)
    public void sendMeasurement() throws ParserConfigurationException, IOException, SAXException, ParseException {
        System.out.println(ANSI_CYAN+"Day: "+ sdf.format(from)+ANSI_RESET);
        Calendar c = Calendar.getInstance();
        c.setTime(from);
        c.add(Calendar.DATE, 1);
        to = c.getTime();
        //System.out.println("From "+ from.getTime() +" to "+to.getTime() );
        for( String meterUri : meterURIs){
            String requestURI = getRequestURI(meterUri,from.getTime(),to.getTime());
            NetsensClient netsensClient = new NetsensClient(requestURI);
            System.out.print(ANSI_GREEN+"Getting: "+meterUri+"..."+ANSI_RESET);
            List<Meter> meterList = netsensClient.getMeasurement();
            System.out.println(ANSI_BLUE+"Got " +meterList.size()+" measurements"+ANSI_RESET);

            for (Meter meter : meterList) {
                double value = netSensConverter.getValue(meter.getId(), Double.parseDouble(meter.getValue()));
                long timestamp = Long.parseLong(meter.getTimestamp()) / 1000;
                String meterURI = netSensConverter.getURI(meter.getId());
                send(meterURI, value, timestamp);
            }
        }
        if(to.compareTo(last)>=0){
            shutdownManager.initiateShutdown(200);
        }
        from = to;




/*
        System.err.println("HELLOOOOO");
        Date now = new Date();
        LOGGER.info("Request: " + now.getTime());
        System.out.println("URL: " + webserviceURI);
        NetsensClient netsens = new NetsensClient(webserviceURI);
        List<Meter> meterList = netsens.getMeasurement();
        System.out.print("Start sending to the queue...");
        for (Meter meter : meterList) {
            double value = netSensConverter.getValue(meter.getId(), Double.parseDouble(meter.getValue()));
            long timestamp = Long.parseLong(meter.getTimestamp()) / 1000;
            String meterURI = netSensConverter.getURI(meter.getId());
            //System.out.println(meterURI+"\t"+value+"\t"+timestamp);
            send(meterURI, value, timestamp);
        }
        System.out.println("Done");
*/

    }

    private void send(String URI, double value, long timestamp) {
        senderService.sendMeasurement(URI, value, timestamp);
    }


}
