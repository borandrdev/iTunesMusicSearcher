package demo.self.edu.itunesmusicsearch.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppContextModule(private val appContext: Context) {
    @Singleton
    @Provides
    fun provide(): Context = appContext
}
