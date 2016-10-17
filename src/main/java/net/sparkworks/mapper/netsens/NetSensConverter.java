package net.sparkworks.mapper.netsens;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuffaro on 03/10/2016.
 */
public class NetSensConverter {

    private final String baseURI = "gaia-prato/gw1";
    private final String current = "cur";
    private final String voltage = "voltage";
    private final String activePower = "";
    private final String reactivePower = "";
    private final String apparentPower = "";
    private final String activeEnergy = "";
    private final String reactiveEnergy = "";
    private final String apparentEnergy = "";
    private final String powerFactor = "";
    private Map<String, String> unitMap;
    private Map<String, MeasureMapper> meterMap;

    //TODO Load mapping from properties file
    public NetSensConverter() {
        unitMap = new HashMap<>();
        meterMap = new HashMap<>();


        //Sensors mapping
        //Geometri - First Floor
        //FIXME parameters names
        meterMap.put("Geometri-1F-Current1", new MeasureMapper("Geom/1F/" + current + "/1", 10.0, "mA"));
        meterMap.put("Geometri-1F-Current2", new MeasureMapper("Geom/1F/" + current + "/2", 10.0, "mA"));
        meterMap.put("Geometri-1F-Current3", new MeasureMapper("Geom/1F/" + current + "/3", 10.0, "mA"));
        meterMap.put("Geometri-1F-ActivePower", new MeasureMapper("Geom/1F/" + activePower, 10.0, "mW"));
        meterMap.put("Geometri-1F-ApparentPower", new MeasureMapper("Geom/1F/" + apparentPower, 10.0, "mVA"));
        meterMap.put("Geometri-1F-ReactivePower", new MeasureMapper("Geom/1F/" + reactivePower, 10.0, "mVAR"));
        meterMap.put("Geometri-1F-PowerFactor", new MeasureMapper("Geom/1F/" + powerFactor, 10.0, null));
        meterMap.put("Geometri-1F-ActiveEnergy", new MeasureMapper("Geom/1F/" + activeEnergy, 10.0, "mWh"));
        meterMap.put("Geometri-1F-ReactiveEnergy", new MeasureMapper("Geom/1F/" + reactiveEnergy, 10.0, "mVARh"));
        meterMap.put("Geometri-1F-ApparentEnergy", new MeasureMapper("Geom/1F/" + apparentEnergy, 10.0, "mVAh"));

        //Geometri - Ground Floor
        meterMap.put("Geometri-GF-Current1", new MeasureMapper("Cur/1", 10.0, "mA"));
        meterMap.put("Geometri-GF-Current2", new MeasureMapper("Cur/2", 10.0, "mA"));
        meterMap.put("Geometri-GF-Current3", new MeasureMapper("Cur/2", 10.0, "mA"));
        meterMap.put("Geometri-GF-PowerFactor", new MeasureMapper("Cur/2", 10.0, "mA"));
        meterMap.put("Geometri-GF-ActivePower", new MeasureMapper("Cur/2", 10.0, "mA"));
        meterMap.put("Geometri-GF-ReactivePower", new MeasureMapper("Cur/2", 10.0, "mA"));
        meterMap.put("Geometri-GF-ApparentPower", new MeasureMapper("Cur/2", 10.0, "mA"));
        meterMap.put("Geometri-GF-ActiveEnergy", new MeasureMapper("Cur/2", 10.0, "mA"));
        meterMap.put("Geometri-GF-ReactiveEnergy", new MeasureMapper("Cur/2", 10.0, "mA"));
        meterMap.put("Geometri-GF-ApparentEnergy", new MeasureMapper("Cur/2", 10.0, "mA"));

        //Geometri - Labs - FM
        meterMap.put("FM - Current 1", new MeasureMapper("Cur/2", 10.0, "mA"));
        meterMap.put("FM - Current 2", new MeasureMapper("Cur/2", 10.0, "mA"));
        meterMap.put("FM - Current 3", new MeasureMapper("Cur/2", 10.0, "mA"));
        meterMap.put("FM - Power Factor", new MeasureMapper("Cur/2", 10.0, "mA"));
        meterMap.put("FM - Active Power", new MeasureMapper("Cur/2", 10.0, "mA"));
        meterMap.put("FM - Apparent Power", new MeasureMapper("Cur/2", 10.0, "mA"));
        meterMap.put("FM - Reactive Power", new MeasureMapper("Cur/2", 10.0, "mA"));
        meterMap.put("FM - Active Energy", new MeasureMapper("Cur/2", 10.0, "mA"));
        meterMap.put("FM - Reactive Energy", new MeasureMapper("Cur/2", 10.0, "mA"));
        meterMap.put("FM - Apparent Energy", new MeasureMapper("Cur/2", 10.0, "mA"));

        // Geometri - Labs - Lighting
        meterMap.put("Lighting - Current 1", new MeasureMapper("Cur/2", 10.0, "mA"));
        meterMap.put("Lighting - Power Factor", new MeasureMapper("Cur/2", 10.0, "mA"));
        meterMap.put("Lighting - Active Power", new MeasureMapper("Cur/2", 10.0, "mA"));
        meterMap.put("Lighting - Active Energy", new MeasureMapper("Cur/2", 10.0, "mA"));

        //Geometri - Rooms
        meterMap.put("Active Energy", new MeasureMapper("Cur/2", 10.0, "mA"));
        //meterMap.put("Power Factor", new MeasureMapper("Cur/2", 10.0, "mA"));
        //meterMap.put("Current1", new MeasureMapper("Cur/2", 10.0, "mA"));
        //meterMap.put("Active Power", new MeasureMapper("Cur/2", 10.0, "mA"));


        //Hall
        meterMap.put("Hall-Lighting-Current1", new MeasureMapper("Cur/3", 10.0, "mA"));
        meterMap.put("Hall-Lighting-Current2", new MeasureMapper("Cur/3", 10.0, "mA"));
        meterMap.put("Hall-Lighting-Current3", new MeasureMapper("Cur/3", 10.0, "mA"));
        meterMap.put("Hall-Lighting-ActivePower", new MeasureMapper("Cur/3", 10.0, "mA"));
        meterMap.put("Hall-Lighting-ActiveEnergy", new MeasureMapper("Cur/3", 10.0, "mA"));
        meterMap.put("Hall-Lighting-PowerFactor", new MeasureMapper("Cur/3", 10.0, "mA"));

        meterMap.put("Voltage 1", new MeasureMapper("Vol/1", 10.0, "mW"));
        meterMap.put("Voltage 2", new MeasureMapper("vol/2", 10.0, "mW"));
        meterMap.put("Voltage 3", new MeasureMapper("Vol/3", 10.0, "mW"));
        meterMap.put("Active Power", new MeasureMapper("ActCon", 10.0, "mWh"));
        meterMap.put("Reactive Power", new MeasureMapper("ReactPower", 10.0, "mVAR"));
        meterMap.put("Apparent Power", new MeasureMapper("AppPower", 10.0, "mVA"));
    }

    public String getURI(String meterName) {
        if (meterMap.containsKey(meterName)) {
            MeasureMapper measureMapper = meterMap.get(meterName);
            return baseURI + "/" + measureMapper.getNewName();
        } else {
            System.err.println("Sensor name " + meterName + " not found");
            return null;
        }
    }

    public Double getValue(String meterName, String stringValue) {
        Double value = Double.parseDouble(stringValue);
        MeasureMapper measureMapper = meterMap.get(meterName);
        return measureMapper.getScaleFactor() * value;
    }

    //TODO Timestamp converter?

}
