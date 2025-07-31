package com.arisaema.blesselfrescue

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.arisaema.blesselfrescue.ui.MainScreen

class MainActivity : ComponentActivity() {

    private lateinit var scanner: AndroidBluetoothScanner
    private lateinit var permissionRequester: AndroidPermissionRequester

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scanner = AndroidBluetoothScanner(this)
        permissionRequester = AndroidPermissionRequester(this)

        setContent {
            var granted by remember { mutableStateOf<Boolean?>(null) }

            // 只请求一次权限
            LaunchedEffect(Unit) {
                permissionRequester.requestPermissions { isGranted ->
                    granted = isGranted
                }
            }

            when (granted) {
                true -> MainScreen(scanner)
                false -> {
                    Toast.makeText(this, "权限不足，无法使用", Toast.LENGTH_LONG).show()
                    finish()
                }
                null -> {
                    // 等待授权时显示
                    Text("等待权限授权...")
                }
            }
        }
    }
}
