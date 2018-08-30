package demo.self.edu.itunesmusicsearch.mvi.models

import demo.self.edu.itunesmusicsearch.api.model.Track

class SearchResultsScreenModel {
    companion object {
        fun createUninitializedModel(): SearchResultsScreenModel = SearchResultsScreenModel()
        fun createStartSearchingModel(searchText: String):
                SearchResultsScreenModel = SearchResultsScreenModel(searchText, null)

        fun createOnSearchComplete(model: SearchResultsScreenModel, foundTracks: List<Track>?):
                SearchResultsScreenModel = SearchResultsScreenModel(model.searchText, foundTracks)

        fun createOnTracksFiltered(model: SearchResultsScreenModel, filterText: String?, filteredTracks: List<Track>?):
                SearchResultsScreenModel = SearchResultsScreenModel(model.searchText, filteredTracks, filterText)
    }

    val searchText: String?
    val filterText: String?
    val tracks: List<Track>?
    val isFilterApplied: Boolean
        get() = filterText?.isNotEmpty() ?: false

    private constructor() {
        searchText = null
        tracks = null
        filterText = null
    }

    private constructor(searchText: String?,
                        foundTracks: List<Track>?) {
        this.searchText = searchText
        this.tracks = foundTracks
        filterText = null
    }

    private constructor(searchText: String?,
                        foundTracks: List<Track>?,
                        filterText: String?) {
        this.searchText = searchText
        this.tracks = foundTracks
        this.filterText = filterText
    }

    fun hasLoadedTracks(): Boolean = tracks != null
}