// MainScreen.kt (commonMain)
package com.arisaema.blesselfrescue.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arisaema.blesselfrescue.BluetoothScanner
import com.arisaema.blesselfrescue.model.BroadcastData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(scanner: BluetoothScanner) {
    var broadcastList by remember { mutableStateOf(listOf<BroadcastData>()) }

    LaunchedEffect(Unit) {
        scanner.startScanning { data ->
            broadcastList = broadcastList
                .filterNot { it.deviceId == data.deviceId } + data
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("湖南煤安  压缩氧设备读取系统") })
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).padding(16.dp)) {
            items(broadcastList) { data ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("设备编号: ${data.deviceId}")
                        Text("电量: ${data.battery} %")
                        Text("气压: ${data.pressure} Mpa")
                        Text("状态: ${data.status}")
                        Text("气量: ${data.gasAmount} %")
                    }
                }
            }
        }
    }
}

