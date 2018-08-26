package demo.self.edu.itunesmusicsearch.interactors

import demo.self.edu.itunesmusicsearch.api.CompleteListener
import demo.self.edu.itunesmusicsearch.api.TrackSearcher
import demo.self.edu.itunesmusicsearch.api.model.Track
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchTrackInteractor(private val trackSearcher: TrackSearcher) {

    fun searchTracksForText(text: String, completeListener: CompleteListener<List<Track>>) {
        trackSearcher.searchTracksForText(text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    completeListener.onCompelete(it)
                }

    }
}