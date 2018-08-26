package demo.self.edu.itunesmusicsearch.di

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
class CiceroneRouterModule(private val router: Router) {
    @Singleton
    @Provides
    fun provides(): Router = router
}