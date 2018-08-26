package demo.self.edu.itunesmusicsearch.api

import android.util.Log
import demo.self.edu.itunesmusicsearch.api.model.SearchResponse
import demo.self.edu.itunesmusicsearch.api.model.Track
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class TrackSearcher {
    private companion object {
        @Suppress("unused")
        private val TAG = TrackSearcher::class.java.simpleName
        private const val BASE_URL = "https://itunes.apple.com/"
        private const val SEARCH_RESULT_TYPE_SONG = "song"
    }

    private var iTunesApiImpl: ITunesApi
    private val searchResultsLimit = 30


    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        iTunesApiImpl = retrofit.create<ITunesApi>(ITunesApi::class.java)
    }

    fun searchTracksForText(text: String): Observable<List<Track>> {
        return iTunesApiImpl.searchByText(text, searchResultsLimit).map { searchResponse ->
            Log.v(TAG, "Result count: " + searchResponse.resultCount)

            val tracks = getTracksFromResponse(searchResponse)
            return@map tracks ?: error("Null result list")
        }
    }

    private fun getTracksFromResponse(searchResponse: SearchResponse): ArrayList<Track>? {
        val searchResults = searchResponse.results
        var tracks: ArrayList<Track>? = null
        if (searchResults != null) {
            tracks = ArrayList()
            tracks.addAll(
                    searchResults
                            .filter { it.kind?.equals(SEARCH_RESULT_TYPE_SONG) ?: false })
        }
        return tracks
    }

//    fun searchTracksForText(text: String, completeListener: CompleteListener<List<SearchResult>>) {
//        iTunesApiImpl.searchByText(text, searchResultsLimit).enqueue(object : Callback<SearchResponse> {
//            override fun onFailure(call: Call<SearchResponse>?, t: Throwable?) {
//                Log.e(TAG, "Failed to load data", t)
//                completeListener.onCompelete(null)
//            }
//
//            override fun onResponse(call: Call<SearchResponse>?, response: Response<SearchResponse>?) {
//                Log.v(TAG, "response: " + response?.code())
//                val isSuccess = response?.isSuccessful
//                val searchResponse = if ((isSuccess != null) && isSuccess) response.body() else null
//                Log.v(TAG, "Results count: " + searchResponse?.resultCount)
//                completeListener.onCompelete(searchResponse?.results)
//            }
//        })
//    }
}