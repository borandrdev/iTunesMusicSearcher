package demo.self.edu.itunesmusicsearch.di

import dagger.Component
import demo.self.edu.itunesmusicsearch.mvi.presenters.SearchPresenter
import javax.inject.Singleton

@Singleton
@Component(modules = [TrackSearcherModule::class, CiceroneRouterModule::class])
interface AppComponent {
    fun buildSearchResultsComponent(searchTrackInteractorModule: SearchTrackInteractorModule,
                                    filterTracksInteractorModule: FilterTracksInteractorModule): SearchResultsComponent

    fun inject(presenter: SearchPresenter)
}