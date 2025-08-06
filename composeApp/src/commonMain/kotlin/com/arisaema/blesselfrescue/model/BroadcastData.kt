package com.arisaema.blesselfrescue.model

data class BroadcastData(
    val deviceId: String,
    val pressure: Float,
    val status: String,
    val battery: Int,
    val gasAmount: Float
)
