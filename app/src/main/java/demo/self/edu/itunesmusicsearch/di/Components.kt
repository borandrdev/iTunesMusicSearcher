package demo.self.edu.itunesmusicsearch.di

import demo.self.edu.itunesmusicsearch.App
import demo.self.edu.itunesmusicsearch.api.TrackSearcher

class Components(private val app: App) {
    private val trackSearcher = TrackSearcher()

    val appComponent: AppComponent = buildAppComponent()

    val searchResultsComponent: SearchResultsComponent =
            appComponent.buildSearchResultsComponent(SearchTrackInteractorModule(), FilterTracksInteractorModule())

    private fun buildAppComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .trackSearcherModule(TrackSearcherModule(trackSearcher))
                .ciceroneRouterModule(CiceroneRouterModule(app.router))
                .build()
    }
}