package com.arisaema.blesselfrescue.model

data class BroadcastData(
    val deviceId: Int,
    val pressure: Float,
    val status: String,
    val battery: Int,
    val gasAmount: Float
)
