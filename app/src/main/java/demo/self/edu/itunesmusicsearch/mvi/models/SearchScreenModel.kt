package demo.self.edu.itunesmusicsearch.mvi.models

class SearchScreenModel(val text: String, val isSearching: Boolean = false) {
    companion object {
        fun createModelForSearchingState(model: SearchScreenModel): SearchScreenModel = SearchScreenModel(model.text, true)

        fun createModelForSearchFinished(model: SearchScreenModel): SearchScreenModel = SearchScreenModel(model.text, false)
    }

    val isTextEntered: Boolean
        get() = text.isNotEmpty()
}