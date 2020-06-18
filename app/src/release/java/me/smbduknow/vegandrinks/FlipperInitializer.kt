package me.smbduknow.vegandrinks

import android.content.Context
import okhttp3.Interceptor

object FlipperInitializer {

    var interceptor: Interceptor = Interceptor { chain -> chain.proceed(chain.request()) }

    fun init(context: Context) {
        // Do nothing
    }
}
