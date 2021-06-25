package com.example.mydata.util

import androidx.fragment.app.Fragment
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions

fun Fragment.checkPermission(permission: String, granted: () -> Unit) {
    val mContext = context ?: return
    val options = Permissions.Options()
    options.setCreateNewTask(true)
    Permissions.check(
        mContext,
        arrayOf(permission),
        null,
        options,
        object : PermissionHandler() {
            override fun onGranted() {
                granted()
            }
        })
}