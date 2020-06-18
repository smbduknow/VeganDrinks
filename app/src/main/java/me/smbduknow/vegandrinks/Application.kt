package me.smbduknow.vegandrinks

class Application : android.app.Application() {

    companion object {
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        FlipperInitializer.init(this)
    }
}
