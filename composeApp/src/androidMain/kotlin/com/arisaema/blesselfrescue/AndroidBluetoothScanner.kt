package com.arisaema.blesselfrescue

import android.annotation.SuppressLint
import android.bluetooth.BluetoothManager
import android.bluetooth.le.*
import android.content.Context
import android.os.ParcelUuid
import android.util.Log
import com.arisaema.blesselfrescue.model.BroadcastData
import java.util.*

class AndroidBluetoothScanner(
    private val context: Context
) : BluetoothScanner {

    private val adapter by lazy {
        (context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
    }

    private val scanner by lazy { adapter?.bluetoothLeScanner }
    private var callback: ScanCallback? = null

    // ✅ 替换为你产品的 128-bit UUID
    private val targetServiceUuid = ParcelUuid.fromString("49535343-fe7d-4ae5-8fa9-9fafd205e455")

    @SuppressLint("MissingPermission")
    override fun startScanning(onDataReceived: (BroadcastData) -> Unit) {
        callback = object : ScanCallback() {
            override fun onScanResult(type: Int, result: ScanResult) {
                result.scanRecord?.bytes?.let { raw ->
                    val data = BroadcastParser.parse(raw)
                    if (data != null) {
                        onDataReceived(data)
                    }
                }
            }

            override fun onScanFailed(errorCode: Int) {
                Log.e("hnma", "Scan failed: $errorCode")
            }
        }

        // ✅ 加入过滤器
        val filters = listOf(
            ScanFilter.Builder()
                .setServiceUuid(targetServiceUuid)
                .build()
        )

        val settings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .build()

        scanner?.startScan(filters, settings, callback)
    }

    @SuppressLint("MissingPermission")
    override fun stopScanning() {
        scanner?.stopScan(callback)
    }
}

// ✅ 十六进制打印工具
fun ByteArray.toHexString(): String = joinToString(" ") { "%02X".format(it) }
