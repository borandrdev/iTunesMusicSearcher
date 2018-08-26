package demo.self.edu.itunesmusicsearch.di

import dagger.Module
import dagger.Provides
import demo.self.edu.itunesmusicsearch.api.TrackSearcher
import javax.inject.Singleton


@Module
class TrackSearcherModule(private val trackSearcher: TrackSearcher) {
    @Singleton
    @Provides
    fun provide(): TrackSearcher = trackSearcher
}