package demo.self.edu.itunesmusicsearch.di

import demo.self.edu.itunesmusicsearch.App
import demo.self.edu.itunesmusicsearch.api.TrackSearcher

class Components(private val app: App) {
    private val trackSearcher = TrackSearcher()

    private val appComponent: AppComponent = buildAppComponent()

    val searchResultsScreenComponent: SearchResultsComponent =
            appComponent.buildSearchResultsScreenComponent(FilterTracksInteractorModule())

    val searchScreenComponent: SearchScreenComponent =
            appComponent.buildSearchScreenComponent()

    private fun buildAppComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .appContextModule(AppContextModule(app))
                .trackSearcherModule(TrackSearcherModule(trackSearcher))
                .searchTrackInteractorModule(SearchTrackInteractorModule())
                .ciceroneRouterModule(CiceroneRouterModule(app.router))
                .build()
    }
}