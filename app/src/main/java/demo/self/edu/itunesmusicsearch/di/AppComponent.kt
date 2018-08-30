package demo.self.edu.itunesmusicsearch.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TrackSearcherModule::class, CiceroneRouterModule::class, SearchTrackInteractorModule::class, AppContextModule::class])
interface AppComponent {
    fun buildSearchResultsScreenComponent(filterTracksInteractorModule: FilterTracksInteractorModule): SearchResultsComponent

    fun buildSearchScreenComponent(): SearchScreenComponent
}