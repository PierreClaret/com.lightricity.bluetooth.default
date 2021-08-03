package com.lightricity.station.bluetooth.decoder;

import com.lightricity.station.bluetooth.FoundSensor;

import java.math.BigDecimal;
import java.math.RoundingMode;

import timber.log.Timber;

public class DecodeFormatMultiSensor2 implements LeScanResult.RuuviTagDecoder {
    @Override
    public FoundSensor decode(byte[] data, int offset) {
        return null;
    }
}
