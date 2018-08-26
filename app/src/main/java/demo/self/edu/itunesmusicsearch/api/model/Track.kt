package demo.self.edu.itunesmusicsearch.api.model

class Track {
    var trackName: String? = null
    var collectionName: String? = null
    var artistName: String? = null
    var kind: String? = null

    val trackInfo: String
        get() = "$artistName;$collectionName"
}