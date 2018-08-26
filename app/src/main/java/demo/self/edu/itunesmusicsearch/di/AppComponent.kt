package demo.self.edu.itunesmusicsearch.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TrackSearcherModule::class])
interface AppComponent {
    fun buildSearchResultsComponent(searchTrackInteractorModule: SearchTrackInteractorModule,
                                    filterTracksInteractorModule: FilterTracksInteractorModule): SearchResultsComponent
}