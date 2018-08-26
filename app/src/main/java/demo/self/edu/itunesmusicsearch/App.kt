package demo.self.edu.itunesmusicsearch

import android.app.Application
import demo.self.edu.itunesmusicsearch.di.Components
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router


class App : Application() {

    val diComponents: Components
        get() = components

    val navigatorHolder: NavigatorHolder
        get() = cicerone.navigatorHolder
    val router: Router
        get() = cicerone.router

    private lateinit var components: Components
    private val cicerone: Cicerone<Router> = Cicerone.create()


    override fun onCreate() {
        super.onCreate()

        components = Components(this)
    }
}