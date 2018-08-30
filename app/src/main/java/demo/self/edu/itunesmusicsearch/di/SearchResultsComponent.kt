package demo.self.edu.itunesmusicsearch.di

import dagger.Subcomponent
import demo.self.edu.itunesmusicsearch.mvi.presenters.SearchResultsPresenter

@SearchResultsScreenScope
@Subcomponent(modules = [FilterTracksInteractorModule::class])
interface SearchResultsComponent {
    fun inject(presenter: SearchResultsPresenter)
}