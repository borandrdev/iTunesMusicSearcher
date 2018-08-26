package demo.self.edu.itunesmusicsearch.mvi.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import demo.self.edu.itunesmusicsearch.api.CompleteListener
import demo.self.edu.itunesmusicsearch.api.model.Track
import demo.self.edu.itunesmusicsearch.di.SearchResultsComponent
import demo.self.edu.itunesmusicsearch.interactors.FilterTracksInteractor
import demo.self.edu.itunesmusicsearch.interactors.SearchTrackInteractor
import demo.self.edu.itunesmusicsearch.mvi.models.SearchResultsScreenModel
import demo.self.edu.itunesmusicsearch.mvi.views.SearchResultsMvpView
import javax.inject.Inject

@InjectViewState
class SearchResultsPresenter(searchResultsComponent: SearchResultsComponent) : MvpPresenter<SearchResultsMvpView>() {

    private var model = SearchResultsScreenModel.createUninitializedModel()

    @Inject
    lateinit var filterInteractor: FilterTracksInteractor
    @Inject
    lateinit var searchInteractor: SearchTrackInteractor

    init {
        searchResultsComponent.inject(this)
    }

    fun searchTracksForText(text: String?) {
        if ((text != null) && !model.isLoading) {
            model = SearchResultsScreenModel.createStartSearchingModel(text)
            view(model)

            searchTracks(text)
        }
    }

    fun filterTracksWithText(text: CharSequence?) {
        val allTracks = model.tracks
        if ((text != null) && model.hasLoadedTracks() && (allTracks != null)) {
            val filterText = text.toString()
            val filteredTracks = filterInteractor.getFilteredTracks(filterText)
            model = SearchResultsScreenModel.createOnTracksFiltered(model, filterText, filteredTracks)
            view(model)
        }
    }

    private fun view(model: SearchResultsScreenModel) {
        viewState.render(model)
    }

    private fun searchTracks(searchQuery: String) {
        searchInteractor.searchTracksForText(searchQuery, object : CompleteListener<List<Track>> {
            override fun onCompelete(result: List<Track>?) {
                onSearchComplete(result)
            }
        })
    }

    private fun onSearchComplete(foundTracks: List<Track>?) {
        if ((foundTracks != null) && foundTracks.isNotEmpty()) {
            filterInteractor.allTracks = foundTracks
        }
        model = SearchResultsScreenModel.createOnSearchComplete(model, foundTracks)
        view(model)
    }
}