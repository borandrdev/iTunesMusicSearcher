package demo.self.edu.itunesmusicsearch.di

import dagger.Module
import dagger.Provides
import demo.self.edu.itunesmusicsearch.api.TrackSearcher
import demo.self.edu.itunesmusicsearch.interactors.SearchTrackInteractor

@Module
class SearchTrackInteractorModule {
    @SearchResultsScreenScope
    @Provides
    fun provide(trackSearcher: TrackSearcher): SearchTrackInteractor = SearchTrackInteractor(trackSearcher)
}