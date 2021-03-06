package com.lightricity.station.bluetooth

import android.app.Application
import com.lightricity.station.bluetooth.decoder.LeScanResult
import com.lightricity.station.bluetooth.util.ScannerSettings

object BluetoothLibrary {
    internal lateinit var bluetoothInteractor: BluetoothInteractor
    internal val scanIntervalMilliseconds
        get() = bluetoothInteractor.settings.getBackgroundScanIntervalMilliseconds()
    val isInitialized
        get() = this::bluetoothInteractor.isInitialized

    fun getBluetoothInteractor(
            application: Application,
            onTagsFoundListener: IRuuviTagScanner.OnTagFoundListener,
            settings: ScannerSettings): BluetoothInteractor {
        if (!isInitialized) {
            bluetoothInteractor = BluetoothInteractor(application, onTagsFoundListener, settings)
        }
        return bluetoothInteractor
    }

    fun decode(id: String, rawData: String, rssi: Int): FoundSensor {
        val data = rawData.hexStringToByteArray()
        val tag = LeScanResult.from(id, null, data, rssi)
        return tag
    }

    private val HEX_CHARS = "0123456789ABCDEF"

    fun String.hexStringToByteArray() : ByteArray {
        val result = ByteArray(length / 2)

        for (i in 0 until length step 2) {
            val firstIndex = HEX_CHARS.indexOf(this[i]);
            val secondIndex = HEX_CHARS.indexOf(this[i + 1]);

            val octet = firstIndex.shl(4).or(secondIndex)
            result.set(i.shr(1), octet.toByte())
        }

        return result
    }
}