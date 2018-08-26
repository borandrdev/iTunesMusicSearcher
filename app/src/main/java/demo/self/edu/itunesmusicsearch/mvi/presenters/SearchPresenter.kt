package demo.self.edu.itunesmusicsearch.mvi.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import demo.self.edu.itunesmusicsearch.di.AppComponent
import demo.self.edu.itunesmusicsearch.mvi.models.SearchScreenModel
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

    private var model = SearchScreenModel("")


    init {
        appComponent.inject(this)
    }


    fun onSearchTextChanged(text: String) {
        model = SearchScreenModel(text)
        renderModel()

    }

    fun onSearchButtonClicked() {
        if (model.isTextEntered) {
            router.navigateTo(SEARCH_RESULTS_SCREEN, model.text)
        }
    }

    private fun renderModel() {
        viewState.render(model)
    }
}