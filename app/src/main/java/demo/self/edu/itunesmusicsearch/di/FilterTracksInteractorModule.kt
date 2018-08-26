package demo.self.edu.itunesmusicsearch.di

import dagger.Module
import dagger.Provides
import demo.self.edu.itunesmusicsearch.interactors.FilterTracksInteractor

@Module
class FilterTracksInteractorModule {
    @SearchResultsScreenScope
    @Provides
    fun provide(): FilterTracksInteractor = FilterTracksInteractor()
}