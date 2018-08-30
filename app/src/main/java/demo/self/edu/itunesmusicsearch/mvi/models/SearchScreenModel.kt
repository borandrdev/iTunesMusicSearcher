package demo.self.edu.itunesmusicsearch.mvi.models

import demo.self.edu.itunesmusicsearch.api.model.Track

class SearchScreenModel(val text: String, val isSearching: Boolean = false, val hasError: Boolean = false) {
    companion object {
        fun createModelForIdleState(): SearchScreenModel = SearchScreenModel("")

        fun createModelForTextEnterState(model: SearchScreenModel, text: String): SearchScreenModel = SearchScreenModel(text, false, model.hasError)

        fun createModelForSearchingState(model: SearchScreenModel): SearchScreenModel = SearchScreenModel(model.text, true)

        fun createModelForSearchFinished(model: SearchScreenModel): SearchScreenModel = SearchScreenModel(model.text, false, false)

        fun createModelForSearchError(model: SearchScreenModel): SearchScreenModel = SearchScreenModel(model.text, false, true)
    }

    val isTextEntered: Boolean
        get() = text.isNotEmpty()
}