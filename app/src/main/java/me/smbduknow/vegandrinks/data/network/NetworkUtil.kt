package me.smbduknow.vegandrinks.data.network

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtil {

    fun isConnectionAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}