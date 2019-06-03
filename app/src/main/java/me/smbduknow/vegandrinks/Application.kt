package me.smbduknow.vegandrinks

import io.reactivex.plugins.RxJavaPlugins

class Application : android.app.Application() {

    companion object {

        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        RxJavaPlugins.setErrorHandler {  }
    }
}