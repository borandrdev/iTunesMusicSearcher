package demo.self.edu.itunesmusicsearch.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import demo.self.edu.itunesmusicsearch.App
import demo.self.edu.itunesmusicsearch.R
import demo.self.edu.itunesmusicsearch.adapters.TrackAdapter
import demo.self.edu.itunesmusicsearch.interactors.ui.RxSearchObservable
import demo.self.edu.itunesmusicsearch.mvi.models.SearchResultsScreenModel
import demo.self.edu.itunesmusicsearch.mvi.presenters.SearchResultsPresenter
import demo.self.edu.itunesmusicsearch.mvi.views.SearchResultsMvpView
import kotlinx.android.synthetic.main.activity_search_results.*
import kotlinx.android.synthetic.main.toolbar_search_result.*
import ru.terrakok.cicerone.android.SupportAppNavigator
import java.util.concurrent.TimeUnit

class SearchResultsActivity :
        MvpAppCompatActivity()
        , SearchResultsMvpView {

    companion object {
        const val ARG_STR_SEARCH_QUERY = "search_query"
        @Suppress("unused")
        val TAG = SearchResultsActivity::class.java.simpleName!!
    }

    @InjectPresenter
    lateinit var presenter: SearchResultsPresenter


    @ProvidePresenter
    fun providePresenter(): SearchResultsPresenter {
        val diComponent = (application as App).diComponents.searchResultsComponent
        return SearchResultsPresenter(diComponent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        if (savedInstanceState == null) {
            searchTracksForTextInIntentExtra()
        }

        initView()
    }

    override fun onResume() {
        super.onResume()
        (application as App).navigatorHolder.setNavigator(SearchResultsNavigator(this))
    }

    override fun onPause() {
        super.onPause()
        (application as App).navigatorHolder.removeNavigator()
    }

    private fun searchTracksForTextInIntentExtra() {
        var searchQuery: String? = null
        if (intent != null) {
            searchQuery = intent.getStringExtra(ARG_STR_SEARCH_QUERY)
        }

        presenter.searchTracksForText(searchQuery)
    }


    private fun initView() {
        imgBack.setOnClickListener { finish() }

        rvSearchResults.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rvSearchResults.adapter = TrackAdapter(null)

        edFilter.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.filterTracksWithText(text)
            }
        })
    }


// SearchResultsMvpView ============================================================================

    override fun render(model: SearchResultsScreenModel) {
        tvSearchResultTitle.text = model.searchText ?: ""
        prgSearching.visibility = if (model.isLoading) View.VISIBLE else View.GONE

        val tracks = model.tracks
        // TODO Split
        if (!model.isLoading) {
            if (tracks != null) {
//            Snackbar.make(rvSearchResults, "Found tracks: " + tracks.size, Snackbar.LENGTH_LONG).show()
                tvNoResults.visibility = if (tracks.isNotEmpty()) View.GONE else View.VISIBLE

                rvSearchResults.adapter = TrackAdapter(model.tracks)

                val tracksCount = tracks.size
                tvFoundSongsCount.text = resources.getQuantityString(R.plurals.found_tracks, tracksCount, tracksCount)

                val hideFilterTracksView = (tracksCount == 0) && (model.filterText == null)
                edFilter.visibility = if (hideFilterTracksView) View.GONE else View.VISIBLE

                val searchIntent = RxSearchObservable()
                        .fromView(edFilter)
                        .debounce(300, TimeUnit.MILLISECONDS)
                // TODO Filter
                searchIntent.subscribe { Log.v(TAG, it) }
            } else {
                tvNoResults.visibility = View.GONE
                edFilter.visibility = View.GONE
                rvSearchResults.adapter = TrackAdapter(tracks)
                Snackbar.make(rvSearchResults, R.string.err_failed_to_search, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.btnRetry) { this.searchTracksForTextInIntentExtra() }   // TODO Via presenter
                        .show()
            }
        }
    }


// Navigator =======================================================================================

    private class SearchResultsNavigator(val activity: FragmentActivity) : SupportAppNavigator(activity, 0) {
        override fun createActivityIntent(context: Context?, screenKey: String?, data: Any?): Intent = Intent()

        override fun createFragment(screenKey: String?, data: Any?): Fragment = Fragment()
    }
}