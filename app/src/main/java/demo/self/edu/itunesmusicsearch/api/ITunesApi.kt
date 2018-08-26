package demo.self.edu.itunesmusicsearch.api

import demo.self.edu.itunesmusicsearch.api.model.SearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesApi {
    @GET("search")
    fun searchByText(@Query("term") searchText: String, @Query("limit") limit: Int): Observable<SearchResponse>
}