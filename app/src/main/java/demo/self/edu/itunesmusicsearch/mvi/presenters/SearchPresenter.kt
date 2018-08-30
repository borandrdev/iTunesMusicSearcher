package demo.self.edu.itunesmusicsearch.mvi.presenters

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
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
        const val DELAY_SEARCH_COMPLETED_SCREEN_UPDATE_MS = 100L
    }

    @Suppress("unused")
    @Inject
    lateinit var appContext: Context
    @Inject
    lateinit var searchInteractor: SearchTrackInteractor
    @Inject
    lateinit var router: Router

    private var model = SearchScreenModel.createModelForIdleState()


    init {
        appComponent.inject(this)
    }


    fun onSearchTextChanged(text: String) {
        model = SearchScreenModel.createModelForTextEnterState(model, text)
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
        if (foundTracks == null) {
            showSearchFailedScreenState()
        } else {
            onSearchCompletedSuccessfully()
        }
    }

    private fun showSearchFailedScreenState() {
        model = SearchScreenModel.createModelForSearchError(model)
        view(model)
    }

    private fun onSearchCompletedSuccessfully() {
        router.navigateTo(SEARCH_RESULTS_SCREEN, model.text)

        model = SearchScreenModel.createModelForSearchFinished(model)
        Handler(Looper.getMainLooper()).postDelayed({
            view(model)
        }, DELAY_SEARCH_COMPLETED_SCREEN_UPDATE_MS)
    }

    private fun view(model: SearchScreenModel) {
        viewState.render(model)
    }
}