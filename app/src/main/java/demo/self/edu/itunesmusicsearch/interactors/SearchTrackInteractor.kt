package demo.self.edu.itunesmusicsearch.interactors

import android.util.Log
import demo.self.edu.itunesmusicsearch.api.CompleteListener
import demo.self.edu.itunesmusicsearch.api.TrackSearcher
import demo.self.edu.itunesmusicsearch.api.model.Track
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchTrackInteractor(private val trackSearcher: TrackSearcher) {
    companion object {
        private val TAG: String = SearchTrackInteractor::class.java.simpleName
    }

    private var text: String? = null
    private var foundTracks: List<Track>? = null

    val lastSearchText: String?
        get() = text
    val lastFoundTracks: List<Track>?
        get() = foundTracks


    fun searchTracksForText(text: String, completeListener: CompleteListener<List<Track>>) {
        trackSearcher.searchTracksForText(text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            this.text = text
                            this.foundTracks = it
                            completeListener.onCompelete(it)
                        }
                        ,
                        {
                            Log.e(TAG, "Failed to search", it)
                            completeListener.onCompelete(null)
                        })

    }
}