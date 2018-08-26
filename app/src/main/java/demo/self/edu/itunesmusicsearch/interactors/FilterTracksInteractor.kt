package demo.self.edu.itunesmusicsearch.interactors

import demo.self.edu.itunesmusicsearch.api.model.Track

class FilterTracksInteractor {
    var allTracks: List<Track>? = null

    fun getFilteredTracks(text: String, matchIgnoringCase: Boolean = true): List<Track>? {
        val tracksToFilter = allTracks
        return if (!text.isBlank() && (tracksToFilter != null)) {
            val tracksMatchingByName = tracksToFilter.filter {
                val trackName = it.trackName
                (trackName != null) && (trackName.contains(text, matchIgnoringCase))
            }
            val tracksMatchingByInfo = tracksToFilter.filter {
                it.trackInfo.contains(text, matchIgnoringCase)
            }
            val matchingTracks = ArrayList<Track>()
            matchingTracks.addAll(tracksMatchingByName)
            matchingTracks.addAll(tracksMatchingByInfo)
            matchingTracks
        } else {
            tracksToFilter
        }
    }
}