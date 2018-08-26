package demo.self.edu.itunesmusicsearch.mvi.models

import demo.self.edu.itunesmusicsearch.api.model.Track

class SearchResultsScreenModel {
    companion object {
        fun createUninitializedModel(): SearchResultsScreenModel = SearchResultsScreenModel()
        fun createStartSearchingModel(searchText: String):
                SearchResultsScreenModel = SearchResultsScreenModel(searchText, true, null)

        fun createOnSearchComplete(model: SearchResultsScreenModel, foundTracks: List<Track>?):
                SearchResultsScreenModel = SearchResultsScreenModel(model.searchText, false, foundTracks)

        fun createOnTracksFiltered(model: SearchResultsScreenModel, filterText:String?, filteredTracks: List<Track>?):
                SearchResultsScreenModel = SearchResultsScreenModel(model.searchText, false, filteredTracks, filterText)
    }

    val searchText: String?
    val filterText: String?
    val isLoading: Boolean
    val tracks: List<Track>?

    private constructor() {
        searchText = null
        isLoading = false
        tracks = null
        filterText = null
    }

    private constructor(searchText: String?,
                        isLoading: Boolean,
                        foundTracks: List<Track>?) {
        this.searchText = searchText
        this.isLoading = isLoading
        this.tracks = foundTracks
        filterText = null
    }

    private constructor(searchText: String?,
                        isLoading: Boolean,
                        foundTracks: List<Track>?,
                        filterText: String?) {
        this.searchText = searchText
        this.isLoading = isLoading
        this.tracks = foundTracks
        this.filterText = filterText
    }

    fun hasLoadedTracks(): Boolean = tracks != null
}