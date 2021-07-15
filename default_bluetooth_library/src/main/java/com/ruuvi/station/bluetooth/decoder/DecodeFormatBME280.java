package com.ruuvi.station.bluetooth.decoder;

import com.ruuvi.station.bluetooth.FoundSensor;
import java.math.BigDecimal;
import java.math.RoundingMode;

import timber.log.Timber;

public class DecodeFormatBME280 implements LeScanResult.RuuviTagDecoder {
    //offset = 7
    @Override
    public FoundSensor decode(byte[] data, int offset) {
        FoundSensor tag = new FoundSensor();
        tag.setBrand("BME 280 Bosch");
        int decodeFormat = data[offset+1];
        switch (decodeFormat) {
            case 1:
                tag.setDataFormat("Decode temperature");
                tag.setTemperature((data[6 + offset] << 8 | data[5 + offset] & 0xFF) / 100d);
                tag.setPressure(0.0);
                tag.setHumidity(0.0);
                break;
            case 2:
                tag.setDataFormat("Decode humidity");
                tag.setTemperature(0.0);
                tag.setPressure(0.0);
                tag.setHumidity(((data[10 + offset] & 0xFF) << 8 | data[9 + offset] & 0xFF) / 1000d);
                break;
            case 3:
                tag.setDataFormat("Decode pressure");
                tag.setTemperature(0.0);
                tag.setPressure((double) ((data[8 + offset] & 0xFF) << 8 | data[7 + offset] & 0xFF)/0.5);
                tag.setPressure(tag.getPressure() != null ? tag.getPressure() : 0.0);
                tag.setHumidity(0.0);
                break;
            case 4:
                tag.setDataFormat("Decode temperature, humidity");
                tag.setTemperature((data[6 + offset] << 8 | data[5 + offset] & 0xFF) / 100d);
                tag.setPressure(0.0);
                tag.setPressure(tag.getPressure() != null ? tag.getPressure() : 0.0);
                tag.setHumidity(((data[10 + offset] & 0xFF) << 8 | data[9 + offset] & 0xFF) / 1000d);
                break;
            case 5:
                tag.setDataFormat("Decode temperature, pressure");
                tag.setTemperature((data[6 + offset] << 8 | data[5 + offset] & 0xFF) / 100d);
                tag.setPressure((double) ((data[8 + offset] & 0xFF) << 8 | data[7 + offset] & 0xFF)/0.5);
                tag.setPressure(tag.getPressure() != null ? tag.getPressure() : 0.0);
                tag.setHumidity(0.0);
                break;
            case 6:
                tag.setDataFormat("Decode pressure, humidity");
                tag.setTemperature(0.0);
                tag.setPressure((double) ((data[8 + offset] & 0xFF) << 8 | data[7 + offset] & 0xFF)/0.5);
                tag.setPressure(tag.getPressure() != null ? tag.getPressure() : 0.0);
                tag.setHumidity(((data[10 + offset] & 0xFF) << 8 | data[9 + offset] & 0xFF) / 1000d);
                break;
            case 7:
                tag.setDataFormat("Decode temperature, pressure, humidity");
                tag.setTemperature((data[6 + offset] << 8 | data[5 + offset] & 0xFF) / 100d);
                tag.setPressure((double) ((data[8 + offset] & 0xFF) << 8 | data[7 + offset] & 0xFF) / 0.5);
                tag.setPressure(tag.getPressure() != null ? tag.getPressure() : 0.0);
                tag.setHumidity(((data[10 + offset] & 0xFF) << 8 | data[9 + offset] & 0xFF) / 1000d);
                break;
            default:
                Timber.d("Unknown tag protocol version: %1$s (PROTOCOL_OFFSET: %2$s)", decodeFormat, offset);
        }

        // make it pretty
        tag.setTemperature(round(tag.getTemperature() != null ? tag.getTemperature() : 0.0, 2));
        tag.setHumidity(round(tag.getHumidity() != null ? tag.getHumidity() : 0.0, 2));
        tag.setPressure(round(tag.getPressure(), 2));

        return tag;
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
