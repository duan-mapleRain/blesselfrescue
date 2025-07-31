package com.arisaema.blesselfrescue

interface PermissionRequester {
    fun requestPermissions(onResult: (granted: Boolean) -> Unit)
}
