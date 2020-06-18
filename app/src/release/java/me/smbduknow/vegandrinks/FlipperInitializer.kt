package me.smbduknow.vegandrinks

import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.soloader.SoLoader
import me.smbduknow.vegandrinks.BuildConfig

object FlipperInitializer {

    var interceptor: Interceptor = Interceptor { chain -> chain.proceed(chain.request()) }

    fun init(context: Context) {
        // Do nothing
    }
}
