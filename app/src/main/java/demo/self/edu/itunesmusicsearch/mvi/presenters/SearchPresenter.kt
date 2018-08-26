package demo.self.edu.itunesmusicsearch.mvi.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import demo.self.edu.itunesmusicsearch.di.AppComponent
import demo.self.edu.itunesmusicsearch.mvi.views.SearchMvpView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class SearchPresenter(appComponent: AppComponent) : MvpPresenter<SearchMvpView>() {
    companion object {
        const val SEARCH_RESULTS_SCREEN = "SEARCH_RESULTS"
    }


    @Inject
    lateinit var router: Router

    private var searchText: String = ""


    init {
        appComponent.inject(this)
    }


    fun onSearchTextChanged(text: String) {
        this.searchText = text
    }

    fun onSearchButtonClicked() {
        if (searchText.isNotEmpty()) {
            router.navigateTo(SEARCH_RESULTS_SCREEN, searchText)
        }
    }
}