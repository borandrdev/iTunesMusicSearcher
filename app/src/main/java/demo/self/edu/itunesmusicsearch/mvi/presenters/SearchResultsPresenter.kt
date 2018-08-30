package demo.self.edu.itunesmusicsearch.mvi.presenters

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import demo.self.edu.itunesmusicsearch.di.SearchResultsComponent
import demo.self.edu.itunesmusicsearch.interactors.FilterTracksInteractor
import demo.self.edu.itunesmusicsearch.interactors.SearchTrackInteractor
import demo.self.edu.itunesmusicsearch.mvi.models.SearchResultsScreenModel
import demo.self.edu.itunesmusicsearch.mvi.views.SearchResultsMvpView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class SearchResultsPresenter(searchResultsComponent: SearchResultsComponent) : MvpPresenter<SearchResultsMvpView>() {
    companion object {
        @Suppress("unused")
        val TAG = SearchResultsPresenter::class.java.simpleName!!
    }

    private var model = SearchResultsScreenModel.createUninitializedModel()

    @Inject
    lateinit var filterInteractor: FilterTracksInteractor
    @Inject
    lateinit var searchInteractor: SearchTrackInteractor
    @Inject
    lateinit var router: Router

    init {
        searchResultsComponent.inject(this)
    }

    fun init() {
        val lastSearchText = searchInteractor.lastSearchText
        if (lastSearchText != null) {
            filterInteractor.allTracks = searchInteractor.lastFoundTracks
            model = SearchResultsScreenModel.createStartSearchingModel(lastSearchText)
            model = SearchResultsScreenModel.createOnSearchComplete(model, searchInteractor.lastFoundTracks)
            view(model)
        }
    }

    fun onFilterTextChanged(text: CharSequence?) {
        Log.v(TAG, "filter: $text")

        val allTracks = model.tracks
        if ((text != null) && model.hasLoadedTracks() && (allTracks != null)) {
            val filterText = text.toString()
            val filteredTracks = filterInteractor.getFilteredTracks(filterText)
            model = SearchResultsScreenModel.createOnTracksFiltered(model, filterText, filteredTracks)
            view(model)
        }
    }


    fun onBackPressed() {
        if (model.isFilterApplied) {
            val filterText = ""
            onFilterTextChanged(filterText)
        } else {
            router.exit()
        }
    }

    private fun view(model: SearchResultsScreenModel) {
        viewState.render(model)
    }
}