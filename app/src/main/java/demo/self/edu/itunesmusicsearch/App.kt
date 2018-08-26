package demo.self.edu.itunesmusicsearch

import android.app.Application
import demo.self.edu.itunesmusicsearch.di.Components

class App : Application() {

    val diComponents: Components
        get() = components

    private lateinit var components: Components


    override fun onCreate() {
        super.onCreate()
        components = Components(this)
    }
}