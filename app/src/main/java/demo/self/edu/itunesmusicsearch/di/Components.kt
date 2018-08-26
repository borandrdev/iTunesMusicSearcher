package demo.self.edu.itunesmusicsearch.di

import android.content.Context
import demo.self.edu.itunesmusicsearch.api.TrackSearcher

class Components(private val appContext: Context) {
    private val trackSearcher = TrackSearcher()

    val appComponent: AppComponent = buildAppComponent()

    val searchResultsComponent: SearchResultsComponent =
            appComponent.buildSearchResultsComponent(SearchTrackInteractorModule(), FilterTracksInteractorModule())

    private fun buildAppComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .trackSearcherModule(TrackSearcherModule(trackSearcher))
                .build()
    }
}