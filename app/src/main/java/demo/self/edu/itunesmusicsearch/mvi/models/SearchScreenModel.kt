package demo.self.edu.itunesmusicsearch.mvi.models

class SearchScreenModel(val text: String) {
    val isTextEntered: Boolean
        get() = text.isNotEmpty()
}