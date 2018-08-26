package demo.self.edu.itunesmusicsearch.di

import dagger.Subcomponent
import demo.self.edu.itunesmusicsearch.mvi.presenters.SearchResultsPresenter

@SearchResultsScreenScope
@Subcomponent(modules = [SearchTrackInteractorModule::class, FilterTracksInteractorModule::class])
interface SearchResultsComponent {
    fun inject(presenter: SearchResultsPresenter)
}