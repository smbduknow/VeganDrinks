package me.smbduknow.vegandrinks

import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import okhttp3.Interceptor

object FlipperInitializer {

    var interceptor: Interceptor = Interceptor { chain -> chain.proceed(chain.request()) }

    fun init(context: Context) {
        SoLoader.init(context, false)

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(context)) {
            val networkPlugin = NetworkFlipperPlugin()
            interceptor = FlipperOkhttpInterceptor(networkPlugin, true)

            val client = AndroidFlipperClient.getInstance(context)
            // Layout Inspector
            client.addPlugin(InspectorFlipperPlugin(context, DescriptorMapping.withDefaults()))
            // Network
            client.addPlugin(networkPlugin)

            client.start()
        }
    }
}
