package demo.self.edu.itunesmusicsearch.di

import dagger.Subcomponent
import demo.self.edu.itunesmusicsearch.mvi.presenters.SearchPresenter

@SearchScreenScope
@Subcomponent
interface SearchScreenComponent {
    fun inject(presenter: SearchPresenter)
}