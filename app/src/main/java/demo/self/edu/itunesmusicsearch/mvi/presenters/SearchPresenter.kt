package demo.self.edu.itunesmusicsearch.mvi.presenters

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import demo.self.edu.itunesmusicsearch.R
import demo.self.edu.itunesmusicsearch.api.CompleteListener
import demo.self.edu.itunesmusicsearch.api.model.Track
import demo.self.edu.itunesmusicsearch.di.SearchScreenComponent
import demo.self.edu.itunesmusicsearch.interactors.SearchTrackInteractor
import demo.self.edu.itunesmusicsearch.mvi.models.SearchScreenModel
import demo.self.edu.itunesmusicsearch.mvi.views.SearchMvpView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class SearchPresenter(appComponent: SearchScreenComponent) : MvpPresenter<SearchMvpView>() {
    companion object {
        const val SEARCH_RESULTS_SCREEN = "SEARCH_RESULTS"
    }

    @Inject
    lateinit var appContext: Context
    @Inject
    lateinit var searchInteractor: SearchTrackInteractor
    @Inject
    lateinit var router: Router

    private var model = SearchScreenModel("")


    init {
        appComponent.inject(this)
    }


    fun onSearchTextChanged(text: String) {
        model = SearchScreenModel(text)
        view(model)
    }

    fun onSearchButtonClicked() {
        if (model.isTextEntered && !model.isSearching) {
            model = SearchScreenModel.createModelForSearchingState(model)
            view(model)

            startSearch(model.text)
        }
    }


    private fun startSearch(searchQuery: String) {
        searchInteractor.searchTracksForText(searchQuery, object : CompleteListener<List<Track>> {
            override fun onCompelete(result: List<Track>?) {
                onSearchComplete(result)
            }
        })
    }

    private fun onSearchComplete(foundTracks: List<Track>?) {
        model = SearchScreenModel.createModelForSearchFinished(model)
        view(model)
        if (foundTracks == null) {
            router.showSystemMessage(appContext.getString(R.string.err_failed_to_search))
        } else {
            router.navigateTo(SEARCH_RESULTS_SCREEN, model.text)
        }
    }

    private fun view(model: SearchScreenModel) {
        viewState.render(model)
    }
}