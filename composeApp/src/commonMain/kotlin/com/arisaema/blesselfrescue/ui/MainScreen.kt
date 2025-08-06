// MainScreen.kt (commonMain)
package com.arisaema.blesselfrescue.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.arisaema.blesselfrescue.BluetoothScanner
import com.arisaema.blesselfrescue.model.BroadcastData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(scanner: BluetoothScanner) {
    var boundDevices = remember { listOf<BroadcastData>() }
    boundDevices = listOf(
        //BroadcastData("Device-001", 88F, "正常", 75, 30F), BroadcastData("Device-002", 60F, "异常", 60, 30F)
    )

    // 假数据：扫描到的设备
    val scannedDevices = listOf(
        BroadcastData("Device-003", 77F, "正常", 65, 30F),
        BroadcastData("Device-004", 50F, "异常", 40, 30F),
        BroadcastData("Device-005", 95F, "正常", 90, 30F)
    )
//    val boundDevices = remember { listOf<BroadcastData>() } // TODO: 替换为真实绑定设备来源
//    val scannedDevices = remember { mutableStateListOf<BroadcastData>() }

//    LaunchedEffect(Unit) {
//        scanner.startScanning { data ->
//            val index = scannedDevices.indexOfFirst { it.deviceId == data.deviceId }
//            if (index != -1) {
//                scannedDevices[index] = data
//            } else {
//                scannedDevices.add(data)
//            }
//        }
//    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("湖南煤安  压缩氧设备读取系统") })
        }) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize()
        ) {

            // ✅ 绑定设备卡片
            Text("已绑定设备", style = MaterialTheme.typography.titleMedium)
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    if (boundDevices.isEmpty()) {
                        Text("暂无绑定设备")
                    } else {
                        boundDevices.forEach { device ->
                            DeviceBondInfoView(device)
                            Divider(modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ✅ 扫描设备部分：只替换 LazyColumn → LazyVerticalGrid
            Text("扫描到的设备", style = MaterialTheme.typography.titleMedium)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(top = 8.dp, bottom = 16.dp)
            ) {
                items(scannedDevices) { device ->
                    DeviceScanInfoView(device)
                }
            }
        }
    }
}

// 🔄 单个设备信息显示组件
@Composable
fun DeviceBondInfoView(data: BroadcastData) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("设备编号: ${data.deviceId}")
            Text("已绑定")
            Text("电量: ${data.battery} %")
            Text("气压: ${data.pressure} Mpa")
            Text("状态: ${data.status}")
            Text("气量: ${data.gasAmount} %")
        }
    }
}

@Composable
fun DeviceScanInfoView(data: BroadcastData) {
    Card(
        modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0E254E)
        )
    ) {
        CompositionLocalProvider(LocalContentColor provides Color.White) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(data.deviceId)
                Text("电量: ${data.battery} %")
                Text("气压: ${data.pressure} Mpa")
                Text("状态: ${data.status}")
                Text("气量: ${data.gasAmount} %")
            }
        }

    }
}




