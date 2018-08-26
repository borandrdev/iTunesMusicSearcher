package demo.self.edu.itunesmusicsearch.mvi.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import demo.self.edu.itunesmusicsearch.mvi.views.SearchMvpView

@InjectViewState
class SearchPresenter : MvpPresenter<SearchMvpView>() {

    private var searchText: String = ""

    fun onSearchButtonClicked() {
        if (searchText.isNotEmpty()) {
            viewState.startMusicSearchingForText(searchText)
        }
    }


    fun onSearchTextChanged(text: String) {
        this.searchText = text
    }
}