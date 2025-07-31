package com.arisaema.blesselfrescue

import android.util.Log
import com.arisaema.blesselfrescue.model.BroadcastData


//5A A5 包头
    // 01 功能码
    // 07 数据长度
    // 00 00 00 02 设备编号
    // 00 气压高位
    // 00 气压低位
    // 01 设备状态
    // 57 电量
    // 00 气量高位
    // 00 气量地位
    object BroadcastParser {

        fun parse(raw: ByteArray): BroadcastData? {
            val index = raw.indexOfSequence(byteArrayOf(0x5A.toByte(), 0xA5.toByte()))
            if (index < 0 || raw.size < index + 14) return null

            val deviceId = ((raw[index + 4].toInt() and 0xFF) shl 24) or
                    ((raw[index + 5].toInt() and 0xFF) shl 16) or
                    ((raw[index + 6].toInt() and 0xFF) shl 8) or
                    (raw[index + 7].toInt() and 0xFF)
            val pressure = parsePressure(raw[index + 8],raw[index + 9]);


            val statusCode = raw[index + 10].toInt() and 0xFF
            val status = when (statusCode) {
                0 -> "正常"
                1 -> "异常"
                2 -> "过期"
                3 -> "低压"
                else -> "未知状态($statusCode)"
            }

            val battery = raw[index + 11].toInt() and 0xFF

            val gasAmount=parseGasAmount(raw[index + 12], raw[index + 13])

            return BroadcastData(
                deviceId = deviceId,
                pressure = pressure,
                status = status,
                battery = battery,
                gasAmount = gasAmount
            )
        }

        private fun ByteArray.indexOfSequence(sequence: ByteArray): Int {
            outer@ for (i in 0 until this.size - sequence.size + 1) {
                for (j in sequence.indices) {
                    if (this[i + j] != sequence[j]) continue@outer
                }
                return i
            }
            return -1
        }

    private fun parsePressure(high: Byte, low: Byte): Float {
        val raw = ((high.toInt() and 0xFF) shl 8) or (low.toInt() and 0xFF)
        return raw / 250f
    }

    private fun parseGasAmount(high: Byte, low: Byte): Float {
        val raw = ((high.toInt() and 0xFF) shl 8) or (low.toInt() and 0xFF)
        return raw / 10f
    }
    }

