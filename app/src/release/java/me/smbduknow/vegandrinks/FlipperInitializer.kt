package me.smbduknow.vegandrinks

import android.content.Context
import me.smbduknow.vegandrinks.BuildConfig

object FlipperInitializer {

    var interceptor: Interceptor = Interceptor { chain -> chain.proceed(chain.request()) }

    fun init(context: Context) {
        // Do nothing
    }
}
