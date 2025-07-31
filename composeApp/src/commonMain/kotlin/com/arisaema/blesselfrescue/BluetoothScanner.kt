package com.arisaema.blesselfrescue

import com.arisaema.blesselfrescue.model.BroadcastData

interface BluetoothScanner {
    fun startScanning(onDataReceived: (BroadcastData) -> Unit)
    fun stopScanning()
}